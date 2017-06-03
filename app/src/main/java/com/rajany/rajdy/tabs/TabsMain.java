package com.rajany.rajdy.tabs;


import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rajany.rajdy.R;
import com.rajany.rajdy.advices.AdvicesMain;
import com.rajany.rajdy.classes.FragmentDrawer;
import com.rajany.rajdy.classes.SQLiteHandler;
import com.rajany.rajdy.classes.SessionManager;
import com.rajany.rajdy.classes.TabsPagerAdapter;
import com.rajany.rajdy.company.about;
import com.rajany.rajdy.company.beliefs;
import com.rajany.rajdy.company.careers;
import com.rajany.rajdy.company.contact;
import com.rajany.rajdy.auth.main_login;
import com.rajany.rajdy.friendlychat.chatMain;
import com.rajany.rajdy.services.services;
import com.rajany.rajdy.profilefragment.fragment_main;
import com.rajany.rajdy.stories.StoriesMain;

import java.util.HashMap;

import static com.rajany.rajdy.auth.main_login.Name;

public class TabsMain extends FragmentActivity implements ActionBar.TabListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = TabsMain.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    // Tab titles
    private String[] tabs = {"HOME", "IDEAS", "COLORS", "PARTNERSHIP"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(TabsMain.this);
        setContentView(R.layout.activity_user_main);
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        //this.activity = this;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        mAuth = FirebaseAuth.getInstance();


        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */

        if(!isNetworkAvailable()){
            Toast.makeText(TabsMain.this,"Please Connect To The Internet",Toast.LENGTH_LONG).show();
        }else{

        }




        // Displaying the user details on the screen
        //txtName.setText(name);
        //txtEmail.setText(email);

        // Logout button click event
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }



    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_mainn, menu);
            getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(TabsMain.this,about.class);
            TabsMain.this.startActivity(intent);
            return true;
        }

        if (id == R.id.action_profiley) {
            Intent intent = new Intent(TabsMain.this,fragment_main.class);
            TabsMain.this.startActivity(intent);

            return true;
        }

        if (id == R.id.action_msg) {
            Intent intent = new Intent(TabsMain.this,chatMain.class);
            TabsMain.this.startActivity(intent);

            return true;
        }

        if (id == R.id.action_services) {
            Intent intentss = new Intent(TabsMain.this,services.class);
            TabsMain.this.startActivity(intentss);
            return true;
        }
        if (id == R.id.action_contact) {
            Intent intentsss = new Intent(TabsMain.this,contact.class);
            TabsMain.this.startActivity(intentsss);
            return true;
        }
        if (id == R.id.btn_logout) {
            signOut();
        }
        if (id == R.id.careers) {
            Intent intentssss = new Intent(TabsMain.this,careers.class);
            TabsMain.this.startActivity(intentssss);
            return true;
        }
        if (id == R.id.belief) {
            Intent intenta = new Intent(TabsMain.this, beliefs.class);
            TabsMain.this.startActivity(intenta);
            return true;
        }
        //if (id == R.id.another_button) {
        ////    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(TabsMain.this, main_login.class);
        startActivity(intent);
        finish();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


}
