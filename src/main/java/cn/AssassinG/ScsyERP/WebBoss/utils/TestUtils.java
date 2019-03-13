package cn.AssassinG.ScsyERP.WebBoss.utils;

import java.util.Iterator;
import java.util.Map;

public class TestUtils {
    public static <T> void printMap(Map<String, T> paramMap){
        Iterator<Map.Entry<String, T>> iterator = paramMap.entrySet().iterator();
        System.out.println("Printing Map " + paramMap);
        while(iterator.hasNext()){
            Map.Entry<String, T> entry = iterator.next();
            System.out.println("K(" + entry.getKey() + ")-V(" + entry.getValue() + ")");
        }
        System.out.println("End of Print Map " + paramMap);
    }

    public static <T> void printBean(T Bean){
        System.out.println("Printing " + Bean.getClass());
        System.out.println(Bean);
        System.out.println("End of Print " + Bean.getClass());
    }
}
