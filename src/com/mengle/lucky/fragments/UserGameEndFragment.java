package com.mengle.lucky.fragments;



import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.adapter.Row2ListAdapter;
import com.mengle.lucky.adapter.Row2ListAdapter.Row2;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.UserGameEndRequest;
import com.mengle.lucky.network.UserGamingRequest;
import com.mengle.lucky.network.UserGamingRequest.Params;
import com.mengle.lucky.network.model.GameLite;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.wiget.BaseListView;
import com.mengle.lucky.wiget.MyClickSpan;
import com.mengle.lucky.wiget.BaseListView.OnLoadListener;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class UserGameEndFragment extends Fragment implements OnLoadListener,OnClickListener{

	
	private BaseListView baseListView;
	
	private TextView emptytip;
	
	private List<Row2ListAdapter.Row2> list = new ArrayList<Row2ListAdapter.Row2>();
	
	private Row2ListAdapter listAdapter;
	
	private int uid;
	
	public static UserGameEndFragment newInstance(int uid){
		Bundle args = new Bundle();
		args.putInt("uid", uid);
		UserGameEndFragment fragment = new UserGameEndFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	public UserGameEndFragment() {
		
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
		listAdapter = new Row2ListAdapter(getActivity(), list,true);
		baseListView.setAdapter(listAdapter);
		user = new Preferences.User(getActivity());
		baseListView.load(true);
		
	}
	
	
	
	private Preferences.User user;
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	
	
	private int offset = 0;
	
	private List<GameLite> results;



	public boolean onLoad(int offset, int limit) {
		boolean hasMore = false;
		this.offset = offset;
		UserGameEndRequest request = new UserGameEndRequest(new UserGameEndRequest.Params( user.getUid(),user.getToken(),uid, offset, limit));
		request.request();
		
		if(request.getStatus() == Request.Status.SUCCESS){
			results = request.getGameLite();
			
			if(results.size() >= limit){
				hasMore = true;
			}
		}
		
		
		
		
		return hasMore;
	}



	public void onLoadSuccess() {
		if(offset == 0){
			list.clear();
			list.add(new Row2(-1, "结束时间", "竞猜名称"));
		}
		
		for(GameLite gameLite : results){
			this.list.add(gameLite.toRow2());
		}
		if(offset == 0&&list.size() == 1){
			baseListView.setVisibility(View.GONE);
			if(user.getUid() == uid){
				ClickableSpan  textClickable = new MyClickSpan(Color.parseColor("#5dc9e6"),new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						MainActivity.getInstance().setSwitchFragment(ZhuangFragment.newInstance(0));
						getActivity().finish();
						
						
					}
				});
				SpannableString spanableInfo = new SpannableString("您目前没有参与任何竞猜，快去看看有没有您感兴趣的猜题吧！");
				spanableInfo.setSpan(textClickable,20, 26,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				emptytip.setText(spanableInfo);
				emptytip.setClickable(true);
				emptytip.setMovementMethod(LinkMovementMethod.getInstance());
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
