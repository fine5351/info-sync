# Fast JSON 教學

## 初級（Beginner）層級

### 1. 概念說明
Fast JSON 就像是一個超級快速的翻譯機，可以幫我們把 Java 物件和 JSON 字串互相轉換，就像把中文翻譯成英文一樣。初級學習者需要了解：
- 什麼是 JSON
- 為什麼需要 JSON
- 基本的 JSON 轉換方式

### 2. PlantUML 圖解
```plantuml
@startuml
start
:建立 Java 物件;
:使用 Fast JSON 轉換為 JSON 字串;
:使用 Fast JSON 轉換回 Java 物件;
stop
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：基本專案設定
```xml
<!-- pom.xml -->
<dependencies>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.83</version>
    </dependency>
</dependencies>
```

#### 步驟 2：基本使用
```java
import com.alibaba.fastjson.JSON;

public class Student {
    private String name;
    private int age;
    private String grade;
    
    // 建構函式
    public Student(String name, int age, String grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }
    
    // 轉換為 JSON 字串
    public String toJson() {
        return JSON.toJSONString(this);
    }
    
    // 從 JSON 字串轉換回物件
    public static Student fromJson(String json) {
        return JSON.parseObject(json, Student.class);
    }
}
```

#### 步驟 3：簡單範例
```java
public class Main {
    public static void main(String[] args) {
        // 建立學生物件
        Student student = new Student("張小明", 13, "七年級");
        
        // 轉換為 JSON 字串
        String json = student.toJson();
        System.out.println("JSON 字串: " + json);
        
        // 轉換回物件
        Student student2 = Student.fromJson(json);
        System.out.println("學生姓名: " + student2.name);
    }
}
```

## 中級（Intermediate）層級

### 1. 概念說明
中級學習者需要理解：
- JSON 序列化與反序列化
- 複雜物件的處理
- 日期格式處理
- 自訂轉換器

### 2. PlantUML 圖解
```plantuml
@startuml
class JSONProcessor {
    - serializer: Serializer
    - deserializer: Deserializer
    - converters: List<Converter>
    + processObject()
    + processArray()
}

class Serializer {
    - config: SerializeConfig
    + serialize()
    + configure()
}

class Deserializer {
    - config: ParserConfig
    + deserialize()
    + configure()
}

class Converter {
    - type: Class
    + convert()
    + canConvert()
}

JSONProcessor --> Serializer
JSONProcessor --> Deserializer
JSONProcessor --> Converter
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：進階配置
```java
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.parser.ParserConfig;

public class AdvancedJSONConfig {
    private static final SerializeConfig serializeConfig = new SerializeConfig();
    private static final ParserConfig parserConfig = new ParserConfig();
    
    static {
        // 設定日期格式
        serializeConfig.put(Date.class, new DateSerializer());
        
        // 設定序列化特性
        SerializerFeature[] features = {
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.PrettyFormat
        };
    }
    
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj, serializeConfig, features);
    }
    
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz, parserConfig);
    }
}
```

#### 步驟 2：複雜物件處理
```java
import com.alibaba.fastjson.annotation.JSONField;

public class SchoolClass {
    @JSONField(name = "class_name")
    private String className;
    
    @JSONField(name = "teacher")
    private Teacher teacher;
    
    @JSONField(name = "students")
    private List<Student> students;
    
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    // 省略建構函式和其他方法
}

public class Teacher {
    @JSONField(name = "teacher_id")
    private String id;
    
    @JSONField(name = "teacher_name")
    private String name;
    
    @JSONField(name = "subject")
    private String subject;
    
    // 省略建構函式和其他方法
}
```

#### 步驟 3：自訂轉換器
```java
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class DateSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, 
                     Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        
        Date date = (Date) object;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String text = format.format(date);
        out.writeString(text);
    }
}
```

## 高級（Advanced）層級

### 1. 概念說明
高級學習者需要掌握：
- 分散式 JSON 處理
- 效能優化
- 安全性控制
- 監控與分析

### 2. PlantUML 圖解
```plantuml
@startuml
package "進階 JSON 系統" {
    interface JSONSystem {
        + processJSON()
        + optimizePerformance()
        + monitorUsage()
    }
    
    class DistributedProcessor {
        - nodes: List<Node>
        - coordinator: Coordinator
        + handleProcessing()
        + handleLoadBalance()
    }
    
    class PerformanceOptimizer {
        - cache: Cache
        - metrics: Metrics
        + optimize()
        + analyze()
    }
    
    class SecurityManager {
        - validators: List<Validator>
        - policies: List<Policy>
        + validate()
        + enforce()
    }
}

JSONSystem --> DistributedProcessor
DistributedProcessor --> PerformanceOptimizer
PerformanceOptimizer --> SecurityManager
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：分散式處理
```java
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class DistributedJSONProcessor {
    private final List<JSONNode> nodes;
    
    public DistributedJSONProcessor(List<JSONNode> nodes) {
        this.nodes = nodes;
    }
    
    public CompletableFuture<String> processJSON(String json) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return nodes.stream()
                    .map(node -> node.process(json))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("處理失敗"));
            } catch (Exception ex) {
                handleNodeFailure(ex);
                return null;
            }
        });
    }
    
    private void handleNodeFailure(Exception ex) {
        System.err.println("JSON 處理節點失敗: " + ex.getMessage());
    }
}
```

#### 步驟 2：效能優化
```java
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class JSONPerformanceOptimizer {
    private final ConcurrentHashMap<String, JSONCache> cache = new ConcurrentHashMap<>();
    private final AtomicLong totalProcessed = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();
    
    public String processWithCache(String json) {
        String key = generateKey(json);
        JSONCache cached = cache.get(key);
        
        if (cached != null && !cached.isExpired()) {
            return cached.getValue();
        }
        
        long startTime = System.nanoTime();
        String result = processJSON(json);
        long duration = System.nanoTime() - startTime;
        
        cache.put(key, new JSONCache(result));
        recordMetrics(duration);
        
        return result;
    }
    
    private void recordMetrics(long duration) {
        totalProcessed.incrementAndGet();
        totalTime.addAndGet(duration);
    }
    
    private static class JSONCache {
        private final String value;
        private final long timestamp;
        
        JSONCache(String value) {
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > 3600000; // 1小時
        }
        
        String getValue() {
            return value;
        }
    }
}
```

#### 步驟 3：安全性控制
```java
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class JSONSecurityManager {
    private final List<JSONValidator> validators = new CopyOnWriteArrayList<>();
    private final List<SecurityPolicy> policies = new CopyOnWriteArrayList<>();
    
    public void addValidator(JSONValidator validator) {
        validators.add(validator);
    }
    
    public void addPolicy(SecurityPolicy policy) {
        policies.add(policy);
    }
    
    public boolean validateJSON(String json) {
        return validators.stream()
            .allMatch(validator -> validator.validate(json));
    }
    
    public boolean enforcePolicies(String json) {
        return policies.stream()
            .allMatch(policy -> policy.enforce(json));
    }
    
    public interface JSONValidator {
        boolean validate(String json);
    }
    
    public interface SecurityPolicy {
        boolean enforce(String json);
    }
}
```

這個教學文件提供了從基礎到進階的 Fast JSON 學習路徑，每個層級都包含了相應的概念說明、圖解、教學步驟和實作範例。初級學習者可以從基本的 JSON 轉換開始，中級學習者可以學習更複雜的物件處理和自訂轉換，而高級學習者則可以掌握完整的分散式處理系統和效能優化。 