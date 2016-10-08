package classifier;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.mahout.classifier.bayes.BayesParameters;

public class ClassifierDriver {

	public static void main(String[] args) throws Exception {

		// set bayes parameter
		BayesParameters params = new BayesParameters();
		params.setBasePath(args[2]);
		params.set("classifierType", args[3]);
		params.set("alpha_i", "1.0");
		params.set("defaultCat", "unknown");
		params.setGramSize(1);

		// set configuration
		Configuration conf = new Configuration();
		conf.set("bayes.parameters", params.toString());

		// create job
		Job job = new Job(conf, "Classifier");
		job.setJarByClass(ClassifierDriver.class);

		// specify input format
		job.setInputFormatClass(KeyValueTextInputFormat.class);

		// specify mapper & reducer
		job.setMapperClass(classifier.ClassifierMapper.class);
		job.setReducerClass(ClassifierReducer.class);

		// specify output types of mapper and reducer
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// specify input and output DIRECTORIES
		Path inPath = new Path(args[0]);
		Path outPath = new Path(args[1]);
		FileInputFormat.addInputPath(job, inPath);
		FileOutputFormat.setOutputPath(job, outPath); // output path

		// delete output directory
		try {
			FileSystem hdfs = outPath.getFileSystem(conf);
			if (hdfs.exists(outPath))
				hdfs.delete(outPath);
			hdfs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// run the job
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}
