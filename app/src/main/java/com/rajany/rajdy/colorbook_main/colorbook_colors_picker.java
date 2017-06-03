package com.rajany.rajdy.colorbook_main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.danielnilsson9.colorpickerview.view.ColorPanelView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView.OnColorChangedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajany.rajdy.R;
import com.rajany.rajdy.classes.ColorPick;

import java.text.SimpleDateFormat;
import java.util.Date;

public class colorbook_colors_picker extends Activity implements OnColorChangedListener, View.OnClickListener {

    private ColorPickerView mColorPickerView;
    private ColorPanelView mOldColorPanelView;
    private ColorPanelView mNewColorPanelView;
    public static final String PATH = "colorpicks";
    private Button mOkButton;
    private Button mCancelButton;
    private ProgressDialog pDialog;
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    String Colorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);

        setContentView(R.layout.activity_user_main_colors_picker);
        mDatabase = FirebaseDatabase.getInstance().getReference().child(PATH);

        mAuth = FirebaseAuth.getInstance();


        init();

    }

    private void init() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int initialColor = prefs.getInt("color_3", 0xFF000000);

        mColorPickerView = (ColorPickerView) findViewById(R.id.colorpickerview__color_picker_view);
        mOldColorPanelView = (ColorPanelView) findViewById(R.id.colorpickerview__color_panel_old);
        mNewColorPanelView = (ColorPanelView) findViewById(R.id.colorpickerview__color_panel_new);

        mOkButton = (Button) findViewById(R.id.okButton);
        mCancelButton = (Button) findViewById(R.id.cancelButton);


        ((LinearLayout) mOldColorPanelView.getParent()).setPadding(
                mColorPickerView.getPaddingLeft(), 0,
                mColorPickerView.getPaddingRight(), 0);


        mColorPickerView.setOnColorChangedListener(this);
        mColorPickerView.setColor(initialColor, true);
        mOldColorPanelView.setColor(initialColor);

        mOkButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

    }

    @Override
    public void onColorChanged(int newColor) {
        mNewColorPanelView.setColor(mColorPickerView.getColor());
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.okButton:
                /*SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
                edit.putInt("color_3", mColorPickerView.getColor());
                edit.commit();
                Toast.makeText(colorbook_colors_picker.this,"ColorPick Added To Favorite Successfully!",Toast.LENGTH_LONG).show();*/
                Colorname   = String.format("#%06X", (0xFFFFFF & mColorPickerView.getColor()));

                if(!isNetworkAvailable()){
                    Toast.makeText(colorbook_colors_picker.this,"Please Connect To The Internet",Toast.LENGTH_LONG).show();
                }else{
                    new CreateNewProduct().execute();
                }



                break;
            case R.id.cancelButton:
                finish();
                break;
        }

    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(colorbook_colors_picker.this);
            pDialog.setMessage("Adding to ColorPicks...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
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
            } else {
                Toast.makeText(colorbook_colors_picker.this,"Please Login!",Toast.LENGTH_LONG).show();
            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            Toast.makeText(colorbook_colors_picker.this,"Added To ColorPicks Successfully", Toast.LENGTH_LONG).show();
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
