package pt.ua.icm.bringme;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.FacebookImageLoader;
import pt.ua.icm.bringme.helpers.MapHelper;
import pt.ua.icm.bringme.models.Delivery;
import pt.ua.icm.bringme.models.User;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class RequestDeliveryActivity extends ActionBarActivity implements 
	ActionBar.TabListener, DeliveryOriginFragment.OnDeliveryListener,
	DeliveryCourierFragment.OnDeliveryListener, DeliveryDestinationFragment.OnDeliveryListener,
	DeliveryDetailsFragment.OnDeliveryListener{

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	
	Delivery delivery = new Delivery();

	public Delivery getDelivery() {
		return delivery;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_delivery);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.requestDeliveryPager);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			switch (position) {
			case 0:
				return DeliveryOriginFragment.newInstance();
			case 1:
				return DeliveryCourierFragment.newInstance(delivery.origin);
			case 2:
				return DeliveryDestinationFragment.newInstance();
			case 3:
				return DeliveryDetailsFragment.newInstance();
			}
			return null;
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
				return getString(R.string.title_request_delivery_origin).toUpperCase(l);
			case 1:
				return getString(R.string.title_request_delivery_courier).toUpperCase(l);
			case 2:
				return getString(R.string.title_request_delivery_destination).toUpperCase(l);
			case 3:
				return getString(R.string.title_request_delivery_details).toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public void setCourier(User courier) {
		delivery.courierId = courier.getObjectId();
	}

	@Override
	public void setOrigin(ParseGeoPoint geoLocation, String addressName) {
		delivery.origin = geoLocation;
		delivery.originAddress = addressName;
		
		FragmentManager manager = getSupportFragmentManager();
		final SupportMapFragment mapFragment = (SupportMapFragment) manager.findFragmentById(R.id.courierMapFragment);
		
		final GoogleMap map = (GoogleMap) mapFragment.getMap();
		
		if(delivery.origin != null){
			 double latitude = delivery.origin.getLatitude();
			 double longitude = delivery.origin.getLongitude();
			 LatLng target = new LatLng(latitude, longitude);
			 
			 MapHelper.updateMapCamera(map, target);
			 
			 ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
				query.whereNear("lastLocation", delivery.origin).findInBackground(new FindCallback<ParseObject>() {
					
					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						if(e != null){
							List<User> courierList = new ArrayList<User>();
							for(ParseObject obj : objects){
								courierList.add((User) obj);
							}
							
							for(final User courier : courierList){
								if(courier.has("facebookId")){
									Bitmap profilePic = null;
									
									AsyncTask<String, Void, Bitmap> pinImage = new AsyncTask<String, Void, Bitmap>() {
										@Override
										protected Bitmap doInBackground(
												String... params) {
											FacebookImageLoader loader = new FacebookImageLoader();
											try {
												return loader.execute(courier.getString("facebookId")).get();
											} catch (InterruptedException e) {
												e.printStackTrace();
											} catch (ExecutionException e) {
												e.printStackTrace();
											}
											return null;
										}
										
										protected void onPostExecute(Bitmap result) {
											map.addMarker(new MarkerOptions().position(
													courier.getLastLocationLatLng())
													.icon(BitmapDescriptorFactory.fromBitmap(result)));
										};
									};
								}
							}
						}
					}
				});
		}
	}

	@Override
	public void setDestination(ParseGeoPoint geoPoint, String addressName) {
		delivery.destination = geoPoint;
		delivery.destinationAddress = addressName;
	}

	@Override
	public void setPackageLocationDetails(String detailedPackageLocation,
			String detailedDestinationLocation) {
		delivery.detailedOrigin = detailedPackageLocation;
		delivery.detailedDestination = detailedDestinationLocation;
	}

	@Override
	public void setPackageDetails(String packageName,
			String packageDescription, String packageDetails) {
		delivery.name = packageName;
		delivery.description = packageDescription;
		delivery.notes = packageDetails;
	}

	@Override
	public void validateDelivery() {
		boolean valid = true;
		
		if(delivery.origin == null){
			mViewPager.setCurrentItem(0);
			Toast.makeText(this, "Specify Origin!", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(delivery.courierId.isEmpty()){
			mViewPager.setCurrentItem(1);
			Toast.makeText(this, "Specify Courier!", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(delivery.destination == null){
			mViewPager.setCurrentItem(2);
			Toast.makeText(this, "Specify Destination!", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(delivery.detailedOrigin.isEmpty()){
			mViewPager.setCurrentItem(3);
			Toast.makeText(this, "Specify Detailed Origin!", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(delivery.detailedDestination.isEmpty()){
			mViewPager.setCurrentItem(3);
			Toast.makeText(this, "Specify Detailed Destination!", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		if(delivery.name.isEmpty()){
			mViewPager.setCurrentItem(3);
			Toast.makeText(this, "Specify Package Name!", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		
		if(valid){
			//TODO: Start again from here
		}
	}

}
