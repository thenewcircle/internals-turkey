package com.turkcell.tc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.RemoteException;
import android.util.Log;

import com.turkcell.tc.common.ITCService;

public class ITCServiceImpl extends ITCService.Stub {
	static final String TAG = "ITCServiceImpl";

	public String executeCommand(String command) throws RemoteException {
		try {
			StringBuffer ret = new StringBuffer();
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				ret.append(line+"\n");
				Log.d(TAG, "reading line: "+line);
			}
			Log.d(TAG, "executeCommand returning: "+ret.toString());
			return ret.toString();
		} catch (IOException e) {
			String message = "Failed to execute command: " + command;
			Log.e(TAG, message, e);
			return message;
		}
	}
}
