package com.finekuo.infosync.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Map;

public class JsonUtil {

    public static String convertToBase64(Map<String, Object> jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(jsonData);
        return Base64.getEncoder().encodeToString(jsonString.getBytes("UTF-8"));
    }

}
