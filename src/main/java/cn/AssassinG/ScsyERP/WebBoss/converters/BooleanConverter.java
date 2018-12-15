package cn.AssassinG.ScsyERP.WebBoss.converters;

import org.springframework.core.convert.converter.Converter;

public class BooleanConverter implements Converter<String, Boolean> {
    @Override
    public Boolean convert(String s) {
        if(s == null || s.isEmpty())
            return null;
        else if(s.equals("true"))
            return true;
        else if(s.equals("false"))
            return false;
        else
            return null;
    }
}
