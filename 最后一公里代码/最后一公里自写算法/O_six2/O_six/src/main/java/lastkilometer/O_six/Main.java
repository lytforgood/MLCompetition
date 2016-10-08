package lastkilometer.O_six;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
/**
 * 最后一公里比赛
 * 
 * 2018-08-21
 * 
 * @author wangshaoshuai
 * */
import java.io.IOException;

import org.dom4j.DocumentException;

import lastkilometer.readCSV.BasicClass;

public class Main extends BasicClass{

	public static void main(String[] args) throws NumberFormatException, IOException, DocumentException {
		
		//配送
		//清除resultsSecond目录下的所有文件
		DelAllFile.delAllFile("results_cache/");
		//清除outputSecond目录下的所有文件
		DelAllFile.delAllFile("output/");
		
		int n=10;
		LastKilometer_one lk_one=new LastKilometer_one();
		int number=0;
		for(int i=1;i<=n;i++){
			number=lk_one.pickupAndDeliver(number,i);
		}
		
//		DelAllFile.delAllFile("results/");
//		int number_two=0;
//		LastKilometer_two lk_two=new LastKilometer_two();
//		for(int i=1;i<=1;i++){
//			number_two=lk_two.pickupAndDeliver(number_two,i);
//		}
		
//		//清除results目录下的所有文件
//		DelAllFile.delAllFile("results/");
//		LastKilometer_three lk_three=new LastKilometer_three();
//		lk_three.pickupAndDeliver(0);
		
		
//		for(int i=0;i<100;i++){
//			System.out.println("第"+(i+1)+"个...");
//			//清除results目录下的所有文件
//			DelAllFile.delAllFile("results/");
//			LastKilometer_three lk_three=new LastKilometer_three();
//			lk_three.pickupAndDeliver(0);
//			//将results里的文件移动到results_cache里
//			Main.copyFolder("results/","results_cache/");
//		}
		
	} 
	
	/**
	 * 移动文件夹的路径到另一个文件夹里
	 * */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			// 如果文件夹不存在，则建立新文件夹
			(new File(newPath)).mkdirs();
			// 读取整个文件夹的内容到file字符串数组，下面设置一个游标i，不停地向下移开始读这个数组
			File filelist = new File(oldPath);
			String[] file = filelist.list();
			// 要注意，这个temp仅仅是一个临时文件指针
			// 整个程序并没有创建临时文件
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				// 如果oldPath以路径分隔符/或者\结尾，那么则oldPath/文件名就可以了
				// 否则要自己oldPath后面补个路径分隔符再加文件名
				// 谁知道你传递过来的参数是f:/a还是f:/a/啊？
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				// 如果游标遇到文件
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(
							newPath + "/" + (temp.getName()).toString());
					byte[] bufferarray = new byte[1024 * 64];
					int prereadlength;
					while ((prereadlength = input.read(bufferarray)) != -1) {
						output.write(bufferarray, 0, prereadlength);
					}
					output.flush();
					output.close();
					input.close();
				}
				// 如果游标遇到文件夹
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
		}
	}

	public static boolean isEmpty(String path){
		 File file = new File(path);
	      if (file.isDirectory()) {
	         String[] files = file.list();
	         if (files.length > 0) {
	           return false;
	         }
	      }
	      return true;
	}
}
