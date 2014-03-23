package pt.ua.icm.bringme.datastorage;

import pt.ua.icm.bringme.models.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "bringme";
    private static final String USER_TABLE_NAME = "users";
    private static final String USER_TABLE_CREATE =
    		"CREATE TABLE " + USER_TABLE_NAME + " (" +
    				"id INT PRIMARY KEY, " +
    				"firstName TEXT, " +
    				"lastName TEXT, " +
    				"email TEXT, " +
    				"password TEXT, " +
    				"phoneNumber TEXT," +
    				"rate REAL" + ");";

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
	
	public void insertUser(User user){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("firstName", user.getFirstName());
		values.put("lastName", user.getLastName());
		values.put("email", user.getEmail());
		values.put("password", user.getPassword());
		values.put("phoneNumber", user.getPhoneNumber());
		values.put("rate", 0); //Rate initial Value
		db.insert("users", null, values);
		db.close();
	}

}
