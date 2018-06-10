package com.lvmq.util;

import java.io.File;
import java.io.IOException;

public class CreateFile {
	
	public static boolean createImageDir(String fileUri) {
			File file = new File(fileUri);
			//目标已存在
			if(file.exists()) {  
		          return false;  
		    }
			//目标文件不能为目录
		    if (fileUri.endsWith(File.separator)) {  
		          return false;  
		    }
		    //判断目标文件所在的目录是否存在  
	        if(!file.getParentFile().exists()) {  
	            //如果目标文件所在的目录不存在，则创建父目录  
	            if(!file.getParentFile().mkdirs()) {
	            	//失败则返回false
	                return false;
	            }else {
	            	//目标目录创建成功
	            	return true;
	            }
	        }else {
	        	return true;
	        }
	     
	}
}
