package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取已经配送的订单
 * */
public class ReadAssignedOrder_id extends BasicClass{

	/**
	 * 读取(results_cache目录下)已经配送的订单
	 * 
	 * @throws IOException 
	 * */
	public List<String> readAssignedOrder_idWithAll() throws IOException{
		
		List<String> assignedOrder_id=new ArrayList<>();
		
		// 获取xml文件名
		String[] name = getFileName("results_cache/");
		if(name!=null){
			for (String pathAndName : name) {
				//读取 .csv
				BufferedReader br=read("results_cache/"+pathAndName);
				String line=null;
				while((line=br.readLine())!=null){
					String[] res=line.split(",");
					assignedOrder_id.add(res[5]);
				}
			}		
		}
		return assignedOrder_id;
	}
	
   public List<String> readAssignedOrder_idWithOne(String pathAndName) throws IOException{
		
		List<String> assignedOrder_id=new ArrayList<>();
		if(isExists(pathAndName)){
			//读取 .csv
			BufferedReader br=read(pathAndName);
			String line=null;
			while((line=br.readLine())!=null){
				String[] res=line.split(",");
				assignedOrder_id.add(res[5]);
			}
		}else{
			System.out.println(pathAndName+" 文件不存在......");
		}
		
		return assignedOrder_id;
	}
}
