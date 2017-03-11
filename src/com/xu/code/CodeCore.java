package com.xu.code;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;

/**
 * 编码转换项
 * @author xuzhenyao
 *
 */
public class CodeCore {
	/**
	 * 
	 * @param code 转换的编码
	 * @param file	目录
	 * @param fileType 文件类型
	 */
	public void toConcersion(String code,String file,String fileType){
		File filePath = new File(file);
		if(filePath.isFile()){
			fileToConcersion(codeString(file),code,filePath);
		}else{ 
			File[] filePaths = null;
			if(fileType != null && fileType.length() > 0){
				filePaths = filePath.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(fileType);
					}
				});
			}else{
				filePaths = filePath.listFiles();
			}
			if(filePaths != null && filePaths.length > 0){
				for(File file1 :filePaths){
//					System.out.println(file1.getName());
					fileToConcersion(codeString(file1.getAbsolutePath()),code,file1);
				}
			}
		}
	}
	
	/**
	 * 单个文件转换编码
	 * @param code
	 * @param file
	 */
	public void fileToConcersion(String startCode,String code,File file){
		String content = readFile(startCode,file);
		if(content != null ){
			wirteFile(code,file,content);
		}
		System.out.println(file.getAbsolutePath()+"从"+startCode+"到"+code+"转换完成");
	}
	
	public void wirteFile(String code,File file,String content){
		if(file.isFile()){
			FileOutputStream fileFis = null;
			try {
				content = URLDecoder.decode(content, code);
				fileFis = new FileOutputStream(file);
//				OutputStreamWriter inputStreamReader = new OutputStreamWriter(fileFis, code);
				fileFis.write(content.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(fileFis != null){
					try {
						fileFis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			throw new RuntimeException("不是一个文件");
		}
	}
	
	
	public String readFile(String startCode,File file){
		if(file.isFile()){
			StringBuilder sb = new StringBuilder();
			FileInputStream fileFis = null;
			try {
				fileFis = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fileFis, startCode);
				char[] chars = new char[1024];
				int index= 0;
				while((index = inputStreamReader.read(chars)) != -1){
					sb.append(new String(chars,0,index));
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(fileFis != null){
					try {
						fileFis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			throw new RuntimeException("不是一个文件");
		}
		return null;
	}
	
	
    /** 
     * 判断文件的编码格式 
     * @param fileName :file 
     * @return 文件编码格式 
     * @throws Exception 
     */  
    public String codeString(String fileName){  
		BufferedInputStream bin = null;
		String code = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(fileName));
			int p = (bin.read() << 8) + bin.read();
			switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(bin != null){
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return code;  
    }  
}
