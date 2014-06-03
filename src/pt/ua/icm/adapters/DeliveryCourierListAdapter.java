package pt.ua.icm.adapters;

import java.util.LinkedList;
import java.util.List;

import pt.ua.icm.bringme.R;

import com.parse.ParseUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeliveryCourierListAdapter extends BaseAdapter {
	
	private List<ParseUser> courierList = new LinkedList<ParseUser>();
	private Context context;

	public DeliveryCourierListAdapter(List<ParseUser> parseUserList, Context context) {
		this.courierList = parseUserList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return courierList.size();
	}

	@Override
	public Object getItem(int position) {
		return courierList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.request_delivery_courier_item, parent, false);
	    TextView name = (TextView) rowView.findViewById(R.id.courierListItemName);
	    TextView rating = (TextView) rowView.findViewById(R.id.courierListItemRating);
	    
	    ParseUser courier = courierList.get(position);
	    name.setText(courier.getString("firstName") + " " + courier.getString("lastName"));
	    
		return rowView;
	}

}
