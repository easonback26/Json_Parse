import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONTraverseExample {

    public static void main(String[] args) throws IOException, ParseException {
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader("data.json"));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        handleValue(obj);
    }

    static void handleValue(Object value) {
        if (value instanceof JSONObject) {
            handleJSONObject((JSONObject) value);
        } else if (value instanceof JSONArray) {
            handleJSONArray((JSONArray) value);
        } else {
            System.out.print("value: ");
            System.out.println(value);
        }
    }

    private static void handleJSONObject(JSONObject jsonObject) {
        jsonObject.keySet().forEach(key -> {
            Object value = jsonObject.get(key);
            System.out.print("key: ");
            System.out.println(key);
            handleValue(value);
        });
    }

    private static void handleJSONArray(JSONArray jsonArray)
    {
        jsonArray.iterator().forEachRemaining(JSONTraverseExample::handleValue);
    }


}
