package safe.webshell;

import java.io.IOException;

import com.aliyun.odps.data.Record;
import com.aliyun.odps.mapred.Mapper;

public class ShellMap implements Mapper {

	@Override
	public void cleanup(TaskContext arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void map(long recordNum, Record record, TaskContext context)
			throws IOException {
		String id = (String) record.get(0);
//		String url = (String) record.get(1);
		String postdata = (String) record.get(2);
//		Boolean isWebshell=QueryWebshell.isWebshell(postdata);
		Boolean isWebshell=QueryWebshellnoError.isWebshell(id,postdata);
		if (isWebshell) {
			Record result_record = context.createOutputRecord();
			result_record.set("id", id);
//			result_record.set("post_data", postdata);
			context.write(result_record);
		}
		
	}

	@Override
	public void setup(TaskContext arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
