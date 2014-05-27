package pt.ua.icm.bringme;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.User;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
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

		// Image of user
		/*
		 * Bitmap finalBmp = BitmapFactory.decodeResource(getResources(),
		 * R.drawable.mourinho); RoundedImageView riv = (RoundedImageView)
		 * findViewById(R.id.userImage); riv.setImageBitmap(finalBmp);
		 * riv.setBorderColor(Color.rgb(35, 228, 137));
		 */

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

		// fullNameField.setText(currentUser.getFullName());
		// emailField.setText(currentUser.getEmail());
		// phoneNumberField.setText(currentUser.getPhoneNumber());

		ParseFacebookUtils.initialize(getString(R.string.app_id));

		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
								fullNameField.setText(user.getName());

								if (user.getProperty("email") != null) {
									emailField.setText((String) user
											.getProperty("email"));
								} else
									emailField.setText(getString(R.string.no_email));
								if(user.getProperty("phone_number") != null){
									phoneNumberField.setText(user.getProperty("phone_number").toString());
								} else
									phoneNumberField.setText(getString(R.string.no_phone_number));
									

								//Profile Picture AssyncTask
								LongOperation lo = new LongOperation();
								lo.execute(user.getId().toString());
								fbUserId = user.getId().toString();

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

	private String fbUserId;
	
	//Used to load the profile pic
	private class LongOperation extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {

			ParseFacebookUtils.initialize(getString(R.string.app_id));

			URL imageURL;
			try {
				imageURL = new URL("https://graph.facebook.com/" + fbUserId
						+ "/picture?type=large");
				bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}

		private Bitmap bitmap;
		
		@Override
		protected void onPostExecute(Void result) {

			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you
			RoundedImageView riv = (RoundedImageView) findViewById(R.id.userImage);
			riv.setImageBitmap(bitmap);
			riv.setBorderColor(Color.rgb(35, 228, 137));
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

}
