package com.aik.resource;

import com.aik.model.SysIllTypeTree;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;


/**
 * Description:
 * Created by as on 2017/8/29.
 */
@Component
public class SystemResource {

    @Value("${file.mapping-root-uri}")
    private String fileRootUri;

    @Value("${system.api-uri}")
    private String apiUri;

    public String getApiFileUri() {
        return apiUri + fileRootUri;
    }
}
