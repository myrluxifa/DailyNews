package com.lvmq.util;

import java.io.File;
import java.sql.Time;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lvmq.base.Consts;



public class FileUtil {
	
	
	
	
	public static String uploadOneFile(MultipartFile file) {  
        // 判断文件是否为空  
        if (!file.isEmpty()) {  
            try {
            	String[] fileNameStr=file.getOriginalFilename().split("\\.");
            	String url=TimeUtil.today()+"/"+MD5.getMD5(fileNameStr[0])+"."+fileNameStr[1];
                // 文件保存路径  
                String filePath = Consts.file.URI+url;
                if(CreateFile.createImageDir(filePath)) {
                	// 转存文件
                    file.transferTo(new File(filePath));  
                    return "/img-uri/"+url;  
                }else {
                	//转存失败
                	return "-1";
                }
            } catch (Exception e) {  
                e.printStackTrace();  
                return "-2"; 
            }  
        }  else {
        	return "-3"; 
        }
    } 
}
