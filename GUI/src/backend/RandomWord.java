/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.net.HttpURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import static java.lang.System.out;
import java.net.MalformedURLException;

/**
 *
 * @author Autumn
 */
public class RandomWord {
    URL url;
    String key;
    public RandomWord() throws MalformedURLException{
        this.url = this.setUrl();
    }
    
    public static void main(String[] args) {
        try {
            RandomWord list = new RandomWord();
            String result = list.mainFunc();
            out.println(result);
        }catch (IOException | IndexOutOfBoundsException e) {
            out.println(e.toString());
        }
        
    }
    
    public String mainFunc() throws IOException {
        String result = this.sendGET();
        result = this.processWord(result);
        return result;
    }
    
    private URL setUrl() throws MalformedURLException {
        return new URL("https://random-word-api.herokuapp.com/word");
    }
    
    public String sendGET() throws IOException{
        HttpURLConnection req = (HttpURLConnection) this.url.openConnection();
        req.setRequestMethod("GET");
        req.connect();
        
        if (req.getResponseCode() == HttpURLConnection.HTTP_OK) {
            StringBuilder response = new StringBuilder();
            Scanner sc = new Scanner(this.url.openStream());
            while (sc.hasNextLine()) {
                response.append(sc.nextLine());
            }
            sc.close();
            return response.toString(); 
        }else {
            req.disconnect(); 
            return Integer.toString(req.getResponseCode()); 
        }
    }
    
    public String processWord(String word) {
        String[] var = word.split("\\W");
        for (String ans: var) {
            if (!ans.isEmpty()) {
                return (ans);
            }
        }
        return "";
    }
}
