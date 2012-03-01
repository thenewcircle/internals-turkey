package com.turkcell.tc.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class TCManager {
	static final String TAG = "TCManager";
	public static final String ITCSERVICE_ACTION = "com.turkcell.tc.common.ITCService";
	Context context;
	ITCService service;
	ITCServiceConnection serviceConnection;
	static TCManager manager;

	private TCManager(Context context) {
		this.context = context;
		serviceConnection = new ITCServiceConnection();
		
		// Bind to the service
		boolean isBound = context.bindService(
				new Intent(ITCSERVICE_ACTION).putExtra("caller", TAG),
				serviceConnection, Context.BIND_AUTO_CREATE);
		Log.d(TAG, "TCManager isBound: " + isBound);
	}

	/** Factory method. */
	public static TCManager getInstance(Context context) {
		if (manager == null) {
			manager = new TCManager(context);
		}
		return manager;
	}
	
	/** UNbinds the underlying service. */
	public void release() {
		context.unbindService(serviceConnection);
	}
	
	//--- Proxy calls ---
	public void executeAsync(String command, ITCServiceListener listener) {
		try {
			service.executeAsync(command, listener);
		} catch (RemoteException e) {
			Log.e(TAG, "Failed to execute: " + command, e);
		}
	}

	public void executeCommand(String command) {
		try {
			service.executeCommand(command);
		} catch (RemoteException e) {
			Log.e(TAG, "Failed to execute: " + command, e);
		}
	}
	
	/** Responsible for handling the connection/disconnection from ITCService. */
	class ITCServiceConnection implements ServiceConnection {

		public void onServiceConnected(ComponentName name, IBinder binder) {
			service = ITCService.Stub.asInterface(binder);
			Log.d(TAG, "onServiceConnected");
		}

		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "onServiceDisconnected");
			service = null;
		}
	}

}
