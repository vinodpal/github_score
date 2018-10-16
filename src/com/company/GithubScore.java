package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GithubScore {
    private static final Logger logger = Logger.getLogger(GithubScore.class.getSimpleName());

    static final  String publicEventsUrl = "https://api.github.com/users/#username#/events/public";
    static final String receivedEventsUrl = "https://api.github.com/users/#username#/received_events";

    public static void main(String[] args) {
        System.out.println(getCountPublicEvents("antirez",new Date()));
        System.out.println(getCountReceivedEvents("antirez",new Date()));
        System.out.println(getScore("antirez",new Date()));

    }


    static long getScore(String userName, Date date){

        long score = 0L;
        if(date==null){
            date = Calendar.getInstance().getTime();
        }
        long countPublicEvent=getCountPublicEvents(userName,date);
        logger.info("Successfully count public events with ");
        long countReceivedEvents = getCountReceivedEvents(userName,date);
        logger.info("Successfully count received events");
        score = countPublicEvent+countReceivedEvents;
        return score;
    }

    static long getCountPublicEvents(String userName, Date date){
        try {
            JSONArray jsonArrayPublicEvents = JsonReader.readJsonFromUrl(publicEventsUrl.replace("#username#", userName));
            return jsonArrayPublicEvents.length();
//            return JsonReader.countJsonOnDate(jsonArrayPublicEvents,date);
        }catch (Exception ex){
            logger.log(Level.SEVERE,"Error throw while counting public events",ex);
        }
        return  0;
    }

    static long getCountReceivedEvents(String userName, Date date){
        try {
            JSONArray jsonArrayReceivedEvents = JsonReader.readJsonFromUrl(receivedEventsUrl.replace("#username#", userName));
            return jsonArrayReceivedEvents.length();
//            return JsonReader.countJsonOnDate(jsonArrayReceivedEvents,date);
        }catch (Exception ex){
            logger.log(Level.SEVERE,"Error throw while counting received events",ex);
        }
        return  0;
    }



}
