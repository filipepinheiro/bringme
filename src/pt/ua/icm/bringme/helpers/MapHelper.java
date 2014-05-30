package pt.ua.icm.bringme.helpers;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapHelper {

	public MapHelper() {}
	
	public static void updateMapCamera(GoogleMap map, LatLng target){
		float zoom = 14;
		float bearing = 0;
		float tilt = 0;
		CameraPosition cameraPosition = new CameraPosition(target, zoom, tilt, bearing);
		map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

}
