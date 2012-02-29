package com.acme.client;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.turkcell.tc.common.ITCServiceListener;
import com.turkcell.tc.common.TCManager;
import com.turkcell.tc.common.TCResponse;

public class AnyClientActivity extends Activity {
	static final String TAG = "AnyClientActivity";
	EditText in;
	TextView out;
	TCManager manager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		in = (EditText) findViewById(R.id.in);
		out = (TextView) findViewById(R.id.out);
	}

	@Override
	protected void onStart() {
		super.onStart();
		manager = TCManager.getInstance(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		manager.release();
	}

	/** Called when Execute button is clicked. */
	public void onExecute(View v) {
		String command = in.getText().toString();

		// Call service
		manager.executeAsync(command, new TCServiceListner());
	}

	public void onClear(View v) {
		in.setText("");
		out.setText("");
	}

	/**
	 * Responsible for handling the service responses. Runs on UI thread.
	 */
	final Handler responseHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			out.append((String) msg.obj);
		}
	};

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