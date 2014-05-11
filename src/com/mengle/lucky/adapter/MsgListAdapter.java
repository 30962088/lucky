package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.WigetUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

public class MsgListAdapter extends BaseAdapter{

	public enum Type{
		NOFITY,MSG
	}
	
	public static class Msg{
		private int id;
		private String title;
		private boolean checked;
		private Type type;
		private String rightTitle;
		public Msg(int id, String title, boolean checked, Type type,
				String rightTitle) {
			super();
			this.id = id;
			this.title = title;
			this.checked = checked;
			this.type = type;
			this.rightTitle = rightTitle;
		}
		public int getId() {
			return id;
		}
		
	}
	
	public static class Nofity extends Msg{
		
		public Nofity(int id, String title, boolean checked) {
			super(id, title, checked, Type.NOFITY, "详情");
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public static class Message extends Msg{
		
		public Message(int id, String title,String rightTitle,boolean checked
				) {
			super(id, title, checked, Type.MSG, rightTitle);
			// TODO Auto-generated constructor stub
		}

		
	}
	
	
	
	private Context context;
	
	private List<Msg> list;
	
	
	
	public MsgListAdapter(Context context, List<Msg> list) {
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

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Msg msg = list.get(position);
		if(convertView == null){
			
			convertView = LayoutInflater.from(context).inflate(
					R.layout.msg_item, null);
			holder = new ViewHolder((ViewGroup)convertView);
			convertView.setTag(holder);
			convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.nofity_tab)));
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.titleView.setText(msg.title);
		holder.rightView.setText(msg.rightTitle);
		int rightWidth;
		if(msg.type==Type.MSG){
			rightWidth = (int) context.getResources().getDimension(R.dimen.msg_right);
			
		}else{
			rightWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
		}
		
		holder.rightView.setLayoutParams(new LinearLayout.LayoutParams(rightWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
		
		holder.setSelected(msg.checked);
		
		if(position%2==0){
			convertView.setBackgroundColor(Color.parseColor("#f6f8fa"));
		}else{
			convertView.setBackgroundColor(Color.parseColor("#ffffff"));
		}
		
		
		return convertView;
	}
	
	private static class ViewHolder{
		private TextView titleView;
		private TextView rightView;
		private ViewGroup view;
		public ViewHolder(ViewGroup view) {
			this.view = view;
			titleView = (TextView) view.findViewById(R.id.title);
			rightView = (TextView) view.findViewById(R.id.right);
		}
		public void setSelected(boolean selected){
			WigetUtils.setSelected(view, selected);
		}
	}
	
	
	
	

}
