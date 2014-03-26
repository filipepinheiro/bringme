package pt.ua.icm.bringme;

import pt.ua.icm.bringme.adapters.HistoricalAdapter;
import pt.ua.icm.bringme.datastorage.StaticDatabase;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class HistoricalActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historical);
		
		//Enable ActionBar Home
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		ListView historicalListView = (ListView) findViewById(R.id.historicalListView);
		// TODO: Retrieve all deliveries that contain the user
		historicalListView.setAdapter(new HistoricalAdapter(this, StaticDatabase.getFinishedRequests()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historical, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	    case android.R.id.home:
	      Intent homeIntent = new Intent(this, MainMenuActivity.class);
	      homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	      startActivity(homeIntent);
	    }
	  return (super.onOptionsItemSelected(menuItem));
	}

}
