package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.User.facade.enums.GovernmentDeptType;
import org.springframework.core.convert.converter.Converter;

public class GovernmentDeptConverter implements Converter<String, GovernmentDeptType> {
    @Override
    public GovernmentDeptType convert(String s) {
        return GovernmentDeptType.getEnum(s);
    }
}
