package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.Preferences.Network;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

public class NetworkDialog implements OnClickListener{
	
	private PopupWindow mPopupWindow;
	
	private Context context;
	
	private View parent;
	
	private OnNetworkClick confirmClick;
	
	public static interface OnNetworkClick{
		public void onResult(int type);
	}
	
	private NetworkDialog(Context context,OnNetworkClick confirmClick,View parent) {
		this.context = context;
		this.parent = parent;
		this.confirmClick = confirmClick;
		init();
	}
	
	public static void open(Context context,OnNetworkClick confirmClick,View parent){
		new NetworkDialog(context,confirmClick,parent);
	}
	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.network_dialog_layout,
				null);
		view.setOnClickListener(this);
		
		if(parent != null){
			int height = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
			mPopupWindow = new PopupWindow(view, parent.getWidth(),
					height
					, true);
		}else{
			mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, true);
		}
		
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#80b7bbc2")));
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		
		Animation rotation = AnimationUtils.loadAnimation(context,
				R.anim.slide_from_buttom);

		View popup = view.findViewById(R.id.popup);
		popup.setOnClickListener(this);
		popup.startAnimation(rotation);
		view.findViewById(R.id.cancel).setOnClickListener(this);
		view.findViewById(R.id.least).setOnClickListener(this);
		view.findViewById(R.id.most).setOnClickListener(this);
		view.findViewById(R.id.none).setOnClickListener(this);
		if(parent != null){
			mPopupWindow.showAtLocation(parent, Gravity.LEFT, 0, 0);
		}else{
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
		
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alert:
		case R.id.cancel:
			mPopupWindow.dismiss();
			break;
		case R.id.least:
			mPopupWindow.dismiss();
			confirmClick.onResult(Network.TYPE_LEAST);
			break;
		case R.id.most:
			mPopupWindow.dismiss();
			confirmClick.onResult(Network.TYPE_MOST);
			break;
		case R.id.none:
			mPopupWindow.dismiss();
			confirmClick.onResult(Network.TYPE_NONE);
			break;
		default:
			break;
		}

	}
	
	
	
}
