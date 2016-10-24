package lastkilometer.O_four;

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

public class Main2 extends BasicClass{

	public static void main(String[] args) throws NumberFormatException, IOException, DocumentException {

		
		//配送
//		//清除resultsSecond目录下的所有文件
//		DelAllFile.delAllFile("results_cache/");
//		//清除outputSecond目录下的所有文件
//		DelAllFile.delAllFile("output/");
//		
//		int n=10;
//		LastKilometer_one lk_one=new LastKilometer_one();
//		int number=0;
//		for(int i=1;i<=n;i++){
//			number=lk_one.pickupAndDeliver(number,i);
//		}
		
//		int number_two=0;
//		LastKilometer_two lk_two=new LastKilometer_two();
//		for(int i=1;i<=1;i++){
//			number_two=lk_two.pickupAndDeliver(number_two,i);
//		}
		
		//清除results目录下的所有文件
		DelAllFile.delAllFile("results/");
		LastKilometer_three lk_three=new LastKilometer_three();
		lk_three.pickupAndDeliver(0);
		
//		//解析outputSecond目录下的文件得出结果
//		ParseXMLForPost_one pxps=new ParseXMLForPost_one();
//		List<String> un=pxps.getUnassignedO2OOrder("output/");
//	    for(String Order_id:un){
//	    	System.out.println(Order_id);
////	    	save("results/", "un", Order_id);
//	    }
	} 
}
