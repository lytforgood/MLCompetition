package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 根据订单id读取订单信息
 * */
public class ReadOrderFromOrder_id extends BasicClass{

	public static void main(String[] args) throws IOException {
		ReadOrderFromOrder_id readOFO=new ReadOrderFromOrder_id();
		Map<String,List<Object>> ofo=readOFO.readOrder();
		System.out.println(ofo);
	}
	 /**
	  * 根据订单id读取订单信息
	  *
	 * @throws IOException 
	 * 
	 *  @return  Map<Order_id,List<Objec>> list={Shop_id,Spot_id,Pickup_time,Delivery_time,Num} 
	  * */
	public Map<String,List<Object>> readOrder() throws IOException{
		 
		 //使用Map<String,List<Object>>来保存数据
		 Map<String,List<Object>> Order=new TreeMap<>();
		 
		//读取电商订单
		 BufferedReader brESO = read("read//new_4.csv");
		 String LineESO=null;
		 while((LineESO=brESO.readLine())!=null){
			 String[] res=LineESO.split(",");
			 String Order_id=res[0];
			 String Spot_id=res[1];
			 String Site_id=res[2];
			 int Num=Integer.parseInt(res[3]);
			 
			 List<Object> list=new ArrayList<>();
			 list.add(Spot_id);
			 list.add(Site_id);
			 list.add(Num);
			 
			 Order.put(Order_id, list);
		 }
		 
		 //读取同城O2O订单
		 BufferedReader brOO = read("read//new_5(修改).csv");
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
			 
			 Order.put(Order_id, list);
		 }
		return Order;
	 }
}
