package pt.ua.icm.bringme;

import pt.ua.icm.bringme.helpers.BitmapHelper;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.User;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.ParseUser;

public class ProfileActivity extends ActionBarActivity {

	TextView fullNameField, emailField, phoneNumberField;
	User currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		// Enable ActionBar Home
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		fullNameField = (TextView) findViewById(R.id.profileFullName);
		emailField = (TextView) findViewById(R.id.profileEmailField);
		phoneNumberField = (TextView) findViewById(R.id.profilePhoneField);
	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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
*/
	@Override
	protected void onStart() {
		super.onStart();
		String fullName ="";
		ParseUser user = ParseUser.getCurrentUser();
		if(user.get("firstName") != null)
			fullName = user.get("firstName").toString();
		if(user.get("lastName") != null)
			fullName += " "+ user.get("lastName").toString();
		
		fullNameField.setText(fullName);
		
		if(user.get("email") != null)
			emailField.setText(user.get("email").toString());
		if(user.get("phoneNumebr") != null)
			phoneNumberField.setText(user.get("phoneNumber").toString());
		else
			phoneNumberField.setText("Do not has phone.");
		
		byte[] profilePictureBytes = user.getBytes("pic").clone();
		RoundedImageView imageProfile = (RoundedImageView) findViewById(R.id.userImage);
		imageProfile.setImageBitmap(BitmapHelper.byteArrayToBitmap(profilePictureBytes));
		imageProfile.setBorderColor(Color.parseColor(getString(R.color.green_peas)));
		
		
		
		//Static rate
		RatingBar rbar = (RatingBar) findViewById(R.id.starRatingBar);
		float rate = Float.parseFloat("3.8");
		rbar.setRating(rate);
		rbar.setEnabled(false);
				
		
		//TODO: GET INFO ABOUT REQUESTS, DELIVERIES AND RATING

		
		//.............................................
		
		
	

	}

}
