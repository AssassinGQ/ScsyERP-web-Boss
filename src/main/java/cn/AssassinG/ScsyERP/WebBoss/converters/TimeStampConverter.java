package cn.AssassinG.ScsyERP.WebBoss.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class TimeStampConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        Date date = new Date(Long.parseLong(s));
        return date;
    }
}
