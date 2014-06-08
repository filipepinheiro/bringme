package pt.ua.icm.bringme;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.helpers.ParserAdapter;

import pt.ua.icm.bringme.models.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.parse.FindCallback;
import com.parse.LocationCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class CourierMenuFragment extends Fragment {
	
	private ToggleButton courierToggle;
	private OnLocationListener mListener;
	private LinkedList<ParseObject> deliveryList = new LinkedList<ParseObject>();
	private ListView listViewTaskList;
	
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
		
		courierToggle.setChecked(ParseUser.getCurrentUser().getBoolean("courier"));
		
		if(courierToggle.isChecked()){
			mListener.updateLocation();
		}
		
		courierToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mListener.setCourierMode(true);
				}
				else{
					mListener.setCourierMode(false);
				}
			}
		});
		
		listViewTaskList = (ListView) view.findViewById(R.id.listViewTaskList);
		
		ParseQuery<ParseObject> deliveryQuery = new ParseQuery<ParseObject>("Delivery");
		deliveryQuery.whereEqualTo("courier", ParseUser.getCurrentUser());
		deliveryQuery.whereEqualTo("finished", false);
		deliveryQuery.whereNotEqualTo("accepted", false);
		deliveryQuery.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(e == null){
					deliveryList.addAll(objects);
					listViewTaskList.setAdapter(new deliveryAdapter(deliveryList, getActivity().getApplicationContext()));
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
		//void changeLastLocation(ParseGeoPoint geoPoint);

		void setCourierMode(boolean b);

		void updateLocation();
	}

	public class deliveryAdapter extends BaseAdapter {
		
		LinkedList<ParseObject> deliveryList = new LinkedList<ParseObject>();
		Context context;
	
		public deliveryAdapter(LinkedList<ParseObject> deliveryList, Context context) {
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
					Intent showDeliveryIntent = new Intent(getActivity(), DeliveryActionActivity.class);
					showDeliveryIntent.putExtra("delivery", delivery.getObjectId());
					getActivity().startActivity(showDeliveryIntent);
				}
			});
			return rowView;
		}
	}

}
