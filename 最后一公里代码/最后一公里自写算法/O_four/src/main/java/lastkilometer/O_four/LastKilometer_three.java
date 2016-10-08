package lastkilometer.O_four;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

import jsprit.core.algorithm.VehicleRoutingAlgorithm;
import jsprit.core.algorithm.box.Jsprit;
import jsprit.core.algorithm.io.VehicleRoutingAlgorithms;
import jsprit.core.problem.Location;
import jsprit.core.problem.VehicleRoutingProblem;
import jsprit.core.problem.job.Job;
import jsprit.core.problem.job.Shipment;
import jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.solution.route.activity.TimeWindow;
import jsprit.core.problem.solution.route.activity.TourActivity;
import jsprit.core.problem.solution.route.activity.TourActivity.JobActivity;
import jsprit.core.problem.vehicle.Vehicle;
import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.problem.vehicle.VehicleType;
import jsprit.core.problem.vehicle.VehicleTypeImpl;
import jsprit.core.reporting.SolutionPrinter;
import jsprit.core.problem.vehicle.VehicleImpl.Builder;
import jsprit.core.util.Coordinate;
import jsprit.core.util.Solutions;
import lastkilometer.readCSV.ReadAssignedOrder_id;
import lastkilometer.readCSV.ReadCSVForParse;
import lastkilometer.readCSV.ReadOrderFromOrder_id;
import lastkilometer.readCSV.ReadResults_cache;

/**
 * 
 * 最后一公里配送
 * 
 * */
public class LastKilometer_three extends ReadData{

	
	//读取已经配送的订单id
	ReadAssignedOrder_id readAssignedOrder_id =new ReadAssignedOrder_id(); 
	
	public LastKilometer_three() throws NumberFormatException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * 配送
	 * @return 
	 * 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @throws DocumentException 
	 * */
	public int pickupAndDeliver(int number) throws NumberFormatException, IOException, DocumentException{
		
		
		int n=1;
		int step=124;
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
			//设置订单
			List<Shipment> orders=setOrder(Site_id_list);
			VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
			
			vrpBuilder.addAllVehicles(sites);
			vrpBuilder.addAllJobs(orders);
			
			//设置初始路径
			List<VehicleRoute> vehicleRoutes=setInitialVehicleRoute(vrpBuilder);
			vrpBuilder.addInitialVehicleRoutes(vehicleRoutes);
			
//			vrpBuilder.setFleetSize(FleetSize.FINITE);
			VehicleRoutingProblem problem = vrpBuilder.build();
			
			/*
			 * get the algorithm
			 */
//			VehicleRoutingAlgorithm algorithm = new SchrimpfFactory().createAlgorithm(problem);
//			VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);
			VehicleRoutingAlgorithm algorithm =VehicleRoutingAlgorithms.readAndCreateAlgorithm(problem,"input/algorithmConfig.xml");
			algorithm.setMaxIterations(1);
			/*
			 * and search a solution
			 */
			Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
			
			/*
			 * get the best 
			 */
			number=writePostFile(number, solutions);
//			VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
//			SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);
//			 
//			String pathAndName="output/results_"+i+"_"+step+".xml";
//			new VrpXMLWriter(problem, solutions).write(pathAndName);
//			
//	    	//解析output目录下的文件得出结果
//			ParseXMLForPost_three pxps=new ParseXMLForPost_three();
//			boolean bool=pxps.savePostFormat(pathAndName, number);
//			if(bool){
//				number=pxps.number;
//			}
		}
		
		return number;
	}
	
	private int writePostFile(int number,Collection<VehicleRoutingProblemSolution> solutions) throws IOException {
		ReadCSVForParse read = new ReadCSVForParse();
		Map<String, List<Object>> ESO = read.readElectricitySuppliersOrder();
		Map<String, List<Object>> OO = read.readO2OOrder();

		if (solutions == null)
			return number;
		int counter = 0;
		for (VehicleRoutingProblemSolution solution : solutions) {
			if (counter == solutions.size() - 1) {// 取最后一个路径
				System.out.println("solution.getCost()--->" + solution.getCost());

				for (VehicleRoute route : solution.getRoutes()) {
					
					// 设置快递员id
					String cahce = (number + 1) + "";
					StringBuffer buffer = new StringBuffer("D0000");
					buffer.replace(5 - cahce.length(), 5, cahce);
					String Courier_id = buffer.toString();
					
					for (TourActivity act : route.getTourActivities().getActivities()) {
						Job job = ((JobActivity) act).getJob();
						String Order_id = job.getId();
						
						List<Object> list = null;
						if (ESO.get(Order_id) != null) {
							list = ESO.get(Order_id);
						} else {
							list = OO.get(Order_id);
						}
						int Num = (int) list.get(2);

						// act.getName()--->pickupShipment、deliverShipment 取还是送
						// act.getArrTime()--->到达时间
						// act.getEndTime()--->离开时间
						String pickupORdeliver=act.getName();
						if ("pickupShipment".equals(pickupORdeliver)) {
							// 根据订单号和pickup判断来取网点或商户id、 取/送货量（取为+，送为-）
							String Addr = (String) list.get(0);
							save("results/", "post_last", Courier_id + "," + Addr + "," + (int)act.getArrTime() + "," +  (int)act.getEndTime()
									+ "," + Num + "," + Order_id);
						} else if ("deliverShipment".equals(pickupORdeliver)) {
							// 根据订单号和deliver判断来取配送点、 取/送货量（取为+，送为-）
							String Addr = (String) list.get(1);
							save("results/", "post_last", Courier_id + "," + Addr + "," +  (int)act.getArrTime() + "," +  (int)act.getEndTime()
									+ ",-" + Num + "," + Order_id);
						}
					}
					number++;
				}

				// 未配送订单
				for (Job unassignedJob : solution.getUnassignedJobs()) {
					System.out.println("unassignedJob.getId():" + unassignedJob.getId());
				}
			}
			counter++;
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
					
					//设置
//					if(!assignedOrder_idWithAll.contains(Order_id)){
//						int Num=eThree.getValue();
//						//网点的经纬度
//						double[] Lng_Lat_Site=Site_Lng_Lat.get(Site_id);
//						//配送点的经纬度
//						double[] Lng_Lat_Spot=Spot_Lng_Lat.get(Spot_id);
//						
//						Shipment shipment = Shipment.Builder.newInstance(Order_id)
//								.addSizeDimension(0, Num)
//								.setPickupLocation(loc(Coordinate.newInstance(Lng_Lat_Site[0], Lng_Lat_Site[1])))
//								.setDeliveryLocation(loc(Coordinate.newInstance(Lng_Lat_Spot[0], Lng_Lat_Spot[1])))
//								.setDeliveryTimeWindow(TimeWindow.newInstance(0, 12*60))
//								.setDeliveryServiceTime(Math.round(3*Math.sqrt(Num)+5))
//								.build();
//						shipments.add(shipment);
//					}
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
							
							//设置
//							if(!assignedOrder_idWithAll.contains(Order_id)){	
//								List<Object> list=eThree.getValue();
//								int Pickup_time=(int) list.get(0);
//								int Delivery_time=(int) list.get(1);
//								int Num=(int)list.get(2);
//								
//								//商户的经纬度
//								double[] Lng_Lat_Shop=Shop_Lng_Lat.get(Shop_id);
//								//配送点的经纬度
//								double[] Lng_Lat_Spot=Spot_Lng_Lat.get(Spot_id);
//								Shipment shipment = Shipment.Builder.newInstance(Order_id)
//										.addSizeDimension(0, Num)
//										.setPickupLocation(loc(Coordinate.newInstance(Lng_Lat_Shop[0], Lng_Lat_Shop[1])))
//										.setPickupTimeWindow(TimeWindow.newInstance(Pickup_time,Pickup_time))
//										.setDeliveryLocation(loc(Coordinate.newInstance(Lng_Lat_Spot[0], Lng_Lat_Spot[1])))
//										.setDeliveryServiceTime(Math.round(3*Math.sqrt(Num)+5))
//										.setDeliveryTimeWindow(TimeWindow.newInstance(Pickup_time, Delivery_time))
//										.build();
//								
//								shipments.add(shipment);
//							}
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
//			 vehicleBuilder.setLatestArrival(12*60);//十二个小时,设置快递员工作时间
			 vehicleBuilder.setType(vehicleType);
//			 vehicleBuilder.setReturnToDepot(false);//设置车辆不返回
			 
			 VehicleImpl vehicle = vehicleBuilder.build();
			 vehicles.add(vehicle);
		 }
		return vehicles;
	}
	
	/**
	 * 设置初始路径
	 * @throws IOException 
	 * */
	private List<VehicleRoute> setInitialVehicleRoute(VehicleRoutingProblem.Builder vrpBuilder) throws IOException{
		
		List<VehicleRoute> vehicleRoutes=new ArrayList<>();
		
		ReadOrderFromOrder_id readOFO=new ReadOrderFromOrder_id();
		Map<String,List<Object>> ofo=readOFO.readOrder();
		//读取第一次配送的结果数据
		ReadResults_cache read=new ReadResults_cache();
		Map<String, List<List<Object>>> post_res=read.readResults_cahce();
		for(Entry<String, List<List<Object>>> entry:post_res.entrySet()){
			List<List<Object>> listFirst=entry.getValue();
			String Addr=null;
			for(List<Object> listSecond_C:listFirst){
				Addr=(String) listSecond_C.get(0);
				if("A".equals(Addr.substring(0,1))){
					break;
				}
			}
			Vehicle vehicle=getVehicle((String)listFirst.get(0).get(0), vrpBuilder);
			if(vehicle!=null){
				jsprit.core.problem.solution.route.VehicleRoute.Builder vehicleRouteBuilder = VehicleRoute.Builder.newInstance(vehicle);
				for(List<Object> listSecond:listFirst){
					String Order_id=(String) listSecond.get(2);
					int Amount=(int) listSecond.get(1);
					//根据订单id读取订单信息
					List<Object> listThird=ofo.get(Order_id);
					if(listThird.size()==3){//电商的订单
						String Spot_id=(String) listThird.get(0);
						String Site_id=(String) listThird.get(1);
						int Num=(int) listThird.get(2);
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
						if(Amount>0){//判断是Pickup还是Delivery
							vehicleRouteBuilder.addPickup(shipment);
						}else{
							vehicleRouteBuilder.addDelivery(shipment);
						}
					}else{//O2O的订单
						String Spot_id=(String) listThird.get(0);
						String Shop_id=(String) listThird.get(1);
						int Pickup_time=(int) listThird.get(2);
						int Delivery_time=(int) listThird.get(3);
						int Num=(int) listThird.get(4);
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
						
						if(Amount>0){//判断是Pickup还是Delivery
							vehicleRouteBuilder.addPickup(shipment);
						}else{
							vehicleRouteBuilder.addDelivery(shipment);
						}
					}
				}
				VehicleRoute vehicleRoute=vehicleRouteBuilder.build();
				vehicleRoutes.add(vehicleRoute);
			}
		
		}
		return vehicleRoutes;
	}
	
	private static Location loc(Coordinate coordinate) {
		return Location.Builder.newInstance().setCoordinate(coordinate).build();
	}
	
	private static Vehicle getVehicle(String vehicleId, VehicleRoutingProblem.Builder vrpBuilder) {
		for (Vehicle v : vrpBuilder.getAddedVehicles()) {
			if (v.getId().equals(vehicleId))
				return v;
		}
		return null;
	}
}
