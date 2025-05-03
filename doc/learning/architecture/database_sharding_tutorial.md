# 資料庫分庫分表教學

## 初級（Beginner）層級

### 1. 概念說明
分庫分表就像學校的圖書館：
- 如果所有書都放在一個書架上，找書會很慢
- 我們可以把書分成不同類別，放在不同區域
- 這樣找書會更快，管理也更容易

初級學習者需要了解：
- 什麼是分庫分表
- 為什麼需要分庫分表
- 基本的資料分割概念

### 2. PlantUML 圖解
```plantuml
@startuml
class Table {
    - name: String
    - data: List<Object>
    + getName()
    + addData()
    + getData()
}

class ShardedTable {
    - tables: List<Table>
    + addData()
    + getData()
}

ShardedTable --> Table
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：基本分表實現
```yaml
# application-sharding.yaml
spring:
  shardingsphere:
    datasource:
      names: ds0,ds1
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/db0
        username: root
        password: root
      ds1:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/db1
        username: root
        password: root
    sharding:
      tables:
        t_order:
          actual-data-nodes: ds$->{0..1}.t_order$->{0..1}
          table-strategy:
            inline:
              sharding-column: order_id
              algorithm-expression: t_order$->{order_id % 2}
          key-generator:
            column: order_id
            type: SNOWFLAKE
```

```java
// 實體類
@Data
@Table(name = "t_order")
public class Order {
    @Id
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "status")
    private String status;
}

// 數據訪問層
@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void save(Order order) {
        String sql = "INSERT INTO t_order (order_id, user_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, order.getOrderId(), order.getUserId(), order.getStatus());
    }
    
    public Order findById(Long orderId) {
        String sql = "SELECT * FROM t_order WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, 
            (rs, rowNum) -> {
                Order order = new Order();
                order.setOrderId(rs.getLong("order_id"));
                order.setUserId(rs.getLong("user_id"));
                order.setStatus(rs.getString("status"));
                return order;
            });
    }
}
```

### 4. 配置說明

#### Maven 依賴配置
```xml
<dependencies>
    <dependency>
        <groupId>org.apache.shardingsphere</groupId>
        <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
        <version>5.3.2</version>
    </dependency>
    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
        <version>4.0.3</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
```

## 中級（Intermediate）層級

### 1. 概念說明
中級學習者需要理解：
- ShardingSphere 的架構和組件
- 分片策略和算法
- 分佈式事務
- 讀寫分離

### 2. PlantUML 圖解
```plantuml
@startuml
class ShardingSphere {
    - shardingDataSource: ShardingDataSource
    - shardingRule: ShardingRule
    + executeQuery()
    + executeUpdate()
}

class ShardingRule {
    - tableRules: List<TableRule>
    - bindingTableRules: List<BindingTableRule>
    + getTableRule()
    + getBindingTableRule()
}

class TableRule {
    - logicTable: String
    - actualDataNodes: List<String>
    - tableShardingStrategy: ShardingStrategy
    + getActualDataNodes()
    + getTableShardingStrategy()
}

ShardingSphere --> ShardingRule
ShardingRule --> TableRule
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：分片策略配置
```yaml
# application-sharding.yaml
spring:
  shardingsphere:
    sharding:
      tables:
        t_order:
          actual-data-nodes: ds$->{0..1}.t_order$->{0..1}
          database-strategy:
            inline:
              sharding-column: user_id
              algorithm-expression: ds$->{user_id % 2}
          table-strategy:
            inline:
              sharding-column: order_id
              algorithm-expression: t_order$->{order_id % 2}
          key-generator:
            column: order_id
            type: SNOWFLAKE
```

#### 步驟 2：讀寫分離配置
```yaml
# application-sharding.yaml
spring:
  shardingsphere:
    datasource:
      names: master,slave0,slave1
      master:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/master
        username: root
        password: root
      slave0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/slave0
        username: root
        password: root
      slave1:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/slave1
        username: root
        password: root
    masterslave:
      name: ms
      master-data-source-name: master
      slave-data-source-names: slave0,slave1
      load-balance-algorithm-type: ROUND_ROBIN
```

## 高級（Advanced）層級

### 1. 概念說明
高級學習者需要掌握：
- 分佈式事務
- 數據加密
- 數據脫敏
- 監控和治理

### 2. PlantUML 圖解
```plantuml
@startuml
package "進階 ShardingSphere 系統" {
    class DistributedTransaction {
        - transactionManager: TransactionManager
        + begin()
        + commit()
        + rollback()
    }
    
    class EncryptRule {
        - encryptors: Map<String, Encryptor>
        - tables: Map<String, EncryptTable>
        + encrypt()
        + decrypt()
    }
    
    class Governance {
        - registryCenter: RegistryCenter
        - configCenter: ConfigCenter
        + register()
        + updateConfig()
    }
}

DistributedTransaction --> Governance
EncryptRule --> Governance
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：分佈式事務配置
```yaml
# application-sharding.yaml
spring:
  shardingsphere:
    transaction:
      type: XA
      props:
        defaultTimeout: 60
        maxTimeout: 60
        rollbackTimeout: 30
```

```java
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    @ShardingTransactionType(TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order order) {
        orderRepository.save(order);
        // 其他業務邏輯
    }
}
```

#### 步驟 2：數據加密配置
```yaml
# application-sharding.yaml
spring:
  shardingsphere:
    encrypt:
      encryptors:
        aes_encryptor:
          type: AES
          props:
            aes-key-value: 123456
      tables:
        t_order:
          columns:
            user_id:
              plainColumn: user_id
              cipherColumn: user_id_cipher
              encryptor: aes_encryptor
```

#### 步驟 3：數據脫敏配置
```yaml
# application-sharding.yaml
spring:
  shardingsphere:
    encrypt:
      encryptors:
        mobile_encryptor:
          type: MASK
          props:
            mask-char: '*'
            mask-length: 4
      tables:
        t_order:
          columns:
            mobile:
              plainColumn: mobile
              cipherColumn: mobile_cipher
              encryptor: mobile_encryptor
```

### 4. 進階配置

#### 監控配置（使用 Prometheus）
```yaml
# application-sharding.yaml
spring:
  shardingsphere:
    metrics:
      enabled: true
      name: prometheus
      host: 127.0.0.1
      port: 9190
      props:
        jvm-information: true
```

#### 治理配置
```yaml
# application-sharding.yaml
spring:
  shardingsphere:
    governance:
      name: governance_ds
      registry-center:
        type: ZOOKEEPER
        server-lists: localhost:2181
        namespace: governance_ds
      overwrite: false
```

這個擴展提供了動態縮容擴容的分庫分表方案，主要包含以下功能：

1. 動態監控：通過 `ShardMetrics` 類收集分片的使用情況
2. 自動擴容：當分片使用率超過閾值時，自動創建新的分片並重新平衡資料
3. 自動縮容：當分片使用率低於閾值時，自動將資料遷移到其他分片並移除該分片
4. 平滑遷移：在擴縮容過程中確保資料的完整性和一致性

使用這個方案，系統可以根據實際負載情況自動調整分片數量，實現更好的資源利用和性能優化。

這個教學文件提供了從基礎到進階的資料庫分庫分表學習路徑，每個層級都包含了相應的概念說明、圖解、教學步驟和實作範例。初級學習者可以從基本的資料分割開始，中級學習者可以學習分片策略和路由機制，而高級學習者則可以掌握分散式分片、一致性雜湊和跨分片事務等進階功能。 