package pt.ua.icm.bringme.adapters;

import java.util.LinkedList;
import java.util.List;

import pt.ua.icm.bringme.BringMeNotification;
import pt.ua.icm.bringme.R;
import pt.ua.icm.bringme.RateCourierActivity;
import pt.ua.icm.bringme.models.Delivery;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeliveryRequestAdapter extends BaseAdapter {
	private Context context;
	private static LayoutInflater inflater = null;

	List<Delivery> deliveryRequestList = new LinkedList<Delivery>();

	public DeliveryRequestAdapter(Context context,
			List<Delivery> deliveryRequestList) {
		this.context = context;
		this.deliveryRequestList = deliveryRequestList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return deliveryRequestList.size();
	}

	@Override
	public Delivery getItem(int position) {
		return deliveryRequestList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Inflate with the courier_list_item.xml
		if (convertView == null)
			convertView = inflater.inflate(R.layout.delivery_request_list_item,	null);

		Delivery deliveryRequest = getItem(position);
		
		TextView deliveryOrderTagText = 
				(TextView) convertView.findViewById(R.id.deliveryRequestOrderTag);
		deliveryOrderTagText.setText("TODO");

		TextView sourceAddressText = 
				(TextView) convertView.findViewById(R.id.deliveryRequestSourceAddress);
		sourceAddressText.setText("TODO");
		
		TextView targetAddressText = 
				(TextView) convertView.findViewById(R.id.deliveryRequestTargetAddress);
		targetAddressText.setText("TODO");

		convertView.setOnClickListener(requestClickHandler(position));

		return convertView;
	}

	public View.OnClickListener requestClickHandler(int position) {
		final Delivery delivery = deliveryRequestList.get(position);

		View.OnClickListener handler = new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				AlertDialog.Builder optionDialog = new AlertDialog.Builder(
						context);
				final TextView requestStatus = (TextView) v
						.findViewById(R.id.deliveryRequestStatus);

				// If the delivery is not accepted
				if (!delivery.isAccepted()) {
					optionDialog.setTitle("Can you make this delivery for [Requestor]?");
					optionDialog.setItems(new CharSequence[] { "Accept","Decline" }, 
							new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int position) {
							switch (position) {
							case 0:
								BringMeNotification
										.sendDeliveryRequestAccepted(context,"User Name");
								requestStatus.setText("Accepted");
								delivery.setAccepted(true);
								break;
							case 1:
								BringMeNotification
										.sendDeliveryRequestRejected(context,"User Name");
								requestStatus.setText("Rejected");
								// TODO: Remove from the list
								break;
							default:
								throw new UnsupportedOperationException(
										"This is a Yes/No dialog, you weren't supposed to be here!");
							}
						}

					});
				}

				// If the delivery was accepted and is not finished
				if (delivery.isAccepted() && !delivery.isFinished()) {
					optionDialog.setTitle("Have you finished the delivery?");
					optionDialog.setItems(
						new CharSequence[] { "Yes", "Not yet" },
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,	int position) {
								switch (position) {
								case 0:
									requestStatus.setText("Finished");
									
									delivery.setFinished(true);
									// TODO: Send this notification to the Requestor
									BringMeNotification.sendNotification(context, 
										BringMeNotification.smallIconDeliveryRequest,
										"Delivery Finished!", "[Courier] finished your delivery! Rate him!",
										true, RateCourierActivity.class);

									AlertDialog.Builder courierRateDialog = 
											new AlertDialog.Builder(context);
									LayoutInflater inflater = ((Activity) context).getLayoutInflater();

									courierRateDialog.setTitle("Please rate the courier job");
									// Inflate and set the layout for the dialog Pass null as the parent 
									// view because its going in the dialog layout
									courierRateDialog
											.setView(inflater.inflate(R.layout.dialog_rate_courier, null))
											// Add action buttons
											.setPositiveButton(
													"Rate it!",
													new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog, int id) {
															// TODO: Add rate to the database
														}
													})
											.setNegativeButton(
													"Cancel",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog, int id) {
														}
													});
									courierRateDialog.show();
									break;
								case 1:
									break;
								default:
									throw new UnsupportedOperationException(
											"This is a Yes/No dialog, " +
											"you weren't supposed to be here!");
								}
								//Say that data changed!
								notifyDataSetChanged();
							}
						});
				}

				optionDialog.show();
			}
		};

		return handler;
	}

}