package com.rajany.rajdy.company;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rajany.rajdy.R;

public class contact extends Activity {
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        final TextView Facebook = (TextView)findViewById(R.id.facebook);
        final LinearLayout act = (LinearLayout)findViewById(R.id.activity_main);
        final TextView Site = (TextView)findViewById(R.id.site);
        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/rajanydesigns/?fref=ts"));
                startActivity(intent);
            }
        });
        Site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.rajany.com"));
                startActivity(intent);
            }
        });
    }


}
