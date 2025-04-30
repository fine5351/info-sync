# Oracle PeopleSoft SQR (結構化查詢報表) 教學文件

## 目錄

1. [概述](#概述)
2. [初級教學](#初級教學)
    - [什麼是 SQR](#什麼是-sqr)
    - [安裝與設置](#安裝與設置)
    - [SQR 基本語法](#sqr-基本語法)
    - [第一個 SQR 程序](#第一個-sqr-程序)
    - [變量與數據類型](#變量與數據類型)
    - [SQL 查詢基礎](#sql-查詢基礎)
    - [條件語句](#條件語句)
    - [循環結構](#循環結構)
    - [報表格式化](#報表格式化)
    - [初級練習](#初級練習)
3. [中級教學](#中級教學)
    - [高級 SQL 技巧](#高級-sql-技巧)
    - [過程與函數](#過程與函數)
    - [數組操作](#數組操作)
    - [文件操作](#文件操作)
    - [圖表生成](#圖表生成)
    - [自定義布局](#自定義布局)
    - [參數處理](#參數處理)
    - [錯誤處理](#錯誤處理)
    - [中級練習](#中級練習)
4. [高級教學](#高級教學)
    - [性能優化](#性能優化)
    - [故障排除](#故障排除)
    - [最佳實踐](#最佳實踐)
    - [安全性考量](#安全性考量)
    - [與其他系統集成](#與其他系統集成)
    - [大數據處理](#大數據處理)
    - [高級案例研究](#高級案例研究)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 Oracle PeopleSoft SQR (結構化查詢報表) 工具。無論您是完全沒有 SQR 經驗的初學者，還是已經了解基礎功能需要進行自定義的中級學習者，或是想要深入了解性能調優和問題排查的高級使用者，本文檔都能為您提供所需的知識和技能。

SQR 是 PeopleSoft 系統中最重要的報表開發工具，用於創建複雜的報表、執行批處理操作和數據處理。通過學習 SQR，您可以自定義 PeopleSoft 系統的報表功能，使其更好地滿足組織的需求。

## 初級教學

本節適合完全沒有 SQR 經驗的初學者。我們將從最基本的概念開始，逐步建立您對 SQR 的理解。

### 什麼是 SQR

SQR (結構化查詢報表) 是一種專門為報表開發設計的程式語言，在 Oracle PeopleSoft 系統中廣泛使用。它結合了 SQL 查詢功能和報表格式化能力，讓您能夠從數據庫中提取數據並生成格式精美的報表。

SQR 的主要功能包括：

- 從數據庫中提取數據
- 處理和轉換數據
- 生成格式化報表
- 執行批處理操作
- 與其他系統交換數據

對於初學者來說，可以將 SQR 想像成一個特殊的「翻譯員」，它能夠理解您的指令，從數據庫獲取信息，然後將這些信息整理成漂亮的報表。

### 安裝與設置

在開始使用 SQR 之前，您需要安裝和設置它。通常，這項工作由系統管理員完成，但了解基本步驟對您也很有幫助。

#### 安裝步驟：

1. **獲取安裝文件**：從 Oracle 官方網站或您的組織的 IT 部門獲取 PeopleSoft 安裝文件。

2. **安裝 PeopleTools**：SQR 是 PeopleTools 的一部分，所以您需要安裝 PeopleTools。

3. **安裝 SQR**：在 PeopleTools 安裝過程中，確保選擇 SQR 組件。

4. **配置環境變量**：設置必要的環境變量，如 SQRDIR（指向 SQR 安裝目錄）。

#### 系統要求：

- 操作系統：Windows 10 或更高版本，或支持的 Linux/Unix 版本
- 內存：至少 4GB RAM（建議 8GB 或更多）
- 硬盤空間：至少 5GB 可用空間
- 數據庫客戶端：Oracle、Microsoft SQL Server 或 DB2（取決於您的 PeopleSoft 系統使用的數據庫）

### SQR 基本語法

SQR 程序由多個部分組成，每個部分都有特定的語法和用途。

#### 程序結構：

一個基本的 SQR 程序通常包含以下部分：

1. **程序開始**：使用 `begin-program` 和 `end-program` 標記程序的開始和結束。

2. **設置部分**：使用 `begin-setup` 和 `end-setup` 定義程序的設置，如頁面大小、字體等。

3. **報表部分**：使用 `begin-report` 和 `end-report` 定義報表的主體。

4. **過程**：使用 `begin-procedure` 和 `end-procedure` 定義可重用的代碼塊。

5. **SQL 部分**：使用 `begin-sql` 和 `end-sql` 包含 SQL 查詢。

#### 基本命令：

- **let**：用於變量賦值，如 `let $name = 'John'`
- **print**：將文本輸出到報表，如 `print '員工姓名：' (+1,1)`
- **if/else**：條件語句，如 `if #salary > 5000`
- **while**：循環結構，如 `while #count < 10`
- **do**：調用過程，如 `do print-header`

#### 注釋：

SQR 中的注釋以感嘆號 (!) 開始，如：

```
! 這是一個注釋
let $name = 'John'  ! 這也是一個注釋
```

### 第一個 SQR 程序

讓我們創建一個簡單的 SQR 程序，它會打印 "Hello, World!" 並顯示當前日期。

```
!----- hello.sqr -----
begin-program
  do main
end-program

begin-procedure main
  print 'Hello, World!' (1,1)
  do get-current-date
  print '今天是：' (3,1)
  print $current-date (3,12)
end-procedure

begin-procedure get-current-date
  let $current-date = datenow()
  let $current-date = strtodate($current-date, 'YYYY-MM-DD')
end-procedure
```

#### 運行程序：

1. 保存上面的代碼為 `hello.sqr`
2. 打開命令提示符
3. 導航到保存文件的目錄
4. 運行命令：`sqr hello.sqr`
5. 查看生成的輸出文件 `hello.lis`

### 變量與數據類型

SQR 支持多種變量類型，用於存儲和處理不同類型的數據。

#### 變量命名規則：

- 字符串變量以 `$` 開頭，如 `$name`
- 數字變量以 `#` 開頭，如 `#salary`
- 變量名可以包含字母、數字和下劃線
- 變量名區分大小寫

#### 主要數據類型：

1. **字符串**：用於存儲文本，如 `let $name = 'John Smith'`
2. **數字**：用於存儲數值，如 `let #salary = 5000.50`
3. **日期**：用於存儲日期，通常作為字符串處理，如 `let $hire_date = '2023-01-15'`

#### 變量聲明：

在 SQR 中，您可以在使用前聲明變量，但這不是必須的：

```
begin-setup
  declare-variable
    $name    string
    #salary  number
    $date    string
  end-declare
end-setup
```

#### 變量操作：

```
! 字符串連接
let $full_name = $first_name || ' ' || $last_name

! 數學運算
let #total = #price * #quantity
let #discount = #total * 0.1
let #final_price = #total - #discount

! 日期操作
let $tomorrow = dateadd($today, 'day', 1)
```

### SQL 查詢基礎

SQR 的核心功能是執行 SQL 查詢並處理結果。

#### 基本查詢：

```
begin-sql
  SELECT first_name, last_name, salary
  FROM employees
  WHERE department_id = 10
end-sql
```

#### 使用變量：

```
begin-sql
  SELECT first_name, last_name, salary
  INTO $first_name, $last_name, #salary
  FROM employees
  WHERE employee_id = #emp_id
end-sql
```

#### 處理多行結果：

```
begin-select
  first_name    &first_name
  last_name     &last_name
  salary        &salary

  print &first_name (,1)
  print &last_name (,15)
  print &salary (,30) edit $999,999.99

  position (+1)
FROM employees
WHERE department_id = #dept_id
end-select
```

在上面的例子中：

- `&first_name` 是一個列變量，用於存儲當前行的 `first_name` 列的值
- `position (+1)` 將打印位置移動到下一行
- `edit $999,999.99` 指定了數字的格式化方式

### 條件語句

SQR 提供了多種條件語句，用於根據不同條件執行不同的代碼。

#### if-then-else：

```
if #salary > 5000
  print '高薪員工' (,50)
else
  print '普通員工' (,50)
end-if
```

#### 多重條件：

```
if #score >= 90
  let $grade = 'A'
else
  if #score >= 80
    let $grade = 'B'
  else
    if #score >= 70
      let $grade = 'C'
    else
      if #score >= 60
        let $grade = 'D'
      else
        let $grade = 'F'
      end-if
    end-if
  end-if
end-if
```

#### 使用 evaluate：

`evaluate` 是 SQR 中類似於其他語言中 `switch` 或 `case` 的語句：

```
evaluate #month
  when = 1
    let $month_name = '一月'
    break
  when = 2
    let $month_name = '二月'
    break
  when = 3
    let $month_name = '三月'
    break
  when-other
    let $month_name = '其他月份'
    break
end-evaluate
```

### 循環結構

SQR 提供了多種循環結構，用於重複執行代碼塊。

#### while 循環：

```
let #count = 1
while #count <= 5
  print '計數：' || to_char(#count) (,1)
  let #count = #count + 1
end-while
```

#### repeat 循環：

```
let #count = 1
repeat
  print '計數：' || to_char(#count) (,1)
  let #count = #count + 1
until #count > 5
end-repeat
```

#### for 循環：

SQR 沒有直接的 for 循環語法，但可以使用 while 循環模擬：

```
let #i = 1
while #i <= 10
  print '迭代：' || to_char(#i) (,1)
  let #i = #i + 1
end-while
```

#### 循環控制：

- **break**：跳出當前循環
- **next**：跳過當前迭代，繼續下一次迭代

```
let #i = 0
while #i < 10
  let #i = #i + 1
  if mod(#i, 2) = 0
    next  ! 跳過偶數
  end-if
  print '奇數：' || to_char(#i) (,1)
  if #i = 7
    break  ! 到達 7 時跳出循環
  end-if
end-while
```

### 報表格式化

SQR 提供了豐富的報表格式化功能，讓您能夠創建專業的報表輸出。

#### 頁面設置：

```
begin-setup
  page-size 66 80  ! 66 行，80 列
  orientation portrait
  paper-size letter
end-setup
```

#### 標題和頁腳：

```
begin-heading 5
  print 'Company Confidential' (1,center)
  print 'Employee Salary Report' (3,center)
  print 'Page: ' (5,65)
  page-number (5,71)
  print 'Date: ' (5,1)
  print $current-date (5,7)
end-heading

begin-footing 2
  print 'End of Report' (2,center)
end-footing
```

#### 文本格式化：

```
! 設置位置 (行,列)
print '員工姓名' (5,1)

! 使用相對位置 (+n,n)
print '部門：' (+2,1)

! 文本對齊
print '報表標題' (1,center)
print '頁碼' (1,right)

! 格式化數字
print #salary (,30) edit $999,999.99

! 設置字體和樣式
print '重要提示' (,1) bold
print '注意事項' (,1) italic
```

#### 表格和網格：

```
! 打印表頭
print '員工 ID' (5,1)
print '姓名' (5,15)
print '部門' (5,40)
print '薪資' (5,60)

! 打印分隔線
print '=' (6,1,79) fill

! 打印數據行
begin-select
emp_id    &emp_id
name      &name
dept      &dept
salary    &salary

  print &emp_id (,1)
  print &name (,15)
  print &dept (,40)
  print &salary (,60) edit $999,999.99

  position (+1)
from employees
end-select
```

### 初級練習

以下是一些幫助您熟悉 SQR 的初級練習：

#### 練習 1：創建員工列表報表

創建一個 SQR 程序，從 `employees` 表中獲取員工信息，並生成一個格式化的報表。

```
!----- employee_list.sqr -----
begin-program
  do main
end-program

begin-procedure main
  do init
  do print-header
  do process-employees
  do print-footer
end-procedure

begin-procedure init
  let $current-date = datenow()
  let $current-date = strtodate($current-date, 'YYYY-MM-DD')
  let #total_employees = 0
end-procedure

begin-procedure print-header
  print 'Employee List Report' (1,center)
  print 'Date: ' (3,1)
  print $current-date (3,7)
  print 'Page: ' (3,65)
  page-number (3,71)

  print 'Employee ID' (6,1)
  print 'Name' (6,15)
  print 'Department' (6,40)
  print 'Hire Date' (6,60)

  print '-' (7,1,75) fill
end-procedure

begin-procedure process-employees
  position (+2)

begin-select
employee_id    &employee_id
first_name     &first_name
last_name      &last_name
department     &department
hire_date      &hire_date

  print &employee_id (,1)
  print &first_name || ' ' || &last_name (,15)
  print &department (,40)
  print &hire_date (,60)

  let #total_employees = #total_employees + 1
  position (+1)
from employees
order by department, last_name
end-select
end-procedure

begin-procedure print-footer
  print '-' (+2,1,75) fill
  print 'Total Employees: ' (+2,1)
  print #total_employees (,20)
  print 'End of Report' (+2,center)
end-procedure
```

#### 練習 2：部門薪資統計報表

創建一個 SQR 程序，計算每個部門的平均薪資和總薪資。

```
!----- department_salary.sqr -----
begin-program
  do main
end-program

begin-procedure main
  do init
  do print-header
  do process-departments
  do print-footer
end-procedure

begin-procedure init
  let $current-date = datenow()
  let $current-date = strtodate($current-date, 'YYYY-MM-DD')
  let #grand_total = 0
end-procedure

begin-procedure print-header
  print 'Department Salary Report' (1,center)
  print 'Date: ' (3,1)
  print $current-date (3,7)
  print 'Page: ' (3,65)
  page-number (3,71)

  print 'Department' (6,1)
  print 'Employee Count' (6,25)
  print 'Average Salary' (6,45)
  print 'Total Salary' (6,65)

  print '-' (7,1,80) fill
end-procedure

begin-procedure process-departments
  position (+2)

begin-select
department     &department
count(*)       &emp_count
avg(salary)    &avg_salary
sum(salary)    &total_salary

  print &department (,1)
  print &emp_count (,30)
  print &avg_salary (,45) edit $999,999.99
  print &total_salary (,65) edit $9,999,999.99

  let #grand_total = #grand_total + &total_salary
  position (+1)
from employees
group by department
order by department
end-select
end-procedure

begin-procedure print-footer
  print '-' (+2,1,80) fill
  print 'Grand Total: ' (+2,50)
  print #grand_total (,65) edit $9,999,999.99
  print 'End of Report' (+2,center)
end-procedure
```

完成這些練習後，您將對 SQR 的基本功能有一個初步的了解。接下來，您可以繼續學習更高級的功能。

## 中級教學

本節適合已經了解 SQR 基礎知識的用戶。我們將深入探討更複雜的功能和技術，幫助您自定義和擴展 SQR 報表。

### 高級 SQL 技巧

在中級階段，您需要掌握更複雜的 SQL 查詢技巧，以處理更複雜的數據需求。

#### 子查詢：

```
begin-select
e.employee_id
e.name
e.department_id
(select d.department_name 
 from departments d 
 where d.department_id = e.department_id) &dept_name

  print &employee_id (,1)
  print &name (,15)
  print &dept_name (,40)

  position (+1)
from employees e
end-select
```

#### 聯接查詢：

```
begin-select
e.employee_id
e.first_name || ' ' || e.last_name &name
d.department_name
j.job_title

  print &employee_id (,1)
  print &name (,15)
  print &department_name (,40)
  print &job_title (,65)

  position (+1)
from employees e
join departments d on e.department_id = d.department_id
join jobs j on e.job_id = j.job_id
where e.hire_date > '2020-01-01'
order by d.department_name, e.last_name
end-select
```

#### 動態 SQL：

```
begin-procedure generate-dynamic-sql
  let $where_clause = ''

  if #dept_id <> 0
    let $where_clause = 'WHERE department_id = ' || to_char(#dept_id)
  end-if

  let $sql_query = 'SELECT employee_id, name, salary FROM employees ' || $where_clause

  show $sql_query  ! 顯示生成的 SQL 查詢
end-procedure
```

#### 使用游標：

```
begin-procedure process-with-cursor
  let #total = 0

begin-sql
  DECLARE emp_cursor CURSOR FOR
  SELECT employee_id, salary
  FROM employees
  WHERE department_id = :dept_id
end-sql

begin-sql
  OPEN emp_cursor
end-sql

  let #done = 0
  while #done = 0

begin-sql
  FETCH emp_cursor INTO :emp_id, :salary
end-sql

    if sqlcode = 100  ! 沒有更多記錄
      let #done = 1
    else
      print &emp_id (,1)
      print &salary (,15) edit $999,999.99
      let #total = #total + &salary
      position (+1)
    end-if
  end-while

begin-sql
  CLOSE emp_cursor
end-sql

  print 'Total: ' (+2,1)
  print #total (,15) edit $9,999,999.99
end-procedure
```

### 過程與函數

SQR 允許您創建可重用的過程和函數，提高代碼的模塊化和可維護性。

#### 帶參數的過程：

```
begin-procedure print-employee(#emp_id, $format)
begin-select
first_name    &first_name
last_name     &last_name
salary        &salary
from employees
where employee_id = #emp_id
end-select

  if $format = 'FULL'
    print 'Employee ID: ' || to_char(#emp_id) (,1)
    print 'Name: ' || &first_name || ' ' || &last_name (+1,1)
    print 'Salary: ' || to_char(&salary, '$999,999.99') (+1,1)
  else
    print #emp_id (,1)
    print &first_name || ' ' || &last_name (,10)
    print &salary (,40) edit $999,999.99
  end-if
end-procedure

! 調用過程
do print-employee(101, 'FULL')
do print-employee(102, 'BRIEF')
```

#### 返回值的函數：

SQR 不直接支持返回值的函數，但可以通過參數傳遞來模擬：

```
begin-procedure calculate-bonus(#salary, #years, :#bonus)
  if #years < 2
    let #bonus = #salary * 0.05
  else
    if #years < 5
      let #bonus = #salary * 0.1
    else
      let #bonus = #salary * 0.15
    end-if
  end-if
end-procedure

! 調用並獲取結果
let #emp_salary = 50000
let #emp_years = 3
let #emp_bonus = 0
do calculate-bonus(#emp_salary, #emp_years, #emp_bonus)
print 'Bonus: ' || to_char(#emp_bonus, '$999,999.99') (,1)
```

#### 遞歸過程：

```
begin-procedure factorial(#n, :#result)
  if #n <= 1
    let #result = 1
  else
    let #temp = 0
    do factorial(#n - 1, #temp)
    let #result = #n * #temp
  end-if
end-procedure

! 計算 5 的階乘
let #fact = 0
do factorial(5, #fact)
print '5! = ' || to_char(#fact) (,1)
```

### 數組操作

SQR 支持數組，可以用於存儲和處理多個相關值。

#### 聲明和初始化數組：

```
begin-setup
  declare-array name=employee_array size=100
    field=id:number
    field=name:char
    field=salary:number
  end-declare
end-setup

begin-procedure init-array
  let #i = 0
  while #i < 100
    put 0 #i 'id' into employee_array
    put '' #i 'name' into employee_array
    put 0 #i 'salary' into employee_array
    let #i = #i + 1
  end-while
end-procedure
```

#### 填充數組：

```
begin-procedure load-employees
  let #count = 0

begin-select
employee_id    &employee_id
name           &name
salary         &salary

  put &employee_id #count 'id' into employee_array
  put &name #count 'name' into employee_array
  put &salary #count 'salary' into employee_array

  let #count = #count + 1
from employees
where rownum <= 100
end-select

  let #array_size = #count
end-procedure
```

#### 訪問數組元素：

```
begin-procedure print-array
  let #i = 0
  while #i < #array_size
    get $id #i 'id' from employee_array
    get $name #i 'name' from employee_array
    get $salary #i 'salary' from employee_array

    print $id (,1)
    print $name (,10)
    print $salary (,40) edit $999,999.99

    position (+1)
    let #i = #i + 1
  end-while
end-procedure
```

#### 數組排序：

```
begin-procedure sort-array-by-salary
  let #i = 0
  while #i < #array_size - 1
    let #j = 0
    while #j < #array_size - #i - 1
      get $salary1 #j 'salary' from employee_array
      get $salary2 #j+1 'salary' from employee_array

      if $salary1 > $salary2
        ! 交換元素
        get $id1 #j 'id' from employee_array
        get $name1 #j 'name' from employee_array
        get $id2 #j+1 'id' from employee_array
        get $name2 #j+1 'name' from employee_array

        put $id2 #j 'id' into employee_array
        put $name2 #j 'name' into employee_array
        put $salary2 #j 'salary' into employee_array

        put $id1 #j+1 'id' into employee_array
        put $name1 #j+1 'name' into employee_array
        put $salary1 #j+1 'salary' into employee_array
      end-if

      let #j = #j + 1
    end-while
    let #i = #i + 1
  end-while
end-procedure
```

### 文件操作

SQR 允許您讀取和寫入外部文件，這對於數據導入、導出和日誌記錄非常有用。

#### 打開和關閉文件：

```
begin-procedure file-operations
  let $filename = 'output.txt'

  ! 打開文件進行寫入
  open $filename as 1 for-writing record=80

  ! 寫入數據
  write 1 from 'This is a test line'
  write 1 from 'Another line of text'

  ! 關閉文件
  close 1
end-procedure
```

#### 讀取文件：

```
begin-procedure read-file
  let $filename = 'input.txt'
  let #status = 0

  ! 打開文件進行讀取
  open $filename as 1 for-reading record=80 status=#status

  if #status = 0
    while 1
      read 1 into $line status=#status
      if #status <> 0
        break
      end-if
      print $line (+1,1)
    end-while
    close 1
  else
    print 'Error opening file: ' || $filename (,1)
  end-if
end-procedure
```

#### CSV 文件處理：

```
begin-procedure export-to-csv
  let $csv_file = 'employees.csv'

  open $csv_file as 1 for-writing record=1000

  ! 寫入標題行
  let $header = 'Employee ID,Name,Department,Salary'
  write 1 from $header

begin-select
employee_id    &employee_id
name           &name
department     &department
salary         &salary

  let $line = &employee_id || ',' || &name || ',' || &department || ',' || &salary
  write 1 from $line
from employees
end-select

  close 1
end-procedure
```

### 圖表生成

SQR 可以生成簡單的圖表，幫助可視化數據。

#### 條形圖：

```
begin-procedure create-bar-chart
  print 'Department Salary Chart' (1,center) bold
  position (+3)

begin-select
department     &department
sum(salary)    &total_salary

  let #scale = &total_salary / 1000  ! 每 1000 單位顯示一個 *
  let #bars = round(#scale, 0)

  print &department (,1)
  print ' ' (,20)

  let #i = 0
  while #i < #bars
    print '*' ()
    let #i = #i + 1
  end-while

  print ' ' ()
  print &total_salary () edit $9,999,999

  position (+1)
from employees
group by department
order by sum(salary) desc
end-select
end-procedure
```

#### 簡單餅圖：

```
begin-procedure create-pie-chart
  print 'Employee Distribution by Department' (1,center) bold
  position (+3)

  ! 獲取總員工數
  let #total_employees = 0

begin-select
count(*) &total
  let #total_employees = &total
from employees
end-select

  ! 計算每個部門的百分比
begin-select
department     &department
count(*)       &dept_count

  let #percentage = (&dept_count / #total_employees) * 100
  let #stars = round(#percentage / 2, 0)  ! 每 2% 顯示一個 *

  print &department (,1)
  print ' ' (,20)

  let #i = 0
  while #i < #stars
    print '*' ()
    let #i = #i + 1
  end-while

  print ' ' ()
  print #percentage () edit 999.9
  print '%' ()
  print ' (' ()
  print &dept_count ()
  print ' employees)' ()

  position (+1)
from employees
group by department
order by count(*) desc
end-select
end-procedure
```

### 自定義布局

SQR 允許您創建複雜的報表布局，包括多列、表格和自定義頁眉頁腳。

#### 多列報表：

```
begin-procedure multi-column-report
  let #col_width = 40
  let #current_col = 1
  let #max_cols = 2
  let #row = 5

begin-select
employee_id    &employee_id
name           &name
department     &department

  if #current_col > #max_cols
    let #current_col = 1
    let #row = #row + 4
  end-if

  let #col_pos = (#current_col - 1) * #col_width + 1

  print 'ID: ' || &employee_id (#row, #col_pos)
  print 'Name: ' || &name (#row+1, #col_pos)
  print 'Dept: ' || &department (#row+2, #col_pos)

  let #current_col = #current_col + 1
from employees
end-select
end-procedure
```

#### 自定義頁眉：

```
begin-heading 8
  print 'CONFIDENTIAL' (1,center) bold
  print '=' (2,1,80) fill

  print 'EMPLOYEE SALARY REPORT' (4,center) bold

  print 'Company: XYZ Corporation' (6,1)
  print 'Date: ' (6,50)
  print $current-date (6,56)

  print 'Page: ' (6,70)
  page-number (6,76)

  print '-' (8,1,80) fill
end-heading
```

#### 條件格式化：

```
begin-procedure conditional-formatting
begin-select
employee_id    &employee_id
name           &name
salary         &salary
hire_years     &hire_years

  print &employee_id (,1)
  print &name (,10)

  ! 根據薪資應用不同格式
  if &salary < 50000
    print &salary (,40) edit $999,999.99
  else
    if &salary < 100000
      print &salary (,40) edit $999,999.99 bold
    else
      print &salary (,40) edit $999,999.99 bold italic
    end-if
  end-if

  ! 根據服務年限應用不同顏色（僅在支持顏色的環境中）
  if &hire_years < 2
    print 'New Hire' (,60)
  else
    if &hire_years < 5
      print 'Experienced' (,60) bold
    else
      if &hire_years < 10
        print 'Senior' (,60) bold
      else
        print 'Veteran' (,60) bold italic
      end-if
    end-if
  end-if

  position (+1)
from employees
end-select
end-procedure
```

### 參數處理

SQR 允許您定義和處理運行時參數，使報表更加靈活。

#### 定義參數：

```
begin-setup
  declare-parameter
    dept_id  = number
    start_date = string
    end_date = string
    include_inactive = string
  end-declare
end-setup
```

#### 獲取參數值：

```
begin-procedure get-parameters
  input $dept_id 'Enter Department ID (0 for all)'
  let #dept_id = to_number($dept_id)

  input $start_date 'Enter Start Date (YYYY-MM-DD)'
  input $end_date 'Enter End Date (YYYY-MM-DD)'

  input $include_inactive 'Include Inactive Employees? (Y/N)'
  let $include_inactive = upper($include_inactive)
end-procedure
```

#### 使用參數構建查詢：

```
begin-procedure build-query
  let $where_clause = ''

  if #dept_id <> 0
    let $where_clause = 'WHERE department_id = ' || to_char(#dept_id)
  else
    let $where_clause = 'WHERE 1=1'
  end-if

  if $start_date <> ''
    let $where_clause = $where_clause || ' AND hire_date >= ''' || $start_date || ''''
  end-if

  if $end_date <> ''
    let $where_clause = $where_clause || ' AND hire_date <= ''' || $end_date || ''''
  end-if

  if $include_inactive <> 'Y'
    let $where_clause = $where_clause || ' AND status = ''ACTIVE'''
  end-if

  let $sql_query = 'SELECT employee_id, name, department, hire_date, status FROM employees ' || $where_clause

  show $sql_query  ! 顯示生成的 SQL 查詢
end-procedure
```

### 錯誤處理

有效的錯誤處理對於創建健壯的 SQR 程序至關重要。

#### 檢查 SQL 錯誤：

```
begin-procedure check-sql-error
begin-sql
  SELECT employee_id, name
  FROM employees
  WHERE department_id = :dept_id
end-sql

  if sqlcode <> 0
    print 'SQL Error: ' || sqlerrtext (,1)
    stop
  end-if
end-procedure
```

#### 使用 on-error：

```
begin-procedure handle-errors
  let #status = 0

  open 'nonexistent_file.txt' as 1 for-reading record=80 status=#status on-error=file-error

  if #status = 0
    ! 文件成功打開，進行處理
    close 1
  end-if
end-procedure

begin-procedure file-error
  print 'Error opening file. Status code: ' || to_char(#status) (,1)
  ! 執行錯誤恢復操作
end-procedure
```

#### 自定義錯誤處理：

```
begin-procedure validate-input
  if #dept_id < 0
    do log-error('Invalid department ID: ' || to_char(#dept_id))
    let #dept_id = 0  ! 設置默認值
  end-if

  if $start_date > $end_date and $end_date <> ''
    do log-error('Start date cannot be after end date')
    let $start_date = $end_date  ! 修正日期
  end-if
end-procedure

begin-procedure log-error($message)
  print 'ERROR: ' || $message (,1)

  ! 寫入錯誤日誌文件
  let $log_file = 'error_log.txt'
  open $log_file as 1 for-append record=200

  let $timestamp = datenow()
  let $log_entry = $timestamp || ' - ' || $message
  write 1 from $log_entry

  close 1
end-procedure
```

### 中級練習

以下是一些幫助您掌握 SQR 中級功能的練習：

#### 練習 1：參數化部門報表

創建一個 SQR 程序，接受部門 ID 和日期範圍參數，生成該部門在指定日期範圍內的員工薪資報表。

```
!----- department_report.sqr -----
begin-program
  do main
end-program

begin-procedure main
  do get-parameters
  do init
  do print-header
  do process-employees
  do print-footer
end-procedure

begin-procedure get-parameters
  input $dept_id 'Enter Department ID (0 for all)'
  let #dept_id = to_number($dept_id)

  input $start_date 'Enter Start Date (YYYY-MM-DD)'
  input $end_date 'Enter End Date (YYYY-MM-DD)'
end-procedure

begin-procedure init
  let $current-date = datenow()
  let $current-date = strtodate($current-date, 'YYYY-MM-DD')
  let #total_salary = 0
  let #employee_count = 0
end-procedure

begin-procedure print-header
  print 'Department Salary Report' (1,center) bold

  if #dept_id <> 0
    print 'Department ID: ' || to_char(#dept_id) (3,1)
  else
    print 'All Departments' (3,1)
  end-if

  print 'Date Range: ' (4,1)
  if $start_date <> ''
    print $start_date (4,13)
  else
    print 'All' (4,13)
  end-if
  print ' to ' (4,)
  if $end_date <> ''
    print $end_date (4,)
  else
    print 'Present' (4,)
  end-if

  print 'Page: ' (4,65)
  page-number (4,71)

  print 'Employee ID' (7,1)
  print 'Name' (7,15)
  print 'Hire Date' (7,40)
  print 'Salary' (7,60)

  print '-' (8,1,75) fill
end-procedure

begin-procedure process-employees
  position (+2)

  ! 構建 WHERE 子句
  let $where_clause = ''

  if #dept_id <> 0
    let $where_clause = 'WHERE department_id = ' || to_char(#dept_id)
  else
    let $where_clause = 'WHERE 1=1'
  end-if

  if $start_date <> ''
    let $where_clause = $where_clause || ' AND hire_date >= ''' || $start_date || ''''
  end-if

  if $end_date <> ''
    let $where_clause = $where_clause || ' AND hire_date <= ''' || $end_date || ''''
  end-if

  ! 動態構建 SQL 查詢
  let $sql_query = 'SELECT employee_id, first_name, last_name, hire_date, salary FROM employees ' || $where_clause || ' ORDER BY hire_date'

  ! 執行動態 SQL
begin-select
employee_id    &employee_id
first_name     &first_name
last_name      &last_name
hire_date      &hire_date
salary         &salary

  print &employee_id (,1)
  print &first_name || ' ' || &last_name (,15)
  print &hire_date (,40)
  print &salary (,60) edit $999,999.99

  let #total_salary = #total_salary + &salary
  let #employee_count = #employee_count + 1
  position (+1)
from employees
where 1=1
  [$where_clause]  ! 使用動態 WHERE 子句
order by hire_date
end-select
end-procedure

begin-procedure print-footer
  print '-' (+2,1,75) fill
  print 'Total Employees: ' (+2,1)
  print #employee_count (,20)
  print 'Total Salary: ' (+2,40)
  print #total_salary (,60) edit $9,999,999.99

  if #employee_count > 0
    print 'Average Salary: ' (+2,40)
    print #total_salary / #employee_count (,60) edit $9,999,999.99
  end-if

  print 'End of Report' (+2,center)
end-procedure
```

#### 練習 2：多層次報表

創建一個 SQR 程序，生成一個按部門和職位分組的員工報表，包括小計和總計。

```
!----- hierarchical_report.sqr -----
begin-program
  do main
end-program

begin-procedure main
  do init
  do print-header
  do process-departments
  do print-footer
end-procedure

begin-procedure init
  let $current-date = datenow()
  let $current-date = strtodate($current-date, 'YYYY-MM-DD')
  let #grand_total = 0
  let #grand_count = 0
end-procedure

begin-procedure print-header
  print 'Hierarchical Employee Report' (1,center) bold
  print 'Date: ' (3,1)
  print $current-date (3,7)
  print 'Page: ' (3,65)
  page-number (3,71)

  print '-' (5,1,80) fill
end-procedure

begin-procedure process-departments
  position (+2)

begin-select
department     &department

  print 'Department: ' || &department (,1) bold
  position (+1)

  let #dept_total = 0
  let #dept_count = 0

  ! 處理該部門的職位
  do process-jobs(&department)

  print 'Department Total: ' (+1,40) bold
  print #dept_count (,60)
  print #dept_total (,70) edit $9,999,999.99

  let #grand_total = #grand_total + #dept_total
  let #grand_count = #grand_count + #dept_count

  position (+2)
from departments
order by department
end-select
end-procedure

begin-procedure process-jobs($dept)
begin-select
job_title      &job_title

  print '  Job: ' || &job_title (,1)
  position (+1)

  let #job_total = 0
  let #job_count = 0

  ! 處理該職位的員工
  do process-employees($dept, &job_title)

  print '  Job Total: ' (+1,40)
  print #job_count (,60)
  print #job_total (,70) edit $999,999.99

  let #dept_total = #dept_total + #job_total
  let #dept_count = #dept_count + #job_count

  position (+2)
from jobs j
where exists (select 1 from employees e 
              where e.department = $dept 
              and e.job_title = j.job_title)
order by job_title
end-select
end-procedure

begin-procedure process-employees($dept, $job)
  print '    ID' (,1)
  print 'Name' (,15)
  print 'Hire Date' (,40)
  print 'Salary' (,60)
  print '-' (+1,1,75) fill
  position (+1)

begin-select
employee_id    &employee_id
first_name     &first_name
last_name      &last_name
hire_date      &hire_date
salary         &salary

  print &employee_id (,1)
  print &first_name || ' ' || &last_name (,15)
  print &hire_date (,40)
  print &salary (,60) edit $999,999.99

  let #job_total = #job_total + &salary
  let #job_count = #job_count + 1
  position (+1)
from employees
where department = $dept
and job_title = $job
order by last_name
end-select
end-procedure

begin-procedure print-footer
  print '=' (+2,1,80) fill
  print 'Grand Total: ' (+2,40) bold
  print #grand_count (,60) bold
  print #grand_total (,70) bold edit $99,999,999.99

  if #grand_count > 0
    print 'Overall Average: ' (+2,40)
    print #grand_total / #grand_count (,70) edit $999,999.99
  end-if

  print 'End of Report' (+2,center)
end-procedure
```

完成這些練習後，您將對 SQR 的中級功能有更深入的了解。接下來，您可以繼續學習高級功能。

## 高級教學

本節適合已經掌握 SQR 基礎和中級功能的用戶。我們將深入探討性能優化、故障排除和高級管理技術，幫助您解決複雜的報表需求和問題。

### 性能優化

優化 SQR 程序的性能對於處理大量數據和滿足嚴格的運行時間要求至關重要。

#### SQL 優化：

```
! 不良實踐：在循環中執行 SQL
let #i = 1
while #i <= 1000
begin-select
  salary &salary
  from employees
  where employee_id = #i
end-select
  let #i = #i + 1
end-while

! 良好實踐：一次獲取所有數據
begin-select
employee_id &employee_id
salary &salary
  ! 在這裡處理數據
from employees
where employee_id <= 1000
end-select
```

#### 數組批處理：

```
begin-procedure batch-processing
  ! 一次性加載數據到數組
  do load-data-to-array

  ! 在內存中處理數據
  do process-array-data

  ! 一次性更新數據庫
  do update-database
end-procedure
```

#### 減少 I/O 操作：

```
begin-procedure optimize-io
  ! 不良實踐：頻繁打開和關閉文件
  let #i = 1
  while #i <= 1000
    open 'output.txt' as 1 for-append
    write 1 from 'Line ' || to_char(#i)
    close 1
    let #i = #i + 1
  end-while

  ! 良好實踐：保持文件打開
  open 'output.txt' as 1 for-writing
  let #i = 1
  while #i <= 1000
    write 1 from 'Line ' || to_char(#i)
    let #i = #i + 1
  end-while
  close 1
end-procedure
```

#### 使用索引：

```
! 確保查詢使用索引
begin-select
e.employee_id
e.name
e.salary
  ! 處理數據
from employees e
where e.department_id = 10  ! 假設 department_id 有索引
order by e.employee_id      ! 假設 employee_id 是主鍵
end-select
```

#### 內存管理：

```
begin-procedure memory-management
  ! 釋放不再需要的大型數組
  clear-array name=employee_array

  ! 在處理大量數據時分批處理
  let #batch_size = 1000
  let #offset = 0

  while 1
  begin-select
  employee_id &employee_id
  name &name
  salary &salary
    ! 處理當前批次的數據
  from employees
  where rownum > #offset and rownum <= #offset + #batch_size
  end-select

    if sqlrows = 0
      break
    end-if

    let #offset = #offset + #batch_size
  end-while
end-procedure
```

### 故障排除

有效的故障排除技能可以幫助您快速識別和解決 SQR 程序中的問題。

#### 常見錯誤類型：

1. **語法錯誤**：代碼不符合 SQR 語法規則
2. **運行時錯誤**：程序執行過程中發生的錯誤
3. **邏輯錯誤**：程序運行但結果不正確
4. **性能問題**：程序運行緩慢或消耗過多資源

#### 診斷工具：

```
begin-procedure diagnostic-tools
  ! 使用 show 命令顯示變量值
  show 'Debug: employee_id = ' || to_char(#employee_id)

  ! 顯示 SQL 語句
  show $sql_query

  ! 顯示執行時間
  let #start_time = datenow()
  ! 執行某些操作
  let #end_time = datenow()
  let #elapsed = datediff('ss', #start_time, #end_time)
  show 'Execution time: ' || to_char(#elapsed) || ' seconds'

  ! 檢查 SQL 錯誤
  if sqlcode <> 0
    show 'SQL Error: ' || sqlerrtext
  end-if
end-procedure
```

#### 分段測試：

```
begin-procedure incremental-testing
  ! 測試數據庫連接
  do test-database-connection

  ! 測試 SQL 查詢
  do test-sql-query

  ! 測試數據處理
  do test-data-processing

  ! 測試報表格式
  do test-report-formatting
end-procedure
```

#### 錯誤日誌：

```
begin-procedure error-logging
  let $log_file = 'sqr_error.log'

  ! 創建詳細的錯誤日誌
  open $log_file as 1 for-append

  write 1 from '=== Error Report ==='
  write 1 from 'Date/Time: ' || datenow()
  write 1 from 'Program: ' || $program_name
  write 1 from 'User: ' || $user_id
  write 1 from 'Error: ' || $error_message
  write 1 from 'SQL Code: ' || to_char(sqlcode)
  write 1 from 'SQL Error: ' || sqlerrtext
  write 1 from 'Context: ' || $context
  write 1 from '===================='

  close 1
end-procedure
```

#### 常見問題解決方案：

1. **"Column not found" 錯誤**：
    - 檢查列名拼寫
    - 確認表名和別名
    - 驗證列是否存在於表中

2. **內存不足錯誤**：
    - 減少數組大小
    - 分批處理數據
    - 優化 SQL 查詢減少結果集大小

3. **性能緩慢**：
    - 檢查 SQL 查詢是否使用索引
    - 避免在循環中執行 SQL
    - 減少 I/O 操作
    - 使用批處理技術

4. **報表格式問題**：
    - 檢查頁面設置
    - 驗證位置坐標
    - 確認字體和樣式支持

### 最佳實踐

遵循最佳實踐可以幫助您創建高質量、易於維護的 SQR 程序。

#### 代碼組織：

```
! 良好的代碼組織結構
begin-program
  do main
end-program

begin-procedure main
  do initialization
  do process-data
  do finalization
end-procedure

begin-procedure initialization
  ! 初始化變量和環境
end-procedure

begin-procedure process-data
  ! 處理主要業務邏輯
end-procedure

begin-procedure finalization
  ! 清理和結束處理
end-procedure
```

#### 命名約定：

```
! 使用一致的命名約定
! 變量
let $employee_name = 'John Smith'  ! 字符串變量使用 $
let #employee_count = 0            ! 數字變量使用 #

! 過程
begin-procedure calculate_total_salary  ! 使用下劃線分隔單詞
  ! 過程內容
end-procedure

! SQL 別名
begin-select
e.employee_id  &emp_id   ! 列變量使用 &
e.name         &emp_name
from employees e         ! 表別名簡短但有意義
end-select
```

#### 註釋和文檔：

```
!------------------------------------------------------
! 程序: employee_report.sqr
! 描述: 生成員工薪資報表
! 作者: John Doe
! 日期: 2023-05-15
! 參數:
!   - dept_id: 部門 ID (0 表示所有部門)
!   - as_of_date: 截止日期 (YYYY-MM-DD)
!------------------------------------------------------
begin-program
  do main
end-program

!------------------------------------------------------
! 主過程
! 控制程序的整體流程
!------------------------------------------------------
begin-procedure main
  ! 獲取參數
  do get-parameters

  ! 初始化變量
  let #total = 0  ! 用於存儲總薪資

  ! 處理數據並生成報表
  do process-data
end-procedure
```

#### 錯誤處理策略：

```
begin-procedure robust-error-handling
  ! 使用 on-error 子句
  open $filename as 1 for-reading status=#status on-error=file-error

  ! 檢查返回狀態
  if #status <> 0
    do handle-error('File open failed', #status)
    return
  end-if

  ! 使用 try-catch 風格的錯誤處理
  let #error_occurred = 0
  do risky-operation
  if #error_occurred
    do recovery-procedure
  end-if
end-procedure

begin-procedure risky-operation
  ! 可能失敗的操作
  if [某些錯誤條件]
    let #error_occurred = 1
    let $error_message = '操作失敗'
  end-if
end-procedure
```

#### 模塊化設計：

```
! 創建可重用的模塊
begin-procedure format-currency(#amount, :#formatted_amount)
  let #integer_part = trunc(#amount)
  let #decimal_part = #amount - #integer_part
  let #decimal_part = round(#decimal_part * 100)

  let $integer_string = edit(#integer_part, '999,999,999')
  let $decimal_string = edit(#decimal_part, '09')

  let #formatted_amount = $integer_string || '.' || $decimal_string
end-procedure

! 使用可重用模塊
let #salary = 1234.56
let $formatted_salary = ''
do format-currency(#salary, $formatted_salary)
print 'Salary: $' || $formatted_salary (,1)
```

### 安全性考量

在開發 SQR 程序時，安全性是一個重要的考慮因素。

#### SQL 注入防護：

```
! 不安全的做法（容易受到 SQL 注入攻擊）
let $where_clause = 'WHERE employee_id = ' || $user_input

! 安全的做法（使用綁定變量）
begin-select
employee_id
name
salary
from employees
where employee_id = :user_input  ! 使用綁定變量
end-select
```

#### 敏感數據處理：

```
begin-procedure handle-sensitive-data
  ! 掩碼敏感信息
  let $ssn = '123-45-6789'
  let $masked_ssn = 'XXX-XX-' || substr($ssn, 8, 4)
  print $masked_ssn (,1)

  ! 限制敏感數據的顯示
  if #user_security_level >= 5
    print 'Salary: ' || edit(#salary, '$999,999.99') (,1)
  else
    print 'Salary: [Restricted]' (,1)
  end-if
end-procedure
```

#### 文件安全：

```
begin-procedure secure-file-handling
  ! 驗證文件路徑
  if instr($file_path, '..') > 0
    print 'Error: Invalid file path' (,1)
    stop
  end-if

  ! 使用絕對路徑
  let $safe_path = '/app/data/reports/'
  let $full_path = $safe_path || $filename

  ! 檢查文件權限
  ! (這裡需要系統特定的實現)
end-procedure
```

#### 用戶認證：

```
begin-procedure authenticate-user
  ! 獲取用戶憑據
  input $username 'Enter username'
  input $password 'Enter password' no-echo

  ! 驗證用戶
begin-select
security_level &security_level
from users
where username = :username
and password = :encrypted_password  ! 實際應用中應使用加密密碼
end-select

  if sqlrows = 0
    print 'Authentication failed' (,1)
    stop
  else
    let #user_security_level = &security_level
  end-if
end-procedure
```

### 與其他系統集成

SQR 可以與其他系統和技術集成，擴展其功能。

#### 調用外部程序：

```
begin-procedure call-external-program
  ! 使用 call 命令執行外部程序
  call system using 'dir'

  ! 執行帶參數的命令
  let $command = 'python process_data.py ' || $input_file || ' ' || $output_file
  call system using $command status=#return_code

  if #return_code <> 0
    print 'External program failed with code: ' || to_char(#return_code) (,1)
  end-if
end-procedure
```

#### 導入/導出數據：

```
begin-procedure import-csv-data
  let $csv_file = 'employees.csv'
  open $csv_file as 1 for-reading record=1000

  ! 跳過標題行
  read 1 into $header

  while 1
    read 1 into $line status=#status
    if #status <> 0
      break
    end-if

    ! 解析 CSV 行
    let $employee_id = substr($line, 1, instr($line, ',') - 1)
    let $rest = substr($line, instr($line, ',') + 1)
    let $name = substr($rest, 1, instr($rest, ',') - 1)
    let $rest = substr($rest, instr($rest, ',') + 1)
    let $salary = $rest

    ! 處理數據
    print $employee_id (,1)
    print $name (,15)
    print $salary (,40)
    position (+1)
  end-while

  close 1
end-procedure
```

#### 生成 XML/JSON：

```
begin-procedure generate-xml
  let $xml_file = 'employees.xml'
  open $xml_file as 1 for-writing

  ! 寫入 XML 頭
  write 1 from '<?xml version="1.0" encoding="UTF-8"?>'
  write 1 from '<employees>'

begin-select
employee_id &employee_id
name &name
department &department
salary &salary

  ! 為每個員工生成 XML 元素
  write 1 from '  <employee>'
  write 1 from '    <id>' || &employee_id || '</id>'
  write 1 from '    <name>' || &name || '</name>'
  write 1 from '    <department>' || &department || '</department>'
  write 1 from '    <salary>' || &salary || '</salary>'
  write 1 from '  </employee>'
from employees
end-select

  ! 寫入 XML 尾
  write 1 from '</employees>'
  close 1
end-procedure
```

### 大數據處理

處理大量數據需要特殊的技術和策略。

#### 分批處理：

```
begin-procedure process-large-dataset
  let #batch_size = 10000
  let #offset = 0
  let #total_processed = 0

  ! 打開輸出文件
  open 'large_report.txt' as 1 for-writing

  ! 分批處理數據
  while 1
    let #current_batch = 0

begin-select
employee_id &employee_id
name &name
salary &salary

    ! 處理當前記錄
    write 1 from &employee_id || ',' || &name || ',' || &salary

    ! 計數
    let #current_batch = #current_batch + 1
    let #total_processed = #total_processed + 1
from employees
where employee_id > #offset
and employee_id <= #offset + #batch_size
order by employee_id
end-select

    ! 檢查是否處理完畢
    if #current_batch = 0
      break
    end-if

    ! 更新偏移量
    let #offset = #offset + #batch_size

    ! 顯示進度
    print 'Processed ' || to_char(#total_processed) || ' records...' (+1,1)
  end-while

  ! 關閉輸出文件
  close 1

  print 'Total records processed: ' || to_char(#total_processed) (+2,1)
end-procedure
```

#### 並行處理：

```
begin-procedure parallel-processing
  ! 注意：SQR 本身不直接支持多線程，但可以通過生成多個獨立的 SQR 進程來實現並行處理

  ! 生成多個處理不同數據範圍的腳本
  let #num_partitions = 4
  let #partition_size = 25000

  let #i = 0
  while #i < #num_partitions
    let #start_id = #i * #partition_size + 1
    let #end_id = (#i + 1) * #partition_size

    ! 創建配置文件
    let $config_file = 'partition_' || to_char(#i) || '.cfg'
    open $config_file as 1 for-writing
    write 1 from 'start_id=' || to_char(#start_id)
    write 1 from 'end_id=' || to_char(#end_id)
    close 1

    ! 啟動獨立進程
    let $command = 'sqr process_partition.sqr ' || $config_file || ' &'
    call system using $command

    let #i = #i + 1
  end-while

  ! 等待所有進程完成
  ! (這需要特定於系統的實現)

  ! 合併結果
  do merge-results(#num_partitions)
end-procedure
```

#### 內存優化：

```
begin-procedure memory-optimization
  ! 使用流處理而不是加載全部數據到內存

  ! 不良實踐：將所有數據加載到數組
  do load-all-data-to-array
  do process-array

  ! 良好實踐：流處理
begin-select
employee_id &employee_id
name &name
salary &salary

  ! 立即處理每條記錄，不存儲在內存中
  do process-record(&employee_id, &name, &salary)
from employees
order by employee_id
end-select
end-procedure
```

### 高級案例研究

以下案例研究展示了如何在實際情況中應用 SQR 的高級功能。

#### 案例 1：大型財務報表系統

**問題**：
一家大型企業需要生成複雜的財務報表，包括多個部門、多個時間段的數據，並需要進行各種計算和匯總。報表需要高性能和可靠性。

**解決方案**：

1. **數據分層處理**：
    - 首先處理交易級別數據
    - 然後匯總到部門級別
    - 最後匯總到企業級別

2. **性能優化**：
    - 使用臨時表存儲中間結果
    - 實施批處理策略
    - 優化 SQL 查詢

3. **報表格式**：
    - 創建多級標題和頁腳
    - 實現動態列寬和頁面佈局
    - 添加圖表和視覺元素

4. **用戶參數**：
    - 允許用戶選擇報表期間
    - 提供部門篩選選項
    - 支持不同的報表格式（詳細/摘要）

**實現摘要**：

```
begin-program
  do main
end-program

begin-procedure main
  do get-parameters
  do initialize
  do process-financial-data
  do generate-report
  do cleanup
end-procedure

begin-procedure process-financial-data
  ! 處理交易級別數據
  do process-transactions

  ! 匯總到部門級別
  do summarize-by-department

  ! 匯總到企業級別
  do summarize-company-totals
end-procedure
```

**結果**：

- 報表生成時間從 45 分鐘減少到 8 分鐘
- 內存使用減少 70%
- 用戶可以自定義報表參數，無需修改代碼
- 報表格式專業，易於閱讀和分析

#### 案例 2：複雜的人力資源報表

**問題**：
人力資源部門需要一個報表，顯示員工的薪資歷史、晉升記錄和績效評估，並根據不同的安全級別顯示不同的信息。

**解決方案**：

1. **數據整合**：
    - 從多個表中獲取數據（員工、薪資、職位、績效）
    - 使用複雜的 JOIN 和子查詢
    - 實現數據轉換和清理

2. **安全實現**：
    - 基於用戶角色實現行級安全
    - 對敏感數據（如薪資）進行掩碼
    - 記錄報表訪問日誌

3. **動態格式**：
    - 根據用戶角色調整報表內容
    - 使用條件格式突出顯示重要信息
    - 提供多種輸出格式（PDF、Excel、HTML）

**實現摘要**：

```
begin-procedure process-employee-data
  ! 獲取用戶安全級別
  do get-user-security-level

  ! 根據安全級別構建查詢
  if #security_level >= 5
    let $salary_select = 'e.salary, e.bonus, e.stock_options'
  else
    if #security_level >= 3
      let $salary_select = 'e.salary'
    else
      let $salary_select = '''Restricted'''
    end-if
  end-if

  ! 動態構建 SQL
  let $sql = 'SELECT e.employee_id, e.name, e.department, ' || $salary_select || 
             ' FROM employees e JOIN performance p ON e.employee_id = p.employee_id ' ||
             ' WHERE e.status = ''ACTIVE'''

  ! 執行查詢並生成報表
  ! ...
end-procedure
```

**結果**：

- 單一報表滿足了多個用戶組的需求
- 敏感數據得到適當保護
- 報表生成速度快，即使處理大量數據
- 用戶反饋非常正面，報表易於理解和使用

#### 案例 3：實時數據集成

**問題**：
需要一個系統，能夠從多個外部源獲取數據，處理這些數據，並生成實時報表。

**解決方案**：

1. **數據獲取**：
    - 使用 SQR 從多個數據庫和 API 獲取數據
    - 實現錯誤重試和恢復機制
    - 記錄數據獲取過程

2. **數據處理**：
    - 清理和轉換來自不同源的數據
    - 解決數據不一致和衝突
    - 應用業務規則和驗證

3. **報表生成**：
    - 創建實時儀表板報表
    - 實現數據可視化和圖表
    - 提供警報和通知功能

**實現摘要**：

```
begin-procedure fetch-external-data
  ! 從多個源獲取數據
  do fetch-from-database-a
  do fetch-from-api-b
  do fetch-from-file-c

  ! 處理和整合數據
  do clean-and-transform-data
  do resolve-conflicts
  do apply-business-rules

  ! 生成報表
  do generate-dashboard
  do send-alerts
end-procedure
```

**結果**：

- 數據整合過程從手動操作變為自動化
- 報表生成時間從數小時減少到幾分鐘
- 數據質量顯著提高
- 業務決策更加及時和準確

通過這些案例研究，您可以看到如何在實際情況中應用 SQR 的高級功能，解決複雜的業務和技術挑戰。這些經驗和技巧將幫助您成為 SQR 開發的專家。

## 總結

在本教學文件中，我們從初級到高級全面介紹了 Oracle PeopleSoft SQR 工具。我們從基本概念開始，逐步深入到複雜的功能和技術，幫助您掌握這個強大的報表開發工具。

### 學習路徑回顧

1. **初級階段**：
    - 了解 SQR 的基本概念和語法
    - 學習如何創建簡單的報表
    - 掌握變量、條件語句和循環結構
    - 實踐基本的 SQL 查詢和報表格式化

2. **中級階段**：
    - 深入學習高級 SQL 技巧
    - 掌握過程、函數和數組操作
    - 學習文件操作和圖表生成
    - 實現參數處理和錯誤處理

3. **高級階段**：
    - 優化 SQR 程序性能
    - 學習故障排除和最佳實踐
    - 考慮安全性和與其他系統的集成
    - 處理大數據和複雜報表需求

### 持續學習

SQR 是一個豐富而強大的工具，本教學文件只是一個開始。要繼續提高您的 SQR 技能，建議：

1. **實踐**：通過實際項目應用所學知識
2. **探索**：嘗試本教學中未涵蓋的高級功能
3. **分享**：與其他 SQR 開發者交流經驗和技巧
4. **更新**：關注 Oracle PeopleSoft 的最新更新和最佳實踐

### 資源推薦

為了進一步學習 SQR，以下資源可能對您有所幫助：

1. Oracle PeopleSoft 官方文檔
2. SQR 參考手冊
3. PeopleSoft 社區論壇
4. 專業培訓課程和認證

無論您是初學者還是有經驗的開發者，我們希望本教學文件能夠幫助您更好地理解和使用 SQR，創建高質量、高性能的報表，滿足您組織的需求。
