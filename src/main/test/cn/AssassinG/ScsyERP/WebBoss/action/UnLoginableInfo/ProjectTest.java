package cn.AssassinG.ScsyERP.WebBoss.action.UnLoginableInfo;

import org.junit.Test;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class ProjectTest {
    @Test
    public void create() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("corporation", "1");
            paramMap.put("projectNumber", "11231231");
            paramMap.put("customer", "1");
            paramMap.put("manufacturer", "1");
            paramMap.put("consignee", "1");
            paramMap.put("admin", "1");
            paramMap.put("phone", "18812345678");
            paramMap.put("name", "asdsdds");
            System.out.println(HttpUtils.Post("/BasicInfo/Project/create",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void update() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("entityId", "1");
            paramMap.put("name", "asdad");
            System.out.println(HttpUtils.Post("/BasicInfo/Workshop/update",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void delete() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("entityId", "20");
            System.out.println(HttpUtils.Post("/BasicInfo/Workshop/delete",paramMap));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
