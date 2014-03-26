package pt.ua.icm.bringme;

import pt.ua.icm.bringme.datastorage.SQLHelper;
import pt.ua.icm.bringme.models.User;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		//Enable ActionBar Home
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
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
		// TODO Auto-generated method stub
		super.onStart();
		TextView fullName = (TextView) findViewById(R.id.profileFullName);
		TextView email = (TextView) findViewById(R.id.profileEmailField);
		TextView phoneNumber = (TextView) findViewById(R.id.profilePhoneField);

		SharedPreferences prefs = getSharedPreferences("pt.ua.icm.bringme",
				Context.MODE_PRIVATE);
		int userID = prefs.getInt("userID", -1);

		SQLHelper mSQLHelper = new SQLHelper(this);
		if (userID != -1) {
			User user = mSQLHelper.getUser(userID);

			fullName.setText(user.getFullName());
			email.setText(user.getEmail());
			phoneNumber.setText(user.getPhoneNumber());
		}
	}

}
