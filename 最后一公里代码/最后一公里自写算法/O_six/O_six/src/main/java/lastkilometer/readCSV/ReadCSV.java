package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReadCSV extends BasicClass{

	/**
	 * 读取网点id及其经纬度，供124个网点
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * 
	 * @return Map<Site_id,经纬度>
	 * */
	public Map<String, double[]> readSite_Lng_Lat() throws NumberFormatException, IOException{

		//读取网点id经纬度
		BufferedReader brSite=read("read//new_1.csv");
		
		//使用Map<Site_id,double[2]>来保存读取的数据
		Map<String, double[]> site_Lng_Lat=new TreeMap<>();
		
		String lineSite=null;
		while((lineSite=brSite.readLine())!=null){
			String[] res=lineSite.split(",");
			String Site_id=res[0];//网点id（e.g. A001） 
			double Lng=Double.parseDouble(res[1]);//网点经度
			double Lat=Double.parseDouble(res[2]);//网点纬度
			
			double[] Lng_Lat=new double[2];
			Lng_Lat[0]=Lng;
			Lng_Lat[1]=Lat;
			
			site_Lng_Lat.put(Site_id, Lng_Lat);
		}
		brSite.close();
		//关闭InputStreamReader流
		close();
		return site_Lng_Lat;
	}
	
	/**
	 * 读取电商配送点id及其经纬度，共9214个配送点
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * 
	 * @return Map<Spot_id, double[]>
	 */
	public Map<String, double[]> readSpot_Lng_Lat() throws NumberFormatException, IOException {
		
		//读取电商配送点id及其经纬度
		BufferedReader brSpot = read("read//new_2.csv");

		// 使用Map<Spot_id,double[2]>来保存读取的数据
		Map<String, double[]> spot_Lng_Lat = new TreeMap<>();

		String lineSpot = null;
		while ((lineSpot = brSpot.readLine()) != null) {
			String[] res = lineSpot.split(",");
			String Spot_id = res[0];// 配送点id （e.g. B0001） 
			double Lng = Double.parseDouble(res[1]);//配送点经度
			double Lat = Double.parseDouble(res[2]);// 配送点纬度
			
			double[] Lng_Lat = new double[2];
			Lng_Lat[0] = Lng;
			Lng_Lat[1] = Lat;
			
			spot_Lng_Lat.put(Spot_id, Lng_Lat);
		}
		brSpot.close();
		//关闭InputStreamReader流
		close();
		return spot_Lng_Lat;
	}
	
	/**
	 * 读取O2O商户id及其经纬度，共598个商户
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * 
	 * @return Map<Shop_id, 经纬度>
	 * */
	public Map<String, double[]> readShop_Lng_Lat() throws NumberFormatException, IOException {
		
        // 读取O2O商户id及其经纬度
		BufferedReader brShop = read("read//new_3.csv");

		// 使用Map<Shop_id,double[2]>来保存读取的数据
		Map<String, double[]> shop_Lng_Lat = new TreeMap<>();

		String lineShop = null;
		while ((lineShop = brShop.readLine()) != null) {
			String[] res = lineShop.split(",");
			String Shop_id = res[0];//  商户id （e.g. S001） 
			double Lng = Double.parseDouble(res[1]);// 商户经度
			double Lat = Double.parseDouble(res[2]);// 商户纬度
			
			double[] Lng_Lat = new double[2];
			Lng_Lat[0] = Lng;
			Lng_Lat[1] = Lat;
			
			shop_Lng_Lat.put(Shop_id, Lng_Lat);
		}
		brShop.close();
		//关闭InputStreamReader流
		close();
		return shop_Lng_Lat;
	}
	 
	 /**
	  *  读取电商订单，共9214笔电商订单，总包裹量为229780 
	  *  
	 * @throws IOException 
	 * 
	 * @return Map<Site_id,Map<Spot_id,Map<Order_id,Num>>>
	  * */
	public Map<String,Map<String,Map<String,Integer>>> readElectricitySuppliersOrder() throws IOException{
		 
		 //读取电商订单
		 BufferedReader brESO = read("read//new_4.csv");
		 
		 //使用Map<String,Map<String,Map<String,Double>>>来保存数据
		 Map<String,Map<String,Map<String,Integer>>> eso=new TreeMap<>();
		 
		 String lineESO=null;
		 while((lineESO=brESO.readLine())!=null){
			 String[] res=lineESO.split(",");
			 int Num=Integer.parseInt(res[3]);
			 String Site_id=res[2];
			 String Spot_id=res[1];
			 String Order_id=res[0];
			 
			 if(eso.containsKey(Site_id)){
				 Map<String, Map<String,Integer>> two=eso.get(Site_id);
				 if(two.containsKey(Spot_id)){
					 
					 //取第三个Map
					 Map<String, Integer> three=two.get(Spot_id);
					 three.put(Order_id, Num);//添加订单
					
					 //存入第二个Map
					 two.put(Spot_id, three);
				 }else{
					 Map<String, Integer> three=new TreeMap<>();
					 three.put(Order_id, Num);
					 
					//存入第二个Map
					 two.put(Spot_id, three);
				 }
				 
				 //存入第一个Map
				 eso.put(Site_id, two);
			 }else{
				 Map<String, Integer> three=new TreeMap<>();
				 three.put(Order_id, Num);
				 Map<String, Map<String,Integer>> two=new TreeMap<>();
				//存入第二个Map
				 two.put(Spot_id, three);
				 //存入第一个Map
				 eso.put(Site_id, two);
			 }
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
	 *  @return  Map<Shop_id,Map<Spot_id,Map<Order_id,List>>> List.get(0)=Pickup_time List.get(1)=Delivery_time List.get(2)=Num
	  * */
	public Map<String,Map<String, Map<String, List<Object>>>> readO2OOrder() throws IOException{
		 
		 //读取同城O2O订单
		 BufferedReader brOO = read("read//new_5(修改).csv");
		 
		 //使用Map<String,Map<String, Map<String, List<Object>>>>来保存数据
		 Map<String,Map<String, Map<String, List<Object>>>> OO=new TreeMap<>();
		 
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
			 list.add(Pickup_time);
			 list.add(Delivery_time);
			 list.add(Num);
			 
			 if(OO.containsKey(Shop_id)){
				 Map<String, Map<String, List<Object>>> two=OO.get(Shop_id);
				 if(two.containsKey(Spot_id)){
					 
					 Map<String, List<Object>> three=two.get(Spot_id);
					 three.put(Order_id, list);
					 
					 //存入第二个Map
					 two.put(Spot_id, three);
				 }else{
					 
					 Map<String, List<Object>> three=new TreeMap<>();
					 three.put(Order_id, list);
					 
					 //存入第二个Map
					 two.put(Spot_id, three);
				 }
				 
				 //存入第一个Map
				 OO.put(Shop_id, two);
			 }else{
				 
				 Map<String, List<Object>> three=new TreeMap<>();
				 three.put(Order_id, list);
				 
				 Map<String, Map<String, List<Object>>> two=new TreeMap<>();
				 two.put(Spot_id, three);
				 
				 OO.put(Shop_id, two);
			 }
		 }
		 brOO.close();
		//关闭InputStreamReader流
		close();
		return OO;
	 }
	
	/**
	 * 读取商户最近网点距离文件（判断O2O商户属于哪个网点）
	 * @throws IOException 
	 * */
	public Map<String, List<String>> readO2OIsBelong() throws IOException{
		
		//读取商户最近网点距离文件
		 BufferedReader brOIB = read("read//商户最近网点.csv");// 商户最近网点距离.csv
		 
		 //使用Map<Site_id,List<Shop_id>>来保存数据
		 Map<String, List<String>> OIB=new TreeMap<>();
		 
		 String lineOIB=null;
		 while((lineOIB=brOIB.readLine())!=null){
			 String[] res=lineOIB.split(",");
			 String Site_id=res[1];
			 String Shop_id=res[0];
			 
			 if(OIB.containsKey(Site_id)){
				 List<String> list=OIB.get(Site_id);
				 list.add(Shop_id);
				 OIB.put(Site_id, list);
			 }else{
				 List<String> list=new ArrayList<>();
				 list.add(Shop_id);
				 OIB.put(Site_id, list);
			 }
		 }
		 brOIB.close();
		//关闭InputStreamReader流
		close();
		return OIB;
	}
}
