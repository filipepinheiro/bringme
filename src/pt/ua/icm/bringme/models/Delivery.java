package pt.ua.icm.bringme.models;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseGeoPoint;

public class Delivery implements Parcelable, Serializable{
	private static final long serialVersionUID = -5653952413868651761L;

	public String id;
	
	public LatLng origin;
	public String originAddress;
	public String detailedOrigin;
	
	public LatLng destination;
	public String destinationAddress;
	public String detailedDestination;
	
	public String name, description, notes, courierId, requesterId;
	
	private boolean accepted, finished;

	public Delivery() {
	}

	public Delivery(Parcel source) {
		origin = source.readParcelable(LatLng.class.getClassLoader());
		destination = source.readParcelable(LatLng.class.getClassLoader());
		originAddress = source.readString();
		detailedOrigin = source.readString();
		destinationAddress = source.readString();
		detailedDestination = source.readString();
		name = source.readString();
		description = source.readString();
		notes = source.readString();
		courierId = source.readString();
		requesterId = source.readString();
		accepted = source.readByte() != 0;
		finished = source.readByte() != 0;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(origin, flags);
		dest.writeParcelable(destination, flags);
		dest.writeString(originAddress);
		dest.writeString(detailedOrigin);
		dest.writeString(destinationAddress);
		dest.writeString(detailedDestination);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(notes);
		dest.writeString(courierId);
		dest.writeString(requesterId);
		dest.writeByte((byte) (accepted ? 1 : 0));
		dest.writeByte((byte) (finished ? 1 : 0));
	}
	
	public static final Parcelable.Creator<Delivery> CREATOR = new Parcelable.Creator<Delivery>() {
		 
        @Override
        public Delivery createFromParcel(Parcel source) {
            return new Delivery(source); // RECREATE VENUE GIVEN SOURCE
        }
 
        @Override
        public Delivery[] newArray(int size) {
            return new Delivery[size]; // CREATING AN ARRAY OF VENUES
        }
 
    };

}
