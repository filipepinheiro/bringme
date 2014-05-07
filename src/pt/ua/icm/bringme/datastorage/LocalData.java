package pt.ua.icm.bringme.datastorage;

import pt.ua.icm.bringme.RequestDeliveryCourierFragment;
import pt.ua.icm.bringme.RequestDeliveryOrderDetailsFragment;
import pt.ua.icm.bringme.RequestDeliverySourceFragment;
import pt.ua.icm.bringme.RequestDeliveryTargetFragment;
import pt.ua.icm.bringme.models.User;

public class LocalData {

	public static User currentUser = null;
	public static RequestDeliveryOrderDetailsFragment orderFragment;
	public static RequestDeliverySourceFragment sourceFragment;
	public static RequestDeliveryTargetFragment targetFragment;
	public static RequestDeliveryCourierFragment courierFragment;

}
