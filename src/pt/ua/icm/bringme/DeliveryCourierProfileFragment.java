package pt.ua.icm.bringme;

import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.helpers.FacebookImageLoader;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.User;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DeliveryCourierProfileFragment extends Fragment {
	private String userId;
	private TextView deliveryCourierProfileName, deliveryCourierProfileRequests,
		deliveryCourierProfileDeliveries;
	private RoundedImageView deliveryCourierProfileImage;

	private OnDeliveryListener mListener;

	public static DeliveryCourierProfileFragment newInstance(String userId) {
		DeliveryCourierProfileFragment fragment = new DeliveryCourierProfileFragment();
		Bundle args = new Bundle();
		args.putString("userId", userId);
		fragment.setArguments(args);
		return fragment;
	}

	public DeliveryCourierProfileFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			userId = getArguments().getString("userId");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_delivery_courier_profile,
				container, false);
		
		deliveryCourierProfileName = (TextView) view.findViewById(R.id.deliveryCourierProfileName);
		deliveryCourierProfileRequests = (TextView) view.findViewById(R.id.deliveryCourierRequestsValue);
		deliveryCourierProfileDeliveries = (TextView) view.findViewById(R.id.deliveryCourierDeliveriesValue);
		deliveryCourierProfileImage = (RoundedImageView) view.findViewById(R.id.deliveryCourierUserImage);
		
		ParseUser.getQuery().getInBackground(userId, new GetCallback<ParseUser>() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if(e == null){
					if(user.has("facebookId")){
						FacebookImageLoader loader = new FacebookImageLoader();
						try {
							Bitmap picture = loader.execute(user.getString("facebookId"),"normal").get();
							deliveryCourierProfileImage.setImageBitmap(picture);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ExecutionException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					deliveryCourierProfileName.setText(user.getString("firstName") + " "+ user.getString("lastName"));
					deliveryCourierProfileRequests.setText(String.valueOf(user.getInt("requests")));
					deliveryCourierProfileDeliveries.setText(String.valueOf(user.getInt("deliveries")));
				}
			}
		});
		
		Button deliveryCourierPickButton = (Button) view.findViewById(R.id.DeliveryCourierPick);
		deliveryCourierPickButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mListener.setCourierFromMap(userId);
			}
		});
		
		return view;
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

		void setCourierFromMap(String objectId);
	}

}
