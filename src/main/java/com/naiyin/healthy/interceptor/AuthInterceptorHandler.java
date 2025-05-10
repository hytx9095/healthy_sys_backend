package com.naiyin.healthy.interceptor;

import cn.hutool.json.JSONObject;
import com.naiyin.healthy.common.UserContext;
import com.naiyin.healthy.common.UserInfo;
import com.naiyin.healthy.config.JwtProperties;
import com.naiyin.healthy.constant.JwtConstant;
import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.enums.UserRoleEnum;
import com.naiyin.healthy.exception.AuthException;
import com.naiyin.healthy.model.entity.User;
import com.naiyin.healthy.service.UserService;
import com.naiyin.healthy.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class AuthInterceptorHandler implements HandlerInterceptor {

    private final JwtProperties jwtProperties;
    private final UserService userService;

    /**
     * 前置拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x_requested_with,x-requested-with,Authorization,Content-Type,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 获取到JWT的Token
        String jwtToken = request.getHeader(JwtConstant.TOKEN_HEADER);
        // 创建json对象
        JSONObject jsonObject = new JSONObject();

        if (StringUtils.hasLength(jwtToken)) {
            // 解析Token，获取Claims = Map
            Claims claims = null;
            try {
                claims = JwtUtils.parseJwtToken(jwtToken);
            } catch (AuthException e) {
                jsonObject.put("code", e.getCode());
                jsonObject.put("msg", e.getMessage());
                String json = jsonObject.toJSONString(1);
                renderJson(response, json);
            }

            long userId = JwtUtils.getUserIdByToken(jwtToken);
            UserContext.setUserId(userId);

            User user = userService.lambdaQuery().eq(User::getId, userId).one();
            if (user.getRole().equals(UserRoleEnum.BAN.getValue())){
                throw new AuthException(SysErrorEnum.NO_AUTH_ERROR, "账号被封，禁止访问！");
            }
            UserContext.setUserInfo(new UserInfo(userId, user.getUsername(), user.getUserAvatar()));
            return claims != null;
        }
        //如果token不存在
        jsonObject.put("code", SysErrorEnum.PARAM_ERROR.getCode());
        jsonObject.put("msg", "登录非法");
        String json = jsonObject.toJSONString(1);
        renderJson(response, json);

        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUserId();
        UserContext.removeUserInfo();
    }

    private void renderJson(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
