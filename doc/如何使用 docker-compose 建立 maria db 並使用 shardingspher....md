好的，我們可以修改之前的 docker-compose.yml 設定，將後端資料庫從 MySQL 替換成 MariaDB，並繼續使用 ShardingSphere Proxy 作為代理。

目標：  
建立一個 docker-compose.yml 檔案，用來啟動 ShardingSphere Proxy、一個 MariaDB 資料庫（作為後端資料庫）以及 ZooKeeper（作為註冊中心）。  
**核心改動：**

1. 將 docker-compose.yml 中的 mysql 服務替換成 mariadb 服務。  
2. 修改 MariaDB 相關的環境變數。  
3. 當使用 DistSQL 註冊儲存單元 (Storage Unit) 時，需要使用 MariaDB 的 JDBC URL 格式。

**步驟：**

1. **建立設定檔目錄** (如果尚未建立):  
   Bash  
   mkdir conf

2. **準備 ShardingSphere Proxy 設定檔 (conf/server.yaml)** (這部分與之前使用 MySQL 時相同，因為 Proxy 對外仍模擬 MySQL 協議):  
   YAML  
   \# conf/server.yaml  
   mode:  
     type: Cluster  
     repository:  
       type: ZooKeeper  
       props:  
         namespace: shardingsphere\_proxy\_mariadb \# 可以使用新的命名空間以示區別  
         server-lists: zookeeper:2181  
         retryIntervalMilliseconds: 500  
         timeToLiveSeconds: 60  
         maxRetries: 3  
         operationTimeoutMilliseconds: 500  
     overwrite: false

   props:  
     proxy-frontend-database-protocol-type: MySQL \# Proxy 對外協議保持 MySQL  
     \# 其他設定...  
     \# sql-show: true \# 可以在測試時開啟 SQL 顯示

   authentication:  
     root:  
       user: root  
       password: root \# Proxy 登入密碼 (生產環境請修改)

   rules: \[\] \# 規則透過 DistSQL 或治理中心設定

3. 建立 docker-compose.yml 檔案 (使用 MariaDB):  
   在你的專案根目錄下建立或修改 docker-compose.yml 檔案：  
   YAML  
   version: '3.8'

   services:  
     \# ShardingSphere Proxy 服務  
     shardingsphere-proxy:  
       image: apache/shardingsphere-proxy:latest  
       container\_name: shardingsphere-proxy-mariadb \# 建議給予不同名稱  
       hostname: shardingsphere-proxy  
       ports:  
         \- "3307:3307" \# Proxy 對外端口  
       volumes:  
         \- ./conf:/opt/shardingsphere-proxy/conf  
       environment:  
         \- PORT=3307  
         \- JVM\_OPTS=-Xmx2g \-Xms2g  
       depends\_on:  
         \- zookeeper  
         \- mariadb \# 依賴 mariadb 服務  
       restart: on-failure

     \# ZooKeeper 服務 (註冊中心)  
     zookeeper:  
       image: zookeeper:3.9  
       container\_name: zookeeper-mariadb \# 建議給予不同名稱  
       hostname: zookeeper  
       ports:  
         \- "2181:2181"  
       restart: on-failure

     \# MariaDB 服務 (替換 MySQL)  
     mariadb:  
       image: mariadb:latest \# 使用 MariaDB 官方鏡像 (或指定版本如 mariadb:10.11)  
       container\_name: mariadb-backend \# 建議給予不同名稱  
       hostname: mariadb \# 服務名稱，用於內部連接  
       ports:  
         \- "3306:3306" \# 映射 MariaDB 端口 (方便直接連接檢查)  
       environment:  
         MARIADB\_ROOT\_PASSWORD: password \# 設定 MariaDB root 密碼  
         MARIADB\_DATABASE: ds\_0 \# 預先建立一個資料庫 (範例)  
         \# 可以加上 MARIADB\_USER 和 MARIADB\_PASSWORD 來建立非 root 用戶  
       \# 如果需要在啟動時執行初始化 SQL 腳本，可以掛載 .sql 文件  
       \# volumes:  
       \#   \- ./init-mariadb:/docker-entrypoint-initdb.d  
       restart: on-failure

   networks:  
     default:  
       driver: bridge

**說明改動點：**

* **服務名稱**: 將 mysql 服務區塊替換為 mariadb。  
* **鏡像**: image 從 mysql:8.0 改為 mariadb:latest (或你需要的特定版本)。  
* **環境變數**: MySQL 使用 MYSQL\_ROOT\_PASSWORD 和 MYSQL\_DATABASE，而 MariaDB 使用 MARIADB\_ROOT\_PASSWORD 和 MARIADB\_DATABASE。  
* **依賴關係**: shardingsphere-proxy 的 depends\_on 從 mysql 改為 mariadb。  
* **容器/主機名**: 建議修改 container\_name 和 hostname 以避免與之前的 MySQL 設置衝突（例如 mariadb-backend, zookeeper-mariadb 等），雖然在本例中 hostname 保持 mariadb 和 zookeeper 以便於設定檔和 JDBC URL 的一致性。  
* **server.yaml**: proxy-frontend-database-protocol-type 保持 MySQL 不變，因為 ShardingSphere Proxy 是模擬 MySQL 協議與客戶端溝通的。

**4\. 啟動容器**

在包含 docker-compose.yml 和 conf 目錄的專案根目錄下，執行：

Bash

docker-compose up \-d

**5\. 連接與設定**

* **連接 Proxy**: 使用 MySQL 客戶端連接到 localhost:3307，使用者 root，密碼 root (與之前相同)。  
* **設定規則 (使用 DistSQL)**: 這是關鍵的區別！當你註冊儲存單元時，需要使用 MariaDB 的 JDBC URL。  
  SQL  
  \-- 範例 DistSQL (在連接 Proxy 後執行)  
  CREATE DATABASE sharding\_db\_mariadb; \-- 創建邏輯庫  
  USE sharding\_db\_mariadb; \-- 切換邏輯庫

  \-- 註冊後端 MariaDB 資料庫作為儲存單元  
  REGISTER STORAGE UNIT ds\_0 (  
      URL\='jdbc:mariadb://mariadb:3306/ds\_0?serverTimezone=UTC', \-- \*\*\*注意這裡是 jdbc:mariadb://\*\*\*  
      USER\='root',  
      PASSWORD\='password', \-- docker-compose 中設定的 MariaDB 密碼  
      PROPS('maxPoolSize'\=50, 'idleTimeoutMilliseconds'\=30000)  
  );  
  \-- 如果有 ds\_1, ds\_2 等，繼續註冊...

  \-- 創建分片規則 (與之前類似)  
  CREATE SHARDING TABLE RULE t\_order (  
     STORAGE\_UNITS(ds\_0),  
     SHARDING\_COLUMN\=order\_id,  
     TYPE(NAME\='MOD', PROPS('sharding-count'\='2'))  
  );

  \-- ... 其他規則設定

* **停止容器:**  
  Bash  
  docker-compose down

現在，你的 ShardingSphere Proxy 就會將 SQL 請求代理到後端的 MariaDB 資料庫了。主要記得在透過 DistSQL 設定資料來源 (Storage Unit) 時，提供正確的 MariaDB JDBC URL (jdbc:mariadb://...)。