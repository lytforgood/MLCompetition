package safe.fish;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Mapper;

public class TitleMap implements Mapper {

	@Override
	public void cleanup(TaskContext arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void map(long recordNum, Record record, TaskContext context)
			throws IOException {
		String url = (String) record.get(0);
		String html = (String) record.get(1);
//		Boolean isWebshell=QueryWebshell.isWebshell(postdata);
		Document doc = Jsoup.parse(html);
		Elements links=doc.getElementsByTag("title");
		String title="";
		for(Element link:links){
			title=title+","+link.text();
		}
		
		Record result_record = context.createOutputRecord();
		result_record.set("url", url);
		result_record.set("title", title);
		context.write(result_record);
	}

	@Override
	public void setup(TaskContext arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
