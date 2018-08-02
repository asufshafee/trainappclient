package com.traintimes.app.instantdelayrepay.Fragments;


import android.app.Dialog;
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
import com.traintimes.app.instantdelayrepay.Adapter.Delays_Adapter;
import com.traintimes.app.instantdelayrepay.Home;
import com.traintimes.app.instantdelayrepay.Objects.CancelledDetail;
import com.traintimes.app.instantdelayrepay.Objects.ListDate;
import com.traintimes.app.instantdelayrepay.Objects.PostObject;
import com.traintimes.app.instantdelayrepay.Objects.ResponseByApi;
import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.Session.MyApplication;
import com.google.gson.Gson;
import com.traintimes.app.instantdelayrepay.util.AppUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Find_Delays_Details extends Fragment {


    public Find_Delays_Details() {
        // Required empty public constructor
    }

    String Start, End, From, To;
    public static String FromCode, ToCode;


    private RecyclerView recyclerView;
    private Delays_Adapter mAdapter;

    View view;

    Dialog progressDialog;


    List<ListDate> List = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find__delays__details, container, false);
        ((Home) getActivity()).Visibileback();
        this.view = view;
        Start = getArguments().getString("Start");
        End = getArguments().getString("End");
        From = getArguments().getString("From");
        To = getArguments().getString("To");
        FromCode = getArguments().getString("FromCode");
        ToCode = getArguments().getString("ToCode");


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        progressDialog = AppUtils.LoadingSpinner(getActivity());
        progressDialog.setCancelable(false);


        view.findViewById(R.id.TraOtherDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Home) getActivity()).onBackPressed();
            }
        });

        MainCall();

        return view;
    }


    private void MainCall() {

        progressDialog.show();
        String url = "http://192.64.115.101:8080/trainCancelDetails";
        MyApplication myApplication = (MyApplication) getActivity().getApplicationContext();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        PostObject postObject = new PostObject();
        postObject.setDate(Start);
        postObject.setSingleTicketPrice(Float.parseFloat(myApplication.getUser().getPrice()));
        postObject.setToStation(ToCode);
        postObject.setRealTimeRequest(true);
        postObject.setFromStation(FromCode);
        final String requestBody = new Gson().toJson(postObject);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    ResponseByApi responseByApi = new Gson().fromJson(response, ResponseByApi.class);

                    List = new ArrayList<>();

                    for (CancelledDetail cancelledDetail : responseByApi.getCancelledDetails()) {
                        ListDate listDate = new ListDate();
                        listDate.setTime(cancelledDetail.getDepartureTime());
                        if (cancelledDetail.getCancelled()) {
                            listDate.setStatus("Delayed");
                        } else {
                            listDate.setStatus("Cancelled");
                        }

                        listDate.setAtocName(cancelledDetail.getmAtocName());
                        listDate.setAtocCode(cancelledDetail.getmAtocCode());
                        listDate.setOrigin(cancelledDetail.getFromStation());
                        listDate.setDestination(cancelledDetail.getToStation());
                        listDate.setAmount(cancelledDetail.getRefundAmount());
                        listDate.setDelay(String.valueOf(cancelledDetail.getTimeDelayed()));
                        List.add(listDate);
                    }

                    if (List.size() != 0) {
                        ShowList();
                        view.findViewById(R.id.NOItem).setVisibility(View.GONE);
                    } else {
                        view.findViewById(R.id.NOItem).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.recycler_view).setVisibility(View.GONE);
                    }

                } catch (Exception Ex) {
                    view.findViewById(R.id.NOItem).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.recycler_view).setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                view.findViewById(R.id.NOItem).setVisibility(View.VISIBLE);
                view.findViewById(R.id.recycler_view).setVisibility(View.GONE);


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


    public void ShowList() {
        mAdapter = new Delays_Adapter(List, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        progressDialog.dismiss();
    }


//    private void MainCallAgain() {
//
//        String url = "https://api.rtt.io/api/v1/json/search/" + FromCode + "/to/" + ToCode + "/" + Start + "/arrivals";
//
//        progressDialog.show();
//
//        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//
//                        Gson gson = new Gson();
//                        ServicesDelays service = new ServicesDelays();
//                        service = gson.fromJson(response, ServicesDelays.class);
//                        if (service.getServices() != null) {
//                            if (service.getServices().size() > 0) {
//
//
//                                serviceListALL.addAll(service.getServices());
//
//
//                                for (int i = 0; i < serviceListALL.size(); i++) {
//
////                                    if (i == serviceListALL.size() - 1)
////                                        ForCancel(serviceListALL.get(i), true);
////                                    else ForCancel(serviceListALL.get(i), false);
//
//                                }
//
//
//                            } else {
//                                Toast.makeText(getActivity(), "No Route Found", Toast.LENGTH_LONG).show();
//
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), "No Route Found", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        Log.d("ERROR", "error => " + error.toString());
//                    }
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<String, String>();
//                String creds = String.format("%s:%s", "rttapi_grahamridley", "0615931afc35ea650aa127bc40138e87e2db3267");
//                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
//                params.put("Authorization", auth);
//                return params;
//            }
//        };
//        queue.add(postRequest);
//    }


//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        if (enter) {
//            return CubeAnimation.create(CubeAnimation.LEFT, enter, 500);
//
//        } else {
//            return CubeAnimation.create(CubeAnimation.RIGHT, enter, 500);
//
//        }
//    }

}


