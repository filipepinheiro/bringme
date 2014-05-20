package pt.ua.icm.bringme;

import java.util.LinkedList;
import java.util.List;

import pt.ua.icm.bringme.adapters.DeliveryRequestAdapter;
import pt.ua.icm.bringme.models.Delivery;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class RequestListActivity extends ActionBarActivity {

	List<Delivery> requestList = new LinkedList<Delivery>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_list);
		
		//Enable ActionBar Home
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		//requestList = StaticDatabase.deliveryRequestList;

		ListView requestsListView = (ListView) findViewById(R.id.deliveryRequestListView);
		requestsListView.setAdapter(new DeliveryRequestAdapter(this, requestList));
		// TODO: Retrieve Data of the Deliveries that are requesting this user
	}

	//TODO: Use appCompat for compatibility
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_list, menu);
		return true;
	}
	
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
}
