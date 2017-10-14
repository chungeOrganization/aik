package com.aik.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aik.util.AikFileUtils;


/**
 * @author daixiangning
 * @message 文件管理
 */
@RestController
@RequestMapping("/image")
public class FileDealController {
	
	private Logger logger = LoggerFactory.getLogger(FileDealController.class);
	
	@Value("${file.upload-root-uri}")
	private String uploadRootUri;

    @RequestMapping(value = "/getSrcImg/{type}/{fileName}")
    public void getSrcImg(HttpServletRequest request, HttpServletResponse response,@PathVariable String type,@PathVariable String fileName){  
        System.out.println("getSrcImg, fileName="+fileName);  
        try {
        	//fileName = "1507975964409file";
            String tPath  = uploadRootUri + type + File.separator+ fileName;  
            byte data[] = AikFileUtils.getFileContent(tPath);     
            response.setContentType("image/jpg"); //设置返回的文件类型       
            OutputStream os = response.getOutputStream();      
            os.write(data);      
            os.flush();  
            os.close();  
        } catch (Exception e) {      
            e.printStackTrace();      
        }     
        System.out.println("test");  
    }  
      

    
      

    
    
    
    
    
    
}
