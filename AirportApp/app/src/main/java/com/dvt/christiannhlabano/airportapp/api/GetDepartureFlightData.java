package com.dvt.christiannhlabano.airportapp.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.dvt.christiannhlabano.airportapp.adapter.FlightDetailsAdapter;
import com.dvt.christiannhlabano.airportapp.parser.FlightDataParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by christiannhlabano on 2019/03/12.
 */

public class GetDepartureFlightData extends AsyncTask<Object, String, String> {

    private String flightData;
    String url;
    Context mContext;
    RecyclerView recyclerView;
    LinearLayout loadingImage;
    LinearLayout noResult;

    public GetDepartureFlightData(Context context){
        this.mContext = context;
    }

    @Override
    protected String doInBackground(Object... objects){
        url = (String)objects[0];
        recyclerView = (RecyclerView) objects[1];
        loadingImage = (LinearLayout) objects[2];
        noResult = (LinearLayout) objects[3];
        DownloadURL downloadURL = new DownloadURL();
        try {
            flightData = downloadURL.readUrl(url);
            //flightData = loadJSONFromAsset();
        } catch (IOException e) {
            e.printStackTrace();
       }
        return flightData;
    }

    @Override
    protected void onPostExecute(String s){
        List<HashMap<String, String>> flightList;
        FlightDataParser parser = new FlightDataParser();
        if(s.indexOf("No Record Found") > 0){
            loadingImage.setVisibility(View.GONE);
            noResult.setVisibility(View.VISIBLE);
        }else{
            flightList = parser.parse(s);
            loadingImage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            showFlightInfo(flightList);
        }
        Log.d("flightdata","called flight parser method");
    }

    private void showFlightInfo(List<HashMap<String, String>> flightList)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        FlightDetailsAdapter mAdapter = new FlightDetailsAdapter(flightList, mContext);
        recyclerView.setAdapter(mAdapter);

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("timetable.json");
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
