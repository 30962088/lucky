package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.CatListAdater.CatList.Cat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CatListAdater extends BaseAdapter{

	public static class CatList{
		
		private int selected;
		
		private List<Cat> list;
		
		public List<Cat> getList() {
			return list;
		}
		
		public CatList(List<Cat> list,int selected) {
			super();
			this.selected = selected;
			this.list = list;
		}

		public static class Cat{
			private int id;
			private String name;
			public Cat(int id, String name) {
				this.id = id;
				this.name = name;
			}
			public int getId() {
				return id;
			}
			
		}
		
	}
	
	private Context context;
	
	private CatList catList;
	
	
	
	public CatListAdater(Context context, CatList catList) {
		this.context = context;
		this.catList = catList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return catList.list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return catList.list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Cat cat = catList.list.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.cat_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nameView.setText(cat.name);
		if(position == catList.selected){
			holder.nameView.setSelected(true);
		}else{
			holder.nameView.setSelected(false);
		}
		
		
		if((position+1)%4 == 0){
			holder.divView.setVisibility(View.GONE);
		}else{
			holder.divView.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}
	
	private static class ViewHolder{
		private TextView nameView;
		private View divView;
		public ViewHolder(View view) {
			
			nameView = (TextView) view.findViewById(R.id.cat_name);
			divView = view.findViewById(R.id.div);
		}
	}

}
