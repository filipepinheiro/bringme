package pt.ua.icm.bringme;

import java.util.Calendar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class DeliveryRequestNotification extends NotificationCompat {

	private final static int mId = 200;
	private final static int smallIcon = R.drawable.box_icon;
	
	public DeliveryRequestNotification() {
		// TODO Auto-generated constructor stub
	}
	
	public static void sendNotification(Context context){
		//Create a notification builder
		NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
		notification.setSmallIcon(smallIcon);
		notification.setContentTitle("Package Delivery Request");
		notification.setContentText("[User] requested you to pick his delivery at [address]");
		notification.setWhen(Calendar.getInstance().getTimeInMillis());
		//When the user opens the notification it will close
		notification.setAutoCancel(true);
		
		Intent answerIntent = new Intent(context, MainMenuActivity.class);
		
		android.support.v4.app.TaskStackBuilder taskBuilder = android.support.v4.app.TaskStackBuilder.create(context);
		taskBuilder.addParentStack(MainMenuActivity.class);
		taskBuilder.addNextIntent(answerIntent);
		PendingIntent resultPendingIntent = taskBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(mId, notification.build());
	}

}
