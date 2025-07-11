package com.atguigu.lease.web.admin.custom.converter;

import com.atguigu.lease.model.enums.BaseEnum;
import com.atguigu.lease.model.enums.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月10日 9:55
 */
@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String source) {
                for (T enumConstant : targetType.getEnumConstants()) {
                    if (enumConstant.getCode().equals(Integer.valueOf(source))) {
                        return enumConstant;
                    }
                }
                throw new IllegalArgumentException("Invalid ItemType code: " + source);
            }
        };
    }
}
