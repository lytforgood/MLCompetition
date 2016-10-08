package diaoyu.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.TableInfo;
import com.aliyun.odps.mapred.JobClient;
import com.aliyun.odps.mapred.MapperBase;
import com.aliyun.odps.mapred.ReducerBase;
import com.aliyun.odps.mapred.conf.JobConf;
import com.aliyun.odps.mapred.utils.InputUtils;
import com.aliyun.odps.mapred.utils.OutputUtils;
import com.aliyun.odps.mapred.utils.SchemaUtils;

public class DeleteHtml {
	
	/**
	 * 处理表，提取URL路径
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
	   *去掉网页标签
	   **/
	  public static class DelHtml extends ReducerBase {
		
		private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
		  
	    private Record zhongwen;

	    @Override
	    public void setup(TaskContext context) throws IOException {
	    	zhongwen = context.createOutputRecord();
	    }

	    @Override
	    public void reduce(Record key, Iterator<Record> values, TaskContext context)
	        throws IOException {
	    	
	    	zhongwen.set(0, key.get(0));
			while (values.hasNext()) {
				Record val = values.next();
				zhongwen.set(1, getTextFromHtml((String) val.get(0)));
			}
			 context.write(zhongwen);
	    }
	    
	    /**
	     * @param htmlStr
	     * @return
	     *  删除Html标签
	     */
	    private String delHTMLTag(String htmlStr) {
	        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
	        Matcher m_script = p_script.matcher(htmlStr);
	        htmlStr = m_script.replaceAll(""); // 过滤script标签

	        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
	        Matcher m_style = p_style.matcher(htmlStr);
	        htmlStr = m_style.replaceAll(""); // 过滤style标签

	        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
	        Matcher m_html = p_html.matcher(htmlStr);
	        htmlStr = m_html.replaceAll(""); // 过滤html标签

	        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
	        Matcher m_space = p_space.matcher(htmlStr);
	        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
	        return htmlStr.trim(); // 返回文本字符串
	    }
	    
	    private String getTextFromHtml(String htmlStr){
	    	htmlStr = delHTMLTag(htmlStr);
	    	htmlStr = htmlStr.replaceAll(" ", "");
	    	htmlStr = htmlStr.replaceAll("&nbsp;", "");  
	    	return htmlStr;
	    }
	  }
	

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: WordCount <in_table> <out_table>");
			System.exit(2);
		}

		JobConf job = new JobConf();

		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(DelHtml.class);

		job.setMapOutputKeySchema(SchemaUtils.fromString("url:string"));
		job.setMapOutputValueSchema(SchemaUtils.fromString("zhongwen:string"));

		InputUtils.addTable(TableInfo.builder().tableName(args[0]).build(), job);
		OutputUtils.addTable(TableInfo.builder().tableName(args[1]).build(), job);

		JobClient.runJob(job);
	}


}
