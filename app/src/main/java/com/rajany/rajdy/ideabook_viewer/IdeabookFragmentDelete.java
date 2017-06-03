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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajany.rajdy.R;
import com.rajany.rajdy.classes.Image;
import com.rajany.rajdy.colorbook_viewer.colorbook_color_picks_viewer_add;

import java.net.URL;
import java.util.ArrayList;


public class IdeabookFragmentDelete extends DialogFragment {
    public DatabaseReference mDatabase;
    private String TAG = IdeabookFragmentAdd.class.getSimpleName();
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


    public static IdeabookFragmentDelete newInstance() {
        IdeabookFragmentDelete f = new IdeabookFragmentDelete();

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
                DeleteIdea(images.get(viewPager.getCurrentItem()).getLarge());
                Log.e(TAG, "position FROM SAVE: " + viewPager.getCurrentItem());
            }
        });

        Palette();
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
            Palette();

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
        Palette();

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
            Palette();

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


    public void Palette() {
        try {
            URL url = new URL(image.getLarge());
            myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            if (myBitmap != null && !myBitmap.isRecycled()) {
                Palette.from(myBitmap).maximumColorCount(1000000).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        // Get the "vibrant" color swatch based on the bitmap
                            /*Palette.Swatch vibrant = palette.getVibrantSwatch();
                            Palette.Swatch vibrantLightSwatch = palette.getLightVibrantSwatch();
                            Palette.Swatch vibrantDarkSwatch = palette.getDarkVibrantSwatch();
                            Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                            Palette.Swatch mutedLightSwatch = palette.getLightMutedSwatch();
                            Palette.Swatch mutedDarkSwatch = palette.getDarkMutedSwatch();*/
                        // Set the background color of a layout based on the vibrant color
                        int default0 = 0x000000;
                        int vibrant = palette.getVibrantColor(default0);
                        int vibrantLight = palette.getLightVibrantColor(default0);
                        int vibrantDark = palette.getDarkVibrantColor(default0);
                        int muted = palette.getMutedColor(default0);
                        int mutedLight = palette.getLightMutedColor(default0);
                        int mutedDark = palette.getDarkMutedColor(default0);

                        PaletteIcon1.setBackgroundColor(vibrant);
                        PaletteIcon2.setBackgroundColor(vibrantLight);
                        PaletteIcon3.setBackgroundColor(vibrantDark);
                        PaletteIcon4.setBackgroundColor(muted);
                        PaletteIcon5.setBackgroundColor(mutedLight);
                        PaletteIcon6.setBackgroundColor(mutedDark);

                        final ColorDrawable drawable1 = (ColorDrawable) PaletteIcon1.getBackground();
                        final ColorDrawable drawable2 = (ColorDrawable) PaletteIcon2.getBackground();
                        final ColorDrawable drawable3 = (ColorDrawable) PaletteIcon3.getBackground();
                        final ColorDrawable drawable4 = (ColorDrawable) PaletteIcon4.getBackground();
                        final ColorDrawable drawable5 = (ColorDrawable) PaletteIcon5.getBackground();
                        final ColorDrawable drawable6 = (ColorDrawable) PaletteIcon6.getBackground();
                        PaletteIcon1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), colorbook_color_picks_viewer_add.class);
                                intent.putExtra("SelectedColor", drawable1.getColor());
                                startActivity(intent);

                            }
                        });


                        PaletteIcon2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), colorbook_color_picks_viewer_add.class);
                                intent.putExtra("SelectedColor", drawable2.getColor());
                                startActivity(intent);
                            }
                        });

                        PaletteIcon3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), colorbook_color_picks_viewer_add.class);
                                intent.putExtra("SelectedColor", drawable3.getColor());
                                startActivity(intent);
                            }
                        });

                        PaletteIcon4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), colorbook_color_picks_viewer_add.class);
                                intent.putExtra("SelectedColor", drawable4.getColor());
                                startActivity(intent);
                            }
                        });

                        PaletteIcon5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), colorbook_color_picks_viewer_add.class);
                                intent.putExtra("SelectedColor", drawable5.getColor());
                                startActivity(intent);
                            }
                        });

                        PaletteIcon6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), colorbook_color_picks_viewer_add.class);
                                intent.putExtra("SelectedColor", drawable6.getColor());
                                startActivity(intent);
                            }
                        });
                        // Update the title TextView with the proper text color
                        //titleView.setTextColor(vibrant.getTitleTextColor());
                    }
                });
            }

        } catch (Exception e) {
            System.out.println("The Palettes Couldn't be loaded!");
            System.out.println(e.getMessage());
        }
    }

    public void showPDialog() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Adding to Ideabook...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void DeleteIdea(final String imageUrl) {
        showPDialog();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("ideas").child(user.getUid());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot ideaItem : dataSnapshot.getChildren()) {
                        String idea = ideaItem.child("idea").getValue(String.class);
                        if(idea.equals(imageUrl)){
                            ideaItem.getRef().removeValue();
                            Toast.makeText(getActivity(),"Idea Deleted From Ideabook",Toast.LENGTH_LONG).show();
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
