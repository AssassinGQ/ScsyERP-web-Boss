package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.OutStorage.facade.enums.OutStorageFormStatus;
import org.springframework.core.convert.converter.Converter;

public class OutStorageFormStatusConverter implements Converter<String, OutStorageFormStatus> {
    @Override
    public OutStorageFormStatus convert(String s) {
        return OutStorageFormStatus.getEnum(Integer.parseInt(s));
    }
}
