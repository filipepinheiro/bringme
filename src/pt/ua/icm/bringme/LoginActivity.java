package pt.ua.icm.bringme;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import pt.ua.icm.bringme.datastorage.LocalData;
import pt.ua.icm.bringme.models.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.android.Facebook;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.codec.digest.DigestUtils;

public class LoginActivity extends Activity {

	private TextView emailField, passwordField;
	private String emailValue, passwordValue;

	private static String TAG = "FACEBOOK LOGIN";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (getSharedPreferences("userID", Context.MODE_PRIVATE).getString(
				"userID", null) != null) {
			startActivity(new Intent(this, MainMenuActivity.class));
		}

		emailField = (TextView) findViewById(R.id.loginEmailField);
		passwordField = (TextView) findViewById(R.id.loginPasswordField);

		
		Parse.initialize(this, "BAK4DQx9H7pvSsQJTRKKH4MF0souYuQ6E1l5AMa6",
				"qnftcNXKP2BevHACysHKbaK3lhGjQPDrINBT6cRI");

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			Toast.makeText(getApplicationContext(),
					"Tas logado como: " + currentUser.getUsername(),
					320).show();
			
			loginSuccess(currentUser);
			
		} else {
			Toast.makeText(getApplicationContext(), "Nao ha sessao com o FB.",
					220).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to login trying to authenticate into the system through the
	 * BringMe database
	 * 
	 * @param view
	 */
	public void loginWithBringme(View view) {
		// Initialize parse connection
		Parse.initialize(this, "BAK4DQx9H7pvSsQJTRKKH4MF0souYuQ6E1l5AMa6",
				"qnftcNXKP2BevHACysHKbaK3lhGjQPDrINBT6cRI");

		emailValue = emailField.getText().toString();
		passwordValue = passwordField.getText().toString();
		String hashedPassword = DigestUtils.shaHex(passwordValue);

		Log.i(Consts.TAG, "Trying login with email[" + emailValue
				+ "] | password[" + hashedPassword + "]");

		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
		query.whereEqualTo("email", emailValue);
		query.whereEqualTo("password", hashedPassword);

		query.getFirstInBackground(new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					loginSuccess(object);
				} else {
					Log.i(Consts.TAG, e.getMessage());
					loginFail();
				}
			}
		});
	}

	public void loginSuccess(ParseObject user) {
		SharedPreferences prefs = getSharedPreferences("pt.ua.icm.bringme",
				Context.MODE_PRIVATE);
		prefs.edit().putString("userID", user.getObjectId()).commit();

		String id, firstName, lastName, email, phoneNumber;
		boolean isCourier;

		id = user.getObjectId();
		firstName = user.getString("firstName");
		lastName = user.getString("lastName");
		email = user.getString("email");
		phoneNumber = user.getString("phoneNumber");
		isCourier = user.getBoolean("isCourier");

		LocalData.currentUser = new User(firstName, lastName, email,
				phoneNumber);
		LocalData.currentUser.setId(id);
		LocalData.currentUser.setCourier(isCourier);
		LocalData.currentUser.setCurrentLocation(user
				.getParseGeoPoint("currentLocation"));

		Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
		startActivity(mainMenuIntent);
	}

	public void loginFail() {
		if (emailValue.isEmpty()) {
			emailField.setError("Empty Field");
		} else {
			emailField.setError("Wrong Credentials!");
		}
		if (passwordValue.isEmpty()) {
			passwordField.setError("Empty Field");
		} else {
			passwordField.setError("Wrong Credentials!");
		}
	}

	/**
	 * Attempts to login trying to authenticate into the system through the
	 * Facebook authentication system
	 * 
	 * @param view
	 */
	public void loginWithFacebook(View view) {
		// TODO: Account Authorization with facebook
		loginParseFacebook();
	}

	
	private void loginParseFacebook() {

		Parse.initialize(this, "BAK4DQx9H7pvSsQJTRKKH4MF0souYuQ6E1l5AMa6",
				"qnftcNXKP2BevHACysHKbaK3lhGjQPDrINBT6cRI");

	
		//facebook app id for initialization
		ParseFacebookUtils.initialize(getString(R.string.app_id));

		List<String> permissions = Arrays.asList("basic_info", "user_about_me",
				"user_relationships", "user_birthday", "user_location");

		
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				
				if (user == null) {
					Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d(TAG, "User signed up and logged in through Facebook!");
				} else {
					Log.d(TAG, "User logged in through Facebook!");
				}

			}
		});
		

	}

	/**
	 * Changes to the register account activity
	 * 
	 * @param view
	 */
	public void registerAccount(View view) {
		Intent registerAccountIntent = new Intent(this, RegisterActivity.class);
		startActivity(registerAccountIntent);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

}
