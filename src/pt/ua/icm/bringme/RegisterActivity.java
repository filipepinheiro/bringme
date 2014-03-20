package pt.ua.icm.bringme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	
	/**
	 * Register an account if the email used isn't used already, and go straight to
	 * the main menu (instant login)
	 * 
	 * @param view
	 */
	public void registerAccount(View view){
		//TODO: Remove initialization
		boolean emailExists = false;
		
		//TODO: Verify if the the email is already registered.
		if(!emailExists){
			//TODO: Register account
			
			Intent mainMenuIntent = new Intent(this,MainMenuActivity.class);
			startActivity(mainMenuIntent);
		}
	}

}
