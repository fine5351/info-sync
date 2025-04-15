文章目录
Github
官网
文档
ShardingSphere-Proxy
mysql-connector-java 驱动下载
conf 配置
global.yaml
database-sharding.yaml
database-readwrite-splitting.yaml
docker
docker-compose.yml
Apache ShardingSphere 是一款分布式的数据库生态系统， 可以将任意数据库转换为分布式数据库，并通过数据分片、弹性伸缩、加密等能力对原有数据库进行增强。

特性	定义
数据分片	数据分片，是应对海量数据存储与计算的有效手段。ShardingSphere 基于底层数据库提供分布式数据库解决方案，可以水平扩展计算和存储。
分布式事务	事务能力，是保障数据库完整、安全的关键技术，也是数据库的核心技术。基于 XA 和 BASE 的混合事务引擎，ShardingSphere 提供在独立数据库上的分布式事务功能，保证跨数据源的数据安全。
读写分离	读写分离，是应对高压力业务访问的手段。基于对 SQL 语义理解及对底层数据库拓扑感知能力，ShardingSphere 提供灵活的读写流量拆分和读流量负载均衡。
数据迁移	数据迁移，是打通数据生态的关键能力。ShardingSphere 提供跨数据源的数据迁移能力，并可支持重分片扩展。
联邦查询	联邦查询，是面对复杂数据环境下利用数据的有效手段。ShardingSphere 提供跨数据源的复杂查询分析能力，实现跨源的数据关联与聚合。
数据加密	数据加密，是保证数据安全的基本手段。ShardingSphere 提供完整、透明、安全、低成本的数据加密解决方案。
影子库	在全链路压测场景下，ShardingSphere 支持不同工作负载下的数据隔离，避免测试数据污染生产环境。
ShardingSphere-JDBC： 定位为轻量级 Java 框架，在 Java 的 JDBC 层提供的额外服务。

ShardingSphere-Proxy： 的定位为透明化的数据库代理，理论上支持任何使用 MySQL、PostgreSQL、openGauss 协议的客户端操作数据，对异构语言、运维场景更友好。

ShardingSphere-JDBC	ShardingSphere-Proxy
数据库	任意	MySQL/PostgreSQL
连接消耗数	高	低
异构语言	仅 Java	任意
性能	损耗低	损耗略高
无中心化	是	否
静态入口	无	有
Apache ShardingSphere 设计哲学为 Database Plus，旨在构建异构数据库上层的标准和生态。 它关注如何充分合理地利用数据库的计算和存储能力，而并非实现一个全新的数据库。 它站在数据库的上层视角，关注它们之间的协作多于数据库自身。




Github
https://github.com/apache/shardingsphere
官网
https://shardingsphere.apache.org/index_zh.html
文档
https://shardingsphere.apache.org/document/current/cn/overview/
ShardingSphere-Proxy
https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-proxy/startup/docker/
https://hub.docker.com/r/apache/shardingsphere-proxy
docker pull apache/shardingsphere-proxy:latest
1
创建缩主机目录
cd /home
mkdir shardingsphere && cd shardingsphere
mkdir ext-lib
1
2
3
mysql-connector-java 驱动下载
https://repo.maven.apache.org/maven2/mysql/mysql-connector-java/
注： 将下载的 mysql-connector-java-5.1.9.jar 包复制到缩主机 /home/shardingsphere/ext-lib 目录下，注意自己当前 数据库版本 选择相应的驱动包。

conf 配置
拷贝容器 conf 到缩主机
# 创建容器
docker run -d --name shardingsphere --entrypoint=bash apache/shardingsphere-proxy
# 复制配置
docker cp shardingsphere:/opt/shardingsphere-proxy/conf /home/shardingsphere
# 删除容器
docker rm shardingsphere
1
2
3
4
5
6


database-encrypt.yaml: 定义数据库的加密策略和配置。可能包括对特定表或列的数据加密算法、加密密钥管理等配置。

database-readwrite-splitting.yaml: 定义数据库读写分离的配置。读写分离可以根据不同的规则将读操作和写操作分发到不同的数据库节点或者数据库副本上，以提升数据库的读写性能和负载均衡能力。

global.yaml: 定义全局的配置信息，例如全局的数据源配置、全局的规则配置等。这些配置通常会影响整个 ShardingSphere 的运行行为和规则解析。

database-hbase.yaml: 配置和管理连接到 HBase 数据库的相关设置。这可能包括连接信息、表映射、列簇定义等。

database-shadow.yaml: 配置数据库影子复制（Shadow Replica）的相关设置。影子复制是一种技术，用于在生产环境中实时复制数据库的操作，以便在测试或分析时使用真实的数据副本而不影响生产系统。

database-mask.yaml: 配置数据脱敏（Data Masking）策略。数据脱敏用于隐藏或掩盖数据库中敏感信息，确保只有经过授权的用户可以查看特定数据的明文。

database-sharding.yaml: 配置数据库分片（Sharding）的策略和规则。数据库分片是一种将数据库表按照某种规则分布到多个数据库节点中的技术，以提升数据库的扩展性和性能。

global.yaml
#mode:
#  type: Cluster
#  repository:
#    type: ZooKeeper
#    props:
#      namespace: governance_ds
#      server-lists: localhost:2181
#      retryIntervalMilliseconds: 500
#      timeToLiveSeconds: 60
#      maxRetries: 3
#      operationTimeoutMilliseconds: 500

authority:
 users:
   - user: root@%
     password: '4NuMDwIzp05BdKp7Bdmf'
   - user: sharding
     password: sharding
 privilege:
   type: ALL_PERMITTED

# 分布式事务
#transaction:
#  defaultType: XA
#  providerType: Atomikos

# SQL 解析
#sqlParser:
#  sqlStatementCache:
#    initialCapacity: 2000
#    maximumSize: 65535
#  parseTreeCache:
#    initialCapacity: 128
#    maximumSize: 1024

logging:
 loggers:
 - loggerName: ShardingSphere-SQL
   additivity: true
   level: INFO
   props:
     enable: false

# 联邦查询
sqlFederation:
 sqlFederationEnabled: false
 executionPlanCache:
   initialCapacity: 2000
   maximumSize: 65535

props:
 # 系统日志输出级别，支持 DEBUG、INFO、WARN 和 ERROR，默认级别是 INFO。
 system-log-level: INFO
 # 一次查询请求在每个数据库实例中所能使用的最大连接数。
 max-connections-size-per-query: 1
 # 用于设置任务处理线程池的大小。每个 ShardingSphereDataSource 使用一个独立的线程池，同一个 JVM 的不同数据源不共享线程池。
 kernel-executor-size: 16  # Infinite by default.
 # 在 ShardingSphere-Proxy 中设置传输数据条数的 IO 刷新阈值。
 proxy-frontend-flush-threshold: 128  # The default value is 128.
 #  是否在日志中打印 SQL。打印 SQL 可以帮助开发者快速定位系统问题。日志内容包含：逻辑 SQL，真实 SQL 和 SQL 解析结果。如果开启配置，日志将使用 Topic ShardingSphere-SQL，日志级别是 INFO。
 sql-show: true
 #  在程序启动和更新时，是否检查分片元数据的结构一致性。
 check-table-metadata-enabled: false
 # Proxy 后端与数据库交互的每次获取数据行数（使用游标的情况下）。数值增大可能会增加 ShardingSphere Proxy 的内存使用。默认值为 -1，代表设置为 JDBC 驱动的最小值。
 proxy-backend-query-fetch-size: -1
 # Proxy 前端 Netty 线程池线程数量，默认值 0 代表使用 Netty 默认值。
 proxy-frontend-executor-size: 0 # Proxy frontend executor size. The default value is 0, which means let Netty decide.
 # 允许连接 Proxy 的最大客户端数量，默认值 0 代表不限制。
 proxy-frontend-max-connections: 0 # Less than or equal to 0 means no limitation.
 # Proxy 前端协议类型，支持 MySQL，PostgreSQL 和 openGauss
 proxy-frontend-database-protocol-type: MySQL
#  proxy-default-port: 3307 # Proxy default port.
#  proxy-netty-backlog: 1024 # Proxy netty backlog.
#  cdc-server-port: 33071 # CDC server port
#  proxy-frontend-ssl-enabled: false
#  proxy-frontend-ssl-cipher: ''
#  proxy-frontend-ssl-version: TLSv1.2,TLSv1.3
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
database-sharding.yaml
databaseName: sharding_db

dataSources:
 ds_0:
   url: jdbc:mysql://192.168.0.100:3306/demo_0?useSSL=false
   username: test1
   password: 'te123456'
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1
 ds_1:
   url: jdbc:mysql://192.168.0.101:3306/demo_1?useSSL=false
   username: test2
   password: 'te123456'
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1

#rules:
#- !SHARDING
#  tables:
#    t_order:
#      actualDataNodes: ds_${0..1}.t_order_${0..1}
...
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
database-readwrite-splitting.yaml
databaseName: readwrite_splitting_db

dataSources:
 write_ds:
   url: jdbc:mysql://192.168.0.100:3306/demo_0?useSSL=false
   username: test1
   password: 'te123456'
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1
 read_ds_0:
   url: jdbc:mysql://192.168.0.101:3306/demo_1?useSSL=false
   username: test2
   password: 'te123456'
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
docker
docker run -d --name shardingsphere \
-v /home/shardingsphere/conf:/opt/shardingsphere-proxy/conf \
-v /home/shardingsphere/ext-lib:/opt/shardingsphere-proxy/ext-lib \
-e JVM_OPTS="-Djava.awt.headless=true" \
-e CGROUP_MEM_OPTS="-XX:InitialRAMPercentage=80.0 -XX:MaxRAMPercentage=80.0 -XX:MinRAMPercentage=80.0" \
-e PORT=3308 \
-p 3308:3308 \
apache/shardingsphere-proxy:latest
1
2
3
4
5
6
7
8
docker-compose.yml
services:
  shardingsphere:
    image: apache/shardingsphere-proxy:latest
    container_name: shardingsphere
    restart: always
    ports:
      - 3308:3308
    volumes:
      - /etc/localtime:/etc/localtime
      - /home/shardingsphere/conf:/opt/shardingsphere-proxy/conf
      - /home/shardingsphere/ext-lib:/opt/shardingsphere-proxy/ext-lib
    environment:
      PORT: 3308
      JVM_OPTS: "-Djava.awt.headless=true"
      CGROUP_MEM_OPTS: "-XX:InitialRAMPercentage=80.0 -XX:MaxRAMPercentage=80.0 -XX:MinRAMPercentage=80.0"
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
docker-compose up -d shardingsphere
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。
                        
原文链接：https://blog.csdn.net/weixin_42607526/article/details/140331333

### 其他
https://cloud.tencent.com/developer/article/2022206
