package com.turkcell.tc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;

import com.turkcell.tc.common.ITCService;
import com.turkcell.tc.common.ITCServiceListener;
import com.turkcell.tc.common.TCResponse;

public class ITCServiceImpl extends ITCService.Stub {
	static final String TAG = "ITCServiceImpl";
	static final String ERROR = "ERROR";
	Context context;

	public ITCServiceImpl(Context context) {
		this.context = context;
	}

	public String executeCommand(String command) throws RemoteException {
		try {
			StringBuffer ret = new StringBuffer();
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				ret.append(line + "\n");
				Log.d(TAG, "reading line: " + line);
			}
			Log.d(TAG, "executeCommand returning: " + ret.toString());
			return ret.toString();
		} catch (IOException e) {
			String message = "Failed to execute command: " + command;
			Log.e(TAG, message, e);
			return message;
		}
	}

	public void executeAsync(String command, ITCServiceListener listener)
			throws RemoteException {

		// Enforce permissions
		if (context
				.checkCallingOrSelfPermission("com.turkcell.tc.permission.ACCESS_SERVICE_ASYNC") != PackageManager.PERMISSION_GRANTED) {
			SecurityException e = new SecurityException(
					"You need com.turkcell.tc.permission.ACCESS_SERVICE_ASYNC to access this feature");
			Log.e(TAG, "SecurityException", e);
			throw e;
		}

		try {
			Log.d(TAG, "executeAsync with thread id: "
					+ Thread.currentThread().getId());
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			TCResponse response;
			while ((line = in.readLine()) != null) {
				response = new TCResponse(System.currentTimeMillis(), TAG, line);
				// Pass the data back to the listener
				listener.onResponse(response);
			}
			Log.d(TAG, "executeCommand done");
		} catch (IOException e) {
			String message = "Failed to execute command: " + command;
			Log.e(TAG, message, e);
			listener.onError(new TCResponse(System.currentTimeMillis(), ERROR,
					message));
		}
	}
}
