package pt.ua.icm.bringme;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.BitmapHelper;
import pt.ua.icm.bringme.helpers.FacebookImageLoader;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mPlanetTitles = { "Teste", "ola" };

	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
				R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
		
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		ParseUser user = ParseUser.getCurrentUser();
		
		final RoundedImageView drawerProfilePicture = (RoundedImageView) findViewById(R.id.userImageDrawer);
		byte[] profilePictureBytes = ParseUser.getCurrentUser().getBytes("pic").clone();
		if (profilePictureBytes != null) {
			//Bitmap roundedPicture = BitmapHelper
				//	.getRoundedCornerBitmap(BitmapHelper
					//		.byteArrayToBitmap(profilePictureBytes));
			drawerProfilePicture.setImageBitmap(BitmapHelper
							.byteArrayToBitmap(profilePictureBytes));
			drawerProfilePicture.setBorderColor(Color.parseColor(getString(R.color.green_peas)));
			
			
		} else {

			Bitmap defaultPicture = BitmapHelper.drawableToBitmap(
					R.drawable.default_profile_picture, this);
			//Bitmap roundedPicture = BitmapHelper
			//		.getRoundedCornerBitmap(defaultPicture);
			//drawerProfilePicture.setImageBitmap(roundedPicture);
		
			drawerProfilePicture.setImageBitmap(defaultPicture);
			drawerProfilePicture.setBorderColor(Color.parseColor(getString(R.color.green_peas)));
		}
		
		
		TextView profileName = (TextView) findViewById(R.id.drawerProfileName);
		profileName.setText(user.getString("firstName")+" "+user.getString("lastName"));
		
        /*mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));*/

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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

	public void openProfileActivity(View view) {
		Intent profIntent = new Intent(getApplicationContext(),
				ProfileActivity.class);
		startActivity(profIntent);
	}

	public void userLogout(View view){
		
		/*Intent profIntent = new Intent(this,
				SplashActivity.class);
		startActivity(profIntent);
		*/
		finish();
		ParseUser.getCurrentUser().logOut();

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
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
				return (Fragment) RequestMenuFragment.newInstance();
			case 1:
				return (Fragment) CourierMenuFragment.newInstance();
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section_request_menu)
						.toUpperCase(l);
			case 1:
				return getString(R.string.title_section_courier_menu)
						.toUpperCase(l);
			}
			return null;
		}
	}

	public void onRequestDeliveryClick(View v) {
		Intent requestDeliveryIntent = new Intent(this,
				RequestDeliveryActivity.class);
		startActivity(requestDeliveryIntent);
	}
	
	
}
