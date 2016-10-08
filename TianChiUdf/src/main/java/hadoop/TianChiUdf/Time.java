package hadoop.TianChiUdf;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aliyun.odps.udf.UDF;

/***
 * BASE UDF
 */
public final class Time extends UDF {
	
	  
    public Time() {  
    }  
    /**
     * UDF Evaluate接口
     * 
     * UDF在记录层面上是一对一，字段上是一对一或多对一。 Evaluate方法在每条记录上被调用一次，输入为一个或多个字段，输出为一个字段
     */
    public String evaluate(String gmt_create) {
    	Date date=new Date(Long.valueOf(gmt_create+"000"));
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHH");
		String strs=df.format(date);
        // TODO: 请按需要修改参数和返回值，并在这里实现你自己的逻辑
    	
        return strs; 
    }
}
