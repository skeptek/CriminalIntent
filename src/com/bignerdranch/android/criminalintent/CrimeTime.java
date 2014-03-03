package com.bignerdranch.android.criminalintent;

public class CrimeTime {
	private int mHour;
	private int mMinute;
	
	public CrimeTime(int hour, int minute) {
		setHour(hour);
		setMinute(minute);
	}

	public int getHour() {
		return mHour;
	}

	public void setHour(int hour) {
		mHour = hour;
	}

	public int getMinute() {
		return mMinute;
	}

	public void setMinute(int minute) {
		mMinute = minute;
	}
	
	
}
