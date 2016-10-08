package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 读取第二次配送时需要的数据
 * */
public class ReadO2OFromOrder_id extends BasicClass{

	 /**
	  * 读取同城O2O订单,根据订单号读取其它信息
	  *
	 * @throws IOException 
	 * 
	 *  @return  Map<Order_id,List<Objec>> list={Shop_id,Spot_id,Pickup_time,Delivery_time,Num} 
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
			 
			 int Pickup_time=Integer.parseInt(res[3]);
			 int Delivery_time=Integer.parseInt(res[4]);
			 
			 int Num=Integer.parseInt(res[5]);
			 
			 List<Object> list=new ArrayList<>();
			 list.add(Spot_id);
			 list.add(Shop_id);
			 list.add(Pickup_time);
			 list.add(Delivery_time);
			 list.add(Num);
			 
			 OO.put(Order_id, list);
		 }
		return OO;
	 }
}
