package com.turkcell.fib;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FibActivity extends Activity {
	EditText input;
	TextView output;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		// Find views
		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);
	}

	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		// Recursive
		long start = System.currentTimeMillis();
		long fibJ = FibLib.fibJ(n);
		long timeJ = System.currentTimeMillis() - start;
		output.append(String.format("\nfibJ(%d)=%d (%d ms)", n, fibJ, timeJ));

		start = System.currentTimeMillis();
		long fibN = FibLib.fibN(n);
		long timeN = System.currentTimeMillis() - start;
		output.append(String.format("\nfibN(%d)=%d (%d ms)", n, fibN, timeN));
		/*
		 * // Iterative start = System.nanoTime(); long fibJI = FibLib.fibJI(n);
		 * long timeJI = System.nanoTime() - start; output.append(
		 * String.format("\nfibJI(%d)=%d (%d ns)", n, fibJI, timeJI ) );
		 * 
		 * start = System.nanoTime(); long fibNI = FibLib.fibNI(n); long timeNI
		 * = System.nanoTime() - start; output.append(
		 * String.format("\nfibNI(%d)=%d (%d ms)", n, fibNI, timeNI ) );
		 */
	}
}