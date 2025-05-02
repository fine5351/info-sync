## 🟢 初級階段：快速建置系統（<100人使用，無高併發）

### ✅ 系統背景

- 使用人數不多（<100）
- 無高併發壓力
- 目標：快速交付，功能能正常運作即可

---

### ✅ 架構建議

- 後端框架：Spring Boot
- 資料庫：MySQL / PostgreSQL
- 部署方式：單機部署（可使用 Docker）
- 前端可搭配簡單框架（如 React / Vue）

---

### ✅ 架構圖範例（單節點）

```
使用者 → Nginx (反向代理) → Spring Boot → MySQL
```

---

### ✅ 重點設計方向

- 開發效率 > 可擴展性
- 程式碼結構清晰（三層架構）

```text
Controller：處理請求
Service：業務邏輯
Repository：資料存取
```

- 搭配 Swagger 建立 API 文件
- 使用 Spring Security 實作基本身份驗證
- 使用 JWT 管理登入狀態與權限

---

### ✅ RESTful API 設計原則

- 使用 HTTP 動詞（GET、POST、PUT、DELETE）
- URL 命名以資源為導向

```http
GET    /users
POST   /users
GET    /users/{id}
PUT    /users/{id}
DELETE /users/{id}
```

---

### ✅ 範例程式碼（Spring Boot）

```java

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
```

---

### ✅ 資料庫設計與索引

- 主鍵與外鍵設計需合理
- 多表查詢使用 JOIN 時應建立適當索引
- 常用索引類型：單欄索引、複合索引、唯一索引

---

### ✅ 錯誤處理與日誌紀錄

- 使用 `@ControllerAdvice` 與 `@ExceptionHandler` 處理例外

```java

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("系統錯誤：" + ex.getMessage());
    }
}
```

- 使用 SLF4J + Logback 記錄日誌

---

### ✅ 設定與環境管理

- 使用 Spring Profiles 分離環境（如 dev、prod）
- 配置檔案使用 `application.yml`

```yaml
spring:
  profiles:
    active: dev
```

---

### ✅ API 文件與測試工具

- 使用 Swagger / Springdoc OpenAPI 自動生成文件
- 使用 Postman 進行測試與模擬請求

---

### ✅ 身分驗證機制

- 使用 Spring Security 管理權限
- JWT 作為登入憑證傳遞

```java
String token = Jwts.builder()
        .setSubject(username)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
```

---

### ✅ CI/CD 初步實踐

- 透過 GitHub Actions / GitLab CI 實作自動化流程
    - Push 後自動執行測試
    - 自動打包、部署至目標主機（如 AWS、GCP、VPS）

---

### ✅ Docker 與 Nginx 基礎應用

- 簡單 Dockerfile 範例：

```dockerfile
FROM openjdk:17
COPY target/demo.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

- 基本 Nginx 反向代理設定：

```nginx
server {
    listen 80;
    location / {
        proxy_pass http://localhost:8080;
    }
}
```

---

### ✅ 三層架構（Controller - Service - Repository）

優點：

- 模組責任分明
- 易於維護與擴充
- 減少耦合度

---

### ✅ 分散式系統相關概念

#### 🧠 CAP 理論

- **C（Consistency）一致性**：所有節點資料相同
- **A（Availability）可用性**：每次請求都有回應
- **P（Partition tolerance）分區容錯**：即使網路分割也能繼續運作

> CAP 不可同時兼顧，分散式系統只能三選二：
> - AP：如 Cassandra（可用 + 容錯，犧牲一致性）
> - CP：如 Zookeeper（一致性 + 容錯，犧牲部分可用）

---

#### ⚙️ CQRS 模式（Command Query Responsibility Segregation）

> 將「寫入邏輯」與「查詢邏輯」分離，提高查詢效能與可擴展性

適用場景：

- 查詢遠多於寫入
- 查詢需快取但不影響寫入邏輯

實作範例：

```java
// 寫入邏輯
orderService.createOrder(orderDto);

// 查詢邏輯
List<OrderDto> orders = orderQueryService.findOrdersByUser(userId);
```

- 資料存放：
    - Command：MySQL 寫入
    - Query：Elasticsearch / Redis 查詢

---

#### 🔒 分散式鎖（Distributed Lock）

解決：多節點同時執行同一業務邏輯的併發問題（如秒殺、排程）

常用工具：

1. Redis + SETNX 實作簡易鎖
2. Redisson：高階封裝、易於整合
3. ZooKeeper：強一致性鎖方案（適用於金融等要求一致性的場景）

Redisson 使用範例：

```java
RLock lock = redissonClient.getLock("create_order_lock");
if(lock.

tryLock(10,5,TimeUnit.SECONDS)){
        try{
        // 執行關鍵邏輯
        }finally{
        lock.

unlock();
    }
            }
```