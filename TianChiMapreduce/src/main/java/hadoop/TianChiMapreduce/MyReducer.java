package hadoop.TianChiMapreduce;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * Reducer模板。请用真实逻辑替换模板内容
 */
public class MyReducer implements Reducer {
    private Record result;

    public void setup(TaskContext context) throws IOException {
        result = context.createOutputRecord();
    }

    public void reduce(Record key, Iterator<Record> values, TaskContext context) throws IOException {
        result.set(0, key.get(0));//获取key值里的第0个元素--放入result的第0个元素
        result.set(1, key.get(0));//放入result的第1个元素
        context.write(result);
    }

    public void cleanup(TaskContext arg0) throws IOException {

    }
}
