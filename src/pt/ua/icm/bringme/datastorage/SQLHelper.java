package pt.ua.icm.bringme.datastorage;

import pt.ua.icm.bringme.models.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "bringme";
	private static final String USER_TABLE_NAME = "users";
	public static final int INVALID_USER_AUTHENTICATION = -1;
	private static final String USER_TABLE_CREATE = "CREATE TABLE "
			+ USER_TABLE_NAME + " (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "firstName TEXT, " + "lastName TEXT, " + "email TEXT, "
			+ "password TEXT, " + "phoneNumber TEXT," + "rate REAL" + ");";

	public SQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USER_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	public void insertUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("firstName", user.getFirstName());
		values.put("lastName", user.getLastName());
		values.put("email", user.getEmail());
		values.put("password", user.getPassword());
		values.put("phoneNumber", user.getPhoneNumber());
		values.put("rate", 0); // Rate initial Value
		db.insert("users", null, values);
		db.close();
	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @return database user ID or -1 if the user doesn't exists
	 */
	public int existsUser(String email, String password) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cs = db.rawQuery("SELECT count(*) FROM users WHERE email='"
				+ email + "' AND password='" + password + "';", null);
		cs.moveToFirst();
		int count = cs.getInt(0);
		Log.i("BringMeLogin", count + " account matched!");
		if (count == 1) {
			cs = db.rawQuery("SELECT id FROM users WHERE email='" + email
					+ "' AND password='" + password + "';", null);
			cs.moveToFirst();
			return cs.getInt(0);
		}
		cs.close();
		db.close();
		return -1;
	}

	/**
	 * 
	 * @param id
	 * @return user with the specified id
	 */
	public User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cs = db.rawQuery(
				"SELECT firstName, lastName, email, phoneNumber FROM users WHERE id="
						+ id + ";", null);
		cs.moveToFirst();
		User user = new User(cs.getString(0), cs.getString(1), cs.getString(2),
				cs.getString(3));
		return user;
	}

}
