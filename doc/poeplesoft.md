現在有一個peoplecode程式, 它會處理多個 sql 與其他複雜邏輯,
現在部署在 sit、uat 中, sit 的硬體規格<uat, 兩個環境的資料量相同
但是 sit 的執行耗時<uat, 我該如何排查問題?

針對您所描述的情境，PeopleCode 程式在 SIT（系統整合測試環境）執行速度快於 UAT（使用者驗證測試環境），雖然 SIT 的硬體規格較差但資料量相同，這種情況顯示可能與下列因素有關：

---

### 一、**潛在問題點與排查方向**

#### 1. **資料庫統計資訊（Statistics）**
- **問題描述**：UAT 資料庫可能未定期收集統計資訊，導致 SQL 執行計劃（Execution Plan）不佳。
- **排查建議**：比較兩邊資料庫中目標表格的統計資訊與 SQL 執行計劃。確認是否存在 Table Scan 或 Index 選用差異。

#### 2. **資料庫參數或資源限制**
- **問題描述**：UAT 資料庫可能啟用了不同的資源控制（如 CPU 限制、記憶體限制）或 Query Governor。
- **排查建議**：檢查 UAT 資料庫參數、資源限制（如 PGA、SGA 設定、Parallelism），是否有 throttling、timeout 等限制。

#### 3. **PeopleCode 或 App Server 層級配置**
- **問題描述**：UAT 的 App Server 或 Process Scheduler 設定（如 Connection Pool、Max Concurrent）可能與 SIT 不一致。
- **排查建議**：比較兩邊的 App Server `psappsrv.cfg` 與 Process Scheduler `psprcs.cfg`，特別是 DB Connection Pool、JVM Memory 設定。

#### 4. **SQL 執行計劃不同**
- **問題描述**：相同的 SQL 在不同環境下，可能因為 Bind Variable Sniffing 或硬解析而產生不同計劃。
- **排查建議**：抓取實際執行 SQL 的 Explain Plan 或用 SQL Trace / Oracle AWR Report 來比對。

#### 5. **資料分佈雖等量但不均勻**
- **問題描述**：即使資料量相同，資料分佈或索引碎裂度不同也會影響查詢效能。
- **排查建議**：檢查表格與索引的碎裂度、是否有 Rebuild 索引、Analyze Table。

---

### 二、實務經驗建議

#### ✅ **最佳實務參考：**
1. **定期收集統計資訊與 Rebuild Index**
   - 每次資料批次匯入後，針對關鍵表格重新收集統計資訊。
2. **使用 AWR/ASH 報告進行跨環境比較**
   - Oracle DB 提供 AWR Report 可分析 SQL 執行差異。
3. **模擬問題 SQL**
   - 抽出 PeopleCode 產生的關鍵 SQL，在兩環境分別執行，確認執行計劃與耗時差異。

---

### 三、建議執行順序

1. 抓出關鍵 SQL（可以啟用 TraceSQL 選項）。
2. 在 SIT/UAT 比較執行計劃（Explain Plan）。
3. 比對統計資訊與資料庫參數。
4. 若需要，使用 AWR 或 SQL Profiler 詳查瓶頸。

請問目前您是否已經有辦法擷取 SQL 或查看 Execution Plan？我可以進一步協助您分析該 SQL 是否有優化空間。

有辦法擷取sql和查看execution plan

很好，既然您能擷取 SQL 並查看 Execution Plan，我建議您採取以下具體步驟來定位並解決 UAT 執行較慢的問題。

---

### 🔍【步驟一：擷取與驗證 SQL】

1. **啟用 PeopleCode Trace 選項**（如 TraceSQL 或 Trace PeopleCode）：
   - `psprcs.cfg`：開啟 `TraceSQL=7` 或更高。
   - 或在 Process Scheduler 或 Component 中設定 Trace。

2. **取得慢的 SQL 語句與 Bind Variables 值**（避免模糊查詢）。

3. **在 UAT 與 SIT 各自執行 SQL 並取得 Execution Plan**：
   - Oracle 可用：`EXPLAIN PLAN FOR <your_sql>;`
   - 或使用 `DBMS_XPLAN.DISPLAY_CURSOR`。

---

### 🧠【步驟二：比較 Execution Plan 的關鍵差異】

重點觀察以下欄位：

| 項目                 | 關注點說明 |
|----------------------|------------|
| Cost / Cardinality    | 預估成本與行數差異是否過大？ |
| Access Path（如 Index Scan / Full Table Scan） | 是否因缺乏統計資料選錯 Index？ |
| Filter/Join Order     | 是否 Join 順序與方式改變？例如 Nested Loop → Hash Join？ |
| Partition Pruning     | 有無使用 Partition？是否有效？ |

---

### 🔁【步驟三：修復方向建議】

#### 🔧 若 Execution Plan 不同
- 強烈建議 **重新收集統計資訊（ANALYZE / DBMS_STATS）**。
- 考慮在問題 SQL 加上 **Hint** 或使用 **SQL Profile** 對其強制使用好的執行計劃。

#### 🧱 若 Execution Plan 相同但效能仍不同
- 執行 SQL Trace（10046 event），觀察 IO、等待事件。
- 確認資源分配是否一致（SGA/PGA、並行數、磁碟 IO）。

---

### 📌 備註：參考資料

- Oracle 官方文件：[DBMS_XPLAN](https://docs.oracle.com/en/database/oracle/oracle-database/19/arpls/DBMS_XPLAN.html)
- AWR 分析範例：[Oracle AWR Report 分析教學](https://oracle-base.com/articles/misc/automatic-workload-repository-awr)

---

如果您方便提供一段實際的 Execution Plan（或比對後的差異），我可以幫您直接分析該處是否有明顯效能瓶頸。是否需要我提供簡易 SQL 比對模板？

額外提供一個資料, sit的執行時間大概是uat的1/10而已, 這樣可以更縮小需排查的範圍嗎?

是的，**SIT 執行時間僅為 UAT 的 1/10**，這種「極大效能差異」代表問題**很可能集中在某一層特定環節**。這可以協助我們**縮小排查範圍**，以下是依據此現象的更具體分析方向：

---

### 🎯【優先排查重點】

#### 1. **SQL Execution Plan 差異（首要懷疑）**
- 這類巨大落差常見於：
  - **Index 未使用 / Full Table Scan**；
  - **Nested Loop 被轉成 Hash Join**；
  - 或因 **錯誤 Cardinality 導致不佳執行順序**。

▶ 建議：
- 比對 Execution Plan 的「行數預估（Rows）」與「成本（Cost）」欄位是否極端誤判。
- 用 `DBMS_XPLAN.DISPLAY_CURSOR` 查看 Runtime Statistics。

#### 2. **資料庫 I/O 等待時間（Wait Events）**
- 若 SQL Plan 相同但 UAT 慢很多，有可能是：
  - 資料檔案或索引檔 I/O 性能差。
  - UAT 機器的 **磁碟、SAN、NAS 延遲高** 或 **I/O Throttling**。

▶ 建議：
- 查詢 Oracle 的 V$SESSION/V$ACTIVE_SESSION_HISTORY 中的 Wait Events。
- 查看是否發生 `db file sequential read` 或 `db file scattered read` 時間過長。

#### 3. **資料庫參數差異（OPTIMIZER_FEATURES_ENABLE 等）**
- 有可能 UAT 的 DB 或環境設參不同，導致 Optimizer 行為不同。

▶ 建議：
- 執行 `SHOW PARAMETER` 或比較 `V$PARAMETER`。

---

### ✅【縮小範圍的具體做法】

1. **擷取該段 SQL 在 UAT 的執行計劃與實際執行時間（用 SQL Monitor 或 SQL Trace）**
2. **將該 SQL 在 SIT/UAT 手動執行（搭配相同 Bind Variables）**
3. **記錄執行時間與 SQL Plan 差異**
4. **比對統計資訊與索引是否一致**

---

### 🔧 可行補救方案預告（視狀況而定）

- 若是統計資訊問題 ➜ `DBMS_STATS.GATHER_TABLE_STATS`。
- 若是執行計劃差異 ➜ `SQL Plan Baseline` 或加上 `HINT`。
- 若是 I/O 問題 ➜ 需 DBA 協助調查 Storage Performance。

---

請問您是否已有擷取到關鍵 SQL？或者是否可以分享一段 UAT 的 Execution Plan 關鍵節點（如 Cost、Rows、Operation）？我可以協助更快速判斷是否就是執行計劃造成的效能瓶頸。

peoplecode 如何添加 log 來輸出每段程式的執行時間

在 PeopleCode 中若要輸出**每段程式的執行時間以作效能分析**，可以利用以下方式進行紀錄：

---

### ✅ **實務做法：使用 `GetCurrentDateTime()` 或 `GetTickCount()`**

#### 1. **用 `GetTickCount()`（精度為毫秒）**
這是 PeopleCode 中最常見的方式，適合進行精準的程式碼段時間分析。

```peoplecode
Local number &startTime, &endTime, &duration;

&startTime = GetTickCount();

/* --- 實際邏輯區塊 --- */

&endTime = GetTickCount();
&duration = &endTime - &startTime;

MessageBox(0, "", 0, 0, "Step 1 耗時: " | &duration | " 毫秒");
```

---

#### 2. **用 `GetCurrentDateTime()`（若需記錄時間點）**
這方式可用來記錄**具體的時間戳記**，但**不適合做精確的時間差計算**，除非轉成毫秒處理。

```peoplecode
Local datetime &ts;
&ts = %Datetime;
MessageBox(0, "", 0, 0, "執行時間點：" | DateTimeToUserLong(&ts));
```

---

### 📄【進階建議：封裝成共用方法】

若有多段邏輯要分析，建議包裝一個簡易工具，例如：

```peoplecode
Function LogTime(&stepDesc As string, &startTime As number) Returns number
   Local number &nowTime, &duration;
   &nowTime = GetTickCount();
   &duration = &nowTime - &startTime;
   MessageBox(0, "", 0, 0, &stepDesc | " 耗時: " | &duration | " 毫秒");
   Return &nowTime;
End-Function;
```

使用方式：

```peoplecode
Local number &timeStart;

&timeStart = GetTickCount();

/* 步驟 1 */
DoSomething();
&timeStart = LogTime("步驟 1", &timeStart);

/* 步驟 2 */
DoSomethingElse();
&timeStart = LogTime("步驟 2", &timeStart);
```

---

### 📌 實務注意事項

- 在 **Process Scheduler** 中建議用 `MessageBox()` 或 `WriteToLog()` 輸出。
- 在 **Component PeopleCode** 中建議用 `MessageBox()` 做初步偵錯。

---

需要的話我可以提供一段完整的效能偵錯框架程式碼，您是否希望這段程式碼可以直接複用在您目前的程式裡？如果能提供目前 PeopleCode 的結構，我可以幫您嵌入時間紀錄點。

我要輸出資料到檔案, 不是messagebox

既然您是在 **PeopleSoft App Engine 批次程式中**執行，以下提供一個**專門針對 App Engine 設計的完整範例**，可輸出每段邏輯執行時間至檔案，並符合 Process Scheduler 的執行權限與路徑規範。

---

### ✅【App Engine 中記錄執行時間至輸出檔】

```peoplecode
/* 宣告區 */
Local File &logFile;
Local number &startTime, &endTime, &duration;
Local string &logPath;

/* 取得 App Engine 的 Output 路徑（由 Process Scheduler 自動處理） */
&logPath = GetEnv("PS_SERVDIR") | "/" | %OperatorId | "_" | %ProcessInstance | "_timing.log";

/* 以附加模式寫入（A） */
&logFile = GetFile(&logPath, "A", %FilePath_Absolute);

If &logFile.IsOpen Then

   /* Step 1 */
   &startTime = GetTickCount();
   /* --- 你的邏輯程式碼 --- */
   &endTime = GetTickCount();
   &duration = &endTime - &startTime;
   &logFile.WriteLine("步驟 1 耗時：" | &duration | " 毫秒");

   /* Step 2 */
   &startTime = GetTickCount();
   /* --- 第二段邏輯程式碼 --- */
   &endTime = GetTickCount();
   &duration = &endTime - &startTime;
   &logFile.WriteLine("步驟 2 耗時：" | &duration | " 毫秒");

   &logFile.Close();

Else
   /* 錯誤處理：寫入 Message Log */
   MessageLog(0, "", 0, "無法開啟記錄檔：" | &logPath);
End-If;
```

---

### 📂【結果位置】

輸出的檔案會放在 PeopleSoft Process Scheduler 的 Output 資料夾中，可透過：
- Process Monitor → View Log/Trace → 找到 `timing.log` 或您設定的檔名

---

### 📌 實務建議

1. 若此記錄為長期用，建議加上參數（如是否啟用記錄）避免寫入過多檔案。
2. 可擴充為通用 Function，例如 `LogDuration("步驟1", &startTime, &logFile)`。

---

是否需要我幫您封裝這段邏輯為可以複用的共用 Function？或者您已經有既有的 App Engine 程式碼結構可以整合？

封裝這段邏輯為可以複用的共用 Function

以下是專為 **PeopleSoft App Engine 批次程式** 設計的共用函式模組，可用來記錄各段邏輯執行時間到檔案中，並支援初始化與結束操作，利於整合到多個批次程式中。

---

### ✅【共用函式模組】

```peoplecode
/* === 共用變數宣告 === */
Global File &gLogFile;
Global number &gStartTime;

/* === 初始化 log 檔案（須於 App Engine 開始時呼叫）=== */
Function InitPerfLog()
   Local string &logPath;
   &logPath = GetEnv("PS_SERVDIR") | "/" | %OperatorId | "_" | %ProcessInstance | "_timing.log";
   &gLogFile = GetFile(&logPath, "A", %FilePath_Absolute);

   If &gLogFile.IsOpen Then
      &gLogFile.WriteLine("==== 執行效能紀錄開始 ====");
   Else
      MessageLog(0, "", 0, "無法開啟效能紀錄檔：" | &logPath);
   End-If;
End-Function;

/* === 記錄單一步驟耗時 === */
Function LogPerfStep(&stepDesc As string)
   Local number &nowTime, &duration;
   &nowTime = GetTickCount();
   &duration = &nowTime - &gStartTime;

   If &gLogFile.IsOpen Then
      &gLogFile.WriteLine(&stepDesc | " 耗時：" | &duration | " 毫秒");
   End-If;

   &gStartTime = &nowTime; /* 重設起始點 */
End-Function;

/* === 結束紀錄並關閉檔案 === */
Function ClosePerfLog()
   If &gLogFile.IsOpen Then
      &gLogFile.WriteLine("==== 紀錄結束 ====");
      &gLogFile.Close();
   End-If;
End-Function;
```

---

### 🧪【使用範例】

在 App Engine 中：

```peoplecode
/* 初始化效能紀錄 */
InitPerfLog();
&gStartTime = GetTickCount();

/* 你的邏輯程式碼 */
CallYourFunction();
LogPerfStep("步驟 1");

/* 另一段邏輯 */
DoMoreThings();
LogPerfStep("步驟 2");

/* 關閉效能紀錄 */
ClosePerfLog();
```

---

### 📌 優點

- 避免重複撰寫相同時間記錄邏輯。
- 可直接整合進多個 App Engine 批次流程。
- 可擴充支援其他格式（例如 JSON、CSV）或加入記憶體使用量分析。

---

如您需要，我可以進一步協助您將此模組整合進現有的 App Engine 範本，或轉為 PeopleCode 封裝函式庫（例如放入 FUNCLIB Record）。是否需要我協助此部分？

除了紀錄時間, 我還想紀錄當前的cpu使用率、記憶體用量

在 PeopleSoft 的 PeopleCode 環境中，**原生不支援取得作業系統層級的 CPU 使用率與記憶體用量**，因為 PeopleCode 屬於高度沙箱化的語言設計（為安全與跨平台考量）。

但在 **批次處理（App Engine）環境下仍可透過外部程式（如 Shell 或 PowerShell）呼叫系統資源監控指令**，並將結果寫入檔案，再由 PeopleCode 讀入或附加到效能 log 中。

---

### ✅【可行實作方案】

#### 一、設計流程
1. **呼叫外部 Script（Shell/PowerShell）取得系統資源資訊**
2. 將輸出寫入中介檔案（如 `cpu_mem_snapshot.txt`）
3. **PeopleCode 讀取該檔案內容** 並附加進 log

---

#### 二、Windows 範例（PowerShell 指令）

建立一個 PowerShell 檔案 `GetSystemUsage.ps1`，內容如下：

```powershell
$cpu = Get-Counter '\Processor(_Total)\% Processor Time' | Select -ExpandProperty CounterSamples | Select -ExpandProperty CookedValue
$mem = Get-CimInstance Win32_OperatingSystem
$memFree = [math]::Round($mem.FreePhysicalMemory / 1024, 2)
$memTotal = [math]::Round($mem.TotalVisibleMemorySize / 1024, 2)
$memUsed = $memTotal - $memFree

"CPU 使用率: {0:N2}%" -f $cpu | Out-File -FilePath "C:\temp\cpu_mem_snapshot.txt"
"記憶體用量: {0:N2} MB / {1:N2} MB" -f $memUsed, $memTotal | Out-File -FilePath "C:\temp\cpu_mem_snapshot.txt" -Append
```

---

#### 三、PeopleCode 讀入結果（App Engine 用）

```peoplecode
Local File &sysFile;
Local string &line;

&sysFile = GetFile("C:\temp\cpu_mem_snapshot.txt", "R", %FilePath_Absolute);

If &sysFile.IsOpen Then
   While &sysFile.ReadLine(&line)
      &gLogFile.WriteLine("[資源監控] " | &line);
   End-While;
   &sysFile.Close();
End-If;
```

---

### 📌 建議整合方式

- 在每段邏輯結束後，**先執行 PowerShell script**，再用上述 PeopleCode 讀入並附加。
- 若您在 Linux，可用 `top -b -n 1` 或 `free -m` 搭配 Shell Script 寫法。

---

### ⚠️ 實務注意事項

- **Process Scheduler 必須有權限執行外部 Script**
- **權限與路徑需符合 IT 安全規範**
- 可設計為一個參數化功能，例如：`LogSystemUsage(&gLogFile);`

---

若您確認執行環境為 Windows，我可以幫您把整套 script 和 PeopleCode 整合成完整模組，您是否需要這樣的整合樣板？

是在 windows

很好，既然您的 PeopleSoft 執行環境是 Windows，以下提供一個**完整整合範本**，包括：

1. PowerShell 指令腳本產生 `cpu_mem_snapshot.txt`。
2. PeopleCode 模組讀取該檔案並寫入效能 Log。

---

## ✅ 一、建立 PowerShell 腳本

儲存為：`C:\temp\GetSystemUsage.ps1`

```powershell
$cpu = Get-Counter '\Processor(_Total)\% Processor Time' | Select -ExpandProperty CounterSamples | Select -ExpandProperty CookedValue
$mem = Get-CimInstance Win32_OperatingSystem
$memFree = [math]::Round($mem.FreePhysicalMemory / 1024, 2)
$memTotal = [math]::Round($mem.TotalVisibleMemorySize / 1024, 2)
$memUsed = $memTotal - $memFree

"CPU 使用率: {0:N2}%%" -f $cpu | Out-File -FilePath "C:\temp\cpu_mem_snapshot.txt"
"記憶體用量: {0:N2} MB / {1:N2} MB" -f $memUsed, $memTotal | Out-File -FilePath "C:\temp\cpu_mem_snapshot.txt" -Append
```

---

## ✅ 二、PeopleCode 整合模組（加在 App Engine 中）

### 🔧 輔助函式：讀入系統資源檔案並寫入 Log

```peoplecode
Function LogSystemUsage()
   Local File &sysFile;
   Local string &line;
   Local string &command;

   /* 呼叫 PowerShell 腳本 */
   &command = "powershell -ExecutionPolicy Bypass -File C:\temp\GetSystemUsage.ps1";
   Exec(&command, %Exec_Asynchronous);

   /* 等待檔案生成（簡易等待，可視需要延長） */
   Sleep(1); /* 等待 1 秒 */

   /* 讀取並寫入 */
   &sysFile = GetFile("C:\temp\cpu_mem_snapshot.txt", "R", %FilePath_Absolute);
   If &sysFile.IsOpen Then
      While &sysFile.ReadLine(&line)
         &gLogFile.WriteLine("[系統資源] " | &line);
      End-While;
      &sysFile.Close();
   End-If;
End-Function;
```

---

## 🧪 使用範例（結合效能紀錄）

```peoplecode
InitPerfLog();
&gStartTime = GetTickCount();

/* Step 1 */
RunStep1();
LogPerfStep("步驟 1");
LogSystemUsage(); /* 記錄系統資源 */

/* Step 2 */
RunStep2();
LogPerfStep("步驟 2");
LogSystemUsage(); /* 記錄系統資源 */

ClosePerfLog();
```

---

## ⚠️ 注意事項

- **Process Scheduler Service 使用者帳號（例如 `psbatch`）必須有權限執行 PowerShell**。
- 建議將 `GetSystemUsage.ps1` 放在固定路徑（如 `C:\temp`），並確保該目錄可寫入。

---

是否需要我幫您產出整個模組的封裝版本（例如放在 FUNCLIB）或建立條件式參數來控制是否啟用這個資源監控？

有辦法取得peoplesoft中的資源資訊嗎?

在 PeopleSoft 中，**PeopleCode 無法直接取得底層系統層級的 CPU、記憶體、I/O 使用率**，但 PeopleSoft 平台仍提供一些**可間接掌握系統與應用層資源資訊的方式**，以下是可行的幾個層級與方法：

---

## ✅ 一、【應用伺服器 / Process Scheduler 層級監控】

### 1. **Process Monitor + Trace Log**
- 可檢視特定 App Engine、SQR 的執行時間與記憶體峰值（僅部分工具提供）。

### 2. **PSAPPSRV、PSPRCSRV 設定檔（如 psappsrv.cfg）**
- 可設定最大連線、JVM 大小（Heap Size）、SQL Trace 等參數，有助掌握資源瓶頸。
- 無法透過 PeopleCode 動態取得，但可用於分析配置瓶頸。

---

## ✅ 二、【PeopleTools 提供的記錄資訊】

### 1. **PSPRCSQUE、PSPRCSQUEDEFN 資料表**
- 包含 Process 執行時間、狀態、主機資訊，但無 CPU/Memory 數值。

### 2. **Performance Monitoring（Performance Monitor）**
- ✅ 可在 PeopleTools 中啟用 **PeopleSoft Performance Monitor（PPM）**，用於監控：
  - Process 執行時間、SQL 效能、App Server 使用情況、DB Wait Events
  - 類似 Oracle Enterprise Manager，但內建於 PeopleSoft。

> 🔧 需系統層設定與 Agent 安裝，但能提供較完整的 CPU / Memory / SQL 等監控資料。

---

## ✅ 三、【Application Engine 執行統計】

PeopleSoft 並未記錄每個 SQL 的 CPU/記憶體耗用，但您可以：

- 啟用 SQL Trace（如 Level 7, 115）→ 查看執行 SQL 的成本與時間。
- 使用 `EXPLAIN PLAN` 或 `DBMS_XPLAN.DISPLAY_CURSOR` 從資料庫端分析資源使用。

---

## 🔄 補充策略建議（結合應用與系統層）

| 資訊類型       | 是否 PeopleCode 可取得 | 實務建議方式 |
|----------------|------------------------|---------------|
| SQL 耗時        | ✅（搭配 GetTickCount） | 手動記錄執行時間 |
| CPU/記憶體使用率 | ❌（需外部 script）     | 用 PowerShell 結合 PeopleCode |
| Process 整體資源 | ❌（PeopleCode 不支援） | 啟用 PeopleSoft Performance Monitor |
| 系統瓶頸診斷    | ❌（需外部工具）         | 用 OS 工具（如 perfmon、top）或 AWR |

---

若您希望使用 PeopleSoft 官方的 **Performance Monitor（PPM）** 來追蹤整體效能，是否需要我協助您了解如何啟用該模組與配置監控項目？或者您偏好使用目前已有的 App Engine 邏輯與 PowerShell 結合方式？