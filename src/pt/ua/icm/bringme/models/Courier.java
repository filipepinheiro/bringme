package pt.ua.icm.bringme.models;

import java.math.BigInteger;

public class Courier extends User {
	private static final long serialVersionUID = 3433345250516086742L;
	private float rate;

	public Courier(String firstName, String lastName, String email, String phoneNumber) {
		super(firstName, lastName, email, phoneNumber);
	}
	
	public Courier(String firstName, String lastName, String email, BigInteger phoneNumber) {
		this(firstName,lastName,email,phoneNumber.toString());
	}
	
	public Courier(String firstName, String lastName, String email, int phoneNumber) {
		this(firstName,lastName,email,String.valueOf(phoneNumber));
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

}
