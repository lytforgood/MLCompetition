package bao.distance;

import java.io.IOException;
import java.util.Map;

/**
 * 计算网点、配送点、O2O商店两两之间的距离
 * */
public class DistanceWithEcheOther {

	public static void main(String[] args) throws IOException {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");//解决“比较器发生错误”问题
		DistanceWithEcheOther.saveData();
	}
	
	/**
	 * 计算网点、配送点、O2O商店两两之间的距离并存入Berkeley DB数据库
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * */
	public static void saveData() throws NumberFormatException, IOException{
		
		//读取源数据
		ReadCSV rc=new ReadCSV();
		Map<String, double[]> site_Lng_Lat=rc.readSite_Lng_Lat();
		Map<String, double[]> spot_Lng_Lat=rc.readSpot_Lng_Lat();
		Map<String, double[]> shop_Lng_Lat=rc.readShop_Lng_Lat();
		
//		//计算各个网点到各个电商配送点和O2O商户的距离并存入Berkeley DB数据库
		String site_savePath="save//site//";
		new SiteToOtner().site_Dist_SpotAndShop(site_Lng_Lat, spot_Lng_Lat, shop_Lng_Lat, site_savePath);
		
		//计算各个电商配送点到各个网点和O2O商户的距离并存入Berkeley DB数据库
//		String spot_savePath="save//spot//";
//		new SpotToOther().spot_Dist_SiteAndShop(site_Lng_Lat, spot_Lng_Lat, shop_Lng_Lat, spot_savePath);
		
//		//计算各个O2O商户到各个电商配送点和网点的距离并存入Berkeley DB数据库
//		String shop_savePath="save//shop//";
//		new ShopToOther().shop_Dist_SiteAndSpot(site_Lng_Lat, spot_Lng_Lat, shop_Lng_Lat, shop_savePath);
	}
}
