package com.traintimes.app.instantdelayrepay.Adapter;

/**
 * Created by GeeksEra on 4/27/2018.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.traintimes.app.instantdelayrepay.Objects.ListDate;
import com.traintimes.app.instantdelayrepay.Objects.RailwayCompany;
import com.traintimes.app.instantdelayrepay.Objects.Service;
import com.traintimes.app.instantdelayrepay.Objects.Stations;
import com.traintimes.app.instantdelayrepay.Objects.TrainStationsAndCode;
import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.WebView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Delays_Adapter extends RecyclerView.Adapter<Delays_Adapter.MyViewHolder> {

    private List<ListDate> ServiceList;
    private List<Service> ServiceListALL;
    Context context;
    String FromCode;
    String ToCode;

    Stations stations;

    ProgressDialog progressDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView From, To, DateTime, Status, Estimated, TimeDelay;
        ImageView ImageLogo;
        Button Claim;

        public MyViewHolder(View view) {
            super(view);
            From = (TextView) view.findViewById(R.id.From);
            To = (TextView) view.findViewById(R.id.To);
            DateTime = (TextView) view.findViewById(R.id.DateTime);
            Status = (TextView) view.findViewById(R.id.Status);
            Estimated = (TextView) view.findViewById(R.id.Estimated);
            TimeDelay = (TextView) view.findViewById(R.id.TimeDelay);
            ImageLogo = (ImageView) view.findViewById(R.id.ImageLogo);
            Claim = (Button) view.findViewById(R.id.ClaimNOw);
        }
    }


    public Delays_Adapter(List<ListDate> moviesList, Context context) {
        this.ServiceList = moviesList;
        this.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Wait.......");
        progressDialog.setCancelable(false);


        String Json = "";
        try {
            Json = ReadFromfile("Stations.txt", context);

        } catch (Exception Ex) {

        }

        Gson gson = new Gson();
        stations = gson.fromJson(Json, Stations.class);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delays, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListDate service = ServiceList.get(position);


        String NameOrigion = null;
        String NameDestinvation = null;
        String Url = null;
        String Logo = null;


        for (TrainStationsAndCode trainStationsAndCode : stations.getTrainStationsAndCodes()) {


            if (trainStationsAndCode.getCRSCode().equals(service.getOrigin())) {
                NameOrigion = trainStationsAndCode.getStationName();
            }


            if (trainStationsAndCode.getCRSCode().equals(service.getDestination())) {
                NameDestinvation = trainStationsAndCode.getStationName();
            }
        }

        for (RailwayCompany railwayCompany : stations.getRailwayCompanies()) {


            if (railwayCompany.getATOCCode().equals(service.getAtocCode())) {
                Url = railwayCompany.getURL();
                Logo = railwayCompany.getImageURL();
//                Picasso.with(context).load(Url).into(holder.ImageLogo);
                Glide.with(context).load(Logo).into(holder.ImageLogo);


            }
        }

        String One = service.getTime().substring(0, 2);
        String Tow = service.getTime().substring(2, 4);

        if (One.length() == 1) {
            One = "0" + One;
        }

        if (Tow.length() == 1) {
            Tow = "0" + Tow;
        }

        String Date = One + ":" + Tow;

        holder.From.setText(NameOrigion);
        holder.To.setText(NameDestinvation);
        holder.DateTime.setText("Departed Time : " + Date);

        holder.Status.setText(service.getStatus());

        Double aDouble = Double.valueOf(service.getAmount().replace("£", ""));
        DecimalFormat df = new DecimalFormat("0.00");
        holder.Estimated.setText("Refund: £" + String.valueOf(df.format(aDouble)));
        int minites = (int) Double.parseDouble(service.getDelay());
        holder.TimeDelay.setText("Delay: " + String.valueOf(minites) + " minutes");


        final String finalUrl = Url;
        holder.Claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, WebView.class);
                intent.putExtra("Url", finalUrl);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return ServiceList.size();
    }

    public String ReadFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

}

