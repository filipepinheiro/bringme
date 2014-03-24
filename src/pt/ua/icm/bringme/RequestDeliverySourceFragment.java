package pt.ua.icm.bringme;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link RequestDeliverySourceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the
 * {@link RequestDeliverySourceFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class RequestDeliverySourceFragment extends Fragment {

	private OnFragmentInteractionListener mListener;
	
	private Geocoder coder;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @return A new instance of fragment RequestDeliverySourceFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static RequestDeliverySourceFragment newInstance() {
		RequestDeliverySourceFragment fragment = new RequestDeliverySourceFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public RequestDeliverySourceFragment() {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_request_delivery_source,
				container, false);
		
		final GoogleMap sourceMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.sourceMap)).getMap();
		SupportMapFragment supMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.sourceMap);
		AutoCompleteTextView sourceLocationAutoComplete = (AutoCompleteTextView) v.findViewById(R.id.sourceMapAddress);
		
		
		
		
		sourceLocationAutoComplete.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()<3)
					return;
				
				try {
					List<Address> addressList = coder.getFromLocationName(s.toString(), 3);
					
					if(!addressList.isEmpty()){
						Log.i("bring",String.valueOf(addressList.get(0).getLatitude()));
						LatLng address = new LatLng(40.632365, -8.658618);
						sourceMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).position(address).flat(true).rotation(245));
					}else{
						Log.i("bring","EMPTY");
					}
				
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Log.e("bringme", addressList.get(0).getAddressLine(0));
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
