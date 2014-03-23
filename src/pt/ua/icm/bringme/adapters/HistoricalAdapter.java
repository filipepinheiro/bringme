package pt.ua.icm.bringme.adapters;

import java.util.LinkedList;

import pt.ua.icm.bringme.R;
import pt.ua.icm.bringme.models.Delivery;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoricalAdapter extends BaseAdapter {
	private static LayoutInflater inflater = null;

	LinkedList<Delivery> deliveryRequestList = new LinkedList<Delivery>();

	public HistoricalAdapter(Context context, LinkedList<Delivery> deliveryRequestList) {
		this.deliveryRequestList = deliveryRequestList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = inflater.inflate(R.layout.delivery_request_list_item, null);

		Delivery deliveryRequest = getItem(position);
		
		TextView sourceAddressText = (TextView) convertView.findViewById(R.id.deliveryRequestSourceAddress);
		sourceAddressText.setText(deliveryRequest.getOriginAddress());
		TextView targetAddressText = (TextView) convertView.findViewById(R.id.deliveryRequestTargetAddress);
		targetAddressText.setText(deliveryRequest.getDestinationAddress());

		//TODO: Rebuild for future click event or delete
		//convertView.setOnClickListener(requestClickHandler(position));

		return convertView;
	}

	//TODO: Rebuild for future click event or delete
	/*public View.OnClickListener requestClickHandler(int position) {
		final Delivery delivery = deliveryRequestList.get(position);

		View.OnClickListener handler = new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				AlertDialog.Builder optionDialog = new AlertDialog.Builder(context);
				optionDialog.setTitle("Can you make this delivery for [Requestor]?");
				optionDialog.setItems(new CharSequence[] {"Accept","Decline"}, new OnClickListener(){
				TextView requestStatus = (TextView) v.findViewById(R.id.deliveryRequestStatus);
					@Override
					public void onClick(DialogInterface dialog, int position) {
						switch(position) {
						case 0:
							DeliveryRequestNotification.sendDeliveryRequestAccepted(context,"User Name");
							requestStatus.setText("Accepted");
							break;
						case 1:
							DeliveryRequestNotification.sendDeliveryRequestRejected(context,"User Name");
							requestStatus.setText("Rejected");
							break;
						default:
							throw new UnsupportedOperationException("This is a Yes/No dialog, you weren't supposed to be here!"); 
						}						
					}
					
				});
				optionDialog.show();
			}
		};

		return handler;
	}*/
	
}