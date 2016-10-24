package com.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;



public class SplitMap {
	// 按行读取 以行为单位读取文件内容，一次读一整行：类似于map程序
	public static void wordcount(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		// 一次读入一行，直到读入null为文件结束
		while ((tempString = reader.readLine()) != null) {
			// 显示行号
			StringTokenizer stringTokenizer = new StringTokenizer(tempString);
			// 遍历
			while (stringTokenizer.hasMoreElements()) {
				// 获取每个值
				String wordValue = stringTokenizer.nextToken();
				
				String[] array = wordValue.toString().split("\\+");
				if (array.length<=2) {
					System.out.println("xxxxxxx"+wordValue.toString());
				}else {
				for (String str : array) {
//					String[] ar = str.split("\\+");
					WriteFile.writeFileByLines("E:/笔记学习/天池比赛/阿里云安全算法/第一赛季数据/25w_url2.txt", str);
				}
				}
				
				
				
				// 设置map输出的key值
				//WriteFile.writeFileByLines("D:/b.txt", wordValue + "," + "1");
			}

			// System.out.println("line " + line + ": " + tempString);
			line++;
		}
		reader.close();

	}

	public static void main(String[] args) throws IOException {
		wordcount("E:/笔记学习/天池比赛/阿里云安全算法/第一赛季数据/25Wurl2.txt");
	}

}
