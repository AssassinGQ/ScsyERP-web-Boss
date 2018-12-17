package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.InStorage.facade.enums.InStorageFormStatus;
import org.springframework.core.convert.converter.Converter;

public class InStorageFormStatusConverter implements Converter<String, InStorageFormStatus> {
    @Override
    public InStorageFormStatus convert(String s) {
        return InStorageFormStatus.getEnum(Integer.parseInt(s));
    }
}
