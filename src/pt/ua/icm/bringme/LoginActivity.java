package pt.ua.icm.bringme;

<<<<<<< HEAD
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.*;
=======
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f

import android.content.Intent;
import android.graphics.Bitmap;
<<<<<<< HEAD
=======
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
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

<<<<<<< HEAD
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
=======
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.SaveCallback;
import com.parse.ParseFacebookUtils.Permissions;
<<<<<<< HEAD
import com.parse.ParseFile;
=======
import com.parse.ParseQuery;
>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
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
<<<<<<< HEAD
		
		Button btLogin = (Button) findViewById(R.id.loginLoginButton);
		btLogin.setOnClickListener(loginClick());
		
		Button btRegister = (Button) findViewById(R.id.loginRegisterButton);
		btRegister.setOnClickListener(registerClick());
		
		fieldsContainer = (LinearLayout) findViewById(R.id.loginFieldsContainer);
		loaderContainer = (FrameLayout) findViewById(R.id.loginLoaderContainer);
=======

		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ",
				"91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");

>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f

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
<<<<<<< HEAD
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
=======
	public void loginWithBringme(View view) {
		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ",
				"91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");
		emailValue = emailField.getText().toString();
		passwordValue = passwordField.getText().toString();

		ParseUser.logInInBackground(emailValue, passwordValue,
				new LogInCallback() {

					@Override
					public void done(ParseUser user, ParseException e) {
						if (e == null) {
							loginSuccess();
						} else {
							int errorCode = e.getCode();

							if (errorCode == ParseException.CONNECTION_FAILED) {
								Toast.makeText(getApplicationContext(),
										"Connection Failed!",
										Toast.LENGTH_SHORT).show();
							} else if (errorCode == ParseException.EMAIL_MISSING)
								emailField.setError("Empty Field");
							else if (errorCode == ParseException.PASSWORD_MISSING) {
								passwordField.setError("Empty Field");
							} else if (errorCode == ParseException.OBJECT_NOT_FOUND) {
								Toast.makeText(getApplicationContext(),
										"User doesn't exist!",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getApplicationContext(),
										"Error Ocurred!", Toast.LENGTH_SHORT)
										.show();
							}
						}
>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
					}
				});
	}

<<<<<<< HEAD
=======
	public void loginSuccess() {
		Intent menuIntent = new Intent(this, MenuActivity.class);
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

>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
	/**
	 * Attempts to login trying to authenticate into the system through the
	 * Facebook authentication system
	 * 
	 * @param view
	 */
<<<<<<< HEAD
	public void loginWithFacebook(View view) {		
=======
	public void loginWithFacebook(View view) {
		Parse.initialize(this, "99yFCBTgfHtYIhUVJrjmmu0BadhZizdif5tWZCaZ",
				"91wrcZYRC5rYdyKxSltowkKtI8nrpzCFMbwKYvUP");

>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();

		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			loginSuccess();
		}

		// Facebook app id for initialization
		ParseFacebookUtils.initialize(getString(R.string.app_id));
<<<<<<< HEAD
		
		ParseUser.logOut();
		
		Collection<String> facebookPermissions = Arrays.asList(
				Permissions.User.EMAIL, Permissions.User.PHOTOS,
			     Permissions.User.HOMETOWN,Permissions.User.LOCATION,
			     Permissions.User.BIRTHDAY);
		
		ParseFacebookUtils.logIn(facebookPermissions, this, new LogInCallback() {
			@Override
			public void done(final ParseUser user, ParseException e) {
=======

		// List<String> facebookPermissions = Arrays.asList("public_profile",
		// "user_friends");
		ParseUser.logOut();
		// ParseFacebookUtils.logIn(this, new LogInCallback() {
		ParseFacebookUtils.logIn(Arrays.asList(Permissions.User.EMAIL,
				Permissions.User.PHOTOS, Permissions.User.HOMETOWN,
				Permissions.User.LOCATION), this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
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
<<<<<<< HEAD
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
=======
					Log.i(Consts.TAG,
							"Register and Login with facebook Success!");
					loginFacebookSuccess();
				} else {
>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
					Log.i(Consts.TAG, "Login with facebook Success!");
					loginFacebookSuccess();
				}
			}
		});
	}

<<<<<<< HEAD
=======
	private void loginFacebookSuccess() {
		Log.d("PARSE", "PARSE");
		
		ParseFacebookUtils.initialize(getString(R.string.app_id));

		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {

					String email, firstName, lastName;

					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {


							
							if (user.getProperty("email") != null) {
								this.email = user.getProperty("email")
										.toString();
							}
							if (user.getFirstName() != null) {
								this.firstName = user.getFirstName().toString();
							}
							if (user.getLastName() != null) {
								this.lastName = user.getLastName().toString();
							}

							
							ParseUser.getCurrentUser().put("email", this.email);
							ParseUser.getCurrentUser().put("firstName", this.firstName);
							ParseUser.getCurrentUser().put("lastName", this.lastName);

							
							ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
								
								@Override
								public void done(ParseException e) {
									// TODO Auto-generated method stub
									if(e == null){
										Log.e("EDIT_DATA", "Ok.");
									}
									else{
										Log.e("EDIT_DATA", "NOT Ok.");
									}
								}
							});
	                        
	                        

						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.e("Facebook parsing data",
										"The facebook session was invalidated.");
								// onLogoutButtonClicked();
							} else {
								Log.e("Facebook parsing data",
										"Some other error: "
												+ response.getError()
														.getErrorMessage());
							}
						}
					}
				});
		request.executeAsync();

		// init menu activity
		Intent menuIntent = new Intent(this, MenuActivity.class);
		startActivity(menuIntent);
	}

		/**
	 * Changes to the register account activity
	 * 
	 * @param view
	 */
	public void registerAccount(View view) {
		Intent registerAccountIntent = new Intent(this, RegisterActivity.class);
		startActivity(registerAccountIntent);
	}

>>>>>>> 873d7131cf19353f6e7c8298cd2e6bbcc1bd693f
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