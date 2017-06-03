package com.rajany.rajdy.classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rajany.rajdy.tabs.Colors;
import com.rajany.rajdy.tabs.Partnership;
import com.rajany.rajdy.tabs.Home;
import com.rajany.rajdy.tabs.Ideas;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
			case 0:
				// Top Rated fragment activity
				return new Home();
			case 1:
				// Top Rated fragment activity
				return new Ideas();
			case 2:
				// Top Rated fragment activity
				return new Colors();
			case 3:
				// Top Rated fragment activity
				return new Partnership();
			default:
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
