# MariaDB 教學文件

## 目錄

1. [概述](#概述)
2. [入門級教學](#入門級教學)
    - [什麼是 MariaDB](#什麼是-mariadb)
    - [MariaDB 的基本架構](#mariadb-的基本架構)
    - [安裝 MariaDB](#安裝-mariadb)
    - [連接到 MariaDB](#連接到-mariadb)
    - [資料庫基本概念](#資料庫基本概念)
    - [資料類型](#資料類型)
    - [欄位設定](#欄位設定)
    - [字元編碼](#字元編碼)
    - [建立第一個資料庫](#建立第一個資料庫)
    - [建立資料表](#建立資料表)
    - [基本 SQL 指令](#基本-sql-指令)
    - [入門練習](#入門練習)
3. [進階教學](#進階教學)
    - [資料庫設計原則](#資料庫設計原則)
    - [索引設計](#索引設計)
    - [關聯式資料表](#關聯式資料表)
    - [交易處理](#交易處理)
    - [資料完整性](#資料完整性)
    - [使用者管理與權限](#使用者管理與權限)
    - [資料匯入匯出](#資料匯入匯出)
    - [常見查詢優化](#常見查詢優化)
    - [進階 SQL 技巧](#進階-sql-技巧)
    - [進階練習](#進階練習)
4. [高級教學](#高級教學)
    - [效能調優](#效能調優)
    - [問題排查](#問題排查)
    - [資料庫監控](#資料庫監控)
    - [資料同步](#資料同步)
    - [主從架構](#主從架構)
    - [資料備份與恢復](#資料備份與恢復)
    - [高可用性配置](#高可用性配置)
    - [資料庫安全性](#資料庫安全性)
    - [大規模資料處理](#大規模資料處理)
    - [高級案例研究](#高級案例研究)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 MariaDB 資料庫系統。無論您是完全沒有資料庫經驗的初學者，還是已經了解基礎功能的進階學習者，或是想要深入了解效能調優和問題排查的高級使用者，本文檔都能為您提供所需的知識和技能。

MariaDB 是一個開源的關聯式資料庫管理系統，是 MySQL 的一個分支。它被設計為完全兼容 MySQL，同時提供更多功能和改進。通過學習 MariaDB，您可以了解如何有效地儲存、管理和查詢資料，為您的應用程式提供強大的資料支持。

## 入門級教學

本節適合完全沒有 MariaDB 或資料庫經驗的初學者。我們將從最基本的概念開始，逐步建立您對 MariaDB 的理解。

### 什麼是 MariaDB

MariaDB 是一個開源的關聯式資料庫管理系統 (RDBMS)，由 MySQL 的原始開發者創建。它是 MySQL 的一個分支，旨在保持與 MySQL 的兼容性，同時提供更多功能和改進。

想像一下，如果資料庫是一個大型的電子檔案櫃，那麼 MariaDB 就是管理這個檔案櫃的系統。它幫助您組織、儲存、檢索和管理資料，就像檔案櫃幫助您組織、儲存、檢索和管理文件一樣。

MariaDB 的主要特點包括：

1. **開源**：MariaDB 是開源的，這意味著它是免費的，並且有一個活躍的社區不斷改進它。
2. **兼容 MySQL**：MariaDB 設計為與 MySQL 兼容，這意味著如果您熟悉 MySQL，您也會熟悉 MariaDB。
3. **高性能**：MariaDB 提供高性能的資料處理能力，能夠處理大量的資料和查詢。
4. **可靠性**：MariaDB 提供多種功能來確保資料的安全和可靠性，如交易處理、資料備份等。
5. **多功能**：MariaDB 支持多種資料類型、索引類型和查詢語言，能夠滿足各種應用需求。

### MariaDB 的基本架構

MariaDB 的基本架構包括以下幾個主要部分：

1. **伺服器**：MariaDB 伺服器是運行 MariaDB 的核心程式，負責處理客戶端的請求、執行 SQL 查詢、管理資料等。

2. **客戶端**：MariaDB 客戶端是用來連接 MariaDB 伺服器的程式，如命令行工具 (mysql)、圖形界面工具 (MySQL Workbench, HeidiSQL) 等。

3. **資料庫**：資料庫是資料的集合，一個 MariaDB 伺服器可以管理多個資料庫。

4. **資料表**：資料表是資料庫中的資料結構，用來儲存特定類型的資料。一個資料庫可以包含多個資料表。

5. **欄位**：欄位是資料表中的一列，代表資料的一個屬性。例如，在一個學生資料表中，可能有姓名、年齡、班級等欄位。

6. **記錄**：記錄是資料表中的一行，代表一個完整的資料項。例如，在一個學生資料表中，一個記錄代表一個學生的所有資料。

7. **索引**：索引是用來加速資料查詢的資料結構，類似於書籍的索引。

8. **儲存引擎**：儲存引擎是 MariaDB 用來儲存、處理和檢索資料的軟體模組。MariaDB 支持多種儲存引擎，如 InnoDB、MyISAM 等，每種引擎有不同的特性和適用場景。

### 安裝 MariaDB

安裝 MariaDB 的方法取決於您的操作系統。以下是在常見操作系統上安裝 MariaDB 的基本步驟：

#### Windows

1. 訪問 MariaDB 官方網站 (https://mariadb.org/download/) 下載 Windows 版本的安裝程式。
2. 運行下載的安裝程式，按照安裝嚮導的指示進行安裝。
3. 在安裝過程中，您需要設定 root 用戶的密碼，這是最高權限的用戶，請記住這個密碼。
4. 完成安裝後，MariaDB 服務會自動啟動。

#### Linux (Ubuntu/Debian)

1. 打開終端機，運行以下命令更新套件列表：
   ```bash
   sudo apt update
   ```

2. 安裝 MariaDB 伺服器：
   ```bash
   sudo apt install mariadb-server
   ```

3. 啟動 MariaDB 服務：
   ```bash
   sudo systemctl start mariadb
   ```

4. 設定 MariaDB 在系統啟動時自動啟動：
   ```bash
   sudo systemctl enable mariadb
   ```

5. 運行安全安裝腳本，設定 root 密碼和其他安全選項：
   ```bash
   sudo mysql_secure_installation
   ```

#### macOS

1. 使用 Homebrew 安裝 MariaDB：
   ```bash
   brew install mariadb
   ```

2. 啟動 MariaDB 服務：
   ```bash
   brew services start mariadb
   ```

### 連接到 MariaDB

安裝完 MariaDB 後，您可以使用命令行工具或圖形界面工具連接到 MariaDB 伺服器。

#### 使用命令行工具

1. 打開終端機或命令提示符。

2. 使用以下命令連接到 MariaDB：
   ```bash
   mysql -u root -p
   ```

3. 輸入您在安裝時設定的 root 密碼。

4. 如果連接成功，您會看到 MariaDB 的歡迎訊息和命令提示符 `MariaDB [(none)]>`。

#### 使用圖形界面工具

您也可以使用圖形界面工具如 MySQL Workbench、HeidiSQL 或 phpMyAdmin 連接到 MariaDB。這些工具提供了更友好的界面，適合初學者使用。

### 資料庫基本概念

在開始使用 MariaDB 之前，讓我們了解一些基本的資料庫概念：

#### 資料庫 (Database)

資料庫是資料的集合，一個 MariaDB 伺服器可以管理多個資料庫。每個資料庫通常用於一個特定的應用或項目。

例如，一個學校可能有一個資料庫用於管理學生資料，另一個資料庫用於管理教師資料，還有一個資料庫用於管理課程資料。

#### 資料表 (Table)

資料表是資料庫中的資料結構，用來儲存特定類型的資料。一個資料庫可以包含多個資料表。

例如，在一個學生資料庫中，可能有一個學生資料表用於儲存學生的基本資料，一個成績資料表用於儲存學生的成績，還有一個班級資料表用於儲存班級資料。

#### 欄位 (Column)

欄位是資料表中的一列，代表資料的一個屬性。每個欄位都有一個名稱和一個資料類型。

例如，在一個學生資料表中，可能有姓名、年齡、班級等欄位。姓名欄位的資料類型可能是字串，年齡欄位的資料類型可能是整數，班級欄位的資料類型可能是字串。

#### 記錄 (Record)

記錄是資料表中的一行，代表一個完整的資料項。

例如，在一個學生資料表中，一個記錄代表一個學生的所有資料，包括姓名、年齡、班級等。

#### 主鍵 (Primary Key)

主鍵是資料表中的一個或多個欄位，用來唯一標識每個記錄。主鍵的值在資料表中必須是唯一的，且不能為空。

例如，在一個學生資料表中，學號可以作為主鍵，因為每個學生都有一個唯一的學號。

#### 外鍵 (Foreign Key)

外鍵是資料表中的一個欄位，它引用另一個資料表的主鍵。外鍵用於建立資料表之間的關聯。

例如，在一個成績資料表中，學號可以作為外鍵，引用學生資料表的學號（主鍵）。這樣，成績資料表中的每個記錄都與學生資料表中的一個記錄相關聯。

#### 索引 (Index)

索引是用來加速資料查詢的資料結構，類似於書籍的索引。索引可以建立在一個或多個欄位上。

例如，如果您經常按照學生的姓名查詢學生資料，您可以在姓名欄位上建立索引，這樣查詢速度會更快。

### 資料類型

MariaDB 支持多種資料類型，用於定義欄位可以儲存的資料類型。以下是一些常見的資料類型：

#### 數值類型

1. **INT**：整數，範圍從 -2,147,483,648 到 2,147,483,647。
2. **TINYINT**：小整數，範圍從 -128 到 127。
3. **SMALLINT**：小整數，範圍從 -32,768 到 32,767。
4. **MEDIUMINT**：中整數，範圍從 -8,388,608 到 8,388,607。
5. **BIGINT**：大整數，範圍從 -9,223,372,036,854,775,808 到 9,223,372,036,854,775,807。
6. **FLOAT**：單精度浮點數。
7. **DOUBLE**：雙精度浮點數。
8. **DECIMAL**：定點數，用於需要精確小數計算的場景。

#### 字串類型

1. **CHAR**：固定長度的字串，最大長度為 255 個字元。
2. **VARCHAR**：可變長度的字串，最大長度為 65,535 個字元。
3. **TEXT**：長文本，最大長度為 65,535 個字元。
4. **MEDIUMTEXT**：中長文本，最大長度為 16,777,215 個字元。
5. **LONGTEXT**：長文本，最大長度為 4,294,967,295 個字元。

#### 日期和時間類型

1. **DATE**：日期，格式為 'YYYY-MM-DD'。
2. **TIME**：時間，格式為 'HH:MM:SS'。
3. **DATETIME**：日期和時間，格式為 'YYYY-MM-DD HH:MM:SS'。
4. **TIMESTAMP**：時間戳，範圍從 '1970-01-01 00:00:01' UTC 到 '2038-01-19 03:14:07' UTC。
5. **YEAR**：年份，範圍從 1901 到 2155。

#### 二進制類型

1. **BINARY**：固定長度的二進制字串。
2. **VARBINARY**：可變長度的二進制字串。
3. **BLOB**：二進制大對象，用於儲存大量二進制資料。
4. **MEDIUMBLOB**：中型二進制大對象。
5. **LONGBLOB**：大型二進制大對象。

#### 其他類型

1. **ENUM**：枚舉類型，可以從一組預定義的值中選擇一個。
2. **SET**：集合類型，可以從一組預定義的值中選擇零個或多個。
3. **JSON**：JSON 資料類型，用於儲存 JSON 格式的資料。

### 欄位設定

在創建資料表時，除了指定欄位的名稱和資料類型外，還可以設定其他屬性：

#### NOT NULL

指定欄位不能為空值。

```sql
CREATE TABLE students (
    id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    age INT
);
```

在這個例子中，`id` 和 `name` 欄位不能為空，而 `age` 欄位可以為空。

#### DEFAULT

指定欄位的默認值，當插入記錄時如果沒有提供該欄位的值，則使用默認值。

```sql
CREATE TABLE students (
    id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    age INT DEFAULT 18
);
```

在這個例子中，如果插入記錄時沒有提供 `age` 的值，則使用默認值 18。

#### AUTO_INCREMENT

指定欄位的值自動增加，通常用於主鍵。

```sql
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT DEFAULT 18,
    PRIMARY KEY (id)
);
```

在這個例子中，`id` 欄位的值會自動增加，每次插入新記錄時，`id` 的值會比上一次插入的記錄的 `id` 值大 1。

#### UNIQUE

指定欄位的值在資料表中必須是唯一的。

```sql
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT DEFAULT 18,
    PRIMARY KEY (id)
);
```

在這個例子中，`email` 欄位的值在資料表中必須是唯一的，不能有兩個學生使用相同的電子郵件地址。

#### PRIMARY KEY

指定欄位為主鍵，主鍵的值在資料表中必須是唯一的，且不能為空。

```sql
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT DEFAULT 18,
    PRIMARY KEY (id)
);
```

在這個例子中，`id` 欄位被指定為主鍵。

#### FOREIGN KEY

指定欄位為外鍵，引用另一個資料表的主鍵。

```sql
CREATE TABLE scores (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    course VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

在這個例子中，`scores` 資料表的 `student_id` 欄位被指定為外鍵，引用 `students` 資料表的 `id` 欄位（主鍵）。

### 字元編碼

字元編碼是用來表示字元的方式，不同的字元編碼支持不同的字元集。在 MariaDB 中，常用的字元編碼包括：

1. **UTF-8**：支持多種語言的字元，包括中文、日文、韓文等。
2. **Latin1**：支持西歐語言的字元。
3. **ASCII**：支持基本的英文字元。

在創建資料庫和資料表時，可以指定字元編碼：

```sql
-- 創建資料庫時指定字元編碼
CREATE DATABASE mydb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 創建資料表時指定字元編碼
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

在這個例子中，我們使用 `utf8mb4` 字元編碼和 `utf8mb4_unicode_ci` 排序規則，這是支持完整 Unicode 字元集的編碼，包括表情符號。

### 建立第一個資料庫

現在，讓我們建立第一個資料庫：

1. 連接到 MariaDB：
   ```bash
   mysql -u root -p
   ```

2. 創建一個新的資料庫：
   ```sql
   CREATE DATABASE mydb;
   ```

3. 使用這個資料庫：
   ```sql
   USE mydb;
   ```

4. 查看當前選擇的資料庫：
   ```sql
   SELECT DATABASE();
   ```

5. 查看所有資料庫：
   ```sql
   SHOW DATABASES;
   ```

### 建立資料表

現在，讓我們在剛剛創建的資料庫中建立一個資料表：

1. 確保您已經選擇了正確的資料庫：
   ```sql
   USE mydb;
   ```

2. 創建一個學生資料表：
   ```sql
   CREATE TABLE students (
       id INT NOT NULL AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) UNIQUE,
       age INT DEFAULT 18,
       grade VARCHAR(10),
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (id)
   );
   ```

3. 查看資料表結構：
   ```sql
   DESCRIBE students;
   ```

4. 查看所有資料表：
   ```sql
   SHOW TABLES;
   ```

### 基本 SQL 指令

SQL (Structured Query Language) 是用來管理關聯式資料庫的標準語言。以下是一些基本的 SQL 指令：

#### SELECT

用於從資料庫中查詢資料。

```sql
-- 查詢 students 資料表中的所有記錄
SELECT * FROM students;

-- 查詢 students 資料表中的特定欄位
SELECT name, email FROM students;

-- 使用 WHERE 子句過濾記錄
SELECT * FROM students WHERE age > 18;

-- 使用 ORDER BY 子句排序記錄
SELECT * FROM students ORDER BY name ASC;

-- 使用 LIMIT 子句限制結果數量
SELECT * FROM students LIMIT 10;
```

#### INSERT

用於向資料表中插入新記錄。

```sql
-- 插入一條記錄
INSERT INTO students (name, email, age, grade) VALUES ('張三', 'zhangsan@example.com', 20, 'A');

-- 插入多條記錄
INSERT INTO students (name, email, age, grade) VALUES 
('李四', 'lisi@example.com', 19, 'B'),
('王五', 'wangwu@example.com', 21, 'A');
```

#### UPDATE

用於更新資料表中的記錄。

```sql
-- 更新一條記錄
UPDATE students SET age = 21 WHERE id = 1;

-- 更新多條記錄
UPDATE students SET grade = 'A' WHERE age > 20;
```

#### DELETE

用於刪除資料表中的記錄。

```sql
-- 刪除一條記錄
DELETE FROM students WHERE id = 1;

-- 刪除多條記錄
DELETE FROM students WHERE age < 18;
```

#### ALTER TABLE

用於修改資料表的結構。

```sql
-- 添加新欄位
ALTER TABLE students ADD COLUMN address VARCHAR(200);

-- 修改欄位的資料類型
ALTER TABLE students MODIFY COLUMN grade VARCHAR(20);

-- 刪除欄位
ALTER TABLE students DROP COLUMN address;
```

#### DROP

用於刪除資料庫或資料表。

```sql
-- 刪除資料表
DROP TABLE students;

-- 刪除資料庫
DROP DATABASE mydb;
```

### 入門練習

讓我們通過一個簡單的練習來鞏固所學的知識：

1. 創建一個名為 `school` 的資料庫：
   ```sql
   CREATE DATABASE school CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE school;
   ```

2. 創建一個 `students` 資料表：
   ```sql
   CREATE TABLE students (
       id INT NOT NULL AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) UNIQUE,
       age INT DEFAULT 18,
       grade VARCHAR(10),
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (id)
   );
   ```

3. 創建一個 `courses` 資料表：
   ```sql
   CREATE TABLE courses (
       id INT NOT NULL AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       teacher VARCHAR(100) NOT NULL,
       credits INT NOT NULL,
       PRIMARY KEY (id)
   );
   ```

4. 創建一個 `enrollments` 資料表，用於記錄學生選課資料：
   ```sql
   CREATE TABLE enrollments (
       id INT NOT NULL AUTO_INCREMENT,
       student_id INT NOT NULL,
       course_id INT NOT NULL,
       enrollment_date DATE NOT NULL,
       PRIMARY KEY (id),
       FOREIGN KEY (student_id) REFERENCES students(id),
       FOREIGN KEY (course_id) REFERENCES courses(id)
   );
   ```

5. 向 `students` 資料表插入一些資料：
   ```sql
   INSERT INTO students (name, email, age, grade) VALUES 
   ('張三', 'zhangsan@example.com', 20, 'A'),
   ('李四', 'lisi@example.com', 19, 'B'),
   ('王五', 'wangwu@example.com', 21, 'A');
   ```

6. 向 `courses` 資料表插入一些資料：
   ```sql
   INSERT INTO courses (name, teacher, credits) VALUES 
   ('數學', '陳老師', 3),
   ('英語', '李老師', 2),
   ('歷史', '王老師', 2);
   ```

7. 向 `enrollments` 資料表插入一些資料：
   ```sql
   INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES 
   (1, 1, '2023-09-01'),
   (1, 2, '2023-09-01'),
   (2, 1, '2023-09-02'),
   (3, 3, '2023-09-03');
   ```

8. 查詢每個學生選修的課程：
   ```sql
   SELECT s.name AS student_name, c.name AS course_name, c.teacher, e.enrollment_date
   FROM students s
   JOIN enrollments e ON s.id = e.student_id
   JOIN courses c ON e.course_id = c.id
   ORDER BY s.name, c.name;
   ```

這個練習涵蓋了創建資料庫、創建資料表、插入資料和查詢資料等基本操作，幫助您熟悉 MariaDB 的基本使用。

## 進階教學

本節適合已經了解 MariaDB 基礎知識的學習者。我們將探討更進階的概念和技巧，幫助您設計更好的資料庫和編寫更高效的查詢。

### 資料庫設計原則

良好的資料庫設計是建立高效、可靠和可維護的資料庫系統的基礎。以下是一些資料庫設計的基本原則：

#### 1. 正規化 (Normalization)

正規化是一種減少資料冗餘和提高資料完整性的過程。它通過將大的資料表分解為小的資料表，並建立它們之間的關聯來實現。

正規化有幾個級別，常見的有：

- **第一正規形式 (1NF)**：確保每個欄位都包含原子值（不可再分的值）。
- **第二正規形式 (2NF)**：滿足 1NF，並且所有非主鍵欄位都完全依賴於主鍵。
- **第三正規形式 (3NF)**：滿足 2NF，並且所有非主鍵欄位都不依賴於其他非主鍵欄位。

例如，考慮一個包含學生資料和課程資料的資料表：

```
| 學號 | 學生姓名 | 課程編號 | 課程名稱 | 教師 | 成績 |
|------|----------|----------|----------|------|------|
| 1    | 張三     | 101      | 數學     | 陳老師| 90   |
| 1    | 張三     | 102      | 英語     | 李老師| 85   |
| 2    | 李四     | 101      | 數學     | 陳老師| 78   |
```

這個資料表有幾個問題：

- 學生姓名重複儲存，如果學生姓名需要更新，需要更新多條記錄。
- 課程名稱和教師資料重複儲存，浪費空間。

我們可以將其分解為三個資料表：

學生資料表：

```
| 學號 | 學生姓名 |
|------|----------|
| 1    | 張三     |
| 2    | 李四     |
```

課程資料表：

```
| 課程編號 | 課程名稱 | 教師 |
|----------|----------|------|
| 101      | 數學     | 陳老師|
| 102      | 英語     | 李老師|
```

成績資料表：

```
| 學號 | 課程編號 | 成績 |
|------|----------|------|
| 1    | 101      | 90   |
| 1    | 102      | 85   |
| 2    | 101      | 78   |
```

這樣，每個資料表都只包含相關的資料，減少了冗餘，提高了資料完整性。

#### 2. 使用適當的資料類型

選擇適當的資料類型可以節省空間、提高性能並確保資料的準確性。例如：

- 使用 `INT` 而不是 `VARCHAR` 來儲存數字。
- 使用 `DATE` 或 `DATETIME` 而不是 `VARCHAR` 來儲存日期和時間。
- 使用 `ENUM` 或 `SET` 來儲存有限的選項值。

#### 3. 使用索引

索引可以加速資料查詢，但會減慢資料插入、更新和刪除的速度。應該在經常用於查詢條件的欄位上建立索引。

```sql
-- 在 students 資料表的 name 欄位上建立索引
CREATE INDEX idx_name ON students(name);
```

#### 4. 使用外鍵約束

外鍵約束可以確保資料的參照完整性，防止出現孤立的記錄。

```sql
CREATE TABLE scores (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    course VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);
```

在這個例子中，如果刪除了 `students` 資料表中的一個學生，相關的成績記錄也會被自動刪除。

#### 5. 避免過度正規化

雖然正規化可以減少資料冗餘，但過度正規化可能導致查詢變得複雜，需要連接多個資料表。在某些情況下，適當的冗餘可以提高查詢性能。

### 索引設計

索引是資料庫中用來加速查詢的資料結構。合理的索引設計可以大幅提高查詢性能，但不當的索引設計可能會適得其反。

#### 索引的類型

MariaDB 支持多種類型的索引：

1. **普通索引**：最基本的索引類型，沒有唯一性的限制。
   ```sql
   CREATE INDEX idx_name ON students(name);
   ```

2. **唯一索引**：確保索引欄位的值是唯一的。
   ```sql
   CREATE UNIQUE INDEX idx_email ON students(email);
   ```

3. **主鍵索引**：特殊的唯一索引，用於標識資料表中的每一行。
   ```sql
   CREATE TABLE students (
       id INT NOT NULL AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       PRIMARY KEY (id)
   );
   ```

4. **複合索引**：包含多個欄位的索引。
   ```sql
   CREATE INDEX idx_name_age ON students(name, age);
   ```

5. **全文索引**：用於全文搜索。
   ```sql
   CREATE FULLTEXT INDEX idx_content ON articles(content);
   ```

#### 索引設計原則

1. **在經常用於查詢條件的欄位上建立索引**：
   如果您經常按照學生的姓名查詢學生資料，您可以在姓名欄位上建立索引。

2. **在經常用於排序的欄位上建立索引**：
   如果您經常按照學生的年齡排序查詢結果，您可以在年齡欄位上建立索引。

3. **在經常用於連接的欄位上建立索引**：
   如果您經常連接學生資料表和成績資料表，您可以在學生資料表的 id 欄位和成績資料表的 student_id 欄位上建立索引。

4. **避免在頻繁更新的欄位上建立索引**：
   索引會減慢資料插入、更新和刪除的速度。

5. **避免在基數低的欄位上建立索引**：
   基數是指欄位中不同值的數量。例如，性別欄位的基數通常很低（可能只有「男」和「女」兩個值），在這樣的欄位上建立索引通常不會提高查詢性能。

6. **定期維護索引**：
   隨著資料的變化，索引可能會變得碎片化，影響性能。定期重建索引可以提高性能。
   ```sql
   OPTIMIZE TABLE students;
   ```

### 關聯式資料表

關聯式資料表是通過外鍵關聯起來的資料表。它們允許您將相關的資料分散到不同的資料表中，減少冗餘，提高資料完整性。

#### 一對一關聯

一對一關聯是指一個資料表中的一行與另一個資料表中的一行相關聯。例如，一個學生只有一個學生證，一個學生證只屬於一個學生。

```sql
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE student_cards (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL UNIQUE,
    card_number VARCHAR(20) NOT NULL,
    issue_date DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

在這個例子中，`student_cards` 資料表的 `student_id` 欄位是一個外鍵，引用 `students` 資料表的 `id` 欄位。`UNIQUE` 約束確保一個學生只有一個學生證。

#### 一對多關聯

一對多關聯是指一個資料表中的一行與另一個資料表中的多行相關聯。例如，一個學生可以有多個成績，但每個成績只屬於一個學生。

```sql
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE scores (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    course VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

在這個例子中，`scores` 資料表的 `student_id` 欄位是一個外鍵，引用 `students` 資料表的 `id` 欄位。一個學生可以有多個成績記錄。

#### 多對多關聯

多對多關聯是指一個資料表中的一行與另一個資料表中的多行相關聯，反之亦然。例如，一個學生可以選修多門課程，一門課程可以被多個學生選修。

多對多關聯通常通過一個中間資料表來實現：

```sql
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE courses (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    teacher VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE enrollments (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);
```

在這個例子中，`enrollments` 資料表是一個中間資料表，它的 `student_id` 欄位引用 `students` 資料表的 `id` 欄位，`course_id` 欄位引用 `courses` 資料表的 `id` 欄位。這樣，一個學生可以選修多門課程，一門課程可以被多個學生選修。

### 交易處理

交易是一組操作，它們要麼全部成功，要麼全部失敗。交易處理確保資料庫的一致性，即使在系統故障的情況下也是如此。

#### 交易的 ACID 特性

交易具有以下四個特性，通常被稱為 ACID 特性：

1. **原子性 (Atomicity)**：交易中的所有操作要麼全部成功，要麼全部失敗。
2. **一致性 (Consistency)**：交易將資料庫從一個一致的狀態轉變為另一個一致的狀態。
3. **隔離性 (Isolation)**：交易的執行不受其他交易的干擾。
4. **持久性 (Durability)**：一旦交易提交，其結果就是永久的，即使系統故障也不會丟失。

#### 交易控制語句

MariaDB 提供了以下語句來控制交易：

1. **START TRANSACTION**：開始一個新的交易。
2. **COMMIT**：提交交易，使所有的變更永久生效。
3. **ROLLBACK**：回滾交易，取消所有的變更。
4. **SAVEPOINT**：在交易中創建一個保存點，可以回滾到該點。
5. **ROLLBACK TO SAVEPOINT**：回滾到指定的保存點。

以下是一個使用交易的例子：

```sql
-- 開始交易
START TRANSACTION;

-- 從帳戶 A 中扣除 100 元
UPDATE accounts SET balance = balance - 100 WHERE id = 1;

-- 向帳戶 B 中添加 100 元
UPDATE accounts SET balance = balance + 100 WHERE id = 2;

-- 如果兩個操作都成功，提交交易
COMMIT;

-- 如果出現錯誤，回滾交易
-- ROLLBACK;
```

在這個例子中，我們使用交易來確保資金轉移的原子性：要麼兩個帳戶的餘額都更新，要麼都不更新。

#### 交易隔離級別

MariaDB 支持以下四種交易隔離級別：

1. **READ UNCOMMITTED**：最低的隔離級別，一個交易可以讀取另一個未提交交易的變更。
2. **READ COMMITTED**：一個交易只能讀取已提交的變更。
3. **REPEATABLE READ**：在同一個交易中，多次讀取同一數據會得到相同的結果。
4. **SERIALIZABLE**：最高的隔離級別，完全隔離，交易按順序執行。

隔離級別越高，並發性能越低，但資料一致性越高。您可以根據應用的需求選擇適當的隔離級別。

```sql
-- 設定交易隔離級別
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
```

### 資料完整性

資料完整性是指資料的準確性和一致性。MariaDB 提供了多種機制來確保資料完整性：

#### 實體完整性

實體完整性確保每個記錄都是唯一的。主鍵約束是實現實體完整性的主要方式。

```sql
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
```

在這個例子中，`id` 欄位被指定為主鍵，確保每個學生記錄都是唯一的。

#### 參照完整性

參照完整性確保資料表之間的關聯是有效的。外鍵約束是實現參照完整性的主要方式。

```sql
CREATE TABLE scores (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    course VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
```

在這個例子中，`scores` 資料表的 `student_id` 欄位被指定為外鍵，引用 `students` 資料表的 `id` 欄位。這確保了每個成績記錄都與一個有效的學生相關聯。

#### 域完整性

域完整性確保欄位的值符合定義的類型和約束。NOT NULL 約束、CHECK 約束和默認值是實現域完整性的主要方式。

```sql
CREATE TABLE students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT CHECK (age > 0 AND age < 150),
    gender ENUM('男', '女', '其他') NOT NULL,
    grade CHAR(1) DEFAULT 'C',
    PRIMARY KEY (id)
);
```

在這個例子中：

- `name` 欄位不能為空。
- `age` 欄位的值必須大於 0 且小於 150。
- `gender` 欄位的值必須是 '男'、'女' 或 '其他' 之一。
- `grade` 欄位的默認值是 'C'。

### 使用者管理與權限

MariaDB 提供了一個強大的權限系統，允許您控制誰可以訪問資料庫以及他們可以執行哪些操作。

#### 創建使用者

您可以使用 `CREATE USER` 語句創建新的使用者：

```sql
-- 創建一個新使用者，可以從任何主機連接
CREATE USER 'username'@'%' IDENTIFIED BY 'password';

-- 創建一個新使用者，只能從本地主機連接
CREATE USER 'username'@'localhost' IDENTIFIED BY 'password';
```

#### 授予權限

您可以使用 `GRANT` 語句授予使用者權限：

```sql
-- 授予使用者對特定資料庫的所有權限
GRANT ALL PRIVILEGES ON database_name.* TO 'username'@'%';

-- 授予使用者對特定資料表的特定權限
GRANT SELECT, INSERT, UPDATE ON database_name.table_name TO 'username'@'%';

-- 授予使用者對所有資料庫的所有權限（超級使用者）
GRANT ALL PRIVILEGES ON *.* TO 'username'@'%' WITH GRANT OPTION;
```

`WITH GRANT OPTION` 允許使用者將自己的權限授予其他使用者。

#### 撤銷權限

您可以使用 `REVOKE` 語句撤銷使用者權限：

```sql
-- 撤銷使用者對特定資料庫的所有權限
REVOKE ALL PRIVILEGES ON database_name.* FROM 'username'@'%';

-- 撤銷使用者對特定資料表的特定權限
REVOKE SELECT, INSERT ON database_name.table_name FROM 'username'@'%';
```

#### 查看權限

您可以使用 `SHOW GRANTS` 語句查看使用者的權限：

```sql
-- 查看當前使用者的權限
SHOW GRANTS;

-- 查看特定使用者的權限
SHOW GRANTS FOR 'username'@'%';
```

#### 刪除使用者

您可以使用 `DROP USER` 語句刪除使用者：

```sql
DROP USER 'username'@'%';
```

### 資料匯入匯出

MariaDB 提供了多種工具和語句來匯入和匯出資料，方便資料的備份、恢復和遷移。

#### 使用 SQL 語句匯出資料

您可以使用 `SELECT ... INTO OUTFILE` 語句將查詢結果匯出到檔案：

```sql
-- 將查詢結果匯出到 CSV 檔案
SELECT * FROM students INTO OUTFILE '/tmp/students.csv'
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n';
```

#### 使用 SQL 語句匯入資料

您可以使用 `LOAD DATA INFILE` 語句從檔案匯入資料：

```sql
-- 從 CSV 檔案匯入資料
LOAD DATA INFILE '/tmp/students.csv'
INTO TABLE students
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n';
```

#### 使用 mysqldump 工具

mysqldump 是一個命令行工具，可以將整個資料庫或特定資料表的結構和資料匯出到 SQL 腳本檔案：

```bash
# 匯出整個資料庫
mysqldump -u username -p database_name > database_name.sql

# 匯出特定資料表
mysqldump -u username -p database_name table_name > table_name.sql

# 只匯出資料表結構，不包含資料
mysqldump -u username -p --no-data database_name > database_structure.sql
```

#### 使用 mysql 工具匯入 SQL 腳本

您可以使用 mysql 命令行工具執行 SQL 腳本檔案，將資料匯入到資料庫：

```bash
# 匯入 SQL 腳本
mysql -u username -p database_name < database_name.sql
```

#### 使用 CSV 檔案

CSV (Comma-Separated Values) 是一種常見的資料交換格式。您可以使用 MariaDB 的 `SELECT ... INTO OUTFILE` 和 `LOAD DATA INFILE` 語句來匯出和匯入 CSV 檔案。

```sql
-- 匯出 CSV 檔案
SELECT * FROM students INTO OUTFILE '/tmp/students.csv'
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n';

-- 匯入 CSV 檔案
LOAD DATA INFILE '/tmp/students.csv'
INTO TABLE students
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n';
```

### 常見查詢優化

查詢優化是提高資料庫性能的重要方面。以下是一些常見的查詢優化技巧：

#### 使用 EXPLAIN 分析查詢

`EXPLAIN` 語句可以幫助您了解 MariaDB 如何執行查詢，包括使用哪些索引、掃描多少行等。

```sql
EXPLAIN SELECT * FROM students WHERE name = '張三';
```

#### 優化 SELECT 語句

1. **只選擇需要的欄位**：
   ```sql
   -- 不好的做法
   SELECT * FROM students;

   -- 好的做法
   SELECT id, name FROM students;
   ```

2. **使用 WHERE 子句限制結果**：
   ```sql
   -- 不好的做法
   SELECT * FROM students;

   -- 好的做法
   SELECT * FROM students WHERE age > 18;
   ```

3. **使用 LIMIT 子句限制結果數量**：
   ```sql
   -- 不好的做法
   SELECT * FROM students;

   -- 好的做法
   SELECT * FROM students LIMIT 10;
   ```

4. **使用索引**：
   ```sql
   -- 創建索引
   CREATE INDEX idx_name ON students(name);

   -- 使用索引的查詢
   SELECT * FROM students WHERE name = '張三';
   ```

5. **避免使用 SELECT DISTINCT**：
   ```sql
   -- 不好的做法
   SELECT DISTINCT name FROM students;

   -- 好的做法
   SELECT name FROM students GROUP BY name;
   ```

6. **使用 JOIN 而不是子查詢**：
   ```sql
   -- 不好的做法
   SELECT * FROM students WHERE id IN (SELECT student_id FROM scores WHERE score > 90);

   -- 好的做法
   SELECT s.* FROM students s JOIN scores sc ON s.id = sc.student_id WHERE sc.score > 90;
   ```

#### 優化 INSERT 語句

1. **批量插入**：
   ```sql
   -- 不好的做法
   INSERT INTO students (name, age) VALUES ('張三', 20);
   INSERT INTO students (name, age) VALUES ('李四', 21);

   -- 好的做法
   INSERT INTO students (name, age) VALUES ('張三', 20), ('李四', 21);
   ```

2. **使用 INSERT IGNORE**：
   ```sql
   -- 忽略重複的記錄
   INSERT IGNORE INTO students (name, age) VALUES ('張三', 20);
   ```

3. **使用 REPLACE**：
   ```sql
   -- 如果記錄存在則替換，否則插入
   REPLACE INTO students (id, name, age) VALUES (1, '張三', 20);
   ```

#### 優化 UPDATE 和 DELETE 語句

1. **使用索引**：
   ```sql
   -- 創建索引
   CREATE INDEX idx_name ON students(name);

   -- 使用索引的更新
   UPDATE students SET age = 21 WHERE name = '張三';
   ```

2. **限制影響的行數**：
   ```sql
   -- 限制更新的行數
   UPDATE students SET age = 21 WHERE name = '張三' LIMIT 1;
   ```

3. **使用多表更新**：
   ```sql
   -- 基於另一個資料表的值更新
   UPDATE students s JOIN scores sc ON s.id = sc.student_id
   SET s.grade = 'A' WHERE sc.score > 90;
   ```

### 進階 SQL 技巧

以下是一些進階的 SQL 技巧，可以幫助您編寫更複雜、更高效的查詢：

#### 子查詢

子查詢是嵌套在另一個查詢中的查詢。它可以用在 SELECT、INSERT、UPDATE 和 DELETE 語句中。

```sql
-- 使用子查詢查找成績高於平均分的學生
SELECT * FROM students WHERE id IN (
    SELECT student_id FROM scores WHERE score > (
        SELECT AVG(score) FROM scores
    )
);
```

#### 公用表表達式 (CTE)

公用表表達式 (Common Table Expression, CTE) 是一種臨時結果集，可以在查詢中多次引用。

```sql
-- 使用 CTE 查找每個學生的平均成績
WITH student_avg_scores AS (
    SELECT student_id, AVG(score) AS avg_score
    FROM scores
    GROUP BY student_id
)
SELECT s.name, sas.avg_score
FROM students s
JOIN student_avg_scores sas ON s.id = sas.student_id
ORDER BY sas.avg_score DESC;
```

#### 窗口函數

窗口函數 (Window Function) 允許您對結果集的一個子集進行計算，而不需要使用 GROUP BY 子句。

```sql
-- 使用窗口函數計算每個學生的成績排名
SELECT s.name, sc.course, sc.score,
       RANK() OVER (PARTITION BY sc.course ORDER BY sc.score DESC) AS rank
FROM students s
JOIN scores sc ON s.id = sc.student_id;
```

#### 條件表達式

條件表達式允許您根據條件返回不同的值。MariaDB 支持 CASE 表達式和 IF 函數。

```sql
-- 使用 CASE 表達式根據成績分配等級
SELECT s.name, sc.course, sc.score,
       CASE
           WHEN sc.score >= 90 THEN 'A'
           WHEN sc.score >= 80 THEN 'B'
           WHEN sc.score >= 70 THEN 'C'
           WHEN sc.score >= 60 THEN 'D'
           ELSE 'F'
       END AS grade
FROM students s
JOIN scores sc ON s.id = sc.student_id;

-- 使用 IF 函數判斷是否及格
SELECT s.name, sc.course, sc.score,
       IF(sc.score >= 60, '及格', '不及格') AS result
FROM students s
JOIN scores sc ON s.id = sc.student_id;
```

#### 字串函數

MariaDB 提供了多種字串函數，用於處理字串資料。

```sql
-- 連接字串
SELECT CONCAT(name, ' (', age, '歲)') AS student_info FROM students;

-- 轉換大小寫
SELECT UPPER(name) AS upper_name, LOWER(name) AS lower_name FROM students;

-- 截取子字串
SELECT SUBSTRING(name, 1, 1) AS first_char FROM students;

-- 替換字串
SELECT REPLACE(name, '張', '王') AS new_name FROM students;
```

#### 日期和時間函數

MariaDB 提供了多種日期和時間函數，用於處理日期和時間資料。

```sql
-- 獲取當前日期和時間
SELECT NOW(), CURDATE(), CURTIME();

-- 提取日期部分
SELECT YEAR(created_at), MONTH(created_at), DAY(created_at) FROM students;

-- 日期計算
SELECT DATE_ADD(created_at, INTERVAL 1 DAY) AS tomorrow FROM students;
SELECT DATEDIFF(NOW(), created_at) AS days_since_creation FROM students;
```

### 進階練習

讓我們通過一個進階的練習來鞏固所學的知識：

1. 創建一個名為 `library` 的資料庫：
   ```sql
   CREATE DATABASE library CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE library;
   ```

2. 創建以下資料表：
    - `authors`：作者資料表
    - `books`：書籍資料表
    - `categories`：分類資料表
    - `book_categories`：書籍分類關聯資料表
    - `borrowers`：借閱者資料表
    - `loans`：借閱記錄資料表

   ```sql
   -- 創建作者資料表
   CREATE TABLE authors (
       id INT NOT NULL AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       birth_date DATE,
       nationality VARCHAR(50),
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (id)
   );

   -- 創建分類資料表
   CREATE TABLE categories (
       id INT NOT NULL AUTO_INCREMENT,
       name VARCHAR(50) NOT NULL UNIQUE,
       description TEXT,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (id)
   );

   -- 創建書籍資料表
   CREATE TABLE books (
       id INT NOT NULL AUTO_INCREMENT,
       title VARCHAR(200) NOT NULL,
       author_id INT NOT NULL,
       isbn VARCHAR(20) UNIQUE,
       publication_year INT,
       publisher VARCHAR(100),
       available_copies INT NOT NULL DEFAULT 0,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (id),
       FOREIGN KEY (author_id) REFERENCES authors(id)
   );

   -- 創建書籍分類關聯資料表
   CREATE TABLE book_categories (
       id INT NOT NULL AUTO_INCREMENT,
       book_id INT NOT NULL,
       category_id INT NOT NULL,
       PRIMARY KEY (id),
       UNIQUE KEY (book_id, category_id),
       FOREIGN KEY (book_id) REFERENCES books(id),
       FOREIGN KEY (category_id) REFERENCES categories(id)
   );

   -- 創建借閱者資料表
   CREATE TABLE borrowers (
       id INT NOT NULL AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) UNIQUE,
       phone VARCHAR(20),
       address TEXT,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (id)
   );

   -- 創建借閱記錄資料表
   CREATE TABLE loans (
       id INT NOT NULL AUTO_INCREMENT,
       book_id INT NOT NULL,
       borrower_id INT NOT NULL,
       loan_date DATE NOT NULL,
       due_date DATE NOT NULL,
       return_date DATE,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (id),
       FOREIGN KEY (book_id) REFERENCES books(id),
       FOREIGN KEY (borrower_id) REFERENCES borrowers(id)
   );
   ```

3. 插入一些測試資料：

   ```sql
   -- 插入作者資料
   INSERT INTO authors (name, birth_date, nationality) VALUES
   ('J.K. Rowling', '1965-07-31', '英國'),
   ('George Orwell', '1903-06-25', '英國'),
   ('魯迅', '1881-09-25', '中國'),
   ('村上春樹', '1949-01-12', '日本');

   -- 插入分類資料
   INSERT INTO categories (name, description) VALUES
   ('奇幻', '包含魔法、超自然元素的小說'),
   ('科幻', '基於科學和技術發展的小說'),
   ('文學', '具有文學價值的作品'),
   ('歷史', '關於歷史事件或時期的書籍');

   -- 插入書籍資料
   INSERT INTO books (title, author_id, isbn, publication_year, publisher, available_copies) VALUES
   ('哈利波特與魔法石', 1, '9789573317272', 1997, '皇冠出版', 5),
   ('1984', 2, '9789573267874', 1949, '時報出版', 3),
   ('吶喊', 3, '9789571428956', 1923, '麥田出版', 2),
   ('挪威的森林', 4, '9789571428963', 1987, '麥田出版', 4);

   -- 插入書籍分類關聯資料
   INSERT INTO book_categories (book_id, category_id) VALUES
   (1, 1), -- 哈利波特與魔法石 - 奇幻
   (2, 2), -- 1984 - 科幻
   (2, 3), -- 1984 - 文學
   (3, 3), -- 吶喊 - 文學
   (4, 3); -- 挪威的森林 - 文學

   -- 插入借閱者資料
   INSERT INTO borrowers (name, email, phone, address) VALUES
   ('張三', 'zhangsan@example.com', '0912345678', '台北市中正區重慶南路一段122號'),
   ('李四', 'lisi@example.com', '0923456789', '台北市大安區信義路三段134號'),
   ('王五', 'wangwu@example.com', '0934567890', '新北市板橋區文化路一段25號');

   -- 插入借閱記錄資料
   INSERT INTO loans (book_id, borrower_id, loan_date, due_date, return_date) VALUES
   (1, 1, '2023-09-01', '2023-09-15', '2023-09-10'),
   (2, 1, '2023-09-01', '2023-09-15', NULL),
   (3, 2, '2023-09-05', '2023-09-19', NULL),
   (4, 3, '2023-09-10', '2023-09-24', NULL);
   ```

4. 編寫一些進階查詢：

   ```sql
   -- 查詢每個作者的書籍數量
   SELECT a.name, COUNT(b.id) AS book_count
   FROM authors a
   LEFT JOIN books b ON a.id = b.author_id
   GROUP BY a.id
   ORDER BY book_count DESC;

   -- 查詢每個分類的書籍數量
   SELECT c.name, COUNT(bc.book_id) AS book_count
   FROM categories c
   LEFT JOIN book_categories bc ON c.id = bc.category_id
   GROUP BY c.id
   ORDER BY book_count DESC;

   -- 查詢目前借出但尚未歸還的書籍
   SELECT b.title, a.name AS author, br.name AS borrower, l.loan_date, l.due_date
   FROM loans l
   JOIN books b ON l.book_id = b.id
   JOIN authors a ON b.author_id = a.id
   JOIN borrowers br ON l.borrower_id = br.id
   WHERE l.return_date IS NULL
   ORDER BY l.due_date;

   -- 查詢逾期未還的書籍
   SELECT b.title, a.name AS author, br.name AS borrower, l.loan_date, l.due_date,
          DATEDIFF(CURDATE(), l.due_date) AS days_overdue
   FROM loans l
   JOIN books b ON l.book_id = b.id
   JOIN authors a ON b.author_id = a.id
   JOIN borrowers br ON l.borrower_id = br.id
   WHERE l.return_date IS NULL AND l.due_date < CURDATE()
   ORDER BY days_overdue DESC;

   -- 使用交易處理借書流程
   START TRANSACTION;

   -- 檢查書籍是否有可用副本
   SELECT available_copies FROM books WHERE id = 1 FOR UPDATE;

   -- 如果有可用副本，更新可用副本數量並創建借閱記錄
   UPDATE books SET available_copies = available_copies - 1 WHERE id = 1;
   INSERT INTO loans (book_id, borrower_id, loan_date, due_date) VALUES (1, 2, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY));

   -- 提交交易
   COMMIT;
   ```

這個進階練習涵蓋了資料庫設計、資料表關聯、交易處理、進階查詢等概念，幫助您熟悉 MariaDB 的進階使用。

## 高級教學

本節適合已經熟悉 MariaDB 基礎知識和進階概念的學習者。我們將探討更高級的主題，幫助您優化資料庫性能、排查問題、設計高可用性架構等。

### 效能調優

效能調優是確保資料庫系統高效運行的重要方面。以下是一些效能調優的技巧：

#### 查詢優化

1. **使用 EXPLAIN 分析查詢**：
   ```sql
   EXPLAIN SELECT * FROM books WHERE title LIKE '%哈利波特%';
   ```
   EXPLAIN 會顯示 MariaDB 如何執行查詢，包括使用哪些索引、掃描多少行等。

2. **優化索引**：
    - 為經常用於查詢條件的欄位建立索引
    - 為經常用於排序的欄位建立索引
    - 為經常用於連接的欄位建立索引
    - 移除不必要的索引

3. **重寫查詢**：
    - 避免使用 SELECT *，只選擇需要的欄位
    - 使用 JOIN 而不是子查詢
    - 使用 LIMIT 限制結果數量
    - 避免在 WHERE 子句中使用函數，這會阻止使用索引

#### 資料庫配置優化

1. **調整緩衝池大小**：
   緩衝池是 MariaDB 用來快取資料和索引的記憶體區域。增加緩衝池大小可以減少磁碟 I/O，提高性能。
   ```sql
   SET GLOBAL innodb_buffer_pool_size = 1073741824; -- 1GB
   ```

2. **調整連接池大小**：
   連接池控制 MariaDB 可以同時處理的連接數量。
   ```sql
   SET GLOBAL max_connections = 200;
   ```

3. **調整查詢快取**：
   查詢快取可以快取查詢結果，但在高並發環境中可能會導致性能問題。
   ```sql
   -- 啟用查詢快取
   SET GLOBAL query_cache_type = 1;
   SET GLOBAL query_cache_size = 67108864; -- 64MB

   -- 禁用查詢快取
   SET GLOBAL query_cache_type = 0;
   ```

4. **調整臨時表大小**：
   臨時表用於處理複雜查詢。增加臨時表大小可以提高複雜查詢的性能。
   ```sql
   SET GLOBAL tmp_table_size = 67108864; -- 64MB
   SET GLOBAL max_heap_table_size = 67108864; -- 64MB
   ```

#### 資料表優化

1. **選擇適當的儲存引擎**：
    - InnoDB：支持交易、行級鎖定、外鍵約束，適合大多數應用
    - MyISAM：不支持交易，但讀取性能好，適合讀多寫少的應用
    - Memory：資料存儲在記憶體中，速度快但不持久，適合臨時資料

2. **正規化和反正規化**：
    - 正規化可以減少資料冗餘，但可能導致查詢需要連接多個資料表
    - 反正規化可以提高查詢性能，但會增加資料冗餘和更新複雜性

3. **分區**：
   分區可以將大型資料表分成小塊，提高查詢性能。
   ```sql
   CREATE TABLE sales (
       id INT NOT NULL AUTO_INCREMENT,
       sale_date DATE NOT NULL,
       amount DECIMAL(10,2) NOT NULL,
       PRIMARY KEY (id, sale_date)
   )
   PARTITION BY RANGE (YEAR(sale_date)) (
       PARTITION p0 VALUES LESS THAN (2020),
       PARTITION p1 VALUES LESS THAN (2021),
       PARTITION p2 VALUES LESS THAN (2022),
       PARTITION p3 VALUES LESS THAN (2023),
       PARTITION p4 VALUES LESS THAN MAXVALUE
   );
   ```

4. **定期維護**：
    - 重建索引：`OPTIMIZE TABLE table_name;`
    - 更新統計資料：`ANALYZE TABLE table_name;`
    - 檢查和修復資料表：`CHECK TABLE table_name;` 和 `REPAIR TABLE table_name;`

### 問題排查

當資料庫出現問題時，有效的問題排查技巧可以幫助您快速找出並解決問題。

#### 常見問題及解決方法

1. **連接問題**：
    - 檢查 MariaDB 服務是否運行：`systemctl status mariadb`
    - 檢查連接設定：主機名、埠號、用戶名、密碼
    - 檢查防火牆設定：確保允許 MariaDB 埠號 (通常是 3306)
    - 檢查用戶權限：確保用戶有權從特定主機連接

2. **查詢緩慢**：
    - 使用 EXPLAIN 分析查詢
    - 檢查索引使用情況
    - 檢查資料表大小和結構
    - 檢查系統資源使用情況：CPU、記憶體、磁碟 I/O
    - 檢查慢查詢日誌

3. **資料庫崩潰**：
    - 檢查錯誤日誌：`/var/log/mysql/error.log`
    - 檢查系統日誌：`/var/log/syslog`
    - 檢查系統資源使用情況
    - 檢查資料庫配置

4. **資料損壞**：
    - 使用 CHECK TABLE 檢查資料表：`CHECK TABLE table_name;`
    - 使用 REPAIR TABLE 修復資料表：`REPAIR TABLE table_name;`
    - 從備份恢復資料

#### 使用日誌進行問題排查

MariaDB 提供了多種日誌，可以幫助您排查問題：

1. **錯誤日誌**：
   記錄 MariaDB 伺服器的啟動、關閉和錯誤訊息。
   ```sql
   SHOW VARIABLES LIKE 'log_error';
   ```

2. **慢查詢日誌**：
   記錄執行時間超過 `long_query_time` 的查詢。
   ```sql
   -- 啟用慢查詢日誌
   SET GLOBAL slow_query_log = 1;
   SET GLOBAL long_query_time = 1; -- 1秒
   SET GLOBAL slow_query_log_file = '/var/log/mysql/slow-query.log';

   -- 查看慢查詢日誌設定
   SHOW VARIABLES LIKE 'slow_query%';
   SHOW VARIABLES LIKE 'long_query_time';
   ```

3. **一般查詢日誌**：
   記錄所有查詢。在生產環境中通常不啟用，因為會產生大量日誌。
   ```sql
   -- 啟用一般查詢日誌
   SET GLOBAL general_log = 1;
   SET GLOBAL general_log_file = '/var/log/mysql/query.log';

   -- 查看一般查詢日誌設定
   SHOW VARIABLES LIKE 'general_log%';
   ```

4. **二進制日誌**：
   記錄所有修改資料的操作，用於複製和恢復。
   ```sql
   -- 查看二進制日誌設定
   SHOW VARIABLES LIKE 'log_bin%';

   -- 查看二進制日誌檔案
   SHOW BINARY LOGS;

   -- 查看二進制日誌內容
   SHOW BINLOG EVENTS IN 'binlog.000001';
   ```

#### 使用工具進行問題排查

除了 MariaDB 內建的功能外，還有一些工具可以幫助您排查問題：

1. **mysqltuner**：
   分析 MariaDB 的配置和性能，提供優化建議。
   ```bash
   perl mysqltuner.pl
   ```

2. **pt-query-digest**：
   分析慢查詢日誌，找出最耗時的查詢。
   ```bash
   pt-query-digest /var/log/mysql/slow-query.log
   ```

3. **mytop**：
   實時監控 MariaDB 的性能和活動。
   ```bash
   mytop -u username -p password
   ```

4. **innotop**：
   專門用於監控 InnoDB 儲存引擎的工具。
   ```bash
   innotop -u username -p password
   ```

### 資料庫監控

有效的資料庫監控可以幫助您及時發現並解決問題，確保資料庫系統的穩定運行。

#### 監控指標

以下是一些重要的監控指標：

1. **連接數**：
   監控當前連接數和最大連接數，避免連接數達到上限。
   ```sql
   SHOW STATUS LIKE 'Threads_connected';
   SHOW VARIABLES LIKE 'max_connections';
   ```

2. **查詢性能**：
   監控查詢執行時間、查詢快取命中率等。
   ```sql
   SHOW STATUS LIKE 'Slow_queries';
   SHOW STATUS LIKE 'Qcache_%';
   ```

3. **資源使用**：
   監控 CPU、記憶體、磁碟 I/O 等系統資源的使用情況。

4. **資料庫大小**：
   監控資料庫和資料表的大小，及時發現異常增長。
   ```sql
   SELECT table_schema, SUM(data_length + index_length) / 1024 / 1024 AS size_mb
   FROM information_schema.tables
   GROUP BY table_schema;
   ```

5. **複製狀態**：
   監控主從複製的狀態，確保資料同步。
   ```sql
   SHOW SLAVE STATUS\G
   ```

#### 監控工具

以下是一些常用的監控工具：

1. **Prometheus + Grafana**：
   Prometheus 用於收集和存儲監控數據，Grafana 用於可視化監控數據。

2. **Zabbix**：
   全面的監控解決方案，可以監控 MariaDB 和其他系統組件。

3. **Nagios**：
   經典的監控工具，可以通過插件監控 MariaDB。

4. **PMM (Percona Monitoring and Management)**：
   專門用於監控 MySQL 和 MariaDB 的工具。

#### 設置告警

設置告警可以在問題發生時及時通知管理員：

1. **連接數告警**：
   當連接數接近最大連接數時發出告警。

2. **慢查詢告警**：
   當慢查詢數量異常增加時發出告警。

3. **資源使用告警**：
   當 CPU、記憶體、磁碟使用率過高時發出告警。

4. **複製延遲告警**：
   當主從複製延遲超過閾值時發出告警。

### 資料同步

資料同步是指在多個資料庫實例之間保持資料一致性的過程。MariaDB 提供了多種資料同步機制。

#### 主從複製 (Master-Slave Replication)

主從複製是最常用的資料同步機制，它允許將一個 MariaDB 伺服器（主伺服器）的資料複製到一個或多個 MariaDB 伺服器（從伺服器）。

**主伺服器配置**：

1. 編輯 MariaDB 配置檔案 (`/etc/mysql/mariadb.conf.d/50-server.cnf`)：
   ```ini
   [mysqld]
   server-id = 1
   log_bin = /var/log/mysql/mysql-bin.log
   binlog_format = ROW
   ```

2. 重啟 MariaDB 服務：
   ```bash
   systemctl restart mariadb
   ```

3. 創建複製用戶：
   ```sql
   CREATE USER 'repl'@'%' IDENTIFIED BY 'password';
   GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';
   FLUSH PRIVILEGES;
   ```

4. 獲取主伺服器狀態：
   ```sql
   SHOW MASTER STATUS;
   ```
   記下 File 和 Position 的值，後面會用到。

**從伺服器配置**：

1. 編輯 MariaDB 配置檔案：
   ```ini
   [mysqld]
   server-id = 2
   ```

2. 重啟 MariaDB 服務：
   ```bash
   systemctl restart mariadb
   ```

3. 配置從伺服器：
   ```sql
   CHANGE MASTER TO
     MASTER_HOST='主伺服器IP',
     MASTER_USER='repl',
     MASTER_PASSWORD='password',
     MASTER_LOG_FILE='mysql-bin.000001', -- 使用主伺服器的 File 值
     MASTER_LOG_POS=123; -- 使用主伺服器的 Position 值
   ```

4. 啟動複製：
   ```sql
   START SLAVE;
   ```

5. 檢查複製狀態：
   ```sql
   SHOW SLAVE STATUS\G
   ```
   確保 Slave_IO_Running 和 Slave_SQL_Running 都是 Yes。

#### 主主複製 (Master-Master Replication)

主主複製允許兩個 MariaDB 伺服器互相複製，實現雙向同步。

**伺服器 A 配置**：

1. 編輯 MariaDB 配置檔案：
   ```ini
   [mysqld]
   server-id = 1
   log_bin = /var/log/mysql/mysql-bin.log
   binlog_format = ROW
   auto_increment_increment = 2
   auto_increment_offset = 1
   ```

2. 重啟 MariaDB 服務。

3. 創建複製用戶並獲取狀態，與主從複製相同。

**伺服器 B 配置**：

1. 編輯 MariaDB 配置檔案：
   ```ini
   [mysqld]
   server-id = 2
   log_bin = /var/log/mysql/mysql-bin.log
   binlog_format = ROW
   auto_increment_increment = 2
   auto_increment_offset = 2
   ```

2. 重啟 MariaDB 服務。

3. 創建複製用戶並獲取狀態，與主從複製相同。

4. 配置伺服器 B 作為伺服器 A 的從伺服器，與主從複製相同。

**伺服器 A 作為伺服器 B 的從伺服器**：

1. 在伺服器 A 上配置：
   ```sql
   CHANGE MASTER TO
     MASTER_HOST='伺服器B的IP',
     MASTER_USER='repl',
     MASTER_PASSWORD='password',
     MASTER_LOG_FILE='mysql-bin.000001', -- 使用伺服器B的 File 值
     MASTER_LOG_POS=123; -- 使用伺服器B的 Position 值
   ```

2. 啟動複製：
   ```sql
   START SLAVE;
   ```

3. 檢查複製狀態：
   ```sql
   SHOW SLAVE STATUS\G
   ```

#### Galera Cluster

Galera Cluster 是 MariaDB 的一個多主同步複製插件，它提供了真正的多主複製，允許在任何節點上進行讀寫操作。

**安裝 Galera Cluster**：

1. 安裝 MariaDB Galera Cluster：
   ```bash
   apt-get install mariadb-server-10.5 mariadb-client-10.5 galera-4
   ```

2. 編輯配置檔案 (`/etc/mysql/mariadb.conf.d/60-galera.cnf`)：
   ```ini
   [galera]
   wsrep_on = ON
   wsrep_provider = /usr/lib/galera/libgalera_smm.so
   wsrep_cluster_address = "gcomm://節點1IP,節點2IP,節點3IP"
   wsrep_cluster_name = "my_cluster"
   wsrep_node_address = "本節點IP"
   wsrep_node_name = "節點1"
   wsrep_sst_method = rsync
   ```

3. 在第一個節點上啟動集群：
   ```bash
   galera_new_cluster
   ```

4. 在其他節點上啟動 MariaDB：
   ```bash
   systemctl start mariadb
   ```

5. 檢查集群狀態：
   ```sql
   SHOW STATUS LIKE 'wsrep_%';
   ```

### 主從架構

主從架構是一種常見的資料庫部署方式，它包括一個主伺服器和一個或多個從伺服器。主伺服器處理寫操作，從伺服器處理讀操作，這樣可以分散負載，提高系統的整體性能和可用性。

#### 主從架構的優點

1. **負載均衡**：
   將讀操作分散到多個從伺服器，減輕主伺服器的負載。

2. **高可用性**：
   如果主伺服器故障，可以將一個從伺服器提升為新的主伺服器。

3. **資料備份**：
   可以在從伺服器上進行備份操作，不影響主伺服器的性能。

4. **資料分析**：
   可以在從伺服器上進行資料分析和報表生成，不影響主伺服器的性能。

#### 主從架構的實現

主從架構的實現基於主從複製，具體配置方法在前面的「資料同步」部分已經介紹過。

#### 讀寫分離

讀寫分離是主從架構的一個重要應用，它將讀操作和寫操作分別路由到不同的伺服器：

1. **寫操作**：
   路由到主伺服器。

2. **讀操作**：
   路由到從伺服器。

讀寫分離可以通過以下方式實現：

1. **應用層實現**：
   在應用程式中根據操作類型選擇不同的資料庫連接。

2. **中間件實現**：
   使用如 ProxySQL、MaxScale 等中間件自動路由查詢。

3. **連接池實現**：
   使用如 HikariCP、Tomcat JDBC Pool 等連接池配置讀寫分離。

#### 故障轉移

故障轉移是指當主伺服器故障時，自動將一個從伺服器提升為新的主伺服器，確保系統的高可用性。

故障轉移可以通過以下方式實現：

1. **手動故障轉移**：
   當主伺服器故障時，手動將一個從伺服器提升為新的主伺服器。
   ```sql
   -- 在從伺服器上停止複製
   STOP SLAVE;

   -- 重置複製設定
   RESET SLAVE ALL;

   -- 將從伺服器提升為新的主伺服器
   -- 此時其他從伺服器需要重新配置，指向新的主伺服器
   ```

2. **自動故障轉移**：
   使用工具或中間件自動檢測主伺服器故障並執行故障轉移。
    - **MHA (Master High Availability)**：監控主伺服器，當主伺服器故障時自動執行故障轉移。
    - **Orchestrator**：MySQL/MariaDB 複製拓撲管理和故障轉移工具。
    - **ProxySQL**：可以配置為自動檢測主伺服器故障並重新路由查詢。

3. **使用 Galera Cluster**：
   Galera Cluster 提供了真正的多主複製，任何節點故障都不會影響系統的可用性。

### 資料備份與恢復

資料備份是防止資料丟失的重要措施。MariaDB 提供了多種備份和恢復方法。

#### 邏輯備份

邏輯備份是將資料庫的結構和資料導出為 SQL 語句或文本文件。

1. **使用 mysqldump**：
   ```bash
   # 備份整個資料庫
   mysqldump -u username -p database_name > backup.sql

   # 備份多個資料庫
   mysqldump -u username -p --databases db1 db2 > backup.sql

   # 備份所有資料庫
   mysqldump -u username -p --all-databases > backup.sql

   # 只備份結構，不備份資料
   mysqldump -u username -p --no-data database_name > structure.sql

   # 只備份資料，不備份結構
   mysqldump -u username -p --no-create-info database_name > data.sql
   ```

2. **使用 mariadb-dump**：
   MariaDB 10.4 及以上版本提供了 mariadb-dump 工具，用法與 mysqldump 類似。
   ```bash
   mariadb-dump -u username -p database_name > backup.sql
   ```

#### 物理備份

物理備份是直接複製資料庫的資料文件。

1. **使用 Mariabackup**：
   Mariabackup 是 MariaDB 的物理備份工具，類似於 MySQL 的 XtraBackup。
   ```bash
   # 創建完整備份
   mariabackup --backup --target-dir=/path/to/backup --user=username --password=password

   # 準備備份用於恢復
   mariabackup --prepare --target-dir=/path/to/backup

   # 恢復備份
   mariabackup --copy-back --target-dir=/path/to/backup
   ```

2. **使用檔案系統快照**：
   如果資料庫使用 LVM (Logical Volume Manager)，可以使用 LVM 快照功能創建一致性備份。
   ```bash
   # 創建 LVM 快照
   lvcreate -L 10G -s -n mysql_snap /dev/vg0/mysql

   # 掛載快照
   mkdir /mnt/mysql_snap
   mount /dev/vg0/mysql_snap /mnt/mysql_snap

   # 複製資料
   cp -a /mnt/mysql_snap/var/lib/mysql /backup/mysql

   # 卸載快照並刪除
   umount /mnt/mysql_snap
   lvremove -f /dev/vg0/mysql_snap
   ```

#### 增量備份

增量備份只備份自上次備份以來發生變化的資料。

1. **使用二進制日誌**：
   二進制日誌記錄了所有修改資料的操作，可以用於增量備份和時間點恢復。
   ```bash
   # 備份二進制日誌
   mysqlbinlog mysql-bin.000001 > binlog.sql

   # 應用二進制日誌
   mysql -u username -p < binlog.sql
   ```

2. **使用 Mariabackup 增量備份**：
   ```bash
   # 創建完整備份
   mariabackup --backup --target-dir=/path/to/full --user=username --password=password

   # 創建增量備份
   mariabackup --backup --target-dir=/path/to/incr --incremental-basedir=/path/to/full --user=username --password=password

   # 準備完整備份
   mariabackup --prepare --target-dir=/path/to/full

   # 應用增量備份
   mariabackup --prepare --target-dir=/path/to/full --incremental-dir=/path/to/incr

   # 恢復備份
   mariabackup --copy-back --target-dir=/path/to/full
   ```

#### 備份策略

設計有效的備份策略需要考慮以下因素：

1. **備份頻率**：
    - 完整備份：每天或每週
    - 增量備份：每小時或每天
    - 二進制日誌：實時

2. **備份保留期**：
    - 短期備份：保留 7-30 天
    - 長期備份：保留 1-12 個月
    - 歸檔備份：保留 1 年或更長

3. **備份驗證**：
   定期測試備份的有效性，確保能夠成功恢復。
   ```bash
   # 恢復備份到測試伺服器
   mysql -u username -p test_database < backup.sql

   # 驗證資料完整性
   mysqlcheck -u username -p test_database
   ```

4. **備份監控**：
   監控備份作業的成功與否，及時發現並解決問題。

#### 資料恢復

當資料丟失或損壞時，可以使用備份進行恢復。

1. **使用邏輯備份恢復**：
   ```bash
   # 恢復整個資料庫
   mysql -u username -p database_name < backup.sql

   # 恢復特定資料表
   grep -n "CREATE TABLE.*table_name" backup.sql  # 找到資料表在備份文件中的位置
   sed -n 'start_line,end_line p' backup.sql > table_backup.sql
   mysql -u username -p database_name < table_backup.sql
   ```

2. **使用物理備份恢復**：
   ```bash
   # 停止 MariaDB 服務
   systemctl stop mariadb

   # 恢復備份
   mariabackup --copy-back --target-dir=/path/to/backup

   # 修復權限
   chown -R mysql:mysql /var/lib/mysql

   # 啟動 MariaDB 服務
   systemctl start mariadb
   ```

3. **時間點恢復**：
   使用完整備份和二進制日誌，可以將資料庫恢復到特定時間點。
   ```bash
   # 恢復完整備份
   mysql -u username -p < full_backup.sql

   # 應用二進制日誌到特定時間點
   mysqlbinlog --stop-datetime="2023-09-15 14:30:00" mysql-bin.000001 | mysql -u username -p
   ```

### 高可用性配置

高可用性 (High Availability, HA) 是指系統能夠在組件故障的情況下繼續運行的能力。MariaDB 提供了多種高可用性解決方案。

#### 主從複製 + 故障轉移

主從複製結合故障轉移機制可以提供基本的高可用性。

1. **使用 MHA (Master High Availability)**：
   MHA 是一個用於 MySQL/MariaDB 的高可用性管理工具，它可以自動監控主伺服器，當主伺服器故障時自動執行故障轉移。

2. **使用 Orchestrator**：
   Orchestrator 是一個 MySQL/MariaDB 複製拓撲管理和故障轉移工具，它提供了 Web 界面和 API 來管理複製拓撲。

#### Galera Cluster

Galera Cluster 提供了真正的多主同步複製，是一個強大的高可用性解決方案。

1. **Galera Cluster 的優點**：
    - 同步複製：所有節點上的資料都是一致的
    - 多主架構：可以在任何節點上進行讀寫操作
    - 自動成員管理：節點可以動態加入和離開集群
    - 無需特殊硬件：可以在普通伺服器上運行

2. **Galera Cluster 的限制**：
    - 只支持 InnoDB 儲存引擎
    - 所有節點必須有相同的資料
    - 寫入性能可能受到影響，特別是在高並發環境中

#### 負載均衡

負載均衡可以將請求分散到多個資料庫伺服器，提高系統的整體性能和可用性。

1. **使用 HAProxy**：
   HAProxy 是一個流行的負載均衡器，可以將請求分散到多個 MariaDB 伺服器。
   ```
   # HAProxy 配置示例
   frontend mysql
       bind *:3306
       mode tcp
       default_backend mysql_servers

   backend mysql_servers
       mode tcp
       balance roundrobin
       server mysql1 192.168.1.1:3306 check
       server mysql2 192.168.1.2:3306 check backup
   ```

2. **使用 ProxySQL**：
   ProxySQL 是一個專門為 MySQL/MariaDB 設計的代理，它提供了更多的功能，如查詢路由、查詢緩存、連接池等。
   ```
   # ProxySQL 配置示例
   INSERT INTO mysql_servers(hostgroup_id, hostname, port) VALUES (1, '192.168.1.1', 3306);
   INSERT INTO mysql_servers(hostgroup_id, hostname, port) VALUES (1, '192.168.1.2', 3306);

   INSERT INTO mysql_users(username, password, default_hostgroup) VALUES ('app_user', 'password', 1);

   LOAD MYSQL SERVERS TO RUNTIME;
   SAVE MYSQL SERVERS TO DISK;

   LOAD MYSQL USERS TO RUNTIME;
   SAVE MYSQL USERS TO DISK;
   ```

#### 地理分佈式部署

地理分佈式部署可以提供跨地域的高可用性，防止單一數據中心故障導致的服務中斷。

1. **使用 Galera Cluster 跨數據中心部署**：
   Galera Cluster 可以部署在多個數據中心，但由於同步複製的特性，節點之間的網絡延遲會影響性能。

2. **使用異步複製跨數據中心部署**：
   使用 MariaDB 的異步複製功能，可以在不同數據中心之間複製資料。
   ```
   # 數據中心 A 的主伺服器配置
   server-id = 1
   log_bin = /var/log/mysql/mysql-bin.log
   binlog_format = ROW

   # 數據中心 B 的主伺服器配置
   server-id = 2
   log_bin = /var/log/mysql/mysql-bin.log
   binlog_format = ROW

   # 數據中心 A 的主伺服器作為數據中心 B 的主伺服器的從伺服器
   CHANGE MASTER TO
     MASTER_HOST='數據中心B的IP',
     MASTER_USER='repl',
     MASTER_PASSWORD='password',
     MASTER_LOG_FILE='mysql-bin.000001',
     MASTER_LOG_POS=123;

   # 數據中心 B 的主伺服器作為數據中心 A 的主伺服器的從伺服器
   CHANGE MASTER TO
     MASTER_HOST='數據中心A的IP',
     MASTER_USER='repl',
     MASTER_PASSWORD='password',
     MASTER_LOG_FILE='mysql-bin.000001',
     MASTER_LOG_POS=123;
   ```

### 資料庫安全性

資料庫安全是保護資料免受未授權訪問、損壞或丟失的重要方面。

#### 用戶管理與權限

1. **創建具有最小權限的用戶**：
   遵循最小權限原則，只授予用戶完成其工作所需的最小權限。
   ```sql
   -- 創建只有讀取權限的用戶
   CREATE USER 'read_user'@'%' IDENTIFIED BY 'password';
   GRANT SELECT ON database_name.* TO 'read_user'@'%';

   -- 創建具有讀寫權限的用戶
   CREATE USER 'write_user'@'%' IDENTIFIED BY 'password';
   GRANT SELECT, INSERT, UPDATE, DELETE ON database_name.* TO 'write_user'@'%';

   -- 創建管理員用戶
   CREATE USER 'admin_user'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON *.* TO 'admin_user'@'localhost' WITH GRANT OPTION;
   ```

2. **定期審查用戶權限**：
   定期檢查用戶權限，移除不必要的權限和不再需要的用戶。
   ```sql
   -- 查看用戶權限
   SHOW GRANTS FOR 'username'@'hostname';

   -- 撤銷權限
   REVOKE SELECT ON database_name.* FROM 'username'@'hostname';

   -- 刪除用戶
   DROP USER 'username'@'hostname';
   ```

#### 網絡安全

1. **限制連接來源**：
   只允許特定 IP 地址或網絡連接到 MariaDB。
   ```sql
   -- 創建只能從特定 IP 連接的用戶
   CREATE USER 'username'@'192.168.1.100' IDENTIFIED BY 'password';

   -- 或者使用防火牆限制連接
   # iptables -A INPUT -p tcp --dport 3306 -s 192.168.1.0/24 -j ACCEPT
   # iptables -A INPUT -p tcp --dport 3306 -j DROP
   ```

2. **使用 SSL/TLS 加密連接**：
   配置 MariaDB 使用 SSL/TLS 加密連接，保護傳輸中的資料。
   ```ini
   # MariaDB 配置文件
   [mysqld]
   ssl-ca=/path/to/ca.pem
   ssl-cert=/path/to/server-cert.pem
   ssl-key=/path/to/server-key.pem
   ```

   ```sql
   -- 要求用戶使用 SSL 連接
   CREATE USER 'username'@'%' IDENTIFIED BY 'password' REQUIRE SSL;
   ```

3. **使用 SSH 隧道**：
   如果需要從遠程連接到 MariaDB，可以使用 SSH 隧道加密連接。
   ```bash
   # 在本地機器上創建 SSH 隧道
   ssh -L 3306:localhost:3306 username@remote_server

   # 然後連接到本地埠
   mysql -h 127.0.0.1 -u username -p
   ```

#### 資料加密

1. **靜態資料加密**：
   加密存儲在磁盤上的資料，防止未授權訪問。
   ```ini
   # MariaDB 配置文件
   [mysqld]
   innodb_encrypt_tables = ON
   innodb_encrypt_log = ON
   innodb_encryption_threads = 4
   ```

2. **欄位級加密**：
   加密特定的敏感欄位，如信用卡號、社會安全號等。
   ```sql
   -- 創建加密函數
   CREATE FUNCTION encrypt_func(p_plaintext VARCHAR(255)) RETURNS VARBINARY(255)
   RETURN AES_ENCRYPT(p_plaintext, 'encryption_key');

   CREATE FUNCTION decrypt_func(p_ciphertext VARBINARY(255)) RETURNS VARCHAR(255)
   RETURN AES_DECRYPT(p_ciphertext, 'encryption_key');

   -- 使用加密函數
   INSERT INTO users (name, credit_card) VALUES ('John', encrypt_func('1234-5678-9012-3456'));

   -- 查詢加密資料
   SELECT name, decrypt_func(credit_card) FROM users;
   ```

#### 安全審計

1. **啟用審計日誌**：
   記錄所有資料庫操作，用於安全審計和問題排查。
   ```ini
   # MariaDB 配置文件
   [mysqld]
   server_audit_logging = ON
   server_audit_events = CONNECT,QUERY,TABLE
   server_audit_output_type = file
   server_audit_file_path = /var/log/mysql/audit.log
   ```

2. **定期檢查審計日誌**：
   定期檢查審計日誌，查找可疑活動。
   ```bash
   # 查找失敗的登錄嘗試
   grep "Connect.*failed" /var/log/mysql/audit.log

   # 查找特定用戶的操作
   grep "username" /var/log/mysql/audit.log
   ```

### 大規模資料處理

隨著資料量的增長，處理大規模資料成為一個挑戰。MariaDB 提供了多種技術來處理大規模資料。

#### 分區 (Partitioning)

分區是將大型資料表分成小塊的技術，可以提高查詢性能和管理大型資料表的能力。

1. **範圍分區**：
   根據欄位值的範圍將資料分區。
   ```sql
   CREATE TABLE sales (
       id INT NOT NULL AUTO_INCREMENT,
       sale_date DATE NOT NULL,
       amount DECIMAL(10,2) NOT NULL,
       PRIMARY KEY (id, sale_date)
   )
   PARTITION BY RANGE (YEAR(sale_date)) (
       PARTITION p0 VALUES LESS THAN (2020),
       PARTITION p1 VALUES LESS THAN (2021),
       PARTITION p2 VALUES LESS THAN (2022),
       PARTITION p3 VALUES LESS THAN (2023),
       PARTITION p4 VALUES LESS THAN MAXVALUE
   );
   ```

2. **列表分區**：
   根據欄位值的列表將資料分區。
   ```sql
   CREATE TABLE employees (
       id INT NOT NULL,
       name VARCHAR(50) NOT NULL,
       department VARCHAR(20) NOT NULL,
       PRIMARY KEY (id, department)
   )
   PARTITION BY LIST (department) (
       PARTITION p_sales VALUES IN ('sales', 'marketing'),
       PARTITION p_engineering VALUES IN ('engineering', 'research'),
       PARTITION p_hr VALUES IN ('hr', 'administration')
   );
   ```

3. **雜湊分區**：
   根據欄位值的雜湊將資料均勻分佈到分區。
   ```sql
   CREATE TABLE orders (
       id INT NOT NULL,
       customer_id INT NOT NULL,
       order_date DATE NOT NULL,
       PRIMARY KEY (id, customer_id)
   )
   PARTITION BY HASH (customer_id)
   PARTITIONS 4;
   ```

4. **鍵分區**：
   類似於雜湊分區，但使用 MariaDB 的內部雜湊函數。
   ```sql
   CREATE TABLE customers (
       id INT NOT NULL,
       name VARCHAR(50) NOT NULL,
       email VARCHAR(50) NOT NULL,
       PRIMARY KEY (id)
   )
   PARTITION BY KEY (id)
   PARTITIONS 4;
   ```

#### 分片 (Sharding)

分片是將資料分散到多個資料庫伺服器的技術，可以處理超出單個伺服器容量的資料量。

1. **水平分片**：
   根據某個欄位（如用戶 ID）將資料分散到多個伺服器。
   ```
   # 伺服器 1：包含用戶 ID 1-1000000 的資料
   # 伺服器 2：包含用戶 ID 1000001-2000000 的資料
   # 伺服器 3：包含用戶 ID 2000001-3000000 的資料
   ```

2. **垂直分片**：
   根據資料表或功能將資料分散到多個伺服器。
   ```
   # 伺服器 1：包含用戶資料
   # 伺服器 2：包含訂單資料
   # 伺服器 3：包含產品資料
   ```

3. **使用中間件實現分片**：
    - **ProxySQL**：可以根據查詢中的條件路由到不同的伺服器。
    - **MaxScale**：提供了查詢路由和分片功能。
    - **ShardingSphere**：提供了分片、讀寫分離、分佈式事務等功能。

#### 大型查詢優化

處理大型查詢需要特別的優化技巧。

1. **分頁查詢**：
   使用 LIMIT 和 OFFSET 分頁查詢結果，避免一次返回大量資料。
   ```sql
   -- 第一頁（每頁 10 條記錄）
   SELECT * FROM large_table LIMIT 10;

   -- 第二頁
   SELECT * FROM large_table LIMIT 10 OFFSET 10;

   -- 更高效的分頁方式（使用上一頁的最後一條記錄的 ID）
   SELECT * FROM large_table WHERE id > last_id LIMIT 10;
   ```

2. **批量操作**：
   使用批量插入、更新和刪除，減少資料庫操作的次數。
   ```sql
   -- 批量插入
   INSERT INTO table_name (column1, column2) VALUES
   (value1_1, value1_2),
   (value2_1, value2_2),
   (value3_1, value3_2);

   -- 批量更新
   UPDATE table_name
   SET column1 = CASE id
       WHEN 1 THEN value1
       WHEN 2 THEN value2
       WHEN 3 THEN value3
   END
   WHERE id IN (1, 2, 3);
   ```

3. **使用臨時表**：
   對於複雜的查詢，可以使用臨時表存儲中間結果。
   ```sql
   -- 創建臨時表
   CREATE TEMPORARY TABLE temp_results AS
   SELECT customer_id, COUNT(*) AS order_count
   FROM orders
   GROUP BY customer_id;

   -- 使用臨時表
   SELECT c.name, t.order_count
   FROM customers c
   JOIN temp_results t ON c.id = t.customer_id
   WHERE t.order_count > 10;

   -- 刪除臨時表
   DROP TEMPORARY TABLE temp_results;
   ```

4. **使用索引**：
   確保查詢使用適當的索引，特別是對於大型資料表。
   ```sql
   -- 創建複合索引
   CREATE INDEX idx_customer_date ON orders(customer_id, order_date);

   -- 使用索引的查詢
   SELECT * FROM orders
   WHERE customer_id = 123 AND order_date BETWEEN '2023-01-01' AND '2023-12-31';
   ```

### 高級案例研究

以下是一些高級案例研究，展示如何在實際場景中應用 MariaDB 的高級功能。

#### 案例一：電子商務網站

電子商務網站需要處理大量的產品、訂單和用戶資料，同時確保系統的高可用性和性能。

**解決方案**：

1. **資料庫設計**：
    - 使用正規化設計減少資料冗餘
    - 對於經常一起查詢的資料，考慮適當的反正規化
    - 使用分區處理大型資料表，如訂單和日誌

2. **高可用性**：
    - 使用主從複製 + 故障轉移確保系統的高可用性
    - 使用讀寫分離提高性能，將讀操作分散到多個從伺服器
    - 定期備份資料，確保資料安全

3. **性能優化**：
    - 優化索引設計，確保常用查詢使用索引
    - 使用查詢快取加速常見查詢
    - 使用連接池減少資料庫連接開銷

4. **安全性**：
    - 使用最小權限原則管理用戶權限
    - 加密敏感資料，如信用卡資訊
    - 啟用審計日誌，監控可疑活動

#### 案例二：大型日誌系統

大型日誌系統需要處理和分析大量的日誌資料，同時確保資料的完整性和可查詢性。

**解決方案**：

1. **資料庫設計**：
    - 使用時間範圍分區，將日誌資料按時間分區
    - 使用壓縮儲存引擎減少儲存空間
    - 定期歸檔舊的日誌資料

2. **寫入優化**：
    - 使用批量插入減少寫入開銷
    - 使用異步寫入，不阻塞主要業務流程
    - 考慮使用 Memory 儲存引擎作為緩衝，定期將資料寫入磁碟

3. **查詢優化**：
    - 創建適當的索引，支持常見的查詢模式
    - 使用分區裁剪，只掃描相關的分區
    - 使用聚合表預先計算常用的統計資料

4. **擴展性**：
    - 使用分片將資料分散到多個伺服器
    - 使用複製提供讀取擴展性
    - 考慮使用時序資料庫（如 InfluxDB）作為補充

#### 案例三：多租戶 SaaS 應用

多租戶 SaaS (Software as a Service) 應用需要在同一個系統中安全地隔離不同客戶（租戶）的資料，同時提供良好的可擴展性和性能。

**解決方案**：

1. **資料隔離策略**：
    - **單一資料庫，共享資料表**：所有租戶的資料存儲在同一個資料表中，使用租戶 ID 欄位區分。
      ```sql
      CREATE TABLE customers (
          id INT NOT NULL AUTO_INCREMENT,
          tenant_id INT NOT NULL,
          name VARCHAR(100) NOT NULL,
          email VARCHAR(100) NOT NULL,
          PRIMARY KEY (id),
          INDEX idx_tenant (tenant_id)
      );
      ```

    - **單一資料庫，獨立資料表**：每個租戶有自己的資料表，通常使用資料表名稱前綴或後綴區分。
      ```sql
      CREATE TABLE tenant1_customers (
          id INT NOT NULL AUTO_INCREMENT,
          name VARCHAR(100) NOT NULL,
          email VARCHAR(100) NOT NULL,
          PRIMARY KEY (id)
      );
 
      CREATE TABLE tenant2_customers (
          id INT NOT NULL AUTO_INCREMENT,
          name VARCHAR(100) NOT NULL,
          email VARCHAR(100) NOT NULL,
          PRIMARY KEY (id)
      );
      ```

    - **獨立資料庫**：每個租戶有自己的資料庫。
      ```sql
      CREATE DATABASE tenant1;
      CREATE DATABASE tenant2;
      ```

2. **安全性**：
    - 使用資料庫視圖和存儲過程限制租戶只能訪問自己的資料。
      ```sql
      -- 創建租戶特定的視圖
      CREATE VIEW tenant1_view AS
      SELECT * FROM customers WHERE tenant_id = 1;
 
      -- 創建租戶特定的存儲過程
      DELIMITER //
      CREATE PROCEDURE get_tenant_customers(IN p_tenant_id INT)
      BEGIN
          SELECT * FROM customers WHERE tenant_id = p_tenant_id;
      END //
      DELIMITER ;
      ```

    - 使用資料庫用戶和權限控制租戶的訪問。
      ```sql
      -- 創建租戶特定的用戶
      CREATE USER 'tenant1'@'%' IDENTIFIED BY 'password';
 
      -- 授予租戶特定的權限
      GRANT SELECT, INSERT, UPDATE, DELETE ON tenant1_view TO 'tenant1'@'%';
      GRANT EXECUTE ON PROCEDURE get_tenant_customers TO 'tenant1'@'%';
      ```

3. **可擴展性**：
    - 使用分片將租戶資料分散到多個伺服器。
      ```
      # 伺服器 1：包含租戶 1-100 的資料
      # 伺服器 2：包含租戶 101-200 的資料
      # 伺服器 3：包含租戶 201-300 的資料
      ```

    - 使用複製提供讀取擴展性。

    - 使用連接池和查詢快取提高性能。

4. **租戶管理**：
    - 創建租戶管理系統，自動化租戶的創建、配置和管理。

    - 實現租戶資源限制，防止單個租戶消耗過多資源。
      ```sql
      -- 設定租戶特定的資源限制
      SET SESSION max_connections = 50;
      SET SESSION max_user_connections = 10;
      ```

    - 實現租戶資料的備份和恢復機制。

## 總結

本教學文件涵蓋了 MariaDB 的基礎知識、進階概念和高級主題，從安裝和基本操作到效能調優、高可用性配置和大規模資料處理。無論您是初學者還是有經驗的資料庫管理員，都可以從中找到有用的資訊和技巧。

MariaDB 是一個功能強大、靈活且可靠的關聯式資料庫管理系統，適用於各種應用場景，從小型網站到大型企業應用。通過掌握本教學文件中的知識和技能，您將能夠有效地使用 MariaDB 來儲存、管理和查詢資料，為您的應用程式提供強大的資料支持。

記住，資料庫設計和管理是一個持續學習的過程。隨著技術的發展和應用需求的變化，不斷更新您的知識和技能，探索 MariaDB 的新功能和最佳實踐，將幫助您成為一個更好的資料庫專家。

祝您在 MariaDB 的學習和使用中取得成功！
