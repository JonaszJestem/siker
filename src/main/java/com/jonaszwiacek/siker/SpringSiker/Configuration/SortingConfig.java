package com.jonaszwiacek.siker.SpringSiker.Configuration;

import com.jonaszwiacek.siker.SpringSiker.Converters.SortingConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SortingConfig extends WebMvcConfigurationSupport {
    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f = super.mvcConversionService();
        f.addConverter(new SortingConverter());
        return f;
    }
}