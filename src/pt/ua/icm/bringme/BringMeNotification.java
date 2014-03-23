package pt.ua.icm.bringme;

import java.util.Calendar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class BringMeNotification extends NotificationCompat {

	private final static int mId = 200;
	public final static int smallIconDeliveryRequest = R.drawable.box_icon;
	public final static int smallIconDeliveryRequestAccepted = R.drawable.box_icon_confirm;
	public final static int smallIconDeliveryRequestRejected = R.drawable.box_icon_rejected;
	
	public static void sendNotification(Context context, int icon, String title, String text, boolean autoCancel, Class<?> targetClass){
		//Create a notification builder
		NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
		notification.setSmallIcon(icon);
		notification.setContentTitle(title);
		notification.setContentText(text);
		notification.setWhen(Calendar.getInstance().getTimeInMillis());

		//When the user opens the notification it will close
		notification.setAutoCancel(autoCancel);
		
		PendingIntent resultPendingIntent;
		if(targetClass != null){
			Intent answerIntent = new Intent(context, targetClass);
			
			android.support.v4.app.TaskStackBuilder taskBuilder = android.support.v4.app.TaskStackBuilder.create(context);
			taskBuilder.addParentStack(targetClass);
			taskBuilder.addNextIntent(answerIntent);
			resultPendingIntent = taskBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		}else{
			resultPendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
		}
		
		notification.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(mId, notification.build());
	}
	
	public static void sendDeliveryRequestAccepted(Context context, String courierFullName){
		sendNotification(context, smallIconDeliveryRequestAccepted, "[Courier] Accepted your request", null, true, null);
	}
	
	public static void sendDeliveryRequestRejected(Context context, String courierFullName){
		sendNotification(context, smallIconDeliveryRequestRejected, "[Courier] Rejected your request", null, true, null);
	}

}
