package pt.ua.icm.bringme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.ua.icm.bringme.helpers.AddressHelper;
import pt.ua.icm.bringme.helpers.MapHelper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;

import android.app.Activity;
import android.app.Service;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class DeliveryDestinationFragment extends Fragment {

	private OnDeliveryListener mListener;
	private Geocoder coder;

	public static DeliveryDestinationFragment newInstance() {
		return new DeliveryDestinationFragment();
	}

	public DeliveryDestinationFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		coder = new Geocoder(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_delivery_destination,
				container, false);
		
		final SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.destinationMapFragment);
		
		final AutoCompleteTextView destinationAddress = (AutoCompleteTextView) view.findViewById(R.id.deliveryDestinationAddress);
		
		final GoogleMap map = (GoogleMap) mapFragment.getMap();
		
		map.setOnMapClickListener(mapClickHandler(map, destinationAddress));
		
		destinationAddress.addTextChangedListener(addressSugestionsHandler(destinationAddress, map));
		
		return view;
	}
	
	private TextWatcher addressSugestionsHandler(final AutoCompleteTextView autoCompleteField, 
			final GoogleMap map) {
		return new TextWatcher() {
			
			List<String> addressNameList = new ArrayList<String>();
			List<Address> addressList = new ArrayList<Address>();
			
			String query = "";
			
			private Handler mHandler = new Handler();
			
			private Runnable queryMap = new Runnable(){

				@Override
				public void run() {
					if (query.length() < 3){
						map.clear();
						return;
					}
					
					addressNameList.clear();
					
					try {
						Log.d(Consts.TAG, "Searching for: "+query);
						addressList = coder.getFromLocationName(query, 3);
					} catch (IOException e) {
						Toast.makeText(getActivity(), "Failed to retrive location!", Toast.LENGTH_SHORT).show();
					}

					for (Address address : addressList) {
						addressNameList.add(AddressHelper.getPrettyAddress(address));
					}

					autoCompleteField.setAdapter(
							new ArrayAdapter<String>(getActivity(),
									android.R.layout.simple_dropdown_item_1line,
									addressNameList));
					autoCompleteField.showDropDown();

					if (!addressList.isEmpty()) {
						double latitude = addressList.get(0).getLatitude(), 
								longitude = addressList.get(0).getLongitude();
						LatLng address = new LatLng(latitude, longitude);
						
						map.clear();

						map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_action_place))
								.position(address).flat(true).rotation(0));
						
						MapHelper.updateMapCamera(map, address);
						
						if(mListener != null){
							mListener.setDestination(new ParseGeoPoint(latitude, longitude), addressNameList.get(0));
						}
					}
					
					//Hide the soft keyboard
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(autoCompleteField.getWindowToken(), 0);
				}
			};
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}

			@Override
			public void afterTextChanged(Editable query) {
				this.query = query.toString();
				mHandler.removeCallbacks(queryMap);
				mHandler.postDelayed(queryMap, 2000);
			}
		};
	}
	
	private OnMapClickListener mapClickHandler(final GoogleMap map, 
			final AutoCompleteTextView addressTextView){
		return new OnMapClickListener(){

			@Override
			public void onMapClick(LatLng coordinates) {
				try {
					double latitude = coordinates.latitude;
					double longitude = coordinates.longitude;
					
					map.addMarker(new MarkerOptions().position(coordinates)
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.ic_action_place)));
				
					Address address = coder.getFromLocation(latitude, 
							longitude, 1).get(0);

					String originAddressName = AddressHelper.getPrettyAddress(address); 

					addressTextView.setText(originAddressName);
					
					mListener.setDestination(AddressHelper.latLngToParseGeoPoint(coordinates), originAddressName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IndexOutOfBoundsException e) {
					map.clear();
					Toast.makeText(getActivity(), "Invalid Point!", Toast.LENGTH_SHORT).show();
				}
			}
		};
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnDeliveryListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnDeliveryListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
	
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
	    		.findFragmentById(R.id.destinationMapFragment);
	    if (mapFragment != null){
	        getFragmentManager().beginTransaction().remove(mapFragment).commit();
	    }
	}

	public interface OnDeliveryListener{
		public void setDestination(ParseGeoPoint parseGeoPoint, String string);
	}

}
