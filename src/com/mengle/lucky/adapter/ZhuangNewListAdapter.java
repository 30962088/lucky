package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuangNewListAdapter extends BaseAdapter{

	
	public static class Model{
		private int id;
		private String title;
		private String photo;
		private int person;
		private String endTime;
		private String who;
		public Model(int id, String title, String photo, int person,
				String endTime, String who) {
			super();
			this.id = id;
			this.title = title;
			this.photo = photo;
			this.person = person;
			this.endTime = endTime;
			this.who = who;
		}
		public int getId() {
			return id;
		}
		
	}
	private Context context;
	
	private List<Model> list;
	
	

	public ZhuangNewListAdapter(Context context, List<Model> list) {
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
		ViewHolder holder = null;
		Model model = list.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.dd_game_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(position%2==0){
			holder.setSelected(false);
		}else{
			holder.setSelected(true);
		}
		
		holder.count.setText("("+model.person+")");
		
		BitmapLoader.displayImage(context, model.photo, holder.photo);
		
		holder.time.setText(model.endTime);
		
		holder.title.setText(model.title);
		
		holder.who.setText(model.who);
		
		
		
		return convertView;
	}
	
	public void updateViews(final View view, boolean isSelected) {
	    view.setSelected(isSelected);

	    //do stuff
	}
	
	private class ViewHolder{
		
		private TextView time;
		
		private TextView title;
		
		private TextView who;
		
		private TextView count;
		
		private ImageView photo;
		
		private View container1;
		
		private View container2;
		
		
		public ViewHolder(View view) {
			container1 = view.findViewById(R.id.container1);
			container2 = view.findViewById(R.id.container2);
			time = (TextView) view.findViewById(R.id.time);
			title = (TextView) view.findViewById(R.id.title);
			who = (TextView) view.findViewById(R.id.who);
			count = (TextView) view.findViewById(R.id.count);
			photo = (ImageView) view.findViewById(R.id.photo);
		}
		
		public void setSelected(boolean val){
			if(val){
				container1.setBackgroundResource(R.drawable.shape_blue);
				container2.setBackgroundResource(R.drawable.shape_blue_dark);
				time.setTextColor(Color.parseColor("#ffffff"));
				title.setTextColor(Color.parseColor("#ffffff"));
				who.setTextColor(Color.parseColor("#ffffff"));
				time.setCompoundDrawablesWithIntrinsicBounds( R.drawable.dd_p2, 0, 0, 0);
				count.setTextColor(Color.parseColor("#ffffff"));
				count.setCompoundDrawablesWithIntrinsicBounds( R.drawable.dd_d2, 0, 0, 0);
			}else{
				container1.setBackgroundResource(R.drawable.shape_white);
				container2.setBackgroundResource(R.drawable.shape_gray);
				time.setTextColor(Color.parseColor("#434a54"));
				time.setCompoundDrawablesWithIntrinsicBounds( R.drawable.dd_p1, 0, 0, 0);
				title.setTextColor(Color.parseColor("#434a54"));
				who.setTextColor(Color.parseColor("#434a54"));
				count.setTextColor(Color.parseColor("#434a54"));
				count.setCompoundDrawablesWithIntrinsicBounds( R.drawable.dd_d1, 0, 0, 0);
			}
		}
		
		
	}
	
	

}
