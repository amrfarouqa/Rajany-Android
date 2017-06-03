package com.rajany.rajdy.profilefragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.rajany.rajdy.R;
import com.rajany.rajdy.classes.FragmentDrawer;
import com.rajany.rajdy.classes.SQLiteHandler;
import com.rajany.rajdy.classes.SessionManager;
import com.rajany.rajdy.auth.main_login;

public class fragment_main extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, FragmentDrawer.FragmentDrawerListener {
    private static final String TAG = main_login.class.getSimpleName();
    private SQLiteHandler db;
    private SessionManager session;
    private GoogleApiClient mGoogleApiClient;
    private FragmentDrawer drawerFragment;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(fragment_main.this);
        setContentView(R.layout.activity_fragment_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mToolbar = (Toolbar) findViewById(R.id.mytoolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(!isNetworkAvailable()){
            Toast.makeText(fragment_main.this,"Please Connect To The Internet",Toast.LENGTH_LONG).show();
        }else{

        }

        mAuth = FirebaseAuth.getInstance();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new fragment_profile();
                title = getString(R.string.title_profile);
                break;
            case 1:
                fragment = new fragment_about();
                title = getString(R.string.title_about);
                break;
            case 2:
                fragment = new fragment_belief();
                title = getString(R.string.title_belief);
                break;
            case 3:
                fragment = new fragment_careers();
                title = getString(R.string.title_careers);
                break;
            case 4:
                fragment = new fragment_services();
                title = getString(R.string.title_services);
                break;
            case 5:
                fragment = new fragment_contact();
                title = getString(R.string.title_contacts);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle(title);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}