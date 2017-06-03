package com.rajany.rajdy.services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rajany.rajdy.R;

public class services extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        final LinearLayout One = (LinearLayout)findViewById(R.id.Admin);
        final LinearLayout Two = (LinearLayout)findViewById(R.id.Docum);
        final LinearLayout Three = (LinearLayout)findViewById(R.id.Desproga);
        final LinearLayout Four = (LinearLayout)findViewById(R.id.Develop);
        final LinearLayout Five = (LinearLayout)findViewById(R.id.Meeting);
        final LinearLayout Six = (LinearLayout)findViewById(R.id.Present);
        final LinearLayout Seven = (LinearLayout)findViewById(R.id.Post);
        final LinearLayout Eight = (LinearLayout)findViewById(R.id.Impl);

        Five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(services.this, services_meeting.class);
                services.this.startActivity(mainIntent);
            }
        });

        Eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent1 = new Intent(services.this, services_impl.class);
                services.this.startActivity(mainIntent1);
            }
        });

        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent2 = new Intent(services.this, services_admin.class);
                services.this.startActivity(mainIntent2);
            }
        });

        Six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent3 = new Intent(services.this, services_present.class);
                services.this.startActivity(mainIntent3);
            }
        });

        Seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent4 = new Intent(services.this, services_post.class);
                services.this.startActivity(mainIntent4);
            }
        });

        Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent5 = new Intent(services.this, services_docum.class);
                services.this.startActivity(mainIntent5);
            }
        });

        Three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent6 = new Intent(services.this, services_desprog.class);
                services.this.startActivity(mainIntent6);
            }
        });

        Four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent7 = new Intent(services.this, services_develop.class);
                services.this.startActivity(mainIntent7);
            }
        });



    }



}
