package com.traintimes.app.instantdelayrepay.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traintimes.app.instantdelayrepay.Home;
import com.traintimes.app.instantdelayrepay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class About_Us extends Fragment {


    public About_Us() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about__us, container, false);

        ((Home) getActivity()).ChangeTittle("About Us");
        return view;
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
//        }
//    }


}
