package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.BasicInfo.facade.enums.ProductStatus;
import org.springframework.core.convert.converter.Converter;

public class ProductStatusConverter implements Converter<String, ProductStatus> {
    @Override
    public ProductStatus convert(String s) {
        System.out.println("in ProductStatusConverter value = " + s);
        return ProductStatus.getEnum(Integer.parseInt(s));
    }
}
