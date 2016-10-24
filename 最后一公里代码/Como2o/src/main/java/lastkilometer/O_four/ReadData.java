package lastkilometer.O_four;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import lastkilometer.readCSV.BasicClass;
import lastkilometer.readCSV.ReadCSV;

public abstract class ReadData extends BasicClass{

	ReadCSV readCSV=new ReadCSV();
	Map<String, double[]> Site_Lng_Lat=null;//网点经纬度
	Map<String, double[]> Spot_Lng_Lat=null;//配送点经纬度
	Map<String, double[]> Shop_Lng_Lat=null;//商户经纬度
	Map<String, Map<String, Map<String, Integer>>> ESO=null;//读取电商订单
	Map<String, Map<String, Map<String, List<Object>>>> OO=null;//读取O2O订单
	Map<String,List<String>> OIB=null;//读取离网点最近商户
	public ReadData() throws NumberFormatException, IOException{
		Site_Lng_Lat=readCSV.readSite_Lng_Lat();//读取网点经纬度
		Spot_Lng_Lat=readCSV.readSpot_Lng_Lat();//读取配送点经纬度
		Shop_Lng_Lat=readCSV.readShop_Lng_Lat();//读取商户的经纬度
		//读取电商订单
		ESO=readCSV.readElectricitySuppliersOrder();// Map<Site_id,Map<Spot_id,Map<Order_id,Num>>>
		//读取O2O订单
		OO=readCSV.readO2OOrder();//Map<Shop_id,Map<Spot_id,Map<Order_id,List>>> List.get(0)=Pickup_time List.get(1)=Delivery_time List.get(2)=Num
		//读取离网点最近商户
		OIB=readCSV.readO2OIsBelong();
	}
}
