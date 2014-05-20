package pt.ua.icm.bringme;

import pt.ua.icm.bringme.models.User;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends ActionBarActivity {

	TextView fullNameField, emailField, phoneNumberField;
	User currentUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		//Enable ActionBar Home
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

		fullNameField.setText(currentUser.getFullName());
		emailField.setText(currentUser.getEmail());
		phoneNumberField.setText(currentUser.getPhoneNumber());
	}

}
