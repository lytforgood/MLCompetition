package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReadCSVForParse extends BasicClass{

	
	 /**
	  *  读取电商订单，共9214笔电商订单，总包裹量为229780 
	  *  
	 * @throws IOException 
	 * 
	 * @return Map<Order_id,List<Object>> list={Site_id,Spot_id,Num}
	 * 
	  * */
	public Map<String,List<Object>> readElectricitySuppliersOrder() throws IOException{
		 
		 //读取电商订单
		 BufferedReader brESO = read("read//new_4.csv");
		 
		 //使用 Map<String,List<Object>>来保存数据
		 Map<String,List<Object>> eso=new TreeMap<>();
		 
		 String lineESO=null;
		 while((lineESO=brESO.readLine())!=null){
			 String[] res=lineESO.split(",");
			 int Num=Integer.parseInt(res[3]);
			 String Site_id=res[2];
			 String Spot_id=res[1];
			 String Order_id=res[0];
			 
			 List<Object> list=new ArrayList<>();
			 list.add(Site_id);
			 list.add(Spot_id);
			 list.add(Num);
			 
			 eso.put(Order_id, list);
		 }
		 brESO.close();
		//关闭InputStreamReader流
		close();
		return eso;
	 }
	 
	 /**
	  * 读取同城O2O订单,共3273笔O2O订单，总包裹量为8856
	  *
	 * @throws IOException 
	 * 
	 *  @return  Map<Shop_id,List<Objec>> list={Shop_id,Spot_id,Pickup_time,Delivery_time,Num} 
	  * */
	public Map<String,List<Object>> readO2OOrder() throws IOException{
		 
		 //读取同城O2O订单
		 BufferedReader brOO = read("read//new_5(修改).csv");
		 
		 //使用Map<String,List<Object>>来保存数据
		 Map<String,List<Object>> OO=new TreeMap<>();
		 
		 String LineOO=null;
		 while((LineOO=brOO.readLine())!=null){
			 String[] res=LineOO.split(",");
			 String Order_id=res[0];
			 String Spot_id=res[1];
			 String Shop_id=res[2];
			 
			 int Num=Integer.parseInt(res[5]);
			 
			 List<Object> list=new ArrayList<>();
			 list.add(Shop_id);
			 list.add(Spot_id);
			 list.add(Num);
			 OO.put(Order_id, list);
		 }
		 brOO.close();
		//关闭InputStreamReader流
		close();
		return OO;
	 }
}
