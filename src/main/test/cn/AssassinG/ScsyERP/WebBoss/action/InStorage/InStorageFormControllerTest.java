package cn.AssassinG.ScsyERP.WebBoss.action.InStorage;

import org.junit.Test;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class InStorageFormControllerTest {

    @Test
    public void uploadLocation() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("warehouse", "2");
            paramMap.put("location", "B");
//            System.out.println(paramMap);
            System.out.println(HttpUtils.Post("/InStorageForm/upload_location",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        try{
            Map<String, String> paramMap = new HashMap<>();
//            System.out.println(paramMap);
            System.out.println(HttpUtils.Post("/InStorageForm/create",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        try{
            Map<String, String> paramMap = new HashMap<>();
//            paramMap.put("warehouse", "1");
//            paramMap.put("location", "B");
//            System.out.println(paramMap);
            System.out.println(HttpUtils.Post("/InStorageForm/update",paramMap));
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
            System.out.println(HttpUtils.Post("/InStorageForm/complete",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}