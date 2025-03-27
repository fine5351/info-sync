在 **Spring Boot** 中，你可以使用 `ResponseEntity<byte[]>` 來回傳 **`.png` 或 `.jpeg`** 圖片。這通常用於 API 直接提供圖片，例如從資料庫、雲端儲存、或本地檔案系統載入圖片後回傳給前端。

---

## **🔹 基本實作**

### **方法 1：從本地檔案讀取並回傳**

```java
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam String filename) {
        try {
            // 設定圖片檔案路徑（確保圖片在 `static/images/` 目錄下）
            Path filePath = Paths.get("static/images").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 設定適當的 Content-Type
            String contentType = filename.endsWith(".png") ? "image/png" : "image/jpeg";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
```

### **🔹 測試方式**

- 瀏覽器或 Postman 輸入：
  ```
  http://localhost:8080/image?filename=test.png
  ```

---

## **🔹 進階實作**

### **方法 2：從資料庫或其他來源載入圖片**

如果你的圖片存儲在 **資料庫或遠端儲存（S3、MinIO 等）**，你可以將圖片讀取為 **byte[]**，然後回傳：

```java
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping("/image-bytes")
    public ResponseEntity<byte[]> getImageBytes(@RequestParam String filename) {
        try {
            Path filePath = Paths.get("static/images").resolve(filename).normalize();
            byte[] imageBytes = Files.readAllBytes(filePath);

            // 設定適當的 Content-Type
            String contentType = filename.endsWith(".png") ? "image/png" : "image/jpeg";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
```

### **🔹 測試方式**

- 瀏覽器或 Postman 輸入：
  ```
  http://localhost:8080/image-bytes?filename=test.jpg
  ```

---

## **🔹 總結**

| 方法                    | 優點          | 缺點                  |
|-----------------------|-------------|---------------------|
| **方法 1（Resource 方式）** | 直接讀取檔案，效能較佳 | 需確保檔案路徑安全           |
| **方法 2（byte[] 方式）**   | 可用於資料庫、遠端圖片 | 需要讀取全部 byte[]，佔用記憶體 |

如果圖片是靜態檔案，建議使用 **方法 1**，效能更好。  
如果圖片來自 **資料庫或遠端 API**，建議使用 **方法 2**。 🚀
