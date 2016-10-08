package hadoop.TianChiUdf;

import com.aliyun.odps.udf.UDF;

/***
 * 输入一个输出多个
 */
public final class Urlto extends UDF {
	
//	private Long result ;  
	  
    public Urlto() {  
//      result=(long) 0;  
    }  
    /**
     * UDF Evaluate接口
     * 
     * UDF在记录层面上是一对一，字段上是一对一或多对一。 Evaluate方法在每条记录上被调用一次，输入为一个或多个字段，输出为一个字段
     */
    public String evaluate(String url) {
    	String host ="1";
    	try {
    		java.net.URL urlto = new java.net.URL(url);
    		 host = urlto.getHost();
		} catch (Exception e) {
			 host ="1";
		}
		return host;
        // TODO: 请按需要修改参数和返回值，并在这里实现你自己的逻辑
//    	result=result + 1;  
//        return result; 
    }
}
