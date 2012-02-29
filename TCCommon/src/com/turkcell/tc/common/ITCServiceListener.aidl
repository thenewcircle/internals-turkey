package com.turkcell.tc.common;

import com.turkcell.tc.common.TCResponse;

oneway interface ITCServiceListener {
	void onResponse(in TCResponse response );
	void onError(in TCResponse error );
}