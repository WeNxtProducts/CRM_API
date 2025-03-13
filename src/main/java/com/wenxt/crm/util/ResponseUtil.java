// ResponseUtil.java
package com.wenxt.crm.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static Map<String, Object> createResponse(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return response;
    }
}