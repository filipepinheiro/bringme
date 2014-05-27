package pt.ua.icm.bringme.models;

import com.facebook.Session;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class User extends ParseUser{
	
	private String objectId;
	private String firstName;
	private String lastName;
	private String phoneNumber;

	
	public User(String objectId) {
		this.objectId = objectId;
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("User");
		try {
			ParseObject user = query.get(objectId);
			firstName = user.getString("firstName");
			lastName = user.getString("lastName");
			phoneNumber = user.getString("phoneNumber");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
