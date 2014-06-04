package pt.ua.icm.bringme.models;

import java.io.Serializable;

import pt.ua.icm.bringme.Consts;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseUser implements Serializable{
	
	private static final long serialVersionUID = -7949525893899555714L;
	
	private String objectId, firstName, lastName, phoneNumber;
	private ParseGeoPoint lastLocation;
	private int requests = 0, deliveries = 0;
	private double rating = 0.0;
	
	public User(){}

	public User(String objectId) {
		this.objectId = objectId;
		Log.d(Consts.TAG, "New user based on objectId: " + objectId);
		
		ParseUser.getQuery().getInBackground(objectId, new GetCallback<ParseUser>() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				if(e == null){
					firstName = user.getString("firstName");
					lastName = user.getString("lastName");
					phoneNumber = user.getString("phoneNumber");
					requests = user.getInt("requests");
					deliveries = user.getInt("deliveries");
					rating = user.getDouble("rating");
				}
			}
		});
	}

	public String getObjectId() {
		return objectId;
	}

	public String getFirstName() {
		return firstName;
	}

	protected void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	protected void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public int getRequests() {
		return requests;
	}

	public ParseGeoPoint getLastLocation() {
		return lastLocation;
	}

	private void setLastLocation(ParseGeoPoint lastLocation) {
		this.lastLocation = lastLocation;
	}
	
	public LatLng getLastLocationLatLng(){
		return new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
	}

	public int getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(int deliveries) {
		this.deliveries = deliveries;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
