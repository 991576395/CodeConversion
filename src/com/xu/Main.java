package com.xu;

import java.io.File;

import com.xu.code.CodeCore;

/**
 * 起动项
 * @author xuzhenyao
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		CodeCore codeCore = new CodeCore();
		String paht = "/media/xuzhenyao/wendang/linux/opencv/fruit-recognition/fruit-identification/";
//		System.out.println(codeCore.readFile(codeCore.codeString(paht), new File(paht)));
		
//		codeCore.wirteFile("UTF-8", new File(paht), "你好");
//		codeCore.fileToConcersion(codeCore.codeString(paht),"UTF-8",new File(paht));
		codeCore.toConcersion("UTF-8", paht, "h");
	}
}
