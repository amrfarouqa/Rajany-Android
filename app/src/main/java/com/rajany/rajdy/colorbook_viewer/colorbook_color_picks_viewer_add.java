package com.rajany.rajdy.colorbook_viewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajany.rajdy.R;
import com.rajany.rajdy.classes.ColorPick;

import java.text.SimpleDateFormat;
import java.util.Date;


public class colorbook_color_picks_viewer_add extends Activity {
    int SelectedColor;
    private ProgressDialog pDialog;
    public static final String PATH = "colorpicks";
    int inputColor;
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_color_viewer);
        inputColor = SelectedColor;
        final ImageView Viewer = (ImageView) findViewById(R.id.Viewer);
        final ImageView Close = (ImageView) findViewById(R.id.closee);
        final ImageView add = (ImageView) findViewById(R.id.addcolor);
        Intent intent = getIntent();
        SelectedColor = intent.getIntExtra("SelectedColor", SelectedColor);
        Viewer.setBackgroundColor(SelectedColor);
        mDatabase = FirebaseDatabase.getInstance().getReference().child(PATH);

        mAuth = FirebaseAuth.getInstance();


        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!isNetworkAvailable()){
                   Toast.makeText(colorbook_color_picks_viewer_add.this,"Please Connect To The Internet",Toast.LENGTH_LONG).show();
               }else{
                   saveColorPick();
               }

            }
        });


    }

    public void saveColorPick(){
        String Colorname = String.valueOf(SelectedColor);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String currentDateandTime = sdf.format(new Date());
            ColorPick color = new ColorPick(name, email, Colorname, currentDateandTime);
            String mItemId = mDatabase.child(uid).push().getKey();
            mDatabase.child(uid).child(mItemId).setValue(color);
            Toast.makeText(colorbook_color_picks_viewer_add.this,"Added To Color Picks",Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(colorbook_color_picks_viewer_add.this,"Please Login!",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
