import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class QueryReducer extends MapReduceBase
  implements Reducer<Text, Text, Text, Text> {
	
	public static String query;
	public static String[] arrQ;
	public void configure(JobConf job) {
	    query = job.get("query");
		arrQ = query.replace("(", "( ").replace(")", " )").split("\\s+");
		for(int i = 0;i<arrQ.length;i++){
			arrQ[i] = arrQ[i].replaceAll("\\s+", "");
		}
	}

	@Override
	public void reduce(Text key, Iterator<Text> value,
		OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		
		String[] arrQuery = arrQ;
		String fQuery = "";
		
		while(value.hasNext()){
			Text tVal = value.next();
			for(int i=0;i<arrQuery.length;i++){
				if(tVal.toString().equals(arrQuery[i]))
					arrQuery[i] = "true";
				else if(arrQuery[i].equals("AND"))
					arrQuery[i] = "&&";
				else if(arrQuery[i].equals("OR"))
					arrQuery[i] = "||";
				else if(arrQuery[i].equals("NOT"))
					arrQuery[i] = "!";
			}
			for(int i=0;i<arrQuery.length;i++){
				if(!(arrQuery[i].contains("(") || arrQuery[i].contains(")") 
						|| arrQuery[i].contains("&&") || arrQuery[i].contains("||")
						|| arrQuery[i].contains("!") || arrQuery[i].contains("true"))){
					arrQuery[i] = "false";
				}
			}	
		}
		for(int i=0;i<arrQuery.length;i++){
			fQuery += arrQuery[i] += " ";
		}
		fQuery += ";";
		try{
			ScriptEngineManager factory = new ScriptEngineManager();
	        ScriptEngine engine = factory.getEngineByName("JavaScript");
	        boolean result = (Boolean) engine.eval(fQuery);
	   
	        if(result){
	        	output.collect(key, new Text(query));
	        }
	        	
	     }catch(ScriptException e){
	        e.printStackTrace();
	     }
	}
}
