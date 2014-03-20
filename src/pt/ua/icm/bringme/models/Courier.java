package pt.ua.icm.bringme.models;

public class Courier extends User {
	private static final long serialVersionUID = 3433345250516086742L;

	public Courier(String firstName, String lastName, String email,
			String password, double contact) {
		super(firstName, lastName, email, password, contact);
	}

}
