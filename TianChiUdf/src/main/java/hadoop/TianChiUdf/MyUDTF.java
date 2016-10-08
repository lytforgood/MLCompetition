package hadoop.TianChiUdf;

import com.aliyun.odps.udf.UDTF;
import com.aliyun.odps.udf.UDTFCollector;
import com.aliyun.odps.udf.annotation.Resolve;
import com.aliyun.odps.udf.UDFException;

/**
 * BASE UDTF
 */
// TODO define input and output types, e.g., "string,string->string,bigint".
public class MyUDTF extends UDTF {

    /**
     * UDTF Process接口
     *
     * 每条记录都会调用此接口。
     */
    public void process(Object[] args) throws UDFException {
        // TODO: 实现对每条记录的处理逻辑
    }

    /**
     * UDTF Close接口
     *
     * 任务最后调用此接口，规格化所有数据并输出。forward方法用于输出结果
     */
    public void close() throws UDFException {
        // TODO: 实现终结逻辑
    }

}
