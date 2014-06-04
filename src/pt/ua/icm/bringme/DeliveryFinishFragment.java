package pt.ua.icm.bringme;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import pt.ua.icm.bringme.models.Delivery;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DeliveryFinishFragment extends Fragment {

	private Delivery delivery;
	
	TextView packageName, packageDescription, packageNotes, packageDetailedOrigin,
	packageDetailedDestination;
	
	private OnDeliveryListener mListener;

	public static DeliveryFinishFragment newInstance(Delivery delivery) {
		DeliveryFinishFragment fragment = new DeliveryFinishFragment();
		if(delivery != null){
			Bundle bundle = new Bundle();
			bundle.putParcelable("delivery", delivery);
			fragment.setArguments(bundle);
		}
		return fragment;
	}

	public DeliveryFinishFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			Log.d(Consts.TAG,"Gonna parse the delivery");
			delivery = (Delivery) getArguments().getParcelable("delivery");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_delivery_finish, container,
				false);
		
		packageName = (TextView) view.findViewById(R.id.finish_request_package_name);
		packageDescription = (TextView) view.findViewById(R.id.finish_request_package_description);
		packageNotes = (TextView) view.findViewById(R.id.finish_request_special_notes);
		packageDetailedOrigin = (TextView) view.findViewById(R.id.finish_request_detailed_origin);
		packageDetailedDestination = (TextView) view.findViewById(R.id.finish_request_detailed_destination);
		
		fillFields();
		
		Button submitButton = (Button) view.findViewById(R.id.finish_request_button);
		submitButton.setOnClickListener(submitRequestDelivery());
		
		return view;
	}

	private OnClickListener submitRequestDelivery() {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendRequestNotification(delivery.courierId);
			}
		};
	}
	
	private void fillFields() {
		if(delivery != null){
			packageName.setText(delivery.name);
			packageDescription.setText(delivery.description);
			packageNotes.setText(delivery.notes);
			packageDetailedOrigin.setText(delivery.detailedOrigin);
			packageDetailedDestination.setText(delivery.detailedDestination);
		}
	}

	private void sendRequestNotification(final String courierId) {
		//Install Notifications
		ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public void done(ParseException e) {
				if(e == null){
					ParseQuery userQuery = ParseUser.getQuery().whereEqualTo("objectId", courierId);
					
					ParseQuery pushQuery = ParseInstallation.getQuery();
					pushQuery.whereMatchesQuery("user", userQuery);
					
					ParsePush push = new ParsePush();
					//push.setQuery(pushQuery);
					push.setChannel(courierId);
					push.setMessage("Hey! Can you BringMe?");
					push.sendInBackground(new SendCallback() {
						
						@Override
						public void done(ParseException e) {
							if(e == null){
								Log.i(Consts.TAG, "Notification send with success!");
								Toast.makeText(getActivity(), "Notification sent!", Toast.LENGTH_SHORT).show();
							}
							else{
								Toast.makeText(getActivity(), "Notification failed!", Toast.LENGTH_SHORT).show();
								Log.e(Consts.TAG, "Notification failed! :(");
							}
						}
					});
				}else{
					Log.e(Consts.TAG, "Failed to save Current Installation!");					
				}
			}
		});
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

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnDeliveryListener {
	}

}
