package pt.ua.icm.bringme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {	
	//GUI components
	private TextView firstNameField, lastNameField, emailField, passwordField, passwordConfirmationField, phoneNumberField;
	private String firstNameValue, lastNameValue, emailValue, passwordValue, passwordConfirmationValue, phoneNumberValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//Initialize parse connection
		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ", "91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");

		//Bind GUI components
		firstNameField = (TextView) findViewById(R.id.registerFirstNameField);
		lastNameField = (TextView) findViewById(R.id.registerLastNameField);
		emailField = (TextView) findViewById(R.id.registerEmailField);
		passwordField = (TextView) findViewById(R.id.registerPasswordField);
		passwordConfirmationField = (TextView) findViewById(R.id.registerPasswordConfirmationField);
		phoneNumberField = (TextView) findViewById(R.id.registerPhoneNumberField);
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
		emailValue = emailField.getText().toString();
		passwordValue = passwordField.getText().toString();
		passwordConfirmationValue = passwordConfirmationField.getText().toString();
		phoneNumberValue = phoneNumberField.getText().toString();
		firstNameValue = firstNameField.getText().toString();
		lastNameValue = lastNameField.getText().toString();
		
		Log.i(Consts.TAG, passwordValue + " == " + passwordConfirmationValue);
		
		if(passwordValue.equals(passwordConfirmationValue)){
			ParseUser user = new ParseUser();
			user.setUsername(emailValue);
			user.setPassword(passwordValue);
			user.setEmail(emailValue);
			user.put("phoneNumber", phoneNumberValue);
			user.put("firstName", firstNameValue);
			user.put("lastName", lastNameValue);
			
			user.signUpInBackground(new SignUpCallback() {
				
				@Override
				public void done(ParseException e) {
					if(e == null){
						userRegisterSuccess();
					}else{
						if(e.getCode() == ParseException.EMAIL_TAKEN){
							emailField.setError("Email already taken!");
						}
						Toast.makeText(getApplicationContext(), "Error ocorred, try again later!", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}else{
			passwordField.setError("Password mismatch!");
		}
	}
	
	private void userRegisterSuccess(){
		Intent menuIntent = new Intent(this, MenuActivity.class);
		startActivity(menuIntent);
	}

}
