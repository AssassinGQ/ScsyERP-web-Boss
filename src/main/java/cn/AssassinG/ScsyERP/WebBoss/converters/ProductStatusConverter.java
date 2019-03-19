package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.BasicInfo.facade.enums.ProductStatus;
import org.springframework.core.convert.converter.Converter;

public class ProductStatusConverter implements Converter<Integer, ProductStatus> {
    @Override
    public ProductStatus convert(Integer s) {
        return ProductStatus.getEnum(s);
    }
}
