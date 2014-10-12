package com.mengle.lucky.fragments;

import java.sql.SQLException;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.j256.ormlite.dao.Dao;
import com.mengle.lucky.ChatActivity;
import com.mengle.lucky.adapter.MsgListAdapter;
import com.mengle.lucky.adapter.MsgListAdapter.Message;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.MsgGetRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.Msg;
import com.mengle.lucky.utils.Preferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MsgFragment extends Fragment implements OnItemClickListener,OnRefreshListener<ListView>{

	public static MsgFragment newInstance(View newMsg){
		MsgFragment fragment =  new MsgFragment();
		fragment.newMsg = newMsg;
		return fragment;
	}
	
	private PullToRefreshListView listView;
	
	private DataBaseHelper helper;
	
	private View newMsg;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		helper = new DataBaseHelper(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listView = new PullToRefreshListView(getActivity());
		listView.setOnItemClickListener(this);
		listView.setOnRefreshListener(this);
		listView.getRefreshableView().setDivider(null);
		return listView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		
		
	}
	
	private List<MsgListAdapter.Msg> list;
	
	private MsgListAdapter adapter;
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		checkNew();
		list = Msg.getModelList(getActivity());
		adapter = new MsgListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		try {
			request();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void checkNew(){
		if(Msg.getNewCount(getActivity())>0){
			newMsg.setVisibility(View.VISIBLE);
		}else{
			newMsg.setVisibility(View.GONE);
		}
	}
	
	
	public void request() throws SQLException{
		final Dao<Msg, Integer> dao = new DataBaseHelper(getActivity()).getMsgDao();
		Preferences.User user = new Preferences.User(getActivity());
		final MsgGetRequest noticeGetRequest = new MsgGetRequest(new MsgGetRequest.Params(user.getUid(), user.getToken()));
		RequestAsync.request(noticeGetRequest, new Async() {
			
			public void onPostExecute(Request request) {
				for(Msg msg : noticeGetRequest.getMsgList()){
					list.add(0, msg.toModel());
					
				}
				adapter.notifyDataSetChanged();
				checkNew();
				listView.onRefreshComplete();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MsgListAdapter.Message msg = (Message) list.get(position-1);
		msg.setChecked(false);
		try {
			helper.getMsgDao().executeRaw("update msg set checked=0 where sender like '{\"uid\":\""+msg.getUid()+"\"%';");
			adapter.notifyDataSetChanged();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ChatActivity.open(getActivity(), msg.getUid());
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		try {
			request();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
