package com.turkcell.tc.service;

import java.io.IOException;

import android.os.RemoteException;
import android.util.Log;

import com.turkcell.tc.common.ITCService;

public class ITCServiceImpl extends ITCService.Stub {
	static final String TAG = "ITCServiceImpl";
	
	public void executeCommand(String command) throws RemoteException {
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			Log.e(TAG, "Failed to execute command: " + command, e);
		}
	}

}
