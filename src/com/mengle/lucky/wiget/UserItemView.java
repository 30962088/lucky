package com.mengle.lucky.wiget;

import com.mengle.lucky.ChatActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.ZoneActivity;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.UserMeFollow;
import com.mengle.lucky.network.UserMeUnFollow;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.TranslateRelativeLayout.ExpanedListener;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class UserItemView extends FrameLayout implements OnClickListener{

	public static class Model{
		private int uid;
		private String nickname;
		private String avatar;
		private String level;
		private int coin;
		private int win;
		private int lost;
		private int lose;
		private boolean focused;
		private boolean expand = false;
		public Model(int uid, String nickname, String avatar, String level,
				int coin, int win, int lost, int lose, boolean focused) {
			super();
			this.uid = uid;
			this.nickname = nickname;
			this.avatar = avatar;
			this.level = level;
			this.coin = coin;
			this.win = win;
			this.lost = lost;
			this.lose = lose;
			this.focused = focused;
		}
		
		public void setExpand(boolean expand) {
			this.expand = expand;
		}
		public boolean isExpand() {
			return expand;
		}
		
	}
	
	
	
	
	
	public UserItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public UserItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public UserItemView(Context context) {
		super(context);
		init();
	}
	
	private CheckBox btnStar;
	
	private ImageView avatar;
	
	private TextView textRow1;
	
	private TextView textRow2;
	
	private TextView textCoin;
	private TranslateRelativeLayout layout;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.user_item, this);
		layout = (TranslateRelativeLayout) findViewById(R.id.center);
		findViewById(R.id.container).setOnClickListener(this);
		layout.setMax(Utils.dpToPx(getContext(), 50));
		btnStar = (CheckBox) findViewById(R.id.icon_star);
		btnStar.setOnClickListener(this);
		avatar = (ImageView) findViewById(R.id.avatar);
		textRow1 = (TextView) findViewById(R.id.textrow1);
		textRow2 = (TextView) findViewById(R.id.textrow2);
		textCoin = (TextView) findViewById(R.id.coin);
		findViewById(R.id.icon_comment).setOnClickListener(this);
	}
	
	private Model model;
	
	public void setModel(Model model) {
		this.model = model;
		textRow1.setText(getResources().getString(R.string.user_item_row1, model.nickname,model.level));
		textRow2.setText(getResources().getString(R.string.user_item_row2, model.win,model.lose,model.lost));
		textCoin.setText(""+model.coin);
		BitmapLoader.displayImage(getContext(), model.avatar, avatar);
		btnStar.setChecked(model.focused);
	}
	
	public void setExpanedListener(ExpanedListener expanedListener) {
		layout.setExpanedListener(expanedListener);
	}
	

	public void setExpand(boolean expand){
		layout.setExpand(expand, 0);
	}
	
	private void onStar(){
		Preferences.User user = new Preferences.User(getContext());
//		btnStar.setChecked(btnStar.isChecked());
		Request request = null;
		if(btnStar.isChecked()){
			request = new UserMeFollow(new UserMeFollow.Params(user.getUid(), user.getToken(), model.uid));
		}else{
			request = new UserMeUnFollow(new UserMeUnFollow.Params(user.getUid(), user.getToken(), model.uid));
		}
		final Request request2 = request;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				request2.request();
				
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_star:
			onStar();
			break;
		case R.id.icon_comment:
			ChatActivity.open(getContext(), model.uid);
			break;
		case R.id.container:
			ZoneActivity.open(getContext(), model.uid);
			break;
		default:
			break;
		}
		
	}
	
	
}
