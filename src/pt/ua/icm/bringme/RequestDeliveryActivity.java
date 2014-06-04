package pt.ua.icm.bringme;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import pt.ua.icm.bringme.helpers.AddressHelper;
import pt.ua.icm.bringme.models.Delivery;
import pt.ua.icm.bringme.models.User;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

public class RequestDeliveryActivity extends ActionBarActivity implements
		ActionBar.TabListener, DeliveryOriginFragment.OnDeliveryListener,
		DeliveryCourierListFragment.OnDeliveryListener,
		DeliveryDestinationFragment.OnDeliveryListener,
		DeliveryDetailsFragment.OnDeliveryListener,
		DeliveryCourierMapFragment.OnDeliveryListener,
		DeliveryCourierProfileFragment.OnDeliveryListener,
		DeliveryFinishFragment.OnDeliveryListener{

	private MenuItem listActionButton, mapActionButton;

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	Delivery delivery = new Delivery();
	LinkedList<ParseUser> courierList = new LinkedList<ParseUser>();
	LatLng origin = null;
	
	public LinkedList<ParseUser> getCourierList() {
		return courierList;
	}

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
		mapActionButton = menu.findItem(R.id.mapViewActionIcon);
		listActionButton = menu.findItem(R.id.listViewActionIcon);
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
		if(id == R.id.mapViewActionIcon){
			showActionListButton();
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			Fragment f = new DeliveryCourierMapFragment().newInstance(courierList, origin);
			ft.replace(R.id.courierMapContainer, f);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commit();
			return true;
		}
		if(id == R.id.listViewActionIcon){
			showActionMapButton();
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			Fragment f = new DeliveryCourierListFragment().newInstance(courierList);
			ft.replace(R.id.courierMapContainer, f);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showActionMapButton() {
		listActionButton.setVisible(false);
		mapActionButton.setVisible(true);
	}
	
	private void showActionListButton() {
		listActionButton.setVisible(true);
		mapActionButton.setVisible(false);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		
		if(tab.getPosition() == 1){
			showActionListButton();
			Log.d(Consts.TAG, "Creating DeliveryCourierMapFragment");
			DeliveryCourierMapFragment f = new DeliveryCourierMapFragment().newInstance(courierList,origin);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.courierMapContainer, f);
			ft.addToBackStack(null);
			ft.commit();
		}
		
		if(tab.getPosition() == 4){
			validateDelivery();
			DeliveryFinishFragment f = new DeliveryFinishFragment().newInstance(delivery);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.deliveryFinishContainer, f);
			ft.addToBackStack(null);
			ft.commit();
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		if(tab.getPosition() == 0){
			/**/
		}
		
		if(tab.getPosition() == 1){
			listActionButton.setVisible(false);
			mapActionButton.setVisible(false);
			SupportMapFragment f = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.courierMapFragment);
			if(f != null){
				getSupportFragmentManager().beginTransaction().remove(f).commit();
			}
		}
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
				return DeliveryCourierFragment.newInstance();
			case 2:
				return DeliveryDestinationFragment.newInstance();
			case 3:
				return DeliveryDetailsFragment.newInstance();
			case 4:
				return DeliveryFinishPlaceholderFragment.newInstance();
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_request_delivery_origin)
						.toUpperCase(l);
			case 1:
				return getString(R.string.title_request_delivery_courier)
						.toUpperCase(l);
			case 2:
				return getString(R.string.title_request_delivery_destination)
						.toUpperCase(l);
			case 3:
				return getString(R.string.title_request_delivery_details)
						.toUpperCase(l);
			case 4:
				return getString(R.string.title_request_delivery_finish)
						.toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public void setOrigin(ParseGeoPoint geoLocation, String addressName) {
		
		delivery.origin = AddressHelper.parseGeoPointToLatLng(geoLocation);
		delivery.originAddress = addressName;
		
		if(delivery.origin != null){
			origin = delivery.origin;
			ParseUser.getQuery().whereWithinKilometers("lastLocation", geoLocation,3)
			.findInBackground(new FindCallback<ParseUser>() {
				
				@Override
				public void done(List<ParseUser> nearUsers, ParseException e) {
					if(e == null){
						if(nearUsers != null){
							Log.i(Consts.TAG, "User success found! ["+nearUsers.size()+"]");
							courierList.clear();
							courierList.addAll(nearUsers);
						}
						else{
							Toast.makeText(getApplicationContext(), "No couriers found!", Toast.LENGTH_SHORT)
							.show();
						}
					}
					else{
						Log.e(Consts.TAG, "Error retrieving couriers!");
					}
				}
			});
		}
	}

	@Override
	public void setDestination(ParseGeoPoint geoPoint, String addressName) {
		delivery.destination = AddressHelper.parseGeoPointToLatLng(geoPoint);
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
		Toast.makeText(getApplicationContext(), "Details saved", Toast.LENGTH_SHORT)
		.show();
	}

	@Override
	public void validateDelivery() {
		if (delivery.origin == null) {
			mViewPager.setCurrentItem(0);
			Toast.makeText(this, "Specify Origin!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (delivery.courierId == null || delivery.courierId.isEmpty()) {
			mViewPager.setCurrentItem(1);
			Toast.makeText(this, "Specify Courier!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (delivery.destination == null) {
			mViewPager.setCurrentItem(2);
			Toast.makeText(this, "Specify Destination!", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (delivery.detailedDestination == null || delivery.detailedOrigin.isEmpty()) {
			mViewPager.setCurrentItem(3);
			Toast.makeText(this, "Specify Detailed Origin!", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (delivery.detailedDestination == null || delivery.detailedDestination.isEmpty()) {
			mViewPager.setCurrentItem(3);
			Toast.makeText(this, "Specify Detailed Destination!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (delivery.name == null || delivery.name.isEmpty()) {
			mViewPager.setCurrentItem(3);
			Toast.makeText(this, "Specify Package Name!", Toast.LENGTH_SHORT)
					.show();
			return;
		}
	}

	@Override
	public void showCourierProfile(String objectId) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment f = new DeliveryCourierProfileFragment().newInstance(objectId);
		ft.replace(R.id.courierMapContainer, f);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void setCourierFromMap(String objectId) {
		delivery.courierId = objectId;
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment f = new DeliveryCourierMapFragment().newInstance(courierList, origin);
		ft.replace(R.id.courierMapContainer, f);
		ft.addToBackStack(null);
		ft.commit();
		
		mViewPager.setCurrentItem(3);
	}

	@Override
	public void setCourierFromList(String objectId) {
		delivery.courierId = objectId;
		mViewPager.setCurrentItem(3);
	}
}
