package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReadResults_cache extends BasicClass{

	/**
	 * 读取结果文件
	 * 
	 * @throws IOException
	 */
	public Map<String, List<List<List<Object>>>> readResultsFile() throws IOException {
		Map<String, List<List<List<Object>>>> post_res=new TreeMap<>();
		
		// 将结果存入集合中
		BufferedReader br = read("results_cache/post_last.csv");
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
				boolean bool=true;
				for(List<Object> list_three_cahce:list_two){
					int arrTime_cache = (int) list_three_cahce.get(1);
					int endTime_cache = (int) list_three_cahce.get(2);
					//100---50
					if((endTime_cache-arrTime_cache)>100){
						bool=false;
					}
				}
				int last_Amount=(int) list_two.get(list_two.size()-1).get(3);
				int last_last_Amount=(int) list_two.get(list_two.size()-2).get(3);
				if(last_Amount>0&&last_last_Amount<0){
					int last_arrTime=(int) list_two.get(list_two.size()-1).get(1);
					int last_last_endTime=(int) list_two.get(list_two.size()-2).get(2);
					if((last_arrTime-last_last_endTime)>10){
						bool=false;
					}
				}
//				//去掉最后一个endTime时间少于指定时间的小路径
//				int last_endTime=(int) list_two.get(list_two.size()-1).get(2);
//				if(last_endTime>12*10&&last_endTime<20*60){
//					bool=false;
//				}
				if(bool){
					if(post_res.containsKey(Courier_id)){
						List<List<List<Object>>> list_one = post_res.get(Courier_id);
						list_one.add(list_two);
						post_res.put(Courier_id, list_one);
					}else{
						List<List<List<Object>>> list_one = new ArrayList<>();
						list_one.add(list_two);
						post_res.put(Courier_id, list_one);
					}
				}
				list_two=new ArrayList<>();
			}
		}
		System.out.println("post_res.size():"+post_res.size());
		return post_res;
	}
	
//	/**
//	 * 读取结果文件
//	 * 
//	 * @throws IOException
//	 */
//	public List<List<List<Object>>> readResultsFile() throws IOException {
//		
//		List<String> unSet=new ArrayList<>();
//		
//		List<List<List<Object>>> post_res = new ArrayList<>();
//		// 将结果存入集合中
//		BufferedReader br = read("results_cache/post_last.csv");
//		String line = null;
//		List<String> list_zhen = new ArrayList<>();//统计小路径的取得的订单数
//		List<List<Object>> list_one=new ArrayList<>();
//		while ((line = br.readLine()) != null) {
//			String[] res = line.split(",");
//			String Addr = res[1];
//			int arrTime = Integer.parseInt(res[2]);
//			int endTime = Integer.parseInt(res[3]);
//			int Amount = Integer.parseInt(res[4]);
//			String Order_id = res[5];
//			
//			
//			List<Object> list_two = new ArrayList<>();
//			list_two.add(Addr);
//			list_two.add(arrTime);
//			list_two.add(endTime);
//			list_two.add(Amount);
//			list_two.add(Order_id);
//			
//			
//			
//			if(Amount>0){
//				if((endTime-arrTime)!=0){
//					unSet.add(Order_id);
//				}else{
//					list_one.add(list_two);
//				}
//				list_zhen.add(Order_id);
//			}else{
//				if(!unSet.contains(Order_id)){
//					list_one.add(list_two);
//				}
//				list_zhen.remove(list_zhen.indexOf(Order_id));
//			}
//			if(list_zhen.isEmpty()){//为空，表示一个小路径的结束
////				boolean bool=true;
////				for(List<Object> list_two_cahce:list_one){
////					int arrTime_cache = (int) list_two_cahce.get(1);
////					int endTime_cache = (int) list_two_cahce.get(2);
////					if((endTime_cache-arrTime_cache)>100){
////						bool=false;
////					}
////				}
////				if(bool){
////					post_res.add(list_one);
////				}
//				if(!list_one.isEmpty()){
//					post_res.add(list_one);
//				}
//				list_one=new ArrayList<>();
//			}
//		}
//		return post_res;
//	}
	
	/**
	 * 读取results_cache数据
	 * @throws IOException 
	 * */
	public Map<String, List<List<Object>>> readResults_cahce() throws IOException{
		
		Map<String, List<List<Object>>> post_res=new TreeMap<>();
		// 获取xml文件名
		String[] names = getFileName("results_cache/");
		for (String name : names) {
			//读取数据
			BufferedReader br=read("results_cache/"+name);
			String line=null;
			while((line=br.readLine())!=null){
				String[] res=line.split(",");
				String Courier_id=res[0];
				String Addr = res[1];
				int arrTime = Integer.parseInt(res[2]);
				int endTime = Integer.parseInt(res[3]);
				int Amount = Integer.parseInt(res[4]);
				String Order_id = res[5];
				
				if(post_res.containsKey(Courier_id)){
					List<List<Object>> listFirst=post_res.get(Courier_id);
					
					List<Object> listSecond=new ArrayList<>();
					listSecond.add(Addr);
					listSecond.add(arrTime);
					listSecond.add(endTime);
					listSecond.add(Amount);
					listSecond.add(Order_id);
					
					listFirst.add(listSecond);
					post_res.put(Courier_id, listFirst);
				}else{
                    List<List<Object>> listFirst=new ArrayList<>();
					
					List<Object> listSecond=new ArrayList<>();
					listSecond.add(Addr);
					listSecond.add(arrTime);
					listSecond.add(endTime);
					listSecond.add(Amount);
					listSecond.add(Order_id);
					listFirst.add(listSecond);
					post_res.put(Courier_id, listFirst);
				}
			}
			br.close();
			//关闭InputStreamReader流
			close();
		}
		return post_res;		
	}
}
