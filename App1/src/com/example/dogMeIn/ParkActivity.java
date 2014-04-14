package com.example.dogMeIn;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.dogMeIn.location.LocationListenerDogMeIn;
import com.example.dogMeIn.location.LocationManagment;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class ParkActivity extends Activity {

	private GoogleMap parkMap;
	private LocationClient mLocationClient;
	private LocationListenerDogMeIn mLocationListener;
	private Context mContext;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_park);

		parkMap = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.parkMap)).getMap();
		parkMap.setMyLocationEnabled(true);

		parkMap.getUiSettings().setZoomControlsEnabled(true);
		parkMap.getUiSettings().setCompassEnabled(true);
		parkMap.getUiSettings().setAllGesturesEnabled(true);

		mContext = getBaseContext();
		
		mLocationListener = new LocationManagment(parkMap, mContext);
		mLocationClient = mLocationListener.getLocatioClient();
	}

	public void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

}
