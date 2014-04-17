package pt.ua.icm.bringme;

import pt.ua.icm.bringme.datastorage.SQLHelper;
import pt.ua.icm.bringme.models.User;

import com.parse.*;
import com.parse.codec.digest.DigestUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private TextView emailField, passwordField;
	private String emailValue, passwordValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		emailField = (TextView) findViewById(R.id.loginEmailField);
		passwordField = (TextView) findViewById(R.id.loginPasswordField);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();
/*
		SQLHelper BringMeSQL = new SQLHelper(this);

		SQLiteDatabase SQLDBRead = BringMeSQL.getReadableDatabase();
		Cursor cs = SQLDBRead.rawQuery("SELECT count(*) FROM users", null);
		cs.moveToFirst();
		int count = cs.getInt(0);
		Log.i("BringMeLogin", "Found " + count + " registered accounts!");*/
	}

	/**
	 * Attempts to login trying to authenticate into the system through the
	 * BringMe database
	 * 
	 * @param view
	 */
	public void loginWithBringme(View view) {
		//Initialize parse connection
		Parse.initialize(this, "BAK4DQx9H7pvSsQJTRKKH4MF0souYuQ6E1l5AMa6", "qnftcNXKP2BevHACysHKbaK3lhGjQPDrINBT6cRI");
		
		emailValue = emailField.getText().toString();
		passwordValue = passwordField.getText().toString();
		String hashedPassword = DigestUtils.shaHex(passwordValue);
		String userID = null;
		
		Log.i(Consts.TAG, "Trying login with email["+emailValue+"] | password["+hashedPassword+"]");
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
		query.whereEqualTo("email", emailValue);
		query.whereEqualTo("password", hashedPassword);
		
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				if(e == null){
					loginSuccess(object);
				}else{
					Log.i(Consts.TAG, e.getMessage());
					loginFail();
				}
			}
		});
		
		/*String email = ((TextView) findViewById(R.id.loginEmailField))
				.getText().toString();
		String password = ((TextView) findViewById(R.id.loginPasswordField))
				.getText().toString();

		int userID = new SQLHelper(this).existsUser(email, password);

		if (userID != SQLHelper.INVALID_USER_AUTHENTICATION) {
			// Get private SharedPreferences
			SharedPreferences prefs = this.getSharedPreferences(
					"pt.ua.icm.bringme", Context.MODE_PRIVATE);
			prefs.edit().putInt("userID", userID).commit();

			Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
			startActivity(mainMenuIntent);
		} else {
			
		}*/
	}
	
	public void loginSuccess(ParseObject parseUser){
		SharedPreferences prefs = this.getSharedPreferences(
				"pt.ua.icm.bringme", Context.MODE_PRIVATE);
		prefs.edit().putString("userID", parseUser.getObjectId()).commit();
		
		Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
		startActivity(mainMenuIntent);
	}
	
	public void loginFail(){
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
		// TODO: remove initialization
		boolean authorized = false;

		// TODO: Account Authorization with facebook

		if (authorized) {
			Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
			startActivity(mainMenuIntent);
		} else {
			// TODO: Error Handling
		}
		Toast t = Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_SHORT);
		t.show();
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

}
