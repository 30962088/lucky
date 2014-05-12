package com.mengle.lucky.wiget;

import com.mengle.lucky.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class JiyunIntroDialog implements OnClickListener{
	
	private PopupWindow mPopupWindow;
	
	private Context context;

	
	
	private JiyunIntroDialog(Context context) {
		this.context = context;
		init();
	}
	
	public static void open(Context context){
		new JiyunIntroDialog(context);
	}
	
	private void init(){
		View view = LayoutInflater.from(context).inflate(R.layout.jiyun_intro_layout,
				null);
		view.setOnClickListener(this);
		
		
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, true);
		
		
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#b3fabe0c")));
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		
		/*Animation rotation = AnimationUtils.loadAnimation(context,
				R.anim.zoom_center);

		View popup = view.findViewById(R.id.popup);
		popup.setOnClickListener(this);
		popup.startAnimation(rotation);*/
		view.findViewById(R.id.cancel).setOnClickListener(this);
		
		mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		
		
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alert:
		case R.id.cancel:
			mPopupWindow.dismiss();
		default:
			break;
		}

	}
	
	
	
}
