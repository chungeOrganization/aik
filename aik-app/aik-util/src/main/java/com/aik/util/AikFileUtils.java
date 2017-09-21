package com.aik.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 * Created by as on 2017/9/16.
 */
public class AikFileUtils {

    public static void uploadImg(InputStream inputStream, String url) throws IOException {
        File file = new File(url);

        FileUtils.copyInputStreamToFile(inputStream, file);
    }
}
