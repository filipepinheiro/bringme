package pt.ua.icm.bringme;

import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.BitmapHelper;
import pt.ua.icm.bringme.helpers.FacebookImageLoader;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.User;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.ParseUser;

public class ProfileActivity extends ActionBarActivity {

	TextView fullNameField, emailField, phoneNumberField, requestsField,
			deliveriesField;
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
		requestsField = (TextView) findViewById(R.id.deliveryStatusPackageCurrentPostalField);
		deliveriesField = (TextView) findViewById(R.id.deliveryStatusCourierNameField);

	}

	@Override
	protected void onStart() {
		super.onStart();
		String fullName = "";
		ParseUser user = ParseUser.getCurrentUser();
		if (user.get("firstName") != null)
			fullName = user.get("firstName").toString();
		if (user.get("lastName") != null)
			fullName += " " + user.get("lastName").toString();

		fullNameField.setText(fullName);

		if (user.get("email") != null)
			emailField.setText(user.get("email").toString());
		if (user.get("phoneNumebr") != null)
			phoneNumberField.setText(user.get("phoneNumber").toString());
		else
			phoneNumberField.setText("Do not has phone.");

		if (user.get("requests") != null)
			requestsField.setText(user.get("requests").toString());
		else
			requestsField.setText("0");

		if (user.get("deliveries") != null)
			deliveriesField.setText(user.get("deliveries").toString());
		else
			deliveriesField.setText("0");

		
		
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
		
		

		// Static rate
		if (user.get("rating") != null) {
			RatingBar rbar = (RatingBar) findViewById(R.id.starRatingBar);
			float rate = Float.parseFloat(user.get("rating").toString());
			rbar.setRating(rate);
			rbar.setEnabled(false);
		}


	}

}
