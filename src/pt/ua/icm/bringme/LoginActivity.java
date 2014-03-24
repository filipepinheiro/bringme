package pt.ua.icm.bringme;

import java.util.prefs.Preferences;

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.internal.em;

import pt.ua.icm.bringme.datastorage.SQLHelper;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DebugUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
		
		SQLHelper BringMeSQL = new SQLHelper(this);
		
		SQLiteDatabase SQLDBRead = BringMeSQL.getReadableDatabase();
		Cursor cs = SQLDBRead.rawQuery("SELECT count(*) FROM users", null);
		cs.moveToFirst();
		int count = cs.getInt(0);
		Log.i("BringMeLogin", "Found "+count+ " registered accounts!");
		
		//GooglePlayServicesUtil.getErrorDialog(ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED, this, 0).show();
	}
	
	/**
	 * Attempts to login trying to authenticate into the system through the
	 * BringMe database
	 * 
	 * @param view
	 */
	public void loginWithBringme(View view){
		String email = ((TextView) findViewById(R.id.loginEmailField)).getText().toString();
		String password = ((TextView) findViewById(R.id.loginPasswordField)).getText().toString();
		
		//Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},false, null, null, null, null);
		 //startActivityForResult(intent, 2);

		
		int userID = new SQLHelper(this).existsUser(email, password);
		
		if(userID != SQLHelper.INVALID_USER_AUTHENTICATION){
			//Get private SharedPreferences
			SharedPreferences prefs = this.getSharedPreferences("pt.ua.icm.bringme", Context.MODE_PRIVATE);
			prefs.edit().putInt("userID", userID).commit();
			
			Intent mainMenuIntent = new Intent(this,MainMenuActivity.class);
			startActivity(mainMenuIntent);
		}
		else{
			TextView emailField = (TextView) findViewById(R.id.loginEmailField);
			TextView passwordField = (TextView) findViewById(R.id.loginPasswordField);
			
			if(emailField.getText().toString().isEmpty()){
				emailField.setError("Empty Field");
			}else{
				emailField.setError("Wrong Credentials!");
			}
			if(passwordField.getText().toString().isEmpty()){
				passwordField.setError("Empty Field");
			}else{
				passwordField.setError("Wrong Credentials!");
			}
		}
	}
	
	/**
	 * Attempts to login trying to authenticate into the system through the
	 * Facebook authentication system
	 * 
	 * @param view
	 */
	public void loginWithFacebook(View view){
		//TODO: remove initialization
		boolean authorized = true;
		
		//TODO: Account Authorization with facebook
		
		if(authorized){
			Intent mainMenuIntent = new Intent(this,MainMenuActivity.class);
			startActivity(mainMenuIntent);
		}
		else{
			//TODO: Error Handling
		}
	}
	
	/**
	 * Changes to the register account activity
	 * 
	 * @param view
	 */
	public void registerAccount(View view){
		Intent registerAccountIntent = new Intent(this,RegisterActivity.class);
		startActivity(registerAccountIntent);
	}

}
