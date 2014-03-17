import java.io.IOException;
//import java.util.Arrays;
//import java.util.Scanner;


import java.util.Arrays;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import edu.umd.cloud9.collection.wikipedia.WikipediaPage;

public class CreateInvertedMapper extends MapReduceBase
  implements Mapper<LongWritable, WikipediaPage, Text, Text> {
	public static String[] stopWords;
	public void configure(JobConf job) {
		stopWords = job.getStrings("stopWords");
	}
  public void map(LongWritable key, WikipediaPage value,
          OutputCollector<Text,Text> output, Reporter reporter)
      throws IOException {
	  	  
	  	Text id = new Text(value.getDocid());
	  	String content[] = {};
	  	try{
	  		content = value.getContent().replaceAll("[^a-zA-Z]", " ").toLowerCase().split("\\s+");
	  	}catch(NullPointerException e){
	  		e.printStackTrace();
	  	}
		int position = 0;
		for (String word : content) {
			++position;
			if(!(Arrays.binarySearch(stopWords, word)>=0))
				output.collect(new Text(word), new Text(id+";"+position));
		}   
	}
}
