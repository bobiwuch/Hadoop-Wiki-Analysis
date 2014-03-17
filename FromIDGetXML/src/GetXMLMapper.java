import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.Arrays;
//import java.util.Scanner;



import java.util.Scanner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import edu.umd.cloud9.collection.wikipedia.WikipediaPage;

public class GetXMLMapper extends MapReduceBase
  implements Mapper<LongWritable, WikipediaPage, Text, Text> {
  
	public static String docIDFilePath;
	public static String strIds;
	public void configure(JobConf job) {
	    docIDFilePath = job.get("docIDFilePath");
	    File docIDs = new File(docIDFilePath);
		Scanner in;
		strIds = "";
		try {
			in = new Scanner(docIDs);
			while(in.hasNextLine()){
				String line = in.nextLine();
				String[] temp = line.split("\\s+");
				strIds += temp[0] + ",";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  public void map(LongWritable key, WikipediaPage value,
          OutputCollector<Text,Text> output, Reporter reporter)
      throws IOException {
	  
	  String[] idList = strIds.split(",");
	  
	  for(String id : idList){
		  if(value.getDocid().equals(id)){
			  output.collect(new Text(value.getDisplayContent()), new Text(""));
		  }
	  }
	}
}
