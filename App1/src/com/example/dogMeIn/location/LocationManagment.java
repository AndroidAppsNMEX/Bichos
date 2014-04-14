package com.example.dogMeIn.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class LocationManagment implements LocationListenerDogMeIn {

	private GoogleMap googleMap;
	private Context mContext;
	private LocationClient mLocationClient;

	public LocationManagment(GoogleMap map, Context context) {
		googleMap = map;
		mContext = context;
		mLocationClient = new LocationClient(mContext, this, this);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "Connected", Toast.LENGTH_SHORT).show();
		Location location = mLocationClient.getLastLocation();
		LatLng latLng = new LatLng(location.getLatitude(),
				location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
				17);
		googleMap.animateCamera(cameraUpdate);
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public LocationClient getLocatioClient() {
		// TODO Auto-generated method stub
		return mLocationClient;
	}

}
