package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.DisplayUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class UserHeadView extends FrameLayout{

	public enum Sex{
		MALE,FEMALE
	}
	
	public static class UserHeadData{
		private boolean hasNewMsg;
		private String photo;
		private String nick;
		private Sex sex;
		private int coin;
		private int win;
		private int eq;
		private int fail;
		private String level;
		private int focusCount;
		private int fansCount;
		private int fansNewCount;
		public UserHeadData(boolean hasNewMsg, String photo, String nick,
				Sex sex, int coin, int win, int eq, int fail, String level,
				int focusCount, int fansCount, int fansNewCount) {
			super();
			this.hasNewMsg = hasNewMsg;
			this.photo = photo;
			this.nick = nick;
			this.sex = sex;
			this.coin = coin;
			this.win = win;
			this.eq = eq;
			this.fail = fail;
			this.level = level;
			this.focusCount = focusCount;
			this.fansCount = fansCount;
			this.fansNewCount = fansNewCount;
		}
		
	}
	
	public UserHeadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public UserHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public UserHeadView(Context context) {
		super(context);
		init();
	}
	
	private View newIconView;
	
	private ImageView photoView;
	
	private TextView nickView;
	
	private View femaleView;
	
	private View maleView;
	
	private TextView coinView;
	
	private TextView statusView;
	
	private TextView levelView;
	
	private TextView focusCountView;
	
	private TextView fansCountView;
	
	private TextView fansNewView;
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.user_head, this);
		newIconView = findViewById(R.id.icon_new);
		photoView = (ImageView) findViewById(R.id.photo);
		nickView = (TextView) findViewById(R.id.nickname);
		femaleView = findViewById(R.id.icon_female);
		maleView = findViewById(R.id.icon_male);
		coinView = (TextView) findViewById(R.id.coin);
		statusView = (TextView) findViewById(R.id.status);
		levelView = (TextView) findViewById(R.id.level);
		focusCountView = (TextView) findViewById(R.id.focus_count);
		fansCountView = (TextView) findViewById(R.id.fans_count);
		fansNewView = (TextView) findViewById(R.id.fans_new);
	}
	
	public void setData(UserHeadData data){
		newIconView.setVisibility(data.hasNewMsg?View.VISIBLE:View.GONE);
		BitmapLoader.displayImage(getContext(), data.photo, photoView);
		nickView.setText(data.nick);
		if(data.sex == Sex.FEMALE){
			femaleView.setVisibility(View.VISIBLE);
			maleView.setVisibility(View.GONE);
		}else{
			femaleView.setVisibility(View.GONE);
			maleView.setVisibility(View.VISIBLE);
		}
		coinView.setText(data.coin);
		statusView.setText(getContext().getString(R.string.user_head_status, new int[]{data.win,data.fail,data.eq}));
		levelView.setText(data.level);
		focusCountView.setText(data.focusCount);
		fansCountView.setText(data.fansCount);
		fansNewView.setText(data.fansNewCount);
	}

	
	
}
