package com.mengle.lucky.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BlurFragment extends Fragment{
	
	
	public abstract void setBlur(boolean blur);

	public abstract void release();
	
}
