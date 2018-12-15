package cn.AssassinG.ScsyERP.WebBoss.Intercepts;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest ssReq = (ServletServerHttpRequest)serverHttpRequest;
        ServletServerHttpResponse ssResp = (ServletServerHttpResponse)serverHttpResponse;
        if(ssReq == null || ssResp == null
                || ssReq.getServletRequest() == null
                || ssResp.getServletResponse() == null) {
            return body;
        }

        // 对于未添加跨域消息头的响应进行处理
        HttpServletRequest req = ssReq.getServletRequest();
        HttpServletResponse resp = ssResp.getServletResponse();
        String originHeader = "Access-Control-Allow-Origin";
        if(!resp.containsHeader(originHeader)) {
            String origin = req.getHeader("Origin");
            if(origin == null) {
                String referer = req.getHeader("Referer");
                if(referer != null) {
                    origin = referer.substring(0, referer.indexOf("/", 7));
                }
            }
            resp.setHeader("Access-Control-Allow-Origin", origin);
        }

        String credentialHeader = "Access-Control-Allow-Credentials";
        if(!resp.containsHeader(credentialHeader)) {
            resp.setHeader(credentialHeader, "true");
        }

        String methodsHeader = "Access-Control-Allow-Methods";
        if(!resp.containsHeader(methodsHeader)) {
            resp.setHeader(methodsHeader, "POST,GET,OPTIONS");
        }

        String headerHeader = "Access-Control-Allow-Headers";
        if(!resp.containsHeader(headerHeader)){
            resp.setHeader(headerHeader, "X-Requested-With,accept,origin,content-type");
        }

        return body;
    }
}
