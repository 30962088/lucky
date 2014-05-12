package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShitiListAdapter extends BaseAdapter{

	public static class Shiti{
		private int id;
		private String title;
		public Shiti(int id,  String title) {
			super();
			this.id = id;
			this.title = title;
		}
		
		public int getId() {
			return id;
		}
		
	}
	
	public static class ShitiList{
		private Integer index;
		private List<Shiti> list;
		public ShitiList(List<Shiti> list) {
			super();
			this.list = list;
		}
		
		public void setIndex(Integer index) {
			this.index = index;
		}
		
		
	}
	
	private Context context;
	
	private ShitiList list;

	public ShitiListAdapter(Context context, ShitiList list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	private static final String[] NOLIST = new String[]{"A","B","C","D","E","F","G"};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Shiti shiti = list.list.get(position);
		ViewHolder holder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(
					R.layout.shiti_item_layout, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.noView.setText(NOLIST[position]);
		holder.contentView.setText(shiti.title);
		if(list.index == null){
			convertView.setSelected(true);
		}else{
			if(list.index == position){
				convertView.setSelected(true);
			}else{
				convertView.setSelected(false);
			}
		}
		
		return convertView;
	}
	
	public static class ViewHolder{
		private TextView noView;
		private TextView contentView;
		public ViewHolder(View view) {
			noView = (TextView) view.findViewById(R.id.no);
			contentView = (TextView) view.findViewById(R.id.content);
		}
	}
	
	
	
	
}
