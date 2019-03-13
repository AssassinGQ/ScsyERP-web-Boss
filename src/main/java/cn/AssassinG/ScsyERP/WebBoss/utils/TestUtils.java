package cn.AssassinG.ScsyERP.WebBoss.utils;

import java.util.Iterator;
import java.util.Map;

public class TestUtils {
    public static void printMap(Map<String, Object> paramMap){
        Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
        System.out.println("Printing Map " + paramMap);
        while(iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            System.out.println("K(" + entry.getKey() + ")-V(" + entry.getValue() + ")");
        }
        System.out.println("End of Print Map " + paramMap);
    }
}
