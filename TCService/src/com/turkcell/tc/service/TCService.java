package com.turkcell.tc.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TCService extends Service {
	static final String TAG = "TCService";

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind from: "+intent.getStringExtra("caller"));
		return new ITCServiceImpl(this);
	}
}

