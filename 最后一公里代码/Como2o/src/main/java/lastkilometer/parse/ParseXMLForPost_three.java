package lastkilometer.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import lastkilometer.readCSV.BasicClass;
import lastkilometer.readCSV.ReadCSVForParse;


public class ParseXMLForPost_three extends BasicClass{

	public int number=0;
	/**
	 * 处理第二次配送结果，生成提交格式
	 * 
	 * @param path 读取的xml文件目录
	 * @param number 计算快递员id时用
	 * @param iteration FleetSize.FINITE模式下迭代的次数
	 * @throws DocumentException 
	 * @throws IOException 
	 * */
	public boolean savePostFormat(String pathAndName,int number) throws DocumentException, IOException{
		ReadCSVForParse read=new ReadCSVForParse();
		Map<String, List<Object>> ESO=read.readElectricitySuppliersOrder();
		Map<String, List<Object>> OO=read.readO2OOrder();
		
		List<List<Map<String,Map<String,int[]>>>> parseResults=Parse(pathAndName);//List<List<Map<Order_id,Map<pickupORdeliver,int[]>>>> int[]--到达时间和结结束时间

		if(parseResults!=null){
			for(int i=0;i<parseResults.size();i++){
				String cahce=(i+number+1)+"";
				StringBuffer buffer = new StringBuffer("D0000");
				buffer.replace(5-cahce.length(),5, cahce);
				String Courier_id=buffer.toString();
				//提取出list,既是快递员的配送路径
				List<Map<String,Map<String,int[]>>> two=parseResults.get(i);
				for(Map<String,Map<String,int[]>> three:two){
					
					for(Entry<String, Map<String,int[]>> entry:three.entrySet()){
						String Order_id=entry.getKey();//订单号
						List<Object> list=null;
						if(ESO.get(Order_id)!=null){
							list=ESO.get(Order_id);
						}else{
							list=OO.get(Order_id);
						}
						int Num=(int) list.get(2);
						
						Map<String,int[]> four=entry.getValue();
						for(Entry<String, int[]> e:four.entrySet()){
							String pickupORdeliver=e.getKey();
							
							int[] time=e.getValue();
							
							if("pickup".equals(pickupORdeliver)){
								//根据订单号和pickup判断来取网点或商户id、 取/送货量（取为+，送为-） 
								String Addr=(String) list.get(0);
								save("results/", "post_last",Courier_id+","+Addr+","+time[0]+","+time[1]+","+Num+","+Order_id);
							}else if("deliver".equals(pickupORdeliver)){
								//根据订单号和deliver判断来取配送点、 取/送货量（取为+，送为-）
								String Addr=(String) list.get(1);
								save("results/", "post_last",Courier_id+","+Addr+","+time[0]+","+time[1]+",-"+Num+","+Order_id);
							}
						}
					}
				}
			}
		}else{
			return false;
		}
		this.number=number+parseResults.size();
		return true;
	} 
	
	/**
	 * 解析XML文件，并保存结果提交格式
	 * 
	 * @return Map<int,List<Map<Order_id,Map<pickupORdeliver,int[]>>>
	 * 
	 * @throws DocumentException 
	 * 
	 * */
	 List<List<Map<String,Map<String,int[]>>>> Parse(String pathAndName) throws DocumentException{
		
		//List<List<Map<Order_id,Map<pickupORdeliver,int[]>>>> int[]--到达时间和结结束时间
		List<List<Map<String,Map<String,int[]>>>> parseResults=new ArrayList<>();
		 
		// 创建saxReader对象
		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象
		Document document = reader.read(new File(pathAndName));
		// 获取根节点元素对象
		Element node = document.getRootElement();
		// 获取root-node中的solutions元素节点对象
		Element solutions = node.element("solutions");
		// 获取solutions中最后一个solution元素节点对象
		Element xElement2 = (Element) solutions.elements().get(solutions.elements().size() - 1);
		
		// 获取solution中的routes元素节点对象
		Element routes = xElement2.element("routes");
		
		if(routes!=null){
			System.out.println("路径的数量："+routes.elements().size());
			
			// 遍历routes
			for (int i = 0; i < routes.elements().size(); i++) {
				
				//List<Map<Order_id,Map<pickupORdeliver,int[]>>> int[]--到达时间和结结束时间
				List<Map<String,Map<String,int[]>>> one=new ArrayList<>();//保存special_Order_id的配送路径
				
				// 获取路线route
				Element route = (Element) routes.elements().get(i);//vrpBuilder.setFleetSize(FleetSize.FINITE);条件下只有固定的路径数
				// 获取当前送货顺序
				Iterator<?> eleIt = route.elementIterator();
				while (eleIt.hasNext()) {
					//Map<Order_id,Map<pickupORdeliver,int[]>>
					Map<String,Map<String,int[]>> two=new TreeMap<>();
					//Map<pickupORdeliver,int[]>
					Map<String,int[]> three=new TreeMap<>();
					
					Element e = (Element) eleIt.next();
					if (e.getName() == "act") {
						if (e.attributeValue("type").equals("pickupShipment")) {
							Element shipmentId = e.element("shipmentId");//Order_id
							Element arrTime = e.element("arrTime");
							Element endTime = e.element("endTime");
							
							String Order_id=shipmentId.getText();
							
							int[] time=new int[2];
							time[0]=(int)Math.rint(Double.parseDouble(arrTime.getText()));
							time[1]=(int)Math.rint(Double.parseDouble(endTime.getText()));
							
							
							three.put("pickup", time);
							two.put(Order_id, three);
						}else if (e.attributeValue("type").equals("deliverShipment")) {
							Element shipmentId = e.element("shipmentId");//Order_id
							Element arrTime = e.element("arrTime");
							Element endTime = e.element("endTime");
							
	                        String Order_id=shipmentId.getText();
							
							int[] time=new int[2];
							time[0]=(int)Math.rint(Double.parseDouble(arrTime.getText()));
							time[1]=(int)Math.rint(Double.parseDouble(endTime.getText()));
							
							three.put("deliver", time);
							two.put(Order_id, three);
						}
						one.add(two);
					}
				}
				parseResults.add(one);
			}
		}else{
			System.out.println("没有配送的路径.................");
			return null;
		}
		return parseResults;
	}
	 /**
	  * 获取未配送的O2O订单id
	  * */
	 public List<String> getUnassignedO2OOrder(String path) throws DocumentException{
		 
		List<String> unassignedO2OOrder=new ArrayList<>();//存储未配送的O2O订单id
		
		// 获取xml文件名
		String[] name = getFileName(path);
		for (String xml : name) {

			// 创建saxReader对象
			SAXReader reader = new SAXReader();
			// 通过read方法读取一个文件 转换成Document对象
			Document document = reader.read(new File(path+xml));
			// 获取根节点元素对象
			Element node = document.getRootElement();
			// 获取root-node中的solutions元素节点对象
			Element solutions = node.element("solutions");
			// 获取solutions中最后一个solution元素节点对象
			Element xElement2 = (Element) solutions.elements().get(solutions.elements().size() - 1);

			// 获取</solution>中的unassignedJobs元素对象，含有未配送的商户id
			Element unassignedJobs = xElement2.element("unassignedJobs");
			if (unassignedJobs != null) {
				for (int i = 0; i < unassignedJobs.elements().size(); i++) {
					Element job = (Element) unassignedJobs.elements().get(i);
					unassignedO2OOrder.add(job.attributeValue("id"));
				}
			}
		}
		return unassignedO2OOrder;
	}
	 
}
