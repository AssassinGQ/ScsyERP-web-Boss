package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.User.facade.enums.AdminDeptType;
import org.springframework.core.convert.converter.Converter;

public class AdminDeptConverter implements Converter<String, AdminDeptType> {
    @Override
    public AdminDeptType convert(String s) {
        return AdminDeptType.getEnum(Integer.parseInt(s));
    }
}
