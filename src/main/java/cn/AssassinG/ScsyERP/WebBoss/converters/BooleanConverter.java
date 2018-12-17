package cn.AssassinG.ScsyERP.WebBoss.converters;

import org.springframework.core.convert.converter.Converter;

public class BooleanConverter implements Converter<String, Boolean> {
    @Override
    public Boolean convert(String s) {
        if(s == null || s.isEmpty())
            return null;
        else if(s.equals("true") || s.equals("0"))
            return true;
        else if(s.equals("false") || s.equals("1"))
            return false;
        else
            return null;
    }
}
