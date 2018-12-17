package cn.AssassinG.ScsyERP.WebBoss.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpSessionUtils {
    public static final String originHeader = "Access-Control-Allow-Origin";
    public static final String methodsHeader = "Access-Control-Allow-Methods";
    public static final String headerHeader = "Access-Control-Allow-Headers";
    public static final String credentialsHeader = "Access-Control-Allow-Credentials";
    public static final String maxAgeHeader = "Access-Control-Max-Age";
    public static void setCORS(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json;charset=utf-8");
        if(!response.containsHeader(originHeader)) {
            String origin = request.getHeader("Origin");
            if(origin == null) {
                String referer = request.getHeader("Referer");
                if(referer != null) {
                    origin = referer.substring(0, referer.indexOf("/", 7));
                }
            }
            response.setHeader(originHeader, origin);
        }
//            response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader(credentialsHeader, "true");
        response.setHeader(methodsHeader, "POST,GET,PUT,DELETE,OPTIONS");
        response.setHeader(headerHeader, "Content-Type, Content-Length, Authorization, Accept, X-Requested-With, Origin");
        response.setHeader(maxAgeHeader, "3600");
    }

    //读取cookie数组，之后迭代出各个cookie
    public static void showCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();//根据请求数据，找到cookie数组

        if (null==cookies) {//如果没有cookie数组
            System.out.println("没有cookie");
        } else {
            for(Cookie cookie : cookies){
                System.out.println("cookieName:"+cookie.getName()+",cookieValue:"+ cookie.getValue());
            }
        }
    }

    //创建cookie，并将新cookie添加到“响应对象”response中。
    public static void addCookie(HttpServletRequest request, HttpServletResponse response){
        System.out.println("set cookie");
        String origin = request.getHeader("Origin");
        if(origin == null) {
            String referer = request.getHeader("Referer");
            if(referer != null) {
                origin = referer.substring(0, referer.indexOf("/", 7));
            }
        }
        Cookie cookie = new Cookie("name_test","value_test");//创建新cookie
        cookie.setMaxAge(5 * 60);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        cookie.setDomain(origin);
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
//        Collection<String> headerNames = response.getHeaderNames();
//        Iterator<String> iterator = headerNames.iterator();
//        System.out.println("In addCookie, check:");
//        while(iterator.hasNext()){
//            String headerName = iterator.next();
//            System.out.println(response.getHeader(headerName));
//        }
    }
}
