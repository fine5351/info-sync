你說:
我現在需要開發一個poc ,有以下需求

1. 因 request 含機敏資料, 所以將 request 存入 DB 時需要針對機敏欄位進行AES256加密, 且每次加密時 IV 都要不同
2. 使用 shardingshpere 來接管加解密的部分
3. 使用 shardingshpere proxy 來給與其他系統使用 jdbc 進行串接並直接取得明文
4. 因為是poc, 所以只要一個 api, request 是 name, rocId, address, 其中 rocId 是機敏資料
5. 使用 SpringBoot3 進行開發
6. DB 採用 maria db

CHATGPT說:
根據您的需求，整體目標是建立一個簡單的 API，能夠接收含有機敏資料的請求並安全地存入資料庫，並利用 ShardingSphere Proxy 提供其他系統透過 JDBC 明文存取的能力。以下是此設計的可行性評估與潛在問題分析：

---

### ✅ 可行性分析

1. **AES256 加密 + 不同 IV：**

- 使用不同 IV 是提升安全性的重要做法。ShardingSphere 支援這項需求，需設定 `aes.encryptor.iv-generator-class-name`。
- IV 必須與密文一同儲存，ShardingSphere 預設行為會自動處理這點。

2. **ShardingSphere 接管加解密：**

- ShardingSphere 提供加解密規則配置，可支援 AES 加密。
- 可透過配置 `encrypt-rule.yaml` 設定 rocId 欄位的加密規則。
- 若要支援 IV 每次不同，需自定義 `IVGenerator`，例如使用 `RandomIVGenerator`。

3. **ShardingSphere Proxy 提供 JDBC 串接：**

- Proxy 模式支援讓其他系統以標準 JDBC 連接，取得自動解密後的明文資料。
- 須注意：此方式預設是**無驗證機制的解密**，任何擁有 JDBC 資訊的用戶都能看到明文，建議限制 Proxy 的使用者權限。

4. **SpringBoot3 + MariaDB：**

- Spring Boot 3 可整合 JPA 或 MyBatis，與 MariaDB 配合穩定。
- 可使用 Spring 的 REST Controller 建立單一 POST API。

---

### ⚠️ 潛在問題與需確認項目

| 問題項目 | 詳細說明 | 建議動作 |
|----------|----------|----------|
| 密鑰管理 | AES 密鑰需安全存儲與讀取，ShardingSphere 預設將密鑰明文寫在 YAML 中。 | 使用環境變數或整合 Vault/KMS 等密鑰管理系統。 |
| IV 儲存 | IV 每次加密需不同，ShardingSphere 會儲存在欄位中或旁欄位。 | 確認 IV 的儲存方式與解密自動對應是否正確。 |
| Proxy 明文存取風險 | Proxy 直接輸出明文，若未限制權限將有資安風險。 | 限制 Proxy 使用者權限與網路來源，必要時加設應用層 ACL。 |
| 加密欄位查詢限制 | 被加密的欄位無法進行模糊查詢、排序等操作。 | 若需查詢功能，考慮額外建立 index 欄位存放 hash。 |

---

### ✅ 推薦做法參考（最佳實踐）

- **ShardingSphere 官方加解密設計文件：**  
  [https://shardingsphere.apache.org/document/current/en/user-manual/shardingsphere-proxy/feature/encrypt/](https://shardingsphere.apache.org/document/current/en/user-manual/shardingsphere-proxy/feature/encrypt/)

- **IV Generator 自定義方式：**  
  自訂一個 `IVGenerator`，實作亂數 IV 並儲存於資料庫對應欄位中。

---

是否已考慮上述密鑰管理與 Proxy 存取風險的對策？如果尚未，我可以提供具體範例或設計建議。是否需要我幫您草擬一份初步的專案架構與配置範本？

你說：

1. aes key 會放在 vault 中, runtime 注入環境變數
2. iv 存於旁欄位
3. proxy 資安不需考慮, 前端已有限制
4. 被加密欄位不會被查詢

ChatGPT 說：
感謝補充，根據您的回應，設計明確且具可行性，以下是針對您補充資訊後的進一步評估與建議：

---

### ✅ 實作設計確認

| 項目 | 實作情況 | 評估 |
|------|----------|------|
| **AES 密鑰管理** | Vault 管理，Runtime 注入環境變數 | ✅ 合理作法，可避免密鑰硬編碼。建議設計啟動時讀取並注入 Encryptor。 |
| **IV 儲存設計** | 存於旁欄位 | ✅ 完全符合 ShardingSphere 設計原則，無額外限制。 |
| **Proxy 安全考量** | 前端已限制使用 | ✅ 若在可信環境中使用，風險可接受。但仍建議保留操作紀錄作審計依據。 |
| **查詢需求** | 加密欄位不查詢 | ✅ 避免了加密帶來的查詢限制與效能問題。 |

---

### ✅ 建議實作架構

1. **SpringBoot 層：**

- 建立單一 `POST /api/submit` API。
- 接收參數 `name`, `rocId`, `address`，無需在程式中進行加密邏輯。
- 使用 JPA 或 MyBatis 直接寫入資料庫。

2. **ShardingSphere Proxy 層：**

- 配置 `encrypt-rule.yaml`：
  ```yaml
  encryptors:
    aes_encryptor:
      type: AES
      props:
        aes-key-value: ${AES_KEY} # 由環境變數注入
        iv-generator-class-name: org.apache.shardingsphere.encrypt.api.context.RandomIVGenerator
  tables:
    your_table:
      columns:
        roc_id:
          cipherColumn: roc_id_cipher
          assistedQueryColumn: roc_id_iv
          encryptorName: aes_encryptor
  ```

3. **DB 設計：**

- `roc_id_cipher` 欄位儲存密文。
- `roc_id_iv` 欄位儲存 IV。
- 實體欄位為 `rocId`，其他欄位照常設計。

---

### ✅ 接下來步驟建議

1. 建立 MariaDB 資料表與欄位（含 IV 欄位）。
2. 設定 ShardingSphere Proxy 並測試 JDBC 連線。
3. 使用 SpringBoot 建立 API 並連線 Proxy。
4. 實測寫入與查詢資料是否正確加解密。
5. 實測多筆請求是否生成不同 IV（驗證安全性）。

---

如需，我可協助您草擬 `encrypt-rule.yaml` 完整範例與 SpringBoot API 實作樣板。是否要繼續往這方向進行？

你說：
先將 jpa 更換為 mybatis 再往下做

ChatGPT 說：
了解，將 JPA 換成 MyBatis 是個合理選擇，能讓您更精細控制 SQL 與資料對應欄位，尤其是在涉及加解密、旁欄位（如 IV）管理的情境中會更彈性。

---

### ✅ MyBatis 整合建議步驟

1. **移除 JPA 依賴：**
   ```xml
   <!-- pom.xml -->
   <!-- 移除 spring-boot-starter-data-jpa -->
   ```

2. **加入 MyBatis 依賴：**
   ```xml
   <!-- pom.xml -->
   <dependency>
     <groupId>org.mybatis.spring.boot</groupId>
     <artifactId>mybatis-spring-boot-starter</artifactId>
     <version>3.0.2</version> <!-- 與 Spring Boot 3 相容版本 -->
   </dependency>
   ```

3. **配置 MyBatis：**
   ```yaml
   # application.yaml
   mybatis:
     mapper-locations: classpath:mapper/*.xml
     type-aliases-package: com.example.model
   ```

4. **定義 Model 與 Mapper：**
   ```java
   // RequestData.java
   public class RequestData {
       private String name;
       private String rocId;   // 對應加密欄位
       private String address;
   }
   ```

   ```java
   // RequestDataMapper.java
   @Mapper
   public interface RequestDataMapper {
       void insert(RequestData data);
   }
   ```

5. **建立 Mapper XML：**
   ```xml
   <!-- mapper/RequestDataMapper.xml -->
   <insert id="insert" parameterType="com.example.model.RequestData">
     INSERT INTO request_data (name, roc_id, address)
     VALUES (#{name}, #{rocId}, #{address})
   </insert>
   ```

6. **Service 與 Controller 建立 API：**

- 單一 `POST /api/submit` API，接收 name、rocId、address 直接存入資料庫。

---

### ✅ 下一步確認

請確認以下幾點，以利進入 ShardingSphere Proxy 的 encrypt 設定：

- 您是否已完成 MariaDB 建表（含加密與 IV 欄位）？
- 是否已設定並啟動 ShardingSphere Proxy 並成功連線 MariaDB？
- 是否要我幫您草擬 `encrypt-rule.yaml` 與 Proxy 啟動配置範例？

只要確認好這些，我可幫您接著設計完整的加解密測試流程與 API 整合邏輯。是否繼續？

你說：

1. MariaDB 未建立
2. 未啟動 proxy 並連線 DB
3. 需要草擬 encrypt-rule.yml 與 proxy 啟動配置

ChatGPT 說：
非常好，我會依據您的需求設計完整配置，包括：

---

## ✅ 1. MariaDB 資料表設計

```sql
CREATE TABLE request_data (
                            id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name          VARCHAR(255),
                            roc_id_cipher VARCHAR(512), -- 加密後 rocId
                            roc_id_iv     VARCHAR(255), -- IV 存放欄位
                            address       VARCHAR(255)
);
```

---

## ✅ 2. `encrypt-rule.yaml` 設定（ShardingSphere 5.x 以上）

```yaml
schemaName: logic_db

dataSources:
  ds_0:
    url: jdbc:mariadb://127.0.0.1:3306/testdb
    username: root
    password: root
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50

rules:
  - !ENCRYPT
    encryptors:
      aes_encryptor:
        type: AES
        props:
          aes-key-value: ${AES_KEY} # 環境變數注入，啟動時指定
          iv-generator-class-name: org.apache.shardingsphere.encrypt.api.context.RandomIVGenerator
    tables:
      request_data:
        columns:
          roc_id:
            plainColumn: null
            cipherColumn: roc_id_cipher
            assistedQueryColumn: roc_id_iv
            encryptorName: aes_encryptor
```

---

## ✅ 3. ShardingSphere Proxy 啟動方式（以 Docker 為例）

### `docker-compose.yml` 範例：

```yaml
version: '3.8'
services:
  proxy:
    image: apache/shardingsphere-proxy:latest
    container_name: shardingsphere-proxy
    ports:
      - "3307:3307"
    environment:
      - AES_KEY=your_32_byte_hex_key_here
    volumes:
      - ./conf:/opt/shardingsphere-proxy/conf
```

請將上述的 `encrypt-rule.yaml` 放在 `./conf` 資料夾，並同時備妥以下兩個必要設定檔：

### `server.yaml` 最小設定：

```yaml
authority:
  users:
    - root@%:root

props:
  sql-show: true
  sql-comment-parse-enabled: true
```

### `config-sharding.yaml`（空檔案，或略過）

若無 sharding 功能，可不設。

---

## ✅ 接下來建議步驟

1. **建立 MariaDB 資料表**
2. **設定並啟動 ShardingSphere Proxy**
3. **測試使用 JDBC（透過 proxy）可正常讀寫並取得明文**
4. **SpringBoot MyBatis 接 proxy JDBC，開發 API**

---

是否需要我同時提供 JDBC 測試工具程式，驗證加解密是否正確？或者先協助您建立 proxy 本機環境並驗證通訊可行？

你說：

1. 先驗證加解密是否正確, 是否真的透過 shardingshpere 來接手處理
2. 設定並啟動 proxy
3. mybatis 串接 proxy

ChatGPT 說：
非常清楚，以下是您目前的目標順序及我建議的對應操作方式：

---

## ✅ [1] 驗證加解密是否正確（是否由 ShardingSphere 處理）

### 驗證目標：

- 使用 JDBC（連接 ShardingSphere Proxy）
- 插入含有機敏資料的 SQL
- 確認：
  1. **寫入後 DB 中為密文（roc_id_cipher）**
  2. **查詢結果中 roc_id 為明文**

### 驗證方式（簡易 Java 程式）：

```java
public class EncryptTest {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:mariadb://localhost:3307/logic_db"; // Proxy port
    String user = "root";
    String password = "root";

        Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO request_data (name, roc_id, address) VALUES (?, ?, ?)");
        ps.setString(1, "John");
        ps.setString(2, "A123456789"); // 機敏資料
        ps.setString(3, "Taipei");
        ps.executeUpdate();

        // 查詢結果是否是明文
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM request_data");
        while (rs.next()) {
            System.out.println("roc_id (明文): " + rs.getString("roc_id"));
        }

        conn.close();
    }
}
```

> **預期結果：**
> - `roc_id_cipher` 實際 DB 欄位為密文（可用 phpMyAdmin / CLI 驗證）
> - 程式中 `roc_id` 欄位應直接輸出原始明文 `A123456789`

---

## ✅ [2] 設定並啟動 ShardingSphere Proxy（Docker）

### 啟動環境準備步驟：

1. 建立以下資料夾結構：
   ```
   shardingsphere-proxy/
   ├── conf/
   │   ├── server.yaml
   │   ├── config-encrypt.yaml
   └── docker-compose.yml
   ```

2. 使用前面提供的設定檔內容。

3. 執行：

```bash
docker compose up -d
```

4. 確認 Proxy 已成功啟動並可連線 MariaDB。

---

## ✅ [3] Spring Boot 使用 MyBatis 串接 Proxy

### application.yaml 配置：

```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3307/logic_db
    username: root
    password: root
    driver-class-name: org.mariadb.jdbc.Driver
```

### 接下來實作方式：

- 使用 `@Mapper` 建立 MyBatis 接口與 XML
- 使用上述測試過的 SQL 結構

---

我建議先協助您**驗證 Proxy 加解密成功**（Step 1），我可以產出完整 JDBC 測試程式碼與執行指引，是否要我幫您準備這份程式碼與測試步驟？

你說：
好

ChatGPT 說：
以下是您可以直接使用的 **MariaDB + ShardingSphere Proxy 的 `docker-compose.yml` 配置**，包含所有必要設定檔案與目錄結構說明：

---

## ✅ 目錄結構

請建立如下目錄與檔案結構：

```
shardingsphere-proxy/
├── docker-compose.yml
└── conf/
    ├── server.yaml
    └── config-encrypt.yaml
```

---

## ✅ `docker-compose.yml`

```yaml
version: '3.8'
services:
  mariadb:
    image: mariadb:10.6
    container_name: mariadb
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: testdb
    ports:
      - "3306:3306"
    volumes:
      - mariadb_data:/var/lib/mysql

  proxy:
    image: apache/shardingsphere-proxy:5.4.1
    container_name: shardingsphere-proxy
    depends_on:
      - mariadb
    ports:
      - "3307:3307"
    environment:
      - AES_KEY=0123456789abcdef0123456789abcdef
    volumes:
      - ./conf:/opt/shardingsphere-proxy/conf

volumes:
  mariadb_data:
```

---

## ✅ `conf/server.yaml`

```yaml
authority:
  users:
    - root@%:root

props:
  sql-show: true
  sql-comment-parse-enabled: true
```

---

## ✅ `conf/config-encrypt.yaml`

```yaml
schemaName: logic_db

dataSources:
  ds_0:
    url: jdbc:mariadb://mariadb:3306/testdb
    username: root
    password: root
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50

rules:
  - !ENCRYPT
    encryptors:
      aes_encryptor:
        type: AES
        props:
          aes-key-value: ${AES_KEY}
          iv-generator-class-name: org.apache.shardingsphere.encrypt.api.context.RandomIVGenerator
    tables:
      request_data:
        columns:
          roc_id:
            cipherColumn: roc_id_cipher
            assistedQueryColumn: roc_id_iv
            encryptorName: aes_encryptor
```

---

## ✅ 啟動方式

```bash
cd shardingsphere-proxy
docker compose up -d
```

> Proxy JDBC 連線資訊為：
> ```
> jdbc:mariadb://localhost:3307/logic_db
> user: root
> password: root
> ```

---

接下來您只需連進 Proxy 所對應的 `testdb` 資料庫並建立 `request_data` 表即可進行測試。是否需要我補上對應的建表 SQL？

你說：
解釋一下 proxy 怎麼知道儲存 iv 的欄位, 以及怎麼使用這個欄位

ChatGPT 說：
這是一個核心問題，以下是 **ShardingSphere Proxy 如何處理 IV 的邏輯說明**：

---

## ✅ IV 在加解密過程中的角色

在使用 **AES-CBC 模式**時（ShardingSphere 預設模式），**每筆資料都需要一組 IV（Initial Vector）**。為了達到每筆資料不同 IV 的目標，ShardingSphere 允許設定 **旁欄位（assistedQueryColumn）** 來儲存 IV，並在解密時使用。

---

## ✅ 如何讓 Proxy 知道 IV 儲存在哪裡？

透過 `config-encrypt.yaml` 裡的設定：

```yaml
columns:
  roc_id:
    cipherColumn: roc_id_cipher             # 儲存加密後資料
    assistedQueryColumn: roc_id_iv         # 儲存 IV（Initial Vector）
    encryptorName: aes_encryptor
```

這邊明確告訴 ShardingSphere：

- 對於欄位 `roc_id`：
  - 真正儲存在 DB 的加密資料在欄位 `roc_id_cipher`
  - 該筆資料對應的 IV 儲存在欄位 `roc_id_iv`
  - 加密邏輯由 `aes_encryptor` 處理

---

你說:
aes-key-value 注入時, 變數名稱應該是什麼?

在 ShardingSphere Proxy 的 `config-encrypt.yaml` 中使用環境變數注入 `aes-key-value` 時，**變數名稱必須符合 YAML 的替代語法**，預設格式如下：

```yaml
aes-key-value: ${AES_KEY}
```

---

## ✅ 對應關係說明

| YAML 配置                    | 對應 Docker 環境變數名 |
|-----------------------------|--------------------------|
| `aes-key-value: ${AES_KEY}` | `AES_KEY`               |

因此，若您在 `encrypt-rule.yaml` 或 `config-encrypt.yaml` 中這樣寫：

```yaml
aes-key-value: ${AES_KEY}
```

您就**必須在 Docker 環境變數中設定**如下：

```yaml
environment:
  - AES_KEY=0123456789abcdef0123456789abcdef
```

---

## ✅ 注意事項

1. AES-256 的 key 長度須為 **32 個字元（256 bit）**
2. Proxy 啟動時會讀取環境變數並動態替換 `${AES_KEY}` 為實際密鑰值
3. 若環境變數未設定，Proxy 啟動會失敗或加解密錯誤

---

是否需要我協助檢查目前 Docker Compose 的環境變數是否正確？或者補充如何使用 Vault 注入這個值？

## ✅ ShardingSphere Proxy 使用 IV 的流程

| 階段 | 說明 |
|------|------|
| **INSERT** | ShardingSphere 會自動生成一組隨機 IV（透過 `iv-generator-class-name`），然後：<br>1. 使用 AES_KEY + IV 加密 roc_id<br>2. 將密文寫入 `roc_id_cipher`，IV 寫入 `roc_id_iv` |
| **SELECT** | 查詢時，ShardingSphere：<br>1. 從 `roc_id_cipher` 取得密文<br>2. 從 `roc_id_iv` 取得 IV<br>3. 使用 AES_KEY + IV 解密為明文 roc_id，並回傳給應用程式 |

---

## ✅ 小結

- **assistedQueryColumn** 在 AES 模式下，其實就被用來**儲存 IV**
- ShardingSphere Proxy 全自動讀寫，不需開發者額外處理 IV
- IV 欄位命名雖叫 "assistedQuery"，實際用法由加密器決定（在 AES 中即代表 IV）

---

以下是您目前架構下，**Spring Boot 透過 MyBatis 串接 ShardingSphere Proxy（含加解密）** 的完整範例設定：

---

## ✅ 1. `application.yaml` – SpringBoot 資料庫連線設定

```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3307/logic_db
    username: root
    password: root
    driver-class-name: org.mariadb.jdbc.Driver

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.example.model
```

---

## ✅ 2. Model：`RequestData.java`

```java
package com.example.model;

public class RequestData {
    private String name;
    private String rocId;  // 明文，實際由 Proxy 加密
    private String address;

    // Getters & Setters
}
```

---

## ✅ 3. Mapper 介面：`RequestDataMapper.java`

```java
package com.example.mapper;

import com.example.model.RequestData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequestDataMapper {
    void insert(RequestData data);
}
```

---

## ✅ 4. Mapper XML：`mapper/RequestDataMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.mapper.RequestDataMapper">

  <insert id="insert" parameterType="com.example.model.RequestData">
    INSERT INTO request_data (name, roc_id, address)
    VALUES (#{name}, #{rocId}, #{address})
  </insert>

</mapper>
```

---

## ✅ 5. Controller 示範

```java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RequestDataController {

    private final RequestDataMapper mapper;

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody RequestData data) {
        mapper.insert(data);
        return ResponseEntity.ok("資料已送出");
    }
}
```

---

## ✅ 資料流程總結

1. 使用者呼叫 `/api/submit` 傳入明文 `rocId`
2. MyBatis 執行 SQL，透過 JDBC 傳給 ShardingSphere Proxy
3. Proxy 根據 `config-encrypt.yaml` 自動加密 `rocId` 為 `roc_id_cipher` 並寫入 DB

---

是否還需要我幫您產出一個可運行的 Spring Boot 最小專案骨架？或是加入查詢 API 進行反查驗證？

以下是針對您需求所設計的 **最小可運行 Spring Boot + MyBatis 專案骨架**，已整合：

- ✅ MyBatis 與 ShardingSphere Proxy 串接
- ✅ `POST /api/submit` API，儲存機敏資料（rocId）
- ✅ `GET /api/latest` API，用於查詢最新一筆資料（驗證明文解密）

---

## ✅ 專案架構

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── DemoApplication.java
│   │   ├── controller/RequestDataController.java
│   │   ├── mapper/RequestDataMapper.java
│   │   └── model/RequestData.java
│   └── resources/
│       ├── application.yaml
│       └── mapper/RequestDataMapper.xml
```

---

## ✅ 1. `application.yaml`

```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3307/logic_db
    username: root
    password: root
    driver-class-name: org.mariadb.jdbc.Driver

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.example.demo.model

server:
  port: 8080
```

---

## ✅ 2. `DemoApplication.java`

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

---

## ✅ 3. `RequestData.java`

```java
package com.example.demo.model;

public class RequestData {
    private Long id;
    private String name;
    private String rocId;
    private String address;

    // Getters and Setters
}
```

---

## ✅ 4. `RequestDataMapper.java`

```java
package com.example.demo.mapper;

import com.example.demo.model.RequestData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequestDataMapper {
    void insert(RequestData data);
    RequestData selectLatest();
}
```

---

## ✅ 5. `RequestDataMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.demo.mapper.RequestDataMapper">

  <insert id="insert" parameterType="com.example.demo.model.RequestData">
    INSERT INTO request_data (name, roc_id, address)
    VALUES (#{name}, #{rocId}, #{address})
  </insert>

  <select id="selectLatest" resultType="com.example.demo.model.RequestData">
    SELECT id, name, roc_id, address
    FROM request_data
    ORDER BY id DESC
    LIMIT 1
  </select>

</mapper>
```

---

## ✅ 6. `RequestDataController.java`

```java
package com.example.demo.controller;

import com.example.demo.mapper.RequestDataMapper;
import com.example.demo.model.RequestData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RequestDataController {

    private final RequestDataMapper mapper;

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody RequestData data) {
        mapper.insert(data);
        return ResponseEntity.ok("已寫入");
    }

    @GetMapping("/latest")
    public ResponseEntity<RequestData> getLatest() {
        return ResponseEntity.ok(mapper.selectLatest());
    }
}
```

---

## ✅ 測試流程

1. 啟動 ShardingSphere Proxy + MariaDB
2. 啟動 Spring Boot 專案
3. 使用以下 JSON 呼叫 `/api/submit`：
   ```json
   {
     "name": "Alice",
     "rocId": "A123456789",
     "address": "Taipei"
   }
   ```
4. 呼叫 `/api/latest`，預期取得：
   ```json
   {
     "id": 1,
     "name": "Alice",
     "rocId": "A123456789",  // 明文，已解密
     "address": "Taipei"
   }
   ```

---

如需，我可直接打包成 GitHub 或 ZIP 專案結構提供下載。是否要我產出專案壓縮包或 Git 倉庫模板？

### 參考資料

https://juejin.cn/post/6999992099978346527
