package pt.ua.icm.bringme;

import java.util.LinkedList;

import pt.ua.icm.bringme.models.User;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.parse.ParseUser;

public class DeliveryCourierFragment extends Fragment {

	public static DeliveryCourierFragment newInstance() {
		return new DeliveryCourierFragment();
	}

	public DeliveryCourierFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_delivery_courier,
				container, false);

		/*
		 * final SupportMapFragment mapFragment = (SupportMapFragment)
		 * getFragmentManager() .findFragmentById(R.id.courierMapFragment);
		 * 
		 * final GoogleMap map = (GoogleMap) mapFragment.getMap();
		 */

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
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
}
