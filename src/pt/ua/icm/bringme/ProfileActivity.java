package pt.ua.icm.bringme;

import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.BitmapHelper;
import pt.ua.icm.bringme.helpers.FacebookImageLoader;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.User;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class ProfileActivity extends ActionBarActivity {

	TextView fullNameField, emailField, phoneNumberField;
	User currentUser;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		// Enable ActionBar Home
		ActionBar actionBar = getActionBar();
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
		if(ParseUser.getCurrentUser().get("firstName") != null)
			fullName = ParseUser.getCurrentUser().get("firstName").toString();
		if(ParseUser.getCurrentUser().get("lastName") != null)
			fullName += " "+ ParseUser.getCurrentUser().get("lastName").toString();
		
		fullNameField.setText(fullName);
		
		if(ParseUser.getCurrentUser().get("email") != null)
			emailField.setText(ParseUser.getCurrentUser().get("email").toString());
		if(ParseUser.getCurrentUser().get("phoneNumebr") != null)
			phoneNumberField.setText(ParseUser.getCurrentUser().get("phoneNumber").toString());
		else
			phoneNumberField.setText("Do not has phone.");
		
		byte[] profilePictureBytes = ParseUser.getCurrentUser().getBytes("pic").clone();
		RoundedImageView imageProfile = (RoundedImageView) findViewById(R.id.userImage);
		imageProfile.setImageBitmap(BitmapHelper.byteArrayToBitmap(profilePictureBytes));
		imageProfile.setBorderColor(Color.parseColor(getString(R.color.green_peas)));
		
		
		//TODO: GET INFO ABOUT REQUESTS AND DELIVERIES

		
		//.............................................
		
		
	

	}

}
