package pt.ua.icm.bringme;

import java.util.List;
import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.BitmapHelper;
import pt.ua.icm.bringme.helpers.FacebookImageLoader;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.User;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ProfileActivity extends ActionBarActivity {

	private TextView fullNameField, emailField, phoneNumberField, requestsField,
			deliveriesField;
	private RatingBar profileRating;
	private User currentUser;
	private LinearLayout loader, profile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		// Enable ActionBar Home
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		fullNameField = (TextView) findViewById(R.id.profileFullName);
		emailField = (TextView) findViewById(R.id.profileEmailField);
		phoneNumberField = (TextView) findViewById(R.id.profilePhoneField);
		requestsField = (TextView) findViewById(R.id.deliveryStatusPackageCurrentPostalField);
		deliveriesField = (TextView) findViewById(R.id.deliveryStatusCourierNameField);
		profileRating = (RatingBar) findViewById(R.id.profileRating);
		
		loader = (LinearLayout) findViewById(R.id.profileLoaderContainer);
		profile = (LinearLayout) findViewById(R.id.profileContainer);
		
		showLoader();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		Log.d(Consts.TAG, "Starting Profile");
		
		String fullName = "";
		ParseUser user = ParseUser.getCurrentUser();
		fullName = user.get("firstName").toString();
		fullName += " " + user.get("lastName").toString();

		fullNameField.setText(fullName);

		emailField.setText(user.get("email").toString());
		
		if (user.get("phoneNumebr") != null){
			phoneNumberField.setText(user.get("phoneNumber").toString());
		}
		else{
			phoneNumberField.setText("Do not has phone.");
		}

		if (user.get("requests") != null){
			requestsField.setText(user.get("requests").toString());
		}
		else{
			requestsField.setText("0");
		}

		if (user.get("deliveries") != null){
			deliveriesField.setText(user.get("deliveries").toString());
		}
		else{
			deliveriesField.setText("0");
		}

		RoundedImageView drawerProfilePicture = 
				(RoundedImageView) findViewById(R.id.deliveryCourierUserImage);
		drawerProfilePicture.setBorderColor(Color
				.parseColor(getString(R.color.green_peas)));
		
		FacebookImageLoader profilePictureLoader = new FacebookImageLoader();
		
		Bitmap profilePicture = null;
		 
		try {
			if(user.has("facebookId")){
				profilePicture = profilePictureLoader.execute(user.getString("facebookId"),"large").get();
			}
		} catch (InterruptedException e) {
			Log.e(Consts.TAG, "Load profile picture was cancelled!");
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.e(Consts.TAG, "Execution Error!");
			e.printStackTrace();
		}
		 
		if (profilePicture != null) {
			drawerProfilePicture.setImageBitmap(profilePicture);
			drawerProfilePicture.setBorderColor(
					Color.parseColor(getString(R.color.green_peas)));
		} else {
			Bitmap defaultPicture = BitmapHelper.drawableToBitmap(
					R.drawable.default_profile_picture, this);
		
			drawerProfilePicture.setImageBitmap(defaultPicture);
			drawerProfilePicture.setBorderColor(Color.parseColor(getString(R.color.green_peas)));
		}
		
		if (user.get("rating") != null) {
			ParseQuery<ParseObject> queryRating = new ParseQuery<ParseObject>("CourierRating");
			queryRating.whereEqualTo("courier", ParseUser.getCurrentUser());
			queryRating.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(e == null){
						double sum = 0;
						int count = 0;
						
						if(objects != null){
							count = objects.size();
						}
						
						for(ParseObject courierRating : objects){
							sum = sum + courierRating.getInt("rating");
						}
						
						setRating((float) (sum/count));
						showProfile();
					}
				}
			});
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(getApplicationContext(), MainActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setRating(float rate){
		profileRating.setRating(rate);
	}
	
	private void showLoader(){
		loader.setVisibility(View.VISIBLE);
		profile.setVisibility(View.GONE);
	}
	
	private void showProfile(){
		loader.setVisibility(View.GONE);
		profile.setVisibility(View.VISIBLE);
	}
}