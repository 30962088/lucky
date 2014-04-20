package com.mengle.lucky.fragments;

import java.util.ArrayList;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.CommentListAdapter;
import com.mengle.lucky.adapter.CommentModel;
import com.mengle.lucky.adapter.CommentModel.Reply;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CommentFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.comment_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ListView listView = (ListView) view.findViewById(R.id.listview);
		listView.setAdapter(new CommentListAdapter(getActivity(), new ArrayList<CommentModel>(){{
			add(new CommentModel("http://tp3.sinaimg.cn/1795737590/180/0/1", "威廉萌", "02-07 10:20", new ArrayList<CommentModel.Reply>(){{
				add(new Reply(null, null, "哇哈哈"));
				add(new Reply("123", "王玥", "哇哈哈"));
			}}));
		}}));
	}
	
}
