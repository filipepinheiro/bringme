package pt.ua.icm.adapters;

import java.util.concurrent.ExecutionException;

import pt.ua.icm.bringme.Consts;
import pt.ua.icm.bringme.R;
import pt.ua.icm.bringme.helpers.BitmapHelper;
import pt.ua.icm.bringme.helpers.FacebookImageLoader;
import pt.ua.icm.bringme.helpers.RoundedImageView;
import pt.ua.icm.bringme.models.Delivery;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.CalendarContract.Colors;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	// private final String[] values;
	private Delivery[] deliveries;

	public MyArrayAdapter(Context context, Delivery[] deliveries,
			String[] values) {
		super(context, R.layout.adapter_row, values);
		this.context = context;
		// this.values = values;
		this.deliveries = deliveries;

	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.w("ADAPTER", "getView");

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.adapter_row, parent, false);

		TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitulo);
		TextView tvDescription = (TextView) rowView
				.findViewById(R.id.tvDescription);
		RoundedImageView riv = (RoundedImageView) rowView
				.findViewById(R.id.userImageViewAdapter);
		riv.setBorderColor(R.color.green_peas);
		
		
		if (position < deliveries.length) {
			String fbId = deliveries[position].facebookId;

			if (fbId != null) {

				FacebookImageLoader profilePictureLoader = new FacebookImageLoader();

				Bitmap profilePicture = null;

				try {

					profilePicture = profilePictureLoader
							.execute(fbId, "small").get();

				} catch (InterruptedException e) {
					Log.e(Consts.TAG, "Load profile picture was cancelled!");
					e.printStackTrace();
				} catch (ExecutionException e) {
					Log.e(Consts.TAG, "Execution Error!");
					e.printStackTrace();
				}

				if (profilePicture != null) {
					riv.setImageBitmap(profilePicture);
				} else {
					Bitmap defaultPicture = BitmapHelper.drawableToBitmap(R.drawable.default_profile_picture,  getContext());
					riv.setImageBitmap(defaultPicture);
					riv.setBorderColor(Color.parseColor(getString(R.color.green_peas)));
				}
			} else {
				Bitmap defaultPicture = BitmapHelper.drawableToBitmap(R.drawable.default_profile_picture,  getContext());
				riv.setImageBitmap(defaultPicture);
				// riv.setBorderColor(Color.parseColor(getString(R.color.green_peas)));

			}

			tvTitle.setText(deliveries[position].name.toString());
			tvDescription.setText(deliveries[position].description.toString());
			// riv.setImageBitmap(defaultPicture);
		}

		return rowView;

	}

	private String getString(int value) {
		// TODO Auto-generated method stub
		return ""+value;
	}

}
