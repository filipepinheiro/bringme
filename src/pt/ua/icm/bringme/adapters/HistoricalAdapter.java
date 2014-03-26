package pt.ua.icm.bringme.adapters;

import java.util.LinkedList;
import java.util.List;

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

	List<Delivery> deliveryRequestList = new LinkedList<Delivery>();

	public HistoricalAdapter(Context context, List<Delivery> deliveryRequestList) {
		this.deliveryRequestList = deliveryRequestList;
		inflater = 
				(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		
		TextView orderTagText = (TextView) convertView.findViewById(R.id.deliveryRequestOrderTag);
			orderTagText.setText(deliveryRequest.getOrder().getTag());

		TextView sourceAddressText = (TextView) convertView
				.findViewById(R.id.deliveryRequestSourceAddress);
		sourceAddressText.setText(deliveryRequest.getOriginAddress());
		
		TextView targetAddressText = (TextView) convertView
				.findViewById(R.id.deliveryRequestTargetAddress);
		targetAddressText.setText(deliveryRequest.getDestinationAddress());
		
		TextView deliveryStatusText = (TextView) convertView.
				findViewById(R.id.deliveryRequestStatus);
		if(deliveryRequest.isFinished()){
			deliveryStatusText.setText("Finished");
		}

		return convertView;
	}
}