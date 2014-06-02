package pt.ua.icm.bringme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.parse.ParseGeoPoint;

public class DeliveryCourierMapFragment extends Fragment {
	private static final String ARG_ORIGIN_LAT = "originLat";
	private static final String ARG_ORIGIN_LNG = "originLng";

	private Double originLat = 0.0;
	private Double originLng = 0.0;

	private OnDeliveryListener mListener;

	public static DeliveryCourierMapFragment newInstance(ParseGeoPoint origin) {
		DeliveryCourierMapFragment fragment = new DeliveryCourierMapFragment();
		Bundle args = new Bundle();
		if (origin != null) {
			args.putDouble(ARG_ORIGIN_LAT, origin.getLatitude());
			args.putDouble(ARG_ORIGIN_LNG, origin.getLongitude());
		}
		fragment.setArguments(args);
		return fragment;
	}

	public DeliveryCourierMapFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			originLat = getArguments().getDouble(ARG_ORIGIN_LAT);
			originLng = getArguments().getDouble(ARG_ORIGIN_LNG);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_delivery_courier_map,
				container, false);
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
	public void onDestroyView() {
		super.onDestroyView();
		SupportMapFragment f = (SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.courierMapFragment);
		if (f != null){
			getFragmentManager().beginTransaction().remove(f).commit();
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnDeliveryListener {
	}

}
