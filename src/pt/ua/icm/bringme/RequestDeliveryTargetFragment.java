package pt.ua.icm.bringme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link RequestDeliveryTargetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the
 * {@link RequestDeliveryTargetFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class RequestDeliveryTargetFragment extends Fragment {
	private OnFragmentInteractionListener mListener;
	private Geocoder coder;
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @return A new instance of fragment RequestDeliveryTargetFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static RequestDeliveryTargetFragment newInstance() {
		RequestDeliveryTargetFragment fragment = new RequestDeliveryTargetFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public RequestDeliveryTargetFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			//TODO: Implement
		}

		coder = new Geocoder(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View v = inflater.inflate(R.layout.fragment_request_delivery_target, container, false);
		
		SupportMapFragment supMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.targetMap);
		final GoogleMap targetMap = supMapFragment.getMap();
		
		//TODO: Make this async
		LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		Location actualLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		//Default location Aveiro
		targetMap.moveCamera(CameraUpdateFactory.newLatLng( new LatLng(40.6273385,-8.6395553)));
		targetMap.animateCamera(CameraUpdateFactory.zoomTo(8));
		
		final AutoCompleteTextView targetLocationAutoComplete = (AutoCompleteTextView) v.findViewById(R.id.targetMapAddress);
		
		targetMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng coordinates) {
				targetMap.addMarker(new MarkerOptions().position(coordinates).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_origin)));
				try {
					String address = coder.getFromLocation(coordinates.latitude, coordinates.longitude, 1).get(0).getAddressLine(0).toString() +
							", " + coder.getFromLocation(coordinates.latitude, coordinates.longitude, 1).get(0).getAddressLine(1).toString();
					targetLocationAutoComplete.setText(address);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		targetLocationAutoComplete.addTextChangedListener(new TextWatcher() {
			List<String> addressNameList = new ArrayList<String>();
		
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()<3)
					return;
				
				try {
					addressNameList.clear();
					List<Address> addressList = coder.getFromLocationName(s.toString(), 3);
					
					for(Address address : addressList){
						addressNameList.add(address.getAddressLine(0) + ", " + address.getAddressLine(1));
					}
					
					targetLocationAutoComplete.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,addressNameList));
					targetLocationAutoComplete.showDropDown();
					
					if(!addressList.isEmpty()){
						LatLng address = new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude());
						targetMap.clear();
						
						targetMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_destination)).position(address).flat(true).rotation(0));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		return v;
		
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
