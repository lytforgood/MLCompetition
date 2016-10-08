package bao.distance;

import java.util.*;

/**
 * 根据 HashMap 的 value 进行排序
 * 
 */
public class MapSortByValue {

	/**
	 * 对根据value值进行降序排序
	 */
	public static Map<String, Double> sortByValue(Map<String, Double> datas) {
		
		Map<String, Double> res=new LinkedHashMap<String, Double>();
		
    	ByValueComparator bvc = new ByValueComparator(datas);
         
 		List<String> keys = new ArrayList<String>(datas.keySet());
 		Collections.sort(keys, bvc);
 		for (String key : keys) {
 			res.put(key, datas.get(key));
 		}
 		
 		return res;
	}

	private static class ByValueComparator implements Comparator<String> {
		Map<String, Double> base_map;

		public ByValueComparator(Map<String, Double> base_map) {
			this.base_map = base_map;
		}

		public int compare(String arg0, String arg1) {
			if (!base_map.containsKey(arg0) || !base_map.containsKey(arg1)) {
				return 0;
			}

			if (base_map.get(arg0) > base_map.get(arg1)) {
				return 1;
			} else if (base_map.get(arg0) == base_map.get(arg1)) {
				return 0;
			} else {
				return -1;
			}
		}
	}

}