# k9s 教學文件（由淺入深）

## 📦 一、k9s 安裝教學（初級）

### 前置需求
- 已安裝並設定 `kubectl`
- 有可存取的 Kubernetes 叢集

### macOS 安裝
```bash
brew install k9s
```

### Linux 安裝
```bash
# Snap 安裝（Ubuntu）
sudo snap install k9s

# 手動安裝
wget https://github.com/derailed/k9s/releases/download/vX.Y.Z/k9s_Linux_x86_64.tar.gz
tar -xzf k9s_Linux_x86_64.tar.gz
sudo mv k9s /usr/local/bin/
```

### Windows 安裝
```powershell
choco install k9s
# 或
scoop install k9s
```

### 驗證安裝
```bash
k9s version
```

## 🧭 二、k9s 基本操作教學（中級）

### 畫面結構
- 上方：資源類型（如 Pod）
- 中間：資源列表（支援即時刷新）
- 下方：狀態與快捷鍵提示

### 快捷鍵操作總覽

| 功能               | 操作                           |
|--------------------|--------------------------------|
| 移動項目           | ↑ ↓                            |
| 搜尋               | `/` → 輸入關鍵字                |
| 清除搜尋           | `Esc`                          |
| 查看 Pod log       | `l`（小寫 L）                  |
| 切換容器 log       | `0`、`1` ...                   |
| 切換 Namespace     | `:ns <namespace>`              |
| 開啟容器 shell     | `s`                            |
| 查看 YAML          | `y`                            |
| 編輯 YAML          | `e`                            |
| 刪除資源           | `d`                            |
| 返回上層           | `Esc`                          |

### 資源切換指令（於主畫面輸入）
- Pods：`po`
- Deployments：`deploy`
- Services：`svc`
- ConfigMaps：`cm`
- Namespaces：`ns`

### 搜尋與過濾
```bash
/api             # 名稱包含 api 的資源
:filter app=web  # 根據 Label 過濾
:clear           # 清除 filter
```

## 🧠 三、k9s 高級功能教學（進階）

### RBAC 檢查
- 選中資源 → `Shift-r` 可檢查目前身份是否具有 view/edit/delete 權限

### Port Forward
- 選中 Pod → `Shift-f` → 輸入格式 `8080:80`
- 刪除轉送：選中後 `d`

### Plugin 設定
檔案：`~/.k9s/plugins.yaml`
```yaml
plugin:
  nginx-logs:
    shortCut: Ctrl-L
    description: Show nginx logs
    scopes: [po]
    command: kubectl
    args: [logs, -f, -c, nginx, $POD]
```

### 自訂主題（skin）
檔案：`~/.k9s/skin.yml`
```yaml
k9s:
  body:
    fgColor: white
    bgColor: black
    logoColor: cyan
```

### 進階視覺模式
| 模式       | 快捷鍵    |
|------------|-----------|
| X-Ray      | `x`       |
| Pulse      | `Shift-p` |
| Benchmark  | `b`       |

## 📚 四、Kubernetes 資源操作（指令彙整）

### Pods（po）
| 操作         | 指令或鍵        |
|--------------|-----------------|
| 查看 log     | `l`             |
| Shell        | `s`             |
| 重啟 Pod     | `r`             |
| 編輯         | `e`             |
| 刪除         | `d`             |

### Services（svc）
| 操作         | 指令或鍵        |
|--------------|-----------------|
| 查看 Endpoint| `Enter`         |
| 編輯         | `e`             |
| 刪除         | `d`             |

### Deployments（deploy）
| 操作             | 指令或鍵    |
|------------------|-------------|
| 查看 ReplicaSet  | `Enter`     |
| 編輯             | `e`         |

### ConfigMaps（cm） & Secrets（sec）
| 操作     | 指令或鍵    |
|----------|-------------|
| 查看     | `y`         |
| 編輯     | `e`         |
| 刪除     | `d`         |

### Namespace（ns）
| 操作         | 指令                  |
|--------------|-----------------------|
| 切換命名空間 | `:ns <namespace>`     |
| X-Ray 檢視   | `x`                   |

### CronJobs / Jobs（cj / job）
| 操作         | 指令或鍵        |
|--------------|-----------------|
| 查看 log     | `l`             |
| 查看歷史     | `Enter`         |

## 📁 五、其他命令模式（Command Mode）

| 指令           | 功能描述                           |
|----------------|------------------------------------|
| `:ns`          | 切換 Namespace                     |
| `:filter`      | 套用 Label 過濾條件                |
| `:clear`       | 清除過濾條件                       |
| `:ctx`         | 列出 Context 並切換                |
| `:q` / `:quit` | 離開 k9s                           |

## 🛠️ 六、常見問題與排錯（FAQ & Troubleshooting）

### 問：k9s 無法連線到叢集？
- 請確認 `kubectl` 能正常存取叢集（`kubectl get nodes`）。
- 檢查 kubeconfig 路徑與內容是否正確。
- 若有多個 context，請切換至正確 context。

### 問：畫面亂碼或顯示異常？
- 請確認終端機支援 UTF-8 編碼。
- 嘗試調整終端機字型。

### 問：k9s 當機或閃退？
- 更新至最新版 k9s。
- 刪除 `~/.k9s` 目錄後重啟（會重置所有設定）。

## 🎹 七、自訂快捷鍵（Hotkey Mapping）

k9s 支援自訂快捷鍵，需編輯 `~/.k9s/hotkey.yml`。

```yaml
hotKey:
  my-shell:
    shortCut: Ctrl-S
    description: Open custom shell
    scopes: [po]
    command: kubectl
    args: [exec, -it, $POD, --, /bin/sh]
```
> 重新啟動 k9s 後生效。

## 🌐 八、多叢集/多 Context 管理

- 使用 `:ctx` 指令可切換 context。
- 支援多 kubeconfig，啟動時可指定：

```bash
k9s --kubeconfig /path/to/your/kubeconfig
```
- 也可用 `KUBECONFIG` 環境變數：

```bash
export KUBECONFIG=~/.kube/config:~/.kube/another-config
k9s
```

## 📑 九、資源書籤（Bookmarks）功能

- 在資源上按 `Shift-b` 可加入書籤。
- 按 `Shift-b` 可快速切換至已書籤的資源。
- 書籤儲存在 `~/.k9s/bookmarks.yml`。

## ⚙️ 十、k9s 配置檔案說明

- 主要配置路徑：`~/.k9s/`
  - `config.yml`：全域設定（如預設 namespace、主題等）
  - `skin.yml`：自訂主題
  - `plugins.yaml`：自訂插件
  - `bookmarks.yml`：資源書籤
  - `hotkey.yml`：自訂快捷鍵

> 建議定期備份設定檔。

## 🔗 十一、常用外部資源連結

- 官方文件：https://k9scli.io/
- GitHub 專案：https://github.com/derailed/k9s
- 官方主題庫：https://github.com/derailed/k9s/tree/master/skins
- k9s 常見問題：https://github.com/derailed/k9s/discussions
- k9s 插件範例：https://github.com/derailed/k9s/blob/master/plugins.yaml
