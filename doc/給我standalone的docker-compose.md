好的，這是一個使用 Standalone (獨立) 模式部署 ShardingSphere Proxy 的 docker-compose.yml 範例。在這種模式下，我們**不需要 ZooKeeper**，設定和規則將直接從本地設定檔載入。

我們將繼續使用 MariaDB 作為後端資料庫範例。

目標：  
建立一個 docker-compose.yml 檔案，用來啟動 ShardingSphere Proxy (Standalone 模式) 和一個 MariaDB 資料庫。  
**步驟：**

1. **建立設定檔目錄** (如果尚未建立):  
   Bash  
   mkdir conf

2. 準備 ShardingSphere Proxy 設定檔 (conf/server.yaml):  
   這是 Standalone 模式與 Cluster 模式的主要區別。你需要直接在 server.yaml 中定義資料庫、資料來源和規則。  
   YAML  
   \# conf/server.yaml (Standalone 模式範例)  
   mode:  
     type: Standalone \# 指定模式為 Standalone  
     \# 不需要 repository 設定  
     overwrite: false \# 是否允許環境變數覆蓋設定檔中的配置

   props:  
     proxy-frontend-database-protocol-type: MySQL \# Proxy 對外協議  
     \# 其他 props 設定...  
     \# sql-show: true \# 方便調試

   authentication: \# Proxy 登入帳號密碼  
     root:  
       user: root  
       password: root \# 生產環境請務必修改

   \# \--- 在 Standalone 模式下，直接定義資料庫、資料來源和規則 \---  
   databases:  
     sharding\_db\_standalone: \# 定義一個邏輯資料庫名稱  
       dataSourceNodes: \# 定義資料來源節點 (與下方 dataSources 的 key 對應)  
         ds\_0: \# 資料來源名稱  
           url: jdbc:mariadb://mariadb:3306/ds\_0?serverTimezone=UTC \# 連接到 docker-compose 中的 mariadb 服務  
           username: root  
           password: password \# MariaDB 的 root 密碼  
           connectionTimeoutMilliseconds: 30000  
           idleTimeoutMilliseconds: 60000  
           maxLifetimeMilliseconds: 1800000  
           maxPoolSize: 50  
           minPoolSize: 1  
       rules:  
         \# \--- 分片規則範例 \---  
         \- \!SHARDING  
           tables: \# 定義分片表  
             t\_order: \# 邏輯表名  
               actualDataNodes: ds\_0.t\_order\_${0..1} \# 實際節點，表示 ds\_0 庫中的 t\_order\_0 和 t\_order\_1 表  
               tableStrategy: \# 分表策略  
                 standard:  
                   shardingColumn: order\_id \# 分片鍵  
                   shardingAlgorithmName: t\_order\_inline \# 使用下方定義的分片算法  
               keyGenerateStrategy: \# 主鍵生成策略 (可選)  
                 column: order\_id  
                 keyGeneratorName: snowflake \# 使用 snowflake 算法  
           \# 可以繼續定義其他表，例如 t\_order\_item...  
           \#  t\_order\_item:  
           \#    actualDataNodes: ds\_0.t\_order\_item\_${0..1}  
           \#    tableStrategy:  
           \#      standard:  
           \#        shardingColumn: order\_id \# 通常使用與主表相同的分片鍵  
           \#        shardingAlgorithmName: t\_order\_inline \# 可以重用算法  
           \#    keyGenerateStrategy:  
           \#      column: item\_id  
           \#      keyGeneratorName: snowflake

           \# bindingTables: \# 綁定表 (可選)，確保關聯表分到同一個分片  
           \#   \- t\_order,t\_order\_item

           \# broadcastTables: \# 廣播表 (可選)，所有分片都有完整數據的表，如配置表  
           \#   \- t\_config

           \# \--- 分片算法定義 \---  
           shardingAlgorithms:  
             t\_order\_inline: \# 上面策略中引用的算法名稱  
               type: INLINE \# 使用 INLINE 算法 (簡單表達式)  
               props:  
                 algorithm-expression: t\_order\_${order\_id % 2} \# 分片表達式，結果為 t\_order\_0 或 t\_order\_1

           \# \--- 主鍵生成器定義 \---  
           keyGenerators:  
             snowflake:  
               type: SNOWFLAKE  
               \# props: \# 可以設定 worker-id 等屬性  
               \#   worker-id: 123  
         \# \--- 可以繼續添加其他規則，例如讀寫分離、加密等 \---  
         \# \- \!READWRITE\_SPLITTING  
         \#   dataSources:  
         \#     \# ...  
         \# \- \!ENCRYPT  
         \#   encryptors:  
         \#     \# ...  
         \#   tables:  
         \#     \# ...

   \# 全局規則 (也可以在這裡定義，但建議放在 database 下)  
   \# rules:  
   \#   \- \!AUTHORITY \# 權限規則  
   \#     users:  
   \#       \- root@%:root \# ShardingSphere Proxy 的使用者  
   \#       \- sharding@%:sharding  
   \#     provider:  
   \#       type: ALL\_PERMITTED \# 允許所有權限 (預設)  
   \#   \- \!SQL\_PARSER \# SQL 解析器設定 (可選)  
   \#     sqlCommentParseEnabled: false  
   \#     parseTreeCache:  
   \#       initialCapacity: 128  
   \#       maximumSize: 1024  
   \#     sqlStatementCache:  
   \#       initialCapacity: 2000  
   \#       maximumSize: 65535

   **注意:** Standalone 模式的 server.yaml 相對複雜，因為所有設定都在這裡。actualDataNodes 中的 ds\_0 必須與 dataSourceNodes 中定義的 key 一致。t\_order\_${0..1} 表示會在 ds\_0 這個資料來源上尋找 t\_order\_0 和 t\_order\_1 這兩張實際的資料表。你需要預先在 MariaDB 的 ds\_0 資料庫中建立這些表。  
3. **建立 docker-compose.yml 檔案** (Standalone 模式):  
   YAML  
   version: '3.8'

   services:  
     \# ShardingSphere Proxy 服務 (Standalone)  
     shardingsphere-proxy-standalone:  
       image: apache/shardingsphere-proxy:latest  
       container\_name: shardingsphere-proxy-standalone  
       hostname: shardingsphere-proxy-standalone  
       ports:  
         \- "3307:3307" \# Proxy 對外端口  
       volumes:  
         \- ./conf:/opt/shardingsphere-proxy/conf \# 掛載包含 server.yaml 的 conf 目錄  
       environment:  
         \- PORT=3307  
         \- JVM\_OPTS=-Xmx1g \-Xms1g \# 可根據需要調整記憶體  
       depends\_on:  
         \- mariadb-standalone \# 依賴 mariadb 服務  
       restart: on-failure

     \# MariaDB 服務  
     mariadb-standalone:  
       image: mariadb:latest  
       container\_name: mariadb-standalone  
       hostname: mariadb \# 保持 hostname 為 mariadb，以匹配 server.yaml 中的 JDBC URL  
       ports:  
         \- "3306:3306" \# 映射 MariaDB 端口 (方便直接連接檢查)  
       environment:  
         MARIADB\_ROOT\_PASSWORD: password \# 設定 MariaDB root 密碼  
         MARIADB\_DATABASE: ds\_0 \# 預先建立一個資料庫，與 server.yaml 中設定對應  
       \# 如果需要在啟動時執行初始化 SQL 腳本 (例如創建 t\_order\_0, t\_order\_1 表)  
       \# volumes:  
       \#   \- ./init-mariadb:/docker-entrypoint-initdb.d  
       restart: on-failure

   networks:  
     default:  
       driver: bridge

   \# 如果有掛載初始化腳本，可以定義 Volume  
   \# volumes:  
   \#   init-mariadb:

**說明與差異：**

* **無 ZooKeeper:** docker-compose.yml 中完全移除了 zookeeper 服務。  
* **server.yaml 核心:** 所有資料庫連接、分片規則（或其他 ShardingSphere 規則）都定義在 conf/server.yaml 檔案中。這是 Standalone 模式與 Cluster 模式最主要的區別。  
* **depends\_on:** Proxy 服務僅依賴後端資料庫 (mariadb-standalone)。  
* **容器名稱:** 為了清晰起見，建議使用不同的容器名稱（例如加上 \-standalone 後綴）。  
* **DistSQL 限制:** 在 Standalone 模式下，使用 DistSQL 對規則進行的修改**不會被持久化**（因為沒有註冊中心來儲存），Proxy 重新啟動後會恢復到 server.yaml 中定義的狀態。查詢類的 DistSQL (如 SHOW DATABASES, SHOW SHARDING TABLE RULES) 仍然可用。

**4\. 啟動容器**

在包含 docker-compose.yml 和 conf 目錄的專案根目錄下，執行：

Bash

docker-compose up \-d

**5\. 連接與使用**

* **連接 Proxy**: 使用 MySQL 客戶端連接到 localhost:3307，使用者 root，密碼 root。  
* **執行 SQL**: 你現在可以像操作普通資料庫一樣執行 SQL。如果插入資料到 t\_order 表，ShardingSphere Proxy 會根據 server.yaml 中定義的 order\_id % 2 規則，將資料路由到後端 MariaDB 的 t\_order\_0 或 t\_order\_1 表中。  
  * **重要:** 你需要先確保 MariaDB 容器內的 ds\_0 資料庫中已經存在 t\_order\_0 和 t\_order\_1 這兩張表。可以透過映射初始化腳本或手動連接到 MariaDB 容器 (localhost:3306) 來創建。

這個設定適用於不需要高可用、規則變動不頻繁、單一 Proxy 節點就能滿足需求的場景。

**來源**  
1\. [https://shardingsphere.medium.com/full-link-online-stress-testing-for-production-database-apache-shardingsphere-shadow-database-84f7cba56f99?source=rss-b9760a2e8a07------2](https://shardingsphere.medium.com/full-link-online-stress-testing-for-production-database-apache-shardingsphere-shadow-database-84f7cba56f99?source=rss-b9760a2e8a07------2)