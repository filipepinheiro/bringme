package pt.ua.icm.bringme;

import java.util.LinkedList;
import java.util.List;

import pt.ua.icm.adapters.DeliveryCourierListAdapter;
import pt.ua.icm.bringme.models.User;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseUser;

public class DeliveryCourierListFragment extends Fragment{
	private OnDeliveryListener mListener;
	private LinkedList<ParseUser> courierList = new LinkedList<ParseUser>();
	private LatLng origin;

	public static Fragment newInstance(LinkedList<ParseUser> parseUserList) {
		Fragment fragment = new DeliveryCourierListFragment();
		Bundle args = new Bundle();
		args.putSerializable("courierList", parseUserList);		
		fragment.setArguments(args);
		return fragment;
	}

	public DeliveryCourierListFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if(args != null){
			if(args.containsKey("courierList")){
				courierList = (LinkedList<ParseUser>) args.getSerializable("courierList");
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view =  inflater.inflate(R.layout.fragment_delivery_courier_list, container, false);
		
		ListView courierListView = (ListView) view.findViewById(R.id.deliveryCourierList);
		RequestDeliveryActivity parent = ((RequestDeliveryActivity) getActivity());
		DeliveryCourierListAdapter adapter = new DeliveryCourierListAdapter(courierList, parent.getApplicationContext());
		courierListView.setAdapter(adapter);
		
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
	}

	public interface OnDeliveryListener {
		public void setCourier(User courier);
	}
}