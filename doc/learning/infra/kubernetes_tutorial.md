# Kubernetes (K8s) 教學文件

## 目錄

1. [概述](#概述)
2. [入門級教學](#入門級教學)
    - [什麼是 Kubernetes](#什麼是-kubernetes)
    - [Kubernetes 的主要功能](#kubernetes-的主要功能)
    - [Kubernetes 的基本架構](#kubernetes-的基本架構)
   - [kubectl 入門教學](#kubectl-入門教學)
    - [deployment.yml 基礎](#deployment-基礎)
    - [configmap.yml 基礎](#configmap-基礎)
    - [virtualservice.yml 基礎](#virtualservice-基礎)
    - [destination.yml 基礎](#destination-基礎)
    - [入門範例](#入門範例)
3. [進階教學](#進階教學)
   - [kubectl 進階教學](#kubectl-進階教學)
    - [deployment.yml 進階設定](#deployment-進階設定)
    - [configmap.yml 進階設定](#configmap-進階設定)
    - [virtualservice.yml 進階設定](#virtualservice-進階設定)
    - [destination.yml 進階設定](#destination-進階設定)
    - [進階範例](#進階範例)
4. [高級教學](#高級教學)
   - [kubectl 高級教學](#kubectl-高級教學)
    - [deployment.yml 高級設定](#deployment-高級設定)
    - [configmap.yml 高級設定](#configmap-高級設定)
    - [virtualservice.yml 高級設定](#virtualservice-高級設定)
    - [destination.yml 高級設定](#destination-高級設定)
    - [高級範例](#高級範例)
5. [常用指令](#常用指令)

## 概述

本教學文件旨在幫助不同程度的學習者掌握 Kubernetes (K8s) 容器編排系統。無論您是完全沒有 Kubernetes 經驗的初學者，還是已經了解基礎功能的進階學習者，或是想要深入了解更複雜設定的高級使用者，本文檔都能為您提供所需的知識和技能。

Kubernetes 是一個開源的容器編排平台，它可以自動化部署、擴展和管理容器化應用程序。通過學習 Kubernetes，您可以了解如何使用這個強大的系統來提高應用程序的可靠性和可擴展性。

本教學將特別關注四種重要的 Kubernetes 配置文件：

- deployment.yml：用於部署和管理應用程序
- configmap.yml：用於管理配置數據
- virtualservice.yml：用於管理服務流量路由（Istio 服務網格的一部分）
- destination.yml：用於定義服務目標（Istio 服務網格的一部分）

## 入門級教學

本節適合完全沒有 Kubernetes 經驗的初學者。我們將從最基本的概念開始，逐步建立您對 Kubernetes 的理解。

### 什麼是 Kubernetes

Kubernetes（常簡稱為 K8s）是一個開源的容器編排平台，由 Google 開發並捐贈給雲原生計算基金會（CNCF）。它的名字來源於希臘語，意思是"舵手"或"飛行員"，象徵著它能夠引導和管理容器化應用程序。

Kubernetes 的主要功能是：

- 自動部署應用程序
- 自動擴展應用程序
- 自動修復應用程序
- 管理應用程序的配置
- 負載均衡
- 服務發現

想像一下，如果您有一個應用程序需要運行在多台電腦上，而且需要確保它始終可用，即使某些電腦出現故障。Kubernetes 就像一個聰明的管理員，它會自動將您的應用程序放在合適的電腦上運行，並在需要時自動增加或減少運行的數量。

### Kubernetes 的主要功能

Kubernetes 提供了許多功能，幫助開發人員和運維人員管理容器化應用程序：

1. **自動部署**：Kubernetes 可以根據您的要求自動將應用程序部署到集群中的任何節點上。

2. **自動擴展**：當應用程序需要處理更多請求時，Kubernetes 可以自動增加運行的容器數量；當請求減少時，它可以自動減少容器數量，節省資源。

3. **自我修復**：如果一個容器崩潰或節點故障，Kubernetes 會自動重新啟動容器或在其他節點上重新創建它。

4. **服務發現**：Kubernetes 可以幫助容器找到並連接到其他容器，即使它們運行在不同的節點上。

5. **負載均衡**：Kubernetes 可以將流量均勻地分配到多個容器上，確保沒有一個容器過載。

6. **配置管理**：Kubernetes 可以管理應用程序的配置數據，並在需要時更新它，而不需要重新構建容器映像。

7. **存儲編排**：Kubernetes 可以自動掛載您選擇的存儲系統，無論是本地存儲還是雲存儲。

### Kubernetes 的基本架構

Kubernetes 系統由以下主要組件組成：

1. **Master 節點**：控制整個 Kubernetes 集群的大腦。它包含：
    - API Server：所有命令和通信的入口點
    - Scheduler：決定在哪些節點上運行新的容器
    - Controller Manager：確保集群的實際狀態與期望狀態匹配
    - etcd：存儲集群的所有配置和狀態信息的數據庫

2. **Worker 節點**：實際運行應用程序容器的機器。每個節點包含：
    - Kubelet：確保容器按照指示運行
    - Kube-proxy：管理節點上的網絡規則
    - Container Runtime：如 Docker，實際運行容器的軟件

3. **Pod**：Kubernetes 中最小的部署單位，可以包含一個或多個容器。

4. **Service**：為一組 Pod 提供穩定的網絡地址和負載均衡。

5. **Volume**：提供持久存儲，使數據在容器重啟後仍然存在。

6. **Namespace**：將一個 Kubernetes 集群分割成多個虛擬集群。

7. **ConfigMap 和 Secret**：管理應用程序的配置數據和敏感信息。

### kubectl 入門教學

kubectl 是 Kubernetes 的命令行工具，它允許您對 Kubernetes 集群運行命令。您可以使用 kubectl 來部署應用程序、檢查和管理集群資源，以及查看日誌。想像 kubectl 就像是您和 Kubernetes 集群之間的翻譯官，您用它來告訴
Kubernetes 您想要做什麼。

#### 什麼是 kubectl？

kubectl 是一個命令行工具，它讓您可以：

- 創建、刪除和更新 Kubernetes 資源（如 Pod、Deployment、Service 等）
- 查看集群中運行的資源
- 查看應用程序的日誌
- 在容器內執行命令
- 將本地端口轉發到集群中的 Pod

#### 安裝 kubectl

在開始使用 kubectl 之前，您需要先安裝它。以下是在不同操作系統上安裝 kubectl 的方法：

**Windows**：

1. 下載最新版本的 kubectl：
   ```
   curl -LO https://dl.k8s.io/release/v1.28.0/bin/windows/amd64/kubectl.exe
   ```
2. 將 kubectl.exe 添加到您的 PATH 環境變量中

**macOS**：

1. 使用 Homebrew 安裝：
   ```
   brew install kubectl
   ```
   或者下載二進制文件：
   ```
   curl -LO "https://dl.k8s.io/release/v1.28.0/bin/darwin/amd64/kubectl"
   chmod +x kubectl
   sudo mv kubectl /usr/local/bin/
   ```

**Linux**：

1. 下載二進制文件：
   ```
   curl -LO "https://dl.k8s.io/release/v1.28.0/bin/linux/amd64/kubectl"
   chmod +x kubectl
   sudo mv kubectl /usr/local/bin/
   ```

安裝完成後，您可以運行以下命令來確認 kubectl 已正確安裝：

```
kubectl version --client
```

#### 配置 kubectl

安裝 kubectl 後，您需要配置它以連接到 Kubernetes 集群。kubectl 使用一個名為 kubeconfig 的配置文件來存儲集群訪問信息。

通常，如果您使用雲服務提供商的 Kubernetes 服務（如 Google Kubernetes Engine、Amazon EKS 或 Azure AKS），您可以使用他們提供的命令來配置 kubectl：

**Google Kubernetes Engine (GKE)**：

```
gcloud container clusters get-credentials 集群名稱 --zone 區域 --project 項目ID
```

**Amazon EKS**：

```
aws eks update-kubeconfig --name 集群名稱 --region 區域
```

**Azure AKS**：

```
az aks get-credentials --resource-group 資源組 --name 集群名稱
```

如果您在本地運行 Kubernetes（如使用 Minikube 或 kind），這些工具通常會自動配置 kubectl。

#### kubectl 基本命令

現在，讓我們學習一些基本的 kubectl 命令：

**1. 查看集群信息**

```
kubectl cluster-info
```

這個命令會顯示集群的基本信息，包括 Kubernetes 控制平面的地址。

**2. 查看節點**

```
kubectl get nodes
```

這個命令會列出集群中的所有節點（即運行 Kubernetes 的機器）。

**3. 查看所有命名空間**

```
kubectl get namespaces
```

命名空間是 Kubernetes 中用來分隔資源的一種方式，有點像是不同的文件夾。

**4. 查看 Pod**

```
kubectl get pods
```

這個命令會列出默認命名空間中的所有 Pod。如果您想查看特定命名空間中的 Pod，可以使用：

```
kubectl get pods -n 命名空間名稱
```

**5. 查看 Deployment**

```
kubectl get deployments
```

這個命令會列出默認命名空間中的所有 Deployment。

**6. 查看 Service**

```
kubectl get services
```

這個命令會列出默認命名空間中的所有 Service。

**7. 查看資源的詳細信息**

```
kubectl describe pod Pod名稱
```

這個命令會顯示指定 Pod 的詳細信息，包括它的狀態、事件等。

**8. 查看 Pod 的日誌**

```
kubectl logs Pod名稱
```

這個命令會顯示指定 Pod 的日誌輸出。

**9. 在 Pod 中執行命令**

```
kubectl exec -it Pod名稱 -- 命令
```

例如，要在名為 "my-pod" 的 Pod 中運行 bash shell，您可以使用：

```
kubectl exec -it my-pod -- bash
```

**10. 將本地端口轉發到 Pod**

```
kubectl port-forward Pod名稱 本地端口:Pod端口
```

例如，要將本地的 8080 端口轉發到名為 "my-pod" 的 Pod 的 80 端口，您可以使用：

```
kubectl port-forward my-pod 8080:80
```

這樣，您就可以通過訪問 http://localhost:8080 來訪問 Pod 中運行的應用程序。

#### kubectl 實用技巧

**1. 使用簡短的資源名稱**

kubectl 支持資源名稱的簡寫，例如：

- `po` 代表 `pods`
- `deploy` 代表 `deployments`
- `svc` 代表 `services`
- `ns` 代表 `namespaces`

所以，您可以使用 `kubectl get po` 而不是 `kubectl get pods`。

**2. 使用 kubectl 自動補全**

您可以設置 kubectl 命令的自動補全功能，這樣當您按 Tab 鍵時，它會自動補全命令或資源名稱。

對於 Bash：

```
source <(kubectl completion bash)
```

對於 Zsh：

```
source <(kubectl completion zsh)
```

**3. 使用 kubectl 別名**

您可以為常用的 kubectl 命令創建別名，以節省輸入時間。例如：

```
alias k=kubectl
alias kgp='kubectl get pods'
alias kgd='kubectl get deployments'
```

#### kubectl 入門範例

讓我們通過一個簡單的例子來學習如何使用 kubectl 部署和管理一個應用程序：

**1. 創建一個 Deployment**

首先，我們將創建一個運行 Nginx 網頁服務器的 Deployment：

```
kubectl create deployment my-nginx --image=nginx
```

這個命令會創建一個名為 "my-nginx" 的 Deployment，它運行 Nginx 映像。

**2. 查看 Deployment**

```
kubectl get deployments
```

您應該會看到 "my-nginx" Deployment 已經創建。

**3. 查看 Pod**

```
kubectl get pods
```

您應該會看到一個由 "my-nginx" Deployment 創建的 Pod。

**4. 創建一個 Service**

現在，我們將創建一個 Service 來暴露 Nginx 服務器：

```
kubectl expose deployment my-nginx --port=80 --type=LoadBalancer
```

這個命令會創建一個名為 "my-nginx" 的 Service，它將流量轉發到 "my-nginx" Deployment 的 Pod 上。

**5. 查看 Service**

```
kubectl get services
```

您應該會看到 "my-nginx" Service 已經創建。如果您在雲環境中運行，LoadBalancer 類型的 Service 會獲得一個外部 IP 地址，您可以使用這個 IP 地址來訪問 Nginx 服務器。

**6. 擴展 Deployment**

如果您想運行更多的 Nginx 實例，您可以擴展 Deployment：

```
kubectl scale deployment my-nginx --replicas=3
```

這個命令會將 "my-nginx" Deployment 的副本數量增加到 3。

**7. 查看 Pod**

```
kubectl get pods
```

現在，您應該會看到有 3 個 Pod 在運行。

**8. 刪除資源**

當您不再需要這些資源時，您可以刪除它們：

```
kubectl delete service my-nginx
kubectl delete deployment my-nginx
```

這些命令會刪除 "my-nginx" Service 和 Deployment，以及它們創建的所有 Pod。

通過這個簡單的例子，您已經學會了如何使用 kubectl 來部署、查看、擴展和刪除 Kubernetes 資源。隨著您對 Kubernetes 的了解加深，您將能夠使用 kubectl 執行更複雜的操作。

### deployment.yml 基礎

Deployment 是 Kubernetes 中最常用的資源類型之一，它用於部署和管理應用程序。Deployment 確保指定數量的 Pod 副本在運行，並處理 Pod 的更新和回滾。

**deployment.yml 的基本結構**：

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: 我的容器
        image: nginx:1.14.2
        ports:
        - containerPort: 80
```

**主要部分解釋**：

1. **apiVersion**: 告訴 Kubernetes 這個文件使用哪個 API 版本。
2. **kind**: 指定這是一個 Deployment 資源。
3. **metadata**: 包含 Deployment 的名稱等信息。
4. **spec**: 包含 Deployment 的詳細規格：
    - **replicas**: 指定要運行的 Pod 副本數量（在這個例子中是 3 個）。
    - **selector**: 定義 Deployment 如何找到它管理的 Pod。
    - **template**: 定義 Pod 的規格：
        - **metadata**: Pod 的標籤等信息。
        - **spec**: Pod 的詳細規格，包括容器信息：
            - **containers**: 定義 Pod 中的容器：
                - **name**: 容器的名稱。
                - **image**: 容器使用的映像。
                - **ports**: 容器暴露的端口。

**deployment.yml 的作用**：

1. **創建和管理 Pod**: Deployment 會創建指定數量的 Pod 副本，並確保它們始終運行。
2. **自動擴展**: 您可以輕鬆地增加或減少 Pod 的數量。
3. **滾動更新**: 當您更新應用程序時，Deployment 會逐步替換舊的 Pod，確保服務不中斷。
4. **回滾**: 如果新版本有問題，Deployment 可以快速回滾到之前的版本。
5. **自我修復**: 如果 Pod 崩潰，Deployment 會自動創建新的 Pod 來替換它。

### configmap.yml 基礎

ConfigMap 是 Kubernetes 中用於存儲非機密配置數據的資源。它允許您將配置與容器映像分離，使應用程序更加可移植。

**configmap.yml 的基本結構**：

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: 我的配置
data:
  database.url: "mysql://localhost:3306/mydb"
  feature.enabled: "true"
  config.file: |
    server {
      listen 80;
      server_name example.com;
    }
```

**主要部分解釋**：

1. **apiVersion**: 告訴 Kubernetes 這個文件使用哪個 API 版本。
2. **kind**: 指定這是一個 ConfigMap 資源。
3. **metadata**: 包含 ConfigMap 的名稱等信息。
4. **data**: 包含配置數據的鍵值對。

**configmap.yml 的作用**：

1. **存儲配置**: ConfigMap 可以存儲各種配置數據，如環境變量、配置文件等。
2. **分離配置和代碼**: 將配置與容器映像分離，使應用程序更加可移植。
3. **動態更新**: 在某些情況下，應用程序可以動態讀取 ConfigMap 的更新，而不需要重啟。
4. **多種使用方式**: ConfigMap 可以作為環境變量、命令行參數或文件掛載到容器中。

### virtualservice.yml 基礎

VirtualService 是 Istio 服務網格的一部分，用於定義流量如何路由到服務。它提供了豐富的流量管理功能，如請求路由、重試、超時等。

**virtualservice.yml 的基本結構**：

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: 我的服務
spec:
  hosts:
  - 我的服務
  http:
  - route:
    - destination:
        host: 我的服務
        subset: v1
```

**主要部分解釋**：

1. **apiVersion**: 告訴 Kubernetes 這個文件使用哪個 API 版本。
2. **kind**: 指定這是一個 VirtualService 資源。
3. **metadata**: 包含 VirtualService 的名稱等信息。
4. **spec**: 包含 VirtualService 的詳細規格：
    - **hosts**: 指定此 VirtualService 適用的目標主機。
    - **http**: 定義 HTTP 流量的路由規則：
        - **route**: 定義流量的目標：
            - **destination**: 指定流量的目標服務和子集。

**virtualservice.yml 的作用**：

1. **流量路由**: 將流量路由到不同版本的服務。
2. **流量分割**: 將流量按比例分配到不同版本的服務（如 90% 到 v1，10% 到 v2）。
3. **故障注入**: 模擬服務故障，用於測試應用程序的彈性。
4. **超時和重試**: 設置請求的超時時間和重試策略。
5. **錯誤處理**: 定義當服務出錯時的處理方式。

### destination.yml 基礎

DestinationRule 也是 Istio 服務網格的一部分，它定義了流量到達目標服務後的策略，如負載均衡、連接池、異常檢測等。

**destination.yml 的基本結構**：

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: 我的目標規則
spec:
  host: 我的服務
  subsets:
  - name: v1
    labels:
      version: v1
  - name: v2
    labels:
      version: v2
```

**主要部分解釋**：

1. **apiVersion**: 告訴 Kubernetes 這個文件使用哪個 API 版本。
2. **kind**: 指定這是一個 DestinationRule 資源。
3. **metadata**: 包含 DestinationRule 的名稱等信息。
4. **spec**: 包含 DestinationRule 的詳細規格：
    - **host**: 指定此規則適用的目標服務。
    - **subsets**: 定義服務的不同子集：
        - **name**: 子集的名稱。
        - **labels**: 用於選擇屬於此子集的 Pod 的標籤。

**destination.yml 的作用**：

1. **定義服務子集**: 將服務分為不同的子集，如不同版本。
2. **負載均衡**: 設置不同的負載均衡策略，如輪詢、隨機、最少連接等。
3. **連接池**: 控制到目標服務的連接數量。
4. **異常檢測**: 定義何時將實例從負載均衡池中移除。
5. **TLS 設置**: 配置與目標服務的 TLS 連接。

### 入門範例

下面是一個簡單的入門範例，展示如何使用這四種配置文件部署一個簡單的 Web 應用程序：

**1. deployment.yml**:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: webapp
spec:
  replicas: 2
  selector:
    matchLabels:
      app: webapp
  template:
    metadata:
      labels:
        app: webapp
        version: v1
    spec:
      containers:
      - name: 網頁容器
        image: nginx:latest
        ports:
        - containerPort: 80
        volumeMounts:
        - name: 配置卷
          mountPath: /etc/nginx/conf.d
      volumes:
      - name: 配置卷
        configMap:
          name: 網頁配置
```

**2. configmap.yml**:

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: 網頁配置
data:
  default.conf: |
    server {
      listen 80;
      server_name localhost;

      location / {
        root /usr/share/nginx/html;
        index index.html;
      }
    }
  index.html: |
    <!DOCTYPE html>
    <html>
    <head>
      <title>歡迎來到我的網站</title>
    </head>
    <body>
      <h1>歡迎來到我的 Kubernetes 網站！</h1>
      <p>這是一個使用 Kubernetes 部署的簡單網頁。</p>
    </body>
    </html>
```

**3. virtualservice.yml**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: 網頁服務
spec:
  hosts:
  - "*"
  gateways:
  - 網頁網關
  http:
  - route:
    - destination:
        host: 網頁服務
        subset: v1
```

**4. destination.yml**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: 網頁目標規則
spec:
  host: 網頁服務
  subsets:
  - name: v1
    labels:
      version: v1
```

**這個範例的效果**:

1. **Deployment** 創建了兩個運行 Nginx 的 Pod。
2. **ConfigMap** 提供了 Nginx 的配置和一個簡單的 HTML 頁面。
3. **VirtualService** 將所有流量路由到服務的 v1 版本。
4. **DestinationRule** 定義了服務的 v1 子集。

## 進階教學

本節適合已經了解 Kubernetes 基礎知識的學習者。我們將深入探討更複雜的配置和功能。

### kubectl 進階教學

在掌握了 kubectl 的基本命令後，我們可以學習一些更進階的功能和技巧，這些將幫助您更有效地管理 Kubernetes 集群和應用程序。

#### 使用 YAML 文件管理資源

在實際工作中，我們通常不會直接使用命令行創建資源，而是使用 YAML 文件來定義資源，然後使用 `kubectl apply` 命令來應用這些文件。這種方法有以下優點：

1. **版本控制**：YAML 文件可以存儲在版本控制系統中，如 Git。
2. **可重複性**：您可以多次應用相同的 YAML 文件，得到相同的結果。
3. **文檔化**：YAML 文件本身就是文檔，描述了資源的配置。

**創建和應用 YAML 文件**：

1. 創建一個名為 `nginx-deployment.yaml` 的文件，內容如下：

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
   name: nginx-deployment
   labels:
      app: nginx
spec:
   replicas: 3
   selector:
      matchLabels:
         app: nginx
   template:
      metadata:
         labels:
            app: nginx
      spec:
         containers:
            - name: nginx
              image: nginx:1.14.2
              ports:
                 - containerPort: 80
```

2. 使用 `kubectl apply` 命令應用這個文件：

```
kubectl apply -f nginx-deployment.yaml
```

3. 您也可以一次應用多個文件：

```
kubectl apply -f nginx-deployment.yaml -f nginx-service.yaml
```

4. 或者應用整個目錄中的所有 YAML 文件：

```
kubectl apply -f k8s/
```

#### 使用標籤和選擇器

標籤（Labels）是 Kubernetes 中用於組織和選擇資源的關鍵功能。您可以使用標籤來分類資源，然後使用選擇器（Selectors）來選擇特定的資源。

**添加和修改標籤**：

1. 為 Pod 添加標籤：

```
kubectl label pod my-pod environment=production
```

2. 修改現有標籤：

```
kubectl label pod my-pod environment=staging --overwrite
```

3. 刪除標籤：

```
kubectl label pod my-pod environment-
```

**使用選擇器**：

1. 使用標籤選擇器列出資源：

```
kubectl get pods -l environment=production
```

2. 使用多個標籤選擇器：

```
kubectl get pods -l environment=production,app=nginx
```

3. 使用 `in` 運算符：

```
kubectl get pods -l 'environment in (production,staging)'
```

#### 使用命名空間

命名空間（Namespaces）是 Kubernetes 中用於隔離資源的一種方式。它們允許您在同一個集群中運行多個環境或應用程序，而不會相互干擾。

**創建和管理命名空間**：

1. 創建一個新的命名空間：

```
kubectl create namespace my-namespace
```

2. 在特定命名空間中創建資源：

```
kubectl apply -f nginx-deployment.yaml -n my-namespace
```

3. 列出特定命名空間中的資源：

```
kubectl get pods -n my-namespace
```

4. 設置默認命名空間：

```
kubectl config set-context --current --namespace=my-namespace
```

5. 查看所有命名空間中的資源：

```
kubectl get pods --all-namespaces
```

#### 使用 kubectl 進行故障排除

當您的應用程序出現問題時，kubectl 提供了多種工具來幫助您診斷和解決問題。

**查看 Pod 狀態和事件**：

1. 查看 Pod 的詳細信息：

```
kubectl describe pod my-pod
```

這個命令會顯示 Pod 的詳細信息，包括它的狀態、事件、容器信息等。特別注意 "Events" 部分，它通常包含有關 Pod 啟動或失敗的重要信息。

2. 查看 Pod 的日誌：

```
kubectl logs my-pod
```

如果 Pod 中有多個容器，您需要指定容器名稱：

```
kubectl logs my-pod -c my-container
```

3. 查看之前的容器日誌（如果容器已重啟）：

```
kubectl logs my-pod -c my-container -p
```

4. 持續查看日誌：

```
kubectl logs -f my-pod
```

**進入容器進行調試**：

1. 在容器中執行命令：

```
kubectl exec my-pod -- ls /app
```

2. 獲取交互式 shell：

```
kubectl exec -it my-pod -- /bin/bash
```

如果 Pod 中有多個容器，您需要指定容器名稱：

```
kubectl exec -it my-pod -c my-container -- /bin/bash
```

**複製文件**：

1. 從容器複製文件到本地：

```
kubectl cp my-pod:/path/to/file.txt ./local-file.txt
```

2. 從本地複製文件到容器：

```
kubectl cp ./local-file.txt my-pod:/path/to/file.txt
```

#### 使用 kubectl 進行資源管理

**擴展應用程序**：

1. 擴展 Deployment：

```
kubectl scale deployment my-deployment --replicas=5
```

2. 自動擴展 Deployment（需要安裝 Metrics Server）：

```
kubectl autoscale deployment my-deployment --min=2 --max=5 --cpu-percent=80
```

**更新應用程序**：

1. 更新容器映像：

```
kubectl set image deployment/my-deployment my-container=my-image:v2
```

2. 編輯資源：

```
kubectl edit deployment my-deployment
```

這會打開一個編輯器，您可以直接編輯 Deployment 的 YAML 定義。

3. 回滾更新：

```
kubectl rollout undo deployment my-deployment
```

4. 回滾到特定版本：

```
kubectl rollout undo deployment my-deployment --to-revision=2
```

5. 查看更新歷史：

```
kubectl rollout history deployment my-deployment
```

6. 暫停和恢復更新：

```
kubectl rollout pause deployment my-deployment
kubectl rollout resume deployment my-deployment
```

#### 使用 kubectl 插件

kubectl 支持插件系統，您可以安裝各種插件來擴展其功能。

**安裝 krew 插件管理器**：

Krew 是 kubectl 的插件管理器，類似於 apt 或 brew。

1. 安裝 krew：

```
(
  set -x; cd "$(mktemp -d)" &&
  OS="$(uname | tr '[:upper:]' '[:lower:]')" &&
  ARCH="$(uname -m | sed -e 's/x86_64/amd64/' -e 's/\(arm\)\(64\)\?.*/\1\2/' -e 's/aarch64$/arm64/')" &&
  KREW="krew-${OS}_${ARCH}" &&
  curl -fsSLO "https://github.com/kubernetes-sigs/krew/releases/latest/download/${KREW}.tar.gz" &&
  tar zxvf "${KREW}.tar.gz" &&
  ./"${KREW}" install krew
)
```

2. 將 krew 添加到您的 PATH：

```
export PATH="${KREW_ROOT:-$HOME/.krew}/bin:$PATH"
```

**安裝和使用插件**：

1. 查看可用插件：

```
kubectl krew search
```

2. 安裝插件：

```
kubectl krew install ctx
kubectl krew install ns
```

3. 使用插件：

```
kubectl ctx  # 切換集群上下文
kubectl ns   # 切換命名空間
```

#### 進階範例：使用 kubectl 部署多層應用程序

讓我們通過一個更複雜的例子來學習如何使用 kubectl 部署一個包含前端、後端和數據庫的多層應用程序：

**1. 創建命名空間**

首先，我們將創建一個專用的命名空間來部署我們的應用程序：

```
kubectl create namespace multi-tier-app
```

**2. 部署數據庫**

創建一個 MySQL 數據庫的 Deployment 和 Service：

```
kubectl apply -f - <<EOF
apiVersion: v1
kind: Secret
metadata:
  name: mysql-secret
  namespace: multi-tier-app
type: Opaque
data:
  password: cGFzc3dvcmQ=  # "password" 的 base64 編碼
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
  namespace: multi-tier-app
spec:
  selector:
    matchLabels:
      app: mysql
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - image: mysql:5.7
        name: mysql
        env:
        - name: MYSQL_ROOT_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: password
        ports:
        - containerPort: 3306
          name: mysql
        volumeMounts:
        - name: mysql-persistent-storage
          mountPath: /var/lib/mysql
      volumes:
      - name: mysql-persistent-storage
        emptyDir: {}
---
apiVersion: v1
kind: Service
metadata:
  name: mysql
  namespace: multi-tier-app
spec:
  ports:
  - port: 3306
  selector:
    app: mysql
  clusterIP: None
EOF
```

**3. 部署後端 API**

創建一個後端 API 的 Deployment 和 Service：

```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: backend-api
  namespace: multi-tier-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: backend-api
  template:
    metadata:
      labels:
        app: backend-api
    spec:
      containers:
      - name: backend-api
        image: my-backend-api:v1
        ports:
        - containerPort: 8080
        env:
        - name: DB_HOST
          value: mysql
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: password
---
apiVersion: v1
kind: Service
metadata:
  name: backend-api
  namespace: multi-tier-app
spec:
  ports:
  - port: 80
    targetPort: 8080
  selector:
    app: backend-api
EOF
```

**4. 部署前端**

創建一個前端應用程序的 Deployment 和 Service：

```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
  namespace: multi-tier-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: frontend
  template:
    metadata:
      labels:
        app: frontend
    spec:
      containers:
      - name: frontend
        image: my-frontend:v1
        ports:
        - containerPort: 80
        env:
        - name: API_URL
          value: http://backend-api
---
apiVersion: v1
kind: Service
metadata:
  name: frontend
  namespace: multi-tier-app
spec:
  type: LoadBalancer
  ports:
  - port: 80
  selector:
    app: frontend
EOF
```

**5. 檢查部署狀態**

```
kubectl get all -n multi-tier-app
```

**6. 查看前端服務的外部 IP**

```
kubectl get service frontend -n multi-tier-app
```

**7. 擴展後端 API**

如果需要處理更多請求，我們可以擴展後端 API：

```
kubectl scale deployment backend-api -n multi-tier-app --replicas=5
```

**8. 更新前端映像**

如果我們有一個新版本的前端應用程序，我們可以更新它：

```
kubectl set image deployment/frontend frontend=my-frontend:v2 -n multi-tier-app
```

**9. 查看更新狀態**

```
kubectl rollout status deployment/frontend -n multi-tier-app
```

**10. 如果新版本有問題，回滾更新**

```
kubectl rollout undo deployment/frontend -n multi-tier-app
```

通過這個例子，您可以看到如何使用 kubectl 來部署和管理一個完整的多層應用程序，包括數據庫、後端 API 和前端。這種方法可以應用於各種類型的應用程序，從簡單的網站到複雜的微服務架構。

### deployment.yml 進階設定

在進階級別，我們可以使用 Deployment 的更多功能，如資源限制、健康檢查、更新策略等。

**進階 deployment.yml 範例**:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: advanced-app
  labels:
    app: advanced-app
spec:
  replicas: 3
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 0
  selector:
    matchLabels:
      app: advanced-app
  template:
    metadata:
      labels:
        app: advanced-app
        version: v2
    spec:
      containers:
      - name: 進階容器
        image: myapp:2.0
        ports:
        - containerPort: 8080
        resources:
          requests:
            cpu: "100m"
            memory: "128Mi"
          limits:
            cpu: "500m"
            memory: "256Mi"
        readinessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 5
          periodSeconds: 10
        livenessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 15
          periodSeconds: 20
        env:
        - name: DB_URL
          valueFrom:
            configMapKeyRef:
              name: 進階配置
              key: database.url
```

**進階功能解釋**:

1. **更新策略 (strategy)**:
    - **type: RollingUpdate**: 使用滾動更新策略，逐步替換舊的 Pod。
    - **maxSurge**: 允許超出所需 Pod 數量的最大數量或百分比。
    - **maxUnavailable**: 更新過程中允許不可用的 Pod 的最大數量或百分比。

2. **資源管理 (resources)**:
    - **requests**: 容器需要的最小資源量。
    - **limits**: 容器可以使用的最大資源量。

3. **健康檢查**:
    - **readinessProbe**: 檢查容器是否準備好接收流量。
    - **livenessProbe**: 檢查容器是否仍在運行，如果失敗，Kubernetes 會重啟容器。

4. **環境變量**:
    - 從 ConfigMap 獲取環境變量值。

### configmap.yml 進階設定

在進階級別，我們可以使用 ConfigMap 的更多功能，如二進制數據、多種配置文件等。

**進階 configmap.yml 範例**:

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: 進階配置
data:
  database.url: "jdbc:mysql://db-service:3306/mydb"
  database.user: "user"
  application.properties: |
    server.port=8080
    logging.level.root=INFO
    feature.x.enabled=true
    feature.y.enabled=false
  logback.xml: |
    <configuration>
      <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
          <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
      </appender>
      <root level="info">
        <appender-ref ref="STDOUT" />
      </root>
    </configuration>
```

**進階功能解釋**:

1. **多種配置文件**:
    - 在一個 ConfigMap 中存儲多個配置文件，如 application.properties 和 logback.xml。

2. **多行文本**:
    - 使用 `|` 符號表示多行文本，保留換行符。

3. **不同類型的配置**:
    - 簡單的鍵值對（如 database.url）。
    - 結構化配置文件（如 application.properties）。
    - XML 配置文件（如 logback.xml）。

### virtualservice.yml 進階設定

在進階級別，我們可以使用 VirtualService 的更多功能，如流量分割、超時、重試等。

**進階 virtualservice.yml 範例**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: 進階服務
spec:
  hosts:
  - 進階服務
  http:
  - match:
    - headers:
        user-agent:
          regex: ".*Chrome.*"
    route:
    - destination:
        host: 進階服務
        subset: v2
      weight: 90
    - destination:
        host: 進階服務
        subset: v1
      weight: 10
    timeout: 5s
    retries:
      attempts: 3
      perTryTimeout: 2s
  - route:
    - destination:
        host: 進階服務
        subset: v1
```

**進階功能解釋**:

1. **基於條件的路由**:
    - **match**: 根據請求的特定條件（如標頭、URL）決定路由規則。

2. **流量分割**:
    - **weight**: 將流量按比例分配到不同版本的服務（如 90% 到 v2，10% 到 v1）。

3. **超時和重試**:
    - **timeout**: 請求的超時時間。
    - **retries**: 重試策略，包括重試次數和每次重試的超時時間。

4. **多個路由規則**:
    - 可以定義多個路由規則，按順序匹配。

### destination.yml 進階設定

在進階級別，我們可以使用 DestinationRule 的更多功能，如負載均衡、連接池、異常檢測等。

**進階 destination.yml 範例**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: 進階目標規則
spec:
  host: 進階服務
  trafficPolicy:
    loadBalancer:
      simple: LEAST_CONN
    connectionPool:
      tcp:
        maxConnections: 100
      http:
        http1MaxPendingRequests: 10
    outlierDetection:
      consecutiveErrors: 5
      interval: 30s
      baseEjectionTime: 60s
  subsets:
  - name: v1
    labels:
      version: v1
  - name: v2
    labels:
      version: v2
    trafficPolicy:
      loadBalancer:
        simple: ROUND_ROBIN
```

**進階功能解釋**:

1. **負載均衡**:
    - **loadBalancer.simple**: 負載均衡策略，如 ROUND_ROBIN（輪詢）、LEAST_CONN（最少連接）、RANDOM（隨機）。

2. **連接池**:
    - **connectionPool.tcp.maxConnections**: 到目標的最大 TCP 連接數。
    - **connectionPool.http.http1MaxPendingRequests**: 等待連接池分配的最大 HTTP 請求數。

3. **異常檢測**:
    - **outlierDetection.consecutiveErrors**: 將實例從負載均衡池中移除前的連續錯誤數。
    - **outlierDetection.interval**: 分析實例的時間間隔。
    - **outlierDetection.baseEjectionTime**: 實例被移除的最小時間。

4. **子集特定策略**:
    - 可以為每個子集定義特定的流量策略。

### 進階範例

下面是一個進階範例，展示如何使用這四種配置文件部署一個具有藍綠部署功能的應用程序：

**1. deployment.yml (v1)**:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-v1
spec:
  replicas: 2
  selector:
    matchLabels:
      app: myapp
      version: v1
  template:
    metadata:
      labels:
        app: myapp
        version: v1
    spec:
      containers:
      - name: 我的容器
        image: myapp:1.0
        ports:
        - containerPort: 8080
        resources:
          requests:
            cpu: "100m"
            memory: "128Mi"
          limits:
            cpu: "200m"
            memory: "256Mi"
        readinessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 5
          periodSeconds: 10
        env:
        - name: CONFIG_FILE
          value: "/config/config.properties"
        volumeMounts:
        - name: config-volume
          mountPath: /config
      volumes:
      - name: config-volume
        configMap:
          name: 我的應用-config
```

**2. deployment.yml (v2)**:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-v2
spec:
  replicas: 2
  selector:
    matchLabels:
      app: myapp
      version: v2
  template:
    metadata:
      labels:
        app: myapp
        version: v2
    spec:
      containers:
      - name: 我的容器
        image: myapp:2.0
        ports:
        - containerPort: 8080
        resources:
          requests:
            cpu: "200m"
            memory: "256Mi"
          limits:
            cpu: "400m"
            memory: "512Mi"
        readinessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 5
          periodSeconds: 10
        env:
        - name: CONFIG_FILE
          value: "/config/config.properties"
        volumeMounts:
        - name: config-volume
          mountPath: /config
      volumes:
      - name: config-volume
        configMap:
          name: 我的應用-config
```

**3. configmap.yml**:

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: 我的應用-config
data:
  config.properties: |
    server.port=8080
    database.url=jdbc:mysql://mysql-service:3306/mydb
    database.user=user
    database.password=password
    feature.new=false
    logging.level=INFO
```

**4. virtualservice.yml**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: myapp-service
spec:
  hosts:
  - "*"
  gateways:
  - myapp-gateway
  http:
  - match:
    - uri:
        prefix: /v1
    route:
    - destination:
        host: myapp-service
        subset: v1
  - match:
    - uri:
        prefix: /v2
    route:
    - destination:
        host: myapp-service
        subset: v2
  - route:
    - destination:
        host: myapp-service
        subset: v1
      weight: 90
    - destination:
        host: myapp-service
        subset: v2
      weight: 10
```

**5. destination.yml**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: myapp-destination
spec:
  host: myapp-service
  trafficPolicy:
    loadBalancer:
      simple: ROUND_ROBIN
  subsets:
  - name: v1
    labels:
      version: v1
  - name: v2
    labels:
      version: v2
```

**這個進階範例的效果**:

1. 兩個 **Deployment** 分別部署了應用程序的 v1 和 v2 版本。
2. **ConfigMap** 提供了應用程序的配置數據。
3. **VirtualService** 根據 URL 路徑將流量路由到不同版本，並為沒有特定路徑的請求設置了權重分配。
4. **DestinationRule** 定義了服務的 v1 和 v2 子集，並設置了負載均衡策略。

## 高級教學

本節適合已經熟悉 Kubernetes 進階功能的學習者。我們將探討更複雜的配置和高級功能。

### kubectl 高級教學

在掌握了 kubectl 的基本和進階功能後，我們可以學習一些高級技巧，這些技巧對於故障排除、效能優化和管理大型 Kubernetes 集群非常有用。

#### 高級故障排除技巧

當您在 Kubernetes 集群中遇到複雜問題時，以下技巧可以幫助您更有效地進行故障排除：

**1. 使用 kubectl 調試暫時性 Pod**

有時，您需要在集群中創建一個臨時的 Pod 來調試網絡或其他問題：

```
kubectl run debug-pod --rm -it --image=nicolaka/netshoot -- bash
```

這個命令會創建一個包含各種網絡調試工具的 Pod，並在您退出 shell 時自動刪除它。

**2. 使用 kubectl debug 命令**

Kubernetes 1.18+ 版本引入了 `kubectl debug` 命令，它可以幫助您調試運行中的 Pod：

```
kubectl debug mypod -it --image=busybox --target=mypod
```

這個命令會創建一個新的容器，它與目標 Pod 共享相同的命名空間，讓您可以檢查 Pod 的環境。

**3. 使用 kubectl events 查看集群事件**

集群事件可以提供有關問題的重要線索：

```
kubectl get events --sort-by='.lastTimestamp'
```

這個命令會按時間順序顯示集群中的所有事件。

**4. 檢查 Pod 的狀態和條件**

```
kubectl get pod mypod -o jsonpath='{.status.conditions}'
```

這個命令會顯示 Pod 的所有條件，如 Ready、PodScheduled 等，幫助您了解 Pod 的當前狀態。

**5. 檢查 Pod 的資源使用情況**

如果您安裝了 Metrics Server，可以使用以下命令查看 Pod 的 CPU 和內存使用情況：

```
kubectl top pod
```

**6. 使用 kubectl explain 了解資源定義**

```
kubectl explain deployment.spec.strategy
```

這個命令會顯示 Deployment 的 strategy 字段的詳細說明，幫助您了解如何正確配置資源。

#### 效能優化技巧

以下技巧可以幫助您優化 Kubernetes 集群和應用程序的效能：

**1. 使用 kubectl 設置資源請求和限制**

為 Pod 設置適當的資源請求和限制可以提高集群的效能和穩定性：

```
kubectl set resources deployment myapp --requests=cpu=200m,memory=512Mi --limits=cpu=500m,memory=1Gi
```

**2. 使用 kubectl 進行水平自動擴展**

```
kubectl autoscale deployment myapp --min=2 --max=10 --cpu-percent=80
```

這個命令會創建一個 HorizontalPodAutoscaler，當 CPU 使用率超過 80% 時自動擴展 Deployment。

**3. 使用 kubectl 進行垂直自動擴展**

如果您安裝了 Vertical Pod Autoscaler (VPA)，可以使用以下命令創建一個 VPA 資源：

```
kubectl apply -f - <<EOF
apiVersion: autoscaling.k8s.io/v1
kind: VerticalPodAutoscaler
metadata:
  name: myapp-vpa
spec:
  targetRef:
    apiVersion: "apps/v1"
    kind: Deployment
    name: myapp
  updatePolicy:
    updateMode: "Auto"
EOF
```

VPA 會自動調整 Pod 的資源請求，以優化資源使用。

**4. 使用 kubectl 進行節點親和性設置**

您可以使用節點親和性將 Pod 調度到特定的節點上，以優化效能：

```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
spec:
  template:
    spec:
      affinity:
        nodeAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            nodeSelectorTerms:
            - matchExpressions:
              - key: kubernetes.io/e2e-az-name
                operator: In
                values:
                - e2e-az1
                - e2e-az2
EOF
```

**5. 使用 kubectl 進行 Pod 拓撲分佈約束**

您可以使用 Pod 拓撲分佈約束來確保 Pod 在不同的節點、可用區或區域中均勻分佈：

```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
spec:
  template:
    spec:
      topologySpreadConstraints:
      - maxSkew: 1
        topologyKey: kubernetes.io/hostname
        whenUnsatisfiable: DoNotSchedule
        labelSelector:
          matchLabels:
            app: myapp
EOF
```

#### 高級集群管理技巧

以下技巧可以幫助您更有效地管理 Kubernetes 集群：

**1. 使用 kubectl 進行多集群管理**

您可以使用 kubectl 的上下文功能來管理多個集群：

```
# 列出所有上下文
kubectl config get-contexts

# 切換上下文
kubectl config use-context my-cluster

# 在特定上下文中執行命令
kubectl --context=my-cluster get pods
```

**2. 使用 kubectl 進行 RBAC 管理**

您可以使用 kubectl 來管理角色和角色綁定，控制用戶對集群資源的訪問權限：

```
# 創建一個角色
kubectl create role pod-reader --verb=get,list,watch --resource=pods

# 創建一個角色綁定
kubectl create rolebinding bob-pod-reader --role=pod-reader --user=bob

# 檢查用戶權限
kubectl auth can-i list pods --as=bob
```

**3. 使用 kubectl 進行資源配額管理**

您可以使用 ResourceQuota 來限制命名空間中的資源使用：

```
kubectl apply -f - <<EOF
apiVersion: v1
kind: ResourceQuota
metadata:
  name: compute-quota
  namespace: my-namespace
spec:
  hard:
    pods: "10"
    requests.cpu: "4"
    requests.memory: 8Gi
    limits.cpu: "8"
    limits.memory: 16Gi
EOF
```

**4. 使用 kubectl 進行網絡策略管理**

您可以使用 NetworkPolicy 來控制 Pod 之間的網絡流量：

```
kubectl apply -f - <<EOF
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-frontend-to-backend
  namespace: my-namespace
spec:
  podSelector:
    matchLabels:
      app: backend
  ingress:
  - from:
    - podSelector:
        matchLabels:
          app: frontend
    ports:
    - protocol: TCP
      port: 80
EOF
```

**5. 使用 kubectl 進行集群升級**

在升級集群之前，您可以使用 kubectl 來檢查集群的狀態：

```
# 檢查節點版本
kubectl get nodes -o wide

# 檢查 Pod 的就緒狀態
kubectl get pods --all-namespaces -o wide

# 驅逐節點上的 Pod
kubectl drain node-1 --ignore-daemonsets
```

#### 高級 kubectl 命令和技巧

**1. 使用 kubectl 進行 JSON 路徑查詢**

您可以使用 JSONPath 表達式從 kubectl 輸出中提取特定信息：

```
# 獲取所有節點的 IP 地址
kubectl get nodes -o jsonpath='{.items[*].status.addresses[?(@.type=="InternalIP")].address}'

# 獲取所有 Pod 的容器映像
kubectl get pods -o jsonpath='{.items[*].spec.containers[*].image}'

# 獲取所有 Pod 的名稱和狀態
kubectl get pods -o jsonpath='{range .items[*]}{.metadata.name}{"\t"}{.status.phase}{"\n"}{end}'
```

**2. 使用 kubectl 進行自定義列輸出**

您可以使用 custom-columns 選項來自定義 kubectl 輸出的列：

```
kubectl get pods -o custom-columns=NAME:.metadata.name,STATUS:.status.phase,NODE:.spec.nodeName
```

**3. 使用 kubectl 進行排序**

您可以使用 --sort-by 選項來對 kubectl 輸出進行排序：

```
kubectl get pods --sort-by=.metadata.creationTimestamp
```

**4. 使用 kubectl 進行過濾**

您可以使用 --field-selector 選項來過濾 kubectl 輸出：

```
kubectl get pods --field-selector=status.phase=Running
```

**5. 使用 kubectl 進行批量操作**

您可以使用 kubectl 的標籤選擇器來對多個資源進行批量操作：

```
# 刪除所有標籤為 app=old-app 的 Pod
kubectl delete pods -l app=old-app

# 重啟所有 Deployment
kubectl rollout restart deployment
```

#### 高級範例：使用 kubectl 進行藍綠部署和金絲雀發布

讓我們通過一個高級例子來學習如何使用 kubectl 進行藍綠部署和金絲雀發布：

**藍綠部署**

藍綠部署是一種將新版本應用程序部署到生產環境的策略，它通過創建兩個相同但版本不同的環境（藍色和綠色）來實現零停機時間的部署。

**1. 部署藍色版本（當前版本）**

```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-blue
  labels:
    app: myapp
    version: blue
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
      version: blue
  template:
    metadata:
      labels:
        app: myapp
        version: blue
    spec:
      containers:
      - name: myapp
        image: myapp:v1
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: myapp
spec:
  selector:
    app: myapp
    version: blue
  ports:
  - port: 80
    targetPort: 80
EOF
```

**2. 部署綠色版本（新版本）**

```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-green
  labels:
    app: myapp
    version: green
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
      version: green
  template:
    metadata:
      labels:
        app: myapp
        version: green
    spec:
      containers:
      - name: myapp
        image: myapp:v2
        ports:
        - containerPort: 80
EOF
```

**3. 測試綠色版本**

您可以創建一個臨時服務來測試綠色版本：

```
kubectl apply -f - <<EOF
apiVersion: v1
kind: Service
metadata:
  name: myapp-green
spec:
  selector:
    app: myapp
    version: green
  ports:
  - port: 80
    targetPort: 80
EOF
```

**4. 切換流量到綠色版本**

一旦您確認綠色版本正常工作，您可以更新主服務以指向綠色版本：

```
kubectl patch service myapp -p '{"spec":{"selector":{"version":"green"}}}'
```

**5. 刪除藍色版本**

當您確認綠色版本穩定後，可以刪除藍色版本：

```
kubectl delete deployment myapp-blue
```

**金絲雀發布**

金絲雀發布是一種將新版本應用程序逐步部署到生產環境的策略，它通過將少量流量路由到新版本來測試其穩定性。

**1. 部署當前版本**

```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-v1
  labels:
    app: myapp
    version: v1
spec:
  replicas: 9
  selector:
    matchLabels:
      app: myapp
      version: v1
  template:
    metadata:
      labels:
        app: myapp
        version: v1
    spec:
      containers:
      - name: myapp
        image: myapp:v1
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: myapp
spec:
  selector:
    app: myapp
  ports:
  - port: 80
    targetPort: 80
EOF
```

**2. 部署新版本（金絲雀）**

```
kubectl apply -f - <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-v2
  labels:
    app: myapp
    version: v2
spec:
  replicas: 1
  selector:
    matchLabels:
      app: myapp
      version: v2
  template:
    metadata:
      labels:
        app: myapp
        version: v2
    spec:
      containers:
      - name: myapp
        image: myapp:v2
        ports:
        - containerPort: 80
EOF
```

**3. 監控新版本的效能和穩定性**

您可以使用各種工具來監控新版本的效能和穩定性，如 Prometheus、Grafana 等。

**4. 逐步增加新版本的流量**

如果新版本表現良好，您可以逐步增加其副本數量，同時減少舊版本的副本數量：

```
kubectl scale deployment myapp-v1 --replicas=8
kubectl scale deployment myapp-v2 --replicas=2
```

繼續這個過程，直到完全遷移到新版本：

```
kubectl scale deployment myapp-v1 --replicas=5
kubectl scale deployment myapp-v2 --replicas=5

kubectl scale deployment myapp-v1 --replicas=0
kubectl scale deployment myapp-v2 --replicas=10
```

**5. 刪除舊版本**

當您確認新版本穩定後，可以刪除舊版本：

```
kubectl delete deployment myapp-v1
```

通過這些高級例子，您可以看到如何使用 kubectl 來實現複雜的部署策略，如藍綠部署和金絲雀發布。這些策略可以幫助您在不影響用戶體驗的情況下安全地部署新版本的應用程序。

### deployment.yml 高級設定

在高級級別，我們可以使用 Deployment 的更多高級功能，如初始化容器、節點選擇器、容器生命週期鉤子等。

**高級 deployment.yml 範例**:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: expert-app
  labels:
    app: expert-app
  annotations:
    kubernetes.io/change-cause: "Update to version 3.0 with new features"
spec:
  replicas: 5
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 2
      maxUnavailable: 1
  selector:
    matchLabels:
      app: expert-app
  template:
    metadata:
      labels:
        app: expert-app
        version: v3
      annotations:
        prometheus.io/scrape: "true"
        prometheus.io/port: "8080"
    spec:
      nodeSelector:
        disktype: ssd
        kubernetes.io/os: linux
      affinity:
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
          - weight: 100
            podAffinityTerm:
              labelSelector:
                matchExpressions:
                - key: app
                  operator: In
                  values:
                  - expert-app
              topologyKey: kubernetes.io/hostname
      initContainers:
      - name: init-db
        image: busybox:1.28
        command: ['sh', '-c', 'until nslookup db-service; do echo waiting for db; sleep 2; done;']
      containers:
      - name: expert-container
        image: myapp:3.0
        ports:
        - containerPort: 8080
        resources:
          requests:
            cpu: "500m"
            memory: "512Mi"
          limits:
            cpu: "1000m"
            memory: "1Gi"
        readinessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 10
          periodSeconds: 5
          timeoutSeconds: 2
          successThreshold: 1
          failureThreshold: 3
        livenessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 20
          periodSeconds: 15
          timeoutSeconds: 3
          failureThreshold: 3
        startupProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
          failureThreshold: 30
        lifecycle:
          postStart:
            exec:
              command: ["/bin/sh", "-c", "echo 'Application started' > /tmp/started"]
          preStop:
            exec:
              command: ["/bin/sh", "-c", "echo 'Application stopping' > /tmp/stopping; sleep 10"]
        env:
        - name: DB_URL
          valueFrom:
            configMapKeyRef:
              name: expert-config
              key: database.url
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: db-secrets
              key: password
        volumeMounts:
        - name: config-volume
          mountPath: /config
        - name: data-volume
          mountPath: /data
      volumes:
      - name: config-volume
        configMap:
          name: expert-config
      - name: data-volume
        persistentVolumeClaim:
          claimName: expert-pvc
```

**高級功能解釋**:

1. **註釋 (annotations)**:
    - 提供非識別性的元數據，如更改原因、監控配置等。

2. **節點選擇器 (nodeSelector)**:
    - 指定 Pod 應該運行在哪些節點上，如具有 SSD 磁盤的節點。

3. **親和性 (affinity)**:
    - **podAntiAffinity**: 避免將相同應用的 Pod 部署在同一節點上，提高可用性。

4. **初始化容器 (initContainers)**:
    - 在主容器啟動前運行的容器，用於初始化工作，如等待依賴服務可用。

5. **容器生命週期鉤子 (lifecycle)**:
    - **postStart**: 容器啟動後執行的命令。
    - **preStop**: 容器停止前執行的命令，如優雅關閉。

6. **啟動探針 (startupProbe)**:
    - 檢查應用程序是否已啟動，在啟動期間禁用其他探針。

7. **持久卷 (volumes)**:
    - 使用 PersistentVolumeClaim 提供持久存儲。

8. **環境變量**:
    - 從 ConfigMap 和 Secret 獲取環境變量值。

### configmap.yml 高級設定

在高級級別，我們可以使用 ConfigMap 的更多高級功能，如二進制數據、更新策略等。

**高級 configmap.yml 範例**:

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: expert-config
  labels:
    app: expert-app
  annotations:
    description: "Configuration for expert application"
data:
  database.url: "jdbc:mysql://db-service:3306/expertdb"
  database.user: "admin"
  cache.enabled: "true"
  cache.size: "1024"
  feature.flags: |
    feature.a=true
    feature.b=false
    feature.c=true
  application.yaml: |
    server:
      port: 8080
      compression:
        enabled: true
        mime-types: text/html,text/css,application/json
    logging:
      level:
        root: INFO
        com.example: DEBUG
    management:
      endpoints:
        web:
          exposure:
            include: health,metrics,info
  nginx.conf: |
    user nginx;
    worker_processes auto;
    error_log /var/log/nginx/error.log;
    pid /run/nginx.pid;

    events {
      worker_connections 1024;
    }

    http {
      include /etc/nginx/mime.types;
      default_type application/octet-stream;

      log_format main '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

      access_log /var/log/nginx/access.log main;

      sendfile on;
      tcp_nopush on;
      tcp_nodelay on;
      keepalive_timeout 65;
      types_hash_max_size 2048;

      include /etc/nginx/conf.d/*.conf;
    }
```

**高級功能解釋**:

1. **標籤和註釋**:
    - 使用標籤和註釋來組織和描述 ConfigMap。

2. **複雜的配置文件**:
    - 包含多種格式的配置文件，如 YAML、屬性文件和 Nginx 配置。

3. **結構化數據**:
    - 使用多層次的結構化配置數據。

4. **特性標誌**:
    - 使用配置來控制應用程序的特性開關。

### virtualservice.yml 高級設定

在高級級別，我們可以使用 VirtualService 的更多高級功能，如故障注入、請求鏡像、高級路由等。

**高級 virtualservice.yml 範例**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: expert-service
spec:
  hosts:
  - expert-service
  - expert.example.com
  gateways:
  - expert-gateway
  - mesh
  http:
  - match:
    - headers:
        user-agent:
          regex: ".*Mobile.*"
      uri:
        prefix: /api
    route:
    - destination:
        host: expert-service
        subset: mobile
        port:
          number: 8080
  - match:
    - headers:
        user-type:
          exact: "premium"
    route:
    - destination:
        host: expert-service
        subset: premium
    corsPolicy:
      allowOrigins:
      - exact: https://example.com
      allowMethods:
      - GET
      - POST
      - PUT
      allowCredentials: true
      maxAge: "24h"
  - match:
    - uri:
        prefix: /api/v2
    rewrite:
      uri: /api/v1
    route:
    - destination:
        host: expert-service
        subset: v1
    fault:
      delay:
        percentage:
          value: 5
        fixedDelay: 2s
      abort:
        percentage:
          value: 1
        httpStatus: 500
  - route:
    - destination:
        host: expert-service
        subset: v1
      weight: 80
    - destination:
        host: expert-service
        subset: v2
      weight: 20
    mirror:
      host: expert-service
      subset: v2
    mirrorPercentage:
      value: 100
    timeout: 10s
    retries:
      attempts: 3
      perTryTimeout: 2s
      retryOn: gateway-error,connect-failure,refused-stream
```

**高級功能解釋**:

1. **多主機和網關**:
    - 為多個主機和網關定義路由規則。

2. **基於用戶代理的路由**:
    - 根據用戶代理（如移動設備）將流量路由到特定服務。

3. **基於標頭的路由**:
    - 根據請求標頭（如用戶類型）將流量路由到特定服務。

4. **CORS 策略**:
    - 配置跨源資源共享策略。

5. **URI 重寫**:
    - 將請求的 URI 重寫為另一個 URI。

6. **故障注入**:
    - 注入延遲和錯誤，用於測試服務的彈性。

7. **流量鏡像**:
    - 將流量鏡像到另一個服務，用於測試新版本。

8. **高級重試策略**:
    - 配置更複雜的重試策略，包括重試條件。

### destination.yml 高級設定

在高級級別，我們可以使用 DestinationRule 的更多高級功能，如 TLS 設置、斷路器等。

**高級 destination.yml 範例**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: expert-destination
spec:
  host: expert-service
  trafficPolicy:
    loadBalancer:
      consistentHash:
        httpHeaderName: "x-user-id"
    connectionPool:
      tcp:
        maxConnections: 100
        connectTimeout: 30ms
        tcpKeepalive:
          time: 7200s
          interval: 75s
      http:
        http2MaxRequests: 1000
        maxRequestsPerConnection: 10
        maxRetries: 3
    outlierDetection:
      consecutiveErrors: 5
      interval: 5s
      baseEjectionTime: 30s
      maxEjectionPercent: 10
      minHealthPercent: 50
    portLevelSettings:
    - port:
        number: 8080
      tls:
        mode: MUTUAL
        clientCertificate: /etc/certs/client.pem
        privateKey: /etc/certs/key.pem
        caCertificates: /etc/certs/ca.pem
  subsets:
  - name: v1
    labels:
      version: v1
  - name: v2
    labels:
      version: v2
    trafficPolicy:
      loadBalancer:
        simple: LEAST_CONN
  - name: mobile
    labels:
      version: mobile
  - name: premium
    labels:
      version: premium
    trafficPolicy:
      connectionPool:
        tcp:
          maxConnections: 200
        http:
          http2MaxRequests: 2000
```

**高級功能解釋**:

1. **一致性哈希負載均衡**:
    - 基於 HTTP 標頭（如用戶 ID）將相同用戶的請求路由到相同的實例。

2. **高級連接池設置**:
    - 配置 TCP 和 HTTP 連接池的高級參數，如 TCP keepalive 和 HTTP/2 設置。

3. **高級異常檢測**:
    - 配置更複雜的異常檢測策略，包括最小健康百分比。

4. **端口級別設置**:
    - 為不同的端口配置不同的策略。

5. **TLS 設置**:
    - 配置雙向 TLS 認證。

6. **子集特定策略**:
    - 為不同的子集配置不同的流量策略，如為高級用戶提供更多資源。

### 高級範例

下面是一個高級範例，展示如何使用這四種配置文件部署一個具有金絲雀發布功能的微服務應用程序：

**1. deployment.yml (v3)**:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: microservice-v3
  labels:
    app: microservice
    version: v3
spec:
  replicas: 1
  selector:
    matchLabels:
      app: microservice
      version: v3
  template:
    metadata:
      labels:
        app: microservice
        version: v3
      annotations:
        prometheus.io/scrape: "true"
        prometheus.io/port: "8080"
    spec:
      containers:
      - name: microservice
        image: microservice:3.0
        ports:
        - containerPort: 8080
        resources:
          requests:
            cpu: "200m"
            memory: "256Mi"
          limits:
            cpu: "500m"
            memory: "512Mi"
        readinessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 10
          periodSeconds: 5
        env:
        - name: CONFIG_FILE
          value: "/config/config.properties"
        - name: VERSION
          value: "v3"
        volumeMounts:
        - name: config-volume
          mountPath: /config
      volumes:
      - name: config-volume
        configMap:
          name: microservice-config
```

**2. configmap.yml**:

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: microservice-config
data:
  config.properties: |
    server.port=8080
    database.url=jdbc:mysql://mysql-service:3306/microdb
    database.user=microuser
    feature.new=true
    logging.level=DEBUG
    metrics.enabled=true
  application.yaml: |
    server:
      compression:
        enabled: true
    management:
      endpoints:
        web:
          exposure:
            include: health,metrics,info
```

**3. virtualservice.yml**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: microservice
spec:
  hosts:
  - microservice
  - api.example.com
  gateways:
  - microservice-gateway
  - mesh
  http:
  - match:
    - headers:
        x-canary:
          exact: "true"
    route:
    - destination:
        host: microservice
        subset: v3
  - match:
    - uri:
        prefix: /api/v3
    route:
    - destination:
        host: microservice
        subset: v3
  - route:
    - destination:
        host: microservice
        subset: v1
      weight: 80
    - destination:
        host: microservice
        subset: v2
      weight: 15
    - destination:
        host: microservice
        subset: v3
      weight: 5
    mirror:
      host: microservice
      subset: v3
    mirrorPercentage:
      value: 10
```

**4. destination.yml**:

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: microservice
spec:
  host: microservice
  trafficPolicy:
    loadBalancer:
      simple: ROUND_ROBIN
    connectionPool:
      tcp:
        maxConnections: 100
      http:
        http1MaxPendingRequests: 10
    outlierDetection:
      consecutiveErrors: 3
      interval: 10s
      baseEjectionTime: 30s
  subsets:
  - name: v1
    labels:
      version: v1
  - name: v2
    labels:
      version: v2
  - name: v3
    labels:
      version: v3
    trafficPolicy:
      loadBalancer:
        simple: LEAST_CONN
```

**這個高級範例的效果**:

1. **金絲雀發布**: 使用 VirtualService 將少量流量（5%）路由到新版本（v3），同時保持大部分流量在穩定版本（v1 和 v2）。
2. **特定用戶測試**: 帶有特定標頭（x-canary: true）的請求會被路由到新版本，允許特定用戶測試新功能。
3. **流量鏡像**: 將 10% 的流量鏡像到新版本，用於監控新版本的性能和穩定性，而不影響用戶體驗。
4. **高級負載均衡**: 為不同版本使用不同的負載均衡策略。
5. **異常檢測**: 配置異常檢測，自動將不健康的實例從負載均衡池中移除。

## 常用指令

以下是 10 個常用的 kubectl 命令及其參數和效果，這些命令可以幫助您管理 Kubernetes 集群和應用程序：

### 1. kubectl get

**用途**: 顯示資源的信息

**常用參數**:

- `pods` (或 `po`): 顯示所有 Pod
- `deployments` (或 `deploy`): 顯示所有 Deployment
- `services` (或 `svc`): 顯示所有 Service
- `nodes` (或 `no`): 顯示所有節點
- `-o wide`: 顯示更多信息
- `-n <namespace>`: 指定命名空間
- `--all-namespaces` (或 `-A`): 顯示所有命名空間的資源
- `-l key=value`: 使用標籤選擇器過濾資源

**範例**:

```bash
kubectl get pods
kubectl get deployments -n default
kubectl get services --all-namespaces
kubectl get pods -o wide
kubectl get pods -l app=myapp
```

**效果**: 顯示指定類型資源的列表，包括名稱、狀態、年齡等信息。使用 `-o wide` 可以看到更多詳細信息，如 Pod 的 IP 地址和所在節點。

### 2. kubectl describe

**用途**: 顯示資源的詳細信息

**常用參數**:

- `pod <pod-name>`: 顯示指定 Pod 的詳細信息
- `deployment <deployment-name>`: 顯示指定 Deployment 的詳細信息
- `service <service-name>`: 顯示指定 Service 的詳細信息
- `-n <namespace>`: 指定命名空間

**範例**:

```bash
kubectl describe pod myapp-pod
kubectl describe deployment myapp -n default
kubectl describe service myapp-service
```

**效果**: 顯示指定資源的詳細信息，包括事件、狀態、配置等。這對於排查問題非常有用，特別是當 Pod 無法啟動或服務無法訪問時。

### 3. kubectl logs

**用途**: 顯示 Pod 中容器的日誌

**常用參數**:

- `<pod-name>`: 指定 Pod 的名稱
- `-c <container-name>`: 指定容器的名稱（當 Pod 中有多個容器時）
- `-f`: 持續顯示日誌（類似於 `tail -f`）
- `--tail=<number>`: 只顯示最後 n 行日誌
- `-p`: 顯示之前容器的日誌（如果容器已重啟）
- `-n <namespace>`: 指定命名空間

**範例**:

```bash
kubectl logs myapp-pod
kubectl logs myapp-pod -c myapp-container
kubectl logs -f myapp-pod
kubectl logs --tail=100 myapp-pod
```

**效果**: 顯示指定 Pod 中容器的日誌輸出。這對於調試應用程序問題非常有用，可以查看應用程序的錯誤信息和運行狀態。

### 4. kubectl apply

**用途**: 從文件創建或更新資源

**常用參數**:

- `-f <filename>`: 指定包含資源定義的文件
- `-f <directory>`: 指定包含資源定義文件的目錄
- `--record`: 在資源的註釋中記錄命令
- `-n <namespace>`: 指定命名空間
- `--dry-run=client`: 不實際執行操作，只顯示將要執行的操作

**範例**:

```bash
kubectl apply -f deployment.yml
kubectl apply -f configmap.yml -f service.yml
kubectl apply -f k8s-manifests/
kubectl apply -f deployment.yml --record
```

**效果**: 根據文件中的定義創建或更新資源。這是管理 Kubernetes 資源的推薦方式，因為它可以處理資源的創建和更新，並保留用戶未明確更改的設置。

### 5. kubectl delete

**用途**: 刪除資源

**常用參數**:

- `pod <pod-name>`: 刪除指定的 Pod
- `deployment <deployment-name>`: 刪除指定的 Deployment
- `service <service-name>`: 刪除指定的 Service
- `-f <filename>`: 刪除文件中定義的資源
- `-l key=value`: 使用標籤選擇器刪除資源
- `--all`: 刪除指定類型的所有資源
- `-n <namespace>`: 指定命名空間
- `--force --grace-period=0`: 強制立即刪除資源（謹慎使用）

**範例**:

```bash
kubectl delete pod myapp-pod
kubectl delete deployment myapp
kubectl delete -f deployment.yml
kubectl delete pods -l app=myapp
kubectl delete pods --all -n default
```

**效果**: 從集群中刪除指定的資源。默認情況下，Kubernetes 會嘗試優雅地終止資源，給應用程序時間進行清理工作。

### 6. kubectl exec

**用途**: 在 Pod 中的容器內執行命令

**常用參數**:

- `<pod-name>`: 指定 Pod 的名稱
- `-c <container-name>`: 指定容器的名稱（當 Pod 中有多個容器時）
- `-it`: 分配一個交互式終端
- `-n <namespace>`: 指定命名空間
- `-- <command>`: 要執行的命令

**範例**:

```bash
kubectl exec myapp-pod -- ls /app
kubectl exec -it myapp-pod -- /bin/bash
kubectl exec -it myapp-pod -c myapp-container -- /bin/sh
```

**效果**: 在指定 Pod 的容器中執行命令。這對於調試容器內部的問題、檢查文件系統或執行臨時操作非常有用。

### 7. kubectl port-forward

**用途**: 將本地端口轉發到 Pod 中的端口

**常用參數**:

- `pod/<pod-name>`: 指定 Pod 的名稱
- `service/<service-name>`: 指定 Service 的名稱
- `deployment/<deployment-name>`: 指定 Deployment 的名稱
- `<local-port>:<pod-port>`: 指定本地端口和 Pod 端口的映射
- `-n <namespace>`: 指定命名空間

**範例**:

```bash
kubectl port-forward pod/myapp-pod 8080:80
kubectl port-forward service/myapp-service 8080:80
kubectl port-forward deployment/myapp 8080:80
```

**效果**: 將本地機器的端口轉發到 Pod 中的端口。這對於直接訪問集群中的服務進行測試和調試非常有用，無需設置 Ingress 或 LoadBalancer。

### 8. kubectl scale

**用途**: 擴展 Deployment、ReplicaSet 或 StatefulSet 的副本數

**常用參數**:

- `deployment <deployment-name>`: 指定 Deployment 的名稱
- `--replicas=<number>`: 指定副本數量
- `-n <namespace>`: 指定命名空間

**範例**:

```bash
kubectl scale deployment myapp --replicas=5
kubectl scale deployment myapp --replicas=3 -n production
```

**效果**: 調整指定資源的副本數量。這對於根據負載增加或減少應用程序的實例數量非常有用，可以實現手動擴展。

### 9. kubectl rollout

**用途**: 管理資源的滾動更新

**常用參數**:

- `status deployment/<deployment-name>`: 查看 Deployment 的滾動更新狀態
- `history deployment/<deployment-name>`: 查看 Deployment 的更新歷史
- `undo deployment/<deployment-name>`: 回滾到上一個版本
- `undo deployment/<deployment-name> --to-revision=<revision>`: 回滾到指定版本
- `restart deployment/<deployment-name>`: 重啟 Deployment
- `-n <namespace>`: 指定命名空間

**範例**:

```bash
kubectl rollout status deployment/myapp
kubectl rollout history deployment/myapp
kubectl rollout undo deployment/myapp
kubectl rollout undo deployment/myapp --to-revision=2
kubectl rollout restart deployment/myapp
```

**效果**: 管理資源的滾動更新過程。可以查看更新狀態、歷史記錄，以及在出現問題時回滾到之前的版本。這對於確保應用程序的可靠部署和快速恢復非常重要。

### 10. kubectl config

**用途**: 管理 kubeconfig 文件

**常用參數**:

- `view`: 顯示當前的 kubeconfig
- `get-contexts`: 顯示可用的上下文
- `use-context <context-name>`: 切換到指定的上下文
- `set-context <context-name>`: 設置上下文
- `set-cluster <cluster-name>`: 設置集群
- `set-credentials <user-name>`: 設置用戶憑證

**範例**:

```bash
kubectl config view
kubectl config get-contexts
kubectl config use-context minikube
kubectl config set-context --current --namespace=default
```

**效果**: 管理 kubeconfig 文件，這是連接到 Kubernetes 集群的配置文件。可以查看和修改集群、用戶和上下文信息，以及切換當前使用的上下文。這對於在多個集群之間切換或更改當前命名空間非常有用。

## 總結

在本教學中，我們學習了 Kubernetes 的基本概念和四種重要的配置文件：deployment.yml、configmap.yml、virtualservice.yml 和 destination.yml。我們從入門級開始，逐步深入到進階和高級設定，並提供了各種實用的範例。

通過 deployment.yml，我們可以部署和管理應用程序，確保它們始終運行並可以輕鬆更新。通過 configmap.yml，我們可以管理應用程序的配置數據，使配置與代碼分離。通過 virtualservice.yml 和
destination.yml，我們可以控制流量路由和負載均衡，實現高級的流量管理功能。

我們還學習了 10 個常用的 kubectl 命令，這些命令可以幫助我們管理 Kubernetes 集群和應用程序。從基本的資源查詢到高級的滾動更新和配置管理，這些命令涵蓋了日常操作的各個方面。

Kubernetes 是一個強大而靈活的平台，可以幫助我們自動化部署、擴展和管理容器化應用程序。通過本教學，您已經掌握了使用 Kubernetes 的基本知識和技能，可以開始在實際環境中應用這些知識。

隨著您的經驗增長，您可以探索更多高級功能，如自定義資源定義（CRD）、Operator、Helm 等，進一步提高您的 Kubernetes 技能。
