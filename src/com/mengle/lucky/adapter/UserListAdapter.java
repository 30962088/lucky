package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.wiget.TranslateRelativeLayout.ExpanedListener;
import com.mengle.lucky.wiget.UserItemView;

import com.mengle.lucky.wiget.UserItemView.Model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class UserListAdapter extends BaseAdapter {

	private Context context;
	
	private List<UserItemView.Model> list;
	

	
	public UserListAdapter(Context context, List<Model> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Model model = list.get(position);
		UserItemView itemView = (UserItemView) convertView;
		if(itemView == null){
			itemView = new UserItemView(context);
			

		}
		
		itemView.setExpanedListener(new ExpanedListener() {
			
			@Override
			public void change(boolean expand) {
				
				model.setExpand(expand);
				
			}
		});
		
		itemView.setExpand(model.isExpand());
		
		
		itemView.setModel(model);
		
		return itemView;
	}



}
