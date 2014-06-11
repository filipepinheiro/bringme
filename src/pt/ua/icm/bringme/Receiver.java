package pt.ua.icm.bringme;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;

public class Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			String action = intent.getAction();
			String channel = intent.getExtras().getString("com.parse.Channel");
			JSONObject json = new JSONObject(intent.getExtras().getString(
					"com.parse.Data"));

			Log.d(Consts.TAG, "got action " + action + " on channel " + channel
					+ " with:");
			Iterator itr = json.keys();
			
			if(action.equals("pt.ua.icm.bringme.FINISHED_DELIVERY")){
				while (itr.hasNext()) {
					String key = (String) itr.next();
					if(key.equals("deliveryId")){
						Intent rate = new Intent(context, RateCourierActivity.class);
						rate.putExtra("deliveryId", json.getString(key));
						
						rate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.getApplicationContext().startActivity(rate);
					}
					Log.d(Consts.TAG, "..." + key + " => " + json.getString(key));
				}
			}
			
			if(action.equals("pt.ua.icm.bringme.DELIVERY_ACCEPTANCE")){
				while (itr.hasNext()) {
					String key = (String) itr.next();
					if(key.equals("deliveryId")){
						Intent deliveryAction = new Intent(context, DeliveryActionActivity.class);
						deliveryAction.putExtra("deliveryId", json.getString(key));
						
						deliveryAction.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.getApplicationContext().startActivity(deliveryAction);
					}
					Log.d(Consts.TAG, "..." + key + " => " + json.getString(key));
				}
			}
		} catch (JSONException e) {
			Log.d(Consts.TAG, "JSONException: " + e.getMessage());
		}
	}

}
