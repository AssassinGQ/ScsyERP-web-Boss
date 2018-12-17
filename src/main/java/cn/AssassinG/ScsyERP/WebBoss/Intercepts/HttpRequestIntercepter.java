package cn.AssassinG.ScsyERP.WebBoss.Intercepts;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class HttpRequestIntercepter implements HandlerInterceptor {

        public static final String MAPKEY = "ParamMap";
        private static final String originHeader = "Access-Control-Allow-Origin";
//        private static final String methodsHeader = "Access-Control-Allow-Methods";
//        private static final String headerHeader = "Access-Control-Allow-Headers";
        /**
         * 在请求处理之前执行，
         * 该方法主要是用于准备资源数据的，
         * 然后可以把它们当做请求属性放到Request中
         * @param request
         * @throws Exception
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//            System.out.println("HttpRequestIntercepter====>preHandle");
            if(request.getRequestURI().endsWith("update")){
                Enumeration<String> paramNames = request.getParameterNames();
                Map<String, String> paramMap = new HashMap<>();
                while(paramNames.hasMoreElements()){
                    String name = paramNames.nextElement();//得到参数名
                    String value = request.getParameter(name);//通过参数名获取对应的值
                    paramMap.put(name, value);
                }
                request.setAttribute(MAPKEY, paramMap);
            }

            showCookies(request);

//            if(!response.containsHeader(originHeader)) {
//                String origin = request.getHeader("Origin");
//                if(origin == null) {
//                    String referer = request.getHeader("Referer");
//                    if(referer != null) {
//                        origin = referer.substring(0, referer.indexOf("/", 7));
//                    }
//                }
//                response.setHeader("Access-Control-Allow-Origin", origin);
//            }
//            response.setContentType("application/json;charset=utf-8");
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
//            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With, Origin");
//            response.setHeader("Access-Control-Max-Age", "3600");
            return true;
        }
        /**
         * 该方法将在Controller执行之后，
         * 返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在
         * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。
         */
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            addCookie(response);
//            System.out.println("AppRequestInterceptor====>preHandle");
//            String method= request.getMethod();
//            if (method.equals("OPTIONS")){
//                response.setStatus(200);
//                response.setHeader("Access-Control-Allow-Origin", "*");
//                response.setHeader("Access-Control-Allow-Credentials", "true");
//                response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
//                response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With, Origin");
//            }
        }

        /**
         * 该方法将在整个请求完成之后，
         * 也就是说在视图渲染之后进行调用，
         * 主要用于进行一些资源的释放
         */
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//            System.out.println("AppRequestInterceptor====>preHandle");
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
    public static void addCookie(HttpServletResponse response){
        System.out.println("set cookie");
        Cookie cookie = new Cookie("name_test","value_test");//创建新cookie
        cookie.setMaxAge(5 * 60);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
//        cookie.setDomain("http://localhost:2333");
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
