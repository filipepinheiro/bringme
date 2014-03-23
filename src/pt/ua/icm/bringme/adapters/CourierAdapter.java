package pt.ua.icm.bringme.adapters;

import java.util.LinkedList;

import pt.ua.icm.bringme.BringMeNotification;
import pt.ua.icm.bringme.R;
import pt.ua.icm.bringme.RequestListActivity;
import pt.ua.icm.bringme.models.Courier;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class CourierAdapter extends BaseAdapter {
	private Context context;
	private static LayoutInflater inflater = null;

	LinkedList<Courier> courierList = new LinkedList<Courier>();

	public CourierAdapter(Context context, LinkedList<Courier> courierList) {
		this.context = context;
		this.courierList = courierList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return courierList.size();
	}

	@Override
	public Courier getItem(int position) {
		return courierList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Inflate with the courier_list_item.xml
		if (convertView == null)
			convertView = inflater.inflate(R.layout.courier_list_item, null);

		Courier courier = getItem(position);

		// Set full name on TextView
		TextView fullNameTextView = (TextView) convertView
				.findViewById(R.id.deliveryRequestTargetAddress);
		fullNameTextView.setText(courier.getFullName());

		// Set Courier Rating
		RatingBar courierRatingBar = (RatingBar) convertView
				.findViewById(R.id.courierRating);
		courierRatingBar.setRating(courier.getRate());

		courierRatingBar.setEnabled(false);

		convertView.setOnClickListener(courierClickHandler(position));

		return convertView;
	}

	public View.OnClickListener courierClickHandler(int position) {
		final Courier courier = courierList.get(position);

		View.OnClickListener handler = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String message = "Do you want to ask " + courier.getFullName()
						+ " to pick your package?";

				Builder alertDialog = new AlertDialog.Builder(context);
				alertDialog.setMessage(message);
				alertDialog.setNegativeButton("No", null);
				alertDialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								int icon = BringMeNotification.smallIconDeliveryRequest;
								BringMeNotification
										.sendNotification(
												context,
												icon,
												"Delivery Request",
												"[User] asked you to delivery a package",
												true, RequestListActivity.class);
							}
						});
				alertDialog.show();
			}
		};

		return handler;
	}

}
