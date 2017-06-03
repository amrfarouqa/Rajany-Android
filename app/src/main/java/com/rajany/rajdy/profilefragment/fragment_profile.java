package com.rajany.rajdy.profilefragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rajany.rajdy.R;
import com.rajany.rajdy.advices.AdvicesMain;
import com.rajany.rajdy.colorbook_viewer.colorbook_color_hints_viewer;
import com.rajany.rajdy.colorbook_viewer.colorbook_color_picks_viewer;
import com.rajany.rajdy.colorbook_viewer.colorbook_color_schemes_viewer;
import com.rajany.rajdy.friendlychat.chatMain;
import com.rajany.rajdy.ideabook_viewer.ideabook;
import com.rajany.rajdy.stories.StoriesMain;

public class fragment_profile extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;

    public fragment_profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_profile, container, false);

        final TextView UserField = (TextView) rootView.findViewById(R.id.UserField);

        final TextView Copyrights = (TextView) rootView.findViewById(R.id.DateTimeField);

        mAuth = FirebaseAuth.getInstance();

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    UserField.setText(user.getEmail());
                    Copyrights.setText("Powered By © Rajany");
                } else {
                    // User is signed out
                    UserField.setText("Please Login!");
                    Copyrights.setText("Powered By © Rajany");
                }
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        };

        final LinearLayout Ideabook = (LinearLayout) rootView.findViewById(R.id.IdeabookLayout);
        Ideabook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ideabook.class);
                startActivity(intent);
            }
        });

        final LinearLayout ColorSchemes = (LinearLayout) rootView.findViewById(R.id.ColorSchemesLayout);
        ColorSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),colorbook_color_schemes_viewer.class);
                startActivity(intent);
            }
        });

        final LinearLayout ColorPicks = (LinearLayout) rootView.findViewById(R.id.ColorPicksLayout);
        ColorPicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),colorbook_color_picks_viewer.class);
                startActivity(intent);
            }
        });

        final LinearLayout ColorHints = (LinearLayout) rootView.findViewById(R.id.ColorHintsLayout);
        ColorHints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),colorbook_color_hints_viewer.class);
                startActivity(intent);
            }
        });

        final LinearLayout Advices = (LinearLayout) rootView.findViewById(R.id.AdvicesLayout);
        Advices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AdvicesMain.class);
                startActivity(intent);
            }
        });

        final LinearLayout Stories = (LinearLayout) rootView.findViewById(R.id.StoriesLayout);
        Stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),StoriesMain.class);
                startActivity(intent);
            }
        });

        final LinearLayout Chatbook = (LinearLayout) rootView.findViewById(R.id.ChatbookLayout);
        Chatbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),chatMain.class);
                startActivity(intent);
            }
        });





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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("Fragment Profile", "onConnectionFailed:" + connectionResult);
    }

}