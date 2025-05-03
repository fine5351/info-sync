# 正規化-反正規化教學

## 初級（Beginner）層級

### 1. 概念說明
正規化-反正規化就像是在整理學校的成績單：
- 正規化：把重複的資料分開存放，就像把每個學生的成績分開記錄
- 反正規化：把相關的資料放在一起，方便快速查詢，就像把一個學生的所有科目成績放在同一張成績單上

初級學習者需要了解：
- 什麼是正規化
- 什麼是反正規化
- 基本的資料組織概念

### 2. PlantUML 圖解
```plantuml
@startuml
class Student {
    - id: int
    - name: String
    - grade: int
    + getInfo()
}

class Score {
    - studentId: int
    - subject: String
    - score: int
    + getScore()
}

Student "1" -- "n" Score
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：基本資料結構
```java
public class Student {
    private int id;
    private String name;
    private int grade;
    
    public Student(int id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }
    
    public String getInfo() {
        return "學號：" + id + " 姓名：" + name + " 年級：" + grade;
    }
}

public class Score {
    private int studentId;
    private String subject;
    private int score;
    
    public Score(int studentId, String subject, int score) {
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
    }
    
    public String getScore() {
        return "科目：" + subject + " 分數：" + score;
    }
}
```

#### 步驟 2：資料查詢
```java
public class GradeSystem {
    private List<Student> students;
    private List<Score> scores;
    
    public GradeSystem() {
        students = new ArrayList<>();
        scores = new ArrayList<>();
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void addScore(Score score) {
        scores.add(score);
    }
    
    public void printStudentScores(int studentId) {
        Student student = findStudent(studentId);
        if (student != null) {
            System.out.println(student.getInfo());
            for (Score score : scores) {
                if (score.getStudentId() == studentId) {
                    System.out.println(score.getScore());
                }
            }
        }
    }
}
```

## 中級（Intermediate）層級

### 1. 概念說明
中級學習者需要理解：
- 正規化的實現方式
- 資料關聯
- 查詢優化
- 資料一致性

### 2. PlantUML 圖解
```plantuml
@startuml
class Student {
    - id: int
    - name: String
    - grade: int
    + getInfo()
}

class Subject {
    - id: int
    - name: String
    - teacher: String
    + getInfo()
}

class Score {
    - studentId: int
    - subjectId: int
    - score: int
    + getScore()
}

Student "1" -- "n" Score
Subject "1" -- "n" Score
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：正規化設計
```java
public class Subject {
    private int id;
    private String name;
    private String teacher;
    
    public Subject(int id, String name, String teacher) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
    }
    
    public String getInfo() {
        return "科目：" + name + " 教師：" + teacher;
    }
}

public class NormalizedScore {
    private int studentId;
    private int subjectId;
    private int score;
    
    public NormalizedScore(int studentId, int subjectId, int score) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.score = score;
    }
    
    public String getScore(Subject subject) {
        return subject.getInfo() + " 分數：" + score;
    }
}
```

#### 步驟 2：資料關聯
```java
public class GradeManager {
    private Map<Integer, Student> students;
    private Map<Integer, Subject> subjects;
    private List<NormalizedScore> scores;
    
    public GradeManager() {
        students = new HashMap<>();
        subjects = new HashMap<>();
        scores = new ArrayList<>();
    }
    
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }
    
    public void addSubject(Subject subject) {
        subjects.put(subject.getId(), subject);
    }
    
    public void addScore(NormalizedScore score) {
        scores.add(score);
    }
    
    public void printStudentReport(int studentId) {
        Student student = students.get(studentId);
        if (student != null) {
            System.out.println(student.getInfo());
            for (NormalizedScore score : scores) {
                if (score.getStudentId() == studentId) {
                    Subject subject = subjects.get(score.getSubjectId());
                    System.out.println(score.getScore(subject));
                }
            }
        }
    }
}
```

## 高級（Advanced）層級

### 1. 概念說明
高級學習者需要掌握：
- 反正規化的進階應用
- 查詢效能優化
- 資料同步
- 快取機制

### 2. PlantUML 圖解
```plantuml
@startuml
package "進階成績系統" {
    class Student {
        - id: int
        - name: String
        - grade: int
        + getInfo()
    }
    
    class DenormalizedScore {
        - studentId: int
        - studentName: String
        - subject: String
        - score: int
        + getScore()
    }
    
    class CacheManager {
        - cache: Map
        + get()
        + put()
    }
    
    class SyncManager {
        - source: DataSource
        - target: DataSource
        + sync()
    }
}

Student --> DenormalizedScore
DenormalizedScore --> CacheManager
CacheManager --> SyncManager
@enduml
```

### 3. 分段教學步驟

#### 步驟 1：反正規化設計
```java
public class DenormalizedScore {
    private int studentId;
    private String studentName;
    private String subject;
    private int score;
    
    public DenormalizedScore(int studentId, String studentName, String subject, int score) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subject = subject;
        this.score = score;
    }
    
    public String getScore() {
        return "學生：" + studentName + " 科目：" + subject + " 分數：" + score;
    }
}
```

#### 步驟 2：快取管理
```java
public class CacheManager {
    private Map<String, DenormalizedScore> cache;
    
    public CacheManager() {
        cache = new HashMap<>();
    }
    
    public DenormalizedScore getScore(String key) {
        return cache.get(key);
    }
    
    public void putScore(String key, DenormalizedScore score) {
        cache.put(key, score);
    }
    
    public void clearCache() {
        cache.clear();
    }
}
```

#### 步驟 3：資料同步
```java
public class SyncManager {
    private DataSource source;
    private DataSource target;
    private CacheManager cache;
    
    public SyncManager(DataSource source, DataSource target) {
        this.source = source;
        this.target = target;
        this.cache = new CacheManager();
    }
    
    public void syncData() {
        // 從正規化資料源讀取
        List<NormalizedScore> normalizedScores = source.getScores();
        
        // 轉換為反正規化格式
        for (NormalizedScore score : normalizedScores) {
            DenormalizedScore denormalized = convertToDenormalized(score);
            
            // 更新快取
            cache.putScore(generateKey(denormalized), denormalized);
            
            // 同步到目標資料源
            target.updateScore(denormalized);
        }
    }
    
    private DenormalizedScore convertToDenormalized(NormalizedScore score) {
        // 轉換邏輯
        return new DenormalizedScore(
            score.getStudentId(),
            source.getStudentName(score.getStudentId()),
            source.getSubjectName(score.getSubjectId()),
            score.getScore()
        );
    }
}
```

這個教學文件提供了從基礎到進階的正規化-反正規化學習路徑，每個層級都包含了相應的概念說明、圖解、教學步驟和實作範例。初級學習者可以從基本的資料結構開始，中級學習者可以學習正規化設計和資料關聯，而高級學習者則可以掌握反正規化設計和資料同步等進階功能。 