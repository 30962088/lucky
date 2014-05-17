package com.mengle.lucky.wiget;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.adapter.ZhuangListAdapter;
import com.mengle.lucky.adapter.ZhuangListAdapter.ZhuangModel;
import com.mengle.lucky.network.IGameGet;
import com.mengle.lucky.network.IGameGet.Result;
import com.mengle.lucky.wiget.BaseListView.OnLoadListener;

import android.content.Context;
import android.util.AttributeSet;

public class GameListView extends BaseListView implements OnLoadListener{

	
	
	
	
	public GameListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GameListView(
			Context context,
			com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode,
			com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle style) {
		super(context, mode, style);
		init();
	}

	public GameListView(Context context,
			com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode) {
		super(context, mode);
		init();
	}

	public GameListView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		setOnLoadListener(this);
		adapter = new ZhuangListAdapter(getContext(), list);
		setAdapter(adapter);
	}
	
	private IGameGet request;
	
	public void setRequest(IGameGet request){
		this.request = request;
	}


	
	@Override
	public void setLimit(int limit) {
		// TODO Auto-generated method stub
		super.setLimit(limit);
	}
	
	private ZhuangListAdapter adapter;
	
	private List<ZhuangModel> list = new ArrayList<ZhuangModel>();
	
	

	@Override
	public boolean onLoad(int offset, int limit) {
		boolean hasMore = true;
		request.request(offset, limit);
		List<Result> list =  request.getResult();
		if(list.size()<limit){
			hasMore = false;
		}
		if(offset == 0){
			this.list.clear();
		}
		this.list.addAll(Result.toZhuangModelList(list));
		return hasMore;
	}

	@Override
	public void onLoadSuccess() {
		adapter.notifyDataSetChanged();
		
	}
	
	

	
	
}
