package com.finekuo.infosync.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.crypto.SecretKey;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    public static void generateQRCode(String text, String filePath, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void main(String[] args) {
        try {
            // 1. JSON 轉 Base64
            Map<String, Object> jsonData = new HashMap<>();
            jsonData.put("name", "John Doe");
            jsonData.put("email", "john@example.com");
            jsonData.put("age", 30);
            String base64Json = JsonUtil.convertToBase64(jsonData);
            System.out.println("Base64 JSON: " + base64Json);

            // 2. AES 加密
            SecretKey secretKey = AESUtil.generateAESKey();
            String encryptedData = AESUtil.encryptAES(base64Json, secretKey);
            System.out.println("Encrypted Data: " + encryptedData);

            // 3. 生成 QR Code
            String qrCodePath = "qrcode.png";
            QRCodeGenerator.generateQRCode(encryptedData, qrCodePath, 300, 300);
            System.out.println("QR Code saved at: " + qrCodePath);

            // 4. 測試解密
            String decryptedData = AESUtil.decryptAES(encryptedData, secretKey);
            System.out.println("Decrypted Data: " + decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
