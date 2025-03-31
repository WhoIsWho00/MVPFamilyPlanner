package com.example.familyplanner.Other.IPAdressUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class IpAddressUtil {
    public String getClientIp(HttpServletRequest request) {
        //если есть прокси
        String ip = request.getHeader("x-forwarded-for");
        //если нет прокси
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
