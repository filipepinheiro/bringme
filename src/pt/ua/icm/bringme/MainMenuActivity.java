package pt.ua.icm.bringme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainMenuActivity extends Activity implements 
	LocationListener,
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener{
	
	private String userID;
	
	private SharedPreferences preferences;
	private ToggleButton toggleCourier;
	private ParseQuery<ParseObject> userQuery;
	
	// A request to connect to Location Services
    private LocationRequest mLocationRequest;

    // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient;
    
    private Location currentLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		// Initialize parse connection
		Parse.initialize(this, "BAK4DQx9H7pvSsQJTRKKH4MF0souYuQ6E1l5AMa6", "qnftcNXKP2BevHACysHKbaK3lhGjQPDrINBT6cRI");
		
		preferences = getSharedPreferences("pt.ua.icm.bringme", Context.MODE_PRIVATE);
		userID = preferences.getString("userID", null);
		
		//toggleCourier = (ToggleButton) findViewById(R.id.toggleCourier);
		
		// Create a new global location parameters object
        mLocationRequest = LocationRequest.create();
        
        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(this, this, this);
	}
	
	/*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    public void onStop() {

        // If the client is connected
        if (mLocationClient.isConnected()) {
            stopPeriodicUpdates();
        }

        // After disconnect() is called, the client is considered "dead".
        mLocationClient.disconnect();

        super.onStop();
    }
    
    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {

        super.onStart();

        /*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
        mLocationClient.connect();

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Open the requestDeliveryActivity
	 * 
	 * @param view
	 */
	public void requestDelivery(View view) {
		Intent requestDeliveryIntent = new Intent(this,
				RequestDeliveryActivity.class);
		startActivity(requestDeliveryIntent);
	}

	/**
	 * 
	 * @param view
	 */
	public void deliveryStatus(View view) {
		Intent deliveryStatusIntent = new Intent(this,
				DeliveryStatusActivity.class);
		startActivity(deliveryStatusIntent);
	}

	/**
	 * 
	 * @param view
	 */
	public void myRequestsList(View view) {
		Intent myRequestListIntent = new Intent(this, RequestListActivity.class);
		startActivity(myRequestListIntent);
	}

	/**
	 * 
	 * @param view
	 */
	public void myHistorical(View view) {
		Intent historicalIntent = new Intent(this, HistoricalActivity.class);
		startActivity(historicalIntent);
	}

	/**
	 * 
	 * @param view
	 */
	public void profile(View view) {
		Intent profileIntent = new Intent(this, ProfileActivity.class);
		startActivity(profileIntent);
	}
	
	/**
	 * 
	 * @param view
	 */
	public void changeClientMode(View view) {
		
		userQuery = new ParseQuery<ParseObject>("User");
		 
		if(toggleCourier.isChecked()){
			try {				
				ParseObject user = userQuery.get(userID);

				currentLocation = mLocationClient.getLastLocation();
				if(currentLocation != null){
					user.put("isCourier", true);
					user.put("currentLocation", new ParseGeoPoint(currentLocation.getLatitude(),currentLocation.getLongitude()));	
				}
				user.saveInBackground();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				Log.i(Consts.TAG, "userID: " + userID);
				
				ParseObject user = userQuery.get(userID);
				user.put("isCourier", false);
				user.saveInBackground();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            return false;
        }
    }
    
    /**
     * Invoked by the "Start Updates" button
     * Sends a request to start location updates
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    public void startUpdates(View v) {
        if (servicesConnected()) {
            startPeriodicUpdates();
        }
    }
    
    /**
     * Invoked by the "Stop Updates" button
     * Sends a request to remove location updates
     * request them.
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    public void stopUpdates(View v) {
        if (servicesConnected()) {
            stopPeriodicUpdates();
        }
    }

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		startPeriodicUpdates();
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
	}
	
	/**
     * Report location updates to the UI.
     *
     * @param location The updated location.
     */
    @Override
    public void onLocationChanged(Location location) {
        // In the UI, set the latitude and longitude to the value received
        Toast.makeText(this, LocationUtils.getLatLng(this, location), Toast.LENGTH_SHORT).show();
    }
    
    /**
     * In response to a request to start updates, send a request
     * to Location Services
     */
    private void startPeriodicUpdates() {

        mLocationClient.requestLocationUpdates(mLocationRequest, this);
    }

    /**
     * In response to a request to stop updates, send a request to
     * Location Services
     */
    private void stopPeriodicUpdates() {
        mLocationClient.removeLocationUpdates(this);
    }
}
