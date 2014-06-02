package pt.ua.icm.bringme;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.parse.LocationCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

public class CourierMenuFragment extends Fragment {
	
	private ToggleButton courierToggle;
	private OnLocationListener mListener;
	
	public static CourierMenuFragment newInstance() {
		return new CourierMenuFragment();
	}

	public CourierMenuFragment() {
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
		View view = inflater.inflate(R.layout.fragment_courier_menu, container,
				false);
		
		courierToggle = (ToggleButton) view.findViewById(R.id.toggleCourierModeButton);
		courierToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					ParseGeoPoint.getCurrentLocationInBackground(5000, new LocationCallback() {
						@Override
						public void done(ParseGeoPoint geoPoint, ParseException e) {
							if(e == null){
								Log.i(Consts.TAG, "Retrieved location!");
								mListener.changeLastLocation(geoPoint);
							}
						}
					});
				}
				else{
					mListener.setCourierMode(false);
				}
			}
		});
		
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnLocationListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnLocationListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
	
	public interface OnLocationListener {
		void changeLastLocation(ParseGeoPoint geoPoint);

		void setCourierMode(boolean b);
	}

}
