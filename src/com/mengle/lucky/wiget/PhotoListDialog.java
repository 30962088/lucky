package com.mengle.lucky.wiget;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.ZoneActivity;
import com.mengle.lucky.ZoneActivity.OnPickListener;
import com.mengle.lucky.adapter.PhotoListAdapter;
import com.mengle.lucky.adapter.PhotoListAdapter.Photo;
import com.mengle.lucky.adapter.PhotoListAdapter.PhotoList;
import com.mengle.lucky.database.DataBaseHelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;

public class PhotoListDialog implements OnClickListener,OnItemClickListener,OnPickListener{
	
	private PopupWindow mPopupWindow;
	
	private Context context;

	
	public static interface OnResultClick{
		public void onResult(Type type, String uri);
	}
	
	public enum Type{
		HEAD,AVATAR
	}
	
	
	
	private Type type;
	
	private OnResultClick onResultClick;
	
	private String defaultImg;
	
	
	public PhotoListDialog(Context context,Type type,OnResultClick onResultClick,String defaultImg) {
		this.context = context;
		this.type = type;
		this.onResultClick = onResultClick;
		this.defaultImg = defaultImg;
		init();
	}
	
	private GridView gridView;
	

	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.photolist_dialog_layout,
				null);
		view.setOnClickListener(this);
		gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setOnItemClickListener(this);
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#00000000")));
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		
		Animation rotation = AnimationUtils.loadAnimation(context,
				R.anim.zoom_center);

		View popup = view.findViewById(R.id.popup);
		popup.setOnClickListener(this);
		popup.startAnimation(rotation);
		view.findViewById(R.id.ok).setOnClickListener(this);
		try {
			request();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}
	
	private PhotoListAdapter adapter;
	
	private PhotoList photoList;
	
	private void request() throws SQLException{
		int resId;
		if(type == Type.HEAD){
			gridView.setNumColumns(2);
			resId = R.drawable.icon_drag_big;
		}else{
			gridView.setNumColumns(3);
			resId = R.drawable.icon_drag_small;
		}
		int type = Photo.TYPE_AVATAR;
		if(this.type == Type.HEAD){
			type = Photo.TYPE_HEAD;
		}
		List<Photo> list = new ArrayList<PhotoListAdapter.Photo>();
//		list.add(new Photo(defaultImg, type));
		list.addAll(Photo.findPhotosByType(context, type));
		list.add(new Photo( "drawable://" + resId,type));
		photoList =  new PhotoList(list,type);
		
		
		photoList.setIndex(0);
		adapter = new PhotoListAdapter(context, photoList);
		gridView.setAdapter(adapter);
	}
	
	
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alert:
			mPopupWindow.dismiss();
			break;
		case R.id.ok:
			onResultClick.onResult(type, photoList.getList().get(photoList.getIndex()).getPhoto());
			mPopupWindow.dismiss();
			break;
		
		default:
			break;
		}

	}
	
	public void onPick(Uri uri) {
		int type = Photo.TYPE_AVATAR;
		if(this.type == Type.HEAD){
			type = Photo.TYPE_HEAD;
		}
		Photo photo = new Photo(uri.toString(),type);
		try {
			new DataBaseHelper(context).getPhotoDao().create(photo);
			photoList.getList().add(photoList.getList().size()-1, photo);
			adapter.notifyDataSetChanged();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(position == photoList.getList().size()-1){
			if(context instanceof ZoneActivity){
				ZoneActivity activity = (ZoneActivity)context;
				activity.pick(this);
			}
		}else{
			photoList.setIndex(position);
			adapter.notifyDataSetChanged();
		}
		
	}
	
	
	
}
