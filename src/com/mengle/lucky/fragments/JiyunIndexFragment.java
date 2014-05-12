package com.mengle.lucky.fragments;

import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.wiget.JiyunIntroDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class JiyunIndexFragment extends Fragment implements OnClickListener{
	
	public static JiyunIndexFragment newInstance(){
		return new JiyunIndexFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.jiyunbao_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.guize).setOnClickListener(this);
		view.findViewById(R.id.btn_play).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guize:
			JiyunIntroDialog.open(getActivity());
			break;
		case R.id.btn_play:
			((MainActivity)getActivity()).switchContent(ShitiFragment.newInstance());
			break;
		default:
			break;
		}
		
	}

}
