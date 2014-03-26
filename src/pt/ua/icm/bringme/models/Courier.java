package pt.ua.icm.bringme.models;

import java.math.BigInteger;

public class Courier extends User {
	private static final long serialVersionUID = 3433345250516086742L;
	private double rate;

	public Courier(String firstName, String lastName, String email,
			String phoneNumber, double rate) {
		super(firstName, lastName, email, phoneNumber);
		this.rate = rate;
	}

	public Courier(String firstName, String lastName, String email,
			BigInteger phoneNumber, double rate) {
		this(firstName, lastName, email, phoneNumber.toString(), rate);
	}

	public Courier(String firstName, String lastName, String email,
			int phoneNumber, double rate) {
		this(firstName, lastName, email, String.valueOf(phoneNumber), rate);
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
