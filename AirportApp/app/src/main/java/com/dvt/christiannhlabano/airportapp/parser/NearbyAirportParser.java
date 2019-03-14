package com.dvt.christiannhlabano.airportapp.parser;

import java.util.HashMap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christiannhlabano on 2019/03/10.
 */

public class NearbyAirportParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
        {
            HashMap<String, String> googlePlaceMap = new HashMap<>();
            String airName = "--NA--";
            String iataCode = "--NA--";
            String latitude= "";
            String longitude="";

            Log.d("NearbyAirportParser","jsonobject ="+googlePlaceJson.toString());


            try {
                if (!googlePlaceJson.isNull("nameAirport")) {
                    airName = googlePlaceJson.getString("nameAirport");
                }
                if (!googlePlaceJson.isNull("codeIataAirport")) {
                    iataCode = googlePlaceJson.getString("codeIataAirport");
                }

                latitude = googlePlaceJson.getString("latitudeAirport");
                longitude = googlePlaceJson.getString("longitudeAirport");

                googlePlaceMap.put("airport_name", airName);
                googlePlaceMap.put("iata_code", iataCode);
                googlePlaceMap.put("lat", latitude);
                googlePlaceMap.put("lng", longitude);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return googlePlaceMap;

        }
        private List<HashMap<String, String>>getPlaces(JSONArray jsonArray)
        {
            int count = jsonArray.length();
            List<HashMap<String, String>> placelist = new ArrayList<>();
            HashMap<String, String> placeMap = null;

            for(int i = 0; i<count;i++)
            {
                try {
                    placeMap = getPlace((JSONObject) jsonArray.get(i));
                    placelist.add(placeMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placelist;
        }

        public List<HashMap<String, String>> parse(String jsonData)
        {
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;

            Log.d("json data", jsonData);

            try {
                //jsonObject = new JSONObject(jsonData);
                jsonArray = new JSONArray(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return getPlaces(jsonArray);
        }
}
