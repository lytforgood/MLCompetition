package lastkilometer.O_four;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

import jsprit.core.algorithm.VehicleRoutingAlgorithm;
import jsprit.core.algorithm.io.VehicleRoutingAlgorithms;
import jsprit.core.problem.Location;
import jsprit.core.problem.VehicleRoutingProblem;
import jsprit.core.problem.VehicleRoutingProblem.FleetSize;
import jsprit.core.problem.io.VrpXMLWriter;
import jsprit.core.problem.job.Shipment;
import jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import jsprit.core.problem.solution.route.activity.TimeWindow;
import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.problem.vehicle.VehicleType;
import jsprit.core.problem.vehicle.VehicleTypeImpl;
import jsprit.core.problem.vehicle.VehicleImpl.Builder;
import jsprit.core.reporting.SolutionPrinter;
import jsprit.core.util.Coordinate;
import jsprit.core.util.Solutions;
import lastkilometer.parse.ParseXMLForPost_one;
import lastkilometer.readCSV.ReadAssignedOrder_id;

/**
 * 
 * 最后一公里配送
 * 
 * */
public class LastKilometer_one extends ReadData{

	//读取已经配送的订单id
	ReadAssignedOrder_id readAssignedOrder_id =new ReadAssignedOrder_id(); 
	public LastKilometer_one() throws NumberFormatException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 配送
	 * 
	 * @param number 已经配送的快递员个数
	 * @param iteration FleetSize.FINITE模式下迭代的次数
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @throws DocumentException 
	 * */
	public int pickupAndDeliver(int number,int iteration) throws NumberFormatException, IOException, DocumentException{
		
		
		int n=1;
		int step=31;
		for(int i=0;i<Site_Lng_Lat.size();i+=step){
			List<String> Site_id_list=new ArrayList<>();
			for(int j=0;j<step;j++){
				if(n<=124){
					//获取网点id
					 String cahce=n+"";
					 StringBuffer buffer = new StringBuffer("A000");
					 buffer.replace(4-cahce.length(),4, cahce);
					 String Site_id=buffer.toString();
					 Site_id_list.add(Site_id);
					 n++;
				}
			}
			System.out.println("设置网点------>"+Site_id_list);
			//设置网点
			List<VehicleImpl> sites=setSite(Site_id_list);
//			List<String> unAssignedOrder_id=null;
			do{
				//设置订单
				List<Shipment> orders=setOrder(Site_id_list);
				VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
				vrpBuilder.addAllVehicles(sites);
				vrpBuilder.addAllJobs(orders);
				vrpBuilder.setFleetSize(FleetSize.FINITE);
				VehicleRoutingProblem problem = vrpBuilder.build();
				
				/*
				 * get the algorithm
				 */
//				VehicleRoutingAlgorithm algorithm = new SchrimpfFactory().createAlgorithm(problem);
//				VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);
				VehicleRoutingAlgorithm algorithm =VehicleRoutingAlgorithms.readAndCreateAlgorithm(problem,"input/algorithmConfig.xml");
				algorithm.setMaxIterations(1);
				/*
				 * and search a solution
				 */
				Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
				
				/*
				 * get the best 
				 */
				VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
				SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);
				 
				String pathAndName="output/results_"+i+"_"+step+".xml";
				new VrpXMLWriter(problem, solutions).write(pathAndName);
				
		    	//解析output目录下的文件得出结果
				ParseXMLForPost_one pxps=new ParseXMLForPost_one();
				boolean bool=pxps.savePostFormat(pathAndName, number,iteration);
				if(bool){
					number=pxps.number;
				}else{
					break;
				}
//				unAssignedOrder_id=pxps.getUnassignedO2OOrder2(pathAndName);
//			}while(!unAssignedOrder_id.isEmpty());
			}while(false);
		}
		
		return number;
	}
	
	
	/**
	 * 设置订单
	 * 
	 * @param Site_id 网点id
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * */
	private List<Shipment> setOrder(List<String> Site_id_list) throws NumberFormatException, IOException{
		
		List<String> assignedOrder_idWithAll=readAssignedOrder_id.readAssignedOrder_idWithAll();
		
		List<Shipment> shipments=new ArrayList<Shipment>();
		
		for(String Site_id:Site_id_list){
			Map<String, Map<String, Integer>> twoESO=ESO.get(Site_id);
			for(Entry<String, Map<String, Integer>> eTwo:twoESO.entrySet()){
				String Spot_id=eTwo.getKey();
				Map<String, Integer> three=eTwo.getValue();
				for(Entry<String, Integer> eThree:three.entrySet()){
					String Order_id=eThree.getKey();
					
					//如果当前订单没有配送，则设置
					if(!assignedOrder_idWithAll.contains(Order_id)){
//					if(assignedOrder_idWithAll.contains(Order_id)){
						int Num=eThree.getValue();
						//网点的经纬度
						double[] Lng_Lat_Site=Site_Lng_Lat.get(Site_id);
						//配送点的经纬度
						double[] Lng_Lat_Spot=Spot_Lng_Lat.get(Spot_id);
						
						Shipment shipment = Shipment.Builder.newInstance(Order_id)
								.addSizeDimension(0, Num)
								.setPickupLocation(loc(Coordinate.newInstance(Lng_Lat_Site[0], Lng_Lat_Site[1])))
								.setDeliveryLocation(loc(Coordinate.newInstance(Lng_Lat_Spot[0], Lng_Lat_Spot[1])))
								.setDeliveryTimeWindow(TimeWindow.newInstance(0, 12*60))
								.setDeliveryServiceTime(Math.round(3*Math.sqrt(Num)+5))
								.build();
						shipments.add(shipment);
					}
				}
			}
			
			//取离当前网点最近的商户id
			List<String> Shop_id_list=OIB.get(Site_id);
			if(Shop_id_list!=null){
				for(String Shop_id:Shop_id_list){
					Map<String, Map<String, List<Object>>> twoOO=OO.get(Shop_id);
					for(Entry<String, Map<String, List<Object>>> eTwo:twoOO.entrySet()){
						String Spot_id=eTwo.getKey();
						Map<String, List<Object>> three=eTwo.getValue();
						for(Entry<String, List<Object>> eThree:three.entrySet()){
							String Order_id=eThree.getKey();
							
							//如果当前订单没有配送，则设置
							if(!assignedOrder_idWithAll.contains(Order_id)){
//							if(assignedOrder_idWithAll.contains(Order_id)){	
								List<Object> list=eThree.getValue();
								int Pickup_time=(int) list.get(0);
								int Delivery_time=(int) list.get(1);
								int Num=(int)list.get(2);
								
								//商户的经纬度
								double[] Lng_Lat_Shop=Shop_Lng_Lat.get(Shop_id);
								//配送点的经纬度
								double[] Lng_Lat_Spot=Spot_Lng_Lat.get(Spot_id);
								Shipment shipment = Shipment.Builder.newInstance(Order_id)
										.addSizeDimension(0, Num)
										.setPickupLocation(loc(Coordinate.newInstance(Lng_Lat_Shop[0], Lng_Lat_Shop[1])))
										.setPickupTimeWindow(TimeWindow.newInstance(Pickup_time,Pickup_time))
										.setDeliveryLocation(loc(Coordinate.newInstance(Lng_Lat_Spot[0], Lng_Lat_Spot[1])))
										.setDeliveryServiceTime(Math.round(3*Math.sqrt(Num)+5))
										.setDeliveryTimeWindow(TimeWindow.newInstance(Pickup_time, Delivery_time))
										.build();
								shipments.add(shipment);
							}
						}
					}
				}
			}
		}
		
		return shipments;
	}
	
	/**
	 * 设置网点
	 * 
	 * @param Site_id 网点id
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @return List<VehicleImpl>
	 * */
	private List<VehicleImpl> setSite(List<String> Site_id_list) throws NumberFormatException, IOException{
		
		 List<VehicleImpl> vehicles=new ArrayList<>();
		 
		 for(String Site_id:Site_id_list){
			 int bearLoad=140;//设置快递员负重容量
			 
			 double[] Lng_Lat=Site_Lng_Lat.get(Site_id);
			 double lng=Lng_Lat[0];
			 double lat=Lng_Lat[1];
			 
			 VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance(Site_id+"Type").addCapacityDimension(0, bearLoad);
//			 vehicleTypeBuilder.setCostPerServiceTime(1.0);//设置，计算cost时的设置
			 VehicleType vehicleType = vehicleTypeBuilder.build();
			 Builder vehicleBuilder = VehicleImpl.Builder.newInstance(Site_id);
			 vehicleBuilder.setStartLocation(loc(Coordinate.newInstance(lng, lat)));
			 vehicleBuilder.setLatestArrival(10*60);//十二个小时,设置快递员工作时间
			 vehicleBuilder.setType(vehicleType);
			 vehicleBuilder.setReturnToDepot(false);//设置车辆不返回
			 
			 VehicleImpl vehicle = vehicleBuilder.build();
			 vehicles.add(vehicle);
		 }
		return vehicles;
	}
	
	
	private static Location loc(Coordinate coordinate) {
		return Location.Builder.newInstance().setCoordinate(coordinate).build();
	}
	
}
