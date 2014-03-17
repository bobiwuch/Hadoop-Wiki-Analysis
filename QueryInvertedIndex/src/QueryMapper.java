import java.io.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class QueryMapper extends MapReduceBase
  implements Mapper<Text, Text, Text, Text> {
	
	public static String query;
	public static String[] arrQuery;
	public static String[] noParen;
	public void configure(JobConf job) {
	    query = job.get("query");
		arrQuery = query.replace("(", "( ").replace(")", " )").split("\\s+");
		noParen = query.replaceAll("[()]", " ").split("\\s+");
	}
	
  public void map(Text key, Text value,
          OutputCollector<Text,Text> output, Reporter reporter)
      throws IOException {
	  
	  for(String x : noParen){
		  if(key.toString().equals(x)){
			  String sVal = value.toString().replaceAll(" ", "");
			  String[] idPos = sVal.split(",");
			  
			  for(int i = 0;i<idPos.length;i++){
					idPos[i] = idPos[i].replaceAll("[{}]", "");
					String[] temp = idPos[i].split(";");
					output.collect(new Text(temp[0]), key);
			  }
		  }
	  }
	}
}
