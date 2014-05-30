package pt.ua.icm.bringme.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BitmapHelper {

	/**
	 * Function that converts bitmap into circular images
	 * http://ruibm.com/2009/06/16/rounded-corner-bitmaps-on-android/
	 */
	public BitmapHelper() {}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	        bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	 
	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	    final float roundPx = 12;
	 
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	 
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	 
	    return output;
	}
	
	public static Bitmap drawableToBitmap(int drawable, Context context){
		Bitmap output = null;
		output = BitmapFactory.decodeResource(context.getResources(),
                drawable);
		return output;
	}
	
	public static Bitmap byteArrayToBitmap(byte[] byteArray){
		Bitmap output = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		return output;
	}

}
