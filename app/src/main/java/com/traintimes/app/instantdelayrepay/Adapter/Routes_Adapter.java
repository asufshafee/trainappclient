package com.traintimes.app.instantdelayrepay.Adapter;

/**
 * Created by GeeksEra on 4/27/2018.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.traintimes.app.instantdelayrepay.Fragments.DelayRouteDetails;
import com.traintimes.app.instantdelayrepay.Home;
import com.traintimes.app.instantdelayrepay.Objects.Route;
import com.traintimes.app.instantdelayrepay.R;

import java.util.List;

public class Routes_Adapter extends RecyclerView.Adapter<Routes_Adapter.MyViewHolder> {

    private List<Route> ServiceList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView From, To;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            From = (TextView) view.findViewById(R.id.From);
            To = (TextView) view.findViewById(R.id.To);
            cardView = view.findViewById(R.id.card);
            view.setSelected(true);
        }
    }


    public Routes_Adapter(List<Route> moviesList, Context context) {
        this.ServiceList = moviesList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Route route = ServiceList.get(position);

        holder.From.setText(route.getFromStation());
        holder.To.setText(route.getToStation());
//        holder.cardView.setFocusable(true);
//        holder.cardView.setFocusableInTouchMode(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DelayRouteDetails delayRouteDetails = new DelayRouteDetails();
                Bundle bundle = new Bundle();
                bundle.putString("From", route.getFromStationCode());
                bundle.putString("To", route.getToStationCode());
                delayRouteDetails.setArguments(bundle);
                ((Home) context).ShowFragemnt(delayRouteDetails);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ServiceList.size();
    }


}

