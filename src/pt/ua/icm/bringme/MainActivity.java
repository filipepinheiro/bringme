package pt.ua.icm.bringme;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ActionBar actionBar = getActionBar();

		actionBar.setTitle(getResources().getText(R.string.app_name)); //Set action bar title
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); //Set navigation mode as TABS
		
		//Add actionBar Tabs
		actionBar.addTab(actionBar.newTab().setText("My Deliveries").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Find Courier").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Delivery Status").setTabListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
