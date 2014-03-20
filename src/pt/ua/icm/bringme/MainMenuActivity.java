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
	 * @param view
	 */
	public void requestDelivery(View view){
		Intent requestDeliveryIntent = new Intent(this,RequestDeliveryActivity.class);
		startActivity(requestDeliveryIntent);
	}

}
