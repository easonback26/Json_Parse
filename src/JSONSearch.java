import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JSONSearch {

    private static JSONObject JSONOutput = new JSONObject();
    private static JSONArray ja = new JSONArray();

    public static void main(String[] args) throws IOException, ParseException {
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader("data.json"));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        //Create ArrayList to search a certain amount of keys.
        ArrayList<String> searchKeys = new ArrayList<String>();
        searchKeys.add("orderType");
        searchKeys.add("dueToday");
        searchKeys.add("email");
        searchKeys.add("shippingAddress");

        //Traverse each item in the ArrayList and do the serach.
        for (String i : searchKeys)
        {
            ja = new JSONArray();
            searchValue(jo,i);

            //After each search, put the key and JSONArray into the Output.
            JSONOutput.put(i,ja);
        }

        System.out.println(JSONOutput);

        // writing JSON to file:"JSONExample.json" in cwd
        PrintWriter pw = new PrintWriter("JSONSearchOutput.json");
        pw.write(JSONOutput.toJSONString());

        pw.flush();
        pw.close();
    }

    private  static void searchValue(Object value, Object searchKey) {
        /*
        Depth first search with recursion.
         */

        //If JSONObject, handle with searchJSONObject
        if (value instanceof JSONObject)
        {
            searchJSONObject((JSONObject) value, searchKey);
        }
        //If JSONArray, handle with searchJSONArray
        else if (value instanceof JSONArray) {
            searchJSONArray((JSONArray) value, searchKey);
        }

    }

    private static void searchJSONObject(JSONObject jsonObject, Object searchKey) {
        jsonObject.keySet().forEach(key -> {
            Object value = jsonObject.get(key);

            //seach for the key, if the key matches, put it into the JSONArray
            if (key.equals(searchKey))
            {
                ja.add(jsonObject.get(key));
            }

            //Recursion on each value.
            // (In case the value is not a plain string, but a JSONObject or JSONArray. )
            searchValue(value,searchKey);
        });
    }
//
    private static void searchJSONArray(JSONArray jsonArray, Object searchKey)
    {
        //Traverse the JSONArray, for each item, do the searchValue.
        jsonArray.iterator().forEachRemaining(element -> {
            searchValue(element, searchKey);
        });
    }

}
