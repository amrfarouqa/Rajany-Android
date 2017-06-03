package com.rajany.rajdy.classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rajany.rajdy.R;
import com.rajany.rajdy.auth.main_regist;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Splash extends Activity {
    View view;
    String Status = "Normal";
    String ID;
    String UserID;
    final static public String PREFS_NAME = "PREFS_NAME";
    final static private String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if(!isNetworkAvailable()){
            Toast.makeText(Splash.this,"Please Connect To The Internet",Toast.LENGTH_LONG).show();
        }else{

        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        new CreateShortcut().execute();

        slideonentrance(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Status == "Normal") {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(Splash.this, WelcomeActivity.class);
                        Splash.this.startActivity(mainIntent);
                        finish();

                }
            }

        }, SPLASH_DISPLAY_LENGTH);

        final RelativeLayout Relay = (RelativeLayout)findViewById(R.id.RelLay);
        Relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Status = "Weird";


                    Intent intent = new Intent(Splash.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();

            }
        });

    new CreateShortcut().execute();

    }

    public void createShortcutIcon(){

        // Checking if ShortCut was already added
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean shortCutWasAlreadyAdded = sharedPreferences.getBoolean(PREF_KEY_SHORTCUT_ADDED, false);
        if (shortCutWasAlreadyAdded) {

        }else{
            Intent shortcutIntent = new Intent(getApplicationContext(), Splash.class);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Intent addIntent = new Intent();
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Rajany");
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.logo));
            addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            getApplicationContext().sendBroadcast(addIntent);

            // Remembering that ShortCut was already added
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PREF_KEY_SHORTCUT_ADDED, true);
            editor.commit();
        }
    }

    public void slideonentrance(View view) {
        /*ImageView image = (ImageView) findViewById(R.id.LogoImg);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        image.startAnimation(animation1);

        TextView Text1 = (TextView) findViewById(R.id.LogoText);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        Text1.startAnimation(animation2);

        ImageView imagebg = (ImageView) findViewById(R.id.ImgBg);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom2);
        imagebg.startAnimation(animation3);

        TextView Slogan = (TextView) findViewById(R.id.textView46);
        Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        Slogan.startAnimation(animation4);*/
    }

    class CreateShortcut extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            createShortcutIcon();
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {

        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
