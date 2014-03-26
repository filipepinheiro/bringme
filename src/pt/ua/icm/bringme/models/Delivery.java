package pt.ua.icm.bringme.models;

import com.google.android.gms.maps.model.LatLng;

public class Delivery {

	private LatLng originCoordinates, destinationCoordinates;
	private String originAddress, destinationAddress;
	private Order order;
	private int requestorId;
	private int courierId;
	private boolean finished = false, accepted = false;
	
	public Delivery(LatLng originCoordinates, LatLng destinationCoordinates,
			String originAddress, String destinationAddress, Order order,
			int requestorId, int courierId) {
		super();
		this.originCoordinates = originCoordinates;
		this.destinationCoordinates = destinationCoordinates;
		this.originAddress = originAddress;
		this.destinationAddress = destinationAddress;
		this.order = order;
		this.requestorId = requestorId;
		this.courierId = courierId;
	}

	public Delivery(LatLng originCoordinates, LatLng destinationCoordinates,
			String originAddress, String destinationAddress, int requestorId,
			int courierId) {
		super();
		this.originCoordinates = originCoordinates;
		this.destinationCoordinates = destinationCoordinates;
		this.originAddress = originAddress;
		this.destinationAddress = destinationAddress;
		this.requestorId = requestorId;
		this.courierId = courierId;
	}



	public String getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public LatLng getOriginCoordinates() {
		return originCoordinates;
	}

	public void setOriginCoordinates(LatLng originCoordinates) {
		this.originCoordinates = originCoordinates;
	}

	public LatLng getDestinationCoordinates() {
		return destinationCoordinates;
	}

	public void setDestinationCoordinates(LatLng destinationCoordinates) {
		this.destinationCoordinates = destinationCoordinates;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * @param finished
	 *            the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * 
	 * @return the Requestor Id
	 */
	public int getRequestorId() {
		return requestorId;
	}

	/**
	 * 
	 * @return the Courier Id
	 */
	public int getCourierId() {
		return courierId;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted
	 *            the accepted to set
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

}
