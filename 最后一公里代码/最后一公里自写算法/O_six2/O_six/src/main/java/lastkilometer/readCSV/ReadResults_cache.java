package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReadResults_cache extends BasicClass{

	public static void main(String[] args) throws IOException {
		new ReadResults_cache().readResultsFile();
	}
	
	/**
	 * 读取结果文件
	 * 
	 * @throws IOException
	 */
	public Map<String, List<List<List<Object>>>> readResultsFile() throws IOException {
		Map<String, List<List<List<Object>>>> post_res=new TreeMap<>();
		// 获取文件名
		String[] names = getFileName("results_cache/");
		for (String name : names) {
			// 将结果存入集合中
			BufferedReader br = read("results_cache/"+name);
			String line = null;
			List<String> list_zhen = new ArrayList<>();//统计小路径的取得的订单数
			List<List<Object>> list_two=new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] res = line.split(",");
				String Courier_id=res[0];
				String Addr = res[1];
				int arrTime = Integer.parseInt(res[2]);
				int endTime = Integer.parseInt(res[3]);
				int Amount = Integer.parseInt(res[4]);
				String Order_id = res[5];
				
				
				List<Object> list_three = new ArrayList<>();
				list_three.add(Addr);
				list_three.add(arrTime);
				list_three.add(endTime);
				list_three.add(Amount);
				list_three.add(Order_id);
				
				list_two.add(list_three);
				
				if(Amount>0){
					list_zhen.add(Order_id);
				}else{
					list_zhen.remove(list_zhen.indexOf(Order_id));
				}
				if(list_zhen.isEmpty()){//为空，表示一个小路径的结束
					
//					boolean bool=true;
//
//					//判断小路径是否全是电商订单
//					boolean bool_F=true;
//					for(List<Object> list_three_cahce:list_two){
//						String Order_id_cache = (String) list_three_cahce.get(4);
//						if("E".equals(Order_id_cache.substring(0, 1))){
//							bool_F=false;
//						}
//					}
//					if(bool_F){
//						bool=false;
//					}else{
//						for(List<Object> list_three_cahce:list_two){
//							int arrTime_cache = (int) list_three_cahce.get(1);
//							int endTime_cache = (int) list_three_cahce.get(2);
//							int Amount_cache = (int) list_three_cahce.get(3);
//							
//							if(Amount_cache>0){
//								if((endTime_cache-(arrTime_cache+Math.round(Math.sqrt(Amount_cache)*3+5)))>10){
//									bool=false;
//								}
////								if((endTime_cache-arrTime_cache)>100){
////									bool=false;
////								}
//							}
//						}
//					}
//					if(bool){
//						if(post_res.containsKey(Courier_id)){
//							List<List<List<Object>>> list_one = post_res.get(Courier_id);
//							list_one.add(list_two);
//							post_res.put(Courier_id, list_one);
//						}else{
//							List<List<List<Object>>> list_one = new ArrayList<>();
//							list_one.add(list_two);
//							post_res.put(Courier_id, list_one);
//						}
//					}
//				
////					List<Object> list_three_last=list_two.get(list_two.size()-1);
////					int endTime_last=(int) list_three_last.get(2);
////					
////					if(endTime_last>100&&list_two.size()>4){
////						if(bool){
////							if(post_res.containsKey(Courier_id)){
////								List<List<List<Object>>> list_one = post_res.get(Courier_id);
////								list_one.add(list_two);
////								post_res.put(Courier_id, list_one);
////							}else{
////								List<List<List<Object>>> list_one = new ArrayList<>();
////								list_one.add(list_two);
////								post_res.put(Courier_id, list_one);
////							}
////						}
////					}
					if(post_res.containsKey(Courier_id)){
						List<List<List<Object>>> list_one = post_res.get(Courier_id);
						list_one.add(list_two);
						post_res.put(Courier_id, list_one);
					}else{
						List<List<List<Object>>> list_one = new ArrayList<>();
						list_one.add(list_two);
						post_res.put(Courier_id, list_one);
					}
					list_two=new ArrayList<>();
				}
			}
		}	
		
		System.out.println("post_res.size():"+post_res.size());
		return post_res;
	}
}
