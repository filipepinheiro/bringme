package pt.ua.icm.bringme;

import pt.ua.icm.bringme.helpers.AddressHelper;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class DeliveryActionActivity extends ActionBarActivity {
	
	private TextView actionDeliveryPackageName, actionDeliveryRequesterName, 
	actionDeliveryDetails, actionDeliveryDetailedOrigin, 
	actionDeliveryDetailedDestination, actionDeliveryOriginAddress,
	actionDeliveryDestinationAddress; 

	private Button acceptButton, rejectButton, finishButton;
	
	private ParseObject delivery = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery_action);
		
		Intent received = getIntent();
		
		actionDeliveryPackageName = (TextView) findViewById(R.id.actionDeliveryPackageName);
		actionDeliveryRequesterName = (TextView) findViewById(R.id.actionDeliveryRequesterName); 
		actionDeliveryDetails = (TextView) findViewById(R.id.actionDeliveryDetails);
		actionDeliveryDetailedOrigin = (TextView) findViewById(R.id.actionDeliveryDetailedOrigin);
		actionDeliveryDetailedDestination = (TextView) findViewById(R.id.actionDeliveryDetailedDestination);
		actionDeliveryOriginAddress = (TextView) findViewById(R.id.actionDeliveryOriginAddress);
		actionDeliveryDestinationAddress = (TextView) findViewById(R.id.actionDeliveryDestinationAddress);
		
		acceptButton = (Button) findViewById(R.id.actionDeliveryAcceptButton);
		rejectButton = (Button) findViewById(R.id.actionDeliveryRejectButton);
		finishButton = (Button) findViewById(R.id.actionDeliveryFinishButton);
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Delivery");
		query.getInBackground(received.getStringExtra("delivery"), new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject deliveryResult, ParseException e) {
				if(e == null){
					delivery = deliveryResult;
					
					ParseUser requester = delivery.getParseUser("requester");
					String requesterName = getString(R.string.none);
					try {
						requester.fetchIfNeeded();
						String requesterFirstName =	requester.getString("firstName");
						String requesterLastName = requester.getString("lastName");
						requesterName = requesterFirstName + " " + requesterLastName;
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					actionDeliveryRequesterName.setText(requesterName);
					actionDeliveryPackageName.setText(delivery.getString("packageName"));
					actionDeliveryDetails.setText(delivery.getString("packageNotes"));
					ParseGeoPoint origin = delivery.getParseGeoPoint("origin");
					String originAddress = AddressHelper.getLocationAddress(
							origin.getLatitude(), 
							origin.getLongitude(), 
							getApplicationContext());
					String destinationAddress = AddressHelper.getLocationAddress(
							delivery.getDouble("destinationLat"), 
							delivery.getDouble("destinationLng"), 
							getApplicationContext());
					actionDeliveryOriginAddress.setText(originAddress);
					actionDeliveryDetailedOrigin.setText(delivery.getString("detailedOrigin"));
					actionDeliveryDestinationAddress.setText(destinationAddress);
					actionDeliveryDetailedDestination.setText(delivery.getString("detailedDestination"));
					
					acceptButton.setOnClickListener(acceptDelivery());
					rejectButton.setOnClickListener(rejectDelivery());
				}
			}
		});
	}

	protected OnClickListener rejectDelivery() {
		return new OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				Log.d(Consts.TAG, "Clicked Reject Button");
				
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle("Reject Delivery Request");
				builder.setMessage("Confirm that you will NOT deliver this item.");
				builder.setNegativeButton(R.string.negative, null);
				builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(dialog.BUTTON_POSITIVE == which){
							delivery.put("accepted", false);
							delivery.saveInBackground(new SaveCallback() {
								
								@Override
								public void done(ParseException e) {
									if(e == null){
										startActivity(new Intent(v.getContext(), MainActivity.class));
									}
								}
							});
							
						}
					}
					
				});

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		};
	}

	protected OnClickListener acceptDelivery() {
		return new OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				Log.d(Consts.TAG, "Clicked Accept Button");
				
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle("Accept Delivery Request");
				builder.setMessage("Confirm that you will deliver this item.");
				builder.setNegativeButton(R.string.negative, null);
				builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(AlertDialog.BUTTON_POSITIVE == which){
							delivery.put("accepted", true);
							delivery.saveInBackground(new SaveCallback() {
								
								@Override
								public void done(ParseException e) {
									if(e == null){
										startActivity(new Intent(v.getContext(), MainActivity.class));
									}
								}
							});
						}
					}
					
				});
				
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delivery_action, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
