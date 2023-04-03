package com.qycr.framework.boot.autoconfigure;

import com.qycr.framework.boot.office.excel.core.support.swagger.ParameterImportTypeReader;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;


@Configuration
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
@AutoConfigureAfter(SolarExcelAutoConfiguration.class)
@ConditionalOnBean({SpringfoxWebMvcConfiguration.class, SolarExcelAutoConfiguration.class})
public class SolarSwaggerAutoConfiguration {



    @Bean
    @ConditionalOnMissingBean
    public ParameterImportTypeReader parameterImportTypeReader() {
        return new ParameterImportTypeReader();
    }



}
