package pt.ua.icm.bringme.models;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = -8121361232996384115L;
	private String firstName,lastName,email,password;
	private double contact;
	private float rate;
	private int id;

	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param contact
	 */
	public User(String firstName, String lastName, String email,
			String password, double contact) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.contact = contact;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the contact
	 */
	public double getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(double contact) {
		this.contact = contact;
	}

	/**
	 * @return the rate
	 */
	public float getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(float rate) {
		this.rate = rate;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return the User full name
	 */
	public String getFullName(){
		return getFirstName().concat(" ").concat(getLastName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", contact="
				+ contact + ", rate=" + rate + "]";
	}
	
	

}
