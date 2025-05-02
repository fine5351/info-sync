# Apache Airflow 教學文件

這份教學文件將分為初級、中級、高級三個階段，幫助你從不會用到會上線、除錯與調校，全面掌握 Airflow 這套排程與工作流工具。

---

## 🟢 初級階段：Airflow 是什麼？怎麼安裝與開始使用？

### ✅ Airflow 是什麼？

Airflow 是 Apache 出品的一套工作排程系統。你可以想像它是一個「自動執行任務的管家」，可以讓你設定任務（像寫 Python 程式），再依照時間或順序自動執行。

### ✅ 安裝 Airflow（使用 Docker 快速入門）

Airflow 官方提供一套 docker-compose 安裝方案，非常適合初學者。

#### 步驟：

```bash
git clone https://github.com/apache/airflow.git
cd airflow
cd docs/apache-airflow/start/docker-compose
cp .env.example .env
```

#### 啟動：

```bash
docker-compose up airflow-init
```

```bash
docker-compose up -d
```

### ✅ 登入 Airflow UI

打開瀏覽器，輸入：[http://localhost:8080](http://localhost:8080)

* 帳號：airflow
* 密碼：airflow

### ✅ 建立第一個 DAG（排程任務）

建立檔案：`dags/hello_dag.py`

```python
from airflow import DAG
from airflow.operators.python import PythonOperator
from datetime import datetime

def say_hello():
    print("Hello Airflow!")

def say_bye():
    print("Bye Airflow!")

default_args = {
    'start_date': datetime(2024, 1, 1)
}

dag = DAG('hello_dag', schedule_interval='@daily', default_args=default_args, catchup=False)

hello_task = PythonOperator(task_id='say_hello', python_callable=say_hello, dag=dag)
bye_task = PythonOperator(task_id='say_bye', python_callable=say_bye, dag=dag)

hello_task >> bye_task
```

---

## 🟡 中級階段：實務應用與進階操作

### ✅ 使用 BashOperator、EmailOperator

```python
from airflow.operators.bash import BashOperator
from airflow.operators.email import EmailOperator

bash = BashOperator(
    task_id='run_bash',
    bash_command='echo hello from bash',
    dag=dag
)

email = EmailOperator(
    task_id='send_email',
    to='you@example.com',
    subject='Airflow 任務完成通知',
    html_content='<p>成功了！</p>',
    dag=dag
)
```

### ✅ 傳遞參數給任務

```python
def greet(name):
    print(f"哈囉, {name}")

greet_task = PythonOperator(
    task_id='greet_name',
    python_callable=greet,
    op_args=['小明'],
    dag=dag
)
```

### ✅ 使用 XCom 傳遞資料

```python
def push_data(**kwargs):
    kwargs['ti'].xcom_push(key='my_key', value='Hello!')

def pull_data(**kwargs):
    value = kwargs['ti'].xcom_pull(task_ids='push', key='my_key')
    print(f"拿到的值是：{value}")

push = PythonOperator(task_id='push', python_callable=push_data, provide_context=True, dag=dag)
pull = PythonOperator(task_id='pull', python_callable=pull_data, provide_context=True, dag=dag)

push >> pull
```

### ✅ 自訂 Plugin、Hook、Sensor（進階內容略）

建議參考官方文檔進行擴充：[https://airflow.apache.org/docs/apache-airflow/stable/plugins/index.html](https://airflow.apache.org/docs/apache-airflow/stable/plugins/index.html)

---

## 🔴 高級階段：錯誤排查與效能優化

### ✅ 任務失敗怎麼辦？

* 可在 UI 點選任務，看 Logs 查看錯誤原因
* 任務可設定重試次數與重試間隔：

```python
default_args = {
    'retries': 3,
    'retry_delay': timedelta(minutes=5)
}
```

### ✅ 調整排程與效能建議

1. **避免大量同時啟動的 DAG**：設置 `max_active_runs`
2. **使用資源池 pool 控制任務數量**
3. **設計輕量化的 DAG**，避免 DAG 內有大量資料處理，可改為觸發其他系統

### ✅ 使用 Celery Executor / Kubernetes Executor

若 DAG 執行太慢或量太大，建議改用分散式執行架構：

* Celery Executor：透過 Redis + Worker 執行任務
* Kubernetes Executor：每個任務跑一個 Pod，自動擴容

### ✅ 整合 Slack 通知、Prometheus 監控

* 透過 Webhook 發送 Slack 告警
* 使用 Airflow Exporter 搭配 Grafana 畫圖表

---

這份教學完整涵蓋了 Airflow 的初級、中級與高級操作範疇，讓你從完全不懂到可以部署生產任務、擴充與優化！
