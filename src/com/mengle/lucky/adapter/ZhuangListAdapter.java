package com.mengle.lucky.adapter;

import java.util.HashMap;
import java.util.List;

import com.mengle.lucky.KanZhuangActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.wiget.ZhuangItem;
import com.mengle.lucky.wiget.ZhuangItem1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ZhuangListAdapter extends BaseAdapter {

	public static class ZhuangModel {
		private int id;
		private String title;
		private String photo;
		private int person;
		private String endTime;

		

		public ZhuangModel(int id,String title, String photo, int person,
				String endTime) {
			super();
			this.id = id;
			this.title = title;
			this.photo = photo;
			this.person = person;
			this.endTime = endTime;
		}

	}

	private Context context;

	private List<ZhuangModel> list;

	public ZhuangListAdapter(Context context, List<ZhuangModel> list) {
		this.context = context;
		this.list = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();

	public void reset(){
		map.clear();
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final ZhuangModel model = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.zhuang_list_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ZhuangItem1 zhuangItem1 = holder.item1;
		zhuangItem1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean expanded = map.get(position) == null || map.get(position)==false ? false:true;
				if(!expanded){
					zhuangItem1.setExpand(true, 200);
					map.put(position, true);
				}else{
					KanZhuangActivity.open(context, model.id);
				}
				
				
			}
		});
		boolean expanded = map.get(position) == null || map.get(position)==false ? false:true;
		 
		zhuangItem1.setExpand(expanded,0);
		holder.setModel(model);
		return convertView;
	}

	private static class ViewHolder {

		private ZhuangItem1 item1;

		public ViewHolder(View view) {

			item1 = (ZhuangItem1) view.findViewById(R.id.item1);
		}

		public void setModel(ZhuangModel model) {

			item1.setVisibility(View.VISIBLE);
			item1.setTitle(model.title);
			item1.setPhoto(model.photo);
			item1.setPerson(model.person);
			item1.setEndTime(model.endTime);

		}

	}

}
