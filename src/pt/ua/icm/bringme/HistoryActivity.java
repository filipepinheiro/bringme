package pt.ua.icm.bringme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ua.icm.adapters.MyArrayAdapter;
import pt.ua.icm.bringme.models.Delivery;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		ListView listview = (ListView) findViewById(android.R.id.list);

		Delivery dev1 = new Delivery();
		dev1.name = "Caderno";
		dev1.description = "Caderno de ICM";

		Delivery dev2 = new Delivery();
		dev2.name = "Pasta";
		dev2.description = "Pasta do computador. Café Rozeta!";

		Delivery[] deliveries = new Delivery[2];
		deliveries[0] = dev1;
		deliveries[1] = dev2;
		
		MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(), deliveries, new String[2]);
	    //setListAdapter(adapter);
		
		listview.setAdapter(adapter);
		
		
		
		
		
		
		
		
		
		
		
		/*final ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < deliveries.length; ++i) {
	      list.add(deliveries[i]);
	    }
		

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				R.id.tvTitulo, list);
		listview.setAdapter(adapter);*/

		

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

		/*@Override
		public long getItemId(int position) {
			Delivery item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}*/

	}

}
