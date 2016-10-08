package lastkilometer.BerkeleyDB;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import com.sleepycat.je.DatabaseException;

import lastkilometer.readCSV.ReadCSV;

import java.util.Map.Entry;

public class FindNearstSite {
	
	public Map<String, List<String>> find() throws DatabaseException, IOException{
		ReadCSV readCSV=new ReadCSV();
		Map<String,Map<String,Map<String,Integer>>> eso=readCSV.readElectricitySuppliersOrder();
		Map<String,Map<String, Map<String, List<Object>>>> OO=readCSV.readO2OOrder();
		
		BerkeleyDB read_Site_Site = new BerkeleyDB("distance/site_site_distance");//格式：Map<Site_id,Map<Site_id,distance>>
		Map<String, List<String>>  nearestSite=new TreeMap<>();
		Set<Entry<String, Object>> set=read_Site_Site.iteration();
		for(Entry<String, Object> entry:set){
			String Site_id_key=entry.getKey();
			@SuppressWarnings("unchecked")
			Map<String, Double> value=(Map<String, Double>) entry.getValue();
			int num=0;
			List<String> Site_id_value=new ArrayList<>();
			for(Entry<String, Double> e:value.entrySet()){
				//根据网点id计算订单数量
				Map<String, Map<String, Integer>> eso_two=eso.get(e.getKey());
				for(Entry<String, Map<String, Integer>> eso_two_entry:eso_two.entrySet()){
					num+=eso_two_entry.getValue().size();
				}
				Map<String, Map<String, List<Object>>> OO_two=OO.get(e.getKey());
				if(OO_two!=null){
					for(Entry<String, Map<String, List<Object>>> OO_two_entry:OO_two.entrySet()){
						num+=OO_two_entry.getValue().size();
					}
				}
				if(num<4500){//4500个订单，按最近网点取
					Site_id_value.add(e.getKey());
				}else{
					break;
				}
			}
			nearestSite.put(Site_id_key, Site_id_value);
		}
		return nearestSite;
	}
	
//	public Map<String, List<String>> find() throws DatabaseException, FileNotFoundException{
//		BerkeleyDB read_Site_Site = new BerkeleyDB("distance/site_site_distance");//格式：Map<Site_id,Map<Site_id,distance>>
//		Map<String, List<String>>  nearestSite=new TreeMap<>();
//		Set<Entry<String, Object>> set=read_Site_Site.iteration();
//		for(Entry<String, Object> entry:set){
//			String Site_id_key=entry.getKey();
//			@SuppressWarnings("unchecked")
//			Map<String, Double> value=(Map<String, Double>) entry.getValue();
//			int n=0;
//			List<String> Site_id_value=new ArrayList<>();
//			for(Entry<String, Double> e:value.entrySet()){
//				if(n<50){//十个最近网点
//					Site_id_value.add(e.getKey());
//				}else{
//					break;
//				}
//				n++;
//			}
//			nearestSite.put(Site_id_key, Site_id_value);
//		}
//		return nearestSite;
//	}
}
