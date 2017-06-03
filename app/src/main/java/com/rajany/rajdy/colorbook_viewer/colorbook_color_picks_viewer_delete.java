package com.rajany.rajdy.colorbook_viewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajany.rajdy.R;


public class colorbook_color_picks_viewer_delete extends Activity {
    public String Color;
    public DatabaseReference mDatabase;
    // Progress Dialog
    private ProgressDialog pDialog;
    public FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_color_viewer);
        Intent i = getIntent();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        // getting product id (pid) from intent
        Color = i.getStringExtra("color");
        Toast.makeText(colorbook_color_picks_viewer_delete.this,"ColorPick Code: "+ Color, Toast.LENGTH_LONG).show();

        final ImageView ColorImg = (ImageView) findViewById(R.id.colorimg);
        ColorImg.setBackgroundColor(android.graphics.Color.parseColor(Color));
        final ImageView Share = (ImageView) findViewById(R.id.shareee);
        final ImageView Delete = (ImageView) findViewById(R.id.delete);

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "ColorPick Code :"+Color;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Checkout Rajany Link:");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isNetworkAvailable()){
                    Toast.makeText(colorbook_color_picks_viewer_delete.this,"Please Connect To The Internet",Toast.LENGTH_LONG).show();
                }else{
                    deleteColorPick();
                }


            }
        });

    }

    public void deleteColorPick(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("colorpicks").child(user.getUid());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot colorItem : dataSnapshot.getChildren()) {
                        String color = colorItem.child("color").getValue(String.class);
                        if(color.equals(Color)){
                            colorItem.getRef().removeValue();
                            Toast.makeText(colorbook_color_picks_viewer_delete.this,"Color Deleted From Color Picks",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Color Picks Viewer Del", "Failed to read value.", error.toException());
                }
            });
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}