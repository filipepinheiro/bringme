package pt.ua.icm.bringme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

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
	
	/**
	 * Attempts to login trying to authenticate into the system through the
	 * BringMe database
	 * 
	 * @param view
	 */
	public void loginWithBringme(View view){
		//TODO: remove initialization
		boolean authorized = true;
		
		//TODO: Account Authorization
		
		if(authorized){
			Intent mainMenuIntent = new Intent(this,MainMenuActivity.class);
			startActivity(mainMenuIntent);
		}
		else{
			//TODO: Error Handling
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
