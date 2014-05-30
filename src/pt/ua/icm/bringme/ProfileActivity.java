package pt.ua.icm.bringme;

import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.FacebookPhotoHelper;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.User;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
		
		
		//TODO: GET INFO ABOUT REQUESTS AND DELIVERIES

		
		//.............................................
		
		
		ParseFacebookUtils.initialize(getString(R.string.app_id));

		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {

							// Profile Picture AssyncTask
							try {
								FacebookPhotoHelper photoHelper = new FacebookPhotoHelper(
										user.getId().toString(),
										getString(R.string.app_id));
								photoHelper.execute();

								Bitmap bmp = photoHelper.get();
								
								RoundedImageView riv = (RoundedImageView) findViewById(R.id.userImage);
								riv.setImageBitmap(bmp);
								riv.setBorderColor(Color.parseColor(getString(R.color.green_letter)));
								
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.e("Facebook parsing data",
										"The facebook session was invalidated.");
								// onLogoutButtonClicked();
							} else {
								Log.e("Facebook parsing data",
										"Some other error: "
												+ response.getError()
														.getErrorMessage());
							}
						}
					}
				});
		request.executeAsync();

	}

}
