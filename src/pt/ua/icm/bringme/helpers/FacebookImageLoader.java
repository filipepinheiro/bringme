package pt.ua.icm.bringme.helpers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
		
		Log.d("FKKK", params[0].toString());
		
		URL imageURL;
		try {
			imageURL = new URL("https://graph.facebook.com/" + params[0]
					+ "/picture?type=large");
			bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
		
		/*try{
			URL url = new URL(params[0]);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        myBitmap = BitmapFactory.decodeStream(input);
		}catch(IOException e){
			//TODO: Handle error
			e.printStackTrace();
		}
		
		return myBitmap;
		*/
        
	}

}
