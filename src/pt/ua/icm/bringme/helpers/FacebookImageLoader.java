package pt.ua.icm.bringme.helpers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pt.ua.icm.bringme.Consts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseFacebookUtils;

public class FacebookImageLoader extends AsyncTask<String, Void, Bitmap> {

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		
		ParseFacebookUtils.initialize("635266799888180");
		
		Log.d(Consts.TAG, params[0].toString());
		
		URL imageURL;
		try {
			imageURL = new URL("https://graph.facebook.com/" + params[0]
					+ "/picture?type=normal");
			bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
        
	}

}
