package com.mengle.lucky.fragments;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.ZoneActivity;
import com.mengle.lucky.adapter.AicaiListAdapter;
import com.mengle.lucky.adapter.AicaiListAdapter.Aicai;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserRank;
import com.mengle.lucky.network.UserRankMe;
import com.mengle.lucky.network.UserRank.Result;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.BaseListView;
import com.mengle.lucky.wiget.BaseListView.OnLoadListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AicaiFragment extends Fragment implements OnLoadListener,OnItemClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		user = new User(getActivity());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.aicai_layout, null);
	}
	
	private BaseListView listView;
	
	private List<Aicai> list = new ArrayList<AicaiListAdapter.Aicai>();
	
	private AicaiListAdapter adapter;
	
	private User user;
	
	private TextView rankView;
	
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		rankView = (TextView) view.findViewById(R.id.rank);
		listView = (BaseListView) view.findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		listView.getRefreshableView().addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.aicai_header, null));
		listView.setOnLoadListener(this);
		adapter = new AicaiListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setRefreshing(true);
		listView.load(true);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		final UserRankMe rankMe = new UserRankMe(new UserRankMe.Params(user.getUid(), user.getToken()));
		RequestAsync.request(rankMe, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				String rank = "排名太低，未入排行，加油！";
				if(rankMe.getRank() > 0){
					rank = ""+rankMe.getRank();
				}
				rankView.setText(rank);
				
			}
		});
	}

	private List<Result> results;
	
	private int offset;

	@Override
	public boolean onLoad(int offset, int limit) {
		results = null;
		this.offset = offset;
		UserRank rank = new UserRank(new UserRank.Params(user.getUid(), user.getToken(), offset,limit));
		rank.request();
		results = rank.getResults();
		
		
		return results.size()>=limit?true:false;
	}



	@Override
	public void onLoadSuccess() {
		if(offset == 0){
			list.clear();
		}
		list.addAll(Result.toAicaiList(results));
		adapter.notifyDataSetChanged();
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		position -= 2;
		if(position >=0){
			Aicai aicai = list.get(position);
			ZoneActivity.open(getActivity(), aicai.getUid());
		}
		
		
	}
	
}
