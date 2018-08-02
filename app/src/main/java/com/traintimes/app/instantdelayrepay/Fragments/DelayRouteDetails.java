package com.traintimes.app.instantdelayrepay.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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
import com.traintimes.app.instantdelayrepay.util.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelayRouteDetails extends Fragment implements DatePickerDialog.OnDateSetListener {


    private RecyclerView recyclerView;
    private Delays_Routes_Adapter mAdapter;
    List<Header> List;
    String From, To;
    List<String> Dates = new ArrayList<>();
    ListDate listDate = new ListDate();
    View view;
    Calendar myCalendar;
    TextView Date, OK;


    List<Header> SearchList;

    public DelayRouteDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_delay_route_details, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        List = new ArrayList<>();
        SearchList = new ArrayList<>();
        this.view = view;
        ((Home) getActivity()).Visibileback();
        myCalendar = Calendar.getInstance();

        progressDialog = AppUtils.LoadingSpinner(getActivity());


        From = getArguments().getString("From");
        To = getArguments().getString("To");
        Date = view.findViewById(R.id.Date);
        OK = view.findViewById(R.id.OK);
        OK.setVisibility(View.GONE);

        MainCall();


        view.findViewById(R.id.Date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!Date.getText().equals("Select Date")) {
//                    ShowList();
//                    Date.setText("Select Date");
//
//                } else {
//
//                }

                showDate();


            }
        });

        view.findViewById(R.id.OK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowList();
                Date.setText("Select Date");
                OK.setVisibility(View.GONE);

//                if (OK.getText().toString().toLowerCase().equals("ok")) {
//
//                    OK.setText("Cancel");
//                } else {
//                    ShowList();
//                    Date.setText("Select Date");
//                    OK.setText("OK");
//                }

            }
        });
        return view;
    }


    public void ShowList() {
        mAdapter = new Delays_Routes_Adapter(List, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 500);

    }

    Dialog progressDialog;

    private void MainCall() {


        progressDialog.show();
        String url = "http://192.64.115.101:8080/cancelDetailsBatch";
        MyApplication myApplication = (MyApplication) getActivity().getApplicationContext();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        PostObject postObject = new PostObject();
        postObject.setSingleTicketPrice(Float.parseFloat(myApplication.getUser().getPrice()));
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
                            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            java.util.Date d = fmt.parse(s);
                            fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
                            header.setDate(fmt.format(d));
                            header.setDatepoint(d);

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
                                if (!cancelDetailsObject.getCancelled()) {
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


                    }
                    Collections.sort(List, new Comparator<Header>() {
                        public int compare(Header o1, Header o2) {
                            return o1.getDatepoint().compareTo(o2.getDatepoint());
                        }
                    });

                    Collections.reverse(List);


//                    for (Header header : List)
//                        for (int i = 0; i < List.size() - 1; i++) {
//                            DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
//                            Header header1 = List.get(i);
//                            Header header2 = List.get(i + 1);
//
//                            java.util.Date d = fmt.parse(List.get(i).getDate());
//                            java.util.Date d2 = fmt.parse(List.get(i + 1).getDate());
//
//                            int abc = d.getMonth();
//                            int abc1 = d2.getMonth();
//
//                            if (d.getMonth() == d2.getMonth()) {
//
//
//                                Calendar c = Calendar.getInstance();
//                                c.setTime(d);
//                                int abc11 = c.get(Calendar.DAY_OF_WEEK);
//                                c.setTime(d2);
//                                int abc111 = c.get(Calendar.DAY_OF_WEEK);
//                                if (abc11 < abc111) {
//                                    List.set(i, header2);
//                                    List.set(i + 1, header1);
//                                }
//
//
//                            }
//
//
//                        }
//

                    ShowList();


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    view.findViewById(R.id.NOItem).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.recycler_view).setVisibility(View.GONE);
                } catch (ParseException e) {
                    progressDialog.dismiss();

                    e.printStackTrace();
                }


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

    String year, day, mounth;

    @Override
    public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {


        progressDialog.show();

        year = String.valueOf(year1);
        mounth = String.valueOf(monthOfYear + 1);
        day = String.valueOf(dayOfMonth);

        myCalendar.set(Calendar.YEAR, year1);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        if (mounth.length() == 1) {
            mounth = "0" + mounth;
        }

        if (day.length() == 1) {
            day = "0" + day;
        }

        DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

        SearchList.clear();
        for (Header header : List) {
            if (header.getDate().equals(fmt.format(myCalendar.getTime())))
                SearchList.add(header);
        }

        Date.setText(fmt.format(myCalendar.getTime()));


        if (SearchList.size() == 0) {
            this.view.findViewById(R.id.NOItem).setVisibility(View.VISIBLE);
            this.view.findViewById(R.id.recycler_view).setVisibility(View.GONE);
        } else {
            this.view.findViewById(R.id.NOItem).setVisibility(View.GONE);
            this.view.findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
        }

        Collections.sort(SearchList, new Comparator<Header>() {
            public int compare(Header o1, Header o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        Collections.reverse(SearchList);
        mAdapter = new Delays_Routes_Adapter(SearchList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 500);
        OK.setVisibility(View.VISIBLE);


//        SearchList.clear();
//        if (!OK.getText().toString().toLowerCase().equals("ok")) {
//            for (Header header : List) {
//                if (header.getDate().equals(Date.getText().toString()))
//                    SearchList.add(header);
//            }
//
//
//            if (SearchList.size() == 0) {
//                this.view.findViewById(R.id.NOItem).setVisibility(View.VISIBLE);
//                this.view.findViewById(R.id.recycler_view).setVisibility(View.GONE);
//            } else {
//                this.view.findViewById(R.id.NOItem).setVisibility(View.GONE);
//                this.view.findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
//            }
//
//            mAdapter = new Delays_Routes_Adapter(SearchList, getActivity());
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//            recyclerView.setLayoutManager(mLayoutManager);
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.setAdapter(mAdapter);
//        } else {
//            ShowList();
//            Date.setText("Select Date");
//        }
    }

    void showDate() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme11, this,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 30);
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        calendar = Calendar.getInstance();
        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dialog.show();
    }
}
