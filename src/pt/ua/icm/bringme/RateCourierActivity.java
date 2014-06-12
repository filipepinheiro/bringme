package pt.ua.icm.bringme;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.os.Build;

public class RateCourierActivity extends ActionBarActivity {
	
	Button submitButton;
	RatingBar courierRatingBar;
	ParseObject delivery;
	ParseUser courier;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate_courier);
		
		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ",
				"91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");
		Log.d(Consts.TAG, "Initialized parse.");
		
		//UI Initialization
		submitButton = (Button) findViewById(R.id.submitCourierRate);
		courierRatingBar  = (RatingBar) findViewById(R.id.profileRating);
		Log.d(Consts.TAG, "Initialized UI.");
		
		String deliveryId = getIntent().getStringExtra("deliveryId");
		Log.d(Consts.TAG, "deliveryId: "+ deliveryId);
		ParseQuery<ParseObject> queryDelivery = new ParseQuery<ParseObject>("Delivery");
		queryDelivery.getInBackground(deliveryId, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				if(e == null){
					delivery = object;
					courier = object.getParseUser("courier");
					Log.d(Consts.TAG, "Courier: " + courier.getObjectId());
				}
				else{
					//TODO: Handle the error
					e.printStackTrace();
				}
			}
		});
		submitButton.setOnClickListener(submitRating());
	}

	private OnClickListener submitRating() {
		return new OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				Log.d(Consts.TAG, "Clicked Submit button!");
				
				int rating = courierRatingBar.getProgress();
				if(courier != null && delivery != null){
					ParseObject courierRating = new ParseObject("CourierRating");
					courierRating.put("courier", courier);
					courierRating.put("delivery", delivery);
					courierRating.put("rating", rating);
					courierRating.saveInBackground(new SaveCallback() {
						
						@Override
						public void done(ParseException e) {
							if(e == null){
								Log.d(Consts.TAG, "Save rating success!");
								startActivity(new Intent(v.getContext(), MainActivity.class));
							}else{
								Log.e(Consts.TAG, "Save rating failed!");
								Log.d(Consts.TAG, e.getMessage());
							}
						}
					}); 
				 }
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rate_courier, menu);
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

}
