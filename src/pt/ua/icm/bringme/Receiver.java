package pt.ua.icm.bringme;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Receiver extends BroadcastReceiver {

	public Receiver() {}

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
		      String action = intent.getAction();
		      String channel = intent.getExtras().getString("com.parse.Channel");
		      JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
		 
		      Log.d(Consts.TAG, "got action " + action + " on channel " + channel + " with:");
		      Iterator itr = json.keys();
		      while (itr.hasNext()) {
		        String key = (String) itr.next();
		        Log.d(Consts.TAG, "..." + key + " => " + json.getString(key));
		      }
		    } catch (JSONException e) {
		      Log.d(Consts.TAG, "JSONException: " + e.getMessage());
		    }
	}

}
