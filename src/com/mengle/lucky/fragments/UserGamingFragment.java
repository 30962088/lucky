package com.mengle.lucky.fragments;



import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.Row2ListAdapter;
import com.mengle.lucky.adapter.Row2ListAdapter.Row2;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.UserGamingRequest;
import com.mengle.lucky.network.UserGamingRequest.Params;
import com.mengle.lucky.network.model.GameLite;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.wiget.BaseListView;
import com.mengle.lucky.wiget.BaseListView.OnLoadListener;



import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class UserGamingFragment extends Fragment implements OnLoadListener,OnClickListener{

	
	private BaseListView baseListView;
	
	private TextView emptytip;
	
	private List<Row2ListAdapter.Row2> list = new ArrayList<Row2ListAdapter.Row2>();
	
	private Row2ListAdapter listAdapter;
	
	private int uid;
	
	public static UserGamingFragment newInstance(int uid){
		Bundle args = new Bundle();
		args.putInt("uid", uid);
		UserGamingFragment fragment = new UserGamingFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	public UserGamingFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		
		return inflater.inflate(R.layout.common_listview, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		uid = getArguments().getInt("uid");
		emptytip = (TextView) view.findViewById(R.id.emptytip);
		emptytip.setVisibility(View.GONE);
		emptytip.setOnClickListener(this);
		baseListView = (BaseListView) view.findViewById(R.id.listview);
		baseListView.setOnLoadListener(this);
		listAdapter = new Row2ListAdapter(getActivity(), list);
		baseListView.setAdapter(listAdapter);
		
		
	}
	
	private Preferences.User user;
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user = new Preferences.User(getActivity());
		baseListView.load(true);
	}
	
	
	
	private int offset = 0;



	public boolean onLoad(int offset, int limit) {
		boolean hasMore = false;
		this.offset = offset;
		UserGamingRequest request = new UserGamingRequest(new Params(uid, user.getToken(), offset, limit));
		request.request();
		if(offset == 0){
			list.clear();
			list.add(new Row2(-1, "结束时间", "竞猜名称"));
		}
		if(request.getStatus() == Request.Status.SUCCESS){
			List<GameLite> list = request.getGameLite();
			for(GameLite gameLite : list){
				this.list.add(gameLite.toRow2());
			}
			if(list.size() >= limit){
				hasMore = true;
			}
		}
		
		
		
		
		return hasMore;
	}



	public void onLoadSuccess() {
		if(offset == 0&&list.size() == 1){
			baseListView.setVisibility(View.GONE);
			if(user.getUid() == uid){
				emptytip.setText("您目前没有发起任何竞猜，快去试试吧！");
				emptytip.setEnabled(true);
			}else{
				emptytip.setText("他目前没有参与任何竞猜！");
				emptytip.setEnabled(false);
			}
			emptytip.setVisibility(View.VISIBLE);
		}
		listAdapter.notifyDataSetChanged();
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.emptytip:
			
			break;

		default:
			break;
		}
		
	}

}
