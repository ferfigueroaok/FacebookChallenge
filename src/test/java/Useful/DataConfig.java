package Useful;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataConfig {

	public DataConfig() {}
	
	public static JSONObject GetJson(String path) throws FileNotFoundException, IOException, ParseException {
				
		JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(path));
        JSONObject json = (JSONObject) obj;       
        
        return json;
	}
	
}
