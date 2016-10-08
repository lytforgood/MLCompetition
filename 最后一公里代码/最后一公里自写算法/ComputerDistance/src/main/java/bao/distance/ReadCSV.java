package bao.distance;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ReadCSV extends BasicClass{

	/**
	 * 读取网点id及其经纬度
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * */
	 Map<String, double[]> readSite_Lng_Lat() throws NumberFormatException, IOException{

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
		return site_Lng_Lat;
	}
	
	/**
	 * 读取电商配送点id及其经纬度
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	 Map<String, double[]> readSpot_Lng_Lat() throws NumberFormatException, IOException {
		
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
		return spot_Lng_Lat;
	}
	
	/**
	 * 读取O2O商户id及其经纬度
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * */
	 Map<String, double[]> readShop_Lng_Lat() throws NumberFormatException, IOException {
		
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
		return shop_Lng_Lat;
	}
}
