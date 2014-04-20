
package com.mengle.lucky.fragments;
import java.util.ArrayList;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.StageAdapter;
import com.mengle.lucky.adapter.ZhuangListAdapter;
import com.mengle.lucky.adapter.StageAdapter.Stage;
import com.mengle.lucky.adapter.ZhuangListAdapter.ZhuangModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ZhuangFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.zhuang_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ListView listView = (ListView) getView().findViewById(R.id.listview);
		View headView = View.inflate(getActivity(), R.layout.zhuang_header, null);
		listView.addHeaderView(headView);
		listView.setAdapter(new ZhuangListAdapter(getActivity(), new ArrayList<ZhuangListAdapter.ZhuangModel>(){{
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1,"2222"));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
			add(new ZhuangModel("1", "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", 1));
		}}));
		StageFragment stageFragment = new StageFragment();
		stageFragment.setAdapter(new StageAdapter(getChildFragmentManager(), new ArrayList<StageAdapter.Stage>(){{
			add(new Stage("世界杯竞猜","http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", "23",55));
			add(new Stage("世界杯竞猜","http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", "23",55));
			add(new Stage("世界杯竞猜","http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", "23",55));
			add(new Stage("世界杯竞猜","http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg", "23",55));
		}}));
		getFragmentManager().beginTransaction().add(R.id.stage_fragment,stageFragment ).commit();
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	
}
