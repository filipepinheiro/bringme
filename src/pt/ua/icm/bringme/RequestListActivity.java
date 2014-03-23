package pt.ua.icm.bringme;

import java.util.LinkedList;

import pt.ua.icm.bringme.adapters.DeliveryRequestAdapter;
import pt.ua.icm.bringme.models.Delivery;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ListView;

public class RequestListActivity extends ActionBarActivity {
	
	LinkedList<Delivery> requestList = new LinkedList<Delivery>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_list);
		
		requestList.add(new Delivery("Rua João de Moura, 3800-163 Aveiro", "Edifício Central e da Reitoria, 3810-164 Aveiro", 1, 2));
		
		ListView requestsListView = (ListView) findViewById(R.id.deliveryRequestListView);
		requestsListView.setAdapter(new DeliveryRequestAdapter(this, requestList));
		//TODO: Retrieve Data of the Deliveries that are requesting this user
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_list, menu);
		return true;
	}

}
