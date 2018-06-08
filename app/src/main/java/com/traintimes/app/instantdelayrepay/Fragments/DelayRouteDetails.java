package com.traintimes.app.instantdelayrepay.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.traintimes.app.instantdelayrepay.Adapter.Delays_Routes_Adapter;
import com.traintimes.app.instantdelayrepay.Home;
import com.traintimes.app.instantdelayrepay.Objects.CancelDetailsObject;
import com.traintimes.app.instantdelayrepay.Objects.CancelledDetail;
import com.traintimes.app.instantdelayrepay.Objects.DelayRoutes;
import com.traintimes.app.instantdelayrepay.Objects.Header;
import com.traintimes.app.instantdelayrepay.Objects.ListDate;
import com.traintimes.app.instantdelayrepay.Objects.ListItem;
import com.traintimes.app.instantdelayrepay.Objects.PostObject;
import com.traintimes.app.instantdelayrepay.Objects.ResponseByApi;
import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.Session.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelayRouteDetails extends Fragment {


    private RecyclerView recyclerView;
    private Delays_Routes_Adapter mAdapter;

    List<Header> List;
    String From, To;
    List<String> Dates = new ArrayList<>();
    ListDate listDate = new ListDate();
    View view;

    public DelayRouteDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delay_route_details, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        List = new ArrayList<>();
        this.view = view;
        ((Home) getActivity()).Visibileback();


        From = getArguments().getString("From");
        To = getArguments().getString("To");


        MainCall();

        return view;
    }


    public void ShowList() {
        mAdapter = new Delays_Routes_Adapter(List, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    ProgressDialog progressDialog;


    private void MainCall() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);

        progressDialog.show();
        String url = "http://192.64.115.101:8080/cancelDetailsBatch";
        MyApplication myApplication = (MyApplication) getActivity().getApplicationContext();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        PostObject postObject = new PostObject();
        postObject.setSingleTicketPrice(Long.valueOf(myApplication.getUser().getPrice()));
        postObject.setToStation(To);
        postObject.setFromStation(From);
        final String requestBody = new Gson().toJson(postObject);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    JSONObject jsonObj = new JSONObject(response);
                    JSONObject jsonObj1 = jsonObj.getJSONObject("cancelledDetails");

                    Map<String, ArrayList<CancelledDetail>> map = new HashMap<>();
                    map = (Map<String, ArrayList<CancelledDetail>>) gson.fromJson(jsonObj1.toString(), map.getClass());


                    for (String s : map.keySet()) {

                        if (map.get(s).size() != 0) {
                            Dates.add(s);


                            Header header = new Header();
                            header.setDate(s);

                            List<ListDate> listDates = new ArrayList<>();

                            for (int i = 0; i < map.get(s).size(); i++) {

                                String JsonString = gson.toJson(map.get(s).get(i));
                                CancelledDetail cancelDetailsObject = gson.fromJson(JsonString, CancelledDetail.class);

                                listDate = new ListDate();
                                listDate.setAtocName(cancelDetailsObject.getmAtocName());
                                listDate.setAtocCode(cancelDetailsObject.getmAtocCode());
                                listDate.setAmount(cancelDetailsObject.getRefundAmount());
                                listDate.setDelay(String.valueOf(cancelDetailsObject.getmTimeDelayed()));
                                listDate.setDestination(cancelDetailsObject.getToStation());
                                listDate.setOrigin(cancelDetailsObject.getFromStation());
                                if (cancelDetailsObject.getCancelled()) {
                                    listDate.setStatus("Delayed");
                                } else {
                                    listDate.setStatus("Cancelled");
                                }
                                listDate.setTime(cancelDetailsObject.getDepartureTime());

                                listDates.add(listDate);
                            }
                            header.setListDates(listDates);
                            List.add(header);
                        }
                        if (List.size() == 0) {
                            view.findViewById(R.id.NOItem).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.recycler_view).setVisibility(View.GONE);
                        } else {
                            view.findViewById(R.id.NOItem).setVisibility(View.GONE);
                            view.findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
                        }
                        ShowList();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    view.findViewById(R.id.NOItem).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.recycler_view).setVisibility(View.GONE);
                }
                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();


            }
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        queue.add(stringRequest);
    }


}
