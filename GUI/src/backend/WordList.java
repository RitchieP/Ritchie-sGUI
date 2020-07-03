/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import static java.lang.System.out;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author Autumn
 */
public class WordList {
    URL url;
    String key;
    public WordList() {
        this.url = this.setUrl();
        this.key = this.getKey();
    }
    
    public static void main(String[] args) {
        WordList list = new WordList();
        String key = list.getKey();
        out.println(key);
        try {
            out.println(list.sendGET());
        }catch (IOException e) {
            out.println(e.toString());
        }
        
    }
    
    private URL setUrl() {
        try {
            return new URL("https://xkubist-random-word-v1.p.rapidapi.com/run.cgi");
        }catch (java.net.MalformedURLException e) {
            out.println(e.toString());
            return null;
        }
    }
    
    private String getKey() {
        StringBuilder dir = new StringBuilder();
        dir.append(System.getProperty("user.dir"));
        dir.append("/credentials.txt");
        
        File inputFile = new File(dir.toString());
        try {
            Scanner inputStream = new Scanner(inputFile);
            while (inputStream.hasNextLine()) {
                key = inputStream.nextLine();
            }
        }catch (IOException e) {
            return "";
        }
        
        return key;
    }
    
    public String sendGET() throws IOException{
        HttpURLConnection req = (HttpURLConnection) this.url.openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("X-Mashape-Key", this.key);
        req.setRequestProperty("Accept", "application/json");
        
        if (req.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(req.getInputStream()));
            StringBuilder response = new StringBuilder();
            while (inputStream.readLine() != null) {
                response.append(inputStream.readLine());
            }
            inputStream.close();
            return response.toString();
        }else {
            req.disconnect();
            return Integer.toString(req.getResponseCode());
        }
    }
    
    public void writeFile(String response) {
        StringBuilder dir = new StringBuilder();
        dir.append(System.getProperty("user.dir"));
        dir.append("/response.txt");
        File outputFile = new File(dir.toString());
        try {
            FileWriter fw = new FileWriter(outputFile);
            fw.write(response);
            fw.close();
        } catch (IOException e) {
            out.println(e.toString());
        }
    }
}
