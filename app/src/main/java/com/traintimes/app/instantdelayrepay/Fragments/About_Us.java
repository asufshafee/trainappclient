package com.traintimes.app.instantdelayrepay.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.traintimes.app.instantdelayrepay.Home;
import com.traintimes.app.instantdelayrepay.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class About_Us extends Fragment {


    public About_Us() {
        // Required empty public constructor
    }

    TextView About;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about__us, container, false);

        ((Home) getActivity()).ChangeTittle("About Us");

        About = view.findViewById(R.id.textView);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            About.setText(Html.fromHtml("<p>Instant Delay Repay was set up to make sure you don\'t need to remember or research the delays you experience. We don\'t take any commission and want honest customers to be able to reclaim the money their owed. Save hundreds a month on the routes you take.</p>\n" +
                    "<p></p>\n" +
                    "<p>We currently cover the majority of the National Rail network, but if we don\'t have one, feel free to contact us a hello@instantdelayrepay.com.</p>\n" +
                    "<p></p>\n" +
                    "<p>There are a few things to remember though:</p>\n" +
                    "<p></p>\n" +
                    "<ul>\n" +
                    "<li>-  The trial account will expire in 30 days, so make sure you subscribe to not miss out</li>\n" +
                    "<ul>\n" +
                    "<p></p>\n" +
                    "<li>-  Only claim on routes you travel on.</li>\n" +
                    "<ul>\n" +
                    "<p></p>\n" +
                    "<li>-  Have your ticket ready to submit to the website. We work across all tickets, including season tickets.</li>\n" +
                    "<ul>\n" +
                    "<p></p>\n" +
                    "<li>-  \"Find a delay\" only works up to 7 days previously and won\'t show today\'s results.</li>\n" +
                    "</ul>\n" +
                    "<p></p>\n" +
                    "<p>You are shown delay information for all trains, it\'s worth noting some rail providers will not pay out unless the delay is over 30 minutes (Some are above 15).</p>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            About.setText(Html.fromHtml("<p>Instant Delay Repay was set up to make sure you don\'t need to remember or research the delays you experience. We don\'t take any commission and want honest customers to be able to reclaim the money their owed. Save hundreds a month on the routes you take.</p>\n" +
                    "<p></p>\n" +
                    "<p>We currently cover the majority of the National Rail network, but if we don\'t have one, feel free to contact us a hello@instantdelayrepay.com.</p>\n" +
                    "<p></p>\n" +
                    "<p>There are a few things to remember though:</p>\n" +
                    "<p></p>\n" +
                    "<ul>\n" +
                    "<li>-  The trial account will expire in 30 days, so make sure you subscribe to not miss out</li>\n" +
                    "<ul>\n" +
                    "<p></p>\n" +
                    "<li>-  Only claim on routes you travel on.</li>\n" +
                    "<ul>\n" +
                    "<p></p>\n" +
                    "<li>-  Have your ticket ready to submit to the website. We work across all tickets, including season tickets.</li>\n" +
                    "<ul>\n" +
                    "<p></p>\n" +
                    "<li>-  \"Find a delay\" only works up to 7 days previously and won\'t show today\'s results.</li>\n" +
                    "</ul>\n" +
                    "<p></p>\n" +
                    "<p>You are shown delay information for all trains, it\'s worth noting some rail providers will not pay out unless the delay is over 30 minutes (Some are above 15).</p>"));
        }

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
