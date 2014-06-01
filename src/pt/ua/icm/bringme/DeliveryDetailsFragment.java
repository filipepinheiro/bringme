package pt.ua.icm.bringme;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DeliveryDetailsFragment extends Fragment{
	private OnDeliveryListener mListener;
	private LinearLayout packageLocation;
	private LinearLayout packageDetails;
	
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
		
		packageLocation = 
				(LinearLayout) view.findViewById(R.id.packageLocationDetails);
		
		packageDetails = 
				(LinearLayout) view.findViewById(R.id.packageDetails);
		
		packageDetails.setVisibility(View.GONE);
		
		Button detailedLocationButton = 
				(Button) view.findViewById(R.id.packageLocationDetailsButton);
		Button packageDetailsButton = 
				(Button) view.findViewById(R.id.packageDetailsButton);
		
		detailedLocationButton.setOnClickListener(submitDetailedLocation());
		packageDetailsButton.setOnClickListener(submitPackageDetails());
		
		return view;
	}

	private OnClickListener submitPackageDetails() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText packageName = 
						(EditText) v.findViewById(R.id.packageNameEditText);
				EditText packageDescription = 
						(EditText) v.findViewById(R.id.packageDescriptionEditText);
				EditText packageDetails = 
						(EditText) v.findViewById(R.id.packageDetailsEditText);
				
				String packageNameValue = 
						packageName.getText().toString();
				String packageDescriptionValue = 
						packageDescription.getText().toString();
				String dpackageDetailsValue = 
						packageDetails.getText().toString();
				
				mListener.setPackageDetails(packageNameValue,packageDescriptionValue,
						dpackageDetailsValue);
			}
		};
	}

	private OnClickListener submitDetailedLocation() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText detailedPackageLocation = 
						(EditText) v.findViewById(R.id.detailedPackageLocationEditText);
				EditText detailedDestinationLocation = 
						(EditText) v.findViewById(R.id.detailedDestinationLocationEditText);
				
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
				
				mListener.setPackageLocationDetails(detailedPackageLocationValue, 
						detailedDestinationLocationValue);
				
				packageLocation.setVisibility(View.INVISIBLE);
				packageDetails.setVisibility(View.VISIBLE);
			}
		};
	}

	protected void showLocationDetails() {
		// TODO Auto-generated method stub
		
	}

	protected void showPackageDetails() {
		// TODO Auto-generated method stub
		
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
		public void setPackageLocationDetails(String detailedPackageLocation, 
				String detailedDestinationLocation);

		public void setPackageDetails(String packageName,
				String packageDescription, String packageDetails);
	}

}
