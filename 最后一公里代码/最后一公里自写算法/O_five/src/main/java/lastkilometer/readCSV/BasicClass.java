package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 保存处理用到的基本函数
 * */
public abstract class BasicClass {

	static InputStreamReader read = null;
	
	/**
	 * 读取文件里的数据
	 * 
	 * @param path
	 *            文件路径+文件名
	 * 
	 * @return BufferedReader
	 * 
	 * */
	public static BufferedReader read(String pathName) {
		File file = new File(pathName);
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			
			try {
				read = new InputStreamReader(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new BufferedReader(read);
		}
		return null;
	}

	/**
	 * 关闭流
	 * @throws IOException 
	 * */
	public static void close() throws IOException{
		read.close();
	}
	
	/**
	 * 获取当前目录下的文件名
	 * 
	 * @param path
	 *            当前目录路径
	 * */
	public static String[] getFileName(String path) {
		File file = new File(path);
		String[] fileName = file.list();
		return fileName;
	}

	/**
	 * 保存结果
	 * 
	 * @param path
	 *            保存路径
	 * @param name
	 *            保存的文件名
	 * @param record
	 *            一条记录
	 * */
	public static void save(String path, String name, String record) {

		String md = path + name + ".csv";
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(md, true));
			bw.write(record+ "\r\n");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * 创建文件
	 * 
	 * @param 需要创建的文件的路径和文件名
	 * */
	public static void createFile(String path, String name) {
		String file = path + name + ".txt";
		File zb = new File(file);
		if (!zb.exists()) {
			try {
				zb.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            创建的文件夹的路径
	 * 
	 * */
	public static void createFolder(String path) {

		File wjj = new File(path);
		if (!wjj.exists()) {
			wjj.mkdirs();
		}

	}
	
	/**
	 * 判断文件是否存在
	 * */
	public static boolean isExists(String pathAndName){
		File wjj = new File(pathAndName);
		if(wjj.exists()){
			return true;
		}else{
			return false;
		}
	}
}
