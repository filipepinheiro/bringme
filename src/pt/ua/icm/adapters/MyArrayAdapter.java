package pt.ua.icm.adapters;


import pt.ua.icm.bringme.R;
import pt.ua.icm.bringme.models.Delivery;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
	  private final Context context;
	  //private final String[] values;
	  private Delivery[] deliveries;
	  
	  public MyArrayAdapter (Context context,Delivery[] deliveries, String[] values) {
	    super(context, R.layout.adapter_row, values);
	    this.context = context;
	    //this.values = values;
	    this.deliveries = deliveries;
	    
	    
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  Log.w("ADAPTER","getView");
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
	    View rowView = inflater.inflate(R.layout.adapter_row, parent, false);
	    
	    TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitulo);
	    TextView tvDescription= (TextView) rowView.findViewById(R.id.tvDescription);
	    
	    Log.w("Laver",""+position);
	    
	    if(position<deliveries.length)
	    {
	    	tvTitle.setText(deliveries[position].name.toString());
	    	tvDescription.setText(deliveries[position].description.toString());

	    }		

	    return rowView;
	  }

}
