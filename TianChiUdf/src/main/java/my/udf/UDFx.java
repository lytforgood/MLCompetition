package my.udf;

import com.aliyun.odps.io.LongWritable;
import com.aliyun.odps.udf.UDF;

public class UDFx extends UDF{
	private LongWritable result = new LongWritable();  
	  
    public UDFx() {  
      result.set(0);  
    }  

	 /**
     * UDF Evaluate接口
     * 
     * UDF在记录层面上是一对一，字段上是一对一或多对一。 Evaluate方法在每条记录上被调用一次，输入为一个或多个字段，输出为一个字段
     */
    public LongWritable  evaluate() {
        // TODO: 请按需要修改参数和返回值，并在这里实现你自己的逻辑

    	result.set(result.get() + 1);  
        return result;  
    }
}
