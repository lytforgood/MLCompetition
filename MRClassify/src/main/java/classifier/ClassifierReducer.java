package classifier;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ClassifierReducer extends Reducer<Text, IntWritable, NullWritable, Text> {

	private Text outValue = new Text();
	public void reduce(Text key, Iterable<IntWritable> values, Context context) 
			throws IOException, InterruptedException {
		// get the number of labels that user read
		int num = 0;
		for(IntWritable value: values){
			num += value.get();
		}
		outValue.set(key.toString()+"|"+num);
		// output
		context.write(NullWritable.get(), outValue);
	}
}


