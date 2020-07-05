/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Autumn
 */
public class Meaning {
    
    public String app_key;
    public URL url;
    public String word;
    public boolean valid;
    
    public Meaning(String app_key, String word) {
        this.app_key = app_key;
        this.word = word;
        this.url = this.setURL();
    }
    
    public static void main(String[] args) {
        Meaning meaning = new Meaning("", "hello"); // insert your api key in the first positional argument
        try {
            String response = meaning.sendGet();
            System.out.println(response);
            response = meaning.parseJSON(response);
            response = meaning.processString(response);
            System.out.println(response);
            
        }catch (IOException | ParseException e) {
            System.out.println(e.toString());
        }
    }
    
    // TODO: this method returns similar words when the meaning of the word is not found, instead of the warning message
    public static String getMeaning(String word) {
        Meaning meaning = new Meaning("", word); // insert your api key in the first positional argument
        try {
            String response = meaning.sendGet();
            response = meaning.parseJSON(response);
            response = meaning.processString(response);
            return response;
            
        }catch (IOException | ParseException e) {
            System.out.println(e);
            return "Sorry. Could not find definition.";
        }
    }
    
    private URL setURL() {
        String tmp = "https://dictionaryapi.com/api/v3/references/learners/json/" + this.word + "?key=" + this.app_key;
        try {
            return new URL(tmp);
        }catch(MalformedURLException e) {
            return null;
        }
    }
    
    public String sendGet() throws IOException{
        HttpsURLConnection conn = (HttpsURLConnection)this.url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            Scanner sc = new Scanner(this.url.openStream());
            StringBuilder result = new StringBuilder();
            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
            sc.close();
            conn.disconnect();
            return result.toString();
        }else {
            return Integer.toString(conn.getResponseCode());
        }
    }
    
    public String parseJSON(String response) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object json = parser.parse(response);
        JSONArray tmp = (JSONArray)json;
        try {
            JSONObject arr = (JSONObject)tmp.get(0);
            JSONObject str = (JSONObject)arr.get("meta");
            str = (JSONObject)str.get("app-shortdef");
            tmp = (JSONArray)str.get("def");

            StringBuilder outcome = new StringBuilder();

            for (Object def: tmp) {
                outcome.append((String)def);
            }

            return ("Meaning: \n" + outcome.toString());
        }catch (ClassCastException e) {
            return ("Similar words: \n" + tmp.toString());
        }
        
    }
    
    public String processString(String raw) {
        StringBuilder result = new StringBuilder();
        String[] tmp = raw.split("\\{/*\\w+\\}");
        for (String substring: tmp) {
            if (!substring.isEmpty()) {
                String toAppend = "\n" + substring.trim();
                result.append(toAppend);
            }   
        }
        
        return result.toString();
    }
}
