package pt.ua.icm.bringme;

import java.util.List;

import pt.ua.icm.adapters.MyArrayAdapter;
import pt.ua.icm.bringme.models.Delivery;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class DeliveryStatusActivity extends ListActivity {

	private Delivery[] deliveries;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery_status);

		final ParseUser user = ParseUser.getCurrentUser();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Delivery");
		//query.include("requester");
		// query.whereEqualTo("requester", user.get("objectId"));
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					Log.i(Consts.TAG, "LIST = " + objects.size());
					dataAdapter(objects, user.getObjectId().toString());
				} else {
					Log.i(Consts.TAG, "Delivery List Empty! - " + e);
				}
			}
		});

	}

	private void dataAdapter(List<ParseObject> objects, String objectId) {

		deliveries = new Delivery[objects.size()];
		int counter = 0;

		ParseUser user = ParseUser.getCurrentUser();
		
		while (!objects.isEmpty()) {
			Delivery delivery = new Delivery();
			
			ParseObject requester = new ParseObject("Delivery");
			requester = objects.get(0).getParseObject("requester");
			String deliveryRequesterId = requester.getObjectId();
			
			
			
			boolean isFinished = (Boolean) objects.get(0).get("finished");
			
			
			Log.i(Consts.TAG, "-> " +user.getObjectId().toString());
			Log.i(Consts.TAG, "-> " +deliveryRequesterId.toString());

			
			if (user.getObjectId().toString().equals(deliveryRequesterId.toString()) &&
					!isFinished) {

				delivery.finished = isFinished;				
				delivery.origin = new LatLng(objects.get(0).getParseGeoPoint("origin").getLatitude(),objects.get(0).getParseGeoPoint("origin").getLongitude());
				delivery.destination = new LatLng(Double.parseDouble(objects
						.get(0).get("destinationLat").toString()),
						Double.parseDouble(objects.get(0).get("destinationLng")
								.toString()));

				delivery.accepted = (Boolean) objects.get(0).get("accepted");
				delivery.courierId = objects.get(0).get("courier").toString();
				delivery.detailedDestination = objects.get(0)
						.get("detailedDestination").toString();
				delivery.detailedOrigin = objects.get(0).get("detailedOrigin")
						.toString();
				delivery.description = objects.get(0).get("packageDescription")
						.toString();
				delivery.name = objects.get(0).get("packageName").toString();
				delivery.notes = objects.get(0).get("packageNotes").toString();
				
				//ir buscar fb id
				String fbId= objects.get(0).get("facebookIdCourier").toString();
				
				//------
				if(fbId != null && fbId != "")
					delivery.facebookId = fbId;
				else
					delivery.facebookId = null;
					
					
				deliveries[counter] = delivery;
				counter++;
			}
			objects.remove(0);
		}

		Delivery[] compress = new Delivery[counter];
		
		for(int i=0; i<counter;i++){
			compress[i] = deliveries[i];
		}
		
		ListView listview = (ListView) findViewById(android.R.id.list);
		MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(),
				compress, new String[compress.length]);

		listview.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delivery_status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Intent detailedInt = new Intent(getApplicationContext(), DeliveryDetailsActivity.class);
		detailedInt.putExtra("delivery", deliveries[position]);
		
		startActivityForResult(detailedInt, position);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_delivery_status,
					container, false);
			return rootView;
		}

	}

}
