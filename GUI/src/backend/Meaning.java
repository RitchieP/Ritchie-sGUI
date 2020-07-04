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

/**
 *
 * @author Autumn
 */
public class Meaning {
    
    public String app_id;
    public String app_key;
    public URL url;
    public String word;
    
    public Meaning(String app_key, String word) {
        this.app_key = app_key;
        this.word = word;
        this.url = this.setURL();
    }
    
    public static void main(String[] args) {
        Meaning meaning = new Meaning("", "hello"); // insert your api key in the first positional argument
        try {
            System.out.println(meaning.sendGet());
        }catch (IOException e) {
            System.out.println(e.toString());
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
}
