package pt.ua.icm.bringme;

import org.json.JSONException;
import org.json.JSONObject;

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
			packageName.setText(delivery.name.toString());
			packageDescription.setText(delivery.description.toString());
			packageNotes.setText(delivery.notes.toString());
			packageDetailedOrigin.setText(delivery.detailedOrigin.toString());
			packageDetailedDestination.setText(delivery.detailedDestination.toString());
		}
	}

	private void sendRequestNotification(final String courierId) {
		//Install Notifications
		ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				if(e == null){
					
					try {
						JSONObject data = new JSONObject(							
								"{\"alert\":\"Hey! Can you BringMe this item?\"," +
								"\"action\":\"pt.ua.icm.bringme.MainActivity\"}");
					
						ParsePush push = new ParsePush();
						push.setExpirationTimeInterval(60*5);
						push.setChannel("bringme"+courierId);
						push.setData(data);
						push.setMessage("Hey! Can you BringMe?");
						push.sendInBackground(new SendCallback() {
							
							@Override
							public void done(ParseException e) {
								if(e == null){
									Log.i(Consts.TAG, "Notification send with success!");
									Toast.makeText(getActivity(), "Notification sent!", Toast.LENGTH_SHORT).show();
									startActivity(new Intent(getActivity(),MainActivity.class));
								}
								else{
									Toast.makeText(getActivity(), "Notification failed!", Toast.LENGTH_SHORT).show();
									Log.e(Consts.TAG, "Notification failed! :(");
								}
							}
						});
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
