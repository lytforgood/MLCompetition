package bao.distance;

import java.io.FileNotFoundException;
import java.util.HashMap;

import com.sleepycat.je.DatabaseException;

import bao.BerkeleyDB.BerkeleyDB;

/**
 * 读取距离网点最近的商点
 * @author Administrator
 *
 */
public class ReadTop {
	public static void main(String[] args) throws DatabaseException, FileNotFoundException {
		ReadTop.readsite_shop_dis();
	}

	private static void readsite_shop_dis() throws DatabaseException, FileNotFoundException {
		//根目录地址
		String readPath="save//site//";	
		BerkeleyDB read_Shop_Site = new BerkeleyDB(readPath+"site_spot_distance");//格式：Map<Shop_id,Map<Shop_id,distance>>
		@SuppressWarnings("unchecked")
		HashMap<String, Double> ABdis=(HashMap<String, Double>) read_Shop_Site.getData("A001");
		for (String key:ABdis.keySet()) {
			System.out.println(key);
			System.out.println(ABdis.get(key).toString());
		}
		
	}

}
