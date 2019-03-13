package cn.AssassinG.ScsyERP.WebBoss.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class JavaBeanUtils {
    public static Map<String, Object> Bean2Map(Object clazz){
        Map<String, Object> ret = new HashMap<>();
        if(clazz == null){
            return ret;
        }
        List<Field> fields = new ArrayList<>();
        Class<?> tempClass = clazz.getClass();
        while(tempClass != null){
            fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);
            if ((field.getModifiers()&(Modifier.PRIVATE|Modifier.PROTECTED)) == 0)
                continue;
            String fieldname = field.getName();
            Object object = null;
            try {
                object = field.get(clazz);
                if(object != null)
                    ret.put(fieldname, object);
            } catch (IllegalArgumentException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return ret;
    }
}
