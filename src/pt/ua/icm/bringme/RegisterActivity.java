package pt.ua.icm.bringme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends ActionBarActivity {	
	//GUI components
	private TextView firstNameField, lastNameField, emailField, passwordField, passwordConfirmationField, phoneNumberField;
	private String firstNameValue, lastNameValue, emailValue, passwordValue, passwordConfirmationValue, phoneNumberValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().hide();
		
		//Initialize parse connection
		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ", "91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");
<<<<<<< HEAD
		
		setContentView(R.layout.activity_register);
		
=======

>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
		//Bind GUI components
		firstNameField = (TextView) findViewById(R.id.registerFirstNameField);
		lastNameField = (TextView) findViewById(R.id.registerLastNameField);
		emailField = (TextView) findViewById(R.id.registerEmailField);
		passwordField = (TextView) findViewById(R.id.registerPasswordField);
		passwordConfirmationField = (TextView) findViewById(R.id.registerPasswordConfirmationField);
		phoneNumberField = (TextView) findViewById(R.id.registerPhoneNumberField);
		
		Button btRegister = (Button) findViewById(R.id.registerRegisterButton);
		btRegister.setOnClickListener(registerClick());
	}

	private OnClickListener registerClick() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				registerAccount();
			}
		};
	}

	/**
	 * Register an account if the email used isn't used already, and go straight
	 * to the main menu (instant login)
	 * 
	 * @param view
	 */
	public void registerAccount() {
		emailValue = emailField.getText().toString();
		passwordValue = passwordField.getText().toString();
		passwordConfirmationValue = passwordConfirmationField.getText().toString();
		phoneNumberValue = phoneNumberField.getText().toString();
		firstNameValue = firstNameField.getText().toString();
		lastNameValue = lastNameField.getText().toString();
		
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
		Intent menuIntent = new Intent(this, MainActivity.class);
		startActivity(menuIntent);
	}

}
