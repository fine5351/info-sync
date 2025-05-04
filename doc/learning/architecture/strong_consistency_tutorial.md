# 強一致性教學

## 初級（Beginner）層級

### 1. 概念說明
強一致性就像是在教室裡傳遞訊息：
- 老師要確保每個同學都收到相同的訊息
- 必須等所有同學都收到訊息後，才能進行下一步
- 如果有同學沒收到訊息，就要重新傳送

@startuml
title 強一致性基本概念
participant "老師" as Teacher
participant "同學A" as StudentA
participant "同學B" as StudentB
participant "同學C" as StudentC

Teacher -> StudentA: 傳送訊息
Teacher -> StudentB: 傳送訊息
Teacher -> StudentC: 傳送訊息
StudentA --> Teacher: 收到確認
StudentB --> Teacher: 收到確認
StudentC --> Teacher: 收到確認
Teacher -> Teacher: 確認所有同學都收到
@enduml

### 2. 使用原因
為什麼需要強一致性？
1. 確保資料正確：
   - 就像考試成績，每個同學都要有正確的分數
   - 不能讓不同同學看到不同的成績
   - 避免資料錯誤造成的問題

2. 重要系統需要：
   - 線上購物系統
   - 遊戲點數系統
   - 學校成績系統

### 3. 問題表象
可能遇到的問題：
1. 速度變慢：
   - 系統反應變慢
   - 等待時間變長
   - 電腦資源使用增加

2. 系統問題：
   - 系統卡住
   - 無法使用
   - 電腦當機

### 4. 避免方法
如何避免問題：
1. 系統設計：
   - 選擇合適的系統架構
   - 使用正確的工具
   - 做好錯誤處理

2. 日常維護：
   - 定期檢查系統
   - 監控系統狀態
   - 及時處理問題

### 5. 實作範例
```java
// 簡單的成績系統
public class GradeSystem {
    private Map<String, Integer> grades = new HashMap<>();
    
    // 更新成績
    public synchronized void updateGrade(String studentId, int grade) {
        grades.put(studentId, grade);
    }
    
    // 取得成績
    public synchronized int getGrade(String studentId) {
        return grades.getOrDefault(studentId, 0);
    }
}
```

## 中級（Intermediate）層級

### 1. 概念說明
中級強一致性就像班級幹部管理班級事務：
- 班長需要協調各幹部的工作
- 確保所有幹部都完成任務
- 處理可能出現的問題

@startuml
title 中級強一致性架構
class "班長" as Monitor {
    +協調工作()
    +檢查進度()
    +處理問題()
}

class "風紀股長" as Discipline {
    +管理秩序()
    +回報狀況()
}

class "學藝股長" as Study {
    +管理作業()
    +回報狀況()
}

Monitor --> Discipline
Monitor --> Study
@enduml

### 2. 使用原因
為什麼需要進階的強一致性？
1. 系統更複雜：
   - 多個部分需要協調
   - 資料需要同步更新
   - 錯誤處理更複雜

2. 業務需求：
   - 線上購物系統的庫存管理
   - 遊戲伺服器的玩家資料同步
   - 學校系統的成績統計

### 3. 實作範例
```java
// 使用 Redis 實現分布式鎖
public class DistributedLock {
    private final RedisTemplate<String, String> redisTemplate;
    
    public boolean tryLock(String key, String value, long timeout) {
        return redisTemplate.opsForValue()
            .setIfAbsent(key, value, Duration.ofSeconds(timeout));
    }
    
    public void unlock(String key, String value) {
        if (value.equals(redisTemplate.opsForValue().get(key))) {
            redisTemplate.delete(key);
        }
    }
}

// 使用鎖來保護共享資源
public class SharedResource {
    private final DistributedLock lock;
    private int count = 0;
    
    public void increment() {
        String lockKey = "resource:lock";
        String lockValue = UUID.randomUUID().toString();
        
        try {
            if (lock.tryLock(lockKey, lockValue, 10)) {
                count++;
            }
        } finally {
            lock.unlock(lockKey, lockValue);
        }
    }
}
```

## 高級（Advanced）層級

### 1. 概念說明
高級強一致性就像學校的行政系統：
- 多個部門需要協同工作
- 資料需要在不同系統間同步
- 需要處理各種異常情況
- 確保系統的可靠性和可用性

@startuml
title 高級強一致性架構
participant "前端系統" as Frontend
participant "應用服務" as App
participant "資料庫" as DB
participant "備份系統" as Backup

Frontend -> App: 請求資料
App -> DB: 讀取資料
DB --> App: 返回資料
App --> Frontend: 返回結果

App -> Backup: 同步資料
Backup --> App: 確認同步
@enduml

### 2. 使用原因
為什麼需要高級強一致性？
1. 系統可靠性：
   - 確保系統24小時運作
   - 資料不會遺失
   - 系統故障時能快速恢復

2. 業務需求：
   - 金融交易系統
   - 大型電商平台
   - 雲端服務系統

### 3. 實作範例
```java
// 使用 Spring 實現事務管理
@Transactional
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    
    public void transfer(TransferRequest request) {
        // 開始事務
        TransactionStatus status = transactionManager.getTransaction(
            new DefaultTransactionDefinition());
        
        try {
            // 扣款
            Account fromAccount = accountRepository.findById(
                request.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException());
            fromAccount.debit(request.getAmount());
            accountRepository.save(fromAccount);
            
            // 存款
            Account toAccount = accountRepository.findById(
                request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException());
            toAccount.credit(request.getAmount());
            accountRepository.save(toAccount);
            
            // 記錄交易
            Transaction transaction = new Transaction(request);
            transactionRepository.save(transaction);
            
            // 提交事務
            transactionManager.commit(status);
        } catch (Exception e) {
            // 回滾事務
            transactionManager.rollback(status);
            throw e;
        }
    }
}
```

### 4. 最佳實踐
1. 使用成熟的框架：
   - Spring Framework
   - Hibernate
   - Redis

2. 實現監控機制：
   - 系統健康檢查
   - 效能監控
   - 錯誤追蹤

3. 設計備份策略：
   - 定期備份
   - 異地備份
   - 災難恢復演練 