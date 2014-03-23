package pt.ua.icm.bringme;

import java.util.LinkedList;

import pt.ua.icm.bringme.adapters.HistoricalAdapter;
import pt.ua.icm.bringme.models.Delivery;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ListView;

public class HistoricalActivity extends ActionBarActivity {
	
	private LinkedList<Delivery> deliveryList = new LinkedList<Delivery>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historical);
		
		ListView historicalListView = (ListView) findViewById(R.id.historicalListView);
		//TODO: Retrieve all deliveries that contain the user
		deliveryList.add(new Delivery("Rua João de Moura, 3800-163 Aveiro", "Edifício Central e da Reitoria, 3810-164 Aveiro", 1, 2));
		historicalListView.setAdapter(new HistoricalAdapter(this, deliveryList));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historical, menu);
		return true;
	}

}
