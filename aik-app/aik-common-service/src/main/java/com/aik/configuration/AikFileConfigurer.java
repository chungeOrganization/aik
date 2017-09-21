package com.aik.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Description:
 * Created by as on 2017/8/29.
 */
@Configuration
public class AikFileConfigurer extends WebMvcConfigurerAdapter {

    @Value("${file.mapping-root-uri}")
    private String fileRootUri;

    @Value("${file.upload-root-uri}")
    private String uploadRootUri;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileRootUri + "/**").addResourceLocations("file:" + uploadRootUri);
        super.addResourceHandlers(registry);
    }
}
