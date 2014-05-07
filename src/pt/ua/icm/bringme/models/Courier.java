package pt.ua.icm.bringme.models;

public class Courier extends User {

	private static final long serialVersionUID = 3433345250516086742L;
	private double rating = 0.0;
	
	public Courier(String firstName, String lastName, String phoneNumber, double rating) {
		super(firstName, lastName, phoneNumber);
		this.setRating(rating);
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

}
