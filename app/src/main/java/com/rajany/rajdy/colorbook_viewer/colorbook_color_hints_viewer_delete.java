package com.rajany.rajdy.colorbook_viewer;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajany.rajdy.R;
import com.rajany.rajdy.classes.Image;

import java.net.URL;
import java.util.ArrayList;


public class colorbook_color_hints_viewer_delete extends DialogFragment {
    public DatabaseReference mDatabase;
    private String TAG = colorbook_color_hints_viewer_delete.class.getSimpleName();
    private ArrayList<Image> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
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


    public static colorbook_color_hints_viewer_delete newInstance() {
        colorbook_color_hints_viewer_delete f = new colorbook_color_hints_viewer_delete();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ideabook_image_slider, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();


        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);
        final ImageView Delete = (ImageView) v.findViewById(R.id.DeleteIdea);
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

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteHint(images.get(viewPager.getCurrentItem()).getLarge());
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
            View view = layoutInflater.inflate(R.layout.image_ideabook_fullscreen_preview, container, false);

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
        pDialog.setMessage("Deleting From Color Hints...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void DeleteHint(final String imageUrl) {
        showPDialog();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("colorhints").child(user.getUid());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot hintItem : dataSnapshot.getChildren()) {
                        String hint = hintItem.child("hint").getValue(String.class);
                        if(hint.equals(imageUrl)){
                            hintItem.getRef().removeValue();
                            Toast.makeText(getActivity(),"Color Deleted From Color Hints",Toast.LENGTH_LONG).show();
                            getActivity().finish();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
        hidepDialog();
    }

    public void hidepDialog() {
        pDialog.dismiss();
        Toast.makeText(getActivity(), "Removed Successfully", Toast.LENGTH_LONG).show();
    }
}
