# 可用性-容錯轉移教學

## 初級（Beginner）層級

### 1. 概念說明
可用性-容錯轉移就像是在學校裡，當班級電腦壞掉時：
- 可以立即使用備用電腦繼續上課
- 不會因為一台電腦壞掉就影響整堂課
- 系統會自動切換到備用設備，保持課程正常進行

初級學習者需要了解：
- 什麼是容錯轉移
- 為什麼需要容錯轉移
- 基本的備用系統概念

### 2. PlantUML 圖解
```plantuml
@startuml
class Computer {
    - name: String
    - isWorking: boolean
    + use()
    + checkStatus()
}

class BackupComputer {
    - name: String
    + takeOver()
    + start()
}

Computer --> BackupComputer : 故障時切換
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：基本電腦系統
```java
public class SimpleComputerSystem {
    private Computer mainComputer;
    private Computer backupComputer;
    
    public SimpleComputerSystem() {
        mainComputer = new Computer("主電腦");
        backupComputer = new Computer("備用電腦");
    }
    
    public void useComputer() {
        if (mainComputer.isWorking()) {
            mainComputer.use();
        } else {
            System.out.println("主電腦故障，切換到備用電腦");
            backupComputer.takeOver();
        }
    }
}

class Computer {
    private String name;
    private boolean isWorking;
    
    public Computer(String name) {
        this.name = name;
        this.isWorking = true;
    }
    
    public void use() {
        System.out.println("使用 " + name);
    }
    
    public boolean isWorking() {
        return isWorking;
    }
    
    public void takeOver() {
        System.out.println(name + " 接管工作");
        isWorking = true;
    }
}
```

#### 步驟 2：簡單的故障檢測
```java
public class FaultDetector {
    private Computer computer;
    
    public void checkComputer() {
        if (!computer.isWorking()) {
            System.out.println("檢測到電腦故障");
            // 觸發故障處理
            handleFault();
        }
    }
    
    private void handleFault() {
        System.out.println("開始處理故障");
        // 這裡可以加入更多的故障處理邏輯
    }
}
```

## 中級（Intermediate）層級

### 1. 概念說明
中級學習者需要理解：
- 容錯轉移的實現方式
- 故障檢測機制
- 狀態同步
- 自動切換策略

### 2. PlantUML 圖解
```plantuml
@startuml
class FailoverManager {
    - nodes: List
    + monitor()
    + switchOver()
}

class HealthChecker {
    - checks: List
    + checkHealth()
    + reportStatus()
}

class StateSynchronizer {
    - states: Map
    + sync()
    + verify()
}

FailoverManager --> HealthChecker
FailoverManager --> StateSynchronizer
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：健康檢查
```java
import java.util.*;

public class HealthChecker {
    private List<HealthCheck> checks;
    private Map<String, Boolean> nodeStatus;
    
    public HealthChecker() {
        checks = new ArrayList<>();
        nodeStatus = new HashMap<>();
    }
    
    public void addCheck(HealthCheck check) {
        checks.add(check);
    }
    
    public boolean checkHealth(String nodeId) {
        boolean isHealthy = true;
        for (HealthCheck check : checks) {
            if (!check.perform(nodeId)) {
                isHealthy = false;
                break;
            }
        }
        nodeStatus.put(nodeId, isHealthy);
        return isHealthy;
    }
}

interface HealthCheck {
    boolean perform(String nodeId);
}

class PingCheck implements HealthCheck {
    @Override
    public boolean perform(String nodeId) {
        // 模擬 ping 檢查
        return Math.random() > 0.1; // 90% 的成功率
    }
}
```

#### 步驟 2：狀態同步
```java
public class StateSynchronizer {
    private Map<String, NodeState> states;
    
    public void syncState(String nodeId, NodeState state) {
        // 同步節點狀態
        states.put(nodeId, state);
        
        // 驗證同步結果
        verifySync(nodeId);
    }
    
    private void verifySync(String nodeId) {
        NodeState state = states.get(nodeId);
        if (state != null && state.isValid()) {
            System.out.println("節點 " + nodeId + " 狀態同步成功");
        } else {
            System.out.println("節點 " + nodeId + " 狀態同步失敗");
        }
    }
}

class NodeState {
    private String data;
    private long timestamp;
    
    public boolean isValid() {
        return data != null && !data.isEmpty();
    }
}
```

## 高級（Advanced）層級

### 1. 概念說明
高級學習者需要掌握：
- 分散式容錯轉移
- 自動故障恢復
- 負載平衡
- 系統監控與優化

### 2. PlantUML 圖解
```plantuml
@startuml
package "進階容錯系統" {
    class DistributedFailover {
        - nodes: List
        + coordinate()
        + recover()
    }
    
    class AutoRecovery {
        - strategies: Map
        + detect()
        + recover()
    }
    
    class LoadBalancer {
        - metrics: Map
        + balance()
        + optimize()
    }
    
    class SystemMonitor {
        - alerts: List
        + monitor()
        + alert()
    }
}

DistributedFailover --> AutoRecovery
AutoRecovery --> LoadBalancer
LoadBalancer --> SystemMonitor
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：自動故障恢復
```java
import java.util.*;

public class AutoRecovery {
    private Map<String, RecoveryStrategy> strategies;
    private List<Node> nodes;
    
    public void handleFailure(String nodeId) {
        // 檢測故障
        if (detectFailure(nodeId)) {
            // 選擇恢復策略
            RecoveryStrategy strategy = selectStrategy(nodeId);
            
            // 執行恢復
            strategy.recover(nodeId);
            
            // 驗證恢復結果
            verifyRecovery(nodeId);
        }
    }
    
    private boolean detectFailure(String nodeId) {
        Node node = findNode(nodeId);
        return node != null && !node.isHealthy();
    }
    
    private RecoveryStrategy selectStrategy(String nodeId) {
        return strategies.getOrDefault(nodeId, new DefaultRecoveryStrategy());
    }
}

interface RecoveryStrategy {
    void recover(String nodeId);
    boolean verify(String nodeId);
}
```

#### 步驟 2：負載平衡
```java
public class LoadBalancer {
    private List<Node> nodes;
    private Map<String, LoadMetric> metrics;
    
    public void balanceLoad() {
        // 收集負載指標
        collectMetrics();
        
        // 分析負載情況
        analyzeLoad();
        
        // 執行負載平衡
        performBalancing();
    }
    
    private void analyzeLoad() {
        for (Node node : nodes) {
            LoadMetric metric = metrics.get(node.getId());
            if (needsBalancing(metric)) {
                // 觸發負載平衡
                triggerBalancing(node);
            }
        }
    }
}

class LoadMetric {
    private String nodeId;
    private double cpuUsage;
    private double memoryUsage;
    private int connectionCount;
    
    public LoadMetric(String nodeId) {
        this.nodeId = nodeId;
    }
    
    public boolean isOverloaded() {
        return cpuUsage > 80 || memoryUsage > 80 || connectionCount > 1000;
    }
}
```

#### 步驟 3：系統監控
```java
public class SystemMonitor {
    private List<Alert> alerts;
    private Map<String, MonitorMetric> metrics;
    
    public void monitorSystem() {
        // 收集監控指標
        collectMetrics();
        
        // 檢查系統狀態
        checkStatus();
        
        // 處理警報
        handleAlerts();
    }
    
    private void checkStatus() {
        for (MonitorMetric metric : metrics.values()) {
            if (metric.isAbnormal()) {
                // 產生警報
                generateAlert(metric);
            }
        }
    }
}

class MonitorMetric {
    private String name;
    private double value;
    private double threshold;
    
    public boolean isAbnormal() {
        return value > threshold;
    }
}
```

這個教學文件提供了從基礎到進階的可用性-容錯轉移學習路徑，每個層級都包含了相應的概念說明、圖解、教學步驟和實作範例。初級學習者可以從基本的電腦系統開始，中級學習者可以學習健康檢查和狀態同步，而高級學習者則可以掌握自動故障恢復和負載平衡等進階功能。 