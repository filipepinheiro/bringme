package pt.ua.icm.bringme.helpers;

import java.io.IOException;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseGeoPoint;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

public class AddressHelper {
	
	public static String getPrettyAddress(Address address){
		StringBuilder prettyAddress = new StringBuilder("");
		if(address != null){
			int maxAddressLine = address.getMaxAddressLineIndex(); 
			
			for(int i = 0;i<=maxAddressLine;i++){
				prettyAddress.append(address.getAddressLine(i));
				if(i != maxAddressLine){
					prettyAddress.append(", ");
				}
			}
		}
		
		return prettyAddress.toString();
	}
	
	public static String getLocationAddress(double latitude, double longitude, Context context){
		Geocoder coder = new Geocoder(context);
		Address address = null;
		try {
			address = coder.getFromLocation(latitude, longitude, 1).get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPrettyAddress(address);
	}
	
	public static LatLng parseGeoPointToLatLng(ParseGeoPoint geopoint){
		LatLng point = new LatLng(geopoint.getLatitude(), geopoint.getLongitude());
		return point;
	}
	
	public static String printParseGeoPoint(ParseGeoPoint location){
		return  "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
	}
	
	public static String printLatLng(LatLng location){
		return  "(" + location.latitude + ", " + location.longitude + ")";
	}
	
	public static ParseGeoPoint latLngToParseGeoPoint(LatLng latlng){
		ParseGeoPoint point = new ParseGeoPoint(latlng.latitude, latlng.longitude);
		return point;
	}
}
