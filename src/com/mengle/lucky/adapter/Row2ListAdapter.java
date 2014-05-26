package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.KanZhuangActivity;
import com.mengle.lucky.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Row2ListAdapter extends BaseAdapter{


	
	public static class Row2{
		private int id;
		private String leftText;
		private String rightText;
		private Integer color;
		public Row2(int id, String leftText, String rightText, Integer color) {
			super();
			this.id = id;
			this.leftText = leftText;
			this.rightText = rightText;
			this.color = color;
		}
		public Row2(int id, String leftText, String rightText) {
			super();
			this.id = id;
			this.leftText = leftText;
			this.rightText = rightText;
		}
		
		public int getId() {
			return id;
		}
		
		
		
		
	}
	
	private Context context;
	
	private List<Row2> list;
	
	
	
	public Row2ListAdapter(Context context, List<Row2> list) {
		super();
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
		final Row2 row2 = list.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.row_2_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(row2.id>0){
					KanZhuangActivity.open(context, row2.id);
				}
				
			}
		});
		int row1bgColor;
		int row2bgColor;
		int row1Color = Color.parseColor("#768082");
		int row2Color = Color.parseColor("#768082");
		if(position == 0){
			row1Color = Color.parseColor("#e47033");
			row2Color = Color.parseColor("#429db6");
			row1bgColor = Color.parseColor("#f9e3d6");
			row2bgColor = Color.parseColor("#dbf2fa");
		}else{
			if(row2.color != null){
				row1Color = row2.color;
				row2Color = row2.color;
			}
			if(position%2==0){
				row1bgColor = Color.parseColor("#f6f8fa");
				row2bgColor = Color.parseColor("#ffffff");
			}else{
				row2bgColor = Color.parseColor("#f6f8fa");
				row1bgColor = Color.parseColor("#ffffff");
			}
		}
		
		
		
		holder.row1.setBackgroundColor(row1bgColor);
		holder.row2.setBackgroundColor(row2bgColor);
		holder.row1.setTextColor(row1Color);
		holder.row2.setTextColor(row2Color);
		holder.row1.setText(row2.leftText);
		holder.row2.setText(row2.rightText);
		

		
		return convertView;
	}
	
	private static class ViewHolder{
		private TextView row1;
		private TextView row2;
		public ViewHolder(View view) {
			row1 = (TextView) view.findViewById(R.id.row1);
			row2 = (TextView) view.findViewById(R.id.row2);
		}
		
	}
	

}
