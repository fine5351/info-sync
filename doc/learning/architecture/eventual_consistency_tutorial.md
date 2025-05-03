# 最終一致性教學

## 初級（Beginner）層級

### 1. 概念說明
最終一致性就像是在學校裡，當老師要更新班級成績時：
- 不需要立即讓所有同學都看到最新的成績
- 允許短暫的時間差，最終所有同學都會看到相同的成績
- 系統可以繼續運作，不會因為等待所有同學都看到成績而停下來

初級學習者需要了解：
- 什麼是最終一致性
- 為什麼需要最終一致性
- 基本的資料更新方式

### 2. PlantUML 圖解
```plantuml
@startuml
class GradeSystem {
    - grades: Map
    + updateGrade()
    + getGrade()
}

class Student {
    - name: String
    + checkGrade()
}

GradeSystem --> Student : 更新
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：基本成績系統
```java
public class SimpleGradeSystem {
    private Map<String, Integer> grades;
    
    public SimpleGradeSystem() {
        grades = new HashMap<>();
    }
    
    public void updateGrade(String studentId, int grade) {
        // 非同步更新成績
        new Thread(() -> {
            grades.put(studentId, grade);
            System.out.println("更新學生 " + studentId + " 的成績: " + grade);
        }).start();
    }
    
    public Integer getGrade(String studentId) {
        return grades.get(studentId);
    }
}
```

#### 步驟 2：簡單的成績查詢
```java
public class GradeChecker {
    private SimpleGradeSystem gradeSystem;
    
    public void checkGrade(String studentId) {
        // 嘗試讀取成績
        Integer grade = gradeSystem.getGrade(studentId);
        
        if (grade != null) {
            System.out.println("學生 " + studentId + " 的成績是: " + grade);
        } else {
            System.out.println("成績尚未更新");
        }
    }
}
```

## 中級（Intermediate）層級

### 1. 概念說明
中級學習者需要理解：
- 最終一致性的實現方式
- 資料同步機制
- 衝突解決策略
- 讀取優化

### 2. PlantUML 圖解
```plantuml
@startuml
class GradeManager {
    - nodes: List
    + synchronize()
    + resolveConflict()
}

class SyncManager {
    - strategies: Map
    + sync()
    + verify()
}

class ConflictHandler {
    - rules: List
    + resolve()
    + merge()
}

GradeManager --> SyncManager
GradeManager --> ConflictHandler
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：資料同步
```java
import java.util.*;

public class SyncManager {
    private List<GradeNode> nodes;
    private Map<String, SyncStrategy> strategies;
    
    public void synchronize(String studentId, int grade) {
        // 選擇同步策略
        SyncStrategy strategy = selectStrategy(studentId);
        
        // 執行同步
        strategy.sync(nodes, studentId, grade);
    }
    
    private SyncStrategy selectStrategy(String studentId) {
        return strategies.getOrDefault(studentId, new DefaultSyncStrategy());
    }
}

interface SyncStrategy {
    void sync(List<GradeNode> nodes, String studentId, int grade);
}

class DefaultSyncStrategy implements SyncStrategy {
    @Override
    public void sync(List<GradeNode> nodes, String studentId, int grade) {
        for (GradeNode node : nodes) {
            node.updateGradeAsync(studentId, grade);
        }
    }
}
```

#### 步驟 2：衝突處理
```java
public class ConflictHandler {
    private List<ConflictRule> rules;
    
    public void resolveConflict(String studentId, List<Integer> grades) {
        // 選擇衝突解決規則
        ConflictRule rule = selectRule(studentId);
        
        // 解決衝突
        int resolvedGrade = rule.resolve(grades);
        
        // 更新所有節點
        updateNodes(studentId, resolvedGrade);
    }
    
    private ConflictRule selectRule(String studentId) {
        return rules.stream()
            .filter(rule -> rule.isApplicable(studentId))
            .findFirst()
            .orElse(new DefaultConflictRule());
    }
}

interface ConflictRule {
    boolean isApplicable(String studentId);
    int resolve(List<Integer> grades);
}
```

## 高級（Advanced）層級

### 1. 概念說明
高級學習者需要掌握：
- 分散式系統設計
- 版本控制
- 讀取修復
- 寫入修復

### 2. PlantUML 圖解
```plantuml
@startuml
package "進階成績系統" {
    class DistributedGradeSystem {
        - nodes: List
        + coordinate()
        + synchronize()
    }
    
    class VersionManager {
        - versions: Map
        + compare()
        + merge()
    }
    
    class RepairManager {
        - strategies: Map
        + repair()
        + verify()
    }
    
    class SystemOptimizer {
        - metrics: Map
        + optimize()
        + tune()
    }
}

DistributedGradeSystem --> VersionManager
VersionManager --> RepairManager
RepairManager --> SystemOptimizer
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：版本控制
```java
import java.util.*;

public class VersionManager {
    private Map<String, Long> versions;
    
    public VersionManager() {
        versions = new HashMap<>();
    }
    
    public void increment(String studentId) {
        versions.merge(studentId, 1L, Long::sum);
    }
    
    public boolean isConcurrent(String studentId, long otherVersion) {
        long thisVersion = versions.getOrDefault(studentId, 0L);
        return thisVersion > otherVersion;
    }
    
    public void merge(String studentId, long otherVersion) {
        versions.merge(studentId, otherVersion, Math::max);
    }
}
```

#### 步驟 2：讀取修復
```java
public class ReadRepair {
    private DistributedGradeSystem system;
    private VersionManager versionManager;
    
    public int readWithRepair(String studentId) {
        // 從多個節點讀取
        List<GradeResult> results = readFromNodes(studentId);
        
        // 檢查一致性
        if (needsRepair(results)) {
            // 執行修復
            repairInconsistency(studentId, results);
        }
        
        // 返回最新值
        return getLatestGrade(results);
    }
    
    private List<GradeResult> readFromNodes(String studentId) {
        List<GradeResult> results = new ArrayList<>();
        for (GradeNode node : system.getNodes()) {
            results.add(node.readGrade(studentId));
        }
        return results;
    }
    
    private boolean needsRepair(List<GradeResult> results) {
        return results.stream()
            .map(GradeResult::getGrade)
            .distinct()
            .count() > 1;
    }
}

class GradeResult {
    private int grade;
    private long version;
    
    public GradeResult(int grade, long version) {
        this.grade = grade;
        this.version = version;
    }
    
    public int getGrade() {
        return grade;
    }
}
```

#### 步驟 3：寫入修復
```java
public class WriteRepair {
    private DistributedGradeSystem system;
    private VersionManager versionManager;
    
    public void writeWithRepair(String studentId, int grade) {
        // 寫入到多個節點
        List<WriteResult> results = writeToNodes(studentId, grade);
        
        // 檢查寫入結果
        if (needsRepair(results)) {
            // 執行修復
            repairInconsistency(studentId, grade, results);
        }
    }
    
    private List<WriteResult> writeToNodes(String studentId, int grade) {
        List<WriteResult> results = new ArrayList<>();
        for (GradeNode node : system.getNodes()) {
            results.add(node.writeGrade(studentId, grade));
        }
        return results;
    }
    
    private boolean needsRepair(List<WriteResult> results) {
        return results.stream()
            .anyMatch(result -> !result.isSuccess());
    }
}

class WriteResult {
    private boolean success;
    private long version;
    
    public WriteResult(boolean success, long version) {
        this.success = success;
        this.version = version;
    }
    
    public boolean isSuccess() {
        return success;
    }
}
```

這個教學文件提供了從基礎到進階的最終一致性學習路徑，每個層級都包含了相應的概念說明、圖解、教學步驟和實作範例。初級學習者可以從基本的成績系統開始，中級學習者可以學習資料同步和衝突處理，而高級學習者則可以掌握版本控制和修復機制等進階功能。 