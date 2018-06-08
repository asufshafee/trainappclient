package com.traintimes.app.instantdelayrepay.Adapter;

/**
 * Created by GeeksEra on 4/27/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.traintimes.app.instantdelayrepay.Objects.DelayRoutes;
import com.traintimes.app.instantdelayrepay.Objects.Header;
import com.traintimes.app.instantdelayrepay.Objects.ListDate;
import com.traintimes.app.instantdelayrepay.Objects.ListItem;
import com.traintimes.app.instantdelayrepay.Objects.RailwayCompany;
import com.traintimes.app.instantdelayrepay.Objects.Stations;
import com.traintimes.app.instantdelayrepay.Objects.TrainStationsAndCode;
import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.WebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Delays_Routes_Adapter extends RecyclerView.Adapter<Delays_Routes_Adapter.MyViewHolder> {

    private List<Header> ServiceList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Date;

        private RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);
            Date = (TextView) view.findViewById(R.id.date);
            recyclerView = view.findViewById(R.id.recycler_view);
        }
    }


    public Delays_Routes_Adapter(List<Header> moviesList, Context context) {
        this.ServiceList = moviesList;
        this.context = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header, parent, false);
        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Header header = ServiceList.get(position);
        holder.Date.setText(header.getDate());
        ShowList(header.getListDates(), holder.recyclerView);

    }

    @Override
    public int getItemCount() {
        return ServiceList.size();
    }


    public void ShowList(List<ListDate> List, RecyclerView recyclerView) {
        Delays_Adapter mAdapter = new Delays_Adapter(List, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}

