# Shell 教學文件

## 目錄

1. [概述](#概述)
2. [初級教學](#初級教學)
    - [什麼是 Shell](#什麼是-shell)
    - [Shell 的基本概念](#shell-的基本概念)
    - [開始使用 Shell](#開始使用-shell)
    - [基本指令介紹](#基本指令介紹)
    - [檔案與目錄操作](#檔案與目錄操作)
    - [查看檔案內容](#查看檔案內容)
    - [使用者與權限](#使用者與權限)
    - [初級範例](#初級範例)
3. [中級教學](#中級教學)
    - [Shell 腳本基礎](#shell-腳本基礎)
    - [變數與環境變數](#變數與環境變數)
    - [條件判斷與迴圈](#條件判斷與迴圈)
    - [函數](#函數)
    - [輸入與輸出重定向](#輸入與輸出重定向)
    - [管道與過濾器](#管道與過濾器)
    - [正則表達式](#正則表達式)
    - [中級範例](#中級範例)
4. [高級教學](#高級教學)
    - [Shell 腳本最佳實踐](#shell-腳本最佳實踐)
    - [效能優化](#效能優化)
    - [故障排除技巧](#故障排除技巧)
    - [系統監控與管理](#系統監控與管理)
    - [自動化任務](#自動化任務)
    - [安全性考量](#安全性考量)
    - [高級範例](#高級範例)
5. [常用指令參考](#常用指令參考)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 Shell 的使用技巧。無論您是完全沒有 Shell 經驗的初學者，還是已經了解基礎功能需要自定義的中級學習者，或是想要進行故障排除和效能優化的高級使用者，本文檔都能為您提供所需的知識和技能。

Shell 是一個命令列介面，讓使用者能夠與作業系統互動。通過學習 Shell，您可以更有效地管理檔案、執行程式、自動化任務，以及解決各種電腦問題。

## 初級教學

本節適合完全沒有 Shell 經驗的初學者。我們將從最基本的概念開始，逐步建立您對 Shell 的理解。

### 什麼是 Shell

Shell 是一個程式，提供了一個介面讓您能夠輸入指令來控制電腦。想像一下，如果電腦是一個機器人，那麼 Shell 就是您用來告訴機器人做什麼的對講機。

在圖形使用者介面 (GUI) 中，您通過點擊圖示和按鈕來操作電腦。而在 Shell 中，您通過輸入文字指令來達到同樣的目的。雖然一開始可能看起來比較困難，但一旦熟悉了，Shell 可以讓您更快速、更精確地完成許多任務。

常見的 Shell 包括：

- **Bash** (Bourne Again SHell)：Linux 和 macOS 上最常見的 Shell
- **PowerShell**：Windows 上的現代 Shell
- **Zsh** (Z Shell)：macOS Catalina 及以上版本的默認 Shell
- **Fish**：一個用戶友好的 Shell

### Shell 的基本概念

在開始使用 Shell 之前，讓我們了解一些基本概念：

1. **終端機 (Terminal)**：這是一個程式，提供了一個視窗讓您可以與 Shell 互動。
2. **提示符 (Prompt)**：這是 Shell 顯示的一段文字，表示它正在等待您輸入指令。通常包含用戶名、電腦名和當前目錄。
3. **指令 (Command)**：這是您告訴電腦要做什麼的指示。
4. **參數 (Arguments)**：這些是提供給指令的額外資訊。
5. **選項 (Options)**：這些是修改指令行為的特殊參數，通常以 `-` 或 `--` 開頭。

### 開始使用 Shell

根據您的作業系統，開啟終端機的方式不同：

- **Windows**：
    - 按下 `Win + R`，輸入 `cmd` 或 `powershell`，然後按 Enter
    - 或在開始選單中搜尋「命令提示字元」或「PowerShell」

- **macOS**：
    - 按下 `Command + Space`，輸入 `terminal`，然後按 Enter
    - 或在應用程式資料夾中找到「終端機」

- **Linux**：
    - 按下 `Ctrl + Alt + T`（在大多數發行版中）
    - 或在應用程式選單中找到「終端機」

當您開啟終端機後，您會看到一個提示符，表示 Shell 已準備好接受您的指令。提示符可能看起來像這樣：

```
username@hostname:~$
```

其中：

- `username` 是您的用戶名
- `hostname` 是您的電腦名
- `~` 表示您當前在家目錄
- `$` 表示您是一般用戶（如果是 `#`，則表示您是超級用戶）

### 基本指令介紹

以下是一些最基本的 Shell 指令：

1. **pwd** (Print Working Directory)：顯示當前所在的目錄
   ```bash
   pwd
   ```
   輸出範例：`/home/username`

2. **ls** (List)：列出目錄中的檔案和子目錄
   ```bash
   ls
   ```
   輸出範例：`Documents  Downloads  Pictures  Music  Videos`

   常用選項：
    - `ls -l`：以詳細格式列出
    - `ls -a`：列出所有檔案，包括隱藏檔案（以 `.` 開頭的檔案）
    - `ls -h`：以人類可讀的格式顯示檔案大小

3. **cd** (Change Directory)：切換到另一個目錄
   ```bash
   cd Documents
   ```

   特殊目錄符號：
    - `.`：當前目錄
    - `..`：上一層目錄
    - `~`：家目錄
    - `/`：根目錄

4. **mkdir** (Make Directory)：創建新目錄
   ```bash
   mkdir MyFolder
   ```

5. **echo**：顯示一段文字
   ```bash
   echo "Hello, World!"
   ```
   輸出：`Hello, World!`

6. **clear**：清除螢幕
   ```bash
   clear
   ```

### 檔案與目錄操作

以下是一些用於操作檔案和目錄的基本指令：

1. **touch**：創建空檔案或更新檔案的時間戳
   ```bash
   touch myfile.txt
   ```

2. **cp** (Copy)：複製檔案或目錄
   ```bash
   # 複製檔案
   cp myfile.txt mycopy.txt

   # 複製目錄（需要 -r 選項）
   cp -r MyFolder MyFolderCopy
   ```

3. **mv** (Move)：移動檔案或目錄，也可用於重命名
   ```bash
   # 移動檔案
   mv myfile.txt MyFolder/

   # 重命名檔案
   mv myfile.txt newname.txt
   ```

4. **rm** (Remove)：刪除檔案或目錄
   ```bash
   # 刪除檔案
   rm myfile.txt

   # 刪除目錄（需要 -r 選項）
   rm -r MyFolder
   ```

   **注意**：使用 `rm` 時要非常小心，尤其是與 `-r`（遞迴）和 `-f`（強制）選項一起使用時。刪除的檔案通常無法恢復！

5. **rmdir** (Remove Directory)：刪除空目錄
   ```bash
   rmdir EmptyFolder
   ```

### 查看檔案內容

以下是一些用於查看檔案內容的基本指令：

1. **cat** (Concatenate)：顯示檔案的全部內容
   ```bash
   cat myfile.txt
   ```

2. **less**：分頁顯示檔案內容，適合查看大檔案
   ```bash
   less myfile.txt
   ```

   在 `less` 中，您可以使用以下按鍵：
    - 空格鍵：向下翻頁
    - `b`：向上翻頁
    - 上下箭頭：上下移動
    - `q`：退出

3. **head**：顯示檔案的前幾行
   ```bash
   # 顯示前 10 行（默認）
   head myfile.txt

   # 顯示前 5 行
   head -n 5 myfile.txt
   ```

4. **tail**：顯示檔案的最後幾行
   ```bash
   # 顯示最後 10 行（默認）
   tail myfile.txt

   # 顯示最後 5 行
   tail -n 5 myfile.txt

   # 持續顯示新增的內容（監視檔案）
   tail -f myfile.txt
   ```

### 使用者與權限

在 Unix/Linux 系統中，檔案和目錄都有所有者、群組和權限設定：

1. **chmod** (Change Mode)：修改檔案或目錄的權限
   ```bash
   # 給檔案所有者添加執行權限
   chmod u+x myscript.sh

   # 給所有人添加讀取權限
   chmod a+r myfile.txt

   # 使用數字設定權限（4=讀取, 2=寫入, 1=執行）
   chmod 755 myscript.sh  # rwxr-xr-x
   ```

2. **chown** (Change Owner)：修改檔案或目錄的所有者
   ```bash
   # 修改檔案所有者
   sudo chown username myfile.txt

   # 修改檔案所有者和群組
   sudo chown username:groupname myfile.txt
   ```

3. **sudo** (Superuser Do)：以超級用戶權限執行指令
   ```bash
   sudo apt update
   ```

### 初級範例

讓我們通過一個簡單的範例來練習基本的 Shell 操作：

**範例 1：創建一個簡單的專案目錄結構**

```bash
# 創建專案目錄
mkdir MyProject

# 進入專案目錄
cd MyProject

# 創建子目錄
mkdir src docs tests

# 創建一些檔案
touch src/main.py docs/README.md tests/test_main.py

# 查看目錄結構
ls -la

# 在 README.md 中添加一些內容
echo "# My Project" > docs/README.md
echo "This is a sample project." >> docs/README.md

# 查看 README.md 的內容
cat docs/README.md

# 複製 README.md 到專案根目錄
cp docs/README.md ./

# 重命名根目錄的 README.md
mv README.md README.txt

# 查看當前目錄的檔案
ls -l
```

**範例 2：使用基本指令處理文字檔案**

```bash
# 創建一個包含數字的檔案
echo "1" > numbers.txt
echo "2" >> numbers.txt
echo "3" >> numbers.txt
echo "4" >> numbers.txt
echo "5" >> numbers.txt

# 查看檔案內容
cat numbers.txt

# 顯示檔案的前 3 行
head -n 3 numbers.txt

# 顯示檔案的最後 2 行
tail -n 2 numbers.txt

# 計算檔案中的行數
wc -l numbers.txt

# 搜尋檔案中的內容
grep "3" numbers.txt
```

## 中級教學

本節適合已經了解 Shell 基礎操作，需要進一步自定義和提高效率的學習者。我們將探討 Shell 腳本、變數、條件判斷、迴圈等更進階的概念。

### Shell 腳本基礎

Shell 腳本是一個包含一系列 Shell 指令的文字檔案，可以讓您自動化執行多個指令。

**創建第一個 Shell 腳本**：

1. 使用文字編輯器創建一個檔案，例如 `myscript.sh`：
   ```bash
   #!/bin/bash
   # 這是我的第一個 Shell 腳本

   echo "Hello, World!"
   echo "今天的日期是: $(date)"
   echo "當前目錄是: $(pwd)"
   ```

2. 第一行 `#!/bin/bash` 稱為 shebang，它告訴系統這個腳本應該使用哪個 Shell 來執行。

3. 給腳本添加執行權限：
   ```bash
   chmod +x myscript.sh
   ```

4. 執行腳本：
   ```bash
   ./myscript.sh
   ```

### 變數與環境變數

**變數**是用來儲存資料的容器。在 Shell 中，變數可以儲存數字、字串或其他值。

1. **定義變數**：
   ```bash
   name="小明"
   age=15
   ```

2. **使用變數**：
   ```bash
   echo "我的名字是 $name，今年 $age 歲。"
   ```

3. **命令替換**：將命令的輸出儲存到變數中
   ```bash
   current_date=$(date)
   files=$(ls)
   ```

4. **環境變數**：系統預設的變數，影響程式的行為
   ```bash
   # 顯示所有環境變數
   env

   # 顯示特定環境變數
   echo $HOME
   echo $PATH

   # 設定環境變數
   export MY_VAR="my value"
   ```

5. **讀取使用者輸入**：
   ```bash
   echo "請輸入您的名字："
   read user_name
   echo "您好，$user_name！"
   ```

### 條件判斷與迴圈

**條件判斷**允許您根據特定條件執行不同的指令。

1. **if 語句**：
   ```bash
   #!/bin/bash

   echo "請輸入一個數字："
   read number

   if [ $number -gt 10 ]; then
       echo "您輸入的數字大於 10"
   elif [ $number -eq 10 ]; then
       echo "您輸入的數字等於 10"
   else
       echo "您輸入的數字小於 10"
   fi
   ```

2. **比較運算符**：
    - 數字比較：
        - `-eq`：等於
        - `-ne`：不等於
        - `-gt`：大於
        - `-lt`：小於
        - `-ge`：大於或等於
        - `-le`：小於或等於

    - 字串比較：
        - `=`：等於
        - `!=`：不等於
        - `-z`：字串長度為零
        - `-n`：字串長度不為零

3. **檔案測試**：
    - `-e file`：檔案存在
    - `-f file`：檔案存在且是一個普通檔案
    - `-d file`：檔案存在且是一個目錄
    - `-r file`：檔案存在且可讀
    - `-w file`：檔案存在且可寫
    - `-x file`：檔案存在且可執行

4. **for 迴圈**：
   ```bash
   #!/bin/bash

   # 列出 1 到 5
   for i in 1 2 3 4 5; do
       echo "數字: $i"
   done

   # 使用範圍
   for i in {1..5}; do
       echo "數字: $i"
   done

   # 遍歷檔案
   for file in *.txt; do
       echo "處理檔案: $file"
   done
   ```

5. **while 迴圈**：
   ```bash
   #!/bin/bash

   # 計數器
   count=1

   # 當 count 小於或等於 5 時執行迴圈
   while [ $count -le 5 ]; do
       echo "計數: $count"
       count=$((count + 1))
   done
   ```

### 函數

**函數**是一組可重複使用的指令，可以讓您的腳本更加模組化。

1. **定義函數**：
   ```bash
   #!/bin/bash

   # 定義一個簡單的函數
   say_hello() {
       echo "Hello, World!"
   }

   # 帶參數的函數
   greet() {
       echo "Hello, $1!"
   }

   # 調用函數
   say_hello
   greet "小明"
   ```

2. **返回值**：
   ```bash
   #!/bin/bash

   # 計算兩數之和的函數
   add() {
       local result=$(($1 + $2))
       echo $result
   }

   # 調用函數並儲存結果
   sum=$(add 5 3)
   echo "5 + 3 = $sum"
   ```

### 輸入與輸出重定向

**重定向**允許您控制指令的輸入來源和輸出目的地。

1. **輸出重定向**：
   ```bash
   # 將輸出寫入檔案（覆蓋）
   echo "Hello" > output.txt

   # 將輸出附加到檔案
   echo "World" >> output.txt

   # 將錯誤輸出重定向
   command 2> error.log

   # 將標準輸出和錯誤輸出都重定向到同一個檔案
   command > output.txt 2>&1
   ```

2. **輸入重定向**：
   ```bash
   # 從檔案讀取輸入
   sort < unsorted.txt

   # Here Document（多行輸入）
   cat << EOF > myfile.txt
   這是第一行
   這是第二行
   這是第三行
   EOF
   ```

### 管道與過濾器

**管道**允許您將一個指令的輸出作為另一個指令的輸入。

1. **基本管道**：
   ```bash
   # 列出目錄中的檔案，然後只顯示包含 "log" 的行
   ls -l | grep "log"

   # 計算目錄中的檔案數量
   ls | wc -l
   ```

2. **常用過濾器**：
    - `grep`：搜尋文字
    - `sort`：排序
    - `uniq`：移除重複行
    - `wc`：計算行數、字數或字元數
    - `cut`：擷取欄位
    - `sed`：文字替換
    - `awk`：文字處理

3. **組合使用**：
   ```bash
   # 找出最大的 5 個檔案
   du -h | sort -hr | head -n 5

   # 計算每個使用者的處理程序數量
   ps aux | awk '{print $1}' | sort | uniq -c | sort -nr
   ```

### 正則表達式

**正則表達式**是一種用於匹配文字模式的強大工具。

1. **基本模式**：
   ```bash
   # 搜尋包含 "error" 的行
   grep "error" logfile.txt

   # 搜尋以 "Start" 開頭的行
   grep "^Start" logfile.txt

   # 搜尋以 "End" 結尾的行
   grep "End$" logfile.txt
   ```

2. **擴展模式**：
   ```bash
   # 搜尋包含數字的行
   grep -E "[0-9]+" logfile.txt

   # 搜尋包含 "error" 或 "warning" 的行
   grep -E "error|warning" logfile.txt

   # 搜尋包含郵件地址的行
   grep -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" logfile.txt
   ```

### 中級範例

**範例 1：批次重命名檔案**

```bash
#!/bin/bash

# 這個腳本將目錄中所有的 .txt 檔案重命名為 .bak 檔案

# 檢查是否提供了目錄參數
if [ $# -ne 1 ]; then
    echo "用法: $0 <目錄>"
    exit 1
fi

# 檢查目錄是否存在
if [ ! -d "$1" ]; then
    echo "錯誤: '$1' 不是一個目錄"
    exit 1
fi

# 進入目錄
cd "$1"

# 計數器
count=0

# 遍歷所有 .txt 檔案
for file in *.txt; do
    # 檢查檔案是否存在（避免 *.txt 沒有匹配時的錯誤）
    if [ -f "$file" ]; then
        # 取得檔案名稱（不含副檔名）
        filename="${file%.txt}"

        # 重命名檔案
        mv "$file" "${filename}.bak"

        # 增加計數器
        count=$((count + 1))

        echo "已重命名: $file -> ${filename}.bak"
    fi
done

echo "完成！共重命名了 $count 個檔案。"
```

**範例 2：監控系統資源**

```bash
#!/bin/bash

# 這個腳本監控系統的 CPU、記憶體和磁碟使用情況

# 顯示標題
echo "=== 系統資源監控 ==="
echo "日期: $(date)"
echo

# CPU 使用情況
echo "--- CPU 使用情況 ---"
top -bn1 | grep "Cpu(s)" | awk '{print "使用率: " $2 + $4 "%"}'
echo

# 記憶體使用情況
echo "--- 記憶體使用情況 ---"
free -h | grep "Mem:" | awk '{print "總計: " $2 "\n使用中: " $3 "\n可用: " $4}'
echo

# 磁碟使用情況
echo "--- 磁碟使用情況 ---"
df -h | grep -v "tmpfs" | grep -v "udev"
echo

# 顯示最耗資源的 5 個處理程序
echo "--- 最耗資源的處理程序 ---"
ps aux --sort=-%cpu | head -n 6
```

## 高級教學

本節適合需要進行故障排除、效能優化的高級使用者。我們將探討更複雜的 Shell 腳本技巧、系統監控、自動化任務等高級主題。

### Shell 腳本最佳實踐

編寫高品質的 Shell 腳本需要遵循一些最佳實踐：

1. **添加適當的註釋**：
   ```bash
   #!/bin/bash

   # 腳本名稱: backup.sh
   # 描述: 備份重要檔案到指定目錄
   # 作者: 小明
   # 日期: 2023-04-29

   # 設定備份目錄
   BACKUP_DIR="/backup"

   # 檢查備份目錄是否存在，如果不存在則創建
   if [ ! -d "$BACKUP_DIR" ]; then
       mkdir -p "$BACKUP_DIR"
   fi
   ```

2. **使用有意義的變數名稱**：
   ```bash
   # 不好的做法
   d=$(date +%Y%m%d)

   # 好的做法
   current_date=$(date +%Y%m%d)
   ```

3. **錯誤處理**：
   ```bash
   #!/bin/bash

   # 設定錯誤時退出
   set -e

   # 自定義錯誤處理函數
   error_handler() {
       echo "錯誤: 腳本在第 $1 行失敗" >&2
       exit 1
   }

   # 設定錯誤處理器
   trap 'error_handler $LINENO' ERR

   # 腳本主體
   echo "開始執行..."

   # 嘗試執行可能會失敗的命令
   some_command || {
       echo "命令失敗，正在清理..."
       cleanup_function
       exit 1
   }
   ```

4. **模組化設計**：
   ```bash
   #!/bin/bash

   # 載入共用函數
   source ./common_functions.sh

   # 定義特定功能的函數
   backup_database() {
       # 函數實現
   }

   backup_files() {
       # 函數實現
   }

   # 主函數
   main() {
       parse_arguments "$@"
       setup_environment
       backup_database
       backup_files
       cleanup
   }

   # 執行主函數
   main "$@"
   ```

### 效能優化

優化 Shell 腳本的效能可以節省時間和資源：

1. **減少外部命令調用**：
   ```bash
   # 不好的做法（多次調用 date）
   echo "當前年份: $(date +%Y)"
   echo "當前月份: $(date +%m)"
   echo "當前日期: $(date +%d)"

   # 好的做法（只調用一次 date）
   current_date=$(date +%Y-%m-%d)
   year=${current_date%-*-*}
   month=${current_date#*-}
   month=${month%-*}
   day=${current_date##*-}

   echo "當前年份: $year"
   echo "當前月份: $month"
   echo "當前日期: $day"
   ```

2. **使用內建命令**：
   ```bash
   # 不好的做法（使用外部命令）
   if grep -q "pattern" file.txt; then
       echo "找到匹配"
   fi

   # 好的做法（使用內建命令）
   if [[ -f file.txt ]] && [[ $(< file.txt) == *pattern* ]]; then
       echo "找到匹配"
   fi
   ```

3. **避免不必要的子 Shell**：
   ```bash
   # 不好的做法（創建子 Shell）
   for file in $(find . -name "*.txt"); do
       process_file "$file"
   done

   # 好的做法（使用 glob 模式）
   for file in *.txt; do
       process_file "$file"
   done
   ```

4. **使用並行處理**：
   ```bash
   #!/bin/bash

   # 定義處理函數
   process_file() {
       echo "處理檔案: $1"
       sleep 1  # 模擬處理時間
   }

   # 導出函數，使其可在子 Shell 中使用
   export -f process_file

   # 並行處理檔案
   find . -name "*.txt" -print0 | xargs -0 -P 4 -I{} bash -c 'process_file "{}"'
   ```

### 故障排除技巧

當 Shell 腳本出現問題時，以下技巧可以幫助您找出並解決問題：

1. **啟用調試模式**：
   ```bash
   # 在腳本開頭添加
   set -x  # 顯示執行的每一行命令

   # 或在執行時啟用
   bash -x myscript.sh
   ```

2. **添加詳細的日誌**：
   ```bash
   #!/bin/bash

   # 定義日誌函數
   log() {
       echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a script.log
   }

   log "開始執行腳本"

   # 執行命令並記錄結果
   if some_command; then
       log "命令執行成功"
   else
       log "錯誤: 命令執行失敗，退出碼: $?"
   fi
   ```

3. **檢查返回值**：
   ```bash
   command
   status=$?

   if [ $status -ne 0 ]; then
       echo "命令失敗，退出碼: $status"
   fi
   ```

4. **使用 trap 捕獲信號**：
   ```bash
   #!/bin/bash

   # 定義清理函數
   cleanup() {
       echo "正在清理臨時檔案..."
       rm -f /tmp/tempfile_$$
       exit
   }

   # 捕獲信號
   trap cleanup EXIT INT TERM

   # 創建臨時檔案
   touch /tmp/tempfile_$$

   # 腳本主體
   echo "腳本正在執行..."
   sleep 10
   ```

5. **分析系統日誌**：
   ```bash
   # 查看系統日誌
   tail -f /var/log/syslog

   # 查看特定服務的日誌
   journalctl -u apache2
   ```

### 系統監控與管理

Shell 腳本可以用於監控和管理系統資源：

1. **監控磁碟空間**：
   ```bash
   #!/bin/bash

   # 設定閾值（百分比）
   THRESHOLD=90

   # 獲取磁碟使用情況
   USAGE=$(df -h | grep "/dev/sda1" | awk '{print $5}' | tr -d '%')

   if [ "$USAGE" -gt "$THRESHOLD" ]; then
       echo "警告: 磁碟使用率已達 $USAGE%，超過閾值 $THRESHOLD%"
       # 發送郵件通知
       echo "磁碟空間警告" | mail -s "磁碟空間警告" admin@example.com
   fi
   ```

2. **監控系統負載**：
   ```bash
   #!/bin/bash

   # 設定閾值
   THRESHOLD=2

   # 獲取系統負載（1分鐘平均值）
   LOAD=$(uptime | awk '{print $(NF-2)}' | tr -d ',')

   if (( $(echo "$LOAD > $THRESHOLD" | bc -l) )); then
       echo "警告: 系統負載為 $LOAD，超過閾值 $THRESHOLD"
   fi
   ```

3. **監控網路連接**：
   ```bash
   #!/bin/bash

   # 監控特定端口的連接數
   PORT=80
   CONNECTIONS=$(netstat -an | grep ":$PORT " | grep ESTABLISHED | wc -l)

   echo "當前有 $CONNECTIONS 個連接到端口 $PORT"
   ```

4. **自動清理舊檔案**：
   ```bash
   #!/bin/bash

   # 清理超過 30 天的日誌檔案
   find /var/log -name "*.log" -type f -mtime +30 -exec rm {} \;
   ```

### 自動化任務

Shell 腳本可以用於自動化各種任務：

1. **定時備份**：
   ```bash
   #!/bin/bash

   # 設定變數
   BACKUP_DIR="/backup"
   SOURCE_DIR="/var/www"
   DATE=$(date +%Y%m%d)

   # 創建備份目錄
   mkdir -p "$BACKUP_DIR"

   # 創建備份
   tar -czf "$BACKUP_DIR/backup-$DATE.tar.gz" "$SOURCE_DIR"

   # 刪除超過 7 天的備份
   find "$BACKUP_DIR" -name "backup-*.tar.gz" -type f -mtime +7 -delete
   ```

   設定 crontab 定時執行：
   ```bash
   # 每天凌晨 2 點執行備份
   0 2 * * * /path/to/backup.sh
   ```

2. **批次處理檔案**：
   ```bash
   #!/bin/bash

   # 批次壓縮圖片
   for img in *.jpg; do
       echo "處理圖片: $img"
       convert "$img" -resize 800x600 "resized_$img"
   done
   ```

3. **自動部署應用程式**：
   ```bash
   #!/bin/bash

   # 設定變數
   APP_DIR="/var/www/myapp"
   REPO="https://github.com/user/repo.git"

   # 更新代碼
   cd "$APP_DIR" || exit 1
   git pull origin master

   # 安裝依賴
   npm install

   # 建置應用程式
   npm run build

   # 重啟服務
   systemctl restart myapp
   ```

### 安全性考量

在編寫 Shell 腳本時，安全性是一個重要的考量因素：

1. **避免使用 eval**：
   ```bash
   # 不安全的做法
   eval "echo $user_input"

   # 更安全的做法
   echo "$user_input"
   ```

2. **檢查和驗證輸入**：
   ```bash
   #!/bin/bash

   # 檢查輸入是否為數字
   if ! [[ "$1" =~ ^[0-9]+$ ]]; then
       echo "錯誤: 參數必須是數字"
       exit 1
   fi
   ```

3. **使用引號包裹變數**：
   ```bash
   # 不安全的做法
   rm -rf $directory

   # 安全的做法
   rm -rf "$directory"
   ```

4. **限制權限**：
   ```bash
   # 設定適當的檔案權限
   chmod 700 myscript.sh  # 只有所有者可以讀取、寫入和執行
   ```

5. **避免在腳本中硬編碼敏感資訊**：
   ```bash
   # 不好的做法
   PASSWORD="supersecret"

   # 好的做法
   read -s -p "請輸入密碼: " PASSWORD
   ```

### 高級範例

**範例 1：監控網站可用性並發送通知**

```bash
#!/bin/bash

# 這個腳本監控網站的可用性，如果網站無法訪問，則發送通知

# 設定變數
WEBSITES=("https://example.com" "https://example.org")
CHECK_INTERVAL=300  # 5 分鐘
MAX_RETRIES=3
RETRY_DELAY=30  # 30 秒
NOTIFICATION_EMAIL="admin@example.com"

# 日誌函數
log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a website_monitor.log
}

# 發送通知
send_notification() {
    local website=$1
    local status=$2

    log "發送通知: $website 狀態為 $status"

    # 發送郵件
    echo "網站 $website 目前狀態為 $status" | mail -s "網站監控警報" "$NOTIFICATION_EMAIL"

    # 如果有其他通知方式（如 Slack、Telegram 等），可以在這裡添加
}

# 檢查網站
check_website() {
    local website=$1
    local retry_count=0

    while [ $retry_count -lt $MAX_RETRIES ]; do
        log "檢查網站: $website (嘗試 $((retry_count + 1))/$MAX_RETRIES)"

        # 使用 curl 檢查網站
        if curl -s --head --request GET "$website" | grep "200 OK" > /dev/null; then
            log "網站 $website 可以訪問"
            return 0
        else
            log "網站 $website 無法訪問，等待 $RETRY_DELAY 秒後重試"
            sleep $RETRY_DELAY
            retry_count=$((retry_count + 1))
        fi
    done

    log "網站 $website 在 $MAX_RETRIES 次嘗試後仍然無法訪問"
    send_notification "$website" "無法訪問"
    return 1
}

# 主循環
log "開始監控網站"

while true; do
    for website in "${WEBSITES[@]}"; do
        check_website "$website"
    done

    log "等待 $CHECK_INTERVAL 秒後進行下一次檢查"
    sleep $CHECK_INTERVAL
done
```

**範例 2：自動化資料庫備份與恢復**

```bash
#!/bin/bash

# 這個腳本自動備份 MySQL 資料庫並提供恢復功能

# 設定變數
DB_USER="dbuser"
DB_PASS="dbpassword"
DB_NAME="mydb"
BACKUP_DIR="/backup/mysql"
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="$BACKUP_DIR/$DB_NAME-$DATE.sql.gz"

# 確保備份目錄存在
mkdir -p "$BACKUP_DIR"

# 顯示使用方法
usage() {
    echo "用法: $0 [選項]"
    echo "選項:"
    echo "  backup    備份資料庫"
    echo "  restore   恢復資料庫"
    echo "  list      列出可用的備份"
    exit 1
}

# 備份資料庫
backup_db() {
    echo "正在備份資料庫 $DB_NAME..."

    # 使用 mysqldump 備份資料庫並壓縮
    mysqldump -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" | gzip > "$BACKUP_FILE"

    if [ $? -eq 0 ]; then
        echo "備份成功: $BACKUP_FILE"

        # 刪除超過 30 天的備份
        find "$BACKUP_DIR" -name "$DB_NAME-*.sql.gz" -type f -mtime +30 -delete
        echo "已刪除超過 30 天的舊備份"
    else
        echo "備份失敗"
        exit 1
    fi
}

# 列出可用的備份
list_backups() {
    echo "可用的備份:"
    ls -lh "$BACKUP_DIR" | grep "$DB_NAME-" | awk '{print $9 " (" $5 ")"}'
}

# 恢復資料庫
restore_db() {
    # 列出可用的備份
    list_backups

    # 讓用戶選擇要恢復的備份
    echo "請輸入要恢復的備份檔案名稱:"
    read -r backup_file

    if [ ! -f "$BACKUP_DIR/$backup_file" ]; then
        echo "錯誤: 備份檔案不存在"
        exit 1
    fi

    echo "警告: 這將覆蓋當前的資料庫 $DB_NAME"
    echo "確定要繼續嗎? (y/n)"
    read -r confirm

    if [ "$confirm" != "y" ]; then
        echo "已取消恢復操作"
        exit 0
    fi

    echo "正在恢復資料庫 $DB_NAME 從 $backup_file..."

    # 解壓縮備份檔案並恢復到資料庫
    gunzip < "$BACKUP_DIR/$backup_file" | mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME"

    if [ $? -eq 0 ]; then
        echo "恢復成功"
    else
        echo "恢復失敗"
        exit 1
    fi
}

# 主程式
case "$1" in
    backup)
        backup_db
        ;;
    list)
        list_backups
        ;;
    restore)
        restore_db
        ;;
    *)
        usage
        ;;
esac
```

## 常用指令參考

以下是一些常用的 Shell 指令參考，可以幫助您更有效地使用 Shell：

### 檔案與目錄操作

1. **ls**：列出目錄內容
   ```bash
   # 列出詳細資訊
   ls -l

   # 列出所有檔案（包括隱藏檔案）
   ls -a

   # 以人類可讀的格式顯示檔案大小
   ls -lh

   # 按修改時間排序
   ls -lt
   ```

2. **find**：搜尋檔案
   ```bash
   # 搜尋名稱為 "file.txt" 的檔案
   find /path/to/search -name "file.txt"

   # 搜尋修改時間在 7 天以內的檔案
   find /path/to/search -type f -mtime -7

   # 搜尋大於 100MB 的檔案
   find /path/to/search -type f -size +100M

   # 搜尋並執行命令
   find /path/to/search -name "*.log" -exec rm {} \;
   ```

3. **grep**：搜尋文字
   ```bash
   # 在檔案中搜尋文字
   grep "search_text" file.txt

   # 遞迴搜尋目錄
   grep -r "search_text" /path/to/directory

   # 顯示匹配行號
   grep -n "search_text" file.txt

   # 忽略大小寫
   grep -i "search_text" file.txt
   ```

### 文字處理

1. **sed**：文字替換
   ```bash
   # 替換第一個匹配
   sed 's/old/new/' file.txt

   # 替換所有匹配
   sed 's/old/new/g' file.txt

   # 替換特定行
   sed '3s/old/new/' file.txt

   # 刪除空行
   sed '/^$/d' file.txt
   ```

2. **awk**：文字處理
   ```bash
   # 印出第一欄
   awk '{print $1}' file.txt

   # 計算數字總和
   awk '{sum += $1} END {print sum}' file.txt

   # 過濾特定行
   awk '$3 > 100' file.txt

   # 設定欄位分隔符
   awk -F: '{print $1}' /etc/passwd
   ```

3. **sort**：排序
   ```bash
   # 按字母順序排序
   sort file.txt

   # 按數字排序
   sort -n file.txt

   # 反向排序
   sort -r file.txt

   # 按特定欄位排序
   sort -k2 file.txt
   ```

### 系統管理

1. **ps**：顯示處理程序
   ```bash
   # 顯示所有處理程序
   ps aux

   # 顯示特定使用者的處理程序
   ps -u username

   # 顯示處理程序樹
   ps -ejH
   ```

2. **top**：監控系統資源
   ```bash
   # 啟動 top
   top

   # 按 CPU 使用率排序
   top -o %CPU

   # 按記憶體使用率排序
   top -o %MEM
   ```

3. **df**：顯示磁碟空間
   ```bash
   # 顯示所有檔案系統
   df -h

   # 顯示特定檔案系統
   df -h /dev/sda1
   ```

4. **du**：顯示目錄大小
   ```bash
   # 顯示目錄大小
   du -h /path/to/directory

   # 顯示目錄及子目錄大小
   du -sh /path/to/directory/*

   # 顯示最大的 10 個目錄
   du -h /path/to/directory | sort -hr | head -n 10
   ```

### 網路

1. **ping**：測試網路連接
   ```bash
   # 測試網路連接
   ping google.com

   # 指定次數
   ping -c 4 google.com
   ```

2. **curl**：傳輸資料
   ```bash
   # 獲取網頁內容
   curl https://example.com

   # 下載檔案
   curl -O https://example.com/file.txt

   # 發送 POST 請求
   curl -X POST -d "param1=value1&param2=value2" https://example.com/api
   ```

3. **wget**：下載檔案
   ```bash
   # 下載檔案
   wget https://example.com/file.txt

   # 遞迴下載
   wget -r https://example.com

   # 後台下載
   wget -b https://example.com/largefile.iso
   ```

4. **netstat**：顯示網路連接
   ```bash
   # 顯示所有連接
   netstat -a

   # 顯示監聽的端口
   netstat -l

   # 顯示 TCP 連接
   netstat -t
   ```

### 壓縮與解壓縮

1. **tar**：打包檔案
   ```bash
   # 創建 tar 檔案
   tar -cf archive.tar file1 file2

   # 解開 tar 檔案
   tar -xf archive.tar

   # 創建 gzip 壓縮的 tar 檔案
   tar -czf archive.tar.gz directory

   # 解開 gzip 壓縮的 tar 檔案
   tar -xzf archive.tar.gz
   ```

2. **zip**：創建 zip 檔案
   ```bash
   # 創建 zip 檔案
   zip archive.zip file1 file2

   # 解開 zip 檔案
   unzip archive.zip

   # 遞迴壓縮目錄
   zip -r archive.zip directory
   ```

3. **gzip**：壓縮檔案
   ```bash
   # 壓縮檔案
   gzip file.txt

   # 解壓縮檔案
   gunzip file.txt.gz

   # 保留原始檔案
   gzip -k file.txt
   ```

這些指令和範例應該能幫助您更有效地使用 Shell。隨著您的經驗增加，您會發現 Shell 是一個非常強大的工具，可以幫助您自動化各種任務，提高工作效率。
