# Dockerfile 教學文件

## 目錄

1. [概述](#概述)
2. [入門級教學](#入門級教學)
    - [什麼是 Dockerfile](#什麼是-dockerfile)
    - [Dockerfile 的基本結構](#dockerfile-的基本結構)
    - [基礎映像檔 (Base Image)](#基礎映像檔-base-image)
    - [基本指令介紹](#基本指令介紹)
    - [建立第一個 Dockerfile](#建立第一個-dockerfile)
    - [建置 (Build) 映像檔](#建置-build-映像檔)
    - [執行容器](#執行容器)
    - [入門範例](#入門範例)
3. [進階教學](#進階教學)
    - [多階段建置 (Multi-stage Builds)](#多階段建置-multi-stage-builds)
    - [快取機制 (Caching)](#快取機制-caching)
    - [最佳化映像檔大小](#最佳化映像檔大小)
    - [環境變數與參數](#環境變數與參數)
    - [資料卷 (Volumes)](#資料卷-volumes)
    - [網路設定](#網路設定)
    - [健康檢查](#健康檢查)
    - [進階範例](#進階範例)
4. [高級教學](#高級教學)
    - [Dockerfile 最佳實踐](#dockerfile-最佳實踐)
    - [安全性考量](#安全性考量)
    - [自動化建置流程](#自動化建置流程)
    - [映像檔分層優化](#映像檔分層優化)
    - [跨平台建置](#跨平台建置)
    - [高級範例](#高級範例)
5. [常用指令參考](#常用指令參考)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 Dockerfile 的撰寫技巧。無論您是完全沒有 Docker 經驗的初學者，還是已經了解基礎功能的進階學習者，或是想要深入了解更複雜設定的高級使用者，本文檔都能為您提供所需的知識和技能。

Dockerfile 是用來建立 Docker 映像檔的文字檔案，它包含了一系列的指令，告訴 Docker 如何建立一個映像檔。通過學習 Dockerfile，您可以了解如何打包應用程式及其依賴項，使其能夠在任何安裝了 Docker 的環境中一致地運行。

## 入門級教學

本節適合完全沒有 Docker 經驗的初學者。我們將從最基本的概念開始，逐步建立您對 Dockerfile 的理解。

### 什麼是 Dockerfile

Dockerfile 是一個文字檔案，其中包含了一系列的指令，用來告訴 Docker 如何建立一個映像檔。映像檔就像是一個模板，可以用來創建容器。容器則是映像檔的運行實例，就像是從程式安裝檔安裝出來的應用程式。

想像一下，如果您要教別人如何做一道菜，您會寫下食譜，列出所有需要的食材和步驟。Dockerfile 就像是這樣的食譜，它告訴 Docker 需要哪些材料（基礎映像檔和其他檔案）以及如何處理這些材料（執行命令、複製檔案等）來製作最終的「菜餚」（映像檔）。

### Dockerfile 的基本結構

一個基本的 Dockerfile 通常包含以下部分：

1. **基礎映像檔**：指定從哪個映像檔開始建立
2. **設定工作目錄**：指定在容器中的工作目錄
3. **複製檔案**：將檔案從主機複製到容器中
4. **安裝依賴項**：安裝應用程式所需的軟體和套件
5. **設定環境變數**：設定應用程式所需的環境變數
6. **指定啟動命令**：指定容器啟動時要執行的命令

以下是一個簡單的 Dockerfile 範例：

```dockerfile
# 使用 Ubuntu 20.04 作為基礎映像檔
FROM ubuntu:20.04

# 設定工作目錄
WORKDIR /app

# 複製應用程式檔案
COPY . /app

# 安裝 Python
RUN apt-get update && apt-get install -y python3 python3-pip

# 安裝應用程式依賴項
RUN pip3 install -r requirements.txt

# 設定環境變數
ENV PORT=8080

# 指定容器啟動時要執行的命令
CMD ["python3", "app.py"]
```

### 基礎映像檔 (Base Image)

基礎映像檔是建立新映像檔的起點。它通常是一個精簡的作業系統映像檔，或是已經包含了某些工具或程式語言環境的映像檔。

選擇基礎映像檔時，應考慮以下因素：

1. **大小**：較小的基礎映像檔可以減少最終映像檔的大小，加快下載和部署速度。
2. **安全性**：選擇有定期安全更新的映像檔。
3. **功能性**：確保映像檔包含您應用程式所需的基本工具和函式庫。

常見的基礎映像檔包括：

- **ubuntu**：完整的 Ubuntu 作業系統，大小較大但功能齊全。
- **alpine**：非常小的 Linux 發行版，僅約 5MB，適合建立小型映像檔。
- **node**：包含 Node.js 環境的映像檔，適合 JavaScript 應用程式。
- **python**：包含 Python 環境的映像檔，適合 Python 應用程式。
- **openjdk**：包含 Java 環境的映像檔，適合 Java 應用程式。

在 Dockerfile 中使用 `FROM` 指令來指定基礎映像檔：

```dockerfile
FROM ubuntu:20.04
```

或

```dockerfile
FROM alpine:3.14
```

### 基本指令介紹

Dockerfile 中有許多指令，以下是最常用的基本指令：

1. **FROM**：指定基礎映像檔
   ```dockerfile
   FROM ubuntu:20.04
   ```

2. **WORKDIR**：設定工作目錄
   ```dockerfile
   WORKDIR /app
   ```

3. **COPY**：將檔案從主機複製到容器中
   ```dockerfile
   COPY . /app
   ```

4. **ADD**：類似 COPY，但有額外功能，如自動解壓縮
   ```dockerfile
   ADD app.tar.gz /app
   ```

5. **RUN**：在建置映像檔時執行命令
   ```dockerfile
   RUN apt-get update && apt-get install -y python3
   ```

6. **ENV**：設定環境變數
   ```dockerfile
   ENV PORT=8080
   ```

7. **EXPOSE**：指定容器要監聽的埠號
   ```dockerfile
   EXPOSE 8080
   ```

8. **CMD**：指定容器啟動時要執行的命令
   ```dockerfile
   CMD ["python3", "app.py"]
   ```

9. **ENTRYPOINT**：類似 CMD，但更難被覆蓋
   ```dockerfile
   ENTRYPOINT ["python3", "app.py"]
   ```

### 建立第一個 Dockerfile

讓我們一步一步建立一個簡單的 Dockerfile，用來打包一個簡單的網頁應用程式：

1. 首先，創建一個新的資料夾，並在其中創建一個名為 `Dockerfile` 的檔案（沒有副檔名）。

2. 在 Dockerfile 中輸入以下內容：

```dockerfile
# 使用 Node.js 14 作為基礎映像檔
FROM node:14-alpine

# 設定工作目錄
WORKDIR /app

# 複製 package.json 和 package-lock.json
COPY package*.json ./

# 安裝依賴項
RUN npm install

# 複製應用程式檔案
COPY . .

# 設定環境變數
ENV PORT=3000

# 指定容器要監聽的埠號
EXPOSE 3000

# 指定容器啟動時要執行的命令
CMD ["npm", "start"]
```

3. 在同一個資料夾中，創建一個簡單的 Node.js 應用程式：

    - 創建 `package.json`：
      ```json
      {
        "name": "docker-demo",
        "version": "1.0.0",
        "description": "簡單的 Docker 示範",
        "main": "index.js",
        "scripts": {
          "start": "node index.js"
        },
        "dependencies": {
          "express": "^4.17.1"
        }
      }
      ```

    - 創建 `index.js`：
      ```javascript
      const express = require('express');
      const app = express();
      const port = process.env.PORT || 3000;
 
      app.get('/', (req, res) => {
        res.send('你好，Docker！');
      });
 
      app.listen(port, () => {
        console.log(`應用程式正在監聽 ${port} 埠`);
      });
      ```

### 建置 (Build) 映像檔

現在，我們可以使用 `docker build` 命令來建置映像檔：

```bash
docker build -t my-node-app .
```

這個命令會讀取當前目錄中的 Dockerfile，並建立一個名為 `my-node-app` 的映像檔。

建置過程中，Docker 會執行 Dockerfile 中的每一個指令，並為每個指令創建一個新的層。這些層會被快取，如果您再次建置相同的映像檔，Docker 會重用這些層，加快建置速度。

### 執行容器

建置完成後，我們可以使用 `docker run` 命令來執行容器：

```bash
docker run -p 3000:3000 my-node-app
```

這個命令會從 `my-node-app` 映像檔創建一個容器，並將主機的 3000 埠映射到容器的 3000 埠。

現在，您可以在瀏覽器中訪問 `http://localhost:3000`，應該會看到「你好，Docker！」的訊息。

### 入門範例

以下是一個更完整的入門範例，展示如何使用 Dockerfile 打包一個簡單的 Python Flask 應用程式：

1. 創建 Dockerfile：

```dockerfile
# 使用 Python 3.9 作為基礎映像檔
FROM python:3.9-slim

# 設定工作目錄
WORKDIR /app

# 複製 requirements.txt
COPY requirements.txt .

# 安裝依賴項
RUN pip install --no-cache-dir -r requirements.txt

# 複製應用程式檔案
COPY . .

# 設定環境變數
ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0
ENV FLASK_RUN_PORT=5000

# 指定容器要監聽的埠號
EXPOSE 5000

# 指定容器啟動時要執行的命令
CMD ["flask", "run"]
```

2. 創建 `requirements.txt`：

```
flask==2.0.1
```

3. 創建 `app.py`：

```python
from flask import Flask, render_template

app = Flask(__name__)

@app.route('/')
def hello():
    return '你好，Docker！這是一個 Flask 應用程式。'

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
```

4. 建置映像檔：

```bash
docker build -t my-flask-app .
```

5. 執行容器：

```bash
docker run -p 5000:5000 my-flask-app
```

6. 在瀏覽器中訪問 `http://localhost:5000`，應該會看到「你好，Docker！這是一個 Flask 應用程式。」的訊息。

## 進階教學

本節適合已經了解 Dockerfile 基礎知識的學習者。我們將探討更進階的概念和技巧，幫助您建立更高效、更小、更安全的映像檔。

### 多階段建置 (Multi-stage Builds)

多階段建置是一種強大的技術，可以幫助您創建更小的映像檔。它允許您在一個 Dockerfile 中使用多個 `FROM` 指令，每個指令開始一個新的建置階段。您可以從一個階段複製檔案到另一個階段，但最終的映像檔只包含最後一個階段的內容。

這對於需要編譯的語言（如 Go、Java、C++ 等）特別有用，因為您可以在一個階段中編譯程式，然後只將編譯好的二進制檔案複製到最終的映像檔中，而不需要包含編譯工具和源代碼。

以下是一個使用多階段建置的 Dockerfile 範例，用於建置一個 Go 應用程式：

```dockerfile
# 第一階段：建置階段
FROM golang:1.17 AS builder

WORKDIR /app

# 複製 Go 模組檔案並下載依賴項
COPY go.mod go.sum ./
RUN go mod download

# 複製源代碼
COPY . .

# 編譯應用程式
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o main .

# 第二階段：最終階段
FROM alpine:3.14

WORKDIR /app

# 從建置階段複製編譯好的二進制檔案
COPY --from=builder /app/main .

# 執行應用程式
CMD ["./main"]
```

在這個例子中，第一階段使用 `golang:1.17` 映像檔來編譯應用程式，第二階段使用 `alpine:3.14` 映像檔作為基礎，只包含編譯好的二進制檔案。這樣可以大幅減少最終映像檔的大小。

### 快取機制 (Caching)

Docker 在建置映像檔時會使用快取來加速建置過程。了解快取機制可以幫助您更有效地建置映像檔。

Docker 建置映像檔時，會為 Dockerfile 中的每個指令創建一個新的層。如果您再次建置相同的映像檔，Docker 會檢查每個指令是否有變化。如果指令沒有變化，Docker 會重用之前建置的層，而不是重新執行指令。

以下是一些利用快取機制的技巧：

1. **將不常變化的指令放在前面**：
   ```dockerfile
   # 先複製 package.json，安裝依賴項
   COPY package.json .
   RUN npm install

   # 再複製其他檔案
   COPY . .
   ```

   這樣，如果 `package.json` 沒有變化，Docker 會重用之前的層，不需要重新安裝依賴項。

2. **合併相關的指令**：
   ```dockerfile
   # 不好的做法
   RUN apt-get update
   RUN apt-get install -y python3

   # 好的做法
   RUN apt-get update && apt-get install -y python3
   ```

   合併相關的指令可以減少層的數量，並確保 `apt-get update` 和 `apt-get install` 在同一層中執行，避免使用過時的套件列表。

3. **使用 `.dockerignore` 檔案**：
   創建一個 `.dockerignore` 檔案，列出不需要複製到映像檔中的檔案和目錄，可以減少建置上下文的大小，加快建置速度。

   ```
   node_modules
   npm-debug.log
   .git
   .gitignore
   ```

### 最佳化映像檔大小

映像檔大小對於下載、部署和運行速度都有影響。以下是一些減少映像檔大小的技巧：

1. **使用較小的基礎映像檔**：
   ```dockerfile
   # 不好的做法
   FROM ubuntu:20.04

   # 好的做法
   FROM alpine:3.14
   ```

   Alpine Linux 是一個非常小的 Linux 發行版，僅約 5MB，而 Ubuntu 映像檔則約 70MB。

2. **清理不必要的檔案**：
   ```dockerfile
   RUN apt-get update && apt-get install -y python3 \
       && apt-get clean \
       && rm -rf /var/lib/apt/lists/*
   ```

   在安裝套件後清理套件管理器的快取可以減少映像檔大小。

3. **使用多階段建置**：如前所述，多階段建置可以幫助您只包含必要的檔案在最終映像檔中。

4. **減少層的數量**：
   ```dockerfile
   # 不好的做法
   RUN mkdir -p /app/data
   RUN mkdir -p /app/logs

   # 好的做法
   RUN mkdir -p /app/data /app/logs
   ```

   合併相關的指令可以減少層的數量，減少映像檔大小。

### 環境變數與參數

環境變數和參數可以使您的 Dockerfile 更加靈活，適應不同的環境和需求。

1. **使用 ENV 設定環境變數**：
   ```dockerfile
   ENV APP_HOME=/app
   ENV PORT=8080

   WORKDIR ${APP_HOME}
   EXPOSE ${PORT}
   ```

   環境變數可以在 Dockerfile 中多次使用，使配置更加集中和一致。

2. **使用 ARG 設定建置參數**：
   ```dockerfile
   ARG VERSION=latest
   FROM node:${VERSION}
   ```

   建置參數只在建置時有效，不會保留在最終的映像檔中。您可以在建置時覆蓋這些參數：

   ```bash
   docker build --build-arg VERSION=14 -t my-node-app .
   ```

3. **結合 ARG 和 ENV**：
   ```dockerfile
   ARG VERSION=latest
   FROM node:${VERSION}

   ARG PORT=3000
   ENV PORT=${PORT}

   EXPOSE ${PORT}
   ```

   這樣，您可以在建置時設定 PORT，並將其保留為環境變數在容器中使用。

### 資料卷 (Volumes)

資料卷是 Docker 中持久化數據的方式。在 Dockerfile 中，您可以使用 `VOLUME` 指令來定義容器中的掛載點。

```dockerfile
# 定義資料卷
VOLUME /app/data

# 或定義多個資料卷
VOLUME ["/app/data", "/app/logs"]
```

當容器運行時，Docker 會在主機上創建一個匿名卷，並將其掛載到容器中的指定路徑。您也可以在運行容器時指定要使用的卷：

```bash
docker run -v my-data:/app/data my-app
```

這會將名為 `my-data` 的卷掛載到容器中的 `/app/data` 路徑。

資料卷有以下優點：

- 數據持久化：即使容器被刪除，數據仍然保留在卷中。
- 數據共享：多個容器可以掛載同一個卷，共享數據。
- 性能：卷的 I/O 性能通常比容器內部的檔案系統更好。

### 網路設定

在 Dockerfile 中，您可以使用 `EXPOSE` 指令來指定容器要監聽的埠號：

```dockerfile
EXPOSE 8080
```

這只是一個文檔性質的指令，告訴使用者容器會監聽哪些埠號，但實際上並不會開放這些埠號。要在運行容器時開放埠號，您需要使用 `-p` 或 `-P` 選項：

```bash
# 將主機的 8080 埠映射到容器的 8080 埠
docker run -p 8080:8080 my-app

# 將容器中所有 EXPOSE 的埠號映射到主機的隨機埠號
docker run -P my-app
```

Docker 也提供了多種網路模式，您可以在運行容器時指定：

```bash
# 使用橋接網路（默認）
docker run --network bridge my-app

# 使用主機網路
docker run --network host my-app

# 使用自定義網路
docker network create my-network
docker run --network my-network my-app
```

### 健康檢查

在 Dockerfile 中，您可以使用 `HEALTHCHECK` 指令來定義如何檢查容器是否健康：

```dockerfile
# 每 30 秒檢查一次，超時時間為 10 秒，重試 3 次後才標記為不健康
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl -f http://localhost:8080/health || exit 1
```

這個指令會定期執行指定的命令，如果命令返回 0，則容器被視為健康；如果返回非 0，則容器被視為不健康。

健康檢查有助於 Docker 和編排工具（如 Kubernetes）了解容器的狀態，並在需要時重啟不健康的容器。

### 進階範例

以下是一個進階的 Dockerfile 範例，展示了多階段建置、快取優化、環境變數、資料卷和健康檢查等技術：

```dockerfile
# 第一階段：建置階段
FROM node:14-alpine AS builder

WORKDIR /app

# 複製 package.json 和 package-lock.json
COPY package*.json ./

# 安裝依賴項
RUN npm ci

# 複製源代碼
COPY . .

# 建置應用程式
RUN npm run build

# 第二階段：運行階段
FROM nginx:alpine

# 設定參數和環境變數
ARG PORT=80
ENV PORT=${PORT}

# 從建置階段複製建置好的檔案
COPY --from=builder /app/build /usr/share/nginx/html

# 複製 Nginx 配置
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 定義資料卷
VOLUME /usr/share/nginx/html/data

# 指定容器要監聽的埠號
EXPOSE ${PORT}

# 設定健康檢查
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl -f http://localhost:${PORT} || exit 1

# 啟動 Nginx
CMD ["nginx", "-g", "daemon off;"]
```

這個 Dockerfile 用於建置一個 React 應用程式，並使用 Nginx 作為 Web 伺服器。它使用多階段建置來減少最終映像檔的大小，並包含了快取優化、環境變數、資料卷和健康檢查等進階技術。

## 高級教學

本節適合已經熟悉 Dockerfile 進階概念的學習者。我們將探討更高級的主題，幫助您建立更高效、更安全、更可維護的映像檔。

### Dockerfile 最佳實踐

以下是一些 Dockerfile 的最佳實踐，可以幫助您建立更好的映像檔：

1. **使用特定版本的基礎映像檔**：
   ```dockerfile
   # 不好的做法
   FROM node

   # 好的做法
   FROM node:14.17.5-alpine3.14
   ```

   使用特定版本可以確保您的映像檔在不同環境中的一致性。

2. **使用非 root 用戶**：
   ```dockerfile
   # 創建非 root 用戶
   RUN addgroup -g 1000 appuser && \
       adduser -u 1000 -G appuser -s /bin/sh -D appuser

   # 切換到非 root 用戶
   USER appuser
   ```

   使用非 root 用戶可以提高容器的安全性。

3. **使用 .dockerignore 檔案**：
   創建一個 `.dockerignore` 檔案，列出不需要複製到映像檔中的檔案和目錄，可以減少建置上下文的大小，加快建置速度。

   ```
   node_modules
   npm-debug.log
   .git
   .gitignore
   ```

4. **最小化層的數量**：
   合併相關的指令可以減少層的數量，減少映像檔大小。

   ```dockerfile
   # 不好的做法
   RUN apt-get update
   RUN apt-get install -y python3
   RUN apt-get clean

   # 好的做法
   RUN apt-get update && \
       apt-get install -y python3 && \
       apt-get clean && \
       rm -rf /var/lib/apt/lists/*
   ```

5. **使用多階段建置**：
   如前所述，多階段建置可以幫助您只包含必要的檔案在最終映像檔中。

6. **使用 ENTRYPOINT 和 CMD 的組合**：
   ```dockerfile
   ENTRYPOINT ["nginx"]
   CMD ["-g", "daemon off;"]
   ```

   `ENTRYPOINT` 指定容器啟動時要執行的命令，`CMD` 提供默認參數。這樣，您可以在運行容器時覆蓋參數，但不能覆蓋命令。

### 安全性考量

建立安全的映像檔是非常重要的。以下是一些安全性考量：

1. **使用最小的基礎映像檔**：
   較小的基礎映像檔通常包含較少的套件，減少了潛在的安全漏洞。

   ```dockerfile
   FROM alpine:3.14
   ```

2. **定期更新基礎映像檔**：
   定期更新基礎映像檔可以獲取最新的安全修補程式。

   ```dockerfile
   FROM ubuntu:20.04
   RUN apt-get update && apt-get upgrade -y
   ```

3. **使用非 root 用戶**：
   如前所述，使用非 root 用戶可以提高容器的安全性。

4. **不要在映像檔中包含敏感信息**：
   不要在 Dockerfile 中包含密碼、API 金鑰等敏感信息。使用環境變數或 Docker 的密碼管理功能來處理敏感信息。

   ```dockerfile
   # 不好的做法
   ENV API_KEY=1234567890abcdef

   # 好的做法
   # 在運行容器時設定環境變數
   # docker run -e API_KEY=1234567890abcdef my-app
   ```

5. **掃描映像檔中的安全漏洞**：
   使用工具如 Docker Scan、Trivy 或 Clair 來掃描映像檔中的安全漏洞。

   ```bash
   # 使用 Docker Scan
   docker scan my-app
   ```

6. **使用內容信任**：
   使用 Docker Content Trust (DCT) 來確保只有簽名的映像檔才能被拉取和運行。

   ```bash
   # 啟用內容信任
   export DOCKER_CONTENT_TRUST=1

   # 拉取映像檔
   docker pull my-app
   ```

### 自動化建置流程

自動化建置流程可以幫助您更一致、更可靠地建置映像檔。以下是一些自動化建置流程的技巧：

1. **使用 CI/CD 工具**：
   使用 Jenkins、GitLab CI/CD、GitHub Actions 等工具來自動化建置流程。

   以下是一個使用 GitHub Actions 的範例：

   ```yaml
   name: Build and Push Docker Image

   on:
     push:
       branches: [ main ]

   jobs:
     build:
       runs-on: ubuntu-latest
       steps:
       - uses: actions/checkout@v2

       - name: Build and push Docker image
         uses: docker/build-push-action@v2
         with:
           context: .
           push: true
           tags: user/app:latest
   ```

2. **使用 Docker Compose**：
   使用 Docker Compose 來定義和運行多容器 Docker 應用程式。

   ```yaml
   version: '3'
   services:
     web:
       build: .
       ports:
         - "8080:80"
       environment:
         - NODE_ENV=production
     db:
       image: postgres
       volumes:
         - db-data:/var/lib/postgresql/data
   volumes:
     db-data:
   ```

3. **使用 Docker Buildx**：
   使用 Docker Buildx 來建置多平台映像檔。

   ```bash
   # 建置多平台映像檔
   docker buildx build --platform linux/amd64,linux/arm64 -t my-app .
   ```

### 映像檔分層優化

Docker 映像檔是由多個層組成的，了解如何優化這些層可以幫助您建立更高效的映像檔。

1. **了解層的概念**：
   每個 Dockerfile 指令都會創建一個新的層。層是增量的，只包含相對於前一層的變化。

2. **最小化層的數量**：
   合併相關的指令可以減少層的數量，減少映像檔大小。

   ```dockerfile
   # 不好的做法
   RUN apt-get update
   RUN apt-get install -y python3
   RUN apt-get clean

   # 好的做法
   RUN apt-get update && \
       apt-get install -y python3 && \
       apt-get clean && \
       rm -rf /var/lib/apt/lists/*
   ```

3. **優化層的順序**：
   將不常變化的層放在前面，將經常變化的層放在後面，可以更好地利用快取。

   ```dockerfile
   # 不好的做法
   COPY . .
   RUN npm install

   # 好的做法
   COPY package.json .
   RUN npm install
   COPY . .
   ```

4. **使用 .dockerignore 檔案**：
   使用 `.dockerignore` 檔案來排除不需要的檔案，減少建置上下文的大小，加快建置速度。

   ```
   node_modules
   npm-debug.log
   .git
   .gitignore
   ```

### 跨平台建置

如果您需要在不同的平台（如 x86_64、ARM64 等）上運行容器，您可以使用跨平台建置技術。

1. **使用 Docker Buildx**：
   Docker Buildx 是一個 Docker CLI 插件，可以用來建置多平台映像檔。

   ```bash
   # 建置多平台映像檔
   docker buildx build --platform linux/amd64,linux/arm64 -t my-app .
   ```

2. **使用 QEMU**：
   QEMU 是一個開源的模擬器，可以用來在一個平台上模擬另一個平台的指令集。

   ```bash
   # 安裝 QEMU
   docker run --privileged --rm tonistiigi/binfmt --install all
   ```

3. **使用多階段建置**：
   使用多階段建置來為不同的平台建置不同的二進制檔案。

   ```dockerfile
   # 第一階段：建置 amd64 二進制檔案
   FROM --platform=linux/amd64 golang:1.17 AS builder-amd64
   WORKDIR /app
   COPY . .
   RUN GOOS=linux GOARCH=amd64 go build -o main-amd64 .

   # 第二階段：建置 arm64 二進制檔案
   FROM --platform=linux/arm64 golang:1.17 AS builder-arm64
   WORKDIR /app
   COPY . .
   RUN GOOS=linux GOARCH=arm64 go build -o main-arm64 .

   # 第三階段：最終階段
   FROM alpine:3.14
   WORKDIR /app
   COPY --from=builder-amd64 /app/main-amd64 .
   COPY --from=builder-arm64 /app/main-arm64 .
   # 根據平台選擇適當的二進制檔案
   CMD ["./main-amd64"]
   ```

### 高級範例

以下是一個高級的 Dockerfile 範例，展示了多階段建置、跨平台建置、安全性考量和最佳實踐等技術：

```dockerfile
# 第一階段：建置階段
FROM --platform=$BUILDPLATFORM golang:1.17-alpine AS builder

# 設定建置參數
ARG TARGETPLATFORM
ARG BUILDPLATFORM
ARG VERSION=1.0.0

# 設定工作目錄
WORKDIR /app

# 安裝建置工具
RUN apk add --no-cache git

# 複製 Go 模組檔案並下載依賴項
COPY go.mod go.sum ./
RUN go mod download

# 複製源代碼
COPY . .

# 根據目標平台設定 GOARCH
RUN case ${TARGETPLATFORM} in \
      "linux/amd64") GOARCH=amd64 ;; \
      "linux/arm64") GOARCH=arm64 ;; \
      *) GOARCH=amd64 ;; \
    esac && \
    CGO_ENABLED=0 GOOS=linux GOARCH=${GOARCH} go build -ldflags="-X main.Version=${VERSION}" -o main .

# 第二階段：最終階段
FROM alpine:3.14

# 設定工作目錄
WORKDIR /app

# 安裝運行時依賴項
RUN apk add --no-cache ca-certificates tzdata && \
    update-ca-certificates

# 創建非 root 用戶
RUN addgroup -g 1000 appuser && \
    adduser -u 1000 -G appuser -s /bin/sh -D appuser

# 從建置階段複製編譯好的二進制檔案
COPY --from=builder /app/main .

# 設定檔案權限
RUN chown -R appuser:appuser /app

# 切換到非 root 用戶
USER appuser

# 設定健康檢查
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD wget -q --spider http://localhost:8080/health || exit 1

# 指定容器要監聽的埠號
EXPOSE 8080

# 執行應用程式
ENTRYPOINT ["/app/main"]
```

這個 Dockerfile 用於建置一個 Go 應用程式，並包含了多階段建置、跨平台建置、安全性考量和最佳實踐等高級技術。它使用 `BUILDPLATFORM` 和 `TARGETPLATFORM` 參數來支援跨平台建置，使用非 root
用戶來提高安全性，並使用健康檢查來監控容器的狀態。

## 常用指令參考

以下是一些常用的 Docker 指令，可以幫助您管理映像檔和容器：

### 映像檔管理

1. **建置映像檔**：
   ```bash
   # 從當前目錄的 Dockerfile 建置映像檔
   docker build -t my-app .

   # 指定 Dockerfile 路徑
   docker build -t my-app -f Dockerfile.prod .

   # 設定建置參數
   docker build -t my-app --build-arg VERSION=1.0.0 .
   ```

2. **列出映像檔**：
   ```bash
   # 列出所有映像檔
   docker images

   # 列出特定映像檔
   docker images my-app
   ```

3. **刪除映像檔**：
   ```bash
   # 刪除特定映像檔
   docker rmi my-app

   # 刪除所有未使用的映像檔
   docker image prune
   ```

4. **拉取映像檔**：
   ```bash
   # 從 Docker Hub 拉取映像檔
   docker pull ubuntu:20.04
   ```

5. **推送映像檔**：
   ```bash
   # 推送映像檔到 Docker Hub
   docker push user/my-app
   ```

### 容器管理

1. **運行容器**：
   ```bash
   # 運行容器
   docker run my-app

   # 運行容器並映射埠號
   docker run -p 8080:80 my-app

   # 運行容器並設定環境變數
   docker run -e NODE_ENV=production my-app

   # 運行容器並掛載卷
   docker run -v my-data:/app/data my-app

   # 運行容器並在背景執行
   docker run -d my-app
   ```

2. **列出容器**：
   ```bash
   # 列出運行中的容器
   docker ps

   # 列出所有容器（包括已停止的）
   docker ps -a
   ```

3. **停止容器**：
   ```bash
   # 停止容器
   docker stop container_id

   # 停止所有容器
   docker stop $(docker ps -q)
   ```

4. **刪除容器**：
   ```bash
   # 刪除容器
   docker rm container_id

   # 刪除所有已停止的容器
   docker container prune
   ```

5. **查看容器日誌**：
   ```bash
   # 查看容器日誌
   docker logs container_id

   # 持續查看容器日誌
   docker logs -f container_id
   ```

6. **在容器中執行命令**：
   ```bash
   # 在容器中執行命令
   docker exec -it container_id bash
   ```

### Docker Compose

1. **啟動服務**：
   ```bash
   # 啟動所有服務
   docker-compose up

   # 在背景啟動所有服務
   docker-compose up -d
   ```

2. **停止服務**：
   ```bash
   # 停止所有服務
   docker-compose down

   # 停止所有服務並刪除卷
   docker-compose down -v
   ```

3. **查看服務狀態**：
   ```bash
   # 查看服務狀態
   docker-compose ps
   ```

4. **查看服務日誌**：
   ```bash
   # 查看所有服務的日誌
   docker-compose logs

   # 查看特定服務的日誌
   docker-compose logs service_name
   ```

5. **在服務中執行命令**：
   ```bash
   # 在服務中執行命令
   docker-compose exec service_name command
   ```

這些指令可以幫助您管理 Docker 映像檔和容器，以及使用 Docker Compose 來管理多容器應用程式。通過掌握這些指令，您可以更有效地使用 Docker 來開發、測試和部署應用程式。
