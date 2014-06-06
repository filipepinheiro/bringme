package pt.ua.icm.bringme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class DeliveryDetailsFragment extends Fragment{
	private OnDeliveryListener mListener;
	private LinearLayout packageLocationLayout, packageDetailsLayout;
	private EditText detailedDestinationLocation, detailedPackageLocation, packageName,
		packageDescription, packageDetails;
	
	public static DeliveryDetailsFragment newInstance() {
		return new DeliveryDetailsFragment();
	}

	public DeliveryDetailsFragment() {
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
		View view = inflater.inflate(R.layout.fragment_delivery_details, container,
				false);
		
		packageDetailsLayout = 
				(LinearLayout) view.findViewById(R.id.packageDetails);
		
		detailedPackageLocation = 
				(EditText) view.findViewById(R.id.detailedPackageLocationEditText);
		detailedDestinationLocation = 
				(EditText) view.findViewById(R.id.detailedDestinationLocationEditText);
		
		packageName = 
				(EditText) view.findViewById(R.id.packageNameEditText);
		packageDescription = 
				(EditText) view.findViewById(R.id.packageDescriptionEditText);
		packageDetails = 
				(EditText) view.findViewById(R.id.packageDetailsEditText);
		
		Button detailedLocationButton = 
				(Button) view.findViewById(R.id.packageLocationDetailsButton);
		
		detailedLocationButton.setOnClickListener(submitDetails());
		
		return view;
	}

	private OnClickListener submitDetails() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String packageNameValue = 
						packageName.getText().toString();
				String packageDescriptionValue = 
						packageDescription.getText().toString();
				String packageDetailsValue = 
						packageDetails.getText().toString();
				
				String detailedPackageLocationValue = 
						detailedPackageLocation.getText().toString();
				String detailedDestinationLocationValue = 
						detailedDestinationLocation.getText().toString();
				
				if(detailedDestinationLocationValue.isEmpty()){
					detailedDestinationLocation.setError("Required!");
					return;
				}
				else if(detailedPackageLocationValue.isEmpty()){
					detailedPackageLocation.setError("Required!");
					return;
				}
				else if(packageNameValue.isEmpty()){
					packageDetails.setError("Required!");
					return;
				}
				
				mListener.validateDelivery();
				
				mListener.setDeliveryDetails(packageNameValue,packageDescriptionValue,
						packageDetailsValue,detailedPackageLocationValue, 
						detailedDestinationLocationValue);
			}
		};
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

	public interface OnDeliveryListener {
		public void setDeliveryDetails(String name,
				String description, String details,
				String originDetails,
				String destinationDetails);

		public boolean validateDelivery();
	}

}
