package com.mengle.lucky.wiget;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.WebViewActivity;
import com.mengle.lucky.network.CampaignsGetRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.CampaignsGetRequest.Result;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.Preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.view.View.OnClickListener;;

public class CatDropList extends FrameLayout implements AnimationListener,OnClickListener{

	public static interface OnStateChange{
		public void onShow();
		public void onDismiss();
	}
	
	public CatDropList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CatDropList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CatDropList(Context context) {
		super(context);
		init();
	}
	
	private GridView gridView;
	private Animation animIn;
	private Animation animOut;
	private View innerView;
	private ImageView adImg;
	private OnStateChange onStateChange;
	private View listContainer;
	public void setOnStateChange(OnStateChange onStateChange) {
		this.onStateChange = onStateChange;
	}
	private void init(){
		if(isInEditMode()){
			return;
		}
		LayoutInflater.from(getContext()).inflate(R.layout.cat_layout, this);
		setVisibility(View.GONE);
		listContainer = findViewById(R.id.listContainer);
		animIn = AnimationUtils.loadAnimation(getContext(),
				R.anim.slide_from_top);
		animIn.setFillAfter(true);
		
		animOut = AnimationUtils.loadAnimation(getContext(),
				R.anim.slide_up_top);
		animOut.setFillBefore(true);
		animOut.setAnimationListener(this);
		gridView = (GridView) findViewById(R.id.catgrid);
		innerView = findViewById(R.id.inner);
		innerView.setOnClickListener(this);
		adImg = (ImageView) findViewById(R.id.adimg);
		findViewById(R.id.outer).setOnClickListener(this);
		findViewById(R.id.close).setOnClickListener(this);
		
		request();
	}
	
	public static interface OnAdCallback{
		public void onAdCallback(List<Result> results);
	}
	
	private OnAdCallback onAdCallback;
	
	public void setOnAdCallback(OnAdCallback onAdCallback) {
		this.onAdCallback = onAdCallback;
	}
	
	private void request() {
		Preferences.User user = new Preferences.User(getContext());
 		final CampaignsGetRequest getRequest = new CampaignsGetRequest(getContext(),
				new CampaignsGetRequest.Param(user.getUid(), user.getToken()));
 		RequestAsync.request(getRequest, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				List<Result> results = getRequest.getResults();
			/*	List<Result> results = new ArrayList<CampaignsGetRequest.Result>(){{
					Result result = new Result();
					result.setImage("http://tp4.sinaimg.cn/2129028663/180/5684393877/1");
					result.setUrl("http://tp4.sinaimg.cn/2129028663/180/5684393877/1");
					add(result);
				}};*/
				adImg.setVisibility(View.GONE);
				if(results != null && results.size()>0){
					setAdimg(results.get(0));
				}
				if(onAdCallback != null){
					onAdCallback.onAdCallback(results);
				}
				
			}
		});
		
	}
	
	public void setAdimg(final Result result){
		adImg.setVisibility(View.VISIBLE);
		adImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WebViewActivity.open(getContext(), result.getUrl());
				
			}
		});
		BitmapLoader.displayImage(getContext(), result.getImage(), adImg);
	}
	
	public void setAdapter(ListAdapter adapter){
		listContainer.setVisibility(View.VISIBLE);
		gridView.setAdapter(adapter);
	}
	
	public void show(){
		setVisibility(View.VISIBLE);
		startAnimation(animIn);
		onStateChange.onShow();
	}
	
	public GridView getGridView() {
		return gridView;
	}
	
	public void dismiss(){
		startAnimation(animOut);
	}

	public void onAnimationEnd(Animation animation) {
		if(animation == animOut){
			setVisibility(View.GONE);
			if(onStateChange != null){
				onStateChange.onDismiss();
			}
		}
		
	}

	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.outer:
			
		case R.id.close:
			dismiss();
			break;
		}
		
	}
	
	
}
