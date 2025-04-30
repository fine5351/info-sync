# Spring Data Redis 教學文件

## 目錄

1. [概述](#概述)
2. [初級教學](#初級教學)
    - [什麼是 Spring Data Redis](#什麼是-spring-data-redis)
    - [為什麼要使用 Spring Data Redis](#為什麼要使用-spring-data-redis)
    - [環境準備](#環境準備)
    - [添加依賴](#添加依賴)
    - [基本配置](#基本配置)
    - [連接到 Redis](#連接到-redis)
    - [基本操作](#基本操作)
    - [字串操作範例](#字串操作範例)
    - [列表操作範例](#列表操作範例)
    - [雜湊操作範例](#雜湊操作範例)
    - [集合操作範例](#集合操作範例)
    - [有序集合操作範例](#有序集合操作範例)
    - [初級練習](#初級練習)
3. [中級教學](#中級教學)
    - [RedisTemplate 詳解](#redistemplate-詳解)
    - [自定義序列化器](#自定義序列化器)
    - [使用 Redis 儲存物件](#使用-redis-儲存物件)
    - [Redis 事務](#redis-事務)
    - [Redis 發布訂閱](#redis-發布訂閱)
    - [Redis 快取註解](#redis-快取註解)
    - [Spring Cache 整合](#spring-cache-整合)
    - [自定義 Redis 操作](#自定義-redis-操作)
    - [Lua 腳本整合](#lua-腳本整合)
    - [中級練習](#中級練習)
4. [高級教學](#高級教學)
    - [連接池配置與優化](#連接池配置與優化)
    - [Redis 哨兵模式整合](#redis-哨兵模式整合)
    - [Redis 叢集整合](#redis-叢集整合)
    - [分散式鎖實現](#分散式鎖實現)
    - [效能監控與調優](#效能監控與調優)
    - [常見問題排查](#常見問題排查)
    - [記憶體優化策略](#記憶體優化策略)
    - [高併發場景最佳實踐](#高併發場景最佳實踐)
    - [高級案例研究](#高級案例研究)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 Spring Data Redis，這是 Spring 框架提供的一個用於簡化 Redis 操作的模組。無論您是完全沒有 Spring Data Redis
經驗的初學者，還是已經了解基礎功能需要進行自定義的中級學習者，或是想要深入了解效能優化和問題排查的高級使用者，本文檔都能為您提供所需的知識和技能。

Spring Data Redis 讓 Java 開發者能夠更輕鬆地使用 Redis 資料庫，它提供了高層次的抽象和便捷的 API，使得 Redis 操作變得簡單而直觀。通過學習 Spring Data Redis，您可以了解如何在 Spring 應用程式中高效地使用
Redis 進行資料快取、訊息發布訂閱、分散式鎖等功能。

## 初級教學

本節適合完全沒有 Spring Data Redis 經驗的初學者。我們將從最基本的概念開始，逐步建立您對 Spring Data Redis 的理解。

### 什麼是 Spring Data Redis

Spring Data Redis 是 Spring Data 專案的一部分，它為 Redis 提供了 Spring 風格的資料訪問支援。簡單來說，它是一個讓 Java 程式更容易使用 Redis 的工具。

想像一下，如果 Redis 是一個外國朋友，只會說外語（Redis 命令），那麼 Spring Data Redis 就像是一個翻譯，它幫助你用 Java 語言與這個朋友交流。你不需要學習外語（Redis 命令），只需要用你熟悉的 Java 語言就可以了。

### 為什麼要使用 Spring Data Redis

使用 Spring Data Redis 有很多好處：

1. **簡化程式碼**：不需要寫複雜的 Redis 命令，只需要使用簡單的 Java 方法。
2. **整合 Spring**：可以輕鬆地與其他 Spring 專案整合。
3. **提供高層抽象**：提供了 RedisTemplate 等工具，使 Redis 操作更加簡單。
4. **支援多種 Redis 客戶端**：可以選擇 Lettuce 或 Jedis 作為底層客戶端。
5. **提供快取支援**：可以輕鬆實現資料快取功能。

### 環境準備

在開始使用 Spring Data Redis 之前，您需要準備以下環境：

1. **Java 開發環境**：安裝 JDK 8 或更高版本。
2. **Maven 或 Gradle**：用於管理專案依賴。
3. **Redis 伺服器**：可以在本機安裝，或使用遠端 Redis 伺服器。
4. **IDE**：推薦使用 IntelliJ IDEA 或 Eclipse。

### 添加依賴

首先，我們需要在專案中添加 Spring Data Redis 的依賴。

#### Maven 專案

在 `pom.xml` 文件中添加以下依賴：

```xml

<dependencies>
  <!-- Spring Data Redis -->
  <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-redis</artifactId>
    <version>2.7.0</version>
  </dependency>

  <!-- Redis 客戶端 (選擇一個) -->
  <!-- Lettuce (推薦) -->
  <dependency>
    <groupId>io.lettuce</groupId>
    <artifactId>lettuce-core</artifactId>
    <version>6.1.8.RELEASE</version>
  </dependency>

  <!-- 或者使用 Jedis -->
  <!--
  <dependency>
      <groupId>redis.clients</groupId>
      <artifactId>jedis</artifactId>
      <version>4.2.3</version>
  </dependency>
  -->
</dependencies>
```

#### Gradle 專案

在 `build.gradle` 文件中添加以下依賴：

```gradle
dependencies {
    // Spring Data Redis
    implementation 'org.springframework.data:spring-data-redis:2.7.0'

    // Redis 客戶端 (選擇一個)
    // Lettuce (推薦)
    implementation 'io.lettuce:lettuce-core:6.1.8.RELEASE'

    // 或者使用 Jedis
    // implementation 'redis.clients:jedis:4.2.3'
}
```

### 基本配置

接下來，我們需要配置 Spring Data Redis 連接到 Redis 伺服器。

#### Java 配置方式

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // 配置 Redis 連接工廠
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 創建 Lettuce 連接工廠
        return new LettuceConnectionFactory("localhost", 6379);
    }

    // 配置 RedisTemplate
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // 設置 key 的序列化器為 StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }
}
```

#### 使用屬性文件配置 (application.properties)

```properties
# Redis 伺服器地址
spring.redis.host=localhost
# Redis 伺服器端口
spring.redis.port=6379
# Redis 資料庫索引（默認為 0）
spring.redis.database=0
# Redis 伺服器密碼（默認為空）
spring.redis.password=
# 連接池最大連接數
spring.redis.lettuce.pool.max-active=8
# 連接池最大阻塞等待時間（使用負值表示沒有限制）
spring.redis.lettuce.pool.max-wait=-1ms
# 連接池中的最大空閒連接
spring.redis.lettuce.pool.max-idle=8
# 連接池中的最小空閒連接
spring.redis.lettuce.pool.min-idle=0
```

### 連接到 Redis

配置完成後，我們可以使用 `RedisTemplate` 來連接 Redis 並進行操作。

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisExample {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisExample(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 測試連接
    public void testConnection() {
        // 設置一個值
        redisTemplate.opsForValue().set("test", "Hello, Redis!");

        // 獲取值
        String value = (String) redisTemplate.opsForValue().get("test");

        System.out.println("從 Redis 獲取的值: " + value);
    }
}
```

### 基本操作

Spring Data Redis 提供了多種操作 Redis 的方法，對應 Redis 的不同資料類型。

#### RedisTemplate 的主要操作方法

- `opsForValue()`: 操作字串
- `opsForList()`: 操作列表
- `opsForSet()`: 操作集合
- `opsForZSet()`: 操作有序集合
- `opsForHash()`: 操作雜湊

### 字串操作範例

字串是 Redis 最基本的資料類型，下面是一些常用的字串操作範例：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class StringOperationsExample {

    private final ValueOperations<String, Object> valueOperations;

    @Autowired
    public StringOperationsExample(RedisTemplate<String, Object> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    // 設置值
    public void setValue(String key, Object value) {
        valueOperations.set(key, value);
    }

    // 獲取值
    public Object getValue(String key) {
        return valueOperations.get(key);
    }

    // 設置值並設定過期時間
    public void setValueWithExpiration(String key, Object value, long timeout, TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }

    // 如果 key 不存在，則設置值
    public boolean setIfAbsent(String key, Object value) {
        return Boolean.TRUE.equals(valueOperations.setIfAbsent(key, value));
    }

    // 增加數值（適用於整數值）
    public Long increment(String key, long delta) {
        return valueOperations.increment(key, delta);
    }
}
```

### 列表操作範例

列表是 Redis 中的有序字串集合，下面是一些常用的列表操作範例：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListOperationsExample {

    private final ListOperations<String, Object> listOperations;

    @Autowired
    public ListOperationsExample(RedisTemplate<String, Object> redisTemplate) {
        this.listOperations = redisTemplate.opsForList();
    }

    // 從列表左側添加元素
    public Long leftPush(String key, Object value) {
        return listOperations.leftPush(key, value);
    }

    // 從列表右側添加元素
    public Long rightPush(String key, Object value) {
        return listOperations.rightPush(key, value);
    }

    // 從列表左側彈出元素
    public Object leftPop(String key) {
        return listOperations.leftPop(key);
    }

    // 從列表右側彈出元素
    public Object rightPop(String key) {
        return listOperations.rightPop(key);
    }

    // 獲取列表指定範圍的元素
    public List<Object> range(String key, long start, long end) {
        return listOperations.range(key, start, end);
    }

    // 獲取列表長度
    public Long size(String key) {
        return listOperations.size(key);
    }
}
```

### 雜湊操作範例

雜湊是 Redis 中的字串欄位和字串值之間的映射，下面是一些常用的雜湊操作範例：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class HashOperationsExample {

    private final HashOperations<String, Object, Object> hashOperations;

    @Autowired
    public HashOperationsExample(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    // 設置雜湊欄位的值
    public void put(String key, Object hashKey, Object value) {
        hashOperations.put(key, hashKey, value);
    }

    // 獲取雜湊欄位的值
    public Object get(String key, Object hashKey) {
        return hashOperations.get(key, hashKey);
    }

    // 刪除雜湊欄位
    public Long delete(String key, Object... hashKeys) {
        return hashOperations.delete(key, hashKeys);
    }

    // 判斷雜湊欄位是否存在
    public Boolean hasKey(String key, Object hashKey) {
        return hashOperations.hasKey(key, hashKey);
    }

    // 獲取所有雜湊欄位和值
    public Map<Object, Object> entries(String key) {
        return hashOperations.entries(key);
    }

    // 獲取所有雜湊欄位
    public Set<Object> keys(String key) {
        return hashOperations.keys(key);
    }

    // 獲取雜湊中欄位的數量
    public Long size(String key) {
        return hashOperations.size(key);
    }
}
```

### 集合操作範例

集合是 Redis 中的無序字串集合，下面是一些常用的集合操作範例：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SetOperationsExample {

    private final SetOperations<String, Object> setOperations;

    @Autowired
    public SetOperationsExample(RedisTemplate<String, Object> redisTemplate) {
        this.setOperations = redisTemplate.opsForSet();
    }

    // 添加元素到集合
    public Long add(String key, Object... values) {
        return setOperations.add(key, values);
    }

    // 從集合中移除元素
    public Long remove(String key, Object... values) {
        return setOperations.remove(key, values);
    }

    // 獲取集合中的所有元素
    public Set<Object> members(String key) {
        return setOperations.members(key);
    }

    // 判斷元素是否在集合中
    public Boolean isMember(String key, Object value) {
        return setOperations.isMember(key, value);
    }

    // 獲取集合的大小
    public Long size(String key) {
        return setOperations.size(key);
    }

    // 獲取兩個集合的交集
    public Set<Object> intersect(String key1, String key2) {
        return setOperations.intersect(key1, key2);
    }

    // 獲取兩個集合的並集
    public Set<Object> union(String key1, String key2) {
        return setOperations.union(key1, key2);
    }

    // 獲取兩個集合的差集
    public Set<Object> difference(String key1, String key2) {
        return setOperations.difference(key1, key2);
    }
}
```

### 有序集合操作範例

有序集合是 Redis 中的字串集合，每個元素都關聯一個分數，下面是一些常用的有序集合操作範例：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ZSetOperationsExample {

    private final ZSetOperations<String, Object> zSetOperations;

    @Autowired
    public ZSetOperationsExample(RedisTemplate<String, Object> redisTemplate) {
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    // 添加元素到有序集合
    public Boolean add(String key, Object value, double score) {
        return zSetOperations.add(key, value, score);
    }

    // 從有序集合中移除元素
    public Long remove(String key, Object... values) {
        return zSetOperations.remove(key, values);
    }

    // 增加元素的分數
    public Double incrementScore(String key, Object value, double delta) {
        return zSetOperations.incrementScore(key, value, delta);
    }

    // 獲取元素的分數
    public Double score(String key, Object value) {
        return zSetOperations.score(key, value);
    }

    // 獲取指定排名範圍的元素
    public Set<Object> range(String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

    // 獲取指定分數範圍的元素
    public Set<Object> rangeByScore(String key, double min, double max) {
        return zSetOperations.rangeByScore(key, min, max);
    }

    // 獲取元素的排名（從低到高）
    public Long rank(String key, Object value) {
        return zSetOperations.rank(key, value);
    }

    // 獲取元素的排名（從高到低）
    public Long reverseRank(String key, Object value) {
        return zSetOperations.reverseRank(key, value);
    }

    // 獲取有序集合的大小
    public Long size(String key) {
        return zSetOperations.size(key);
    }
}
```

### 初級練習

現在，讓我們通過一個簡單的練習來鞏固所學知識。我們將創建一個簡單的用戶管理系統，使用 Spring Data Redis 來儲存和管理用戶資訊。

#### 用戶類

```java
import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String username;
    private String email;
    private int age;

    // 構造函數、getter 和 setter 方法
    public User() {
    }

    public User(String id, String username, String email, int age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
    }

    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", age=" + age +
               '}';
    }
}
```

#### 用戶服務

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, User> hashOperations;
    private static final String KEY = "USER";

    @Autowired
    public UserService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    // 創建用戶
    public User createUser(String username, String email, int age) {
        String id = UUID.randomUUID().toString();
        User user = new User(id, username, email, age);

        // 使用雜湊儲存用戶資訊
        hashOperations.put(KEY, id, user);

        return user;
    }

    // 獲取用戶
    public User getUser(String id) {
        return hashOperations.get(KEY, id);
    }

    // 更新用戶
    public User updateUser(String id, String username, String email, int age) {
        User user = getUser(id);
        if (user != null) {
            user.setUsername(username);
            user.setEmail(email);
            user.setAge(age);
            hashOperations.put(KEY, id, user);
        }
        return user;
    }

    // 刪除用戶
    public void deleteUser(String id) {
        hashOperations.delete(KEY, id);
    }

    // 獲取所有用戶
    public Map<String, User> getAllUsers() {
        return hashOperations.entries(KEY);
    }
}
```

#### 測試用戶服務

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserServiceTest implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // 創建用戶
        User user1 = userService.createUser("小明", "xiaoming@example.com", 15);
        User user2 = userService.createUser("小紅", "xiaohong@example.com", 14);

        System.out.println("創建的用戶1: " + user1);
        System.out.println("創建的用戶2: " + user2);

        // 獲取用戶
        User retrievedUser = userService.getUser(user1.getId());
        System.out.println("獲取的用戶: " + retrievedUser);

        // 更新用戶
        User updatedUser = userService.updateUser(user1.getId(), "小明明", "xiaomingming@example.com", 16);
        System.out.println("更新後的用戶: " + updatedUser);

        // 獲取所有用戶
        Map<String, User> allUsers = userService.getAllUsers();
        System.out.println("所有用戶: " + allUsers);

        // 刪除用戶
        userService.deleteUser(user2.getId());
        allUsers = userService.getAllUsers();
        System.out.println("刪除後的所有用戶: " + allUsers);
    }
}
```

## 中級教學

本節適合已經了解 Spring Data Redis 基礎操作的中級學習者。我們將深入探討 Spring Data Redis 的進階功能和特殊用法。

### RedisTemplate 詳解

`RedisTemplate` 是 Spring Data Redis 的核心類，它提供了豐富的 Redis 操作方法。下面我們將深入了解 `RedisTemplate` 的配置和使用。

#### RedisTemplate 的主要組件

1. **連接工廠 (ConnectionFactory)**：負責建立和管理與 Redis 伺服器的連接。
2. **序列化器 (Serializer)**：負責將 Java 物件轉換為 Redis 可以儲存的格式，以及將 Redis 資料轉換回 Java 物件。
3. **操作類 (Operations)**：提供針對不同 Redis 資料類型的操作方法。

#### 自定義 RedisTemplate

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CustomRedisConfig {

    @Bean
    public RedisTemplate<String, Object> customRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用 StringRedisSerializer 來序列化和反序列化 Redis 的 key
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 使用 GenericJackson2JsonRedisSerializer 來序列化和反序列化 Redis 的 value
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        // 開啟事務支援
        template.setEnableTransactionSupport(true);

        template.afterPropertiesSet();

        return template;
    }
}
```

### 自定義序列化器

Spring Data Redis 提供了多種序列化器，用於將 Java 物件轉換為 Redis 可以儲存的格式。下面是一些常用的序列化器：

1. **StringRedisSerializer**：將字串轉換為位元組陣列，適用於 key 和 value 都是字串的情況。
2. **JdkSerializationRedisSerializer**：使用 Java 的序列化機制，要求物件實現 Serializable 介面。
3. **Jackson2JsonRedisSerializer**：使用 Jackson 2 將物件轉換為 JSON 格式。
4. **GenericJackson2JsonRedisSerializer**：類似於 Jackson2JsonRedisSerializer，但可以處理任何類型的 Java 物件，不需要指定類型。
5. **OxmSerializer**：使用 XML 映射來序列化物件。

#### 自定義 JSON 序列化器範例

```java
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 使用 Jackson2JsonRedisSerializer 來序列化和反序列化 redis 的 value 值
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        // 指定要序列化的域，field,get 和 set，以及修飾符範圍，ANY 是都有包括 private 和 public
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化輸入的類型，類必須是非 final 修飾的，final 修飾的類，比如 String, Integer 等會跑出異常
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        // 值採用 json 序列化
        template.setValueSerializer(serializer);
        // 使用 StringRedisSerializer 來序列化和反序列化 redis 的 key 值
        template.setKeySerializer(new StringRedisSerializer());

        // 設置 hash key 和 value 序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }
}
```

### 使用 Redis 儲存物件

在實際應用中，我們經常需要將 Java 物件儲存到 Redis 中。下面是一個使用 Spring Data Redis 儲存和獲取物件的範例：

#### 定義物件類

```java
import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private String id;
    private String name;
    private double price;
    private Date createTime;

    // 構造函數、getter 和 setter 方法
    public Product() {
    }

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createTime = new Date();
    }

    // Getter 和 Setter 方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Product{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", createTime=" + createTime +
               '}';
    }
}
```

#### 產品服務

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "product:";

    @Autowired
    public ProductService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 儲存產品
    public void saveProduct(Product product) {
        String key = KEY_PREFIX + product.getId();
        redisTemplate.opsForValue().set(key, product);
    }

    // 儲存產品並設置過期時間
    public void saveProductWithExpiration(Product product, long timeout, TimeUnit unit) {
        String key = KEY_PREFIX + product.getId();
        redisTemplate.opsForValue().set(key, product, timeout, unit);
    }

    // 獲取產品
    public Product getProduct(String id) {
        String key = KEY_PREFIX + id;
        return (Product) redisTemplate.opsForValue().get(key);
    }

    // 刪除產品
    public void deleteProduct(String id) {
        String key = KEY_PREFIX + id;
        redisTemplate.delete(key);
    }

    // 獲取所有產品 ID
    public Set<String> getAllProductIds() {
        return redisTemplate.keys(KEY_PREFIX + "*");
    }

    // 檢查產品是否存在
    public boolean exists(String id) {
        String key = KEY_PREFIX + id;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
```

### Redis 事務

Spring Data Redis 提供了對 Redis 事務的支援，可以確保一組操作要麼全部成功，要麼全部失敗。

#### 啟用事務支援

首先，需要在 RedisTemplate 中啟用事務支援：

```java

@Bean
public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    // 啟用事務支援
    template.setEnableTransactionSupport(true);

    // 其他配置...

    return template;
}
```

#### 使用事務

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class TransactionExample {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TransactionExample(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void executeInTransaction() {
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi(); // 開始事務

                try {
                    // 在事務中執行多個操作
                    operations.opsForValue().set("key1", "value1");
                    operations.opsForValue().set("key2", "value2");
                    operations.opsForValue().increment("counter", 1);

                    // 提交事務
                    return operations.exec();
                } catch (Exception e) {
                    // 發生異常時回滾事務
                    operations.discard();
                    throw e;
                }
            }
        });
    }
}
```

### Redis 發布訂閱

Spring Data Redis 提供了對 Redis 發布訂閱功能的支援，可以實現訊息的發布和訂閱。

#### 配置訊息監聽器

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisListenerConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 可以訂閱多個頻道
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        // 指定訊息接收方法
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public MessageReceiver receiver() {
        return new MessageReceiver();
    }
}
```

#### 訊息接收器

```java
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    public void receiveMessage(String message) {
        System.out.println("收到訊息: " + message);
        // 處理訊息...
    }
}
```

#### 訊息發布器

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public MessagePublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
```

### Redis 快取註解

Spring Data Redis 與 Spring Cache 整合，提供了基於註解的快取功能。

#### 啟用快取

```java
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // 快取配置...
}
```

#### 配置 Redis 快取管理器

```java
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 設置快取過期時間為 1 小時
                .entryTtl(Duration.ofHours(1))
                // 設置 key 序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 設置 value 序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不快取 null 值
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}
```

#### 使用快取註解

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class ProductCacheService {

    // 如果快取中有數據，則從快取中獲取；否則執行方法並將結果放入快取
    @Cacheable(value = "products", key = "#id")
    public Product getProduct(String id) {
        System.out.println("從資料庫獲取產品: " + id);
        // 從資料庫獲取產品...
        return new Product(id, "產品" + id, 100.0);
    }

    // 執行方法並將結果放入快取，不管快取中是否已有數據
    @CachePut(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        System.out.println("更新產品: " + product.getId());
        // 更新資料庫中的產品...
        return product;
    }

    // 從快取中移除數據
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(String id) {
        System.out.println("刪除產品: " + id);
        // 從資料庫中刪除產品...
    }

    // 清空整個快取
    @CacheEvict(value = "products", allEntries = true)
    public void clearCache() {
        System.out.println("清空產品快取");
    }
}
```

### Spring Cache 整合

Spring Cache 提供了一個抽象層，可以整合不同的快取實現，包括 Redis。

#### 配置多個快取

```java
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 默認配置
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1));

        // 特定快取配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        // 產品快取 30 分鐘過期
        configMap.put("products", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)));
        // 用戶快取 2 小時過期
        configMap.put("users", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(2)));

        return RedisCacheManager.builder(factory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(configMap)
                .build();
    }
}
```

#### 使用 SpEL 表達式

Spring Cache 支援使用 SpEL 表達式來動態生成快取 key：

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService {

    // 使用多個參數生成 key
    @Cacheable(value = "users", key = "#username + '-' + #age")
    public User getUserByUsernameAndAge(String username, int age) {
        System.out.println("從資料庫獲取用戶: " + username + ", 年齡: " + age);
        // 從資料庫獲取用戶...
        return new User("1", username, username + "@example.com", age);
    }

    // 使用物件屬性生成 key
    @Cacheable(value = "users", key = "#user.username")
    public User getUser(User user) {
        System.out.println("從資料庫獲取用戶: " + user.getUsername());
        // 從資料庫獲取用戶...
        return user;
    }

    // 使用條件快取
    @Cacheable(value = "users", key = "#username", condition = "#age > 18")
    public User getUserIfAdult(String username, int age) {
        System.out.println("從資料庫獲取成年用戶: " + username);
        // 從資料庫獲取用戶...
        return new User("1", username, username + "@example.com", age);
    }

    // 使用 unless 條件（結果不快取的條件）
    @Cacheable(value = "users", key = "#username", unless = "#result == null")
    public User getUserUnlessNull(String username) {
        System.out.println("從資料庫獲取用戶: " + username);
        // 從資料庫獲取用戶...
        return null; // 如果結果為 null，則不快取
    }
}
```

### 自定義 Redis 操作

有時候，我們需要執行一些 RedisTemplate 不直接支援的操作，或者需要更高效地執行批量操作。

#### 使用 Redis 回調

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomRedisOperations {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CustomRedisOperations(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 批量設置值
    public void batchSet(final Map<String, String> keyValues) {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                byte[] key = redisTemplate.getStringSerializer().serialize(entry.getKey());
                byte[] value = redisTemplate.getStringSerializer().serialize(entry.getValue());
                connection.stringCommands().set(key, value);
            }
            return null;
        });
    }

    // 批量獲取值
    public List<Object> batchGet(final List<String> keys) {
        return redisTemplate.execute((RedisCallback<List<Object>>) connection -> {
            List<Object> result = new java.util.ArrayList<>(keys.size());
            for (String key : keys) {
                byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
                byte[] valueBytes = connection.stringCommands().get(keyBytes);
                if (valueBytes != null) {
                    result.add(redisTemplate.getStringSerializer().deserialize(valueBytes));
                } else {
                    result.add(null);
                }
            }
            return result;
        });
    }

    // 執行自定義 Redis 命令
    public Object executeCommand(final String command, final String... args) {
        return redisTemplate.execute((RedisCallback<Object>) connection -> {
            return connection.execute(command, args);
        });
    }
}
```

### Lua 腳本整合

Redis 支援使用 Lua 腳本來執行複雜的操作，Spring Data Redis 提供了對 Lua 腳本的支援。

#### 執行 Lua 腳本

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LuaScriptExample {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public LuaScriptExample(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 使用 Lua 腳本獲取並設置值
    public String getAndSet(String key, String value) {
        // Lua 腳本：獲取舊值並設置新值
        String script = "local oldValue = redis.call('GET', KEYS[1]); " +
                        "redis.call('SET', KEYS[1], ARGV[1]); " +
                        "return oldValue;";

        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);

        return redisTemplate.execute(redisScript, Collections.singletonList(key), value);
    }

    // 使用 Lua 腳本實現計數器
    public Long increment(String key, long delta, long maxValue) {
        // Lua 腳本：增加計數器，但不超過最大值
        String script = "local current = redis.call('GET', KEYS[1]); " +
                        "current = tonumber(current) or 0; " +
                        "local delta = tonumber(ARGV[1]); " +
                        "local maxValue = tonumber(ARGV[2]); " +
                        "if current + delta <= maxValue then " +
                        "    redis.call('INCRBY', KEYS[1], delta); " +
                        "    return current + delta; " +
                        "else " +
                        "    return current; " +
                        "end;";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

        return redisTemplate.execute(redisScript, Collections.singletonList(key), delta, maxValue);
    }

    // 使用 Lua 腳本實現分佈式鎖
    public Boolean acquireLock(String lockKey, String lockValue, long expireTime) {
        // Lua 腳本：如果鎖不存在，則獲取鎖並設置過期時間
        String script = "if redis.call('SET', KEYS[1], ARGV[1], 'NX', 'PX', ARGV[2]) then " +
                        "    return 1; " +
                        "else " +
                        "    return 0; " +
                        "end;";

        RedisScript<Boolean> redisScript = new DefaultRedisScript<>(script, Boolean.class);

        return redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue, expireTime);
    }

    // 使用 Lua 腳本釋放分佈式鎖
    public Boolean releaseLock(String lockKey, String lockValue) {
        // Lua 腳本：如果鎖存在且值匹配，則釋放鎖
        String script = "if redis.call('GET', KEYS[1]) == ARGV[1] then " +
                        "    return redis.call('DEL', KEYS[1]); " +
                        "else " +
                        "    return 0; " +
                        "end;";

        RedisScript<Boolean> redisScript = new DefaultRedisScript<>(script, Boolean.class);

        return redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue);
    }
}
```

### 中級練習

現在，讓我們通過一個更複雜的練習來鞏固所學知識。我們將創建一個購物車系統，使用 Spring Data Redis 來儲存和管理購物車資訊。

#### 購物車項目類

```java
import java.io.Serializable;

public class CartItem implements Serializable {
    private String productId;
    private String productName;
    private double price;
    private int quantity;

    // 構造函數、getter 和 setter 方法
    public CartItem() {
    }

    public CartItem(String productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter 和 Setter 方法
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // 計算小計
    public double getSubtotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
               "productId='" + productId + '\'' +
               ", productName='" + productName + '\'' +
               ", price=" + price +
               ", quantity=" + quantity +
               '}';
    }
}
```

#### 購物車服務

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, CartItem> hashOperations;

    // 購物車 key 前綴
    private static final String CART_KEY_PREFIX = "cart:";
    // 購物車過期時間（天）
    private static final long CART_EXPIRE_DAYS = 7;

    @Autowired
    public CartService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    // 獲取購物車 key
    private String getCartKey(String userId) {
        return CART_KEY_PREFIX + userId;
    }

    // 添加商品到購物車
    public void addItem(String userId, CartItem item) {
        String cartKey = getCartKey(userId);
        String itemId = item.getProductId();

        // 檢查購物車中是否已有該商品
        if (hashOperations.hasKey(cartKey, itemId)) {
            CartItem existingItem = hashOperations.get(cartKey, itemId);
            if (existingItem != null) {
                // 如果已有該商品，增加數量
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                hashOperations.put(cartKey, itemId, existingItem);
            } else {
                // 如果沒有該商品，添加新商品
                hashOperations.put(cartKey, itemId, item);
            }
        } else {
            // 如果沒有該商品，添加新商品
            hashOperations.put(cartKey, itemId, item);
        }

        // 設置購物車過期時間
        redisTemplate.expire(cartKey, CART_EXPIRE_DAYS, TimeUnit.DAYS);
    }

    // 從購物車中移除商品
    public void removeItem(String userId, String productId) {
        String cartKey = getCartKey(userId);
        hashOperations.delete(cartKey, productId);
    }

    // 更新購物車中商品的數量
    public void updateItemQuantity(String userId, String productId, int quantity) {
        String cartKey = getCartKey(userId);
        CartItem item = hashOperations.get(cartKey, productId);
        if (item != null) {
            item.setQuantity(quantity);
            hashOperations.put(cartKey, productId, item);
        }
    }

    // 清空購物車
    public void clearCart(String userId) {
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    // 獲取購物車中的所有商品
    public List<CartItem> getAllItems(String userId) {
        String cartKey = getCartKey(userId);
        Map<String, CartItem> itemMap = hashOperations.entries(cartKey);
        return new ArrayList<>(itemMap.values());
    }

    // 獲取購物車中的商品數量
    public int getItemCount(String userId) {
        String cartKey = getCartKey(userId);
        return hashOperations.size(cartKey).intValue();
    }

    // 計算購物車總金額
    public double getTotalAmount(String userId) {
        List<CartItem> items = getAllItems(userId);
        return items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    // 檢查商品是否在購物車中
    public boolean hasItem(String userId, String productId) {
        String cartKey = getCartKey(userId);
        return hashOperations.hasKey(cartKey, productId);
    }

    // 獲取購物車中的特定商品
    public CartItem getItem(String userId, String productId) {
        String cartKey = getCartKey(userId);
        return hashOperations.get(cartKey, productId);
    }
}
```

#### 購物車控制器

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 獲取購物車所有商品
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable String userId) {
        return cartService.getAllItems(userId);
    }

    // 添加商品到購物車
    @PostMapping("/{userId}/items")
    public void addItem(@PathVariable String userId, @RequestBody CartItem item) {
        cartService.addItem(userId, item);
    }

    // 更新購物車中商品的數量
    @PutMapping("/{userId}/items/{productId}")
    public void updateItemQuantity(@PathVariable String userId,
                                   @PathVariable String productId,
                                   @RequestParam int quantity) {
        cartService.updateItemQuantity(userId, productId, quantity);
    }

    // 從購物車中移除商品
    @DeleteMapping("/{userId}/items/{productId}")
    public void removeItem(@PathVariable String userId, @PathVariable String productId) {
        cartService.removeItem(userId, productId);
    }

    // 清空購物車
    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }

    // 獲取購物車總金額
    @GetMapping("/{userId}/total")
    public double getTotalAmount(@PathVariable String userId) {
        return cartService.getTotalAmount(userId);
    }

    // 獲取購物車商品數量
    @GetMapping("/{userId}/count")
    public int getItemCount(@PathVariable String userId) {
        return cartService.getItemCount(userId);
    }
}
```

#### 測試購物車服務

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartServiceTest implements CommandLineRunner {

    private final CartService cartService;

    @Autowired
    public CartServiceTest(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void run(String... args) {
        // 用戶 ID
        String userId = "user123";

        // 清空購物車，確保測試從空購物車開始
        cartService.clearCart(userId);

        // 添加商品到購物車
        CartItem item1 = new CartItem("p001", "筆記本電腦", 15000.0, 1);
        CartItem item2 = new CartItem("p002", "滑鼠", 500.0, 2);
        CartItem item3 = new CartItem("p003", "鍵盤", 800.0, 1);

        cartService.addItem(userId, item1);
        cartService.addItem(userId, item2);
        cartService.addItem(userId, item3);

        // 獲取購物車中的所有商品
        List<CartItem> items = cartService.getAllItems(userId);
        System.out.println("購物車中的商品:");
        for (CartItem item : items) {
            System.out.println(item + ", 小計: " + item.getSubtotal());
        }

        // 獲取購物車總金額
        double totalAmount = cartService.getTotalAmount(userId);
        System.out.println("購物車總金額: " + totalAmount);

        // 更新商品數量
        cartService.updateItemQuantity(userId, "p002", 3);
        System.out.println("更新滑鼠數量為 3 後的購物車總金額: " + cartService.getTotalAmount(userId));

        // 移除商品
        cartService.removeItem(userId, "p003");
        items = cartService.getAllItems(userId);
        System.out.println("移除鍵盤後的購物車商品:");
        for (CartItem item : items) {
            System.out.println(item + ", 小計: " + item.getSubtotal());
        }

        // 清空購物車
        cartService.clearCart(userId);
        System.out.println("清空購物車後的商品數量: " + cartService.getItemCount(userId));
    }
}
```

## 高級教學

本節適合已經掌握 Spring Data Redis 基礎和進階功能的高級學習者。我們將深入探討 Spring Data Redis 的效能優化、故障排除和高可用性配置等高級主題。

### 連接池配置與優化

在生產環境中，合理配置 Redis 連接池對於應用程式的效能至關重要。

#### Lettuce 連接池配置

```java
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisPoolConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 伺服器配置
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("localhost");
        redisConfig.setPort(6379);
        redisConfig.setDatabase(0);
        // 如果有密碼，設置密碼
        // redisConfig.setPassword(RedisPassword.of("password"));

        // 連接池配置
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        // 最大連接數
        poolConfig.setMaxTotal(20);
        // 最大空閒連接數
        poolConfig.setMaxIdle(10);
        // 最小空閒連接數
        poolConfig.setMinIdle(5);
        // 當連接池耗盡時，等待可用連接的最大時間，超時將拋出異常
        poolConfig.setMaxWait(Duration.ofMillis(3000));
        // 空閒物件逐出器線程的執行間隔時間
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(1000));
        // 當連接池耗盡時，是否阻塞等待
        poolConfig.setBlockWhenExhausted(true);
        // 在獲取連接的時候檢查有效性
        poolConfig.setTestOnBorrow(true);

        // 創建 Lettuce 客戶端配置
        LettucePoolingClientConfiguration lettucePoolConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .commandTimeout(Duration.ofMillis(2000)) // 命令執行超時時間
                .shutdownTimeout(Duration.ofMillis(1000)) // 關閉超時時間
                .build();

        // 創建 Lettuce 連接工廠
        return new LettuceConnectionFactory(redisConfig, lettucePoolConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // 其他配置...
        return template;
    }
}
```

#### 連接池監控

```java
import io.lettuce.core.metrics.DefaultCommandLatencyCollector;
import io.lettuce.core.metrics.DefaultCommandLatencyCollectorOptions;
import io.lettuce.core.metrics.MicrometerCommandLatencyRecorder;
import io.lettuce.core.metrics.MicrometerOptions;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisMonitoringConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(MeterRegistry meterRegistry) {
        // 創建 Lettuce 指標收集器
        DefaultCommandLatencyCollectorOptions latencyOptions = DefaultCommandLatencyCollectorOptions.builder()
                .enable()
                .build();

        DefaultCommandLatencyCollector latencyCollector = new DefaultCommandLatencyCollector(latencyOptions);

        // 配置 Micrometer 指標記錄器
        MicrometerOptions micrometerOptions = MicrometerOptions.builder()
                .build();

        MicrometerCommandLatencyRecorder recorder = new MicrometerCommandLatencyRecorder(meterRegistry, micrometerOptions);

        // 創建 Lettuce 客戶端配置
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(2000))
                .shutdownTimeout(Duration.ofMillis(1000))
                .build();

        // 創建 Lettuce 連接工廠
        return new LettuceConnectionFactory(clientConfig);
    }
}
```

### Redis 哨兵模式整合

Redis 哨兵（Sentinel）提供了高可用性解決方案，可以在主節點故障時自動進行故障轉移。

#### 配置 Redis 哨兵

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisSentinelConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 創建 Redis 哨兵配置
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster") // 主節點名稱
                .sentinel("sentinel1.example.com", 26379) // 哨兵節點 1
                .sentinel("sentinel2.example.com", 26379) // 哨兵節點 2
                .sentinel("sentinel3.example.com", 26379); // 哨兵節點 3

        // 如果有密碼，設置密碼
        // sentinelConfig.setPassword(RedisPassword.of("password"));

        // 創建 Lettuce 連接工廠
        return new LettuceConnectionFactory(sentinelConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // 其他配置...
        return template;
    }
}
```

#### 使用屬性文件配置哨兵 (application.properties)

```properties
# Redis 哨兵配置
spring.redis.sentinel.master=mymaster
spring.redis.sentinel.nodes=sentinel1.example.com:26379,sentinel2.example.com:26379,sentinel3.example.com:26379
spring.redis.password=password
spring.redis.database=0
# Lettuce 連接池配置
spring.redis.lettuce.pool.max-active=20
spring.redis.lettuce.pool.max-idle=10
spring.redis.lettuce.pool.min-idle=5
spring.redis.lettuce.pool.max-wait=3000ms
```

### Redis 叢集整合

Redis 叢集（Cluster）提供了分片和高可用性功能，可以處理大規模的資料和高併發請求。

#### 配置 Redis 叢集

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;

@Configuration
public class RedisClusterConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 創建 Redis 叢集配置
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(
                Arrays.asList(
                        "node1.example.com:6379",
                        "node2.example.com:6379",
                        "node3.example.com:6379",
                        "node4.example.com:6379",
                        "node5.example.com:6379",
                        "node6.example.com:6379"
                )
        );

        // 如果有密碼，設置密碼
        // clusterConfig.setPassword(RedisPassword.of("password"));

        // 設置最大重定向次數
        clusterConfig.setMaxRedirects(3);

        // 創建 Lettuce 連接工廠
        return new LettuceConnectionFactory(clusterConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // 其他配置...
        return template;
    }
}
```

#### 使用屬性文件配置叢集 (application.properties)

```properties
# Redis 叢集配置
spring.redis.cluster.nodes=node1.example.com:6379,node2.example.com:6379,node3.example.com:6379,node4.example.com:6379,node5.example.com:6379,node6.example.com:6379
spring.redis.cluster.max-redirects=3
spring.redis.password=password
# Lettuce 連接池配置
spring.redis.lettuce.pool.max-active=20
spring.redis.lettuce.pool.max-idle=10
spring.redis.lettuce.pool.min-idle=5
spring.redis.lettuce.pool.max-wait=3000ms
```

### 分散式鎖實現

分散式鎖是在分散式系統中實現同步的一種方式，可以防止多個進程同時訪問共享資源。

#### 使用 Lua 腳本實現分散式鎖

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributedLock {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisDistributedLock(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 嘗試獲取分散式鎖
     * @param lockKey 鎖的 key
     * @param requestId 請求標識（用於識別鎖的持有者）
     * @param expireTime 鎖的過期時間（毫秒）
     * @return 是否成功獲取鎖
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime) {
        // 使用 SET key value NX PX milliseconds 命令
        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireTime, TimeUnit.MILLISECONDS);
        return Boolean.TRUE.equals(result);
    }

    /**
     * 釋放分散式鎖
     * @param lockKey 鎖的 key
     * @param requestId 請求標識（用於識別鎖的持有者）
     * @return 是否成功釋放鎖
     */
    public boolean releaseLock(String lockKey, String requestId) {
        // 使用 Lua 腳本確保原子性操作
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        return result != null && result == 1;
    }

    /**
     * 使用分散式鎖執行任務
     * @param lockKey 鎖的 key
     * @param expireTime 鎖的過期時間（毫秒）
     * @param task 要執行的任務
     * @param <T> 任務返回值類型
     * @return 任務執行結果
     * @throws InterruptedException 如果線程被中斷
     */
    public <T> T executeWithLock(String lockKey, long expireTime, DistributedLockCallback<T> task) throws InterruptedException {
        String requestId = UUID.randomUUID().toString();
        boolean locked = false;

        try {
            // 嘗試獲取鎖
            locked = tryLock(lockKey, requestId, expireTime);
            if (locked) {
                // 獲取鎖成功，執行任務
                return task.execute();
            } else {
                // 獲取鎖失敗
                throw new RuntimeException("Failed to acquire lock: " + lockKey);
            }
        } finally {
            // 釋放鎖
            if (locked) {
                releaseLock(lockKey, requestId);
            }
        }
    }

    /**
     * 分散式鎖回調介面
     * @param <T> 返回值類型
     */
    public interface DistributedLockCallback<T> {
        T execute() throws InterruptedException;
    }
}
```

#### 使用分散式鎖範例

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final RedisDistributedLock distributedLock;

    @Autowired
    public InventoryService(RedisDistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }

    /**
     * 減少庫存
     * @param productId 產品 ID
     * @param quantity 減少的數量
     * @return 是否成功減少庫存
     */
    public boolean decreaseInventory(String productId, int quantity) {
        String lockKey = "lock:inventory:" + productId;
        long expireTime = 10000; // 10 秒

        try {
            return distributedLock.executeWithLock(lockKey, expireTime, () -> {
                // 獲取當前庫存
                int currentInventory = getCurrentInventory(productId);

                // 檢查庫存是否足夠
                if (currentInventory < quantity) {
                    return false;
                }

                // 減少庫存
                updateInventory(productId, currentInventory - quantity);

                return true;
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            // 處理其他異常
            return false;
        }
    }

    // 獲取當前庫存（模擬方法）
    private int getCurrentInventory(String productId) {
        // 從資料庫或 Redis 獲取庫存
        return 100;
    }

    // 更新庫存（模擬方法）
    private void updateInventory(String productId, int newInventory) {
        // 更新資料庫或 Redis 中的庫存
    }
}
```

### 效能監控與調優

監控和調優 Redis 效能對於保持應用程式的高效運行至關重要。

#### 使用 Spring Boot Actuator 監控 Redis

首先，添加 Spring Boot Actuator 依賴：

```xml

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

然後，配置 Actuator 端點：

```properties
# 啟用所有 Actuator 端點
management.endpoints.web.exposure.include=*
# 啟用 Redis 健康檢查
management.health.redis.enabled=true
```

#### 自定義 Redis 指標收集器

```java
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Component
public class RedisMetricsCollector {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MeterRegistry meterRegistry;

    @Autowired
    public RedisMetricsCollector(RedisTemplate<String, Object> redisTemplate, MeterRegistry meterRegistry) {
        this.redisTemplate = redisTemplate;
        this.meterRegistry = meterRegistry;
    }

    /**
     * 記錄 Redis 操作的執行時間
     * @param operation 操作名稱
     * @param callback 操作回調
     * @param <T> 返回值類型
     * @return 操作結果
     */
    public <T> T recordOperation(String operation, RedisCallback<T> callback) {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            return redisTemplate.execute(callback);
        } finally {
            sample.stop(meterRegistry.timer("redis.operation", "name", operation));
        }
    }

    /**
     * 收集 Redis 伺服器資訊
     */
    public void collectServerInfo() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            Properties info = connection.serverCommands().info();

            // 記錄連接的客戶端數量
            String connectedClients = info.getProperty("connected_clients");
            if (connectedClients != null) {
                meterRegistry.gauge("redis.clients.connected", Integer.parseInt(connectedClients));
            }

            // 記錄使用的記憶體
            String usedMemory = info.getProperty("used_memory");
            if (usedMemory != null) {
                meterRegistry.gauge("redis.memory.used", Long.parseLong(usedMemory));
            }

            // 記錄每秒處理的命令數
            String instantaneousOpsPerSec = info.getProperty("instantaneous_ops_per_sec");
            if (instantaneousOpsPerSec != null) {
                meterRegistry.gauge("redis.commands.per.second", Integer.parseInt(instantaneousOpsPerSec));
            }

            // 記錄鍵的數量
            String keyspaceKeys = info.getProperty("db0");
            if (keyspaceKeys != null && keyspaceKeys.contains("keys=")) {
                String keysCount = keyspaceKeys.substring(keyspaceKeys.indexOf("keys=") + 5, keyspaceKeys.indexOf(","));
                meterRegistry.gauge("redis.keys.count", Integer.parseInt(keysCount));
            }

            return null;
        });
    }
}
```

#### Redis 效能調優建議

1. **使用連接池**：配置適當的連接池參數，避免頻繁建立和關閉連接。
2. **批量操作**：使用 pipeline 或 multi-exec 批量執行命令，減少網絡往返。
3. **合理設置過期時間**：為 key 設置合理的過期時間，避免 Redis 記憶體不斷增長。
4. **使用適當的資料結構**：根據業務需求選擇合適的 Redis 資料結構。
5. **避免大 key**：拆分大 key，避免單個 key 存儲過多資料。
6. **使用 JSON 序列化**：使用 JSON 序列化器可以提高可讀性和互操作性。
7. **監控 Redis 指標**：定期監控 Redis 的記憶體使用、命令執行時間等指標。

### 常見問題排查

在使用 Spring Data Redis 時，可能會遇到各種問題。以下是一些常見問題及其解決方法。

#### 連接問題

1. **連接超時**

   問題：Redis 連接超時。

   解決方法：
   ```java
   // 增加連接超時時間
   @Bean
   public RedisConnectionFactory redisConnectionFactory() {
       LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
               .commandTimeout(Duration.ofSeconds(5)) // 增加命令超時時間
               .build();

       return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379), clientConfig);
   }
   ```

2. **連接池耗盡**

   問題：連接池中的連接全部被使用，無法獲取新連接。

   解決方法：
   ```java
   // 增加連接池大小
   @Bean
   public RedisConnectionFactory redisConnectionFactory() {
       GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
       poolConfig.setMaxTotal(50); // 增加最大連接數
       poolConfig.setMaxIdle(20); // 增加最大空閒連接數

       LettucePoolingClientConfiguration lettucePoolConfig = LettucePoolingClientConfiguration.builder()
               .poolConfig(poolConfig)
               .build();

       return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379), lettucePoolConfig);
   }
   ```

#### 序列化問題

1. **序列化異常**

   問題：使用 RedisTemplate 儲存或獲取物件時出現序列化異常。

   解決方法：
   ```java
   // 配置適當的序列化器
   @Bean
   public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
       RedisTemplate<String, Object> template = new RedisTemplate<>();
       template.setConnectionFactory(connectionFactory);

       // 使用 Jackson2JsonRedisSerializer 序列化值
       Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
       ObjectMapper om = new ObjectMapper();
       om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
       om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
       jackson2JsonRedisSerializer.setObjectMapper(om);

       // 使用 StringRedisSerializer 序列化鍵
       StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
       template.setKeySerializer(stringRedisSerializer);
       template.setValueSerializer(jackson2JsonRedisSerializer);
       template.setHashKeySerializer(stringRedisSerializer);
       template.setHashValueSerializer(jackson2JsonRedisSerializer);

       template.afterPropertiesSet();
       return template;
   }
   ```

2. **類型轉換錯誤**

   問題：從 Redis 獲取資料時出現類型轉換錯誤。

   解決方法：
   ```java
   // 使用泛型方法獲取資料
   public <T> T getValue(String key, Class<T> clazz) {
       Object value = redisTemplate.opsForValue().get(key);
       if (value == null) {
           return null;
       }

       if (clazz.isInstance(value)) {
           return clazz.cast(value);
       } else {
           // 如果類型不匹配，可以嘗試進行轉換
           ObjectMapper mapper = new ObjectMapper();
           return mapper.convertValue(value, clazz);
       }
   }
   ```

#### 效能問題

1. **高延遲**

   問題：Redis 操作延遲高。

   解決方法：
    - 檢查網絡連接
    - 減少大 key 的使用
    - 使用 pipeline 批量操作
    - 監控 Redis 伺服器負載

   ```java
   // 使用 pipeline 批量操作
   public List<Object> batchGet(List<String> keys) {
       return redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
           for (String key : keys) {
               connection.stringCommands().get(key.getBytes());
           }
           return null;
       });
   }
   ```

2. **記憶體使用過高**

   問題：Redis 記憶體使用過高。

   解決方法：
    - 設置合理的過期時間
    - 使用記憶體優化配置
    - 定期清理不必要的資料

   ```java
   // 設置過期時間
   public void setValue(String key, Object value, long timeout, TimeUnit unit) {
       redisTemplate.opsForValue().set(key, value, timeout, unit);
   }

   // 定期清理過期資料
   @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨 2 點執行
   public void cleanExpiredData() {
       // 執行 SCAN 命令掃描過期的 key
       Set<String> expiredKeys = scanExpiredKeys();
       if (!expiredKeys.isEmpty()) {
           redisTemplate.delete(expiredKeys);
       }
   }

   private Set<String> scanExpiredKeys() {
       // 實現掃描過期 key 的邏輯
       return new HashSet<>();
   }
   ```

### 記憶體優化策略

Redis 是一個記憶體資料庫，合理管理記憶體對於 Redis 的效能至關重要。

#### 記憶體配置

1. **設置最大記憶體**

   在 Redis 配置文件中設置最大記憶體使用量：

   ```
   maxmemory 2gb
   ```

2. **設置記憶體淘汰策略**

   當 Redis 記憶體達到最大值時，可以設置不同的淘汰策略：

   ```
   # 淘汰最近最少使用的帶過期時間的 key
   maxmemory-policy volatile-lru

   # 淘汰最近最少使用的 key，不管有沒有設置過期時間
   # maxmemory-policy allkeys-lru

   # 隨機淘汰帶過期時間的 key
   # maxmemory-policy volatile-random

   # 隨機淘汰所有 key
   # maxmemory-policy allkeys-random

   # 淘汰即將過期的 key
   # maxmemory-policy volatile-ttl

   # 不淘汰 key，但在寫入時返回錯誤
   # maxmemory-policy noeviction
   ```

#### 在 Spring Data Redis 中配置記憶體策略

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisMemoryConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);

        // 使用 Lettuce 客戶端
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config);

        // 在連接後執行配置命令
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 在初始化後設置記憶體策略
        template.execute((RedisCallback<Object>) connection -> {
            // 設置最大記憶體
            connection.serverCommands().configSet("maxmemory", "2gb");
            // 設置記憶體淘汰策略
            connection.serverCommands().configSet("maxmemory-policy", "volatile-lru");
            return null;
        });

        return template;
    }
}
```

#### 記憶體使用監控

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class RedisMemoryMonitor {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisMemoryMonitor(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(fixedRate = 60000) // 每分鐘執行一次
    public void monitorMemoryUsage() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) connection ->
                connection.serverCommands().info("memory"));

        if (info != null) {
            String usedMemory = info.getProperty("used_memory_human");
            String maxMemory = info.getProperty("maxmemory_human");
            String memoryFragmentationRatio = info.getProperty("mem_fragmentation_ratio");

            System.out.println("Redis 記憶體使用情況:");
            System.out.println("已使用記憶體: " + usedMemory);
            System.out.println("最大記憶體: " + maxMemory);
            System.out.println("記憶體碎片率: " + memoryFragmentationRatio);
        }
    }
}
```

### 高併發場景最佳實踐

在高併發場景下，合理使用 Redis 可以大幅提升系統效能。

#### 使用 Redis 作為分散式快取

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductCacheService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductCacheService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(value = "products", key = "#id", unless = "#result == null")
    public Product getProduct(String id) {
        // 從資料庫獲取產品，這個操作可能很慢
        return productRepository.findById(id).orElse(null);
    }
}
```

#### 使用 Redis 實現限流

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RateLimiter {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RateLimiter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 嘗試獲取令牌
     * @param key 限流 key
     * @param limit 限流閾值
     * @param period 限流時間窗口（秒）
     * @return 是否獲取成功
     */
    public boolean tryAcquire(String key, int limit, int period) {
        // 獲取當前計數
        Long count = redisTemplate.opsForValue().increment(key);

        // 如果是第一次請求，設置過期時間
        if (count != null && count == 1) {
            redisTemplate.expire(key, period, TimeUnit.SECONDS);
        }

        // 如果計數超過限制，返回 false
        return count != null && count <= limit;
    }
}
```

#### 使用 Redis 實現分散式計數器

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCounter {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCounter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 增加計數
     * @param key 計數器 key
     * @param delta 增加的值
     * @return 增加後的值
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 減少計數
     * @param key 計數器 key
     * @param delta 減少的值
     * @return 減少後的值
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 獲取計數
     * @param key 計數器 key
     * @return 當前計數
     */
    public Long getCount(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value.toString()) : 0L;
    }
}
```

#### 使用 Redis 實現排行榜

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class RedisLeaderboard {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisLeaderboard(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加或更新分數
     * @param leaderboardKey 排行榜 key
     * @param userId 用戶 ID
     * @param score 分數
     * @return 是否成功
     */
    public Boolean addScore(String leaderboardKey, String userId, double score) {
        return redisTemplate.opsForZSet().add(leaderboardKey, userId, score);
    }

    /**
     * 增加分數
     * @param leaderboardKey 排行榜 key
     * @param userId 用戶 ID
     * @param delta 增加的分數
     * @return 新的分數
     */
    public Double incrementScore(String leaderboardKey, String userId, double delta) {
        return redisTemplate.opsForZSet().incrementScore(leaderboardKey, userId, delta);
    }

    /**
     * 獲取用戶排名（從高到低）
     * @param leaderboardKey 排行榜 key
     * @param userId 用戶 ID
     * @return 排名（從 0 開始）
     */
    public Long getRank(String leaderboardKey, String userId) {
        return redisTemplate.opsForZSet().reverseRank(leaderboardKey, userId);
    }

    /**
     * 獲取用戶分數
     * @param leaderboardKey 排行榜 key
     * @param userId 用戶 ID
     * @return 分數
     */
    public Double getScore(String leaderboardKey, String userId) {
        return redisTemplate.opsForZSet().score(leaderboardKey, userId);
    }

    /**
     * 獲取排行榜前 N 名
     * @param leaderboardKey 排行榜 key
     * @param count 獲取數量
     * @return 排行榜數據
     */
    public List<LeaderboardEntry> getTopN(String leaderboardKey, int count) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(leaderboardKey, 0, count - 1);
        List<LeaderboardEntry> result = new ArrayList<>();

        if (tuples != null) {
            int rank = 0;
            for (ZSetOperations.TypedTuple<Object> tuple : tuples) {
                String userId = tuple.getValue().toString();
                Double score = tuple.getScore();
                result.add(new LeaderboardEntry(userId, score, rank++));
            }
        }

        return result;
    }

    /**
     * 排行榜條目
     */
    public static class LeaderboardEntry {
        private final String userId;
        private final Double score;
        private final int rank;

        public LeaderboardEntry(String userId, Double score, int rank) {
            this.userId = userId;
            this.score = score;
            this.rank = rank;
        }

        public String getUserId() {
            return userId;
        }

        public Double getScore() {
            return score;
        }

        public int getRank() {
            return rank;
        }

        @Override
        public String toString() {
            return "LeaderboardEntry{" +
                   "userId='" + userId + '\'' +
                   ", score=" + score +
                   ", rank=" + rank +
                   '}';
        }
    }
}
```

### 高級案例研究

讓我們通過一個實際案例來綜合應用 Spring Data Redis 的高級功能。

#### 電子商務網站商品庫存管理系統

在電子商務網站中，商品庫存管理是一個關鍵功能，需要處理高併發請求、防止超賣、保證資料一致性等問題。

##### 商品庫存模型

```java
import java.io.Serializable;
import java.util.Date;

public class ProductInventory implements Serializable {
    private String productId;
    private String productName;
    private int availableStock;
    private int reservedStock;
    private Date updateTime;

    // 構造函數、getter 和 setter 方法
    public ProductInventory() {
    }

    public ProductInventory(String productId, String productName, int availableStock) {
        this.productId = productId;
        this.productName = productName;
        this.availableStock = availableStock;
        this.reservedStock = 0;
        this.updateTime = new Date();
    }

    // Getter 和 Setter 方法
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public int getReservedStock() {
        return reservedStock;
    }

    public void setReservedStock(int reservedStock) {
        this.reservedStock = reservedStock;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
               "productId='" + productId + '\'' +
               ", productName='" + productName + '\'' +
               ", availableStock=" + availableStock +
               ", reservedStock=" + reservedStock +
               ", updateTime=" + updateTime +
               '}';
    }
}
```

##### 庫存服務

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class InventoryService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisDistributedLock distributedLock;

    private static final String INVENTORY_KEY_PREFIX = "inventory:";
    private static final String LOCK_KEY_PREFIX = "lock:inventory:";
    private static final long LOCK_EXPIRE_TIME = 10000; // 10 秒

    @Autowired
    public InventoryService(RedisTemplate<String, Object> redisTemplate, RedisDistributedLock distributedLock) {
        this.redisTemplate = redisTemplate;
        this.distributedLock = distributedLock;
    }

    /**
     * 獲取商品庫存
     * @param productId 商品 ID
     * @return 庫存資訊
     */
    public ProductInventory getInventory(String productId) {
        String key = INVENTORY_KEY_PREFIX + productId;
        return (ProductInventory) redisTemplate.opsForValue().get(key);
    }

    /**
     * 初始化商品庫存
     * @param inventory 庫存資訊
     */
    public void initializeInventory(ProductInventory inventory) {
        String key = INVENTORY_KEY_PREFIX + inventory.getProductId();
        redisTemplate.opsForValue().set(key, inventory);
    }

    /**
     * 預留庫存（下單時調用）
     * @param productId 商品 ID
     * @param quantity 預留數量
     * @return 是否成功預留
     */
    public boolean reserveStock(String productId, int quantity) {
        String lockKey = LOCK_KEY_PREFIX + productId;

        try {
            return distributedLock.executeWithLock(lockKey, LOCK_EXPIRE_TIME, () -> {
                // 獲取當前庫存
                ProductInventory inventory = getInventory(productId);
                if (inventory == null) {
                    return false;
                }

                // 檢查可用庫存是否足夠
                if (inventory.getAvailableStock() < quantity) {
                    return false;
                }

                // 更新庫存
                inventory.setAvailableStock(inventory.getAvailableStock() - quantity);
                inventory.setReservedStock(inventory.getReservedStock() + quantity);
                inventory.setUpdateTime(new Date());

                // 保存更新後的庫存
                String key = INVENTORY_KEY_PREFIX + productId;
                redisTemplate.opsForValue().set(key, inventory);

                return true;
            });
        } catch (Exception e) {
            // 處理異常
            return false;
        }
    }

    /**
     * 確認庫存（支付成功後調用）
     * @param productId 商品 ID
     * @param quantity 確認數量
     * @return 是否成功確認
     */
    public boolean confirmStock(String productId, int quantity) {
        String lockKey = LOCK_KEY_PREFIX + productId;

        try {
            return distributedLock.executeWithLock(lockKey, LOCK_EXPIRE_TIME, () -> {
                // 獲取當前庫存
                ProductInventory inventory = getInventory(productId);
                if (inventory == null) {
                    return false;
                }

                // 檢查預留庫存是否足夠
                if (inventory.getReservedStock() < quantity) {
                    return false;
                }

                // 更新庫存
                inventory.setReservedStock(inventory.getReservedStock() - quantity);
                inventory.setUpdateTime(new Date());

                // 保存更新後的庫存
                String key = INVENTORY_KEY_PREFIX + productId;
                redisTemplate.opsForValue().set(key, inventory);

                return true;
            });
        } catch (Exception e) {
            // 處理異常
            return false;
        }
    }

    /**
     * 釋放庫存（取消訂單或支付超時後調用）
     * @param productId 商品 ID
     * @param quantity 釋放數量
     * @return 是否成功釋放
     */
    public boolean releaseStock(String productId, int quantity) {
        String lockKey = LOCK_KEY_PREFIX + productId;

        try {
            return distributedLock.executeWithLock(lockKey, LOCK_EXPIRE_TIME, () -> {
                // 獲取當前庫存
                ProductInventory inventory = getInventory(productId);
                if (inventory == null) {
                    return false;
                }

                // 檢查預留庫存是否足夠
                if (inventory.getReservedStock() < quantity) {
                    return false;
                }

                // 更新庫存
                inventory.setAvailableStock(inventory.getAvailableStock() + quantity);
                inventory.setReservedStock(inventory.getReservedStock() - quantity);
                inventory.setUpdateTime(new Date());

                // 保存更新後的庫存
                String key = INVENTORY_KEY_PREFIX + productId;
                redisTemplate.opsForValue().set(key, inventory);

                return true;
            });
        } catch (Exception e) {
            // 處理異常
            return false;
        }
    }

    /**
     * 使用 Lua 腳本實現原子性庫存扣減
     * @param productId 商品 ID
     * @param quantity 扣減數量
     * @return 是否成功扣減
     */
    public boolean deductStockWithLua(String productId, int quantity) {
        String key = INVENTORY_KEY_PREFIX + productId;

        // Lua 腳本：檢查庫存並扣減
        String script =
                "local inventory = redis.call('GET', KEYS[1]); " +
                "if not inventory then return 0; end; " +
                "inventory = cjson.decode(inventory); " +
                "if inventory.availableStock < tonumber(ARGV[1]) then return 0; end; " +
                "inventory.availableStock = inventory.availableStock - tonumber(ARGV[1]); " +
                "inventory.reservedStock = inventory.reservedStock + tonumber(ARGV[1]); " +
                "inventory.updateTime = os.time() * 1000; " +
                "redis.call('SET', KEYS[1], cjson.encode(inventory)); " +
                "return 1;";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), quantity);

        return result != null && result == 1;
    }

    /**
     * 批量獲取庫存
     * @param productIds 商品 ID 列表
     * @return 庫存列表
     */
    public List<ProductInventory> batchGetInventory(List<String> productIds) {
        List<String> keys = productIds.stream()
                .map(id -> INVENTORY_KEY_PREFIX + id)
                .collect(Collectors.toList());

        List<Object> results = redisTemplate.opsForValue().multiGet(keys);
        List<ProductInventory> inventories = new ArrayList<>();

        if (results != null) {
            for (Object result : results) {
                if (result instanceof ProductInventory) {
                    inventories.add((ProductInventory) result);
                }
            }
        }

        return inventories;
    }
}
```

##### 庫存控制器

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public ProductInventory getInventory(@PathVariable String productId) {
        return inventoryService.getInventory(productId);
    }

    @PostMapping
    public void initializeInventory(@RequestBody ProductInventory inventory) {
        inventoryService.initializeInventory(inventory);
    }

    @PostMapping("/{productId}/reserve")
    public boolean reserveStock(@PathVariable String productId, @RequestParam int quantity) {
        return inventoryService.reserveStock(productId, quantity);
    }

    @PostMapping("/{productId}/confirm")
    public boolean confirmStock(@PathVariable String productId, @RequestParam int quantity) {
        return inventoryService.confirmStock(productId, quantity);
    }

    @PostMapping("/{productId}/release")
    public boolean releaseStock(@PathVariable String productId, @RequestParam int quantity) {
        return inventoryService.releaseStock(productId, quantity);
    }

    @PostMapping("/{productId}/deduct")
    public boolean deductStock(@PathVariable String productId, @RequestParam int quantity) {
        return inventoryService.deductStockWithLua(productId, quantity);
    }
}
```

##### 庫存服務測試

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class InventoryServiceTest implements CommandLineRunner {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryServiceTest(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 初始化庫存
        initializeInventory();

        // 測試庫存預留
        testReserveStock();

        // 測試庫存確認
        testConfirmStock();

        // 測試庫存釋放
        testReleaseStock();

        // 測試高併發庫存扣減
        testConcurrentDeductStock();
    }

    private void initializeInventory() {
        // 創建測試商品庫存
        ProductInventory inventory1 = new ProductInventory("p001", "筆記本電腦", 100);
        ProductInventory inventory2 = new ProductInventory("p002", "智能手機", 200);
        ProductInventory inventory3 = new ProductInventory("p003", "平板電腦", 50);

        // 初始化庫存
        inventoryService.initializeInventory(inventory1);
        inventoryService.initializeInventory(inventory2);
        inventoryService.initializeInventory(inventory3);

        // 獲取並打印庫存
        List<String> productIds = Arrays.asList("p001", "p002", "p003");
        List<ProductInventory> inventories = inventoryService.batchGetInventory(productIds);

        System.out.println("初始化庫存:");
        for (ProductInventory inventory : inventories) {
            System.out.println(inventory);
        }
    }

    private void testReserveStock() {
        String productId = "p001";
        int quantity = 10;

        // 預留庫存
        boolean result = inventoryService.reserveStock(productId, quantity);

        // 獲取並打印庫存
        ProductInventory inventory = inventoryService.getInventory(productId);

        System.out.println("\n預留庫存測試:");
        System.out.println("預留結果: " + result);
        System.out.println("預留後庫存: " + inventory);
    }

    private void testConfirmStock() {
        String productId = "p001";
        int quantity = 5;

        // 確認庫存
        boolean result = inventoryService.confirmStock(productId, quantity);

        // 獲取並打印庫存
        ProductInventory inventory = inventoryService.getInventory(productId);

        System.out.println("\n確認庫存測試:");
        System.out.println("確認結果: " + result);
        System.out.println("確認後庫存: " + inventory);
    }

    private void testReleaseStock() {
        String productId = "p001";
        int quantity = 5;

        // 釋放庫存
        boolean result = inventoryService.releaseStock(productId, quantity);

        // 獲取並打印庫存
        ProductInventory inventory = inventoryService.getInventory(productId);

        System.out.println("\n釋放庫存測試:");
        System.out.println("釋放結果: " + result);
        System.out.println("釋放後庫存: " + inventory);
    }

    private void testConcurrentDeductStock() throws InterruptedException {
        String productId = "p002";
        int threadCount = 10;
        int quantityPerThread = 5;

        // 獲取初始庫存
        ProductInventory initialInventory = inventoryService.getInventory(productId);
        System.out.println("\n高併發庫存扣減測試:");
        System.out.println("初始庫存: " + initialInventory);

        // 創建線程池和計數器
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 提交任務
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    boolean result = inventoryService.deductStockWithLua(productId, quantityPerThread);
                    System.out.println("線程 " + Thread.currentThread().getId() + " 扣減結果: " + result);
                } finally {
                    latch.countDown();
                }
            });
        }

        // 等待所有線程完成
        latch.await();
        executorService.shutdown();

        // 獲取最終庫存
        ProductInventory finalInventory = inventoryService.getInventory(productId);
        System.out.println("最終庫存: " + finalInventory);

        // 計算預期庫存
        int expectedAvailableStock = initialInventory.getAvailableStock() - threadCount * quantityPerThread;
        int expectedReservedStock = initialInventory.getReservedStock() + threadCount * quantityPerThread;

        System.out.println("預期可用庫存: " + expectedAvailableStock);
        System.out.println("預期預留庫存: " + expectedReservedStock);
        System.out.println("實際可用庫存: " + finalInventory.getAvailableStock());
        System.out.println("實際預留庫存: " + finalInventory.getReservedStock());
    }
}
```

## 總結

在本教學中，我們深入探討了 Spring Data Redis 的各個方面，從基礎概念到高級應用。我們學習了如何使用 Spring Data Redis 進行基本的資料操作，如何配置和優化 Redis 連接，以及如何在實際應用中解決各種問題。

### 初級部分

在初級部分，我們介紹了 Spring Data Redis 的基本概念和操作，包括：

1. Spring Data Redis 的基本概念和優勢
2. 環境準備和依賴配置
3. RedisTemplate 的基本使用
4. 各種資料類型的操作（字串、列表、雜湊、集合、有序集合）
5. 簡單的用戶管理系統實例

這些知識為初學者提供了使用 Spring Data Redis 的基礎，讓他們能夠在 Spring 應用程式中輕鬆地使用 Redis。

### 中級部分

在中級部分，我們深入探討了 Spring Data Redis 的進階功能和特殊用法，包括：

1. RedisTemplate 的詳細配置和自定義
2. 序列化器的選擇和配置
3. 儲存和獲取複雜物件
4. Redis 事務的使用
5. Redis 發布訂閱功能
6. Spring Cache 整合
7. 自定義 Redis 操作和 Lua 腳本
8. 購物車系統實例

這些知識幫助中級學習者更深入地理解 Spring Data Redis，並能夠在實際應用中靈活運用。

### 高級部分

在高級部分，我們探討了 Spring Data Redis 的效能優化、故障排除和高可用性配置等高級主題，包括：

1. 連接池配置與優化
2. Redis 哨兵和叢集模式整合
3. 分散式鎖的實現
4. 效能監控與調優
5. 常見問題排查
6. 記憶體優化策略
7. 高併發場景最佳實踐
8. 電子商務網站商品庫存管理系統實例

這些知識幫助高級學習者解決實際應用中的各種問題，並能夠在高併發、高可用性的場景中充分發揮 Redis 的優勢。

### 學習建議

1. **循序漸進**：從基礎開始，逐步深入學習。
2. **實踐為主**：多動手實踐，將所學知識應用到實際項目中。
3. **關注官方文檔**：Spring Data Redis 和 Redis 的官方文檔是最權威的資料來源。
4. **參與社區**：加入 Spring 和 Redis 社區，與其他開發者交流經驗。
5. **持續學習**：Redis 和 Spring 生態系統不斷發展，保持學習的習慣。

通過本教學，您應該已經掌握了 Spring Data Redis 的各個方面，能夠在實際應用中靈活運用 Redis 來提升應用程式的效能和功能。希望本教學能夠幫助您在 Redis 的學習和應用之路上更進一步！
