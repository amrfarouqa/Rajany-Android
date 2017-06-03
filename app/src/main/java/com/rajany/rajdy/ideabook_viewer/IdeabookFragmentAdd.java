package com.rajany.rajdy.ideabook_viewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajany.rajdy.R;
import com.rajany.rajdy.classes.Idea;
import com.rajany.rajdy.classes.Image;
import com.rajany.rajdy.colorbook_viewer.colorbook_color_picks_viewer_add;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class IdeabookFragmentAdd extends DialogFragment {
    public DatabaseReference mDatabase;
    public static final String PATH = "ideas";
    private String TAG = IdeabookFragmentAdd.class.getSimpleName();
    private ArrayList<Image> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    public FirebaseAuth mAuth;
    private int selectedPosition = 0;
    public ImageView PaletteIcon1;
    public ImageView PaletteIcon2;
    public ImageView PaletteIcon3;
    public ImageView PaletteIcon4;
    public ImageView PaletteIcon5;
    public ImageView PaletteIcon6;
    public Palette.PaletteAsyncListener paletteListener;
    public Image image;
    public ImageView imageViewPreview;
    public Bitmap myBitmap;
    private ProgressDialog pDialog;


    public static IdeabookFragmentAdd newInstance() {
        IdeabookFragmentAdd f = new IdeabookFragmentAdd();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(PATH);

        mAuth = FirebaseAuth.getInstance();


        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);
        final ImageView Save = (ImageView) v.findViewById(R.id.SaveImg);
        final ImageView Share = (ImageView) v.findViewById(R.id.ShareImmg);

        images = (ArrayList<Image>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = image.getLarge();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Checkout Rajany Link:");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIdea(images.get(viewPager.getCurrentItem()).getLarge());
                Log.e(TAG, "position FROM SAVE: " + viewPager.getCurrentItem());
            }
        });

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + images.size());

        Image image = images.get(position);
        lblTitle.setText(image.getName());
        lblDate.setText(image.getTimestamp());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);


    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);

            image = images.get(position);
            Glide.with(getActivity()).load(image.getLarge())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);

            PaletteIcon1 = (ImageView) view.findViewById(R.id.PaletteIcon1);
            PaletteIcon2 = (ImageView) view.findViewById(R.id.PaletteIcon2);
            PaletteIcon3 = (ImageView) view.findViewById(R.id.PaletteIcon3);
            PaletteIcon4 = (ImageView) view.findViewById(R.id.PaletteIcon4);
            PaletteIcon5 = (ImageView) view.findViewById(R.id.PaletteIcon5);
            PaletteIcon6 = (ImageView) view.findViewById(R.id.PaletteIcon6);
            container.addView(view);


            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public void showPDialog() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Adding to Ideabook..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void saveIdea(String imageUrl) {
        showPDialog();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String currentDateandTime = sdf.format(new Date());
            Idea idea = new Idea(name, email, imageUrl, currentDateandTime);
            String mItemId = mDatabase.child(uid).push().getKey();
            mDatabase.child(uid).child(mItemId).setValue(idea);
            hidepDialog();
        } else {
            hidepDialog();
            Toast.makeText(getActivity(), "Please Login!", Toast.LENGTH_LONG).show();
        }
    }

    public void hidepDialog() {
        pDialog.dismiss();
        Toast.makeText(getActivity(), "Added To Favorite Successfully", Toast.LENGTH_LONG).show();
    }
}
