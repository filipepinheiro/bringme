package pt.ua.icm.bringme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import pt.ua.icm.bringme.helpers.BitmapHelper;
import pt.ua.icm.bringme.helpers.DirectionsJSONParser;
import pt.ua.icm.bringme.helpers.FacebookImageLoader;
import pt.ua.icm.bringme.helpers.MapHelper;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.Delivery;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class DetailsActivity extends ActionBarActivity {

	GoogleMap map;
	ArrayList<LatLng> markerPoints;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery_details);

		Intent i = getIntent();
		final Delivery delivery = (Delivery) i.getParcelableExtra("delivery");

		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					Log.i(Consts.TAG, "LIST = " + objects.size());
					setNameOfCourier(objects, delivery);
				} else {
					Log.i(Consts.TAG, "Delivery List Empty! - " + e);
				}
			}

		});

	
		String fbId = delivery.facebookId;

		RoundedImageView drawerProfilePicture = (RoundedImageView) findViewById(R.id.deliveryCourierUserImage);
		drawerProfilePicture.setBorderColor(Color
				.parseColor(getString(R.color.green_peas)));

		if (fbId != null) {

			FacebookImageLoader profilePictureLoader = new FacebookImageLoader();

			Bitmap profilePicture = null;

			try {

				profilePicture = profilePictureLoader.execute(fbId, "normal")
						.get();

			} catch (InterruptedException e) {
				Log.e(Consts.TAG, "Load profile picture was cancelled!");
				e.printStackTrace();
			} catch (ExecutionException e) {
				Log.e(Consts.TAG, "Execution Error!");
				e.printStackTrace();
			}

			if (profilePicture != null) {
				drawerProfilePicture.setImageBitmap(profilePicture);
			} else {
				Bitmap defaultPicture = BitmapHelper.drawableToBitmap(
						R.drawable.default_profile_picture, this);
				drawerProfilePicture.setImageBitmap(defaultPicture);
				drawerProfilePicture.setBorderColor(Color
						.parseColor(getString(R.color.green_peas)));
			}
		} else {
			Bitmap defaultPicture = BitmapHelper.drawableToBitmap(
					R.drawable.default_profile_picture, this);
			drawerProfilePicture.setImageBitmap(defaultPicture);
			drawerProfilePicture.setBorderColor(Color
					.parseColor(getString(R.color.green_peas)));

		}

		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.detailLastMapFragment);

		markerPoints = new ArrayList<LatLng>();

		// Getting Map for the SupportMapFragment
		map = fm.getMap();

		LatLng point1 = delivery.origin;
		markerPoints.add(point1);
		LatLng point2 = delivery.destination;
		markerPoints.add(point2);

		MapHelper.updateMapCamera(map, point1);

		MarkerOptions options = new MarkerOptions();
		MarkerOptions options2 = new MarkerOptions();

		// Setting the position of the marker
		options.position(point1);
		options2.position(point2);

		/**
		 * For the start location, the color of marker is GREEN and for the end
		 * location, the color of marker is RED.
		 */
		options.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		options2.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		// Add new marker to the Google Map Android API V2
		map.addMarker(options);
		map.addMarker(options2);

		// Checks, whether start and end locations are captured
		if (markerPoints.size() >= 2) {
			LatLng origin = markerPoints.get(0);
			LatLng dest = markerPoints.get(1);

			// Getting URL to the Google Directions API
			String url = getDirectionsUrl(origin, dest);

			DownloadTask downloadTask = new DownloadTask();

			// Start downloading json data from Google Directions API
			downloadTask.execute(url);

		}

	}

	protected void setNameOfCourier(List<ParseObject> objects, Delivery delivery) {
		// TODO Auto-generated method stub

		while (!objects.isEmpty()) {

			if (objects.get(0).getObjectId().equals(delivery.courierId)) {

				String firstName = objects.get(0).get("firstName").toString();
				String lastName = objects.get(0).get("lastName").toString();
				
				TextView tvUserName = (TextView) findViewById(R.id.nameUserCourier);
tvUserName.setText(firstName + " " + lastName);

				objects.clear();
			}
			else
				objects.remove(0);
		}
	}

	private static String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		public void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();
			String distance = "";
			String duration = "";

			/*
			 * if (result.size() < 1) { // Toast.makeText(getBaseContext(),
			 * "No Points", // Toast.LENGTH_SHORT).show(); return; }
			 */

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					if (j == 0) { // Get distance from the list
						distance = (String) point.get("distance");
						continue;
					} else if (j == 1) { // Get duration from the list
						duration = (String) point.get("duration");
						TextView tvv = (TextView) findViewById(R.id.expectedTime);
						tvv.setText(duration);
						continue;
					}

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(2);
				lineOptions.color(Color.RED);
			}

			map.addPolyline(lineOptions);
		}
	}

}
