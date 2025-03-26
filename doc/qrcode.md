## **📌 問題分析**

目前 **client（用戶手機端）無法知道即將進入的門禁設備（iStart）是哪一台**，這會導致：

1. **client 無法提供 GEP 正確的 iStart ID**，導致 **GEP 無法決定使用哪個 AES 金鑰來加密 QRCode**。
2. **GEP 可能選擇了錯誤的金鑰**，導致 **iStart 無法解密 QRCode**，影響門禁運作。

---

## **🚀 解決方案**

由於 **client 無法決定要面對哪台 iStart**，我們需要 **讓 QRCode 內部支援多組金鑰解密**，確保 **所有 iStart 都能正確解密**。這可以透過 **兩種方式實現**：

---

## **✅ 方案 1：GEP 產生多組 AES 加密數據，QRCode 內含多組加密結果**

### **🔹 流程**

1. **GEP 查詢 ACS，獲取所有 iStart 最新的 AES 金鑰列表**（例如 `version 5` 和 `version 6`）。
2. **GEP 使用所有已知金鑰加密 QRCode 數據**：
   ```json
   {
     "encrypted_v5": "AES_加密資料_版本5",
     "encrypted_v6": "AES_加密資料_版本6"
   }
   ```
3. **client 取得 QRCode（內含多組加密數據）**，不需要知道即將進入哪一台 iStart。
4. **iStart 讀取 QRCode，嘗試解密**：
    - 如果 iStart 內建的是 **version 5 金鑰**，則解密 `encrypted_v5`。
    - 如果 iStart 內建的是 **version 6 金鑰**，則解密 `encrypted_v6`。

### **📌 優點**

✅ **client 無需知道 iStart ID，QRCode 仍然可被所有 iStart 解密**。  
✅ **GEP 可以逐步更換 AES 金鑰，不影響舊版 iStart 的運作**。  
✅ **iStart 斷網時，仍然能解密 QRCode，確保門禁正常運作**。

### **📌 缺點**

❌ **QRCode 數據變大**（但影響不大，因為 AES 加密後的數據量仍然很小）。  
❌ **GEP 需要管理多組加密版本，可能稍微增加計算量**。

---

## **✅ 方案 2：所有 iStart 使用同一組 AES 金鑰**

### **🔹 流程**

1. **GEP & ACS 保持所有 iStart 使用相同的 AES 金鑰**（例如 `global_key_v6`）。
2. **GEP 使用統一的 AES 金鑰加密 QRCode**：
   ```json
   {
     "encrypted_data": "AES_加密後的數據"
   }
   ```
3. **所有 iStart 內建相同的 `global_key_v6`，因此都能成功解密**。

### **📌 優點**

✅ **QRCode 內容更小，只需儲存單一加密數據**。  
✅ **iStart 斷網時仍可解密，避免同步問題**。  
✅ **client 無需考慮 iStart ID，QR Code 通用於所有門禁設備**。

### **📌 缺點**

❌ **如果 AES 金鑰洩漏，所有 iStart 都可能被攻擊**（但可以透過定期更換金鑰來減少風險）。  
❌ **所有 iStart 必須同步更新 AES 金鑰**，若某台設備未及時更新，則可能無法解密新 QRCode。

---

## **📊 方案比較**

| 方案                         | **client 需提供 iStart ID？** | **支援 iStart 斷網？** | **金鑰輪換風險** | **實作複雜度** |
|----------------------------|---------------------------|-------------------|------------|-----------|
| **方案 1（QRCode 內含多組加密數據）**  | ❌ **不需要**                 | ✅ **可解密**         | 低          | 中等        |
| **方案 2（所有 iStart 使用相同金鑰）** | ❌ **不需要**                 | ✅ **可解密**         | 高          | 低         |

---

## **🚀 最佳方案推薦**

✅ **如果希望金鑰更安全、逐步輪換金鑰，不影響舊版 iStart，建議使用** **方案 1（QRCode 內含多組加密數據）**。  
✅ **如果希望 QRCode 更簡潔，簡化管理，可以選擇** **方案 2（所有 iStart 使用相同金鑰）**，但需要更嚴格的密鑰管理策略。

---

## **📌 方案 1 的 Java 實作**

### **🔹 GEP 產生 QRCode（支援多組 AES 加密數據）**

```java
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

public class QRCodeGenerator {
    // 假設這是 ACS 追蹤的所有 iStart 最新金鑰（版本 5 和 6）
    private static final Map<String, SecretKey> AES_KEYS = new HashMap<>();

    static {
        try {
            AES_KEYS.put("v5", AESUtil.generateAESKey()); // AES 版本 5
            AES_KEYS.put("v6", AESUtil.generateAESKey()); // AES 版本 6
        } catch (Exception e) {
            throw new RuntimeException("AES Key Initialization Failed");
        }
    }

    public static String generateQRCode(String userId) throws Exception {
        Map<String, String> encryptedData = new HashMap<>();
        String jsonData = "{\"user_id\":\"" + userId + "\",\"access_level\":\"A\"}";

        // 使用不同版本的 AES 金鑰加密數據
        for (Map.Entry<String, SecretKey> entry : AES_KEYS.entrySet()) {
            String keyVersion = entry.getKey();
            SecretKey aesKey = entry.getValue();
            encryptedData.put("encrypted_" + keyVersion, AESUtil.encryptAES(jsonData, aesKey));
        }

        return encryptedData.toString();
    }

    public static void main(String[] args) throws Exception {
        String qrCode = generateQRCode("12345");
        System.out.println("Generated QR Code Data: " + qrCode);
    }
}
```

---

### **🔹 iStart 解密 QRCode**

```java
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

public class QRCodeReader {
    // 假設 iStart 只內建特定版本的 AES 金鑰（例如 version 5）
    private static final SecretKey AES_KEY = QRCodeGenerator.AES_KEYS.get("v5");

    public static void main(String[] args) throws Exception {
        // 假設這是 QRCode 讀取的資料
        Map<String, String> qrCodeData = new HashMap<>();
        qrCodeData.put("encrypted_v5", "（GEP 產生的加密數據）");
        qrCodeData.put("encrypted_v6", "（GEP 產生的加密數據）");

        // 嘗試解密不同版本的數據
        String decryptedData = null;
        for (Map.Entry<String, String> entry : qrCodeData.entrySet()) {
            try {
                decryptedData = AESUtil.decryptAES(entry.getValue(), AES_KEY);
                break; // 成功解密後結束迴圈
            } catch (Exception ignored) {
                // 解密失敗則嘗試下一組數據
            }
        }

        if (decryptedData != null) {
            System.out.println("Decrypted Data: " + decryptedData);
        } else {
            System.out.println("Decryption Failed!");
        }
    }
}
```

---

## **🎯 最終結論**

✅ **使用 AES（不使用 RSA），讓 QRCode 內部支援多組加密數據（方案 1），確保所有 iStart 都能解密 QRCode**。  
✅ **client 無需知道 iStart ID，GEP 透過 ACS 獲取所有 iStart 的 AES 金鑰版本，確保 QRCode 可解密**。  
✅ **iStart 斷網時仍然可以解密 QRCode，確保門禁系統穩定運作！** 🚀
