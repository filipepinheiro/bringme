package pt.ua.icm.bringme;

import pt.ua.icm.bringme.datastorage.StaticDatabase;
import pt.ua.icm.bringme.models.Order;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link RequestDeliveryOrderDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 
 */
public class RequestDeliveryOrderDetailsFragment extends Fragment {
	
	TextView orderTagField, orderDescriptionField, orderSpecialNotesField;
	Button saveOrderButton;

	public static RequestDeliveryOrderDetailsFragment newInstance() {
		RequestDeliveryOrderDetailsFragment fragment = new RequestDeliveryOrderDetailsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public RequestDeliveryOrderDetailsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			//TODO: implement
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_request_delivery_order_details, 
				container, false);
		
		orderTagField = (TextView) v.findViewById(R.id.orderTagField);
		orderDescriptionField  = (TextView) v.findViewById(R.id.orderDescriptionField);
		orderSpecialNotesField  = (TextView) v.findViewById(R.id.orderSpecialNotesField);
		
		saveOrderButton = (Button) v.findViewById(R.id.saveOrder);
		
		saveOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveOrder(v);
			}
		});
		
		return v;
	}
	
	public void saveOrder(View v){
		if(orderTagField.getText().toString().isEmpty()){
			orderTagField.setError("Required Field");
		}else if(orderDescriptionField.getText().toString().isEmpty()){
			orderDescriptionField.setError("Required Field");
		}else{
			Order order = new Order(orderTagField.getText().toString(),orderDescriptionField.getText().toString());
			if(!orderSpecialNotesField.getText().toString().isEmpty()){
				order.setSpecialNotes(orderSpecialNotesField.getText().toString());
			}
			StaticDatabase.setCurrentOrder(order);
			
			Toast t = Toast.makeText(getActivity(), "Saved with success!", Toast.LENGTH_SHORT);
			t.show();
		}
	}

}
