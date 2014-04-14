package com.example.dogMeIn.location;

import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;

public interface LocationListenerDogMeIn extends ConnectionCallbacks,
OnConnectionFailedListener {
	LocationClient getLocatioClient();
}
