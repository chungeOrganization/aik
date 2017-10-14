package com.aik.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
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
    
    
    /** 
     * 获取文件内容 
     * @param filePath 
     * @return 
     * @throws IOException 
     */  
    public static byte[] getFileContent(String filePath) throws IOException {    
        File file = new File(filePath);    
        long fileSize = file.length();    
        if (fileSize > Integer.MAX_VALUE) {    
            System.out.println("file too big...");    
            return null;    
        }    
        FileInputStream fi = new FileInputStream(file);    
        byte[] buffer = new byte[(int) fileSize];    
        int offset = 0;    
        int numRead = 0;    
        while (offset < buffer.length    
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {    
            offset += numRead;    
        }    
        // 确保所有数据均被读取    
        if (offset != buffer.length) {    
            throw new IOException("Could not completely read file "    
                    + file.getName());    
        }    
        fi.close();    
        return buffer;    
    } 
}
