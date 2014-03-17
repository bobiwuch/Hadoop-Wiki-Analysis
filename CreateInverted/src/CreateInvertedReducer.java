import java.io.IOException;
import java.util.HashSet;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class CreateInvertedReducer extends MapReduceBase
  implements Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterator<Text> values,
		OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		
		HashSet<String> docIdPos = new HashSet<String>();
		while(values.hasNext()){
			docIdPos.add("{"+values.next().toString()+"}");
		}
		output.collect(key, new Text(docIdPos.toString().replaceAll("\\[|\\]", "")));
	}
}
