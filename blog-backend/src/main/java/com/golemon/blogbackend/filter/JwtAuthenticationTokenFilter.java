package com.golemon.blogbackend.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.golemon.blogbackend.enums.BlogSystemConstantsEnum;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.blogbackend.common.LoginUser;
import com.golemon.blogbackend.utils.JwtUtil;
import com.golemon.blogbackend.utils.RedisCache;
import com.golemon.exception.BlogSystemException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Custom filter JwtAuthenticationTokenFilter, which extracts JWT Token from the request,
 * validates the Token's validity, and stores the authentication information in SecurityContext,
 * ensuring that subsequent requests can be authenticated and authorized through Spring Security's authentication mechanism.
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get token
        String token = request.getHeader("token");
//        String token = request.getHeader("Authorization");
        if (token != null) {
//            System.out.println("Token: " + token);
        } else {
            System.out.println("No token provided");
        }

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get user information from redis
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            resolveException(request, response);
            return;
        }
//        System.out.println("test" + BlogSystemConstantsEnum.REDIS_USER_ID_PREFIX.getValue() + userId);
        LoginUser loginUser = redisCache.getCacheObject(BlogSystemConstantsEnum.REDIS_USER_ID_PREFIX.getValue() + userId);
//        JSONObject jsonObject =  redisCache.getCacheObject(BlogSystemConstantsEnum.REDIS_USER_ID_PREFIX.getValue() + userId);
//        LoginUser loginUser = jsonObject.toJavaObject(LoginUser.class);

//        System.out.println("test" + jsonObject);
        if (loginUser == null) {
            resolveException(request, response);
            return;
        }
        // Store the encapsulated authentication in SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private void resolveException(HttpServletRequest request, HttpServletResponse response){
        exceptionResolver.resolveException(request, response, null, new BlogSystemException(HttpStatusCodeEnum.NEED_LOGIN));
    }
}
