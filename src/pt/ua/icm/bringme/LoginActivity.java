package pt.ua.icm.bringme;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class LoginActivity extends ActionBarActivity {

	private LinearLayout fieldsContainer;
	private FrameLayout loaderContainer;
	private TextView emailField, passwordField;
	private String emailValue, passwordValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ", "91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");
		
		setContentView(R.layout.activity_login);
		getSupportActionBar().hide();

		emailField = (TextView) findViewById(R.id.loginEmailField);
		passwordField = (TextView) findViewById(R.id.loginPasswordField);
		
		Button btLogin = (Button) findViewById(R.id.loginLoginButton);
		btLogin.setOnClickListener(loginClick());
		
		Button btRegister = (Button) findViewById(R.id.loginRegisterButton);
		btRegister.setOnClickListener(registerClick());
		
		fieldsContainer = (LinearLayout) findViewById(R.id.loginFieldsContainer);
		loaderContainer = (FrameLayout) findViewById(R.id.loginLoaderContainer);

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			loginSuccess();
		}
	}

	private OnClickListener registerClick() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent registerAccountIntent = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(registerAccountIntent);
			}
		};
	}

	private OnClickListener loginClick() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loginWithBringme();
			}
		};
	}

	public void loginSuccess() {
		ParseUser user = ParseUser.getCurrentUser();
		if(user.has("facebookId")){
			FacebookImageLoader loader = new FacebookImageLoader();
			Bitmap profilePic;
			try {
				profilePic = loader.execute(user.getString("facebookId")).get();
				ByteBuffer buffer = ByteBuffer.allocate(profilePic.getRowBytes() * profilePic.getHeight());
				profilePic.copyPixelsToBuffer(buffer);
				byte[] profilePicBytes = buffer.array();
				ParseFile parsePic = new ParseFile(profilePicBytes);
				user.put("pic", parsePic);
				user.saveInBackground();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Intent menuIntent = new Intent(this, MainActivity.class);
		startActivity(menuIntent);
	}

	public void loginFail() {
		if (emailValue.isEmpty()) {
			
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
	 * BringMe database
	 * 
	 * @param view
	 */
	public void loginWithBringme() {
		emailValue = emailField.getText().toString();
		passwordValue = passwordField.getText().toString();
		
		showLoader();
		
		ParseUser.logInInBackground(emailValue, passwordValue, new LogInCallback() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				if(e == null){
					loginSuccess();
				}else{
					hideLoader();
					int errorCode = e.getCode();
					
					if(errorCode == ParseException.CONNECTION_FAILED) {
						Toast.makeText(getApplicationContext(), "Connection Failed!", Toast.LENGTH_SHORT).show();
					}
					else if(errorCode == ParseException.EMAIL_MISSING) {
						emailField.setError("Empty Field");
					}
					else if(errorCode == ParseException.PASSWORD_MISSING) {
						passwordField.setError("Empty Field");
					}
					else if(errorCode == ParseException.OBJECT_NOT_FOUND) {
						Toast.makeText(getApplicationContext(), "User doesn't exist!", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(getApplicationContext(), "Error Ocurred!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	/**
	 * Attempts to login trying to authenticate into the system through the
	 * Facebook authentication system
	 * 
	 * @param view
	 */
	public void loginWithFacebook(View view) {		
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			loginSuccess();
		}

		//Facebook app id for initialization
		ParseFacebookUtils.initialize(getString(R.string.app_id));
		
		ParseUser.logOut();
		
		Collection<String> facebookPermissions = Arrays.asList(
				Permissions.User.EMAIL, Permissions.User.PHOTOS,
			     Permissions.User.HOMETOWN,Permissions.User.LOCATION,
			     Permissions.User.BIRTHDAY);
		
		ParseFacebookUtils.logIn(facebookPermissions, this, new LogInCallback() {
			@Override
			public void done(final ParseUser user, ParseException e) {
				if (user == null) {
					hideLoader();
					switch(e.getCode()){
					case ParseException.EXCEEDED_QUOTA:
						Toast.makeText(getApplicationContext(), 
								"BOO to Parse Service!", Toast.LENGTH_SHORT).show();
					case ParseException.CONNECTION_FAILED:
						Toast.makeText(getApplicationContext(), 
								"Parse is down! :(", Toast.LENGTH_SHORT).show();
					case ParseException.INVALID_LINKED_SESSION:
						Toast.makeText(getApplicationContext(), 
								"Invalid Facebook Session!", Toast.LENGTH_SHORT).show();
					default:
						Toast.makeText(getApplicationContext(), 
								"Login Failed! Try again Later.", Toast.LENGTH_SHORT).show();
					}
					Log.i(Consts.TAG, "Oops, something wen't wrong!");
				} else if (user.isNew()) {
					final Session session = ParseFacebookUtils.getSession();
					
					Log.i(Consts.TAG, "Register and Login with facebook Success!");
					
					Request request = Request.newMeRequest(session,
							 new Request.GraphUserCallback() {

					        @Override
					        public void onCompleted(GraphUser FBUser, Response response) {
					            // If the response is successful
					            if (session == Session.getActiveSession()) {
					                if (FBUser != null) {
					                    user.put("facebookId", FBUser.getId());
					                    user.saveInBackground();
					                }
					            }
					            if (response.getError() != null) {
					                // TODO: Handle error
					            }
					        }
					    });
				    request.executeAsync();
				    
				    loginSuccess();
				}
				else {
					Log.i(Consts.TAG, "Login with facebook Success!");
					loginSuccess();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		hideLoader();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
		showLoader();
	}
	
	public void hideLoader(){
		loaderContainer.setVisibility(View.GONE);
		fieldsContainer.setVisibility(View.VISIBLE);
	}
	
	public void showLoader(){
		loaderContainer.setVisibility(View.VISIBLE);
		fieldsContainer.setVisibility(View.GONE);
	}

}