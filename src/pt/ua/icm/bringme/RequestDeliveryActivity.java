package pt.ua.icm.bringme;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import pt.ua.icm.bringme.datastorage.LocalData;
import pt.ua.icm.bringme.models.Courier;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class RequestDeliveryActivity extends ActionBarActivity
		implements
		ActionBar.TabListener,
		pt.ua.icm.bringme.RequestDeliveryCourierFragment.OnFragmentInteractionListener,
		pt.ua.icm.bringme.RequestDeliverySourceFragment.OnFragmentInteractionListener,
		pt.ua.icm.bringme.RequestDeliveryTargetFragment.OnFragmentInteractionListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	LinkedList<Courier> courierList = new LinkedList<Courier>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_delivery);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Enable ActionBar Home
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_delivery, menu);
		return true;
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			Intent homeIntent = new Intent(this, MainMenuActivity.class);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
		}
		return (super.onOptionsItemSelected(menuItem));
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			switch (position) {
			case 0:
				LocalData.orderFragment = RequestDeliveryOrderDetailsFragment.newInstance();
				return LocalData.orderFragment;
			case 1:
				LocalData.sourceFragment = RequestDeliverySourceFragment.newInstance(); 
				return LocalData.sourceFragment;
			case 2:
				LocalData.targetFragment = RequestDeliveryTargetFragment.newInstance();
				return LocalData.targetFragment;
			case 3:
				
				LocalData.courierFragment = RequestDeliveryCourierFragment.newInstance();
				
				Bundle args = new Bundle();
				args.putSerializable("COURIER_LIST", courierList);
				LocalData.courierFragment.setArguments(args);
				return LocalData.courierFragment;
			}

			return null;
		}
		
		public LinkedList<Courier> getAvailableCouriers(){
			ParseQuery<ParseObject> queryCouriers = new ParseQuery<ParseObject>("User");
			queryCouriers.whereEqualTo("isCourier", true);
			queryCouriers.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(e == null){
						processCourierList(objects);
					}
				}
			});
			return null;
		}

		protected void processCourierList(List<ParseObject> objects) {
			for(ParseObject parseCourier : objects){				
				Courier courier = new Courier(
						parseCourier.getString("firstName"), 
						parseCourier.getString("lastName"), 
						parseCourier.getString("phoneNumber"), 
						parseCourier.getDouble("rating"));
				
				courierList.add(courier);
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.tab_title_delivery_order_details)
						.toUpperCase(l);
			case 1:
				return getString(R.string.tab_title_delivery_source)
						.toUpperCase(l);
			case 2:
				return getString(R.string.tab_title_delivery_target)
						.toUpperCase(l);
			case 3:
				return getString(R.string.tab_title_delivery_courier)
						.toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
	}

}
