package pt.ua.icm.bringme;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import android.app.Activity;
import pt.ua.icm.bringme.models.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FinishRequestDeliveryActivity extends Activity {
	
	Delivery delivery;
	TextView packageName, packageDescription, packageNotes, packageDetailedOrigin,
		packageDetailedDestination;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish_request_delivery);
		
		Intent intentReceiver = getIntent();
		Bundle deliveryBundle = intentReceiver.getBundleExtra("delivery");
		delivery = (Delivery) deliveryBundle.getSerializable("delivery");
		
		packageName = (TextView) findViewById(R.id.finish_request_package_name);
		packageDescription = (TextView) findViewById(R.id.finish_request_package_description);
		packageNotes = (TextView) findViewById(R.id.finish_request_special_notes);
		packageDetailedOrigin = (TextView) findViewById(R.id.finish_request_detailed_origin);
		packageDetailedDestination = (TextView) findViewById(R.id.finish_request_detailed_destination);
		
		fillFields();
		
		Button submitButton = (Button) findViewById(R.id.finish_request_button);
		submitButton.setOnClickListener(submitRequestDelivery());
	}

	private OnClickListener submitRequestDelivery() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendRequestNotification(delivery.courierId);
			}
		};
	}
	
	private void sendRequestNotification(final String courierId) {
		//Install Notifications
		ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public void done(ParseException e) {
				if(e == null){
					ParseQuery userQuery = ParseUser.getQuery().whereEqualTo("objectId", courierId);
					
					ParseQuery pushQuery = ParseInstallation.getQuery();
					pushQuery.whereMatchesQuery("user", userQuery);
					
					ParsePush push = new ParsePush();
					push.setQuery(pushQuery);
					push.setMessage("Hey! Can you BringMe?");
					push.sendInBackground(new SendCallback() {
						
						@Override
						public void done(ParseException e) {
							if(e == null){
								Log.i(Consts.TAG, "Notification send with success!");
								Toast.makeText(getApplicationContext(), "Notification sent!", Toast.LENGTH_SHORT).show();
							}
							else{
								Log.e(Consts.TAG, "Notification failed! :(");
							}
						}
					});
				}else{
					Log.e(Consts.TAG, "Failed to save Current Installation!");					
				}
			}
		});
	}

	private void fillFields() {
		packageName.setText(delivery.name);
		packageDescription.setText(delivery.description);
		packageNotes.setText(delivery.notes);
		packageDetailedOrigin.setText(delivery.detailedOrigin);
		packageDetailedDestination.setText(delivery.detailedDestination);
	}
}
