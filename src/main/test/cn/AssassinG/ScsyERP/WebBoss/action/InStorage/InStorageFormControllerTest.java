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
            paramMap.put("warehouse", "1");
            paramMap.put("location", "A");
            System.out.println(paramMap);
            System.out.println(HttpUtils.Post("http://127.0.0.1:8095/boss/InStorageForm/upload_location",paramMap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}