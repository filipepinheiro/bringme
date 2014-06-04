package pt.ua.icm.bringme;

import java.util.LinkedList;
import java.util.List;

import pt.ua.icm.bringme.helpers.MapHelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebStorage.Origin;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

public class DeliveryCourierMapFragment extends Fragment implements OnMarkerClickListener{
	private OnDeliveryListener mListener;
	private LinkedList<ParseUser> courierList = new LinkedList<ParseUser>();
	private LatLng origin;
	private GoogleMap map;

	public static DeliveryCourierMapFragment newInstance(LinkedList<ParseUser> parseUserList, LatLng origin) {
		DeliveryCourierMapFragment fragment = new DeliveryCourierMapFragment();
		Bundle args = new Bundle();
		args.putSerializable("courierList", parseUserList);
		args.putParcelable("origin", origin);
		fragment.setArguments(args);
		return fragment;
	}
	
	public void setCourierList(LinkedList<ParseUser> courierList){
		Log.d(Consts.TAG, "Called setCourierList");
		this.courierList = courierList;
	}

	public DeliveryCourierMapFragment() {
		// Required empty public constructor
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if(bundle != null){
			if(bundle.containsKey("courierList")){
				courierList = (LinkedList<ParseUser>) bundle.getSerializable("courierList");
				origin = (LatLng) bundle.getParcelable("origin");
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_delivery_courier_map,
				container, false);
		SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.courierMapFragment);
		
		map = mapFragment.getMap();
		map.setOnMarkerClickListener(this);
		
		if(origin != null){
			MapHelper.updateMapCamera(map, origin);
		}
		
		markUserOnMap(map, courierList);
		
		// Fix for black background on devices < 4.1
        if (android.os.Build.VERSION.SDK_INT < 
            android.os.Build.VERSION_CODES.JELLY_BEAN) {
            setMapTransparent((ViewGroup) view);
        }
		
		return view;
	}
	
	private void setMapTransparent(ViewGroup group) {
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = group.getChildAt(i);
            if (child instanceof ViewGroup) {
                setMapTransparent((ViewGroup) child);
            } else if (child instanceof SurfaceView) {
                child.setBackgroundColor(0x00000000);
            }
        }
    }

	private void markUserOnMap(GoogleMap map, List<ParseUser> courierList) {
		for(ParseUser user : courierList){
			ParseGeoPoint point = user.getParseGeoPoint("lastLocation");
			LatLng coordinates = new LatLng(point.getLatitude(), point.getLongitude());
			map.addMarker(new MarkerOptions()
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place))
			.position(coordinates)
			.title(user.getString("firstName") + " " + user.getString("lastName"))
			.snippet(user.getObjectId()));
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		//Toast.makeText(getActivity(), marker.getSnippet(), Toast.LENGTH_SHORT).show();
		mListener.showCourierProfile(marker.getSnippet());
		return false;
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
		void showCourierProfile(String snippet);
	}

}
