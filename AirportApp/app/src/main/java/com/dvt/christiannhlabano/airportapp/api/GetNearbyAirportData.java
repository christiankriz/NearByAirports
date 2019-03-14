package com.dvt.christiannhlabano.airportapp.api;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.dvt.christiannhlabano.airportapp.parser.NearbyAirportParser;
import com.dvt.christiannhlabano.airportapp.view.FlightDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by christiannhlabano on 2019/03/10.
 */

public class GetNearbyAirportData extends AsyncTask<Object, String, String> implements GoogleMap.OnMarkerClickListener {

    private String airportData;
    private GoogleMap mMap;
    String url;
    List<HashMap<String, String>> nearbyAirportList;

    private Context mContext;

    public GetNearbyAirportData(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadURL downloadURL = new DownloadURL();
        try {
            airportData = downloadURL.readUrl(url);
            //airportData = loadJSONFromAsset();
        } catch (IOException e) {
            e.printStackTrace();
        }

            return airportData;
        }

    @Override
    protected void onPostExecute(String s) {

        NearbyAirportParser parser = new NearbyAirportParser();
        nearbyAirportList = parser.parse(s);
        Log.d("nearbyAirportdata", "called parse method");
        showNearbyAirports(nearbyAirportList);
    }

    private void showNearbyAirports(List<HashMap<String, String>> nearbyAirportList) {
        for (int i = 0; i < nearbyAirportList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> airportList = nearbyAirportList.get(i);

            String airportName = airportList.get("airport_name");
            String iataCode = airportList.get("iata_code");
            double lat = Double.parseDouble(airportList.get("lat"));
            double lng = Double.parseDouble(airportList.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(airportName + " " + iataCode);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            mMap.addMarker(markerOptions);
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            mMap.setOnMarkerClickListener(this);

        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        String title = marker.getTitle();
        if(!title.equals("Current Location")){
            Intent i = new Intent(mContext, FlightDetails.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            i.putExtras(bundle);
            mContext.startActivity(i);
        }

        return false;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("nearby.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
