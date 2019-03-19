package cn.AssassinG.ScsyERP.WebBoss.converters;

import cn.AssassinG.ScsyERP.BasicInfo.facade.enums.PacketType;
import org.springframework.core.convert.converter.Converter;

public class PacketTypeConverter implements Converter<String, PacketType> {
    @Override
    public PacketType convert(String value) {
        System.out.println("in PacketTypeConverter");
        return PacketType.getEnum(Integer.parseInt(value));
    }
}
