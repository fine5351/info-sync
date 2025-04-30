# Spring Cache 教學文件

## 目錄

1. [概述](#概述)
2. [初級教學](#初級教學)
    - [什麼是 Spring Cache](#什麼是-spring-cache)
    - [為什麼要使用 Spring Cache](#為什麼要使用-spring-cache)
    - [Spring Cache 的基本概念](#spring-cache-的基本概念)
    - [環境準備](#環境準備)
    - [添加依賴](#添加依賴)
    - [基本配置](#基本配置)
    - [使用 @Cacheable 註解](#使用-cacheable-註解)
    - [使用 @CacheEvict 註解](#使用-cacheevict-註解)
    - [使用 @CachePut 註解](#使用-cacheput-註解)
    - [使用 @Caching 註解](#使用-caching-註解)
    - [使用 @CacheConfig 註解](#使用-cacheconfig-註解)
    - [初級範例](#初級範例)
3. [中級教學](#中級教學)
    - [快取管理器詳解](#快取管理器詳解)
    - [自定義快取鍵生成器](#自定義快取鍵生成器)
    - [條件快取](#條件快取)
    - [整合不同的快取提供者](#整合不同的快取提供者)
    - [使用 EhCache](#使用-ehcache)
    - [使用 Redis 作為快取](#使用-redis-作為快取)
    - [使用 Caffeine 快取](#使用-caffeine-快取)
    - [快取同步策略](#快取同步策略)
    - [快取過期策略](#快取過期策略)
    - [中級範例](#中級範例)
4. [高級教學](#高級教學)
    - [快取效能優化](#快取效能優化)
    - [分散式快取設計](#分散式快取設計)
    - [快取穿透問題及解決方案](#快取穿透問題及解決方案)
    - [快取擊穿問題及解決方案](#快取擊穿問題及解決方案)
    - [快取雪崩問題及解決方案](#快取雪崩問題及解決方案)
    - [快取預熱策略](#快取預熱策略)
    - [監控與指標](#監控與指標)
    - [故障排除](#故障排除)
    - [高級範例](#高級範例)
5. [常用配置參考](#常用配置參考)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 Spring Cache 的使用技巧。無論您是完全沒有 Spring Cache 經驗的初學者，還是已經了解基礎功能需要進行自定義的中級使用者，或是想要進行故障排除和效能優化的高級使用者，本文檔都能為您提供所需的知識和技能。

Spring Cache 是 Spring 框架提供的一個用於簡化快取操作的模組。它通過註解的方式，讓開發者可以輕鬆地在應用程式中實現快取功能，而不需要編寫大量的快取邏輯程式碼。通過使用 Spring
Cache，您可以顯著提高應用程式的效能，減少資料庫或其他資源的訪問次數。

## 初級教學

本節適合完全沒有 Spring Cache 經驗的初學者。我們將從最基本的概念開始，逐步建立您對 Spring Cache 的理解。

### 什麼是 Spring Cache

Spring Cache 是 Spring 框架提供的一個快取抽象層，它讓開發者可以通過簡單的註解方式在應用程式中實現快取功能。

想像一下，如果您有一個需要經常計算但結果很少變化的方法（例如查詢資料庫中的用戶資訊），每次調用這個方法都會消耗資源和時間。使用 Spring Cache，您可以將這個方法的結果儲存起來，下次再調用相同的方法時，直接返回儲存的結果，而不需要重新計算，這樣就節省了時間和資源。

就像是您在學校做數學題，如果遇到一個複雜的計算，您可能會把結果記在筆記本上，下次遇到相同的計算時，直接查筆記本而不是重新計算。Spring Cache 就是這個「筆記本」的角色。

### 為什麼要使用 Spring Cache

使用 Spring Cache 有很多好處：

1. **提高應用程式效能**：減少重複計算和資料庫訪問，加快響應速度。
2. **簡化程式碼**：通過註解方式實現快取，不需要編寫複雜的快取邏輯。
3. **提供統一的快取抽象**：可以輕鬆切換不同的快取提供者（如 EhCache、Redis、Caffeine 等）。
4. **聲明式快取**：使用註解聲明哪些方法需要快取，清晰易懂。
5. **減少資源消耗**：減少對資料庫等後端資源的訪問次數。

### Spring Cache 的基本概念

在開始使用 Spring Cache 之前，我們需要了解一些基本概念：

1. **快取（Cache）**：用於儲存數據的容器，可以理解為一個特殊的 Map，其中鍵是方法的參數，值是方法的返回結果。

2. **快取管理器（CacheManager）**：管理多個快取的容器，負責創建、配置和獲取快取。

3. **快取註解**：Spring Cache 提供了多種註解來實現快取功能：
    - `@Cacheable`：表示方法的結果會被快取，下次調用相同參數的方法時，直接從快取返回結果。
    - `@CacheEvict`：表示從快取中移除一個或多個條目。
    - `@CachePut`：表示方法的結果會被放入快取，但方法仍然會被執行。
    - `@Caching`：組合多個快取操作。
    - `@CacheConfig`：在類級別設定共用的快取配置。

4. **快取鍵（Cache Key）**：用於識別快取中的條目，通常基於方法參數生成。

5. **快取提供者（Cache Provider）**：實際儲存快取數據的技術，如 EhCache、Redis、Caffeine 等。

### 環境準備

在開始使用 Spring Cache 之前，您需要準備以下環境：

1. **Java 開發環境**：安裝 JDK 8 或更高版本。
2. **Maven 或 Gradle**：用於管理專案依賴。
3. **Spring 框架**：Spring Cache 是 Spring 框架的一部分。
4. **IDE**：推薦使用 IntelliJ IDEA 或 Eclipse。

### 添加依賴

首先，我們需要在專案中添加 Spring Cache 的依賴。

#### Maven 專案

在 `pom.xml` 文件中添加以下依賴：

```xml
<!-- Spring Boot Starter Cache -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-cache</artifactId>
  <version>2.7.0</version>
</dependency>
```

如果您使用的是 Spring Boot，上面的依賴就足夠了。如果您使用的是傳統的 Spring 框架，則需要添加：

```xml
<!-- Spring Context Support -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context-support</artifactId>
  <version>5.3.20</version>
</dependency>
```

#### Gradle 專案

在 `build.gradle` 文件中添加以下依賴：

```groovy
// Spring Boot Starter Cache
implementation 'org.springframework.boot:spring-boot-starter-cache:2.7.0'
```

或者對於傳統的 Spring 框架：

```groovy
// Spring Context Support
implementation 'org.springframework:spring-context-support:5.3.20'
```

### 基本配置

接下來，我們需要在應用程式中啟用快取功能。

#### Spring Boot 應用程式

在 Spring Boot 應用程式中，只需要在主類上添加 `@EnableCaching` 註解：

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching  // 啟用快取功能
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

#### 傳統 Spring 應用程式

在傳統的 Spring 應用程式中，需要在配置類上添加 `@EnableCaching` 註解，並配置 `CacheManager`：

```java
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching  // 啟用快取功能
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // 使用簡單的 ConcurrentMapCacheManager 作為快取管理器
        return new ConcurrentMapCacheManager();
    }
}
```

### 使用 @Cacheable 註解

`@Cacheable` 是最常用的快取註解，它表示方法的結果會被快取，下次調用相同參數的方法時，直接從快取返回結果，而不執行方法。

基本用法：

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Cacheable("users")  // 使用名為 "users" 的快取
    public User getUserById(Long id) {
        System.out.println("從資料庫獲取用戶，ID: " + id);
        // 模擬從資料庫獲取用戶
        return new User(id, "用戶" + id);
    }
}
```

在上面的例子中，第一次調用 `getUserById` 方法時，會執行方法體並將結果快取起來。之後再用相同的 ID 調用這個方法時，會直接從快取返回結果，不會執行方法體中的程式碼。

您可以通過 `key` 屬性自定義快取鍵：

```java

@Cacheable(value = "users", key = "#id")
public User getUserById(Long id) {
    // 方法體
}
```

您還可以設定條件，只有在滿足條件時才進行快取：

```java

@Cacheable(value = "users", condition = "#id > 0")
public User getUserById(Long id) {
    // 方法體
}
```

### 使用 @CacheEvict 註解

`@CacheEvict` 用於從快取中移除一個或多個條目。當資料發生變化時，我們需要清除相關的快取，以確保快取與資料庫保持一致。

基本用法：

```java
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @CacheEvict(value = "users", key = "#id")
    public void updateUser(Long id, User user) {
        System.out.println("更新用戶，ID: " + id);
        // 更新資料庫中的用戶
    }
}
```

您可以通過 `allEntries` 屬性清除快取中的所有條目：

```java

@CacheEvict(value = "users", allEntries = true)
public void clearAllUsers() {
    System.out.println("清除所有用戶快取");
}
```

您還可以通過 `beforeInvocation` 屬性控制清除快取的時機：

```java

@CacheEvict(value = "users", key = "#id", beforeInvocation = true)
public void deleteUser(Long id) {
    System.out.println("刪除用戶，ID: " + id);
    // 從資料庫刪除用戶
}
```

當 `beforeInvocation = true` 時，無論方法是否成功執行，都會清除快取。默認情況下，只有在方法成功執行後才會清除快取。

### 使用 @CachePut 註解

`@CachePut` 用於更新快取，它會執行方法並將結果放入快取。與 `@Cacheable` 不同，`@CachePut` 標註的方法每次都會被執行。

基本用法：

```java
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @CachePut(value = "users", key = "#user.id")
    public User saveUser(User user) {
        System.out.println("保存用戶，ID: " + user.getId());
        // 保存用戶到資料庫
        return user;
    }
}
```

在上面的例子中，每次調用 `saveUser` 方法時，都會執行方法體，並將返回的 `User` 對象放入快取。

### 使用 @Caching 註解

`@Caching` 用於組合多個快取操作，當您需要在一個方法上應用多個快取註解時，可以使用它。

基本用法：

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Caching(
            cacheable = {
                    @Cacheable(value = "users", key = "#username")
            },
            evict = {
                    @CacheEvict(value = "usersByRole", allEntries = true)
            }
    )
    public User getUserByUsername(String username) {
        System.out.println("從資料庫獲取用戶，用戶名: " + username);
        // 從資料庫獲取用戶
        return new User(1L, username);
    }
}
```

在上面的例子中，`getUserByUsername` 方法的結果會被快取在 "users" 快取中，同時會清除 "usersByRole" 快取中的所有條目。

### 使用 @CacheConfig 註解

`@CacheConfig` 用於在類級別設定共用的快取配置，這樣就不需要在每個方法上重複相同的配置。

基本用法：

```java
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "users")  // 設定共用的快取名稱
public class UserService {

    @Cacheable(key = "#id")  // 不需要再指定 value 或 cacheNames
    public User getUserById(Long id) {
        System.out.println("從資料庫獲取用戶，ID: " + id);
        // 從資料庫獲取用戶
        return new User(id, "用戶" + id);
    }

    @Cacheable(key = "#username")  // 不需要再指定 value 或 cacheNames
    public User getUserByUsername(String username) {
        System.out.println("從資料庫獲取用戶，用戶名: " + username);
        // 從資料庫獲取用戶
        return new User(1L, username);
    }
}
```

### 初級範例

讓我們創建一個完整的範例，展示 Spring Cache 的基本用法：

1. **創建一個 Spring Boot 專案**，並添加 Spring Cache 依賴。

2. **創建一個 User 類**：

```java
public class User {
    private Long id;
    private String username;
    private String email;

    // 構造函數、getter 和 setter

    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
```

3. **創建一個 UserRepository 類**，模擬資料庫操作：

```java
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<Long, User> users = new HashMap<>();

    public UserRepository() {
        // 初始化一些用戶數據
        users.put(1L, new User(1L, "user1", "user1@example.com"));
        users.put(2L, new User(2L, "user2", "user2@example.com"));
        users.put(3L, new User(3L, "user3", "user3@example.com"));
    }

    public User findById(Long id) {
        System.out.println("從資料庫查詢用戶，ID: " + id);
        // 模擬資料庫查詢延遲
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users.get(id);
    }

    public User save(User user) {
        System.out.println("保存用戶到資料庫: " + user);
        users.put(user.getId(), user);
        return user;
    }

    public void delete(Long id) {
        System.out.println("從資料庫刪除用戶，ID: " + id);
        users.remove(id);
    }
}
```

4. **創建一個 UserService 類**，使用 Spring Cache 註解：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void clearAllCache() {
        System.out.println("清除所有用戶快取");
    }
}
```

5. **創建一個 Controller 類**，提供 API 接口：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @DeleteMapping("/cache")
    public void clearCache() {
        userService.clearAllCache();
    }
}
```

6. **在主類中啟用快取**：

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApplication.class, args);
    }
}
```

7. **測試快取效果**：

啟動應用程式後，可以通過以下步驟測試快取效果：

- 訪問 `http://localhost:8080/api/users/1`，第一次會從資料庫查詢，並將結果快取。
- 再次訪問 `http://localhost:8080/api/users/1`，會直接從快取返回結果，不會查詢資料庫。
- 通過 PUT 請求更新用戶：`http://localhost:8080/api/users`，請求體為 `{"id": 1, "username": "updated_user1", "email": "updated_user1@example.com"}`，這會更新快取。
- 再次訪問 `http://localhost:8080/api/users/1`，會返回更新後的用戶資訊，而不是從資料庫查詢。
- 通過 DELETE 請求刪除用戶：`http://localhost:8080/api/users/1`，這會清除該用戶的快取。
- 再次訪問 `http://localhost:8080/api/users/1`，由於快取已被清除，會從資料庫查詢。

這個範例展示了 Spring Cache 的基本用法，包括快取查詢結果、更新快取和清除快取。

## 中級教學

本節適合已經了解 Spring Cache 基礎知識，需要進一步自定義和優化 Spring Cache 使用的中級使用者。

### 快取管理器詳解

快取管理器（CacheManager）是 Spring Cache 的核心組件，負責創建、配置和獲取快取。Spring 提供了多種快取管理器實現，可以根據需求選擇合適的實現。

1. **ConcurrentMapCacheManager**：使用 ConcurrentHashMap 作為快取存儲，適合簡單的場景和開發環境。

```java

@Bean
public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager("users", "roles");
}
```

2. **SimpleCacheManager**：允許手動註冊一組預定義的快取。

```java

@Bean
public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    List<Cache> caches = new ArrayList<>();
    caches.add(new ConcurrentMapCache("users"));
    caches.add(new ConcurrentMapCache("roles"));
    cacheManager.setCaches(caches);
    return cacheManager;
}
```

3. **EhCacheCacheManager**：使用 EhCache 作為快取提供者。

```java

@Bean
public CacheManager cacheManager() {
    return new EhCacheCacheManager(ehCacheManager());
}

@Bean
public EhCacheManagerFactoryBean ehCacheManager() {
    EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
    factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
    factory.setShared(true);
    return factory;
}
```

4. **RedisCacheManager**：使用 Redis 作為快取提供者，適合分散式環境。

```java

@Bean
public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

    return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(config)
            .build();
}
```

5. **CaffeineCacheManager**：使用 Caffeine 作為快取提供者，適合高效能場景。

```java

@Bean
public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager("users", "roles");
    cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(100));
    return cacheManager;
}
```

6. **CompositeCacheManager**：組合多個快取管理器，允許同時使用不同的快取提供者。

```java

@Bean
public CacheManager cacheManager() {
    CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
    List<CacheManager> cacheManagers = new ArrayList<>();
    cacheManagers.add(new ConcurrentMapCacheManager("localCache"));
    cacheManagers.add(redisCacheManager());
    compositeCacheManager.setCacheManagers(cacheManagers);
    return compositeCacheManager;
}
```

### 自定義快取鍵生成器

默認情況下，Spring Cache 使用方法的參數作為快取鍵。但在某些情況下，您可能需要自定義快取鍵的生成方式，例如當方法參數是複雜對象時。

1. **使用 SpEL 表達式**：

```java

@Cacheable(value = "users", key = "#user.id + '-' + #user.username")
public User getUser(User user) {
    // 方法體
}
```

2. **使用自定義鍵生成器**：

```java
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
public class CacheConfig {

    @Bean
    public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "_" + Arrays.toString(params);
            }
        };
    }
}
```

然後在註解中使用這個鍵生成器：

```java

@Cacheable(value = "users", keyGenerator = "customKeyGenerator")
public User getUser(Long id, String username) {
    // 方法體
}
```

3. **使用 @Cacheable 的 key 屬性和 SpEL 表達式**：

```java

@Cacheable(value = "users", key = "T(org.springframework.util.StringUtils).arrayToCommaDelimitedString(#args)")
public User getUser(Object... args) {
    // 方法體
}
```

### 條件快取

Spring Cache 允許您設定條件，只有在滿足條件時才進行快取操作。

1. **使用 condition 屬性**：

```java

@Cacheable(value = "users", key = "#id", condition = "#id > 0")
public User getUserById(Long id) {
    // 方法體
}
```

2. **使用 unless 屬性**：

```java

@Cacheable(value = "users", key = "#id", unless = "#result == null")
public User getUserById(Long id) {
    // 方法體
}
```

`condition` 在方法執行前評估，而 `unless` 在方法執行後評估。您可以同時使用這兩個屬性：

```java

@Cacheable(
        value = "users",
        key = "#id",
        condition = "#id > 0",
        unless = "#result == null"
)
public User getUserById(Long id) {
    // 方法體
}
```

### 整合不同的快取提供者

Spring Cache 提供了統一的抽象，可以輕鬆整合不同的快取提供者。以下是一些常用的快取提供者的整合方式。

### 使用 EhCache

EhCache 是一個流行的 Java 快取庫，提供了豐富的功能和配置選項。

1. **添加依賴**：

```xml
<!-- EhCache -->
<dependency>
  <groupId>org.ehcache</groupId>
  <artifactId>ehcache</artifactId>
  <version>3.9.9</version>
</dependency>

        <!-- Spring EhCache Support -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-cache</artifactId>
<version>2.7.0</version>
</dependency>
```

2. **創建 EhCache 配置文件**（`ehcache.xml`）：

```xml

<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://www.ehcache.org/ehcache.xsd"
         updateCheck="true"
         monitoring="autodetect"
         dynamicConfig="true">

  <diskStore path="java.io.tmpdir"/>

  <cache name="users"
         maxEntriesLocalHeap="10000"
         maxEntriesLocalDisk="1000"
         eternal="false"
         diskSpoolBufferSizeMB="20"
         timeToIdleSeconds="300"
         timeToLiveSeconds="600"
         memoryStoreEvictionPolicy="LFU"
         transactionalMode="off">
    <persistence strategy="localTempSwap"/>
  </cache>

</ehcache>
```

3. **配置 EhCache 快取管理器**：

```java
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManager() {
        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
        factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factory.setShared(true);
        return factory;
    }
}
```

### 使用 Redis 作為快取

Redis 是一個流行的分散式快取系統，適合多節點環境。

1. **添加依賴**：

```xml
<!-- Spring Data Redis -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
  <version>2.7.0</version>
</dependency>
```

2. **配置 Redis 連接**（`application.properties`）：

```properties
spring.redis.host=localhost
spring.redis.port=6379
```

3. **配置 Redis 快取管理器**：

```java
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
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
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 默認配置
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 設定過期時間為 10 分鐘
                .entryTtl(Duration.ofMinutes(10))
                // 設定鍵序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 設定值序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不快取 null 值
                .disableCachingNullValues();

        // 為不同的快取設定不同的配置
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("users", defaultConfig.entryTtl(Duration.ofMinutes(5)));
        cacheConfigurations.put("roles", defaultConfig.entryTtl(Duration.ofMinutes(15)));

        // 創建 Redis 快取管理器
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
```

### 使用 Caffeine 快取

Caffeine 是一個高效能的 Java 快取庫，適合本地快取場景。

1. **添加依賴**：

```xml
<!-- Caffeine -->
<dependency>
  <groupId>com.github.ben-manes.caffeine</groupId>
  <artifactId>caffeine</artifactId>
  <version>3.1.0</version>
</dependency>

        <!-- Spring Cache Starter -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-cache</artifactId>
<version>2.7.0</version>
</dependency>
```

2. **配置 Caffeine 快取管理器**：

```java
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(Arrays.asList("users", "roles"));
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 設定最大快取數量
                .maximumSize(500)
                // 設定寫入後過期時間
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 設定訪問後過期時間
                .expireAfterAccess(5, TimeUnit.MINUTES)
                // 設定初始容量
                .initialCapacity(100)
                // 設定統計功能
                .recordStats());
        return cacheManager;
    }
}
```

### 快取同步策略

在分散式環境中，多個節點可能擁有相同數據的快取副本。當一個節點更新了快取，其他節點的快取可能會變得過時。以下是一些常用的快取同步策略：

1. **基於過期時間的策略**：設定較短的過期時間，讓過時的快取自動失效。

```java

@Bean
public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(5));  // 設定較短的過期時間

    return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(config)
            .build();
}
```

2. **基於消息的策略**：使用消息中間件（如 Kafka、RabbitMQ）發送快取更新事件，其他節點接收到事件後更新或清除快取。

```java

@Service
public class UserService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @CacheEvict(value = "users", key = "#id")
    public void updateUser(Long id, User user) {
        // 更新用戶
        userRepository.save(user);

        // 發送快取更新事件
        kafkaTemplate.send("cache-events", "users:" + id);
    }

    @KafkaListener(topics = "cache-events")
    public void handleCacheEvent(String event) {
        if (event.startsWith("users:")) {
            Long id = Long.parseLong(event.substring(6));
            // 清除本地快取
            cacheManager.getCache("users").evict(id);
        }
    }
}
```

3. **使用分散式快取**：使用 Redis 等分散式快取系統，所有節點共享同一個快取存儲。

```java

@Bean
public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
            .build();
}
```

### 快取過期策略

快取過期策略決定了快取條目何時被移除。以下是一些常用的過期策略：

1. **基於時間的過期策略**：設定快取條目的存活時間（TTL）或閒置時間（TTI）。

```java
// Redis
@Bean
public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10));  // 設定 10 分鐘過期

    return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(config)
            .build();
}

// Caffeine
@Bean
public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)  // 寫入後 10 分鐘過期
            .expireAfterAccess(5, TimeUnit.MINUTES));  // 訪問後 5 分鐘過期
    return cacheManager;
}
```

2. **基於容量的過期策略**：當快取達到最大容量時，根據一定的算法（如 LRU、LFU）移除部分快取條目。

```java
// Caffeine
@Bean
public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(1000));  // 最多快取 1000 個條目
    return cacheManager;
}

// EhCache 配置示例
// 在 ehcache.xml 文件中：
// <cache name="users"
//        maxEntriesLocalHeap="10000"
//        memoryStoreEvictionPolicy="LRU">
// </cache>
```

3. **自定義過期策略**：根據業務需求自定義過期策略。

```java

@Bean
public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();
    cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfter(new Expiry<Object, Object>() {
                @Override
                public long expireAfterCreate(Object key, Object value, long currentTime) {
                    // 根據鍵值決定過期時間
                    if (key.toString().startsWith("admin")) {
                        return TimeUnit.MINUTES.toNanos(30);  // 管理員快取 30 分鐘
                    } else {
                        return TimeUnit.MINUTES.toNanos(10);  // 普通用戶快取 10 分鐘
                    }
                }

                @Override
                public long expireAfterUpdate(Object key, Object value, long currentTime, long currentDuration) {
                    return currentDuration;  // 更新不重置過期時間
                }

                @Override
                public long expireAfterRead(Object key, Object value, long currentTime, long currentDuration) {
                    return currentDuration;  // 讀取不重置過期時間
                }
            }));
    return cacheManager;
}
```

### 中級範例

讓我們創建一個更複雜的範例，展示如何在 Spring Boot 應用程式中使用 Redis 作為快取提供者，並實現快取同步：

1. **添加依賴**：

```xml
<!-- Spring Boot Starter Web -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

        <!-- Spring Boot Starter Data Redis -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

        <!-- Spring Boot Starter Cache -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-cache</artifactId>
</dependency>

        <!-- Spring Boot Starter AMQP (RabbitMQ) -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

2. **配置 Redis 和 RabbitMQ**（`application.properties`）：

```properties
# Redis 配置
spring.redis.host=localhost
spring.redis.port=6379
# RabbitMQ 配置
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

3. **創建快取配置類**：

```java
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 默認配置
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();

        // 為不同的快取設定不同的配置
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("products", defaultConfig.entryTtl(Duration.ofMinutes(5)));
        cacheConfigurations.put("categories", defaultConfig.entryTtl(Duration.ofHours(1)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
```

4. **創建消息配置類**：

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "cache-exchange";
    public static final String QUEUE_NAME = "cache-events";
    public static final String ROUTING_KEY = "cache.events.#";

    @Bean
    public TopicExchange cacheExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue cacheQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue cacheQueue, TopicExchange cacheExchange) {
        return BindingBuilder.bind(cacheQueue).to(cacheExchange).with(ROUTING_KEY);
    }
}
```

5. **創建實體類**：

```java
import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;

    // 構造函數、getter 和 setter

    // 省略 getter 和 setter 方法...
}

public class Category implements Serializable {
    private Long id;
    private String name;
    private String description;

    // 構造函數、getter 和 setter

    // 省略 getter 和 setter 方法...
}
```

6. **創建服務類**：

```java
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 模擬資料庫
    private Map<Long, Product> products = new HashMap<>();

    public ProductService() {
        // 初始化一些產品
        products.put(1L, new Product(1L, "iPhone", "Apple iPhone", new BigDecimal("999.99"), 1L));
        products.put(2L, new Product(2L, "Galaxy", "Samsung Galaxy", new BigDecimal("899.99"), 1L));
        products.put(3L, new Product(3L, "iPad", "Apple iPad", new BigDecimal("499.99"), 2L));
    }

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        System.out.println("從資料庫獲取產品，ID: " + id);
        // 模擬資料庫查詢延遲
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return products.get(id);
    }

    @CacheEvict(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        System.out.println("更新產品: " + product);
        products.put(product.getId(), product);

        // 發送快取更新事件
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                "cache.events.product",
                "products:" + product.getId()
        );

        return product;
    }
}

@Service
public class CategoryService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 模擬資料庫
    private Map<Long, Category> categories = new HashMap<>();

    public CategoryService() {
        // 初始化一些分類
        categories.put(1L, new Category(1L, "Phones", "Mobile phones"));
        categories.put(2L, new Category(2L, "Tablets", "Tablet computers"));
    }

    @Cacheable(value = "categories", key = "#id")
    public Category getCategoryById(Long id) {
        System.out.println("從資料庫獲取分類，ID: " + id);
        // 模擬資料庫查詢延遲
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return categories.get(id);
    }

    @CacheEvict(value = "categories", key = "#category.id")
    public Category updateCategory(Category category) {
        System.out.println("更新分類: " + category);
        categories.put(category.getId(), category);

        // 發送快取更新事件
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                "cache.events.category",
                "categories:" + category.getId()
        );

        return category;
    }
}
```

7. **創建消息監聽器**：

```java
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheEventListener {

    @Autowired
    private CacheManager cacheManager;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleCacheEvent(String event) {
        System.out.println("收到快取事件: " + event);

        if (event.startsWith("products:")) {
            Long id = Long.parseLong(event.substring(9));
            cacheManager.getCache("products").evict(id);
            System.out.println("清除產品快取，ID: " + id);
        } else if (event.startsWith("categories:")) {
            Long id = Long.parseLong(event.substring(11));
            cacheManager.getCache("categories").evict(id);
            System.out.println("清除分類快取，ID: " + id);
        }
    }
}
```

8. **創建控制器**：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @GetMapping("/categories/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/categories")
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }
}
```

9. **測試快取同步**：

啟動應用程式後，可以通過以下步驟測試快取同步：

- 訪問 `http://localhost:8080/api/products/1`，第一次會從資料庫查詢，並將結果快取。
- 再次訪問 `http://localhost:8080/api/products/1`，會直接從快取返回結果。
- 通過 PUT 請求更新產品：`http://localhost:8080/api/products`，請求體為 `{"id": 1, "name": "iPhone 13", "description": "Apple iPhone 13", "price": 1099.99, "categoryId": 1}`。
- 這會更新產品並發送快取更新事件。
- 在另一個節點（模擬）上，監聽器會接收到事件並清除相應的快取。
- 再次訪問 `http://localhost:8080/api/products/1`，由於快取已被清除，會從資料庫查詢最新的產品資訊。

這個範例展示了如何在分散式環境中使用 Redis 作為快取提供者，並通過 RabbitMQ 實現快取同步。

## 高級教學

本節適合已經熟悉 Spring Cache 基本操作，需要進行故障排除、效能優化和高級配置的高級使用者。

### 快取效能優化

優化快取效能可以從多個方面入手：

1. **選擇合適的快取提供者**：

    - 本地快取（如 Caffeine）：適合單節點應用，訪問速度快，但不支持分散式場景。
    - 分散式快取（如 Redis）：適合多節點應用，支持數據共享，但有網路開銷。
    - 多級快取：結合本地快取和分散式快取，兼顧速度和一致性。

2. **優化快取大小**：

    - 設定合適的快取容量，避免過大佔用過多記憶體，或過小導致快取命中率低。
    - 監控快取使用情況，根據實際需求調整容量。

   ```java
   @Bean
   public CacheManager cacheManager() {
       CaffeineCacheManager cacheManager = new CaffeineCacheManager();
       cacheManager.setCaffeine(Caffeine.newBuilder()
               .maximumSize(10000)  // 設定最大快取數量
               .recordStats());  // 啟用統計
       return cacheManager;
   }
   ```

3. **優化快取過期策略**：

    - 根據數據更新頻率設定合適的過期時間。
    - 對於不同類型的數據設定不同的過期策略。
    - 考慮使用基於訪問頻率的過期策略（如 LRU、LFU）。

   ```java
   @Bean
   public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
       Map<String, RedisCacheConfiguration> configMap = new HashMap<>();

       // 頻繁變化的數據，短過期時間
       configMap.put("products", RedisCacheConfiguration.defaultCacheConfig()
               .entryTtl(Duration.ofMinutes(5)));

       // 較少變化的數據，長過期時間
       configMap.put("categories", RedisCacheConfiguration.defaultCacheConfig()
               .entryTtl(Duration.ofHours(24)));

       return RedisCacheManager.builder(redisConnectionFactory)
               .withInitialCacheConfigurations(configMap)
               .build();
   }
   ```

4. **優化序列化方式**：

    - 選擇高效的序列化方式，如 Protocol Buffers、MessagePack 或 Kryo，而不是默認的 Java 序列化。
    - 對於 Redis，可以使用 JSON 序列化，提高可讀性和互操作性。

   ```java
   @Bean
   public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
       RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
               .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class)));

       return RedisCacheManager.builder(redisConnectionFactory)
               .cacheDefaults(config)
               .build();
   }
   ```

5. **使用多級快取**：

    - 結合本地快取和分散式快取，先查詢本地快取，再查詢分散式快取。
    - 本地快取命中率高，可以減少網路開銷。

   ```java
   @Bean
   public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
       // 本地快取
       CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
       caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
               .maximumSize(1000)
               .expireAfterWrite(5, TimeUnit.MINUTES));

       // Redis 快取
       RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
               .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                       .entryTtl(Duration.ofMinutes(30)))
               .build();

       // 組合快取管理器
       CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
       compositeCacheManager.setCacheManagers(Arrays.asList(caffeineCacheManager, redisCacheManager));
       compositeCacheManager.setFallbackToNoOpCache(true);

       return compositeCacheManager;
   }
   ```

### 分散式快取設計

在分散式環境中設計快取系統需要考慮多個因素：

1. **快取一致性**：

    - 使用分散式快取系統（如 Redis）確保所有節點訪問相同的快取數據。
    - 實現快取更新通知機制，當一個節點更新快取時，通知其他節點。

   ```java
   @Service
   public class UserService {

       @Autowired
       private RedisTemplate<String, Object> redisTemplate;

       @CacheEvict(value = "users", key = "#user.id")
       public void updateUser(User user) {
           // 更新用戶
           userRepository.save(user);

           // 發送快取更新消息
           redisTemplate.convertAndSend("cache:users:update", user.getId());
       }
   }

   @Component
   public class CacheUpdateListener {

       @Autowired
       private CacheManager cacheManager;

       @Bean
       public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
           RedisMessageListenerContainer container = new RedisMessageListenerContainer();
           container.setConnectionFactory(connectionFactory);
           container.addMessageListener(new MessageListenerAdapter(this, "handleCacheUpdate"), new ChannelTopic("cache:users:update"));
           return container;
       }

       public void handleCacheUpdate(String id) {
           // 清除本地快取
           cacheManager.getCache("users").evict(id);
       }
   }
   ```

2. **快取分片**：

    - 將快取數據分佈在多個節點上，提高可擴展性。
    - 使用一致性雜湊算法決定數據存儲在哪個節點。

   ```java
   @Bean
   public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
       RedisTemplate<String, Object> template = new RedisTemplate<>();
       template.setConnectionFactory(connectionFactory);
       template.setKeySerializer(new StringRedisSerializer());
       template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
       template.setHashKeySerializer(new StringRedisSerializer());
       template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
       return template;
   }
   ```

3. **快取高可用性**：

    - 使用 Redis Sentinel 或 Redis Cluster 確保快取服務的高可用性。
    - 實現快取降級策略，當快取服務不可用時，降級到直接訪問資料庫。

   ```java
   @Bean
   public RedisSentinelConfiguration sentinelConfig() {
       RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
               .master("mymaster")
               .sentinel("127.0.0.1", 26379)
               .sentinel("127.0.0.1", 26380)
               .sentinel("127.0.0.1", 26381);
       return sentinelConfig;
   }

   @Bean
   public RedisConnectionFactory redisConnectionFactory(RedisSentinelConfiguration sentinelConfig) {
       LettuceConnectionFactory factory = new LettuceConnectionFactory(sentinelConfig);
       return factory;
   }
   ```

### 快取穿透問題及解決方案

快取穿透是指查詢一個不存在的數據，因為不存在，所以每次都會去資料庫查詢，如果有惡意攻擊，大量查詢不存在的數據，會導致資料庫壓力過大。

1. **使用空值快取**：

    - 當查詢不存在的數據時，在快取中存入一個空值或特殊標記。
    - 下次查詢相同的數據時，發現快取中有空值，直接返回，不再查詢資料庫。

   ```java
   @Cacheable(value = "users", key = "#id", unless = "#result == null")
   public User getUserById(Long id) {
       User user = userRepository.findById(id).orElse(null);
       if (user == null) {
           // 如果用戶不存在，存入一個空值
           cacheManager.getCache("users").put(id, new NullValue());
       }
       return user;
   }

   // 自定義一個表示空值的類
   public class NullValue implements Serializable {
       private static final long serialVersionUID = 1L;
   }
   ```

2. **使用布隆過濾器**：

    - 布隆過濾器是一種空間效率很高的概率型數據結構，用於判斷一個元素是否在集合中。
    - 在查詢快取之前，先通過布隆過濾器判斷數據是否存在，如果不存在，直接返回，不再查詢資料庫。

   ```java
   @Service
   public class UserService {

       @Autowired
       private BloomFilter<Long> bloomFilter;

       @Cacheable(value = "users", key = "#id", condition = "#this.exists(#id)")
       public User getUserById(Long id) {
           return userRepository.findById(id).orElse(null);
       }

       public boolean exists(Long id) {
           return bloomFilter.mightContain(id);
       }
   }

   @Configuration
   public class BloomFilterConfig {

       @Bean
       public BloomFilter<Long> bloomFilter() {
           // 創建布隆過濾器，預計存入 10000 個元素，錯誤率為 0.01
           BloomFilter<Long> filter = BloomFilter.create(Funnels.longFunnel(), 10000, 0.01);

           // 初始化布隆過濾器，將所有用戶 ID 加入
           List<Long> allUserIds = userRepository.findAllIds();
           for (Long id : allUserIds) {
               filter.put(id);
           }

           return filter;
       }
   }
   ```

### 快取擊穿問題及解決方案

快取擊穿是指一個熱點數據，在快取過期的瞬間，同時有大量請求湧入，導致所有請求都去查詢資料庫，造成資料庫壓力過大。

1. **使用互斥鎖**：

    - 當快取失效時，使用互斥鎖，保證只有一個線程去查詢資料庫，其他線程等待。
    - 查詢到數據後，放入快取，其他線程從快取獲取數據。

   ```java
   @Service
   public class UserService {

       @Autowired
       private RedisTemplate<String, Object> redisTemplate;

       public User getUserById(Long id) {
           String cacheKey = "user:" + id;
           User user = (User) redisTemplate.opsForValue().get(cacheKey);

           if (user == null) {
               String lockKey = "lock:" + cacheKey;
               boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 10, TimeUnit.SECONDS);

               try {
                   if (locked) {
                       // 獲取鎖成功，查詢資料庫
                       user = userRepository.findById(id).orElse(null);
                       if (user != null) {
                           // 放入快取
                           redisTemplate.opsForValue().set(cacheKey, user, 1, TimeUnit.HOURS);
                       }
                   } else {
                       // 獲取鎖失敗，等待一段時間後重試
                       Thread.sleep(50);
                       return getUserById(id);
                   }
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
               } finally {
                   // 釋放鎖
                   redisTemplate.delete(lockKey);
               }
           }

           return user;
       }
   }
   ```

2. **使用熱點數據永不過期**：

    - 對於熱點數據，設置永不過期，或者設置一個較長的過期時間。
    - 通過後台任務定期更新熱點數據的快取。

   ```java
   @Service
   public class UserService {

       @Autowired
       private RedisTemplate<String, Object> redisTemplate;

       @Scheduled(fixedRate = 3600000)  // 每小時執行一次
       public void refreshHotData() {
           List<Long> hotUserIds = getHotUserIds();  // 獲取熱點用戶 ID

           for (Long id : hotUserIds) {
               User user = userRepository.findById(id).orElse(null);
               if (user != null) {
                   // 更新快取，不設置過期時間
                   redisTemplate.opsForValue().set("user:" + id, user);
               }
           }
       }

       private List<Long> getHotUserIds() {
           // 獲取熱點用戶 ID 的邏輯
           return Arrays.asList(1L, 2L, 3L);
       }
   }
   ```

### 快取雪崩問題及解決方案

快取雪崩是指在某一時刻，大量快取同時過期，導致所有請求都去查詢資料庫，造成資料庫壓力過大。

1. **設置隨機過期時間**：

    - 為快取設置隨機過期時間，避免大量快取同時過期。
    - 例如，在基礎過期時間上增加一個隨機值。

   ```java
   @Bean
   public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
       RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
               .entryTtl(Duration.ofMinutes(10 + new Random().nextInt(5)));  // 10-15 分鐘隨機過期

       return RedisCacheManager.builder(redisConnectionFactory)
               .cacheDefaults(config)
               .build();
   }
   ```

2. **使用多級快取**：

    - 使用本地快取和分散式快取組合，即使分散式快取出現問題，本地快取仍然可用。
    - 本地快取可以使用較短的過期時間，分散式快取使用較長的過期時間。

   ```java
   @Bean
   public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
       // 本地快取
       CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
       caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
               .maximumSize(1000)
               .expireAfterWrite(5, TimeUnit.MINUTES));

       // Redis 快取
       RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
               .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                       .entryTtl(Duration.ofMinutes(30)))
               .build();

       // 組合快取管理器
       CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
       compositeCacheManager.setCacheManagers(Arrays.asList(caffeineCacheManager, redisCacheManager));
       compositeCacheManager.setFallbackToNoOpCache(true);

       return compositeCacheManager;
   }
   ```

3. **使用熔斷機制**：

    - 當檢測到資料庫負載過高時，啟動熔斷機制，返回預設值或舊數據。
    - 使用 Hystrix 等熔斷框架實現。

   ```java
   @HystrixCommand(fallbackMethod = "getUserByIdFallback")
   public User getUserById(Long id) {
       String cacheKey = "user:" + id;
       User user = (User) redisTemplate.opsForValue().get(cacheKey);

       if (user == null) {
           // 快取未命中，查詢資料庫
           user = userRepository.findById(id).orElse(null);
           if (user != null) {
               // 放入快取
               redisTemplate.opsForValue().set(cacheKey, user, 1, TimeUnit.HOURS);
           }
       }

       return user;
   }

   public User getUserByIdFallback(Long id) {
       // 返回預設值或從備用數據源獲取
       return new User(id, "默認用戶", "default@example.com");
   }
   ```

### 快取預熱策略

快取預熱是指在系統啟動或重啟後，提前將數據加載到快取中，避免用戶請求時因為快取未命中而導致的性能問題。

1. **系統啟動時預熱**：

    - 在系統啟動時，加載熱點數據到快取中。
    - 可以使用 `@PostConstruct` 註解或 Spring 的 `ApplicationListener` 實現。

   ```java
   @Service
   public class CacheWarmupService implements ApplicationListener<ContextRefreshedEvent> {

       @Autowired
       private UserService userService;

       @Override
       public void onApplicationEvent(ContextRefreshedEvent event) {
           // 系統啟動時預熱快取
           warmupCache();
       }

       private void warmupCache() {
           System.out.println("開始預熱快取...");

           // 預熱用戶快取
           List<Long> hotUserIds = Arrays.asList(1L, 2L, 3L);  // 熱點用戶 ID
           for (Long id : hotUserIds) {
               userService.getUserById(id);
           }

           System.out.println("快取預熱完成");
       }
   }
   ```

2. **定時預熱**：

    - 定時執行預熱任務，更新快取中的數據。
    - 可以使用 Spring 的 `@Scheduled` 註解實現。

   ```java
   @Service
   public class CacheWarmupService {

       @Autowired
       private UserService userService;

       @Scheduled(fixedRate = 3600000)  // 每小時執行一次
       public void warmupCache() {
           System.out.println("開始定時預熱快取...");

           // 預熱用戶快取
           List<Long> hotUserIds = getHotUserIds();  // 獲取熱點用戶 ID
           for (Long id : hotUserIds) {
               userService.getUserById(id);
           }

           System.out.println("定時快取預熱完成");
       }

       private List<Long> getHotUserIds() {
           // 獲取熱點用戶 ID 的邏輯
           return Arrays.asList(1L, 2L, 3L);
       }
   }
   ```

3. **手動觸發預熱**：

    - 提供 API 接口，允許管理員手動觸發快取預熱。
    - 在系統負載較低時執行預熱任務。

   ```java
   @RestController
   @RequestMapping("/api/cache")
   public class CacheController {

       @Autowired
       private CacheWarmupService cacheWarmupService;

       @PostMapping("/warmup")
       public ResponseEntity<String> warmupCache() {
           try {
               cacheWarmupService.warmupCache();
               return ResponseEntity.ok("快取預熱成功");
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("快取預熱失敗: " + e.getMessage());
           }
       }
   }
   ```

### 監控與指標

監控快取的使用情況和效能是優化快取策略的關鍵：

1. **快取命中率監控**：

    - 記錄快取命中和未命中的次數，計算命中率。
    - 低命中率可能表示快取策略不合理或快取容量不足。

   ```java
   @Aspect
   @Component
   public class CacheMonitorAspect {

       private AtomicLong cacheHits = new AtomicLong(0);
       private AtomicLong cacheMisses = new AtomicLong(0);

       @Around("@annotation(org.springframework.cache.annotation.Cacheable)")
       public Object monitorCache(ProceedingJoinPoint joinPoint) throws Throwable {
           String methodName = joinPoint.getSignature().getName();
           String className = joinPoint.getTarget().getClass().getSimpleName();
           String cacheKey = className + "." + methodName;

           // 檢查快取是否命中
           Object cachedValue = getCachedValue(cacheKey);

           if (cachedValue != null) {
               // 快取命中
               cacheHits.incrementAndGet();
               return cachedValue;
           } else {
               // 快取未命中
               cacheMisses.incrementAndGet();
               Object result = joinPoint.proceed();
               return result;
           }
       }

       @Scheduled(fixedRate = 60000)  // 每分鐘輸出一次統計信息
       public void reportCacheStats() {
           long hits = cacheHits.get();
           long misses = cacheMisses.get();
           long total = hits + misses;

           if (total > 0) {
               double hitRate = (double) hits / total * 100;
               System.out.println("快取命中率: " + String.format("%.2f", hitRate) + "% (" + hits + "/" + total + ")");
           }
       }

       private Object getCachedValue(String cacheKey) {
           // 獲取快取值的邏輯
           return null;  // 示例中返回 null，實際應該從快取中獲取
       }
   }
   ```

2. **快取使用量監控**：

    - 監控快取的大小和使用量，避免記憶體溢出。
    - 設置合理的快取容量上限。

   ```java
   @Bean
   public CacheManager cacheManager() {
       CaffeineCacheManager cacheManager = new CaffeineCacheManager();
       cacheManager.setCaffeine(Caffeine.newBuilder()
               .maximumSize(10000)
               .recordStats());  // 啟用統計
       return cacheManager;
   }

   @Scheduled(fixedRate = 60000)  // 每分鐘輸出一次統計信息
   public void reportCacheStats() {
       CaffeineCacheManager caffeineCacheManager = (CaffeineCacheManager) cacheManager;
       for (String cacheName : caffeineCacheManager.getCacheNames()) {
           Cache cache = caffeineCacheManager.getCache(cacheName);
           if (cache instanceof CaffeineCache) {
               CaffeineCache caffeineCache = (CaffeineCache) cache;
               com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
               CacheStats stats = nativeCache.stats();

               System.out.println("快取 " + cacheName + " 統計信息:");
               System.out.println("  大小: " + nativeCache.estimatedSize());
               System.out.println("  命中率: " + String.format("%.2f", stats.hitRate() * 100) + "%");
               System.out.println("  載入時間: " + stats.averageLoadPenalty() + " ns");
               System.out.println("  驅逐次數: " + stats.evictionCount());
           }
       }
   }
   ```

3. **整合監控工具**：

    - 使用 Spring Boot Actuator 暴露快取指標。
    - 使用 Prometheus 和 Grafana 等工具進行監控和可視化。

   ```xml
   <!-- Spring Boot Actuator -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>

   <!-- Prometheus -->
   <dependency>
       <groupId>io.micrometer</groupId>
       <artifactId>micrometer-registry-prometheus</artifactId>
   </dependency>
   ```

   ```properties
   # application.properties
   management.endpoints.web.exposure.include=health,info,prometheus
   management.metrics.export.prometheus.enabled=true
   ```

### 故障排除

當快取出現問題時，以下是一些常見的故障排除步驟：

1. **快取未生效**：

    - 檢查 `@EnableCaching` 註解是否正確配置。
    - 檢查快取管理器是否正確配置。
    - 檢查快取註解是否正確使用。
    - 檢查方法是否通過代理調用（自調用不會觸發快取）。

   ```java
   // 錯誤示例：自調用不會觸發快取
   @Service
   public class UserService {

       @Cacheable("users")
       public User getUserById(Long id) {
           return findUserById(id);
       }

       private User findUserById(Long id) {
           // 從資料庫查詢用戶
       }

       public void updateUser(User user) {
           // 更新用戶
           getUserById(user.getId());  // 自調用，不會觸發快取
       }
   }

   // 正確示例：通過代理調用
   @Service
   public class UserService {

       @Autowired
       private UserService self;  // 注入自身，獲取代理對象

       @Cacheable("users")
       public User getUserById(Long id) {
           return findUserById(id);
       }

       private User findUserById(Long id) {
           // 從資料庫查詢用戶
       }

       public void updateUser(User user) {
           // 更新用戶
           self.getUserById(user.getId());  // 通過代理調用，會觸發快取
       }
   }
   ```

2. **快取序列化問題**：

    - 確保快取的對象實現了 `Serializable` 接口。
    - 使用合適的序列化器。
    - 檢查序列化和反序列化過程中的錯誤。

   ```java
   // 確保實體類實現 Serializable 接口
   public class User implements Serializable {
       private static final long serialVersionUID = 1L;

       private Long id;
       private String username;
       private String email;

       // 構造函數、getter 和 setter
   }

   // 配置合適的序列化器
   @Bean
   public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
       RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
               .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

       return RedisCacheManager.builder(redisConnectionFactory)
               .cacheDefaults(config)
               .build();
   }
   ```

3. **快取過期問題**：

    - 檢查快取過期時間是否設置合理。
    - 檢查快取是否被意外清除。
    - 使用快取監控工具查看快取的生命週期。

   ```java
   // 設置合理的過期時間
   @Bean
   public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
       Map<String, RedisCacheConfiguration> configMap = new HashMap<>();

       // 頻繁訪問但很少變化的數據，設置較長的過期時間
       configMap.put("categories", RedisCacheConfiguration.defaultCacheConfig()
               .entryTtl(Duration.ofHours(24)));

       // 頻繁變化的數據，設置較短的過期時間
       configMap.put("products", RedisCacheConfiguration.defaultCacheConfig()
               .entryTtl(Duration.ofMinutes(10)));

       return RedisCacheManager.builder(redisConnectionFactory)
               .withInitialCacheConfigurations(configMap)
               .build();
   }
   ```

4. **記憶體溢出問題**：

    - 設置合理的快取容量上限。
    - 使用合適的驅逐策略。
    - 監控快取使用量，及時調整容量。

   ```java
   @Bean
   public CacheManager cacheManager() {
       CaffeineCacheManager cacheManager = new CaffeineCacheManager();
       cacheManager.setCaffeine(Caffeine.newBuilder()
               .maximumSize(10000)  // 設置最大容量
               .expireAfterWrite(1, TimeUnit.HOURS)  // 設置過期時間
               .removalListener((key, value, cause) -> {
                   System.out.println("快取條目被移除: " + key + ", 原因: " + cause);
               }));
       return cacheManager;
   }
   ```

### 高級範例

讓我們創建一個完整的高級範例，展示如何在 Spring Boot 應用程式中實現多級快取、快取預熱和監控：

1. **添加依賴**：

```xml
<!-- Spring Boot Starter Web -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

        <!-- Spring Boot Starter Cache -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-cache</artifactId>
</dependency>

        <!-- Spring Boot Starter Data Redis -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

        <!-- Caffeine Cache -->
<dependency>
<groupId>com.github.ben-manes.caffeine</groupId>
<artifactId>caffeine</artifactId>
<version>3.1.0</version>
</dependency>

        <!-- Spring Boot Actuator -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

        <!-- Prometheus -->
<dependency>
<groupId>io.micrometer</groupId>
<artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

2. **配置多級快取**：

```java

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 本地快取 (Caffeine)
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .recordStats());

        // 分散式快取 (Redis)
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();

        // 組合快取管理器
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(Arrays.asList(caffeineCacheManager, redisCacheManager));
        compositeCacheManager.setFallbackToNoOpCache(true);

        return compositeCacheManager;
    }
}
```

3. **實現快取預熱**：

```java

@Service
public class CacheWarmupService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 系統啟動時預熱快取
        warmupCache();
    }

    public void warmupCache() {
        System.out.println("開始預熱快取...");

        // 預熱產品快取
        List<Long> hotProductIds = Arrays.asList(1L, 2L, 3L);
        for (Long id : hotProductIds) {
            productService.getProductById(id);
        }

        // 預熱分類快取
        List<Long> allCategoryIds = Arrays.asList(1L, 2L);
        for (Long id : allCategoryIds) {
            categoryService.getCategoryById(id);
        }

        System.out.println("快取預熱完成");
    }

    @Scheduled(cron = "0 0 */2 * * *")  // 每 2 小時執行一次
    public void scheduledWarmup() {
        System.out.println("開始定時預熱快取...");
        warmupCache();
        System.out.println("定時快取預熱完成");
    }
}
```

4. **實現快取監控**：

```java

@Component
public class CacheMonitor {

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 300000)  // 每 5 分鐘執行一次
    public void reportCacheStats() {
        System.out.println("=== 快取統計信息 ===");

        if (cacheManager instanceof CompositeCacheManager) {
            CompositeCacheManager compositeCacheManager = (CompositeCacheManager) cacheManager;
            for (CacheManager cm : compositeCacheManager.getCacheManagers()) {
                if (cm instanceof CaffeineCacheManager) {
                    reportCaffeineCacheStats((CaffeineCacheManager) cm);
                }
            }
        }
    }

    private void reportCaffeineCacheStats(CaffeineCacheManager caffeineCacheManager) {
        for (String cacheName : caffeineCacheManager.getCacheNames()) {
            Cache cache = caffeineCacheManager.getCache(cacheName);
            if (cache instanceof CaffeineCache) {
                CaffeineCache caffeineCache = (CaffeineCache) cache;
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
                CacheStats stats = nativeCache.stats();

                System.out.println("快取 " + cacheName + " 統計信息:");
                System.out.println("  大小: " + nativeCache.estimatedSize());
                System.out.println("  命中率: " + String.format("%.2f", stats.hitRate() * 100) + "%");
                System.out.println("  載入時間: " + stats.averageLoadPenalty() + " ns");
                System.out.println("  驅逐次數: " + stats.evictionCount());
            }
        }
    }
}
```

5. **實現快取穿透和擊穿保護**：

```java

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private BloomFilter<Long> bloomFilter;

    public Product getProductById(Long id) {
        // 使用布隆過濾器防止快取穿透
        if (!bloomFilter.mightContain(id)) {
            System.out.println("產品 ID " + id + " 不存在（布隆過濾器）");
            return null;
        }

        String cacheKey = "product:" + id;
        Product product = (Product) redisTemplate.opsForValue().get(cacheKey);

        if (product == null) {
            // 使用互斥鎖防止快取擊穿
            String lockKey = "lock:" + cacheKey;
            boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 10, TimeUnit.SECONDS);

            try {
                if (locked) {
                    // 獲取鎖成功，查詢資料庫
                    product = productRepository.findById(id).orElse(null);

                    if (product != null) {
                        // 放入快取，使用隨機過期時間防止快取雪崩
                        int randomExpiry = 3600 + new Random().nextInt(600);  // 1 小時 +/- 10 分鐘
                        redisTemplate.opsForValue().set(cacheKey, product, randomExpiry, TimeUnit.SECONDS);
                    } else {
                        // 放入空值，防止快取穿透
                        redisTemplate.opsForValue().set(cacheKey, new NullValue(), 60, TimeUnit.SECONDS);
                    }
                } else {
                    // 獲取鎖失敗，等待一段時間後重試
                    Thread.sleep(50);
                    return getProductById(id);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // 釋放鎖
                redisTemplate.delete(lockKey);
            }
        }

        // 如果是空值佔位符，返回 null
        if (product instanceof NullValue) {
            return null;
        }

        return product;
    }

    // 其他方法...
}
```

6. **實現快取控制接口**：

```java

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Autowired
    private CacheWarmupService cacheWarmupService;

    @Autowired
    private CacheManager cacheManager;

    @PostMapping("/warmup")
    public ResponseEntity<String> warmupCache() {
        try {
            cacheWarmupService.warmupCache();
            return ResponseEntity.ok("快取預熱成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("快取預熱失敗: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cacheName}")
    public ResponseEntity<String> clearCache(@PathVariable String cacheName) {
        try {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
                return ResponseEntity.ok("清除快取 " + cacheName + " 成功");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到快取 " + cacheName);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("清除快取失敗: " + e.getMessage());
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();

        // 收集快取統計信息
        // 實際實現會根據使用的快取提供者有所不同

        return ResponseEntity.ok(stats);
    }
}
```

這個高級範例展示了如何在 Spring Boot 應用程式中實現多級快取、快取預熱、快取監控，以及如何解決快取穿透、擊穿和雪崩問題。

## 常用配置參考

以下是一些常用的 Spring Cache 配置參考：

### 1. 基本配置

```java

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // 使用 ConcurrentMapCacheManager 作為快取管理器
        return new ConcurrentMapCacheManager();
    }
}
```

### 2. Redis 快取配置

```java

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
```

### 3. Caffeine 快取配置

```java

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .recordStats());
        return cacheManager;
    }
}
```

### 4. EhCache 快取配置

```java

@Configuration
@EnableCaching
public class EhCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManager() {
        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
        factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factory.setShared(true);
        return factory;
    }
}
```

### 5. 多級快取配置

```java

@Configuration
@EnableCaching
public class MultiLevelCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 本地快取
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.MINUTES));

        // Redis 快取
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(30)))
                .build();

        // 組合快取管理器
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(Arrays.asList(caffeineCacheManager, redisCacheManager));
        compositeCacheManager.setFallbackToNoOpCache(true);

        return compositeCacheManager;
    }
}
```

### 6. 自定義鍵生成器配置

```java

@Configuration
@EnableCaching
public class KeyGeneratorConfig {

    @Bean
    public KeyGenerator customKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append(".");
            sb.append(method.getName());
            sb.append("(");
            for (Object param : params) {
                sb.append(param.toString());
                sb.append(",");
            }
            if (params.length > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(")");
            return sb.toString();
        };
    }
}
```

### 7. 快取同步配置

```java

@Configuration
public class CacheSyncConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            CacheUpdateListener cacheUpdateListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(
                new MessageListenerAdapter(cacheUpdateListener, "handleCacheUpdate"),
                new ChannelTopic("cache:update"));
        return container;
    }
}

@Component
public class CacheUpdateListener {

    @Autowired
    private CacheManager cacheManager;

    public void handleCacheUpdate(String message) {
        // 解析消息，清除相應的快取
        String[] parts = message.split(":");
        if (parts.length == 2) {
            String cacheName = parts[0];
            String key = parts[1];
            cacheManager.getCache(cacheName).evict(key);
        }
    }
}
```

### 8. 快取監控配置

```java

@Configuration
public class CacheMonitoringConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "cache-demo");
    }

    @Bean
    public CacheMetricsRegistrar cacheMetricsRegistrar(MeterRegistry registry, CacheManager cacheManager) {
        return new CacheMetricsRegistrar(registry, cacheManager);
    }
}

@Component
public class CacheMetricsRegistrar {

    public CacheMetricsRegistrar(MeterRegistry registry, CacheManager cacheManager) {
        // 註冊快取指標
        if (cacheManager instanceof CaffeineCacheManager) {
            CaffeineCacheManager caffeineCacheManager = (CaffeineCacheManager) cacheManager;
            for (String cacheName : caffeineCacheManager.getCacheNames()) {
                Cache cache = caffeineCacheManager.getCache(cacheName);
                if (cache instanceof CaffeineCache) {
                    CaffeineCache caffeineCache = (CaffeineCache) cache;
                    com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
                    CaffeineCacheMetrics.monitor(registry, nativeCache, cacheName);
                }
            }
        }
    }
}
```

這些配置範例涵蓋了 Spring Cache 的常見使用場景，您可以根據自己的需求選擇合適的配置。
