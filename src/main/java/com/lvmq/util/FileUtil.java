package com.lvmq.util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Time;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lvmq.base.Consts;



public class FileUtil {
	
	
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
	
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
                    return Consts.file.IP+Consts.file.URI+url;  
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
	
public static String decryptByBase64(String base64) {  
	    
	    try {  
	    	String imgeUri = getUUID(); 
	    	URL url = null;  
	    	if(base64.indexOf(",")>0) {
	    		base64 = base64.split(",")[1];
	    	}
	    	byte[] b=org.apache.commons.codec.binary.Base64.decodeBase64(base64);
	    	 String fileUri=TimeUtil.today()+"/";
             // 文件保存路径  
             String filePath = Consts.file.URI+fileUri;
             String imageName = imgeUri + ".jpg";
             File file = new File(filePath);
             if(!file.exists()) {
             	file.mkdirs();
             }
             FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath+imageName));
             fileOutputStream.write(b);
             fileOutputStream.close(); 
             return  Consts.file.IP+fileUri+imageName;
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    	return "";
	    }  
	}  


public static void main(String[] args) {
	String s=decryptByBase64("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" + 
			"");
	System.out.println(s);
}
}




