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
		adapter = new ZhuangListAdapter(getContext(), list1);
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
	
	private List<ZhuangModel> list1 = new ArrayList<ZhuangModel>();
	
	private List<Result> results;
	
	private int offset;

	@Override
	public boolean onLoad(int offset, int limit) {
		this.offset = offset;
		boolean hasMore = true;
		request.request(offset, limit);
		results =  request.getResult();
		if(results.size()<limit){
			hasMore = false;
		}
		
		return hasMore;
	}

	@Override
	public void onLoadSuccess() {
		if(offset == 0){
			adapter.reset();
			this.list1.clear();
		}
		this.list1.addAll(Result.toZhuangModelList(results));
		adapter.notifyDataSetChanged();
		
	}
	
	

	
	
}
