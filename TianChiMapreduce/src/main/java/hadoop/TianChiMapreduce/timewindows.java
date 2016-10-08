package hadoop.TianChiMapreduce;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.TableInfo;
import com.aliyun.odps.mapred.JobClient;
import com.aliyun.odps.mapred.MapperBase;
import com.aliyun.odps.mapred.ReducerBase;
import com.aliyun.odps.mapred.conf.JobConf;
import com.aliyun.odps.mapred.utils.InputUtils;
import com.aliyun.odps.mapred.utils.OutputUtils;
import com.aliyun.odps.mapred.utils.SchemaUtils;

public class timewindows {

  public static class TokenizerMapper extends MapperBase {
    private Record key;
    private Record value;

    @Override
    public void setup(TaskContext context) throws IOException {
    	key = context.createMapOutputKeyRecord();
    	value = context.createMapOutputValueRecord();
    	
      System.out.println("TaskID:" + context.getTaskID().toString());
    }
    private String[] features = { "recentplay", "recentplay_min", "recentplay_stddev",
			"recentplay_avg", "recentplay_median", "recentplay_max"};
	//private int[] days = { 1, 2, 3, 4, 5, 6, 7, 14, 21, 30, 60, 180};
	private int[] days = { 1, 3, 7, 15, 30, 60, 180};
	String day="20150831";
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	int num = -1;

    @Override
    public void map(long recordNum, Record record, TaskContext context)
        throws IOException {
    	key.set(0, record.get(0).toString());
    	//value.setString("ds", record.getString("ds"));
    	String ds=record.getString("ds");
    	Date dateTmp = null;
    	Date otherDateTmp = null;
		try {
			dateTmp = df.parse(day);
			otherDateTmp = df.parse(ds);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (dateTmp != null && otherDateTmp != null) {
			long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
			num = (int) (time / (24 * 60 * 60 * 1000));
		}
		if (num==1) {
			String feature=features[0]+"_"+days[0];
			value.set(0, record.get(1).toString());
		}
		if (num>=1&&num<=3) {
			String feature=features[0]+"_"+days[1];
			value.set(0, record.get(1).toString());
		}
//		if (num>=1&&num<=7) {
//			String feature=features[0]+"_"+days[2];
//			value.set(feature, record.get(1).toString());
//		}
//		if (num>=1&&num<=15) {
//			String feature=features[0]+"_"+days[3];
//			value.set(feature, record.get(1).toString());
//		}
//		if (num>=1&&num<=30) {
//			String feature=features[0]+"_"+days[4];
//			value.set(feature, record.get(1).toString());
//		}
//		if (num>=1&&num<=60) {
//			String feature=features[0]+"_"+days[5];
//			value.set(feature, record.get(1).toString());
//		}
//		if (num>=1&&num<=180) {
//			String feature=features[0]+"_"+days[6];
//			value.set(feature, record.get(1).toString());
//		}
		context.write(key, value);
    	
    	
    }
  }


  /**
   * A reducer class that just emits the sum of the input values.
   **/
  public static class SumReducer extends ReducerBase {
    private Record result = null;
    private int[] days = { 1, 3, 7, 15, 30, 60, 180};

    @Override
    public void setup(TaskContext context) throws IOException {
      result = context.createOutputRecord();
    }

    @Override
    public void reduce(Record key, Iterator<Record> values, TaskContext context)
        throws IOException {
      long count = 0;
      ArrayList<String> recentplay_1=new ArrayList<String>();
      while (values.hasNext()) {
        Record record = values.next();
//        for (int i = 0; i < days.length; i++) {
//        	String feature="recentplay"+"_"+days[i];
//        	recentplay_1.add(record.getBigint(feature));
//		}
        String feature="recentplay"+"_"+days[0];
    	recentplay_1.add((String) record.get(0));
      }
      count=Long.parseLong(recentplay_1.get(0));
      result.set(0, key.get(0));
      result.set(1, count);
      context.write(result);
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: WordCount <in_table> <out_table>");
      System.exit(2);
    }

    JobConf job = new JobConf();

    job.setMapperClass(TokenizerMapper.class);
    job.setReducerClass(SumReducer.class);

    job.setMapOutputKeySchema(SchemaUtils.fromString("word:string"));
    job.setMapOutputValueSchema(SchemaUtils.fromString("count:bigint"));

    InputUtils.addTable(TableInfo.builder().tableName(args[0]).build(), job);
    OutputUtils.addTable(TableInfo.builder().tableName(args[1]).build(), job);

    JobClient.runJob(job);
  }

}
