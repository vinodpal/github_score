package com.company;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    static Logger logger = Logger.getLogger(JsonReader.class.getSimpleName());

    public static final String CREATED_AT = "created_at";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            logger.info("Successfully read url and store in  jsonArray!");
            return json;
        } finally {
            is.close();
        }
    }

    static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static long countJsonOnDate(JSONArray jsonArray, Date date){
        long count =0;
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String sDate="";
            Date date1 = null;
            for(int itr = 0; itr<jsonObject.length();++itr){
                sDate = jsonObject.get(CREATED_AT).toString();
                sDate = sDate.substring(0,sDate.indexOf("T"));
                date1=simpleDateFormat.parse(sDate);
                if(date.compareTo(date1)==0){
                    ++count;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) throws IOException, JSONException {
//        JSONObject jsonEvents = readJsonFromUrl("https://api.github.com/users/antirez/events/public");
//        JSONObject jsonInteracions = readJsonFromUrl("https://api.github.com/users/antirez/events/public");
//        System.out.println(jsonEvents.toString());
    }
}