package pt.ua.icm.bringme;

import java.util.Date;

public class User {
	
	private String name,email,encryptedPassword;
	private int rate;
	private Date birthdate;

	/**
	 * @param name
	 * @param email
	 * @param encryptedPassword
	 * @param birthdate
	 */
	public User(String name, String email, String password, Date birthdate) {
		this.name = name;
		this.email = email;
		this.encryptedPassword = Digest.md5(password);
		this.birthdate = birthdate;
	}

	/**
	 * @param name
	 * @param email
	 * @param password
	 * @param rate
	 * @param birthdate
	 */
	public User(String name, String email, String password, int rate,
			Date birthdate) {
		this.name = name;
		this.email = email;
		this.encryptedPassword = Digest.md5(password);
		this.rate = rate;
		this.birthdate = birthdate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	/**
	 * @param password the password to set
	 */
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	/**
	 * @return the rate
	 */
	public int getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}

	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

}
