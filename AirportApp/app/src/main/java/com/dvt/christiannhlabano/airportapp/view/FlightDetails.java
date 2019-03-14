package com.dvt.christiannhlabano.airportapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dvt.christiannhlabano.airportapp.R;
import com.dvt.christiannhlabano.airportapp.api.GetDepartureFlightData;

public class FlightDetails extends AppCompatActivity {
    String iataCode;
    String airportName;
    String url = null;
    private TextView airportNameView;
    private RecyclerView recyclerView;
    private LinearLayout loadingImage;
    private LinearLayout noResult;
    private ImageView backButton;
    Object dataTransfer[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_infor);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String titleText = bundle.getString("title");
        iataCode = titleText.substring(titleText.lastIndexOf(" ") + 1);
        airportName = titleText.substring(0, titleText.lastIndexOf(" "));
        airportNameView = findViewById(R.id.lbl_airport);
        loadingImage = findViewById(R.id.line_loading);
        noResult = findViewById(R.id.no_result);
        airportNameView.setText(airportName);
        dataTransfer = new Object[4];
        recyclerView = findViewById(R.id.recycler_view);
        backButton = findViewById(R.id.back_image);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getDapartureData();

    }

    private void getDapartureData(){
        GetDepartureFlightData getDepartureFlightData = new GetDepartureFlightData(this);
        url = getUrl();
        dataTransfer[0] = url;
        dataTransfer[1] = recyclerView;
        dataTransfer[2] = loadingImage;
        dataTransfer[3] = noResult;
        getDepartureFlightData.execute(dataTransfer);
    }




    private String getUrl()
    {
        String key = "01f30f-5a09d2";
        StringBuilder flightDepartureUrl = new StringBuilder("http://aviation-edge.com/v2/public/timetable?");
        flightDepartureUrl.append("key="+key);
        flightDepartureUrl.append("&iataCode="+iataCode);
        flightDepartureUrl.append("&type=departure");

        Log.d("FlightDetails", "url = "+flightDepartureUrl.toString());

        return flightDepartureUrl.toString();
    }

}
