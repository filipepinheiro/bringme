package pt.ua.icm.bringme.models;

public class Order {
	private int _id;
	private String tag, description, specialNotes;

	public Order(String tag, String description, String specialNotes) {
		this.tag = tag;
		this.description = description;
		this.specialNotes = specialNotes;
	}

	public Order(String tag, String description) {
		this.tag = tag;
		this.description = description;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpecialNotes() {
		return specialNotes;
	}

	public void setSpecialNotes(String specialNotes) {
		this.specialNotes = specialNotes;
	}

	public int get_id() {
		return _id;
	}
}
