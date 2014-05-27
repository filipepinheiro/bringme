package pt.ua.icm.bringme;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

	private TextView emailField, passwordField;
	private String emailValue, passwordValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		emailField = (TextView) findViewById(R.id.loginEmailField);
		passwordField = (TextView) findViewById(R.id.loginPasswordField);

		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ", "91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			loginSuccess();
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
		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ", "91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");
		emailValue = emailField.getText().toString();
		passwordValue = passwordField.getText().toString();
		
		ParseUser.logInInBackground(emailValue, passwordValue, new LogInCallback() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				if(e == null){
					loginSuccess();
				}else{
					int errorCode = e.getCode();
					
					if(errorCode == ParseException.CONNECTION_FAILED){
						Toast.makeText(getApplicationContext(), "Connection Failed!", Toast.LENGTH_SHORT).show();
					}
					else if(errorCode == ParseException.EMAIL_MISSING)
						emailField.setError("Empty Field");
					else if(errorCode == ParseException.PASSWORD_MISSING){
						passwordField.setError("Empty Field");
					}else if(errorCode == ParseException.OBJECT_NOT_FOUND){
						Toast.makeText(getApplicationContext(), "User doesn't exist!", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(getApplicationContext(), "Error Ocurred!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	public void loginSuccess() {
		Intent menuIntent = new Intent(this, MenuActivity.class);
		startActivity(menuIntent);
	}

	public void loginFail() {
		if (emailValue.isEmpty()) {
			
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
		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ", "91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show();
			loginSuccess();
		}

		//Facebook app id for initialization
		ParseFacebookUtils.initialize(getString(R.string.app_id));

		//List<String> facebookPermissions = Arrays.asList("public_profile", "user_friends");
		ParseUser.logOut();
		//ParseFacebookUtils.logIn(this, new LogInCallback() {
		ParseFacebookUtils.logIn(Arrays.asList(Permissions.User.EMAIL, Permissions.User.PHOTOS,
			     Permissions.User.HOMETOWN,Permissions.User.LOCATION,Permissions.User.BIRTHDAY),this, new LogInCallback() {
			 @Override
			public void done(ParseUser user, ParseException e) {
				if (user == null) {
					Log.i(Consts.TAG, "Oops, something wen't wrong!" + e);
				} else if (user.isNew()) {
					Log.i(Consts.TAG, "Register and Login with facebook Success!");
					loginSuccess();
				} else {
					Log.i(Consts.TAG, "Login with facebook Success!");
					loginSuccess();
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