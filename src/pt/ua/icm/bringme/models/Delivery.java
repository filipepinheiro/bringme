package pt.ua.icm.bringme.models;

import com.parse.ParseGeoPoint;

public class Delivery {
	
	public String id;
	
	public ParseGeoPoint origin;
	public String detailedOrigin;
	
	public ParseGeoPoint destination;
	public String detailedDestination;
	
	public String name, description, notes;
	
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

}
