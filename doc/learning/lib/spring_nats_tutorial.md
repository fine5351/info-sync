# Spring Boot 操作 NATS 教學

本教學將從初學者到高階開發者分三階段講解如何使用 Spring Boot 與 NATS（高效能訊息系統）整合。

---

## 🟢 初級階段：什麼是 NATS？如何與 Spring 整合？

### ✅ NATS 是什麼？

NATS 是一個超輕量級、高效能的訊息傳遞系統，常用於服務之間的即時通訊、事件通知、非同步處理等場景。

### ✅ 建立 NATS 環境（使用 Docker）

```bash
docker run -d --name nats-server -p 4222:4222 nats:latest
```

### ✅ 建立 Spring Boot 專案

使用 [https://start.spring.io](https://start.spring.io) 建立專案：

* Dependencies 加上：Spring Web、Spring Boot DevTools

### ✅ 加入 NATS Java 客戶端依賴

在 `pom.xml` 中加入：

```xml

<dependency>
  <groupId>io.nats</groupId>
  <artifactId>jnats</artifactId>
  <version>2.16.12</version>
</dependency>
```

### ✅ 最簡單的 NATS 發送與接收訊息範例

```java
@Component
public class NatsClient implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Connection nc = Nats.connect("nats://localhost:4222");

        // 訂閱
        Dispatcher d = nc.createDispatcher(msg -> {
            System.out.println("收到訊息：" + new String(msg.getData()));
        });
        d.subscribe("demo.topic");

        // 發送
        nc.publish("demo.topic", "哈囉 NATS".getBytes());

        Thread.sleep(2000);
        nc.close();
    }
}
```

---

## 🟡 中級階段：進階操作與自訂功能

### ✅ 重構為發送端與接收端分離

#### 發送端

```java
@Service
public class NatsPublisher {
    private final Connection natsConnection;

    public NatsPublisher() throws IOException, InterruptedException {
        this.natsConnection = Nats.connect("nats://localhost:4222");
    }

    public void sendMessage(String subject, String message) {
        natsConnection.publish(subject, message.getBytes());
    }
}
```

#### 接收端

```java
@Component
public class NatsSubscriber {
    public NatsSubscriber() throws IOException, InterruptedException {
        Connection nc = Nats.connect("nats://localhost:4222");
        Dispatcher d = nc.createDispatcher(msg -> {
            System.out.println("接收到：" + new String(msg.getData()));
        });
        d.subscribe("demo.topic");
    }
}
```

### ✅ 支援 JSON 資料格式傳輸

使用 `Jackson` 序列化與反序列化：

```java
ObjectMapper mapper = new ObjectMapper();
String json = mapper.writeValueAsString(myObject);
nc.

publish("json.topic",json.getBytes());
```

### ✅ 實作回應式通訊（Request-Reply）

```java
Message reply = nc.request("demo.topic", "請問現在幾點".getBytes(), Duration.ofSeconds(2));
System.out.

println("回應內容："+new String(reply.getData()));
```

---

## 🔴 高級階段：錯誤處理與效能最佳化

### ✅ 錯誤處理與連線重試

```java
Options options = new Options.Builder()
    .server("nats://localhost:4222")
    .connectionListener((conn, type) -> System.out.println("連線狀態改變：" + type))
    .errorListener(new ErrorListener() {
        public void errorOccurred(Connection conn, String subject, Exception e) {
            System.err.println("錯誤發生：" + e.getMessage());
        }
    })
    .reconnectWait(Duration.ofSeconds(2))
    .maxReconnects(5)
    .build();
Connection nc = Nats.connect(options);
```

### ✅ 測試效能

使用 JMH 或寫多執行緒模擬並發發送接收，測試吞吐量（TPS）與延遲（Latency）。

### ✅ NATS Streaming / JetStream 升級（持久化、ACK）

若要支持訊息持久化或保證送達可考慮升級至 JetStream：

* 需要特殊配置與額外指令參考官方文件：[https://docs.nats.io/jetstream](https://docs.nats.io/jetstream)

---

以上即為 Spring Boot 與 NATS 的整合教學，從安裝到自訂與效能最佳化，循序漸進掌握這套高效能訊息系統。
