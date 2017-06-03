package com.rajany.rajdy.colorbook_viewer;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajany.rajdy.R;
import com.rajany.rajdy.classes.ColorPick;

public class colorbook_color_picks_viewer extends ListActivity {
    public DatabaseReference mDatabase;
    private ProgressDialog pDialog;
    public FirebaseAuth mAuth;
    public String id, color;
    private static final String TAG_NAME = "color";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_colors);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();


        // Loading products in Background Thread
        if(!isNetworkAvailable()){
            Toast.makeText(colorbook_color_picks_viewer.this,"Please Connect To The Internet",Toast.LENGTH_LONG).show();
        }else{
            fetchColorPicks();
        }


        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.colorCode)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        colorbook_color_picks_viewer_delete.class);
                // sending pid to next activity
                in.putExtra(TAG_NAME, name);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    public void fetchColorPicks(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("colorpicks").child(user.getUid());
            ListAdapter adapter = new FirebaseListAdapter<ColorPick>(colorbook_color_picks_viewer.this,ColorPick.class,R.layout.list_item,mDatabase) {
                @Override
                protected void populateView(View view, ColorPick colorItem, int i) {
                    TextView colorCodeText = (TextView)view.findViewById(R.id.colorCode);
                    TextView colorDateText = (TextView)view.findViewById(R.id.ColorDate);
                    ImageView colorPickImg = (ImageView) view.findViewById(R.id.ColorPickImg);
                    colorCodeText.setText(colorItem.color);
                    colorPickImg.setBackgroundColor(Color.parseColor(colorItem.color));
                    colorDateText.setText(colorItem.date);
                }

            };
            setListAdapter(adapter);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}