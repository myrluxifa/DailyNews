package com.lvmq.util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.lvmq.base.Consts;



public class FileUtil {
	
	
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
	
//	public static String uploadOneFile(MultipartFile file) {  
//        // 判断文件是否为空  
//        if (!file.isEmpty()) {  
//            try {
//            	String[] fileNameStr=file.getOriginalFilename().split("\\.");
//            	String url=TimeUtil.today()+"/"+MD5.getMD5(fileNameStr[0])+"."+fileNameStr[1];
//                // 文件保存路径  
//                String filePath = Consts.file.URI+url;
//                if(CreateFile.createImageDir(filePath)) {
//                	// 转存文件
//                    file.transferTo(new File(filePath));  
//                    return Consts.file.IP+Consts.file.URI+url;  
//                }else {
//                	//转存失败
//                	return "-1";
//                }
//            } catch (Exception e) {  
//                e.printStackTrace();  
//                return "-2"; 
//            }  
//        }  else {
//        	return "-3"; 
//        }
//    } 
	
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



}




