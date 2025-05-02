# Spring Boot 內嵌 Tomcat 教學

這份文件將分為三個階段（初級、中級、高級），協助你一步步理解並掌握 Spring Boot 內建的 Tomcat，並能自行設定、排錯與優化。

---

## 🟢 初級階段：從零開始理解什麼是 Spring Boot 與內嵌 Tomcat

### ✅ 什麼是 Spring Boot？

Spring Boot 是一個讓你快速建立 Java Web 應用的工具，它可以自動幫你設定大部分的東西。

### ✅ 什麼是 Tomcat？

Tomcat 是一種「Web Server」，它可以讓你的 Java 程式變成一個網站，別人可以用瀏覽器開啟。

### ✅ 什麼叫做“內嵌” Tomcat？

通常我們要額外安裝 Tomcat 才能跑網站，但 Spring Boot 直接把 Tomcat 包在裡面，省得你自己安裝，非常方便！

### ✅ 建立一個最簡單的 Spring Boot 專案（內嵌 Tomcat）

#### 🧪 使用 Spring Initializr（[https://start.spring.io）](https://start.spring.io）)

* Project: Maven
* Language: Java
* Spring Boot: 最新版本
* Dependencies: Spring Web
* 產出後用 IDE（如 IntelliJ）打開

#### 📁 專案結構

```
my-spring-app/
├── src/
│   └── main/java/com/example/demo/DemoApplication.java
├── pom.xml
```

#### ✏️ 程式碼範例：`DemoApplication.java`

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot Tomcat!";
    }
}
```

#### ▶️ 執行程式：

```bash
./mvnw spring-boot:run
```

然後打開瀏覽器輸入：[http://localhost:8080，就會看到文字「Hello](http://localhost:8080，就會看到文字「Hello), Spring Boot Tomcat!」

---

## 🟡 中級階段：客製化與擴充 Tomcat 的使用

### ✅ 改變 Tomcat 的預設 Port

在 `application.properties` 加上：

```properties
server.port=9090
```

### ✅ 設定 Context Path

```properties
server.servlet.context-path=/myapp
```

打開瀏覽器：[http://localhost:9090/myapp/](http://localhost:9090/myapp/)

### ✅ 設定最大上傳檔案大小

```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### ✅ 改寫 Tomcat 的參數（例如連線數、逾時時間）

```java
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return factory -> {
            factory.setPort(8080); // 這行可有可無，除非你想動態設定
            factory.addConnectorCustomizers(connector -> {
                connector.setProperty("maxThreads", "200");
                connector.setProperty("connectionTimeout", "20000");
            });
        };
    }
}
```

### ✅ 加入 HTTPS 支援

1. 建立憑證（可用 keytool）

```bash
keytool -genkeypair -alias mycert -keyalg RSA -keystore keystore.p12 -storetype PKCS12 -storepass 123456 -validity 365 -keysize 2048
```

2. 在 `application.properties` 中加入：

```properties
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=123456
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=mycert
```

---

## 🔴 高級階段：錯誤處理與效能調校

### ✅ 查看 Tomcat 啟動日誌

啟動時會列出 Tomcat 綁定的 Port、執行環境等訊息。常見問題如 Port 被佔用：

```bash
Address already in use: bind
```

解法：換 Port 或關掉其他佔用程式。

### ✅ 自訂錯誤頁面

```java
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return "錯誤！狀態碼：" + statusCode;
    }
}
```

### ✅ 效能調校方向：

1. **增加 maxThreads**（處理更多請求）
2. **減少 connectionTimeout**（避免卡太久）
3. **GZIP 壓縮**：加速回應時間

```properties
server.compression.enabled=true
server.compression.mime-types=text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json
server.compression.min-response-size=1024
```

4. **靜態資源快取**：避免每次都重新載入

```properties
spring.web.resources.cache.cachecontrol.max-age=3600
```

5. **Thread dump 與 memory dump**：排查慢速問題（需搭配 JDK 工具）

---

以上即為從入門到進階的 Spring Boot 內嵌 Tomcat 教學，適合初學者一路學到實戰高手。
