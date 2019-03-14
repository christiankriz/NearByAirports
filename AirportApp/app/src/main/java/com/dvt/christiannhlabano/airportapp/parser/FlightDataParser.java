package com.dvt.christiannhlabano.airportapp.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by christiannhlabano on 2019/03/12.
 */

public class FlightDataParser {

    private HashMap<String, String> getAirline(JSONObject airlineJson) {
        HashMap<String, String> airlineInfo = new HashMap<>();
        String airlineName = "";
        String status = "";
        String departureTime = "";
        String destination = "";
        String flightNumber = "";

        Log.d("FlightDataParser", "jsonobject =" + airlineJson.toString());

        try {
            airlineName = airlineJson.getJSONObject("airline").getString("name");
            status = airlineJson.getString("status");
            departureTime = airlineJson.getJSONObject("departure").getString("scheduledTime");
            destination = airlineJson.getJSONObject("arrival").getString("iataCode");
            flightNumber = airlineJson.getJSONObject("flight").getString("number");

            airlineInfo.put("airlineName", airlineName);
            airlineInfo.put("status", status);
            airlineInfo.put("departureTime", departureTime);
            airlineInfo.put("destination", destination);
            airlineInfo.put("flightNumber", flightNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

       return airlineInfo;
     }

    private List<HashMap<String, String>> getAirlines(JSONArray jsonArray) {
        int count = jsonArray.length();
        List<HashMap<String, String>> airlineList = new ArrayList<>();
        HashMap<String, String> airline = null;

        for (int i = 0; i < count; i++) {
            try {
                airline = getAirline((JSONObject) jsonArray.get(i));
                airlineList.add(airline);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return airlineList;
    }

    public List<HashMap<String, String>> parse(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        Log.d("json data", jsonData);

        try {
            //jsonObject = new JSONObject(jsonData);
            jsonArray = new JSONArray(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAirlines(jsonArray);
    }
}

