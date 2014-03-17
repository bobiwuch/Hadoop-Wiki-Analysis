import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextOutputFormat;

import edu.umd.cloud9.collection.wikipedia.WikipediaPageInputFormatOld;

public class GetXMLConfig {

  public static void main(String[] args) throws IOException {
    if (args.length != 3) {
      System.err.println("Usage: WikiSearchIndex <input path> <output path> <path to docid file>");
      System.exit(-1);
    }
    
    //value takes the query
    String docIDFilePath = args[2];
    
    JobConf conf = new JobConf(new Configuration(),GetXMLConfig.class);
    conf.setJobName("Wikipedia Search Index");

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));
    
    conf.setMapperClass(GetXMLMapper.class);
    conf.setReducerClass(GetXMLReducer.class);
    
    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);
    conf.setInputFormat(WikipediaPageInputFormatOld.class);
    conf.setOutputFormat(TextOutputFormat.class);
    conf.setMapOutputKeyClass(Text.class);
    conf.setMapOutputValueClass(Text.class);
    conf.set("docIDFilePath", docIDFilePath);
    JobClient.runJob(conf);
  }
}
