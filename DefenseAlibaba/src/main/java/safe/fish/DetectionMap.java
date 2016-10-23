package safe.fish;

import java.io.IOException;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Mapper;

public class DetectionMap implements Mapper {

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
		Boolean isfish=QueryfishnoError.isfish(url,html);
		if (isfish) {
			Record result_record = context.createOutputRecord();
			result_record.set("url", url);
//			result_record.set("post_data", postdata);
			context.write(result_record);
		}
		
	}

	@Override
	public void setup(TaskContext arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
