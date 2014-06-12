package pt.ua.icm.bringme;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class HistoryActivity extends ActionBarActivity{
	private ListView list;
	private LinearLayout loader;
	private ParseUser user;
	private HistoryAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		list = (ListView) findViewById(R.id.historyListView);
		loader = (LinearLayout) findViewById(R.id.historyLoader);

		showLoader();
		
		user = ParseUser.getCurrentUser();
		
		Log.i(Consts.TAG, "Query started!");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Delivery");
		query.whereEqualTo("requester", user);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(e == null){
					Log.i(Consts.TAG, "Query finished!");
					LinkedList<ParseObject> deliveryList = new LinkedList<ParseObject>();
					
					if(objects != null){
						Log.d(Consts.TAG, "Found " + objects.size() + " deliveries!");
						for(ParseObject delivery : objects){
							try {
								delivery.fetchIfNeeded();
								deliveryList.add(delivery);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}else{
						Log.d(Consts.TAG, "No deliveries found!");
					}

					adapter = new HistoryAdapter(deliveryList);
					list.setAdapter(adapter);
					showList();
				}
				else{
					Log.e(Consts.TAG, e.getMessage());
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
					startActivity(new Intent(getApplicationContext(),MainActivity.class));
				}
			}
			
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(getApplicationContext(), MainActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showLoader(){
		loader.setVisibility(View.VISIBLE);
		list.setVisibility(View.GONE);
	}
	
	private void showList(){
		loader.setVisibility(View.GONE);
		list.setVisibility(View.VISIBLE);
	}
	
	private class HistoryAdapter extends BaseAdapter{
		
		LinkedList<ParseObject> historyList = new LinkedList<ParseObject>();
		
		public HistoryAdapter(LinkedList<ParseObject> list) {
			historyList = list;
		}

		@Override
		public int getCount() {
			return historyList.size();
		}

		@Override
		public Object getItem(int position) {
			ParseObject delivery = historyList.get(position);
			return delivery;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.delivery_row, parent, false);
		    
		    TextView packageName = (TextView) rowView.findViewById(R.id.delivery_row_package_name);
		    
		    final ParseObject delivery = (ParseObject) getItem(position);
		    
		    if(delivery != null){
		    	String packageNameString = delivery.getString("packageName");
		    	if(packageNameString != null && packageName != null){
		    		packageName.setText(packageNameString);
		    	}
			    
			    rowView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(v.getContext(), DeliveryDetailsActivity.class);
						intent.putExtra("deliveryId", delivery.getObjectId());
						startActivity(intent);
					}
				});
		    }
		    
			return rowView;
		}
		
	}
}
	
	/*private void dataAdapter(List<ParseObject> objects, String objectId) {

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
					isFinished) {

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
	protected void onListItemClick(ListView l, android.view.View v, int position, long id){
		super.onListItemClick(l, v, position, id);
		
		Intent detailedInt = new Intent(getApplicationContext(), DeliveryDetailsActivity.class);
		detailedInt.putExtra("delivery", deliveries[position]);
		
		startActivityForResult(detailedInt, position);
		
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

/*		@Override
		public long getItemId(int position) {
			Delivery item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}*/

