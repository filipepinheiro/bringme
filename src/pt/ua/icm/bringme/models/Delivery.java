package pt.ua.icm.bringme.models;

import java.io.Serializable;

import com.parse.ParseGeoPoint;

public class Delivery implements Serializable{
	private static final long serialVersionUID = -5653952413868651761L;

	public String id;
	
	public ParseGeoPoint origin;
	public String originAddress;
	public String detailedOrigin;
	
	public ParseGeoPoint destination;
	public String destinationAddress;
	public String detailedDestination;
	
	public String name, description, notes, courierId, requesterId;
	
	private boolean accepted, finished;

	public Delivery() {
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	private void setCourierId(String courierId){
		this.courierId = id;
	}
	
	public String getCourierId(){
		return courierId;
	}
	
	private void setRequestId(String requesterId){
		this.requesterId = requesterId;
	}
	
	private String getRequestId(){
		return requesterId;
	}

}
