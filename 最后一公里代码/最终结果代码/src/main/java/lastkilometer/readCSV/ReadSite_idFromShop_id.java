package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 根据Shop_id判断该订单属于哪个网点
 * */
public class ReadSite_idFromShop_id extends BasicClass{

	/**
	 * 读取商户最近网点距离文件（判断O2O商户属于哪个网点）
	 * @throws IOException 
	 * */
	public Map<String, String> readSite_idFromShop_id() throws IOException{
		
		//读取商户最近网点距离文件
		 BufferedReader brOIB = read("read//商户最近网点.csv");// 商户最近网点距离.csv
		 
		 //使用Map<Shop_id,Site_id>来保存数据
		 Map<String, String> OIB=new TreeMap<>();
		 
		 String lineOIB=null;
		 while((lineOIB=brOIB.readLine())!=null){
			 String[] res=lineOIB.split(",");
			 String Site_id=res[1];
			 String Shop_id=res[0];
			 OIB.put(Shop_id, Site_id);
		 }
		 brOIB.close();
		//关闭InputStreamReader流
		close();
		return OIB;
	}
}
