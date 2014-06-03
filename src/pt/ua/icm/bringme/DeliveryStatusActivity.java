package pt.ua.icm.bringme;

import com.parse.ParseUser;

import pt.ua.icm.adapters.MyArrayAdapter;
import pt.ua.icm.bringme.helpers.BitmapHelper;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.Delivery;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DeliveryStatusActivity extends ListActivity {

	private Delivery dev1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery_status);
		
		ListView listview = (ListView) findViewById(android.R.id.list);

		dev1 = new Delivery();
		dev1.name = "Porta Lápis";
		dev1.description = "Porta lápis da UA. Biblioteca.";

		Delivery dev2 = new Delivery();
		dev2.name = "Portátil";
		dev2.description = "Mac Book";

		Delivery[] deliveries = new Delivery[2];
		deliveries[0] = dev1;
		deliveries[1] = dev2;
		
		MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(), deliveries, new String[2]);
	    //setListAdapter(adapter);
		
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
	
		Intent detailedInt = new Intent(this, DetailsActivity.class);
		detailedInt.putExtra("asd", "dsa");
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
