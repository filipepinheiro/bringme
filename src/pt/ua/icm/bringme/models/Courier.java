package pt.ua.icm.bringme.models;

import java.util.LinkedList;
import java.util.List;

import com.parse.ParseGeoPoint;

public class Courier extends User{
	
	public ParseGeoPoint lastKnownLocation;
	public double rating;
	public List<Delivery> deliveryList = new LinkedList<Delivery>();

	public Courier(String objectId) {
		super(objectId);
	}

	
}
