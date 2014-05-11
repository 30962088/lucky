package com.mengle.lucky.fragments;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.mengle.lucky.NotifyDetailActivity;
import com.mengle.lucky.adapter.MsgListAdapter;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.NoticeGetRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.Notice;
import com.mengle.lucky.utils.Preferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NofityFragment extends Fragment implements OnItemClickListener{

	public static NofityFragment newInstance(){
		return new NofityFragment();
	}
	
	private ListView listView;
	
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
		list = Notice.getModelList(getActivity());
		adapter = new MsgListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		try {
			request();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void request() throws SQLException{
		final Dao<Notice, Integer> dao = new DataBaseHelper(getActivity()).getNoticeDao();
		Preferences.User user = new Preferences.User(getActivity());
		final NoticeGetRequest noticeGetRequest = new NoticeGetRequest(new NoticeGetRequest.Params(user.getUid(), user.getToken()));
		RequestAsync.request(noticeGetRequest, new Async() {
			
			public void onPostExecute(Request request) {
				for(Notice notice : noticeGetRequest.getNotices()){
					list.add(0, notice.toModel());
					try {
						dao.create(notice);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MsgListAdapter.Msg msg = list.get(position);
		NotifyDetailActivity.open(getActivity(), msg.getId());
		
	}
	
}
