package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotoListAdapter extends BaseAdapter{

	public static class Photo{
		private String id;
		private String photo;
		public Photo(String id, String photo) {
			super();
			this.id = id;
			this.photo = photo;
		}
		
		public String getId() {
			return id;
		}
		public String getPhoto() {
			return photo;
		}
		
	}
	
	public static class PhotoList{
		private List<Photo> list;
		private int index;
		public PhotoList(List<Photo> list) {
			super();
			this.list = list;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public List<Photo> getList() {
			return list;
		}
		public int getIndex() {
			return index;
		}
	}
	
	private Context context;
	
	private PhotoList list;
	
	
	
	public PhotoListAdapter(Context context, PhotoList list) {
		super();
		this.context = context;
		this.list = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Photo photo = list.list.get(position);
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.photo_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		BitmapLoader.displayImage(context, photo.photo, holder.imageView);
		
		if(list.index == position){
			holder.setSelected(true);
		}else{
			holder.setSelected(false);
		}
		
		return convertView;
	}
	
	private class ViewHolder{
		
		private ImageView imageView;
		
		private View view;
		
		private int padding;
		
		public ViewHolder(View view) {
			this.view = view;
			padding = (int) context.getResources().getDimension(R.dimen.photo_item_padding);
			imageView = (ImageView) view.findViewById(R.id.img);
		}
		
		public void setSelected(boolean selected){
			if(selected){
				view.setPadding(padding, padding, padding, padding);
			}else{
				view.setPadding(0, 0, 0, 0);
			}
		}
		
	}

}
