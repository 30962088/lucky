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
import android.view.ViewGroup;
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

		public Photo(String photo,int type) {
			super();
			this.photo = photo;
			this.type = type;
		}

		public String getId() {
			return id;
		}

		public String getPhoto() {
			return photo;
		}
		
		public static List<Photo> findPhotosByType(Context context,int type) throws SQLException{
			List<Photo> list = new ArrayList<Photo>();
			DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
			list.addAll(dataBaseHelper.getPhotoDao().queryBuilder().where().eq("type", type).query());
			return list;
		}

	}

	public static class PhotoList {
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
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.photo_item, null);
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
