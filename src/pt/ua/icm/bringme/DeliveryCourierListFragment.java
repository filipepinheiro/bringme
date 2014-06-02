package pt.ua.icm.bringme;

import pt.ua.icm.bringme.models.User;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.ParseGeoPoint;

@SuppressLint("NewApi")
public class DeliveryCourierListFragment extends Fragment{
	private OnDeliveryListener mListener;

	public static Fragment newInstance(ParseGeoPoint origin) {
		return new DeliveryCourierListFragment();
	}

	public DeliveryCourierListFragment() {
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
		View view =  inflater.inflate(R.layout.fragment_delivery_courier_list, container, false);
		
		/*final SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
				.findFragmentById(R.id.courierMapFragment);
		
		final GoogleMap map = (GoogleMap) mapFragment.getMap();*/
		
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
		
		/*try {
			mListener = (OnDeliveryListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnDeliveryListener");
		}*/
	}

	@Override
	public void onDetach() {
		super.onDetach();
		//mListener = null;
	}
	
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    /*DeliveryCourierListFragment f = 
	    		(DeliveryCourierListFragment) getFragmentManager()
	    		.findFragmentById(R.id.courierMapFragment);
	    if (f != null) {
	        getFragmentManager().beginTransaction().remove(f).commit();
	    }*/
	}

	public interface OnDeliveryListener {
		public void setCourier(User courier);
	}
}