package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.Fee.facade.enums.OilCardType;
import org.springframework.core.convert.converter.Converter;

public class OilCardTypeConverter implements Converter<String, OilCardType> {
    @Override
    public OilCardType convert(String s) {
        return OilCardType.getEnum(Integer.parseInt(s));
    }
}
