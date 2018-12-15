package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.common.enums.AccountStatus;
import org.springframework.core.convert.converter.Converter;

public class AccountStatusConverter implements Converter<String, AccountStatus> {
    @Override
    public AccountStatus convert(String s) {
        return AccountStatus.getEnum(Integer.parseInt(s));
    }
}
