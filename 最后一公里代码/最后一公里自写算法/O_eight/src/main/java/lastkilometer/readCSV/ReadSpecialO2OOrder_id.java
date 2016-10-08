package lastkilometer.readCSV;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadSpecialO2OOrder_id extends BasicClass{

	/**
	 * 读取特殊O2O订单
	 * @throws IOException 
	 * */
	public List<String> readSpecialO2O() throws IOException{
		
		 List<String> list=new ArrayList<>();
		 BufferedReader br = read("read/特殊O2O订单.txt");
		 String line=null;
		 while((line=br.readLine())!=null){
			 list.add(line);
		 }
		return list;
	}
}
