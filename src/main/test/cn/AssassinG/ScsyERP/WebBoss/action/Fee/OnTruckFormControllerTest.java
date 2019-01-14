package cn.AssassinG.ScsyERP.WebBoss.action.Fee;

import org.junit.Test;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class OnTruckFormControllerTest {

    @Test
    public void create() {
        try{
            Map<String, String> paramMap = new HashMap<>();
//            System.out.println(paramMap);
            System.out.println(HttpUtils.Post("/OnTruckForm/create",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("entityId", "1");
            paramMap.put("signMan", "asd");
            System.out.println(HttpUtils.Post("/OnTruckForm/update",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void complete() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("warehouse", "1");
            paramMap.put("location", "B");
            System.out.println(paramMap);
            System.out.println(HttpUtils.Post("/OnTruckForm/complete",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}