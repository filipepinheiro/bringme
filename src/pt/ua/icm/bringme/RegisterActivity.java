package pt.ua.icm.bringme;

import pt.ua.icm.bringme.models.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.codec.digest.DigestUtils;

public class RegisterActivity extends Activity {
	
	final Context context = getBaseContext();
	
	//GUI components
	private TextView firstNameField, lastNameField, emailField, passwordField, passwordConfirmationField, phoneNumberField;
	private String firstNameValue, lastNameValue, emailValue, passwordValue, passwordConfirmationValue, phoneNumberValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//Initialize parse connection
		Parse.initialize(this, "BAK4DQx9H7pvSsQJTRKKH4MF0souYuQ6E1l5AMa6", "qnftcNXKP2BevHACysHKbaK3lhGjQPDrINBT6cRI");
		
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
		String hashedPassword; //String with the password encrypted
		
		boolean emailExists = true;

		int registeredEmailCount = 0;
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
		
		try {
			 registeredEmailCount = query.whereEqualTo("email", emailValue).count();
			 
			 Log.i(Consts.TAG,"Registered accounts with email ["+emailValue+"] -> "+registeredEmailCount);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(registeredEmailCount == 0){
			emailExists = false;
		}
		
		if(!passwordValue.equals(passwordConfirmationValue)){
			passwordField.setText("");
			passwordConfirmationField.setText("");
			passwordField.setError("Password doesn't match");
			return;
		}
		
		hashedPassword = DigestUtils.shaHex(passwordValue);
		if (!emailExists) {
			// TODO: Register account on cloud
			
			if(!hashedPassword.equals(null) && !hashedPassword.isEmpty()){
				ParseObject newUser = new ParseObject("User");
				newUser.put("email", emailValue);
				newUser.put("password", hashedPassword);
				newUser.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						if(e == null){
							userRegisterSuccess();
						}else{
							userRegisterFail();
						}
					}
				});
			}
		}
		else{
			emailField.setError("Email already in use!");
		}
	}
	
	public void userRegisterSuccess(){
		Intent launchApp = new Intent(this, MainMenuActivity.class);
		startActivity(launchApp);
	}
	
	public void userRegisterFail(){
		Toast.makeText(context, R.string.registration_fail, Toast.LENGTH_SHORT).show();
	}

	public User createUserFromForm() {
		firstNameValue = firstNameField.getText().toString();
		lastNameValue = lastNameField.getText().toString();
		emailValue = emailField.getText().toString();
		passwordValue = passwordField.getText().toString();
		phoneNumberValue = phoneNumberField.getText().toString();
		
		User userInstance = new User(
				firstNameValue, 
				lastNameValue, 
				emailValue, 
				passwordValue, 
				phoneNumberValue);

		return userInstance;
	}

}
