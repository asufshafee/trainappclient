package com.traintimes.app.instantdelayrepay.Fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.traintimes.app.instantdelayrepay.Adapter.Routes_Adapter;
import com.traintimes.app.instantdelayrepay.Home;
import com.traintimes.app.instantdelayrepay.Objects.Route;
import com.traintimes.app.instantdelayrepay.Objects.Stations;
import com.traintimes.app.instantdelayrepay.Objects.TrainStationsAndCode;
import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.utils.AppUtil;
import com.traintimes.app.instantdelayrepay.utils.DatePickerDialogWithMaxMinRange;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Routes extends Fragment {


    public My_Routes() {
        // Required empty public constructor
    }

    TextView NoROute;

    private DatabaseReference databaseReference;


    List<Route> List = new ArrayList<>();
    RecyclerView recyclerView;
    View view;
    private Routes_Adapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__routes, container, false);
        this.view = view;
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        view.findViewById(R.id.AddNewRoute).setVisibility(View.INVISIBLE);
        NoROute = view.findViewById(R.id.NoROute);
        view.findViewById(R.id.AddNewRoute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity());
            }
        });

        GetUSerDate();


        ((Home) getActivity()).ChangeTittle("My Routes");
        return view;
    }

    public void ShowList() {

        mAdapter = new Routes_Adapter(List, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }


//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//
//        if (enter)
//            return CubeAnimation.create(CubeAnimation.LEFT, enter, 500);
//        else return CubeAnimation.create(CubeAnimation.RIGHT, enter, 500);
//    }


    ProgressDialog progressDialog;
    private DatabaseReference dbref;

    public void GetUSerDate() {


        progressDialog = new ProgressDialog(getActivity());
        dbref = FirebaseDatabase.getInstance().getReference("Routes/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        progressDialog.setMessage("Wait.......");
        progressDialog.setCancelable(false);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                view.findViewById(R.id.AddNewRoute).setVisibility(View.VISIBLE);
                List.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Route route = childSnapshot.getValue(Route.class);
                    List.add(route);
                }
                if (List.size() > 0) {
                    NoROute.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    NoROute.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                ShowList();
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    String year, day, mounth;
    private DatePickerDialogWithMaxMinRange datePickerDialog;

    String FromCode, ToCode;
    AutoCompleteTextView From, To;
    TextView Start, End;
    HashMap<String, String> Data = new HashMap<>();


    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_route_dialog);


        dialog.findViewById(R.id.AddROute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {

                    Route route = new Route();
                    route.setStartDate(Start.getText().toString());
                    route.setEndDate(End.getText().toString());
                    route.setFromStation(From.getText().toString());
                    route.setToStation(To.getText().toString());
                    route.setFromStationCode(FromCode);
                    route.setToStationCode(ToCode);
                    DatabaseReference firebaseDatabase;
                    firebaseDatabase = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    firebaseDatabase = FirebaseDatabase.getInstance().getReference("Routes/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                    firebaseDatabase.setValue(route);
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    GetUSerDate();
                }
            }
        });

        String Json = ReadFromfile("Stations.txt", getActivity());

        Gson gson = new Gson();
        Stations stations = gson.fromJson(Json, Stations.class);


        From = dialog.findViewById(R.id.From);
        To = dialog.findViewById(R.id.To);


        From.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    To.requestFocus();
                    return true;
                }
                return false;
            }
        });


        for (TrainStationsAndCode object : stations.getTrainStationsAndCodes()
                ) {
            Data.put(object.getStationName() + "(" + object.getCRSCode() + ")", object.getCRSCode());

        }
        setupAutoComplete(From, stations.getTrainStationsAndCodes());
        setupAutoComplete1(To, stations.getTrainStationsAndCodes());

        Start = dialog.findViewById(R.id.StartDate);
        End = dialog.findViewById(R.id.EndDate);


        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateStart();
            }
        });
        End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateEnd();
            }
        });


        dialog.show();

    }


    private void setupAutoComplete(AutoCompleteTextView view, final List<TrainStationsAndCode> objects) {
        List<String> names = new AbstractList<String>() {
            @Override
            public int size() {
                return objects.size();
            }

            @Override
            public String get(int location) {
                return objects.get(location).getStationName() + "(" + objects.get(location).getCRSCode() + ")";
            }
        };

        view.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_single, names));


        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FromCode = Data.get(From.getText().toString());
                From.setFocusableInTouchMode(false);
                From.setFocusable(false);
                From.setFocusableInTouchMode(true);
                From.setFocusable(true);
                To.setFocusable(true);
                To.requestFocus();
            }
        });
    }


    private void setupAutoComplete1(AutoCompleteTextView view, final List<TrainStationsAndCode> objects) {
        List<String> names = new AbstractList<String>() {
            @Override
            public int size() {
                return objects.size();
            }

            @Override
            public String get(int location) {
                return objects.get(location).getStationName() + "(" + objects.get(location).getCRSCode() + ")";
            }
        };

        view.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_single, names));


        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ToCode = Data.get(To.getText().toString());
            }
        });
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


    public void ShowDateStart() {
        createDateDialog(getActivity(), getActivity());


    }

    public void createDateDialog(final Context ctx, Activity current) {


        DatePickerDialog.OnDateSetListener datePickerOnDateSetListener;
        Calendar myCalendar;

        datePickerOnDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year1, int monthOfYear1,
                                  int dayOfMonth) {
                year = String.valueOf(year);
                mounth = String.valueOf(monthOfYear1 + 1);
                day = String.valueOf(dayOfMonth);
                Start.setText(year + "/" + mounth + "/" + day);

                if (mounth.length() == 1) {
                    mounth = "0" + mounth;
                }

                if (day.length() == 1) {
                    day = "0" + day;
                }

            }

        };

        myCalendar = Calendar.getInstance();

        int CurrentYear = myCalendar.get(Calendar.YEAR);
        int CurrentMonth = myCalendar.get(Calendar.MONTH);
        int CurrentDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getActivity(), R.style.DialogTheme1, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                //DO SOMETHING
                year = String.valueOf(year1);
                mounth = String.valueOf(monthOfYear + 1);
                day = String.valueOf(dayOfMonth);


                if (mounth.length() == 1) {
                    mounth = "0" + mounth;
                }

                if (day.length() == 1) {
                    day = "0" + day;
                }

                Start.setText(year + "/" + mounth + "/" + day);
            }
        }, CurrentYear, CurrentMonth, CurrentDay).show();


        int MaxYear = CurrentYear + 10;
        int MaxMonth = 11;
        int MaxDay = 31;

        int MinYear = CurrentYear;
        int MinMonth = CurrentMonth;
        int MinDay = CurrentDay;

        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialogWithMaxMinRange(ctx, datePickerOnDateSetListener,
                    MinYear, MinMonth, MinDay, MaxYear, MaxMonth, MaxDay);
        }
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    // Do Stuff
                }
            }
        });
//        datePickerDialog.show();


    }


    public void ShowDateEnd() {
        createDateDialog1(getActivity(), getActivity());


    }

    public void createDateDialog1(final Context ctx, Activity current) {


        DatePickerDialog.OnDateSetListener datePickerOnDateSetListener;
        Calendar myCalendar;

        datePickerOnDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year1, int monthOfYear1,
                                  int dayOfMonth) {
                year = String.valueOf(year);
                mounth = String.valueOf(monthOfYear1 + 1);
                day = String.valueOf(dayOfMonth);

                if (mounth.length() == 1) {
                    mounth = "0" + mounth;
                }

                if (day.length() == 1) {
                    day = "0" + day;
                }

                End.setText(year + "/" + mounth + "/" + day);

            }

        };

        myCalendar = Calendar.getInstance();

        int CurrentYear = myCalendar.get(Calendar.YEAR);
        int CurrentMonth = myCalendar.get(Calendar.MONTH);
        int CurrentDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getActivity(), R.style.DialogTheme1, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
                //DO SOMETHING
                year = String.valueOf(year1);
                mounth = String.valueOf(monthOfYear + 1);
                day = String.valueOf(dayOfMonth);
                if (mounth.length() == 1) {
                    mounth = "0" + mounth;
                }

                if (day.length() == 1) {
                    day = "0" + day;
                }
                End.setText(year + "/" + mounth + "/" + day);

            }
        }, CurrentYear, CurrentMonth, CurrentDay).show();


        int MaxYear = CurrentYear + 10;
        int MaxMonth = 11;
        int MaxDay = 31;

        int MinYear = CurrentYear;
        int MinMonth = CurrentMonth;
        int MinDay = CurrentDay;

        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialogWithMaxMinRange(ctx, datePickerOnDateSetListener,
                    MinYear, MinMonth, MinDay, MaxYear, MaxMonth, MaxDay);
        }
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    // Do Stuff
                }
            }
        });
//        datePickerDialog.show();


    }


    private boolean validation() {


        if (AppUtil.isNull(From.getText().toString())) {
            From.setError("fromStation  Required");
            return false;
        }

        if (AppUtil.isNull(To.getText().toString())) {
            To.setError("toStation  id Required");
            return false;
        }

        if (false) {
            Toast.makeText(getActivity(), "Date Not Selected", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
