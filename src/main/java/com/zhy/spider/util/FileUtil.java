package com.zhy.spider.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static boolean mkdirForder(String forderPath){
		File file = new File(forderPath); 
		if(file.exists()){
			if(file.isDirectory()){
				return true;
			}else{
				if(file.delete()){
					try {
						FileUtils.forceMkdir(file);
					} catch (IOException e) {
						logger.error("创建文件失败",e);
						return false;
					}
				}else{
					return false;
				}
				return true;
			}
		}else{
			try {
				FileUtils.forceMkdir(file);
			} catch (IOException e) {
				logger.error("创建文件失败",e);
				return false;
			}
			return true;
		}
	}
	
	 public static boolean createFile(String destFileName) {
	        File file = new File(destFileName);
	        if(file.exists()) {
	            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
	            return false;
	        }
	        if (destFileName.endsWith(File.separator)) {
	            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
	            return false;
	        }
	        //判断目标文件所在的目录是否存在
	        if(!file.getParentFile().exists()) {
	            //如果目标文件所在的目录不存在，则创建父目录
	            System.out.println("目标文件所在目录不存在，准备创建它！");
	            if(!file.getParentFile().mkdirs()) {
	                System.out.println("创建目标文件所在目录失败！");
	                return false;
	            }
	        }
	        //创建目标文件
	        try {
	            if (file.createNewFile()) {
	                System.out.println("创建单个文件" + destFileName + "成功！");
	                return true;
	            } else {
	                System.out.println("创建单个文件" + destFileName + "失败！");
	                return false;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
	            return false;
	        }
	 }
}
