package com.golemon.blogbackend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class IpRateLimitFilter extends OncePerRequestFilter {

    private final RedisTemplate<Object, Object> redisTemplate;
    private static final int MAX_REQUESTS = 50;  // Maximum number of requests
    private static final int TIME_WINDOW = 60;   // Time window (seconds)

    public IpRateLimitFilter(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = getClientIp(request);
        String key = "ip_limit:" + ip;

        // Get current access count
        Object countObj = redisTemplate.opsForValue().get(key);
        Integer count = countObj != null ? (Integer) countObj : null;

        if (count == null) {
            // First access, set expiration time
            redisTemplate.opsForValue().set(key, 1, TIME_WINDOW, TimeUnit.SECONDS);
        } else if (count < MAX_REQUESTS) {
            // Increase access count
            redisTemplate.opsForValue().increment(key);
        } else {
            // Exceeded limit
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(429);  // Too Many Requests
            response.getWriter().write("{\"code\":429,\"message\":\"Too many requests, please try again later\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}