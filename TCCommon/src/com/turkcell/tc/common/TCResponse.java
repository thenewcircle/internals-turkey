package com.turkcell.tc.common;

import android.os.Parcel;
import android.os.Parcelable;

public class TCResponse implements Parcelable {
	private long timestamp;
	private String tag;
	private String line;

	/** Helper constructor */
	public TCResponse(long timestamp, String tag, String line) {
		this.timestamp = timestamp;
		this.tag = tag;
		this.line = line;
	}
	
	/** Unmarshals the parcel. */
	public TCResponse(Parcel source) {
		timestamp = source.readLong();
		tag = source.readString();
		line = source.readString();
	}

	/** Marshals into parcel. */
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(timestamp);
		dest.writeString(tag);
		dest.writeString(line);
	}

	public static final Parcelable.Creator<TCResponse> CREATOR = new Parcelable.Creator<TCResponse>() {

		public TCResponse createFromParcel(Parcel source) {
			return new TCResponse(source);
		}

		public TCResponse[] newArray(int size) {
			return new TCResponse[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	// --- Getters and Setters ---
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
}
