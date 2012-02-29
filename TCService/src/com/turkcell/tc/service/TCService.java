package com.turkcell.tc.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TCService extends Service {
	static final String TAG = "TCService";

	@Override
	public IBinder onBind(Intent intent) {
		return new ITCServiceImpl();
	}
}

