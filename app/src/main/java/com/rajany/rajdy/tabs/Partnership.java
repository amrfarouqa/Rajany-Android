package com.rajany.rajdy.tabs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rajany.rajdy.R;

public class Partnership extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_partnership, container, false);

        if(!isNetworkAvailable()){
            Toast.makeText(getActivity(),"Please Connect To The Internet",Toast.LENGTH_LONG).show();
        }else{

        }




        final LinearLayout SALAB = (LinearLayout) rootView.findViewById(R.id.SALAB);
        final LinearLayout IKEA = (LinearLayout) rootView.findViewById(R.id.IKEA);
        final LinearLayout JOTUN = (LinearLayout) rootView.findViewById(R.id.JOTUN);
        final LinearLayout WIPRO = (LinearLayout) rootView.findViewById(R.id.WIPRO);

        WIPRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.wipro.com/"));
                startActivity(intent);
            }
        });

        SALAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.elsallab.org/en/"));
                startActivity(intent);
            }
        });

        IKEA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.ikea.com/eg/en/"));
                startActivity(intent);
            }
        });

        JOTUN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.jotun.com/"));
                startActivity(intent);
            }
        });

        return rootView;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}