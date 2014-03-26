package pt.ua.icm.bringme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Open the requestDeliveryActivity
	 * 
	 * @param view
	 */
	public void requestDelivery(View view) {
		Intent requestDeliveryIntent = new Intent(this,
				RequestDeliveryActivity.class);
		startActivity(requestDeliveryIntent);
	}

	/**
	 * 
	 * @param view
	 */
	public void deliveryStatus(View view) {
		Intent deliveryStatusIntent = new Intent(this,
				DeliveryStatusActivity.class);
		startActivity(deliveryStatusIntent);
	}

	/**
	 * 
	 * @param view
	 */
	public void myRequestsList(View view) {
		Intent myRequestListIntent = new Intent(this, RequestListActivity.class);
		startActivity(myRequestListIntent);
	}

	/**
	 * 
	 * @param view
	 */
	public void myHistorical(View view) {
		Intent historicalIntent = new Intent(this, HistoricalActivity.class);
		startActivity(historicalIntent);
	}

	/**
	 * 
	 * @param view
	 */
	public void profile(View view) {
		Intent profileIntent = new Intent(this, ProfileActivity.class);
		startActivity(profileIntent);
	}
}
