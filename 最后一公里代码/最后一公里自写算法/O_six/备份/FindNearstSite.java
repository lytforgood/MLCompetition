package lastkilometer.BerkeleyDB;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import com.sleepycat.je.DatabaseException;
import java.util.Map.Entry;

public class FindNearstSite {

	public Map<String, List<String>> find() throws DatabaseException, FileNotFoundException{
		BerkeleyDB read_Site_Site = new BerkeleyDB("distance/site_site_distance");//格式：Map<Site_id,Map<Site_id,distance>>
		Map<String, List<String>>  nearestSite=new TreeMap<>();
		Set<Entry<String, Object>> set=read_Site_Site.iteration();
		for(Entry<String, Object> entry:set){
			String Site_id_key=entry.getKey();
			@SuppressWarnings("unchecked")
			Map<String, Double> value=(Map<String, Double>) entry.getValue();
			int n=0;
			List<String> Site_id_value=new ArrayList<>();
			for(Entry<String, Double> e:value.entrySet()){
				if(n<10){//十个最近网点---50-4 80-8
					Site_id_value.add(e.getKey());
				}else{
					break;
				}
				n++;
			}
			nearestSite.put(Site_id_key, Site_id_value);
		}
		return nearestSite;
	}
}
