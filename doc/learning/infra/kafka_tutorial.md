# Kafka 教學文件

## 目錄

1. [概述](#概述)
2. [初級教學](#初級教學)
    - [什麼是 Kafka](#什麼是-kafka)
    - [Kafka 的基本概念](#kafka-的基本概念)
    - [Kafka 的架構](#kafka-的架構)
    - [安裝 Kafka](#安裝-kafka)
    - [建立第一個主題](#建立第一個主題)
    - [發送與接收訊息](#發送與接收訊息)
    - [初級範例](#初級範例)
3. [中級教學](#中級教學)
    - [Kafka 生產者進階設定](#kafka-生產者進階設定)
    - [Kafka 消費者進階設定](#kafka-消費者進階設定)
    - [消費者群組](#消費者群組)
    - [分區策略](#分區策略)
    - [序列化與反序列化](#序列化與反序列化)
    - [整合 Kafka 與應用程式](#整合-kafka-與應用程式)
    - [中級範例](#中級範例)
4. [高級教學](#高級教學)
    - [Kafka 叢集管理](#kafka-叢集管理)
    - [效能優化](#效能優化)
    - [監控與指標](#監控與指標)
    - [故障排除](#故障排除)
    - [安全性設定](#安全性設定)
    - [資料保留策略](#資料保留策略)
    - [高可用性設計](#高可用性設計)
    - [高級範例](#高級範例)
5. [常用指令參考](#常用指令參考)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 Apache Kafka 的使用技巧。無論您是完全沒有 Kafka 經驗的初學者，還是已經了解基礎功能需要進行自定義的中級使用者，或是想要進行故障排除和效能優化的高級使用者，本文檔都能為您提供所需的知識和技能。

Apache Kafka 是一個分散式的串流處理平台，被廣泛用於建立即時資料管道和串流應用程式。它具有高吞吐量、可靠性和可擴展性，使其成為處理大量資料流的理想選擇。

## 初級教學

本節適合完全沒有 Kafka 經驗的初學者。我們將從最基本的概念開始，逐步建立您對 Kafka 的理解。

### 什麼是 Kafka

Apache Kafka 是一個分散式的串流處理平台，可以用來收集、處理和儲存大量的資料流。它最初由 LinkedIn 開發，後來捐贈給了 Apache 基金會，現在已經成為處理串流資料的標準工具之一。

想像一下，如果您有一個超級大的筆記本，可以記錄下所有發生的事情，而且多個人可以同時寫入和讀取這個筆記本，這就有點像 Kafka 的功能。Kafka 可以接收來自多個來源的資料（例如網站點擊、感測器讀數、日誌檔案等），並將這些資料提供給多個接收者使用。

### Kafka 的基本概念

在開始使用 Kafka 之前，我們需要了解一些基本概念：

1. **訊息（Message）**：Kafka 中的基本資料單位，就像是一條記錄或一個事件。

2. **主題（Topic）**：訊息的分類或分組。您可以將主題想像成一個資料夾，用來組織相關的訊息。例如，您可能有一個「銷售」主題來存儲所有銷售相關的訊息。

3. **分區（Partition）**：每個主題可以分成多個分區，這些分區分佈在不同的伺服器上，使 Kafka 能夠處理大量資料。

4. **生產者（Producer）**：發送訊息到 Kafka 主題的應用程式。

5. **消費者（Consumer）**：從 Kafka 主題讀取訊息的應用程式。

6. **代理（Broker）**：Kafka 伺服器，負責接收、存儲和傳遞訊息。

7. **叢集（Cluster）**：多個 Kafka 代理一起工作形成的群組。

8. **Zookeeper**：用於管理和協調 Kafka 代理的服務。

### Kafka 的架構

Kafka 的架構相對簡單，但非常強大。以下是 Kafka 的基本架構：

```
+-------------+     +---------------+     +-------------+
|  生產者      | --> |  Kafka 叢集    | --> |  消費者      |
+-------------+     +---------------+     +-------------+
                    |               |
                    |  +--------+   |
                    |  | 代理 1  |   |
                    |  +--------+   |
                    |  | 代理 2  |   |
                    |  +--------+   |
                    |  | 代理 3  |   |
                    |  +--------+   |
                    |               |
                    +-------+-------+
                            |
                    +-------v-------+
                    |   Zookeeper   |
                    +---------------+
```

在這個架構中：

- 生產者將訊息發送到 Kafka 叢集
- Kafka 叢集由多個代理組成，每個代理負責存儲和管理部分資料
- 消費者從 Kafka 叢集讀取訊息
- Zookeeper 負責管理和協調 Kafka 叢集

### 安裝 Kafka

讓我們開始安裝 Kafka。以下是在 Windows 系統上安裝 Kafka 的步驟：

1. **安裝 Java**：Kafka 需要 Java 環境，請確保您已安裝 Java 8 或更高版本。

2. **下載 Kafka**：
    - 前往 [Kafka 官方網站](https://kafka.apache.org/downloads)
    - 下載最新的穩定版本（例如 kafka_2.13-3.4.0.tgz）

3. **解壓縮檔案**：
    - 使用解壓縮工具（如 7-Zip）解壓下載的檔案
    - 將解壓後的資料夾移動到您想要的位置（例如 C:\kafka）

4. **修改配置檔案**：
    - 進入 Kafka 資料夾中的 config 目錄
    - 編輯 `server.properties` 檔案
    - 找到並修改 `log.dirs` 設定，指定日誌存儲位置：
      ```
      log.dirs=C:/kafka/kafka-logs
      ```

5. **啟動 Zookeeper**：
    - 打開命令提示字元
    - 導航到 Kafka 資料夾
    - 執行以下命令：
      ```
      .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
      ```

6. **啟動 Kafka 伺服器**：
    - 打開另一個命令提示字元
    - 導航到 Kafka 資料夾
    - 執行以下命令：
      ```
      .\bin\windows\kafka-server-start.bat .\config\server.properties
      ```

恭喜！您現在已經成功啟動了 Kafka 伺服器。

### 建立第一個主題

現在我們已經啟動了 Kafka，讓我們創建第一個主題：

1. 打開一個新的命令提示字元
2. 導航到 Kafka 資料夾
3. 執行以下命令創建一個名為 "test" 的主題，有 1 個分區和 1 個複製因子：

```
.\bin\windows\kafka-topics.bat --create --topic test --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

4. 確認主題已創建：

```
.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
```

您應該會看到 "test" 主題列在輸出中。

### 發送與接收訊息

現在我們有了一個主題，讓我們嘗試發送和接收一些訊息：

1. **發送訊息**：
    - 打開一個新的命令提示字元
    - 導航到 Kafka 資料夾
    - 執行以下命令啟動生產者控制台：
      ```
      .\bin\windows\kafka-console-producer.bat --topic test --bootstrap-server localhost:9092
      ```
    - 輸入一些訊息，每行一條，按 Enter 發送：
      ```
      這是我的第一條 Kafka 訊息
      這是第二條訊息
      Hello, Kafka!
      ```
    - 按 Ctrl+C 退出生產者控制台

2. **接收訊息**：
    - 打開另一個命令提示字元
    - 導航到 Kafka 資料夾
    - 執行以下命令啟動消費者控制台：
      ```
      .\bin\windows\kafka-console-consumer.bat --topic test --from-beginning --bootstrap-server localhost:9092
      ```
    - 您應該會看到之前發送的所有訊息：
      ```
      這是我的第一條 Kafka 訊息
      這是第二條訊息
      Hello, Kafka!
      ```
    - 如果您在生產者控制台發送更多訊息，它們會立即出現在消費者控制台
    - 按 Ctrl+C 退出消費者控制台

### 初級範例

讓我們創建一個簡單的 Java 應用程式，展示如何使用 Kafka 生產者和消費者 API：

1. **創建一個 Maven 專案**，並添加 Kafka 客戶端依賴：

```xml

<dependency>
  <groupId>org.apache.kafka</groupId>
  <artifactId>kafka-clients</artifactId>
  <version>3.4.0</version>
</dependency>
```

2. **創建一個生產者**：

```java
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class SimpleProducer {
    public static void main(String[] args) {
        // 設定生產者屬性
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 創建生產者
        Producer<String, String> producer = new KafkaProducer<>(props);

        // 創建一條訊息
        ProducerRecord<String, String> record = new ProducerRecord<>("test", "key1", "這是一條來自 Java 的訊息");

        // 發送訊息
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    System.out.println("訊息發送成功！");
                    System.out.println("主題: " + metadata.topic());
                    System.out.println("分區: " + metadata.partition());
                    System.out.println("偏移量: " + metadata.offset());
                } else {
                    System.err.println("發送訊息時出錯: " + exception.getMessage());
                }
            }
        });

        // 關閉生產者
        producer.close();
    }
}
```

3. **創建一個消費者**：

```java
import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        // 設定消費者屬性
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        // 創建消費者
        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        // 訂閱主題
        consumer.subscribe(Arrays.asList("test"));

        // 持續輪詢新訊息
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("收到訊息:");
                    System.out.println("主題 = " + record.topic());
                    System.out.println("分區 = " + record.partition());
                    System.out.println("偏移量 = " + record.offset());
                    System.out.println("鍵 = " + record.key());
                    System.out.println("值 = " + record.value());
                }
            }
        } finally {
            // 關閉消費者
            consumer.close();
        }
    }
}
```

4. **執行範例**：
    - 首先確保 Kafka 和 Zookeeper 正在運行
    - 編譯並運行 SimpleProducer 類，它會發送一條訊息
    - 編譯並運行 SimpleConsumer 類，它會接收並顯示訊息

這個簡單的範例展示了 Kafka 的基本功能：生產者發送訊息，消費者接收訊息。

## 中級教學

本節適合已經了解 Kafka 基礎知識，需要進一步自定義和優化 Kafka 使用的中級使用者。

### Kafka 生產者進階設定

生產者有許多可以調整的設定，以滿足不同的需求：

1. **批次處理**：Kafka 生產者會將訊息批次處理後再發送，以提高效率。
   ```java
   // 設定批次大小（以位元組為單位）
   props.put("batch.size", 16384);

   // 設定等待時間（毫秒）
   props.put("linger.ms", 1);
   ```

2. **壓縮**：可以壓縮訊息以節省網路頻寬。
   ```java
   // 啟用壓縮（none, gzip, snappy, lz4, zstd）
   props.put("compression.type", "snappy");
   ```

3. **重試機制**：當發送失敗時，可以自動重試。
   ```java
   // 設定重試次數
   props.put("retries", 3);

   // 設定重試間隔（毫秒）
   props.put("retry.backoff.ms", 100);
   ```

4. **緩衝區大小**：控制生產者可以緩衝的訊息數量。
   ```java
   // 設定緩衝區大小（以位元組為單位）
   props.put("buffer.memory", 33554432);
   ```

5. **確認機制**：控制生產者需要多少代理確認才認為訊息發送成功。
   ```java
   // 0: 不等待確認
   // 1: 只等待領導者確認
   // all: 等待所有同步複製品確認
   props.put("acks", "all");
   ```

### Kafka 消費者進階設定

消費者也有許多可以調整的設定：

1. **自動提交**：控制偏移量的自動提交。
   ```java
   // 啟用自動提交
   props.put("enable.auto.commit", "true");

   // 設定自動提交間隔（毫秒）
   props.put("auto.commit.interval.ms", "1000");
   ```

2. **手動提交**：更精確地控制偏移量的提交。
   ```java
   // 禁用自動提交
   props.put("enable.auto.commit", "false");

   // 在處理完訊息後手動提交
   consumer.commitSync();
   ```

3. **偏移量重置**：當沒有初始偏移量或偏移量無效時的行為。
   ```java
   // earliest: 從最早的偏移量開始
   // latest: 從最新的偏移量開始
   // none: 如果沒有找到消費者群組的先前偏移量，則拋出異常
   props.put("auto.offset.reset", "earliest");
   ```

4. **最大輪詢記錄數**：每次輪詢返回的最大記錄數。
   ```java
   props.put("max.poll.records", 500);
   ```

5. **心跳間隔**：消費者向協調器發送心跳的頻率。
   ```java
   props.put("heartbeat.interval.ms", 3000);
   ```

### 消費者群組

消費者群組是 Kafka 的一個重要概念，它允許多個消費者共同處理一個主題的訊息，每個消費者處理不同的分區：

1. **創建消費者群組**：
   ```java
   // 設定消費者群組 ID
   props.put("group.id", "my-group");
   ```

2. **消費者群組的工作原理**：
    - 同一群組中的消費者共同消費一個主題
    - 每個分區只能被群組中的一個消費者消費
    - 如果消費者數量多於分區數量，一些消費者將處於閒置狀態
    - 如果消費者數量少於分區數量，一些消費者將處理多個分區

3. **再平衡**：當消費者加入或離開群組時，Kafka 會自動重新分配分區。

4. **範例**：創建一個有兩個消費者的消費者群組：

```java
// 消費者 1
Properties props1 = new Properties();
props1.

put("bootstrap.servers","localhost:9092");
props1.

put("group.id","my-group");
props1.

put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
props1.

put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

Consumer<String, String> consumer1 = new KafkaConsumer<>(props1);
consumer1.

subscribe(Arrays.asList("test"));

// 消費者 2
Properties props2 = new Properties();
props2.

put("bootstrap.servers","localhost:9092");
props2.

put("group.id","my-group");
props2.

put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
props2.

put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

Consumer<String, String> consumer2 = new KafkaConsumer<>(props2);
consumer2.

subscribe(Arrays.asList("test"));
```

### 分區策略

分區策略決定了訊息如何分配到主題的不同分區：

1. **默認分區策略**：
    - 如果指定了分區，使用指定的分區
    - 如果沒有指定分區但指定了鍵，使用鍵的雜湊值來選擇分區
    - 如果既沒有指定分區也沒有指定鍵，使用輪詢策略

2. **自定義分區策略**：
   ```java
   public class CustomPartitioner implements Partitioner {
       @Override
       public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
           // 獲取主題的分區數量
           List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
           int numPartitions = partitions.size();

           // 根據鍵選擇分區
           if (key == null) {
               return 0; // 如果沒有鍵，總是使用第一個分區
           }

           // 根據鍵的值選擇不同的分區
           String keyStr = (String) key;
           if (keyStr.startsWith("A")) {
               return 0;
           } else if (keyStr.startsWith("B")) {
               return 1 % numPartitions;
           } else {
               return 2 % numPartitions;
           }
       }

       @Override
       public void close() {}

       @Override
       public void configure(Map<String, ?> configs) {}
   }
   ```

3. **使用自定義分區策略**：
   ```java
   props.put("partitioner.class", "com.example.CustomPartitioner");
   ```

### 序列化與反序列化

Kafka 需要將訊息序列化為位元組數組才能傳輸，然後在接收端反序列化：

1. **內建序列化器**：
    - StringSerializer/StringDeserializer：用於字串
    - IntegerSerializer/IntegerDeserializer：用於整數
    - ByteArraySerializer/ByteArrayDeserializer：用於位元組數組

2. **自定義序列化器**：
   ```java
   public class PersonSerializer implements Serializer<Person> {
       @Override
       public byte[] serialize(String topic, Person data) {
           if (data == null) {
               return null;
           }
           try {
               ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
               ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
               objectStream.writeObject(data);
               objectStream.close();
               return byteStream.toByteArray();
           } catch (Exception e) {
               throw new SerializationException("Error serializing Person", e);
           }
       }

       @Override
       public void close() {}

       @Override
       public void configure(Map<String, ?> configs, boolean isKey) {}
   }
   ```

3. **自定義反序列化器**：
   ```java
   public class PersonDeserializer implements Deserializer<Person> {
       @Override
       public Person deserialize(String topic, byte[] data) {
           if (data == null) {
               return null;
           }
           try {
               ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
               ObjectInputStream objectStream = new ObjectInputStream(byteStream);
               return (Person) objectStream.readObject();
           } catch (Exception e) {
               throw new SerializationException("Error deserializing Person", e);
           }
       }

       @Override
       public void close() {}

       @Override
       public void configure(Map<String, ?> configs, boolean isKey) {}
   }
   ```

4. **使用自定義序列化器和反序列化器**：
   ```java
   // 生產者
   props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
   props.put("value.serializer", "com.example.PersonSerializer");

   // 消費者
   props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
   props.put("value.deserializer", "com.example.PersonDeserializer");
   ```

### 整合 Kafka 與應用程式

讓我們看看如何將 Kafka 整合到實際應用程式中：

1. **Spring Boot 整合**：

```java
// 添加依賴
// implementation 'org.springframework.kafka:spring-kafka'

// 配置生產者
@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

// 配置消費者
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

// 使用生產者
@Service
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}

// 使用消費者
@Service
public class MessageConsumer {
    @KafkaListener(topics = "test", groupId = "my-group")
    public void listen(String message) {
        System.out.println("收到訊息: " + message);
    }
}
```

2. **微服務架構中的 Kafka**：

```
+----------------+     +----------------+     +----------------+
|  用戶服務       |     |  訂單服務       |     |  庫存服務       |
+----------------+     +----------------+     +----------------+
        |                     |                     |
        v                     v                     v
+-------------------------------------------------------+
|                      Kafka 叢集                       |
+-------------------------------------------------------+
```

在這個架構中：

- 用戶服務可以發布用戶相關事件（如用戶註冊、用戶更新）
- 訂單服務可以發布訂單相關事件（如訂單創建、訂單狀態更新）
- 庫存服務可以發布庫存相關事件（如庫存變更）
- 每個服務可以訂閱它關心的事件

### 中級範例

讓我們創建一個更複雜的範例，模擬一個簡單的電子商務系統：

1. **定義事件類**：

```java
public class OrderEvent implements Serializable {
    private String orderId;
    private String userId;
    private String status;
    private List<OrderItem> items;
    private Date timestamp;

    // 構造函數、getter 和 setter
}

public class OrderItem implements Serializable {
    private String productId;
    private int quantity;
    private double price;

    // 構造函數、getter 和 setter
}
```

2. **創建訂單服務**：

```java

@Service
public class OrderService {
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void createOrder(String userId, List<OrderItem> items) {
        // 創建訂單
        String orderId = generateOrderId();

        // 創建訂單事件
        OrderEvent event = new OrderEvent();
        event.setOrderId(orderId);
        event.setUserId(userId);
        event.setStatus("CREATED");
        event.setItems(items);
        event.setTimestamp(new Date());

        // 發布訂單創建事件
        kafkaTemplate.send("orders", orderId, event);
    }

    public void updateOrderStatus(String orderId, String status) {
        // 更新訂單狀態

        // 創建訂單狀態更新事件
        OrderEvent event = new OrderEvent();
        event.setOrderId(orderId);
        event.setStatus(status);
        event.setTimestamp(new Date());

        // 發布訂單狀態更新事件
        kafkaTemplate.send("orders", orderId, event);
    }

    private String generateOrderId() {
        // 生成唯一訂單 ID
        return "ORD-" + System.currentTimeMillis();
    }
}
```

3. **創建庫存服務**：

```java

@Service
public class InventoryService {
    @KafkaListener(topics = "orders", groupId = "inventory-service")
    public void handleOrderEvent(OrderEvent event) {
        if ("CREATED".equals(event.getStatus())) {
            // 處理新訂單
            updateInventory(event.getItems());
        } else if ("CANCELLED".equals(event.getStatus())) {
            // 處理取消的訂單
            restoreInventory(event.getItems());
        }
    }

    private void updateInventory(List<OrderItem> items) {
        for (OrderItem item : items) {
            System.out.println("減少產品 " + item.getProductId() + " 的庫存，數量: " + item.getQuantity());
            // 實際的庫存更新邏輯
        }
    }

    private void restoreInventory(List<OrderItem> items) {
        for (OrderItem item : items) {
            System.out.println("恢復產品 " + item.getProductId() + " 的庫存，數量: " + item.getQuantity());
            // 實際的庫存恢復邏輯
        }
    }
}
```

4. **創建通知服務**：

```java

@Service
public class NotificationService {
    @KafkaListener(topics = "orders", groupId = "notification-service")
    public void handleOrderEvent(OrderEvent event) {
        switch (event.getStatus()) {
            case "CREATED":
                sendOrderConfirmation(event);
                break;
            case "SHIPPED":
                sendShippingNotification(event);
                break;
            case "DELIVERED":
                sendDeliveryNotification(event);
                break;
            case "CANCELLED":
                sendCancellationNotification(event);
                break;
        }
    }

    private void sendOrderConfirmation(OrderEvent event) {
        System.out.println("發送訂單確認通知給用戶 " + event.getUserId() + " 訂單 ID: " + event.getOrderId());
        // 實際的通知邏輯
    }

    private void sendShippingNotification(OrderEvent event) {
        System.out.println("發送出貨通知給用戶 " + event.getUserId() + " 訂單 ID: " + event.getOrderId());
        // 實際的通知邏輯
    }

    private void sendDeliveryNotification(OrderEvent event) {
        System.out.println("發送送達通知給用戶 " + event.getUserId() + " 訂單 ID: " + event.getOrderId());
        // 實際的通知邏輯
    }

    private void sendCancellationNotification(OrderEvent event) {
        System.out.println("發送取消通知給用戶 " + event.getUserId() + " 訂單 ID: " + event.getOrderId());
        // 實際的通知邏輯
    }
}
```

這個範例展示了如何使用 Kafka 在微服務架構中實現事件驅動的通信。訂單服務發布訂單事件，庫存服務和通知服務訂閱這些事件並做出相應的處理。

## 高級教學

本節適合已經熟悉 Kafka 基本操作，需要進行故障排除、效能優化和高級配置的高級使用者。

### Kafka 叢集管理

管理 Kafka 叢集是確保其穩定運行的關鍵：

1. **新增代理**：
    - 準備新伺服器
    - 安裝 Kafka
    - 配置 `server.properties`，設定唯一的 `broker.id`
    - 啟動新代理
    - 等待新代理加入叢集

2. **移除代理**：
    - 確定要移除的代理 ID
    - 將該代理的分區遷移到其他代理：
      ```
      .\bin\windows\kafka-reassign-partitions.bat --bootstrap-server localhost:9092 --generate --topics-to-move-json-file topics.json --broker-list 1,2
      ```
    - 執行遷移計劃
    - 關閉代理

3. **擴展主題分區**：
    - 增加主題的分區數量：
      ```
      .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --alter --topic my-topic --partitions 4
      ```

4. **監控叢集健康狀況**：
    - 使用 Kafka 管理工具（如 Kafka Manager、CMAK）
    - 監控關鍵指標（如磁碟使用率、網路流量、請求率）

### 效能優化

優化 Kafka 效能可以從多個方面入手：

1. **硬體優化**：
    - 使用 SSD 存儲
    - 增加記憶體
    - 使用高速網路
    - 分離 Kafka 和 Zookeeper 的磁碟

2. **代理配置優化**：
    - 調整 `num.io.threads` 和 `num.network.threads`
    - 優化 `socket.send.buffer.bytes` 和 `socket.receive.buffer.bytes`
    - 調整 `log.flush.interval.messages` 和 `log.flush.interval.ms`

3. **主題配置優化**：
    - 選擇適當的分區數量
    - 設定合適的複製因子
    - 調整保留策略

4. **生產者優化**：
    - 增加批次大小
    - 使用壓縮
    - 調整 `linger.ms`
    - 使用非同步發送

5. **消費者優化**：
    - 增加 `fetch.min.bytes`
    - 調整 `fetch.max.wait.ms`
    - 優化消費者數量與分區數量的比例

### 監控與指標

有效的監控是維護 Kafka 叢集健康的關鍵：

1. **JMX 指標**：Kafka 暴露了許多 JMX 指標，可以使用 JConsole 或 JMX 監控工具查看。

2. **重要指標**：
    - **代理指標**：BytesInPerSec、BytesOutPerSec、RequestsPerSec
    - **主題指標**：MessagesInPerSec、BytesInPerSec、BytesOutPerSec
    - **生產者指標**：record-send-rate、record-error-rate
    - **消費者指標**：records-consumed-rate、fetch-rate

3. **監控工具**：
    - Prometheus + Grafana
    - Datadog
    - New Relic
    - Kafka Manager

4. **設定警報**：
    - 磁碟使用率超過 85%
    - 消費者延遲超過閾值
    - 代理離線
    - 複製延遲

### 故障排除

當 Kafka 出現問題時，以下是一些常見的故障排除步驟：

1. **代理無法啟動**：
    - 檢查日誌檔案
    - 確認 Zookeeper 是否正常運行
    - 檢查端口是否被佔用
    - 確認配置檔案是否正確

2. **生產者無法發送訊息**：
    - 檢查連接配置
    - 確認主題是否存在
    - 檢查授權設定
    - 查看生產者日誌

3. **消費者無法接收訊息**：
    - 檢查消費者群組 ID
    - 確認主題訂閱
    - 檢查偏移量設定
    - 查看消費者日誌

4. **分區不平衡**：
    - 使用 Kafka 工具檢查分區分配
    - 手動重新分配分區
    - 調整分區策略

5. **效能問題**：
    - 檢查磁碟 I/O
    - 監控網路流量
    - 檢查 JVM 記憶體使用
    - 調整配置參數

### 安全性設定

保護 Kafka 叢集的安全性非常重要：

1. **啟用 SSL/TLS**：
    - 創建密鑰庫和信任庫
    - 配置 `server.properties`：
      ```
      listeners=SSL://localhost:9093
      ssl.keystore.location=/path/to/kafka.server.keystore.jks
      ssl.keystore.password=keystore-password
      ssl.key.password=key-password
      ssl.truststore.location=/path/to/kafka.server.truststore.jks
      ssl.truststore.password=truststore-password
      ```
    - 配置客戶端使用 SSL

2. **啟用 SASL 認證**：
    - 配置 `server.properties`：
      ```
      listeners=SASL_SSL://localhost:9093
      security.inter.broker.protocol=SASL_SSL
      sasl.mechanism.inter.broker.protocol=PLAIN
      sasl.enabled.mechanisms=PLAIN
      ```
    - 創建 JAAS 配置檔案
    - 配置客戶端使用 SASL

3. **設定 ACL**：
    - 啟用授權：
      ```
      authorizer.class.name=kafka.security.auth.SimpleAclAuthorizer
      ```
    - 創建 ACL 規則：
      ```
      .\bin\windows\kafka-acls.bat --bootstrap-server localhost:9092 --add --allow-principal User:user1 --operation Read --topic test
      ```

### 資料保留策略

Kafka 提供了多種方式來管理資料保留：

1. **基於時間的保留**：
    - 設定主題保留時間：
      ```
      .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --alter --topic my-topic --config retention.ms=86400000
      ```

2. **基於大小的保留**：
    - 設定主題保留大小：
      ```
      .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --alter --topic my-topic --config retention.bytes=1073741824
      ```

3. **日誌壓縮**：
    - 啟用日誌壓縮：
      ```
      .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic my-compacted-topic --config cleanup.policy=compact
      ```

4. **刪除與壓縮結合**：
    - 同時使用刪除和壓縮：
      ```
      .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic my-topic --config cleanup.policy=delete,compact
      ```

### 高可用性設計

設計高可用性的 Kafka 叢集：

1. **多代理部署**：
    - 至少部署 3 個代理
    - 將代理分佈在不同的機器上
    - 使用不同的機架或可用區

2. **複製因子**：
    - 設定適當的複製因子（通常為 3）：
      ```
      .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic my-topic --partitions 3 --replication-factor 3
      ```

3. **最小同步複製品**：
    - 設定最小同步複製品數量：
      ```
      .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --alter --topic my-topic --config min.insync.replicas=2
      ```

4. **多數據中心部署**：
    - 使用 MirrorMaker 在數據中心之間複製資料
    - 配置 MirrorMaker：
      ```
      .\bin\windows\kafka-mirror-maker.bat --consumer.config consumer.properties --producer.config producer.properties --whitelist ".*"
      ```

### 高級範例

讓我們創建一個更複雜的範例，展示 Kafka Streams 的使用：

```java
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

public class WordCountExample {
    public static void main(String[] args) {
        // 配置 Kafka Streams
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // 創建 StreamsBuilder
        StreamsBuilder builder = new StreamsBuilder();

        // 從輸入主題讀取
        KStream<String, String> textLines = builder.stream("text-input");

        // 處理文本
        KTable<String, Long> wordCounts = textLines
                // 分割每行為單詞
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                // 重新分組，將單詞作為鍵
                .groupBy((key, word) -> word)
                // 計數
                .count();

        // 將結果寫入輸出主題
        wordCounts.toStream().to("word-count-output", Produced.with(Serdes.String(), Serdes.Long()));

        // 創建並啟動 Kafka Streams
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // 添加關閉鉤子
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
```

這個範例展示了如何使用 Kafka Streams 處理文本流，計算單詞出現的頻率。

## 常用指令參考

以下是一些常用的 Kafka 指令：

1. **創建主題**：
   ```
   .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic my-topic --partitions 3 --replication-factor 1
   ```

2. **列出所有主題**：
   ```
   .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list
   ```

3. **查看主題詳情**：
   ```
   .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic my-topic
   ```

4. **刪除主題**：
   ```
   .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --delete --topic my-topic
   ```

5. **發送訊息**：
   ```
   .\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic my-topic
   ```

6. **接收訊息**：
   ```
   .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic my-topic --from-beginning
   ```

7. **查看消費者群組**：
   ```
   .\bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list
   ```

8. **查看消費者群組詳情**：
   ```
   .\bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group my-group
   ```

9. **重設消費者偏移量**：
   ```
   .\bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group my-group --topic my-topic --reset-offsets --to-earliest --execute
   ```

10. **查看代理配置**：
    ```
    .\bin\windows\kafka-configs.bat --bootstrap-server localhost:9092 --entity-type brokers --entity-name 0 --describe
    ```

11. **修改主題配置**：
    ```
    .\bin\windows\kafka-configs.bat --bootstrap-server localhost:9092 --entity-type topics --entity-name my-topic --alter --add-config retention.ms=86400000
    ```

12. **執行效能測試**：
    ```
    .\bin\windows\kafka-producer-perf-test.bat --topic my-topic --num-records 1000000 --record-size 1000 --throughput 10000 --producer-props bootstrap.servers=localhost:9092
    ```

這些指令可以幫助您管理和監控 Kafka 叢集，進行故障排除和效能優化。
