package com.rajany.rajdy.tabs;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rajany.rajdy.R;
import com.rajany.rajdy.ideabook_main.ideas_entry;
import com.rajany.rajdy.ideabook_main.ideas_bathroom;
import com.rajany.rajdy.ideabook_main.ideas_bedroom;
import com.rajany.rajdy.ideabook_main.ideas_dining;
import com.rajany.rajdy.ideabook_main.ideas_baby;
import com.rajany.rajdy.ideabook_main.ideas_fireplace;
import com.rajany.rajdy.ideabook_main.ideas_furniture;
import com.rajany.rajdy.ideabook_main.ideas_gym;
import com.rajany.rajdy.ideabook_main.ideas_hall;
import com.rajany.rajdy.ideabook_main.ideas_interior;
import com.rajany.rajdy.ideabook_main.ideas_kitchen;
import com.rajany.rajdy.ideabook_main.ideas_laundry;
import com.rajany.rajdy.ideabook_main.ideas_lighting;
import com.rajany.rajdy.ideabook_main.ideas_living;
import com.rajany.rajdy.ideabook_main.ideas_office;
import com.rajany.rajdy.ideabook_main.ideas_outdoor;
import com.rajany.rajdy.ideabook_main.ideas_pool;
import com.rajany.rajdy.ideabook_main.ideas_restaurant;
import com.rajany.rajdy.ideabook_main.ideas_staircase;
import com.rajany.rajdy.ideabook_main.ideas_storage;
import com.rajany.rajdy.ideabook_main.ideas_vintage;
import com.rajany.rajdy.ideabook_main.ideas_wall;

public class Ideas extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.tab_ideas, container, false);


		if(!isNetworkAvailable()){
			Toast.makeText(getActivity(),"Please Connect To The Internet",Toast.LENGTH_LONG).show();
		}else{

		}


		final LinearLayout Baby = (LinearLayout)rootView.findViewById(R.id.Baby);
		final LinearLayout Bathroom = (LinearLayout)rootView.findViewById(R.id.Bathroom);
		final LinearLayout Bedroom = (LinearLayout)rootView.findViewById(R.id.Bedroom);
		final LinearLayout Dining = (LinearLayout)rootView.findViewById(R.id.Dining);
		final LinearLayout Entry = (LinearLayout)rootView.findViewById(R.id.entry);
		final LinearLayout Fireplace = (LinearLayout)rootView.findViewById(R.id.fireplace);
		final LinearLayout Furniture = (LinearLayout)rootView.findViewById(R.id.furniture);
		final LinearLayout Gym = (LinearLayout)rootView.findViewById(R.id.gym);
		final LinearLayout Hall = (LinearLayout)rootView.findViewById(R.id.hall);
		final LinearLayout Interior = (LinearLayout)rootView.findViewById(R.id.interior);
		final LinearLayout Kitchen = (LinearLayout)rootView.findViewById(R.id.kitchen);
		final LinearLayout Laundry = (LinearLayout)rootView.findViewById(R.id.laundry);
		final LinearLayout Lighting = (LinearLayout)rootView.findViewById(R.id.lighting);
		final LinearLayout Living = (LinearLayout)rootView.findViewById(R.id.living);
		final LinearLayout Office = (LinearLayout)rootView.findViewById(R.id.office);
		final LinearLayout Outdoor = (LinearLayout)rootView.findViewById(R.id.outdoor);
		final LinearLayout Pool = (LinearLayout)rootView.findViewById(R.id.pool);
		final LinearLayout Restaurant = (LinearLayout)rootView.findViewById(R.id.restaurant);
		final LinearLayout Staircase = (LinearLayout)rootView.findViewById(R.id.staircase);
		final LinearLayout Storage = (LinearLayout)rootView.findViewById(R.id.storage);
		final LinearLayout Vintage = (LinearLayout)rootView.findViewById(R.id.vintage);
		final LinearLayout Wall = (LinearLayout)rootView.findViewById(R.id.wallclading);

		Baby.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_baby.class);
				getActivity().startActivity(intent);
			}
		});

		Bathroom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_bathroom.class);
				getActivity().startActivity(intent);
			}
		});


		Bedroom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_bedroom.class);
				getActivity().startActivity(intent);
			}
		});


		Dining.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_dining.class);
				getActivity().startActivity(intent);
			}
		});


		Entry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_entry.class);
				getActivity().startActivity(intent);
			}
		});


		Fireplace.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_fireplace.class);
				getActivity().startActivity(intent);
			}
		});


		Furniture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_furniture.class);
				getActivity().startActivity(intent);
			}
		});


		Gym.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_gym.class);
				getActivity().startActivity(intent);
			}
		});










		Hall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_hall.class);
				getActivity().startActivity(intent);
			}
		});


		Interior.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_interior.class);
				getActivity().startActivity(intent);
			}
		});


		Kitchen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_kitchen.class);
				getActivity().startActivity(intent);
			}
		});


		Laundry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_laundry.class);
				getActivity().startActivity(intent);
			}
		});


		Lighting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_lighting.class);
				getActivity().startActivity(intent);
			}
		});


		Living.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_living.class);
				getActivity().startActivity(intent);
			}
		});


		Office.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_office.class);
				getActivity().startActivity(intent);
			}
		});


		Outdoor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_outdoor.class);
				getActivity().startActivity(intent);
			}
		});

		Pool.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_pool.class);
				getActivity().startActivity(intent);
			}
		});

		Restaurant.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_restaurant.class);
				getActivity().startActivity(intent);
			}
		});
		Staircase.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_staircase.class);
				getActivity().startActivity(intent);
			}
		});

		Storage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_storage.class);
				getActivity().startActivity(intent);
			}
		});

		Vintage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_vintage.class);
				getActivity().startActivity(intent);
			}
		});
		Wall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(),ideas_wall.class);
				getActivity().startActivity(intent);
			}
		});



		return rootView;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}


}
