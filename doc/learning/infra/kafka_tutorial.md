# Kafka 教學文件

## 目錄

- [Kafka 教學文件](#kafka-教學文件)
  - [目錄](#目錄)
  - [概述](#概述)
  - [初級教學](#初級教學)
    - [什麼是 Kafka](#什麼是-kafka)
      - [為什麼要使用 Kafka？](#為什麼要使用-kafka)
      - [可能遇到的問題](#可能遇到的問題)
      - [如何避免問題](#如何避免問題)
    - [Kafka 的基本概念](#kafka-的基本概念)
    - [安裝與設定](#安裝與設定)
    - [第一個 Kafka 程式](#第一個-kafka-程式)
  - [中級教學](#中級教學)
    - [生產者與消費者](#生產者與消費者)
      - [生產者進階設定](#生產者進階設定)
      - [消費者進階設定](#消費者進階設定)
    - [主題與分區](#主題與分區)
    - [消費者群組](#消費者群組)
    - [實作範例](#實作範例)
  - [高級教學](#高級教學)
    - [效能優化](#效能優化)
      - [硬體優化](#硬體優化)
      - [配置優化](#配置優化)
    - [監控與管理](#監控與管理)
      - [監控指標](#監控指標)
      - [管理工具](#管理工具)
    - [進階應用](#進階應用)
      - [事件驅動架構](#事件驅動架構)
      - [實作範例：訂單處理系統](#實作範例訂單處理系統)
  - [常用指令參考](#常用指令參考)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 Apache Kafka 的使用技巧。無論您是完全沒有 Kafka 經驗的初學者，還是已經了解基礎功能需要進行自定義的中級使用者，或是想要進行故障排除和效能優化的高級使用者，本文檔都能為您提供所需的知識和技能。

## 初級教學

### 什麼是 Kafka

想像一下，如果你有一個超級大的筆記本，可以記錄下所有發生的事情，而且多個人可以同時寫入和讀取這個筆記本，這就有點像 Kafka 的功能。Kafka 就像是一個超級厲害的訊息傳遞系統，可以讓不同的程式互相溝通。

#### 為什麼要使用 Kafka？

1. **快速傳遞訊息**：Kafka 可以非常快速地處理大量訊息
2. **可靠儲存**：訊息不會輕易丟失
3. **多人同時使用**：多個程式可以同時讀取和寫入
4. **容易擴展**：當需要處理更多訊息時，可以輕鬆增加容量

#### 可能遇到的問題

1. **訊息傳送失敗**：可能是網路問題或設定錯誤
2. **訊息重複**：同一個訊息可能被處理多次
3. **訊息順序混亂**：訊息可能不按順序到達

#### 如何避免問題

1. **檢查設定**：確保所有設定都正確
2. **使用確認機制**：確保訊息確實被接收
3. **設定重試機制**：當失敗時自動重試

### Kafka 的基本概念

讓我們用一個簡單的比喻來理解 Kafka 的基本概念：

```
@startuml
skinparam monochrome true
skinparam shadowing false

actor "生產者" as Producer
actor "消費者" as Consumer
database "Kafka" as Kafka {
  [主題1]
  [主題2]
}

Producer --> Kafka : 發送訊息
Kafka --> Consumer : 接收訊息

note right of Producer
  生產者就像是寫信的人
end note

note right of Consumer
  消費者就像是收信的人
end note

note right of Kafka
  Kafka 就像是郵局
end note
@enduml
```

1. **生產者（Producer）**：發送訊息的程式，就像寫信的人
2. **消費者（Consumer）**：接收訊息的程式，就像收信的人
3. **主題（Topic）**：訊息的類別，就像不同的信箱
4. **訊息（Message）**：要傳遞的內容，就像信件

### 安裝與設定

讓我們開始安裝 Kafka。以下是在 Windows 系統上安裝 Kafka 的步驟：

1. **安裝 Java**：
   - 下載並安裝 Java 8 或更高版本
   - 設定 JAVA_HOME 環境變數

2. **下載 Kafka**：
   - 前往 [Kafka 官方網站](https://kafka.apache.org/downloads)
   - 下載最新的穩定版本

3. **解壓縮檔案**：
   - 使用解壓縮工具解壓下載的檔案
   - 將解壓後的資料夾移動到您想要的位置

4. **啟動 Kafka**：
   - 開啟命令提示字元
   - 進入 Kafka 資料夾
   - 依序執行以下命令：
     ```
     .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
     .\bin\windows\kafka-server-start.bat .\config\server.properties
     ```

### 第一個 Kafka 程式

讓我們寫一個簡單的程式來發送和接收訊息：

```java
// 發送訊息的程式
import org.apache.kafka.clients.producer.*;

public class SimpleProducer {
    public static void main(String[] args) {
        // 設定生產者
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 創建生產者
        Producer<String, String> producer = new KafkaProducer<>(props);

        // 發送訊息
        producer.send(new ProducerRecord<>("test", "Hello, Kafka!"));

        // 關閉生產者
        producer.close();
    }
}

// 接收訊息的程式
import org.apache.kafka.clients.consumer.*;

public class SimpleConsumer {
    public static void main(String[] args) {
        // 設定消費者
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 創建消費者
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        // 訂閱主題
        consumer.subscribe(Arrays.asList("test"));

        // 接收訊息
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("收到訊息: " + record.value());
            }
        }
    }
}
```

## 中級教學

### 生產者與消費者

在中級教學中，我們將學習更多關於生產者和消費者的細節。

#### 生產者進階設定

```
@startuml
skinparam monochrome true
skinparam shadowing false

actor "生產者" as Producer
database "Kafka" as Kafka {
  [主題]
}

Producer --> Kafka : 批次發送訊息
Producer --> Kafka : 壓縮訊息
Producer --> Kafka : 重試機制

note right of Producer
  進階功能：
  1. 批次處理
  2. 壓縮
  3. 重試
end note
@enduml
```

1. **批次處理**：一次發送多個訊息，提高效率
2. **壓縮**：減少網路傳輸量
3. **重試機制**：當發送失敗時自動重試

```java
// 進階生產者設定
Properties props = new Properties();
props.put("batch.size", 16384);  // 批次大小
props.put("compression.type", "snappy");  // 壓縮類型
props.put("retries", 3);  // 重試次數
```

#### 消費者進階設定

```
@startuml
skinparam monochrome true
skinparam shadowing false

actor "消費者" as Consumer
database "Kafka" as Kafka {
  [主題]
}

Kafka --> Consumer : 自動提交偏移量
Kafka --> Consumer : 手動提交偏移量
Kafka --> Consumer : 重置偏移量

note right of Consumer
  進階功能：
  1. 偏移量管理
  2. 自動/手動提交
  3. 重置位置
end note
@enduml
```

1. **偏移量管理**：記錄讀取位置
2. **自動/手動提交**：控制何時確認訊息已處理
3. **重置位置**：從特定位置開始讀取

```java
// 進階消費者設定
Properties props = new Properties();
props.put("enable.auto.commit", "false");  // 禁用自動提交
props.put("auto.offset.reset", "earliest");  // 從最早開始讀取
```

### 主題與分區

主題可以分成多個分區，這樣可以並行處理訊息：

```
@startuml
skinparam monochrome true
skinparam shadowing false

database "主題" as Topic {
  [分區1]
  [分區2]
  [分區3]
}

actor "生產者1" as P1
actor "生產者2" as P2
actor "消費者1" as C1
actor "消費者2" as C2

P1 --> [分區1]
P2 --> [分區2]
[分區1] --> C1
[分區2] --> C2
[分區3] --> C1

note right of Topic
  分區可以：
  1. 提高並行處理能力
  2. 分散負載
  3. 增加可靠性
end note
@enduml
```

### 消費者群組

消費者群組允許多個消費者共同處理一個主題的訊息：

```
@startuml
skinparam monochrome true
skinparam shadowing false

database "主題" as Topic {
  [分區1]
  [分區2]
  [分區3]
}

package "消費者群組" {
  actor "消費者1" as C1
  actor "消費者2" as C2
  actor "消費者3" as C3
}

[分區1] --> C1
[分區2] --> C2
[分區3] --> C3

note right of Topic
  消費者群組：
  1. 共同處理訊息
  2. 自動負載平衡
  3. 高可用性
end note
@enduml
```

### 實作範例

讓我們創建一個簡單的聊天系統：

```java
// 聊天訊息類別
public class ChatMessage {
    private String sender;
    private String content;
    private Date timestamp;

    // 構造函數、getter 和 setter
}

// 聊天室生產者
public class ChatProducer {
    private Producer<String, ChatMessage> producer;

    public ChatProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    public void sendMessage(String room, String sender, String content) {
        ChatMessage message = new ChatMessage(sender, content, new Date());
        producer.send(new ProducerRecord<>(room, message));
    }
}

// 聊天室消費者
public class ChatConsumer {
    private Consumer<String, ChatMessage> consumer;

    public ChatConsumer(String groupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
    }

    public void joinRoom(String room) {
        consumer.subscribe(Arrays.asList(room));
    }

    public void startListening() {
        while (true) {
            ConsumerRecords<String, ChatMessage> records = consumer.poll(100);
            for (ConsumerRecord<String, ChatMessage> record : records) {
                ChatMessage message = record.value();
                System.out.println(message.getSender() + ": " + message.getContent());
            }
        }
    }
}
```

## 高級教學

### 效能優化

在高級教學中，我們將學習如何優化 Kafka 的效能。

#### 硬體優化

```
@startuml
skinparam monochrome true
skinparam shadowing false

node "Kafka 叢集" {
  database "代理1" as B1 {
    [SSD]
    [RAM]
  }
  database "代理2" as B2 {
    [SSD]
    [RAM]
  }
  database "代理3" as B3 {
    [SSD]
    [RAM]
  }
}

note right of B1
  硬體優化：
  1. 使用 SSD
  2. 增加記憶體
  3. 高速網路
end note
@enduml
```

#### 配置優化

```java
// 生產者配置優化
props.put("batch.size", 16384);  // 增加批次大小
props.put("linger.ms", 1);  // 減少等待時間
props.put("compression.type", "snappy");  // 使用壓縮

// 消費者配置優化
props.put("fetch.min.bytes", 1);  // 最小獲取大小
props.put("fetch.max.wait.ms", 500);  // 最大等待時間
props.put("max.partition.fetch.bytes", 1048576);  // 分區最大獲取大小
```

### 監控與管理

#### 監控指標

```
@startuml
skinparam monochrome true
skinparam shadowing false

node "監控系統" {
  database "Prometheus" as P
  database "Grafana" as G
}

database "Kafka" as K {
  [代理指標]
  [主題指標]
  [消費者指標]
}

K --> P : 收集指標
P --> G : 視覺化

note right of P
  監控指標：
  1. 吞吐量
  2. 延遲
  3. 錯誤率
end note
@enduml
```

#### 管理工具

1. **Kafka Manager**：管理 Kafka 叢集
2. **Kafka Monitor**：監控 Kafka 效能
3. **Kafka Tool**：開發和調試工具

### 進階應用

#### 事件驅動架構

```
@startuml
skinparam monochrome true
skinparam shadowing false

node "訂單服務" as Order
node "庫存服務" as Inventory
node "支付服務" as Payment
node "通知服務" as Notification

database "Kafka" as K {
  [訂單事件]
  [庫存事件]
  [支付事件]
}

Order --> K : 發布訂單事件
K --> Inventory : 訂閱庫存事件
K --> Payment : 訂閱支付事件
K --> Notification : 訂閱通知事件

note right of K
  事件驅動：
  1. 鬆散耦合
  2. 可擴展性
  3. 可靠性
end note
@enduml
```

#### 實作範例：訂單處理系統

```java
// 訂單事件
public class OrderEvent {
    private String orderId;
    private String status;
    private List<OrderItem> items;
    private Date timestamp;
}

// 訂單服務
@Service
public class OrderService {
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void createOrder(Order order) {
        OrderEvent event = new OrderEvent();
        event.setOrderId(order.getId());
        event.setStatus("CREATED");
        event.setItems(order.getItems());
        event.setTimestamp(new Date());
        
        kafkaTemplate.send("orders", event);
    }
}

// 庫存服務
@Service
public class InventoryService {
    @KafkaListener(topics = "orders", groupId = "inventory-service")
    public void handleOrder(OrderEvent event) {
        if ("CREATED".equals(event.getStatus())) {
            updateInventory(event.getItems());
        }
    }
}

// 支付服務
@Service
public class PaymentService {
    @KafkaListener(topics = "orders", groupId = "payment-service")
    public void handleOrder(OrderEvent event) {
        if ("CREATED".equals(event.getStatus())) {
            processPayment(event.getOrderId());
        }
    }
}
```

## 常用指令參考

以下是一些常用的 Kafka 指令：

1. **創建主題**：
   ```
   .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic my-topic --partitions 3 --replication-factor 1
   ```

2. **列出主題**：
   ```
   .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list
   ```

3. **發送訊息**：
   ```
   .\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic my-topic
   ```

4. **接收訊息**：
   ```
   .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic my-topic --from-beginning
   ```

5. **查看消費者群組**：
   ```
   .\bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list
   ```
