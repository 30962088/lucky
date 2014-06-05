package com.mengle.lucky.fragments;

import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.network.GameLibraryDayChanceRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.JiyunIntroDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class JiyunIndexFragment extends Fragment implements OnClickListener{
	
	public static JiyunIndexFragment newInstance(){
		return new JiyunIndexFragment();
	}
	
	private TextView chanceView;
	
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
		chanceView = (TextView) view.findViewById(R.id.chance);
		view.findViewById(R.id.guize).setOnClickListener(this);
		view.findViewById(R.id.btn_play).setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Preferences.User user = new Preferences.User(getActivity());
		final GameLibraryDayChanceRequest chanceRequest = new GameLibraryDayChanceRequest(new GameLibraryDayChanceRequest.Params(user.getUid(), user.getToken()));
		RequestAsync.request(chanceRequest, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				chanceView.setText(""+chanceRequest.getChange());
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guize:
			JiyunIntroDialog.open(getActivity());
			break;
		case R.id.btn_play:
			int chance = 0;
			try{
				chance = Integer.parseInt(chanceView.getText().toString());
			}catch (Exception e) {
				// TODO: handle exception
			}
			if(chance == 0){
				Utils.tip(getActivity(), "游戏次数不足");
				return;
			}
			((MainActivity)getActivity()).switchContent(ShitiFragment.newInstance(chance));
			break;
		default:
			break;
		}
		
	}

}
