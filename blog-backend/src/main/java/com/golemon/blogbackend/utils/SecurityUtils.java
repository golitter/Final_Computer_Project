package com.golemon.blogbackend.utils;

import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.blogbackend.common.LoginUser;
import com.golemon.exception.BlogSystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SecurityUtils is a utility class that provides methods to access information about the currently logged-in user
 * in a Spring Security environment. It offers convenient methods for checking user roles (such as admin role)
 * and login status. This utility class helps to easily access the details of the currently logged-in user in the system.
 */
public class SecurityUtils {

    /**
     * Get the current logged-in user
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * Get the Authentication object
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin() {
        Long id = getLoginUser().getUser().getId();
        return id != null && 14787164048694L == id;
    }

    /**
     * Get the ID of the currently logged-in user, returns null if not found
     *
     * @return User ID
     */
    public static Long getUserId() {
        Long userId;
        try {
            userId = getLoginUser().getUser().getId();
        } catch (Exception e) {
            throw new BlogSystemException(HttpStatusCodeEnum.NEED_LOGIN);
        }
        return userId;
    }
}