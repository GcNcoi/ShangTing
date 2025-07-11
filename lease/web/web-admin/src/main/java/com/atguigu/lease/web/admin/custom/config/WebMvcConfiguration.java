package com.atguigu.lease.web.admin.custom.config;

import com.atguigu.lease.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月10日 9:47
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(this.stringToBaseEnumConverterFactory);
    }

}
