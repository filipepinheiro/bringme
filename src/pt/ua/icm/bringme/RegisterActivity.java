package pt.ua.icm.bringme;

import pt.ua.icm.bringme.datastorage.SQLHelper;
import pt.ua.icm.bringme.models.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
	 * Register an account if the email used isn't used already, and go straight
	 * to the main menu (instant login)
	 * 
	 * @param view
	 */
	public void registerAccount(View view) {
		// TODO: Validate the fields

		// TODO: Remove initialization
		boolean emailExists = false;

		// TODO: Verify if the the email is already registered.
		if (!emailExists) {
			// TODO: Register account on cloud
			SQLHelper SQLQuery = new SQLHelper(this);
			SQLQuery.insertUser(createUserFromForm());
			
			Toast t = Toast.makeText(this, "Account created with success!",
					Toast.LENGTH_SHORT);
			t.show();
			
			Intent mainMenuIntent = new Intent(this, MainMenuActivity.class);
			startActivity(mainMenuIntent);
		}
	}

	public User createUserFromForm() {
		TextView firstName = (TextView) findViewById(R.id.registerFirstNameField);
		TextView lastName = (TextView) findViewById(R.id.registerLastNameField);
		TextView email = (TextView) findViewById(R.id.registerEmailField);
		TextView password = (TextView) findViewById(R.id.registerPasswordField);
		TextView phoneNumber = (TextView) findViewById(R.id.registerPhoneNumberField);

		User userInstance = new User(firstName.getText().toString(), lastName
				.getText().toString(), email.getText().toString(), password
				.getText().toString(), phoneNumber.getText().toString());

		return userInstance;
	}

}
