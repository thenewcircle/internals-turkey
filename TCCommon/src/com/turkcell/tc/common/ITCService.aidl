package com.turkcell.tc.common;

import com.turkcell.tc.common.ITCServiceListener;

interface ITCService {
	String executeCommand(String command);
	oneway void executeAsync(String command, ITCServiceListener listener);
}