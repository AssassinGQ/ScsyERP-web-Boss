package cn.AssassinG.ScsyERP.WebBoss.action.PMS;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class UserControllerTest {

    private Long userId;

    @Before
    public void setUp() throws Exception {
//        User user = new User();
//        user.setUserName(StringUtils.getRandomStr(6));
//        user.setPassWord("d123456");
//        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
//        user.setCorporation(1L);
//        user.setUserType(UserType.Corporation);
//        user.setUserInfo(-1L);
//        userId = userServiceFacade.create(user);
    }

    @After
    public void tearDown() throws Exception {
//        userServiceFacade.deleteById(userId);
    }

    private static final int AccountTypeCorporation = 0;
    private static final int AccountTypeGovernment = 1;
    @Test
    public void getAccount() {
//        String token = "superadminabcd1234";
//        User user = new User();
//        user.setUserName(StringUtils.getRandomStr(6));
//        user.setPassWord("d123456");
//        user.setPhone("188" + StringUtils.getRandomStr(8, StringUtils.StrType.NUMBER));
//        user.setCorporation(1L);
//        JSONObject jsonObject = userController.getAccount(token, AccountTypeCorporation, StringUtils.getRandomStr(6), user);
//        System.out.println(jsonObject);
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", "superadminabcd1234");
            paramMap.put("userName", "corp1");
            paramMap.put("passWord", "123456");
            paramMap.put("phone", "18868183333");
            paramMap.put("name", "corp1");
            paramMap.put("type", String.valueOf(AccountTypeCorporation));
            System.out.println(HttpUtils.Post("/user/getAccount",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //测试SpringSecurity的登录功能
    //VUE中就用这个URL请求登录
    @Test
    public void login() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("userName", "superadmin");
            paramMap.put("passWord", "123456");
            System.out.println(HttpUtils.Post("/user/dologin",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getVcode() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("phone", "18888888888");
            System.out.println(HttpUtils.Post("/user/getVcode",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changePsw() {
    }

    @Test
    public void changeUname() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("userId", "2");
            paramMap.put("vCode", "123456");
            paramMap.put("newUserName", "aaaaaa");
            System.out.println(HttpUtils.Post("/user/changeUname",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changePhone() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("userId", "2");
            paramMap.put("vCode", "123456");
            paramMap.put("newPhone", "18899999999");
            System.out.println(HttpUtils.Post("/user/changePhone",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}