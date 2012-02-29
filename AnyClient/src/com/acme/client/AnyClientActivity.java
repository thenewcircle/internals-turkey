package com.acme.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.turkcell.tc.common.ITCService;
import com.turkcell.tc.common.ITCServiceListener;
import com.turkcell.tc.common.TCResponse;

public class AnyClientActivity extends Activity {
	static final String TAG = "AnyClientActivity";
	EditText in;
	TextView out;
	ITCService service;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		in = (EditText) findViewById(R.id.in);
		out = (TextView) findViewById(R.id.out);

		// Bind to the service
		boolean isBound = bindService(new Intent(
				"com.turkcell.tc.common.ITCService").putExtra("caller", TAG),
				new ITCServiceConnection(), Context.BIND_AUTO_CREATE);
		Log.d(TAG, "onCreate isBound: " + isBound);
	}

	/** Called when Execute button is clicked. */
	public void onExecute(View v) {
		String command = in.getText().toString();

		// Call remote service
		try {
			service.executeAsync(command, new TCServiceListner() );
		} catch (RemoteException e) {
			Log.e(TAG, "Failed to execute: " + command, e);
		}
	}

	public void onClear(View v) {
		in.setText("");
		out.setText("");
	}

	/** Responsible for handling the service responses.
	 * Runs on UI thread.
	 */
	final Handler responseHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			out.append( (String)msg.obj);
		}		
	};
	
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

	/** Listener for callbacks from the ITCService. */
	class TCServiceListner extends ITCServiceListener.Stub {

		public void onResponse(TCResponse response) throws RemoteException {
			Message msg = new Message();
			msg.obj = "\n" + response.getLine();
			responseHandler.sendMessage(msg);
		}

		public void onError(TCResponse response) throws RemoteException {
			Message msg = new Message();
			msg.obj = "\n" + response.getLine();
			responseHandler.sendMessage(msg);
		}		
	}
}