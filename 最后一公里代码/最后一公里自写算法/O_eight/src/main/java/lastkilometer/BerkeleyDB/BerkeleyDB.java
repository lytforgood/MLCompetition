package lastkilometer.BerkeleyDB;

import java.io.FileNotFoundException;
import java.util.Map.Entry;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Set;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.DatabaseException;

/**
 * 对数据库进行保存、查找、删除等操作 
 * 
 * 数据存入数据库的格式为：Map<IP，Map<在线日期，Map<时刻，点击量>>>
 * 
 * */
public class BerkeleyDB extends AbstractDBD{
	
	private StoredMap<String,Object> pendingUrisDB = null;
	
	public BerkeleyDB(String homeDirectory) throws DatabaseException,FileNotFoundException {
		super(homeDirectory);
		// TODO Auto-generated constructor stub
		
		EntryBinding<String> keyBinding = new SerialBinding<String>(javaCatalog, String.class);
		EntryBinding<Object> valueBinding = new SerialBinding<Object>(javaCatalog,Object.class);
		pendingUrisDB = new StoredMap<String, Object>(database, keyBinding, valueBinding, true);
	}

	/**
	 * 迭代数据库
	 * 
	 * */
	public  Set<Entry<String, Object>> iteration(){
		return pendingUrisDB.entrySet();
	}
	
	/**
	 * 存入数据库
	 * 
	 * */
	public void putData(String key, Object value){
		put(key, value);
	}
	
	/**
	 * 根据key值取value值
	 * */
	public  Object getData(String key){
		return  get(key);
	}
	
	/**
	 * 根据key判断是否包含该记录
	 * 
	 * */
	public  boolean containsKey(Object key){
		return pendingUrisDB.containsKey(key);
	}
	
	/**
	 * 关闭数据库
	 * */
	public void closeBDB(){
		close();
	}
	
	@Override
	protected void put(Object key, Object value) {
		// TODO Auto-generated method stub
		pendingUrisDB.put((String)key, value);
	}

	@Override
	protected Object get(Object key) {
		// TODO Auto-generated method stub
		return pendingUrisDB.get(key);
	}

	@Override
	protected Object delete(Object key) {
		// TODO Auto-generated method stub
		return pendingUrisDB.remove(key);
	}
}
