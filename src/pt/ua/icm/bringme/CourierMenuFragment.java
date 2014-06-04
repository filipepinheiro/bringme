package pt.ua.icm.bringme;

import java.util.LinkedList;
import pt.ua.icm.bringme.models.*;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.parse.LocationCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class CourierMenuFragment extends Fragment {
	
	private ToggleButton courierToggle;
	private OnLocationListener mListener;
	private LinkedList<ParseObject> deliveryList;
	
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
		
		ListView listViewTaskList = (ListView) view.findViewById(R.id.listViewTaskList);
		
		listViewTaskList.setAdapter(new deliveryAdapter(deliveryList));
		
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

	public class deliveryAdapter extends BaseAdapter {
		
		LinkedList<ParseObject> deliveryList = new LinkedList<ParseObject>();
		Context context;
	
		public deliveryAdapter(LinkedList<ParseObject> deliveryList) {
			this.deliveryList = deliveryList;
			this.context = context;
		}

		@Override
		public int getCount() {
			if(deliveryList==null)
				return 0;
			else
				return deliveryList.size();
		}

		@Override
		public Object getItem(int position) {
			return deliveryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.request_delivery_courier_item, parent, false);
		    TextView name = (TextView) rowView.findViewById(R.id.courierListItemName);
		    TextView rating = (TextView) rowView.findViewById(R.id.courierListItemRating);
		    
		    final ParseObject delivery = deliveryList.get(position);
		    name.setText(delivery.getString("packageName"));
		    rowView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			return rowView;
		}
	
	}

}
