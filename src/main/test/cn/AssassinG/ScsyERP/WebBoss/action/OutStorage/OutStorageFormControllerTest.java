package cn.AssassinG.ScsyERP.WebBoss.action.OutStorage;

import org.junit.Test;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class OutStorageFormControllerTest {

    @Test
    public void create() {
        try{
            Map<String, String> paramMap = new HashMap<>();
//            System.out.println(paramMap);
            System.out.println(HttpUtils.Post("/OutStorageForm/create",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        try{
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("entityId", "1");
            paramMap.put("pickWorker", "2");
            System.out.println(HttpUtils.Post("/OutStorageForm/update",paramMap));
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
            System.out.println(HttpUtils.Post("/OutStorageForm/complete",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}