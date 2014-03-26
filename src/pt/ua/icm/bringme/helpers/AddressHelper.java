package pt.ua.icm.bringme.helpers;

import android.location.Address;

public class AddressHelper {
	
	public static String getPrettyAddress(Address address){
		StringBuilder prettyAddress = new StringBuilder("");
		int maxAddressLine = address.getMaxAddressLineIndex(); 
		
		for(int i = 0;i<=maxAddressLine;i++){
			prettyAddress.append(address.getAddressLine(i));
			if(i != maxAddressLine){
				prettyAddress.append(", ");
			}
		}
		
		return prettyAddress.toString();
	}
}
