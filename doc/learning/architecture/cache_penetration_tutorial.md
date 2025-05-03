# 快取穿透教學

## 初級（Beginner）層級

### 1. 概念說明
快取穿透就像學校的圖書館：
- 如果同學要找一本不存在的書，圖書館員會一直找
- 這樣會浪費很多時間
- 我們要記錄哪些書是不存在的，避免重複查找

初級學習者需要了解：
- 什麼是快取穿透
- 為什麼會發生穿透
- 基本的空值處理概念

### 2. PlantUML 圖解
```plantuml
@startuml
class CacheItem {
    - key: String
    - value: Object
    - exists: boolean
    + getKey()
    + getValue()
    + exists()
}

class SimpleCache {
    - items: Map<String, CacheItem>
    + put()
    + get()
    + markNotExists()
}

SimpleCache --> CacheItem
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：基本快取實現
```java
// 配置 Caffeine 快取
public class CacheConfig {
    public Cache<String, Object> createCache() {
        return Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();
    }
}

// 使用 Redis 布隆過濾器
public class CacheService {
    private final Cache<String, Object> cache;
    private final RedissonClient redisson;
    private final DataSource dataSource;
    private final CacheMetrics metrics;
    
    public CacheService(DataSource dataSource) {
        this.cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();
        this.redisson = Redisson.create();
        this.dataSource = dataSource;
        this.metrics = new CacheMetrics();
        
        // 初始化布隆過濾器
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bloom:filter");
        bloomFilter.tryInit(100_000, 0.01); // 預期元素數量 100,000，誤判率 1%
    }
    
    public Object get(String key) {
        // 先從快取獲取
        Object value = cache.getIfPresent(key);
        if (value != null) {
            metrics.recordCacheHit();
            return value;
        }
        
        // 使用布隆過濾器檢查
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bloom:filter");
        if (!bloomFilter.contains(key)) {
            metrics.recordBloomFilterMiss();
            return null;
        }
        
        // 從資料來源獲取數據
        value = dataSource.fetch(key);
        if (value != null) {
            cache.put(key, value);
            metrics.recordCacheMiss();
        } else {
            // 如果數據不存在，將 key 加入布隆過濾器
            bloomFilter.add(key);
            metrics.recordBloomFilterAdd();
        }
        return value;
    }
}
```

## 中級（Intermediate）層級

### 1. 概念說明
中級學習者需要理解：
- 布隆過濾器的使用
- 空值快取機制
- 請求過濾機制
- 降級處理策略

### 2. PlantUML 圖解
```plantuml
@startuml
class CacheService {
    - cache: Cache
    - redisson: RedissonClient
    - dataSource: DataSource
    - metrics: CacheMetrics
    + get()
    + put()
    + handleEmpty()
}

class BloomFilter {
    - filter: RBloomFilter
    + contains()
    + add()
}

class RequestFilter {
    - invalidPatterns: RSet
    + isValid()
    + addPattern()
}

CacheService --> BloomFilter
CacheService --> RequestFilter
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：請求過濾
```java
public class RequestFilter {
    private final RSet<String> invalidPatterns;
    private final Pattern pattern;
    
    public RequestFilter(RedissonClient redisson) {
        this.invalidPatterns = redisson.getSet("invalid:patterns");
        this.pattern = Pattern.compile("[^a-zA-Z0-9]");
    }
    
    public boolean isValid(String key) {
        // 檢查是否包含無效字符
        if (pattern.matcher(key).find()) {
            return false;
        }
        
        // 檢查是否匹配無效模式
        return !invalidPatterns.contains(key);
    }
    
    public void addInvalidPattern(String pattern) {
        invalidPatterns.add(pattern);
    }
}

public class AdvancedCacheService {
    private final Cache<String, Object> cache;
    private final RedissonClient redisson;
    private final RequestFilter requestFilter;
    private final CacheMetrics metrics;
    
    public AdvancedCacheService() {
        this.cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();
        this.redisson = Redisson.create();
        this.requestFilter = new RequestFilter(redisson);
        this.metrics = new CacheMetrics();
        
        // 初始化布隆過濾器
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bloom:filter");
        bloomFilter.tryInit(100_000, 0.01);
    }
    
    public Object get(String key) {
        // 檢查請求是否有效
        if (!requestFilter.isValid(key)) {
            metrics.recordInvalidRequest();
            return handleEmpty(key);
        }
        
        // 使用布隆過濾器
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bloom:filter");
        if (!bloomFilter.contains(key)) {
            metrics.recordBloomFilterMiss();
            return handleEmpty(key);
        }
        
        // 從快取獲取
        Object value = cache.getIfPresent(key);
        if (value != null) {
            metrics.recordCacheHit();
            return value;
        }
        
        // 從資料來源獲取
        value = fetchFromDataSource(key);
        if (value != null) {
            cache.put(key, value);
            metrics.recordCacheMiss();
        } else {
            bloomFilter.add(key);
            metrics.recordBloomFilterAdd();
        }
        return value;
    }
    
    private Object handleEmpty(String key) {
        // 返回降級數據
        return "降級數據";
    }
}
```

### 4. 進階配置

#### 監控配置（使用 Micrometer）
```java
public class CacheMetrics {
    private final Counter cacheHits;
    private final Counter cacheMisses;
    private final Counter bloomFilterHits;
    private final Counter bloomFilterMisses;
    private final Counter invalidRequests;
    
    public CacheMetrics() {
        this.cacheHits = Metrics.counter("cache.hits");
        this.cacheMisses = Metrics.counter("cache.misses");
        this.bloomFilterHits = Metrics.counter("bloom.filter.hits");
        this.bloomFilterMisses = Metrics.counter("bloom.filter.misses");
        this.invalidRequests = Metrics.counter("invalid.requests");
    }
    
    public void recordCacheHit() {
        cacheHits.increment();
    }
    
    public void recordCacheMiss() {
        cacheMisses.increment();
    }
    
    public void recordBloomFilterHit() {
        bloomFilterHits.increment();
    }
    
    public void recordBloomFilterMiss() {
        bloomFilterMisses.increment();
    }
    
    public void recordBloomFilterAdd() {
        bloomFilterMisses.increment();
    }
    
    public void recordInvalidRequest() {
        invalidRequests.increment();
    }
}
```

#### Maven 依賴配置
```xml
<dependencies>
    <dependency>
        <groupId>com.github.ben-manes.caffeine</groupId>
        <artifactId>caffeine</artifactId>
        <version>3.1.8</version>
    </dependency>
    <dependency>
        <groupId>org.redisson</groupId>
        <artifactId>redisson</artifactId>
        <version>3.24.3</version>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-core</artifactId>
        <version>1.11.5</version>
    </dependency>
</dependencies>
```

這個教學文件提供了從基礎到進階的快取穿透學習路徑，每個層級都包含了相應的概念說明、圖解、教學步驟和實作範例。初級學習者可以從基本的快取實現開始，中級學習者可以學習布隆過濾器和請求過濾，而高級學習者則可以掌握多級布隆過濾器和自動恢復策略等進階功能。 