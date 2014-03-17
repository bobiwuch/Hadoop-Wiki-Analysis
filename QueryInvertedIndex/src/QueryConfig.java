import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;


public class QueryConfig {

  public static void main(String[] args) throws IOException {
    if (args.length != 3) {
      System.err.println("Usage: QueryConfig <input path> <output path> <query>");
      System.exit(-1);
    }
    
    //value takes the query
    String value = args[2];
    
    JobConf conf = new JobConf(new Configuration(),QueryConfig.class);
    conf.setJobName("Query Index");

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));
    
    conf.setMapperClass(QueryMapper.class);
    conf.setReducerClass(QueryReducer.class);
    
    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);
    conf.setInputFormat(KeyValueTextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);
    conf.setMapOutputKeyClass(Text.class);
    conf.setMapOutputValueClass(Text.class);
    conf.set("query", value);

    JobClient.runJob(conf);
  }
}
