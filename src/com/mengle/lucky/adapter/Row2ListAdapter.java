package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Row2ListAdapter extends BaseAdapter{

	public static class Row{
		private String title;
		private int textColor;
		private int bgColor;
		public Row(String title, int textColor, int bgColor) {
			super();
			this.title = title;
			this.textColor = textColor;
			this.bgColor = bgColor;
		}
		
	}
	
	public static class Row2{
		private Row left;
		private Row right;
		public Row2(Row left, Row right) {
			super();
			this.left = left;
			this.right = right;
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
		Row2 row2 = list.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.row_2_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.setData(row2);
		
		return convertView                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ;
	}
	
	private static class ViewHolder{
		private TextView row1;
		private TextView row2;
		public ViewHolder(View view) {
			row1 = (TextView) view.findViewById(R.id.row1);
			row2 = (TextView) view.findViewById(R.id.row2);
		}
		private void setData(Row2 row){
			row1.setText(row.left.title);
			row1.setBackgroundColor(row.left.bgColor);
			row1.setTextColor(row.left.textColor);
			row2.setText(row.right.title);
			row2.setBackgroundColor(row.right.bgColor);
			row2.setTextColor(row.right.textColor);
		}
	}
	

}
