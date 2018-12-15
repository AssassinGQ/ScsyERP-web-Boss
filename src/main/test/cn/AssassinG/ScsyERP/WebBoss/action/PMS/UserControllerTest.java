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
    }

    //测试SpringSecurity的登录功能
    //VUE中就用这个URL请求登录
    @Test
    public void login() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("UserName", "hgq");
            paramMap.put("PassWord", "123456");
            System.out.println(HttpUtils.Post("http://120.76.219.196:8082/user/mmlogin",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getVcode() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("Phone", "18868187538");
            System.out.println(HttpUtils.Post("http://127.0.0.1:8080/user/getVcode",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changePsw() {
    }

    @Test
    public void changeUname() {
    }

    @Test
    public void changePhone() {
    }
}