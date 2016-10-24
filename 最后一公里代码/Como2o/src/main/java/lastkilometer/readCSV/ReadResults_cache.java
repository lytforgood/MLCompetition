package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ReadResults_cache extends BasicClass{

	public static void main(String[] args) throws IOException {
		ReadResults_cache r=new ReadResults_cache();
		Map<String, List<List<Object>>> post_res=r.readResults_cahce();
		for(Entry<String, List<List<Object>>> e:post_res.entrySet()){
			System.out.println(e.getKey()+" "+e.getValue());
		}
	}
	
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
				String Addr=res[1];
				int Amount=Integer.parseInt(res[4]);
				String Order_id=res[5];
				
				if(post_res.containsKey(Courier_id)){
					List<List<Object>> listFirst=post_res.get(Courier_id);
					
					List<Object> listSecond=new ArrayList<>();
					listSecond.add(Addr);
					listSecond.add(Amount);
					listSecond.add(Order_id);
					
					listFirst.add(listSecond);
					post_res.put(Courier_id, listFirst);
				}else{
                    List<List<Object>> listFirst=new ArrayList<>();
					
					List<Object> listSecond=new ArrayList<>();
					listSecond.add(Addr);
					listSecond.add(Amount);
					listSecond.add(Order_id);
					
					listFirst.add(listSecond);
					post_res.put(Courier_id, listFirst);
				}
			}
		}
		return post_res;		
	}
}
