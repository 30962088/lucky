package com.mengle.lucky;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.mengle.lucky.network.GameFillAnswerRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.IUserGet.UserResult;
import com.mengle.lucky.network.UserMe.Callback;
import com.mengle.lucky.network.model.Game.Option;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.wiget.LoadingPopup;
import com.mengle.lucky.wiget.RadioGroupLayout;
import com.mengle.lucky.wiget.RadioGroupLayout.RadioItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FillAnswerActivity extends BaseActivity implements OnClickListener{
	
	public static void open(Context context,Param param){
		Intent intent = new Intent(context, FillAnswerActivity.class);
		intent.putExtra("param", param);
		context.startActivity(intent);
	}
	
	public static class Param implements Serializable{
		
		private int game_id;
		
		private String title;
		
		private ArrayList<Option> opts;

		public Param(int game_id,String title, ArrayList<Option> opts) {
			super();
			this.game_id = game_id;
			this.title = title;
			this.opts = opts;
		}
		
		
		
	}
	
	private Param param;
	
	private TextView nickView;
	
	private ImageView avatarView;
	
	private TextView totalCoinView;
	
	private TextView titleView;
	
	private TextView question1View;
	
	private TextView question2View;
	
	private TextView question3View;
	
	private RadioGroupLayout radioGroupLayout1;
	
	private EditText reasonText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		App.currentActivity = this;
		param = (Param) getIntent().getSerializableExtra("param");
		setContentView(R.layout.fillanswer_layout);
		findViewById(R.id.publish_btn).setOnClickListener(this);
		findViewById(R.id.leftnav).setOnClickListener(this);
		totalCoinView = (TextView) findViewById(R.id.totalCoin);
		nickView = (TextView) findViewById(R.id.nickname);
		avatarView = (ImageView) findViewById(R.id.user_photo);
		titleView = (TextView) findViewById(R.id.titleview);
		radioGroupLayout1 = (RadioGroupLayout) findViewById(R.id.radio3);
		question1View = (TextView) findViewById(R.id.question1);
		question2View = (TextView) findViewById(R.id.question2);
		question3View = (TextView) findViewById(R.id.question3);
		reasonText = (EditText) findViewById(R.id.reasonView);
		radioGroupLayout1.setList(new ArrayList<RadioGroupLayout.RadioItem>(){{
			add(new RadioItem("A", "A"));
			add(new RadioItem("B", "B"));
			add(new RadioItem("C", "C"));
		}});
		fill();
	}
	
	private void fill(){
		titleView.setText(param.title);
		TextView[] textViews = new TextView[]{question1View,question2View,question3View};
		for(int i = 0;i<param.opts.size() && i<3;i++){
			textViews[i].setText(param.opts.get(i).getContent());
			radioGroupLayout1.addValid(i);
		}
		
		UserMe.get(this, new Callback() {
			
			@Override
			public void onsuccess(UserResult userResult) {
				totalCoinView.setText(""+userResult.getGold_coin());
				nickView.setText(userResult.getNickname());
				avatarView.setTag(userResult.getAvatar());
				BitmapLoader.displayImage(FillAnswerActivity.this, userResult.getAvatar(), avatarView);
				
			}
		});
		
	}
	
	private void onPublish() {
		String reason = reasonText.getText().toString();
		String[] numbers = new String[]{"A","B","C"};
		String answer = radioGroupLayout1.getValue();
		if(answer == null){
			Utils.tip(this, "请填写答案");
			return;
		}
		Preferences.User user = new Preferences.User(this);
		LoadingPopup.show(this);
		GameFillAnswerRequest answerRequest = new GameFillAnswerRequest(this,
				new GameFillAnswerRequest.Param(user.getUid(), user.getToken(), param.game_id, param.opts.get(ArrayUtils.indexOf(numbers, answer)).getId(), reason));
		RequestAsync.request(answerRequest, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				if(request.getStatus() == Status.SUCCESS){
					LoadingPopup.hide(FillAnswerActivity.this);
					finish();
				}
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.publish_btn:
			onPublish();
			break;
		case R.id.leftnav:
			finish();
			break;
		default:
			break;
		}
		
	}

	

}
