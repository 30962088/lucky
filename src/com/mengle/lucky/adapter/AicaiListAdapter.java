package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.DisplayUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AicaiListAdapter extends BaseAdapter{

	public static class Aicai{
		private int uid;
		private String nick;
		private String avatar;
		private int coin;
		public Aicai(int uid, String nick, String avatar, int coin) {
			super();
			this.uid = uid;
			this.nick = nick;
			this.avatar = avatar;
			this.coin = coin;
		}
		public int getUid() {
			return uid;
		}
		
	}
	private Context context;
	
	public List<Aicai> list;
	
	
	
	public AicaiListAdapter(Context context, List<Aicai> list) {
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
	
	private static final int[] BGS = new int[]{R.drawable.bg_aicai1,R.drawable.bg_aicai2,R.drawable.bg_aicai3,R.drawable.bg_aicai4};

	private static final String[] COLORS = new String[]{"#ddba42","#dddddd","#c48547","#ddc396"};
	
	
	
	private static int getColor(int position){
		if(position>3){
			position = 3;
		}
		return Color.parseColor(COLORS[position]);
	}
	
	private static int getBg(int position){
		if(position>3){
			position = 3;
		}
		return BGS[position];
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Aicai aicai = list.get(position);
		
		ViewHolder holder;
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.aicai_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.bgView.setBackgroundResource(getBg(position));
		
		holder.noView.setTextColor(getColor(position));
		
		int size = 24;
		
		if(position > 2){
			size = 18;
		}
		holder.noView.setTextSize(DisplayUtils.spToPx(context, size));
		
		holder.noView.setText(""+(position+1));
		
		holder.nickView.setText(aicai.nick);
		
		BitmapLoader.displayImage(context, aicai.avatar, holder.avatarView);
		
		holder.coinView.setText(""+aicai.coin);
		
		
		return convertView;
	}
	
	private class ViewHolder{
		private TextView noView;
		private TextView nickView;
		private ImageView avatarView;
		private TextView coinView;
		private View bgView;
		public ViewHolder(View view) {
			bgView = view.findViewById(R.id.bg);
			noView = (TextView) view.findViewById(R.id.no);
			nickView = (TextView) view.findViewById(R.id.nick);
			avatarView = (ImageView) view.findViewById(R.id.avatar);
			coinView = (TextView) view.findViewById(R.id.coin);
		}
	}
	
	

}
