package com.aik.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.text.SimpleDateFormat;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
@Component
@Provider
public class AsContextResolver implements ContextResolver<ObjectMapper> {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
}
