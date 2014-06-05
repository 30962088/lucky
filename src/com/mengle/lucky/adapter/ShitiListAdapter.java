package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShitiListAdapter extends BaseAdapter{

	public static class Shiti{
		private int id;
		private String title;
		private boolean c;
		public Shiti(int id,  String title,boolean c) {
			super();
			this.id = id;
			this.title = title;
			this.c = c;
		}
		
		public int getId() {
			return id;
		}
		
	}
	
	public static class ShitiList{
		private int id;
		private Integer index;
		private int c;
		private List<Shiti> list;
		public ShitiList(int id,List<Shiti> list,int c) {
			super();
			this.id = id;
			this.list = list;
			this.c = c;
		}
		
		public void setIndex(Integer index) {
			this.index = index;
		}
		public Integer getIndex() {
			return index;
		}
		
		public int getC() {
			return c;
		}
		public int getId() {
			return id;
		}
		
		public List<Shiti> getList() {
			return list;
		}
		
		
	}
	
	private Context context;
	
	private ShitiList list;

	private Typeface face ;
	
	public ShitiListAdapter(Context context, ShitiList list) {
		super();
		this.context = context;
		this.list = list;
		face = Typeface.createFromAsset(context.getAssets(), "fonts/SHOWG.TTF");
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
		holder.answer.setVisibility(View.GONE);
		if(list.index == null){
			holder.setSelected(true);
		}else{
			if(shiti.c){
				holder.setSelected(true);
				holder.answer.setVisibility(View.VISIBLE);
				holder.answer.setEnabled(true);
			}else{
				if(list.index == position){
					holder.answer.setVisibility(View.VISIBLE);
					holder.answer.setEnabled(false);
					holder.setSelected(true);
				}else{
					holder.setSelected(false);
				}
			}
		}
		
		
		
		return convertView;
	}
	
	public  class ViewHolder{
		private TextView noView;
		private TextView contentView;
		private View shitiItem;
		private View answer;
		public ViewHolder(View view) {
			answer = view.findViewById(R.id.icon_answer);
			shitiItem = view.findViewById(R.id.shiti_item);
			noView = (TextView) view.findViewById(R.id.no);
			contentView = (TextView) view.findViewById(R.id.content);
			noView.setTypeface(face);
		}
		
		public void setSelected(boolean val){
			shitiItem.setSelected(val);
			noView.setSelected(val);
		}
	}
	
	
	
	
}
