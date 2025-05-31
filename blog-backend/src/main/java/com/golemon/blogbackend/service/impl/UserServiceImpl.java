package com.golemon.blogbackend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.golemon.blogbackend.enums.BlogSystemConstantsEnum;
import com.golemon.blogbackend.enums.HttpStatusCodeEnum;
import com.golemon.blogbackend.mapper.UserMapper;
import com.golemon.blogbackend.common.LoginUser;
import com.golemon.blogbackend.common.domain.dto.LoginUserDTO;
import com.golemon.blogbackend.common.domain.entity.User;
import com.golemon.blogbackend.common.vo.LoginUserVo;
import com.golemon.blogbackend.common.vo.ResponseResult;
import com.golemon.blogbackend.common.vo.UserInfoVo;
import com.golemon.blogbackend.service.UserService;
import com.golemon.blogbackend.utils.BeanCopyUtils;
import com.golemon.blogbackend.utils.JwtUtil;
import com.golemon.blogbackend.utils.RedisCache;
import com.golemon.blogbackend.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult<?> getUserInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        if (user != null)
            return ResponseResult.okResult(BeanCopyUtils.copyBean(user, UserInfoVo.class));

        return ResponseResult.errorResult(HttpStatusCodeEnum.NEED_LOGIN);
    }

    @Override
    public ResponseResult<?> getAdminInfo() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getType, BlogSystemConstantsEnum.ADMIN_USER.getValue());
        User user = getOne(wrapper);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(user, UserInfoVo.class));
    }

    @Override
    public ResponseResult<?> register(User user) {

        // Check if user already exists
        LambdaQueryWrapper<User> sameNameWrapper = new LambdaQueryWrapper<>();
        sameNameWrapper.eq(User::getUserName, user.getUserName());
        User sameNameUser = getOne(sameNameWrapper);
        System.out.println(HttpStatusCodeEnum.USERNAME_EXIST);
//        Assert.isNull(sameNameUser, HttpStatusCodeEnum.USERNAME_EXIST);
        if (sameNameUser != null) {
            System.out.println(sameNameUser);
            return ResponseResult.errorResult(HttpStatusCodeEnum.USERNAME_EXIST);
        }
        // Check if email already exists
        // @TODO:
        // @TODO:
        //@TODO: Not necessary
        LambdaQueryWrapper<User> sameEmailWrapper = new LambdaQueryWrapper<>();
        sameEmailWrapper.eq(User::getEmail, user.getEmail());
        User sameEmailUser = getOne(sameEmailWrapper);
//        Assert.isNull(sameEmailUser, HttpStatusCodeEnum.EMAIL_EXIST);
        if (sameEmailUser != null) {
            return ResponseResult.errorResult(HttpStatusCodeEnum.EMAIL_EXIST);
        }

        // Add user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        System.out.println("Execution successful");
        return ResponseResult.okResult();
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    // TODO: November 18, 2024 00:13
    // TODO: November 18, 2024 16:50 Validation error
    @Override
    @Transactional
    public ResponseResult<?> login(LoginUserDTO user) {
//        log.info("User login");
//        // Validate username and password
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
//        log.info("Password: " + user.getPassword());
//        log.info("authenticationToken: " + authenticationToken);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication == null) {
            return ResponseResult.errorResult(HttpStatusCodeEnum.LOGIN_ERROR);
        }
//         Store user information in Redis
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        redisCache.setCacheObject(BlogSystemConstantsEnum.REDIS_USER_ID_PREFIX.getValue() + userId, loginUser);
//        System.out.println("loginUser: " + loginUser);
//         Return the token and user information to the user
        UserInfoVo userInfo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        userInfo.setIsAdmin(BlogSystemConstantsEnum.ADMIN_USER.getValue().equals(loginUser.getUser().getType()));
        LoginUserVo loginUserVo = new LoginUserVo(token, userInfo);
        return ResponseResult.okResult(loginUserVo);
    }

    @Override
    public ResponseResult<?> logout() {
        // Get userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        // Delete user information from Redis
        redisCache.deleteObject(BlogSystemConstantsEnum.REDIS_USER_ID_PREFIX.getLongValue() + userId);
        return ResponseResult.okResult();
    }
}
