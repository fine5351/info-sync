# Spring 操作 Minio 教學（初級、中級、高級）

---

## 初級階段：第一次接觸 Minio 的小白

### 目標

完全沒碰過 Minio 的人，學會基礎文件上傳下載。

---

### 1. 什麼是 Minio？

- Minio 是一個免費的「開源對象存儲服務」，可以當作自己的私人網盤（類似 AWS S3）
- 例如：存圖片、影片、文件，並用程式控制存取

### 2. 環境準備

#### 步驟 1：安裝 Minio

```bash
# 下載 Minio 執行檔（Windows 範例）
wget https://dl.min.io/server/minio/release/windows-amd64/minio.exe

# 啟動 Minio（資料存在 D:\minio-data）
minio.exe server D:\minio-data --console-address :9090
```

打開瀏覽器訪問 http://localhost:9090，帳號密碼預設為 minioadmin/minioadmin

#### 步驟 2：創建 Spring Boot 專案

訪問 Spring Initializr
選擇依賴：
Spring Web
Lombok（簡化程式碼）

#### 步驟 3：添加 Minio 依賴

<!-- pom.xml -->

```xml

<dependency>
  <groupId>io.minio</groupId>
  <artifactId>minio</artifactId>
  <version>8.5.2</version>
</dependency>
```

### 3. 基礎操作：上傳文件

#### 步驟 1：設定連線資訊

properties

```yml
# application.properties
minio.endpoint=http://localhost:9000
minio.access-key=minioadmin
minio.secret-key=minioadmin
minio.bucket-name=my-bucket
```

#### 步驟 2：編寫上傳程式

```java

@Configuration
public class MinioConfig {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}

@Service
@RequiredArgsConstructor
public class FileService {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public void uploadFile(String objectName, InputStream stream) throws Exception {
        // 檢查存儲桶是否存在
        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );

        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
            );
        }

        // 上傳文件
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(stream, -1, 10485760)  // 10MB 分片
                        .build()
        );
    }
}
```

#### 步驟 3：測試上傳

使用 Postman 發送 POST 請求到你的 API

在 Minio 網頁界面檢查 my-bucket 是否出現文件

## 中級階段：進階功能與自定義

### 目標

學會管理文件、設定權限、自定義異常處理

##### 1. 列出所有文件

   ```java
   public List<String> listFiles() throws Exception {
    Iterable<Result<Item>> results = minioClient.listObjects(
            ListObjectsArgs.builder().bucket(bucketName).build()
    );

    List<String> files = new ArrayList<>();
    for (Result<Item> result : results) {
        files.add(result.get().objectName());
    }
    return files;
}
   ```

##### 2. 生成限時下載鏈接

   ```java
   public String getDownloadUrl(String fileName) throws Exception {
    return minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(24 * 60 * 60)  // 24 小時有效期
                    .build()
    );
}
   ```

##### 3. 自定義異常處理

   ```java
   public InputStream downloadFile(String fileName) {
    try {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    } catch (ErrorResponseException e) {
        throw new RuntimeException("⚠️ 文件不存在！", e);
    }
}
   ```

## 高級階段：故障排除與效能優化

### 目標

解決線上問題，提升存取速度

#### 1. 常見錯誤排查表

錯誤訊息 可能原因 解決方法
Connection refused Minio 服務未啟動 檢查 Minio 是否運行在 9000 端口
Access Denied 金鑰錯誤 檢查 access-key/secret-key
NoSuchBucket 存儲桶不存在 先檢查 bucket 是否存在

#### 2. 效能優化技巧

##### 技巧 1：重用 MinioClient

   ```java
   // 在配置類創建單例
@Bean
@Scope("singleton")  // 默認就是單例，確保不要重複創建
public MinioClient minioClient() {
    // 配置內容...
}
   ```

##### 技巧 2：分片並行上傳

   ```java
   // 上傳大文件時自動分片
   minioClient.putObject(
        PutObjectArgs.builder()
   .

bucket(bucketName)
   .

object("4k-video.mp4")
   .

stream(videoStream, videoStream.available(), 5242880)  // 5MB 分片
        .

build()
   );
   ```

##### 3. 監控與日誌

Minio 日誌配置

   ```bash
# 啟動時添加日誌參數
minio server /data --console-address :9090 --audit-log-dir /var/log/minio
```

Spring Boot 效能監控

```java

@Aspect
@Component
@Slf4j
public class PerformanceMonitor {

    @Around("execution(* com.example.service.*.*(..))")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - start;
        log.info("🕒 方法 {} 執行時間: {}ms",
                joinPoint.getSignature().getName(),
                time);
        return result;
    }
}
```

## 總結

### 階段	重點能力	關鍵工具

#### 中級	權限管理、異常處理	Presigned URL、AOP

#### 初級	基礎文件操作	MinioClient、Postman

#### 高級	效能優化、系統監控	連接池、分片上傳、日誌分析

### 遇到問題三步驟：

#### 檢查網路連線 (telnet localhost 9000)

#### 查看 Minio 控制台日誌

#### 在 Spring Boot 添加斷點偵錯

#### 小秘訣：用 minioClient.traceOn() 可以開啟詳細請求日誌，偵錯超方便！