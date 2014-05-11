package com.mengle.lucky.wiget;

import java.util.ArrayList;

import com.mengle.lucky.R;
import com.mengle.lucky.ZoneActivity;
import com.mengle.lucky.ZoneActivity.OnPickListener;
import com.mengle.lucky.adapter.PhotoListAdapter;
import com.mengle.lucky.adapter.PhotoListAdapter.Photo;
import com.mengle.lucky.adapter.PhotoListAdapter.PhotoList;

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
	
	
	public PhotoListDialog(Context context,Type type,OnResultClick onResultClick) {
		this.context = context;
		this.type = type;
		this.onResultClick = onResultClick;
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
		request();
		mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}
	
	private PhotoListAdapter adapter;
	
	private PhotoList photoList;
	
	private void request(){
		int resId;
		if(type == Type.HEAD){
			resId = R.drawable.icon_drag_big;
		}else{
			resId = R.drawable.icon_drag_small;
		}
		final int fresid = resId;
		photoList = new PhotoList(new ArrayList<Photo>(){{
			add(new Photo("1", "http://www.5wants.cc/WEB/File/U3325P704T93D1661F3923DT20090612155225.jpg"));
			add(new Photo("1", "http://www.5wants.cc/WEB/File/U3325P704T93D1661F3923DT20090612155225.jpg"));
			add(new Photo("1", "http://www.5wants.cc/WEB/File/U3325P704T93D1661F3923DT20090612155225.jpg"));
			add(new Photo("1", "http://www.5wants.cc/WEB/File/U3325P704T93D1661F3923DT20090612155225.jpg"));
			add(new Photo("1", "drawable://" + fresid));
		}});
		photoList.setIndex(0);
		adapter = new PhotoListAdapter(context, photoList);
		gridView.setAdapter(adapter);
	}
	
	
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alert:
		case R.id.ok:
			onResultClick.onResult(type, photoList.getList().get(photoList.getIndex()).getPhoto());
			mPopupWindow.dismiss();
			break;
		
		default:
			break;
		}

	}
	
	public void onPick(Uri uri) {
		
		photoList.getList().add(photoList.getList().size()-1, new Photo("1", uri.toString()));
		adapter.notifyDataSetChanged();
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
