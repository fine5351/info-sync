## 🟡 中級階段：面對萬人級流量（高併發，約 100 TPS）

### ✅ 系統背景

- 使用人數超過萬人
- 每秒請求數達 100+（高併發）
- 系統需穩定、具韌性、能承受壓力

---

### ✅ 架構建議

- 架構支援橫向擴充（多台伺服器）
- 資料庫實作讀寫分離（主從架構）
- 引入 Redis 快取層減少資料庫壓力
- 增加 API Gateway 與集中式日誌系統

---

### ✅ 實作方案說明

#### 🔁 橫向擴充（Horizontal Scaling）

- Spring Boot 部署於多台機器或容器
- 使用 Nginx / HAProxy 實作負載平衡

```nginx
upstream backend {
    server app1:8080;
    server app2:8080;
}
server {
    location / {
        proxy_pass http://backend;
    }
}
```

- 若採 Kubernetes，可用 Deployment + Service 自動擴展

#### 🔄 讀寫分離

- 主資料庫（Master）寫入，從資料庫（Slave）讀取
- 使用 MySQL Replication、Aurora Read Replica
- 可用 ShardingSphere / MyCat 實作路由

```java

@DS("slave")
public List<User> listUsers() {
    return userMapper.selectAll();
}
```

#### 🧠 Redis 快取熱資料

- 熱門資料（如商品資訊）可快取提升效能
- 使用 Spring Cache 整合 Redis

```java

@Cacheable(value = "product", key = "#id")
public Product getProduct(Long id) {
    return productRepository.findById(id).orElse(null);
}
```

#### 📩 非同步處理

- 使用 RabbitMQ / Kafka 執行非同步任務（寄信、推播）
- 可降低請求延遲與系統耦合度

```java
rabbitTemplate.convertAndSend("mail-queue",emailDto);
```

---

### ✅ 系統設計核心觀念

#### 🧠 CAP 理論

- C（一致性）、A（可用性）、P（分區容錯）三擇二
- 常見取捨：
    - AP（如 Cassandra）
    - CP（如 Zookeeper）

#### ⚙️ CQRS 模式

- 將 Command（寫入）與 Query（查詢）分離

```java
orderService.createOrder(orderDto);

List<OrderDto> orders = orderQueryService.findOrdersByUser(userId);
```

- 查詢資料可放入 Elasticsearch / Redis 提升效能

#### 🔒 分散式鎖

- 解決多節點競爭同一資源問題（如搶購）
- Redisson 實作範例：

```java
RLock lock = redissonClient.getLock("create_order_lock");
if(lock.

tryLock(10,5,TimeUnit.SECONDS)){
        try{
        // 關鍵邏輯
        }finally{
        lock.

unlock();
    }
            }
```

---

### ✅ 系統分層與高併發設計

- 系統路徑：API Gateway → Service → DB / Redis
- 採用六邊形架構提升可測試性與解耦性

---

### ✅ 快取策略設計

- 本地快取 + 分散式快取並用
- 策略包含：TTL、LRU、Cache Aside、Write-Through、Write-Behind

---

### ✅ 高可用架構實務

- 多實例部署 + Nginx / ALB / Istio 負載平衡
- Session 存 Redis 達成無狀態
- 健康檢查：Kubernetes probes

---

### ✅ Log 與 Trace 系統建設

- 使用 ELK 處理應用日誌
- 使用 Zipkin / Jaeger 追蹤分散式鏈路

---

### ✅ 指標監控與告警

- Prometheus + Grafana 監控 CPU、記憶體、TPS、錯誤率
- 使用 Alertmanager 發送告警（Email、Slack）

---

### ✅ 系統韌性設計（Resilience）

- 使用 Resilience4j / Hystrix 管控失敗

```java

@Retry(name = "inventory")
@CircuitBreaker(name = "inventory", fallbackMethod = "fallback")
public String getInventory() {
    return restTemplate.getForObject("http://inventory", String.class);
}
```

- 功能包含：
    - 重試
    - 熔斷
    - 降級
    - 限流

---

### ✅ 模組化與微服務設計

- 使用 DDD 定義模組邊界（限界上下文）
- 通訊協定：REST、gRPC、Kafka
- 微服務註冊發現（Eureka、Consul）
- 配置中心：Nacos、Spring Cloud Config

---

### ✅ 簡易事件驅動架構應用

- Kafka 應用：生產者 / 消費者
- 熟悉 offset 控制、順序處理、冪等性

---

### ✅ 部署流程最佳化

- 開發流程：GitFlow 或 trunk-based
- 自動化工具：GitHub Actions、GitLab CI、Argo CD

---

這些能力代表中級工程師能在高併發、高可用場景下設計出可擴展、可監控、具韌性的穩定系統。