package hadoop.TianChiMapreduce;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Mapper;

import java.io.IOException;

/**
 * Mapper模板。请用真实逻辑替换模板内容
 * 重复运行程序会删除原来的结果重新写入
 */
public class MyMapper implements Mapper {
    private Record word;
    private Record one;

    public void setup(TaskContext context) throws IOException {
        word = context.createMapOutputKeyRecord();
        one = context.createMapOutputValueRecord();
//        one.setBigint(0, 1L);
        
    }

    public void map(long recordNum, Record record, TaskContext context) throws IOException {
        String w = record.getBigint(0).toString();//获取表中第0个字段的数据
        word.setString(0, w);//放入key中第0个位置
        one.setString(0, "ta1");//放入value中第0个位置
        context.write(word, one);
    }

    public void cleanup(TaskContext context) throws IOException {

    }
}