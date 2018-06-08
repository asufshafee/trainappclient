package com.traintimes.app.instantdelayrepay.Fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.traintimes.app.instantdelayrepay.Home;
import com.traintimes.app.instantdelayrepay.Objects.Stations;
import com.traintimes.app.instantdelayrepay.Objects.TrainStationsAndCode;
import com.traintimes.app.instantdelayrepay.R;
import com.traintimes.app.instantdelayrepay.utils.AppUtil;
import com.traintimes.app.instantdelayrepay.utils.DatePickerDialogWithMaxMinRange;
import com.google.gson.Gson;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Find_Delays extends Fragment implements DatePickerDialog.OnDateSetListener {

    public Find_Delays() {
        // Required empty public constructor
    }

    String year, day, mounth;
    private DatePickerDialogWithMaxMinRange datePickerDialog;

    String FromCode = "", ToCode = "";
    AutoCompleteTextView From, To;
    TextView Start;
    Boolean FromCheck = false;
    HashMap<String, String> Data = new HashMap<>();
    Calendar myCalendar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find__delays, container, false);

        myCalendar = Calendar.getInstance();
        ((Home) getActivity()).ChangeTittle("Find Delays");

        String Json = "";
        try {
            Json = ReadFromfile("Stations.txt", getActivity());

        } catch (Exception Ex) {

        }

        Gson gson = new Gson();
        Stations stations = gson.fromJson(Json, Stations.class);


        From = view.findViewById(R.id.From);
        To = view.findViewById(R.id.To);


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

        To.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ShowDateStart();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

        Start = view.findViewById(R.id.StartDate);


        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateStart();
            }
        });


        view.findViewById(R.id.SaveMyMoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {


                    if (FromCode.equals("")) {
                        From.setError("invalid fromStation");
                        return;
                    }
                    if (ToCode.equals("")) {
                        To.setError("invalid toStation");
                        return;
                    }

                    Fragment fragment = new Find_Delays_Details();
                    Bundle bundle = new Bundle();
                    bundle.putString("Start", Start.getText().toString());
                    bundle.putString("End", Start.getText().toString());
                    bundle.putString("From", From.getText().toString());
                    bundle.putString("To", To.getText().toString());

                    bundle.putString("FromCode", FromCode);
                    bundle.putString("ToCode", ToCode);

                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, "Details").setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).addToBackStack("").commit();

                }
            }
        });

        return view;
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

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(To.getWindowToken(), 0);
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

        showDate();


    }

    void showDate() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme11, this,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year1, int monthOfYear, int dayOfMonth) {
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
        Start.setText(year + "/" + mounth + "/" + day);
        FromCheck = true;
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

        if (!FromCheck) {
            Toast.makeText(getActivity(), "Date Not Selected", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        if (enter)
//        {
//            return CubeAnimation.create(CubeAnimation.LEFT, enter, 500);
//
//        }else {
//            return CubeAnimation.create(CubeAnimation.RIGHT, enter, 500);
//
//        }    }

}
