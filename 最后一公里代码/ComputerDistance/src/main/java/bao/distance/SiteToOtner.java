package bao.distance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import bao.BerkeleyDB.BerkeleyDB;

/**
 * 计算网点到其他（电商配送点、O2O商户）的距离
 * */
public class SiteToOtner{
	
	/**
	 * 计算各个网点到各个电商配送点和O2O商户的距离并存入Berkeley DB数据库
	 * 
	 * @param site_Lng_Lat 网点id及其经纬度
	 * @param spot_Lng_Lat 配送点id及其经纬度
	 * @param shop_Lng_Lat O2O商户id及其经纬度
	 * 
	 * @throws IOException 
	 * */
	public void site_Dist_SpotAndShop(Map<String, double[]> site_Lng_Lat,Map<String, double[]> spot_Lng_Lat,Map<String, double[]> shop_Lng_Lat,String site_savePath) throws IOException{
		
		//将数据存入数据库
		BerkeleyDB save_Site_Site = new BerkeleyDB(site_savePath+"site_site_distance");//格式：Map<Site_id,Map<Site_id,distance>>
		BerkeleyDB save_Site_Spot = new BerkeleyDB(site_savePath+"site_spot_distance");//格式：Map<Site_id,Map<Spot_id,distance>>
		BerkeleyDB save_Site_Shop = new BerkeleyDB(site_savePath+"site_shop_distance");//格式：Map<Site_id,Map<Shop_id,distance>>
		BerkeleyDB save_Site_SiteAndSpotAndShop = new BerkeleyDB(site_savePath+"site_siteandspotandshop_distance");//格式：Map<Site_id,Map<Site_id OR Spot_id OR Shop_id,distance>>
		
		for(Entry<String, double[]> siteEntry:site_Lng_Lat.entrySet()){
			String Site_id=siteEntry.getKey();//网点id（e.g. A001） 
			double[] Lng_Lat_Site=siteEntry.getValue();
			double lng1=Lng_Lat_Site[0];//网点经度
			double lat1=Lng_Lat_Site[1];//网点纬度
			
			HashMap<String, Double> cacheAll=new HashMap<>();
			
			//计算网点到网点的距离，并存储
			HashMap<String, Double> cacheSite_Site=new HashMap<>();
			for(Entry<String, double[]> siteEntry2:site_Lng_Lat.entrySet()){
				String Site_id2 = siteEntry2.getKey();//网点id（e.g. A001）
				double[] Lng_Lat_Site2=siteEntry2.getValue();
				double lng2 = Lng_Lat_Site2[0];//网点经度
				double lat2 = Lng_Lat_Site2[1];//网点纬度
				
				double distance=computeDistance(lng1, lat1, lng2, lat2);
				cacheSite_Site.put(Site_id2,distance);
				cacheAll.put(Site_id2,distance);
			}
			//存储网点到网点的距离
			save_Site_Site.putData(Site_id, MapSortByValue.sortByValue(cacheSite_Site));//按距离降序排序
			
			//计算网点到各个配送点的距离，并存储
			HashMap<String, Double> cacheSite_Spot=new HashMap<>();
			for(Entry<String, double[]> spotEntry:spot_Lng_Lat.entrySet()){
				String Spot_id = spotEntry.getKey();// 配送点id （e.g. B0001） 
				double[] Lng_Lat_Spot=spotEntry.getValue();
				double lng2 = Lng_Lat_Spot[0];//配送点经度
				double lat2 = Lng_Lat_Spot[1];// 配送点纬度
				
				double distance=computeDistance(lng1, lat1, lng2, lat2);
				cacheSite_Spot.put(Spot_id,distance);
				cacheAll.put(Spot_id,distance);
			}
			//存储网点到配送点的距离
			save_Site_Spot.putData(Site_id, MapSortByValue.sortByValue(cacheSite_Spot));//按距离降序排序
			
			// 计算网点到各个O2O商户的距离，并存储
			HashMap<String, Double> cacheSite_Shop=new HashMap<>();
			for (Entry<String, double[]> shopEntry : shop_Lng_Lat.entrySet()) {
				String Shop_id = shopEntry.getKey();//  商户id （e.g. S001） 
				double[] Lng_Lat_Shop = shopEntry.getValue();
				double lng2 = Lng_Lat_Shop[0];//商户经度
				double lat2 = Lng_Lat_Shop[1];//商户纬度
				
				double distance=computeDistance(lng1, lat1, lng2, lat2);
				cacheSite_Shop.put(Shop_id, distance);
				cacheAll.put(Shop_id, distance);
			}
			// 存储网点到O2O商户的距离
			save_Site_Shop.putData(Site_id, MapSortByValue.sortByValue(cacheSite_Shop));//按距离降序排序
			
			//存储网点到配送点和O2O商户的距离
			save_Site_SiteAndSpotAndShop.putData(Site_id, MapSortByValue.sortByValue(cacheAll));//按距离降序排序
		}
		save_Site_Site.close();
		save_Site_Spot.close();
		save_Site_Shop.close();
		save_Site_SiteAndSpotAndShop.close();
	}
	
	
	/**
	 * 根据经纬度计算距离
	 * 
	 */
	private double computeDistance(double lng1, double lat1, double lng2, double lat2) {

		double lat = (lat1 - lat2) / 2.0;
		double lng = (lng1 - lng2) / 2.0;
		double cache = Math.sin(Math.PI / 180 * lat) * Math.sin(Math.PI / 180 * lat) + Math.cos(Math.PI / 180 * lat1)
				* Math.cos(Math.PI / 180 * lat2) * Math.sin(Math.PI / 180 * lng) * Math.sin(Math.PI / 180 * lng);

		double R = 6378137;
		double S = 2 * R * Math.asin(Math.sqrt(cache));
		
		return S;
	}
}
