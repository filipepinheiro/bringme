package pt.ua.icm.bringme.datastorage;

import java.util.LinkedList;
import java.util.List;

import pt.ua.icm.bringme.models.Delivery;
import pt.ua.icm.bringme.models.Order;

import com.google.android.gms.maps.model.LatLng;

//TODO: Remove and swap it with the cloud connection
public final class StaticDatabase {
	public static List<Delivery> deliveryRequestList = new LinkedList<Delivery>();
	public static List<Delivery> finishedRequests = new LinkedList<Delivery>();
	
	public static LatLng originCoordinates, destinationCoordinates;
	public static String originAddress, destinationAddress;
	
	public static Order currentOrder = null;
	
	public static Order getCurrentOrder() {
		return currentOrder;
	}

	public static void setCurrentOrder(Order currentOrder) {
		StaticDatabase.currentOrder = currentOrder;
	}

	public static String getOriginAddress() {
		return originAddress;
	}

	public static void setOriginAddress(String originAddress) {
		StaticDatabase.originAddress = originAddress;
	}

	public static String getDestinationAddress() {
		return destinationAddress;
	}

	public static void setDestinationAddress(String destinationAddress) {
		StaticDatabase.destinationAddress = destinationAddress;
	}

	public static LatLng getDestinationCoordinates() {
		return destinationCoordinates;
	}

	public static void setDestinationCoordinates(LatLng destinationCoordinates) {
		StaticDatabase.destinationCoordinates = destinationCoordinates;
	}

	public static LatLng getOriginCoordinates() {
		return originCoordinates;
	}

	public static void setOriginCoordinates(LatLng originCoordinates) {
		StaticDatabase.originCoordinates = originCoordinates;
	}

	public static List<Delivery> getDeliveryRequestList() {
		return deliveryRequestList;
	}

	public static void setDeliveryRequestList(List<Delivery> deliveryRequestList) {
		StaticDatabase.deliveryRequestList = deliveryRequestList;
	}

	public static List<Delivery> getFinishedRequests() {
		return finishedRequests;
	}

	public static void setFinishedRequests(List<Delivery> finishedRequests) {
		StaticDatabase.finishedRequests = finishedRequests;
	}
	
}
