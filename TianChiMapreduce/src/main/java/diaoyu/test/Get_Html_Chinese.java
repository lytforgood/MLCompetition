package diaoyu.test;

import java.io.IOException;
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


/**
 * 提取html中文
 * */
public class Get_Html_Chinese {
	
	/**
	 * 处理表，提取数据
	 */
	public static class TokenizerMapper extends MapperBase {
		private Record url;
		private Record html;

		@Override
		public void setup(TaskContext context) throws IOException {
			url = context.createMapOutputKeyRecord();
			html = context.createMapOutputValueRecord();
			System.out.println("TaskID:" + context.getTaskID().toString());
		}

		@Override
		public void map(long recordNum, Record record, TaskContext context) throws IOException {
			url.set(new Object[] { record.get(0).toString() });// 取url
			html.set(new Object[] { record.get(1).toString() });// 取html
			context.write(url, html);
		}
	}

	
	  /**
	   *获取html中文
	   **/
	  public static class Chinese extends ReducerBase {
		
	    private Record Chinese;
	    @Override
	    public void setup(TaskContext context) throws IOException {
	    	Chinese = context.createOutputRecord();
	    }

	    @Override
	    public void reduce(Record key, Iterator<Record> values, TaskContext context)
	        throws IOException {
	    	
	    	Chinese.set(0, key.get(0));
			while (values.hasNext()) {
				Record val = values.next();
				Chinese.set(1, getChinese((String)val.get(0)));
			}
			 context.write(Chinese);
	    }
	    
	    /**
		 * 提取中文
		 * */
		private String getChinese(String str){
			String res="";
			char[] ch = str.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				if (isChinese(c)&&!isChinesePunctuation(c)) {
					res+=c;
				}
			}
			return res;
		}
		/**
		 *  根据Unicode编码完美的判断中文汉字和符号
		 * */
		private boolean isChinese(char c) {
			
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
				return true;
			}
			return false;
		}
		
		/**
		 *  根据Unicode编码完美的判断中文汉字和符号
		 * */
		private boolean isChinesePunctuation(char c) {
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
	                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
	                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
	                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
	                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	}
	

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: WordCount <in_table> <out_table>");
			System.exit(2);
		}

		JobConf job = new JobConf();

		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(Chinese.class);

		job.setMapOutputKeySchema(SchemaUtils.fromString("url:string"));
		job.setMapOutputValueSchema(SchemaUtils.fromString("Chinese:string"));

		InputUtils.addTable(TableInfo.builder().tableName(args[0]).build(), job);
		OutputUtils.addTable(TableInfo.builder().tableName(args[1]).build(), job);

		JobClient.runJob(job);
	}


}
