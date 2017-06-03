package com.rajany.rajdy.profilefragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rajany.rajdy.R;
import com.rajany.rajdy.services.services_admin;
import com.rajany.rajdy.services.services_desprog;
import com.rajany.rajdy.services.services_develop;
import com.rajany.rajdy.services.services_docum;
import com.rajany.rajdy.services.services_impl;
import com.rajany.rajdy.services.services_meeting;
import com.rajany.rajdy.services.services_post;
import com.rajany.rajdy.services.services_present;

public class fragment_services extends Fragment {

    public fragment_services() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_services, container, false);
        final LinearLayout One = (LinearLayout)rootView.findViewById(R.id.Admin);
        final LinearLayout Two = (LinearLayout)rootView.findViewById(R.id.Docum);
        final LinearLayout Three = (LinearLayout)rootView.findViewById(R.id.Desproga);
        final LinearLayout Four = (LinearLayout)rootView.findViewById(R.id.Develop);
        final LinearLayout Five = (LinearLayout)rootView.findViewById(R.id.Meeting);
        final LinearLayout Six = (LinearLayout)rootView.findViewById(R.id.Present);
        final LinearLayout Seven = (LinearLayout)rootView.findViewById(R.id.Post);
        final LinearLayout Eight = (LinearLayout)rootView.findViewById(R.id.Impl);

        Five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), services_meeting.class);
                startActivity(mainIntent);
            }
        });

        Eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent1 = new Intent(getActivity(), services_impl.class);
                startActivity(mainIntent1);
            }
        });

        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent2 = new Intent(getActivity(), services_admin.class);
                startActivity(mainIntent2);
            }
        });

        Six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent3 = new Intent(getActivity(), services_present.class);
                startActivity(mainIntent3);
            }
        });

        Seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent4 = new Intent(getActivity(), services_post.class);
               startActivity(mainIntent4);
            }
        });

        Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent5 = new Intent(getActivity(), services_docum.class);
                startActivity(mainIntent5);
            }
        });

        Three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent6 = new Intent(getActivity(), services_desprog.class);
                startActivity(mainIntent6);
            }
        });

        Four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent7 = new Intent(getActivity(), services_develop.class);
                startActivity(mainIntent7);
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}