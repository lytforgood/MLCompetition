package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NeedAssignedOrder_id extends BasicClass{

	public static void main(String[] args) throws IOException {
		List<String> needAssignedOrder_id=new NeedAssignedOrder_id().readNeedAssignedOrder_id();
		System.out.println(needAssignedOrder_id);
	}
	
	/**
	 * 读取(results_cache目录下)已经配送的订单
	 * 
	 * @throws IOException 
	 * */
	public List<String> readNeedAssignedOrder_id() throws IOException{
		
		List<String> needAssignedOrder_id=new ArrayList<>();
		
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
					
					List<Object> list_three_last=list_two.get(list_two.size()-1);
					int endTime_last=(int) list_three_last.get(2);
					
					if(endTime_last<600){
						for(List<Object> list_three_needAssigned: list_two){
							needAssignedOrder_id.add((String)list_three_needAssigned.get(4));
						}
					}
					list_two=new ArrayList<>();
				}
			}
		}	
		
//		//保存不需要重新配送的订单
//		for (String name : names) {
//			// 将结果存入集合中
//			BufferedReader br = read("results_cache/"+name);
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				String[] res = line.split(",");
//				String Courier_id=res[0];
//				String Addr = res[1];
//				int arrTime = Integer.parseInt(res[2]);
//				int endTime = Integer.parseInt(res[3]);
//				int Amount = Integer.parseInt(res[4]);
//				String Order_id = res[5];
//				if(!needAssignedOrder_id.contains(Order_id)){
//					save("output/", "post_last", Courier_id + "," + Addr + "," + arrTime + "," +  endTime+ "," + Amount + "," + Order_id);
//				}
//			}
//		}			
		return removeDuplicate(needAssignedOrder_id);
	}
	
	//去除重复的元素
    private List<String>  removeDuplicate(List<String> list) {  
        for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {  
          for ( int j = list.size() - 1 ; j > i; j -- ) {  
            if (list.get(j).equals(list.get(i))) {  
              list.remove(j);  
            }   
           }   
         }   
		return list;  
     }   
}
