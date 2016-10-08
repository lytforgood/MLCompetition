package classifier;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.classifier.ClassifierResult;
import org.apache.mahout.classifier.bayes.Algorithm;
import org.apache.mahout.classifier.bayes.BayesAlgorithm;
import org.apache.mahout.classifier.bayes.BayesParameters;
import org.apache.mahout.classifier.bayes.CBayesAlgorithm;
import org.apache.mahout.classifier.bayes.ClassifierContext;
import org.apache.mahout.classifier.bayes.Datastore;
import org.apache.mahout.classifier.bayes.InMemoryBayesDatastore;
import org.apache.mahout.classifier.bayes.InvalidDatastoreException;
import org.apache.mahout.common.nlp.NGrams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import classifier.Counter;

public class ClassifierMapper extends Mapper<Text, Text, Text, IntWritable> {

	private Text outKey = new Text();
	private static final IntWritable ONE = new IntWritable(1);
	
	private int gramSize = 1;
	private ClassifierContext classifier;
	private String defaultCategory;

	private static final Logger log = LoggerFactory.getLogger(ClassifierMapper.class);

	/**
	 * Parallel Classification
	 * 
	 * @param key
	 *          The label
	 * @param value
	 *          the features (all unique) associated w/ this label
	 * @param context
	 */
	public void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {

		String docLabel = "";
		String userID = key.toString();
		List<String> ngrams = new NGrams(value.toString(), gramSize).generateNGramsWithoutLabel();
		try {
			ClassifierResult result;
			result = classifier.classifyDocument(ngrams.toArray(new String[ngrams.size()])
					, defaultCategory);
			docLabel = result.getLabel();			
		} catch (InvalidDatastoreException e) {
			log.error(e.toString(), e);
			context.getCounter(Counter.FAILDOCS).increment(1);
		}
		// key is userID and docLabel
		outKey.set(userID+"|"+docLabel);
		context.write(outKey, ONE);
	}


	/**
	 * read the model
	 * @throws IOException 
	 */
	@Override
	public void setup(Context context) throws IOException{
		// get bayes parameters
		Configuration conf = context.getConfiguration();
		BayesParameters params = new BayesParameters(conf.get("bayes.parameters", ""));
		log.info("Bayes Parameter {}", params.print());  

		Algorithm algorithm;
		Datastore datastore;
		if ("bayes".equalsIgnoreCase(params.get("classifierType"))) {
			algorithm = new BayesAlgorithm();
			datastore = new InMemoryBayesDatastore(params);
		} else if ("cbayes".equalsIgnoreCase(params.get("classifierType"))) {
			algorithm = new CBayesAlgorithm();
			datastore = new InMemoryBayesDatastore(params);
		} else {
			throw new IllegalArgumentException(
					"Unrecognized classifier type: " + params.get("classifierType"));
		}

		classifier = new ClassifierContext(algorithm, datastore);
		try {
			classifier.initialize();
		} catch (InvalidDatastoreException e) {
			log.error(e.toString(), e);
		}
		
		defaultCategory = params.get("defaultCat");
		gramSize = params.getGramSize();
	}



}
