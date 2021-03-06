package cn.AssassinG.ScsyERP.WebBoss.security;

import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.WebBoss.utils.HttpSessionUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        HttpSessionUtils.setCORS(httpServletRequest, httpServletResponse);
        PrintWriter out = httpServletResponse.getWriter();
        JSONObject retObject = new JSONObject();
        retObject.put("status", RetStatusType.StatusFailure.getStatus());
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            retObject.put("msg", "登录失败：用户名或密码输入错误");
        } else if (e instanceof DisabledException) {
            retObject.put("msg", "登录失败：账户被禁用");
        } else {
            retObject.put("msg", "登录失败");
        }
        retObject.put("content", null);
        out.write(retObject.toJSONString());
        out.flush();
        out.close();
    }
}
