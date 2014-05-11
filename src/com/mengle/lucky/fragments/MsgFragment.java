package com.mengle.lucky.fragments;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.mengle.lucky.adapter.MsgListAdapter;
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
import android.widget.ListView;

public class MsgFragment extends Fragment{

	public static MsgFragment newInstance(){
		return new MsgFragment();
	}
	
	private ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listView = new ListView(getActivity());
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
	
	
	private void request() throws SQLException{
		final Dao<Msg, Integer> dao = new DataBaseHelper(getActivity()).getMsgDao();
		Preferences.User user = new Preferences.User(getActivity());
		final MsgGetRequest noticeGetRequest = new MsgGetRequest(new MsgGetRequest.Params(user.getUid(), user.getToken()));
		RequestAsync.request(noticeGetRequest, new Async() {
			
			public void onPostExecute(Request request) {
				for(Msg msg : noticeGetRequest.getMsgList()){
					list.add(0, msg.toModel());
					try {
						dao.create(msg);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
	}
	
}
