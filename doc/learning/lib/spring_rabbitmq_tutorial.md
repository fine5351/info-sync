# Spring RabbitMQ 教學

## 初級（Beginner）層級

### 1. 概念說明
Spring RabbitMQ 就像是一個班級的傳話筒系統，可以幫助同學們互相傳遞訊息。初級學習者需要了解：
- 什麼是訊息佇列
- 為什麼需要 RabbitMQ
- 基本的訊息發送和接收

### 2. PlantUML 圖解
```plantuml
@startuml
class Message {
    - content: String
    - timestamp: Date
    + getContent()
    + getTimestamp()
}

class Producer {
    - rabbitTemplate: RabbitTemplate
    + sendMessage(String)
}

class Consumer {
    - rabbitListener: RabbitListener
    + receiveMessage()
}

Message --> Producer
Message --> Consumer
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：基本專案設定
```xml
<!-- pom.xml -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
        <version>3.3.10</version>
    </dependency>
</dependencies>
```

#### 步驟 2：基本配置
```yaml
# application.yml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

#### 步驟 3：簡單範例
```java
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.*;
import org.springframework.stereotype.Service;

@Service
public class ClassMessageService {
    private final RabbitTemplate rabbitTemplate;
    
    public ClassMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("class.notice", message);
    }
    
    @RabbitListener(queues = "class.notice")
    public void receiveMessage(String message) {
        System.out.println("收到班級通知: " + message);
    }
}
```

## 中級（Intermediate）層級

### 1. 概念說明
中級學習者需要理解：
- 交換機和佇列
- 訊息路由
- 訊息確認
- 錯誤處理

### 2. PlantUML 圖解
```plantuml
@startuml
class Exchange {
    - name: String
    - type: ExchangeType
    + route()
    + bind()
}

class Queue {
    - name: String
    - messages: List<Message>
    + enqueue()
    + dequeue()
}

class ErrorHandler {
    - strategy: ErrorStrategy
    + handleError()
    + recover()
}

Exchange --> Queue
Queue --> ErrorHandler
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：交換機和佇列配置
```java
import org.springframework.context.annotation.*;
import org.springframework.amqp.core.*;

@Configuration
public class RabbitConfig {
    
    @Bean
    public DirectExchange classExchange() {
        return new DirectExchange("class.exchange");
    }
    
    @Bean
    public Queue noticeQueue() {
        return new Queue("class.notice");
    }
    
    @Bean
    public Binding binding(Queue noticeQueue, DirectExchange classExchange) {
        return BindingBuilder.bind(noticeQueue)
                .to(classExchange)
                .with("notice");
    }
}
```

#### 步驟 2：訊息路由
```java
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class ClassMessageRouter {
    
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue("homework.queue"),
        exchange = @Exchange(value = "class.exchange", type = "direct"),
        key = "homework"
    ))
    public void handleHomework(String message) {
        System.out.println("收到作業通知: " + message);
    }
    
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue("exam.queue"),
        exchange = @Exchange(value = "class.exchange", type = "direct"),
        key = "exam"
    ))
    public void handleExam(String message) {
        System.out.println("收到考試通知: " + message);
    }
}
```

#### 步驟 3：錯誤處理
```java
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class MessageErrorHandler {
    
    @RabbitListener(queues = "error.queue")
    public void handleError(String errorMessage) {
        System.out.println("處理錯誤訊息: " + errorMessage);
    }
    
    @RabbitListener(queues = "homework.queue")
    public void processHomework(String message) {
        try {
            if (message == null || message.isEmpty()) {
                throw new IllegalArgumentException("訊息不能為空");
            }
            System.out.println("處理作業通知: " + message);
        } catch (Exception e) {
            System.out.println("處理作業通知時發生錯誤: " + e.getMessage());
        }
    }
}
```

## 高級（Advanced）層級

### 1. 概念說明
高級學習者需要掌握：
- 進階訊息路由
- 分散式處理
- 效能優化
- 監控和追蹤

### 2. PlantUML 圖解
```plantuml
@startuml
package "進階 RabbitMQ 系統" {
    class MessageRouter {
        - routes: Map<String, String>
        + route()
        + validate()
    }
    
    class DistributedProcessor {
        - nodes: List<Node>
        + process()
        + synchronize()
    }
    
    class PerformanceMonitor {
        - metrics: Metrics
        + track()
        + alert()
    }
    
    class MessageTracker {
        - traces: List<Trace>
        + track()
        + analyze()
    }
}

MessageRouter --> DistributedProcessor
DistributedProcessor --> PerformanceMonitor
PerformanceMonitor --> MessageTracker
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：進階訊息路由
```java
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class AdvancedMessageRouter {
    
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue("message.router"),
        exchange = @Exchange(value = "class.exchange", type = "topic"),
        key = "message.#"
    ))
    public void routeMessage(String message) {
        if (message.contains("緊急")) {
            rabbitTemplate.convertAndSend("emergency.queue", message);
        } else if (message.contains("通知")) {
            rabbitTemplate.convertAndSend("notification.queue", message);
        } else {
            rabbitTemplate.convertAndSend("default.queue", message);
        }
    }
}
```

#### 步驟 2：分散式處理
```java
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class DistributedMessageProcessor {
    
    @RabbitListener(queues = "distributed.queue")
    public void processMessage(String message) {
        System.out.println("處理分散式訊息: " + message);
        // 同步到其他節點
        syncToOtherNodes(message);
    }
    
    private void syncToOtherNodes(String message) {
        // 實現同步邏輯
    }
}
```

#### 步驟 3：效能監控
```java
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessagePerformanceMonitor {
    private final Map<String, MessageMetrics> metrics = new ConcurrentHashMap<>();
    
    @RabbitListener(queues = "monitored.queue")
    public void processMonitoredMessage(String message) {
        long startTime = System.currentTimeMillis();
        try {
            System.out.println("處理監控訊息: " + message);
            recordMetrics("processMessage", startTime, true);
        } catch (Exception e) {
            recordMetrics("processMessage", startTime, false);
            throw e;
        }
    }
    
    private void recordMetrics(String operation, long startTime, boolean success) {
        long duration = System.currentTimeMillis() - startTime;
        metrics.compute(operation, (key, value) -> {
            if (value == null) {
                return new MessageMetrics(duration, success);
            }
            value.update(duration, success);
            return value;
        });
    }
}
```

這個教學文件提供了從基礎到進階的 Spring RabbitMQ 學習路徑，每個層級都包含了相應的概念說明、圖解、教學步驟和實作範例。初級學習者可以從基本的訊息發送和接收開始，中級學習者可以學習更複雜的交換機和佇列配置，而高級學習者則可以掌握進階訊息路由和分散式處理等進階功能。 