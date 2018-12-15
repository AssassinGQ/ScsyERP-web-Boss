package cn.AssassinG.ScsyERP.WebBoss.action.LoginableInfo;

import cn.AssassinG.ScsyERP.User.facade.enums.AdminDeptType;
import org.junit.Test;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class AdminControllerTest {

    @Test
    public void create() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("corporation", "1");
            //user
            paramMap.put("phone", "18868112117");
            paramMap.put("userName", "hgqaassa");
            paramMap.put("passWord", "123456");
            //admin
            paramMap.put("name", "asde");
            paramMap.put("dept", String.valueOf(AdminDeptType.WarehouseAdmin.getName()));
            System.out.println(paramMap);
            System.out.println(HttpUtils.Post("http://127.0.0.1:8080/BasicInfo/Admin/create",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void query() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("limit", "10");
            paramMap.put("page", "1");
            System.out.println(HttpUtils.Get("http://127.0.0.1:8080/BasicInfo/Admin/query",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}