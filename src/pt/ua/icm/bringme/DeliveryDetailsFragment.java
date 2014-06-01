package pt.ua.icm.bringme;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class DeliveryDetailsFragment extends Fragment{
	private OnDeliveryListener mListener;
	
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
		
		Button submitLocationDetailsButton = 
				(Button) getActivity().findViewById(R.id.packageLocationDetailsButton);
		
		submitLocationDetailsButton.setOnClickListener(submitLocationDetails());
		
		return view;
	}

	private OnClickListener submitLocationDetails() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Click!", Toast.LENGTH_SHORT).show();
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
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
