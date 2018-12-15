package cn.AssassinG.ScsyERP.WebBoss.security;

import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserServiceFacade userServiceFacade;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With, Origin");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        JSONObject retObject = new JSONObject();
        retObject.put("status", RetStatusType.StatusSuccess.getStatus());
        retObject.put("msg", "登录成功");
        JSONObject contentObject = new JSONObject();
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            User user = userServiceFacade.findUserByUname(((UserDetails)principal).getUsername());
            if(user != null && !user.getIfDeleted()){
                contentObject.put("id", user.getId());
                contentObject.put("userName", user.getUserName());
                contentObject.put("userTypeName", user.getUserType().getName());
                contentObject.put("userType", user.getUserType().getValue());
                contentObject.put("userInfo", user.getUserInfo());
                contentObject.put("corporation", user.getCorporation());
                contentObject.put("phone", user.getPhone());
            }
        }
        retObject.put("content", contentObject);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(retObject.toJSONString());
        out.flush();
        out.close();
    }
}
