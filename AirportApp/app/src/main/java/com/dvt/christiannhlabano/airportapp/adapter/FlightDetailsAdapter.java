package com.dvt.christiannhlabano.airportapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dvt.christiannhlabano.airportapp.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by christiannhlabano on 2019/03/12.
 */

public class FlightDetailsAdapter extends  RecyclerView.Adapter<FlightDetailsAdapter.MyViewHolder> {

    private Context context;
    private List<HashMap<String, String>> flightList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView airlineName;
        public TextView status;
        public TextView departureTime;
        public TextView destination;
        public TextView flightNumber;
        public ImageView statusImage;


        public MyViewHolder(View view) {
            super(view);
            airlineName = view.findViewById(R.id.airline_name);
            status = view.findViewById(R.id.status_text);
            departureTime = view.findViewById(R.id.departure_time);
            destination = view.findViewById(R.id.txt_destination);
            destination = view.findViewById(R.id.txt_destination);
            flightNumber = view.findViewById(R.id.txt_flight_number);
            statusImage = view.findViewById(R.id.status_image);
        }
    }


    public FlightDetailsAdapter(List<HashMap<String, String>> flightList, Context context) {
        this.context = context;
        this.flightList = flightList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flight_infor_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HashMap<String, String> airportList = flightList.get(position);
        holder.airlineName.setText(airportList.get("airlineName"));
        String status = airportList.get("status");
        holder.status.setText(status);
        holder.flightNumber.setText(airportList.get("flightNumber"));
        String departutime = formatDate(airportList.get("departureTime"));
        holder.departureTime.setText(departutime);
        holder.destination.setText(airportList.get("destination"));

        if(status.equals("active")){
            holder.statusImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.boarding));
        }else{
            holder.statusImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.departed));
        }
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    /**
     * Formatting timestamp to `HH:mm` format
     * Input: 2019-02-03 'T' 00:15:42
     * Output: 00:15
     */
     private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}

