package com.mengle.lucky.adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;
import com.mengle.lucky.R;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotoListAdapter extends BaseAdapter {

	@DatabaseTable(tableName = "picture")
	public static class Photo {
		
		
		
		public static final int TYPE_HEAD = 1;

		public static final int TYPE_AVATAR = 2;

		public Photo() {
			// TODO Auto-generated constructor stub
		}
		
		@DatabaseField(id = true)
		private String id = "" + new Date().getTime();
		@DatabaseField
		private String photo;

		@DatabaseField(columnName="type")
		private int type;


		public Photo(String photo, int type) {
			super();
			this.photo = photo;
			this.type = type;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getId() {
			return id;
		}
	

		public String getPhoto() {
			return photo;
		}
		
		private static List<Photo> getDefaultPhotos(int type){
			String folder = "";
			if(type==TYPE_AVATAR){
				folder = "avatar";
			}else{
				folder="head";
			}
			List<Photo> photos = new ArrayList<PhotoListAdapter.Photo>();
			for(int i = 1;i<=4;i++){
				String url = "http://res.joypaw.com/"+folder+"/default/"+i+".jpg";
				photos.add(new Photo(url, type));
			}
			return photos;
		}
		
		public static List<Photo> findPhotosByType(Context context,int type) throws SQLException{
			List<Photo> list = new ArrayList<Photo>();
			DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
			list.addAll(getDefaultPhotos(type));
			list.addAll(dataBaseHelper.getPhotoDao().queryBuilder().where().eq("type", type).query());
			
			return list;
		}

	}

	public static class PhotoList {
		private List<Photo> list;
		private Integer index;
		private int type;

		public PhotoList(List<Photo> list,int type) {
			super();
			this.list = list;
			this.type = type;
		}

		public void setIndex(Integer index) {
			this.index = index;
		}

		public List<Photo> getList() {
			return list;
		}

		public Integer getIndex() {
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
		if (convertView == null) {
			if(list.type==Photo.TYPE_AVATAR){
				convertView = LayoutInflater.from(context).inflate(
						R.layout.photo_item, null);
			}else{
				convertView = LayoutInflater.from(context).inflate(
						R.layout.photo_item1, null);
			}
			final View v = convertView;
			convertView.post(new Runnable() {
				
				@Override
				public void run() {
					v.setLayoutParams(new AbsListView.LayoutParams(v.getWidth(), v.getWidth()*9/16));
					
				}
			});
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		
		
		BitmapLoader.displayImage(context, photo.photo, holder.imageView);

		if (list.index == position) {
			holder.setSelected(true);
		} else {
			holder.setSelected(false);
		}

		return convertView;
	}

	private class ViewHolder {

		private ImageView imageView;

		private View view;

		private int padding;

		public ViewHolder(View view) {
			this.view = view;
			padding = (int) context.getResources().getDimension(
					R.dimen.photo_item_padding);
			imageView = (ImageView) view.findViewById(R.id.img);
		}

		public void setSelected(boolean selected) {
			if (selected) {
				view.setPadding(padding, padding, padding, padding);
			} else {
				view.setPadding(0, 0, 0, 0);
			}
		}

	}

}
