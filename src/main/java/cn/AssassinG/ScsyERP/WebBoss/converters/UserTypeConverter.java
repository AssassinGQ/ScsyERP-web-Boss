package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import org.springframework.core.convert.converter.Converter;

public class UserTypeConverter implements Converter<String, UserType> {
    @Override
    public UserType convert(String s) {
        return UserType.getEnumByName(s);
    }
}
