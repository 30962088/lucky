package com.mengle.lucky.wiget;

import java.util.Date;
import java.util.TimerTask;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.QuestionAdapter;
import com.mengle.lucky.adapter.QuestionAdapter.Question;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.DisplayUtils;
import com.mengle.lucky.wiget.PeronCountView.Count;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ThemeLayout extends FrameLayout {

	public static class Theme {

		public static class Header {
			private Count count;
			private boolean intro;
			private boolean tousu;
			private String photo;
			public Header(Count count, boolean intro, boolean tousu,
					String photo) {
				super();
				this.count = count;
				this.intro = intro;
				this.tousu = tousu;
				this.photo = photo;
			}
			
		}

		private View bubble;

		private Header header;

		private Question question;

		private int coin;

		private Date endDate;

		private boolean enable;

		public Theme(View bubble, Header header, Question question, int coin,
				Date endDate, boolean enable) {
			super();
			this.bubble = bubble;
			this.header = header;
			this.question = question;
			this.coin = coin;
			this.endDate = endDate;
			this.enable = enable;
		}

	}

	public ThemeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ThemeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ThemeLayout(Context context) {
		super(context);
		init();
	}

	private GridView gridView;

	private View selectCoinBtn;

	private View submitBtn;

	private PeronCountView peronCountView;

	private View tousuView;

	private View introView;

	private ImageView headerImageView;

	private ViewGroup bubbleView;

	private TextView coinView;

	private TextView timerView;

	private void init() {
		
		LayoutInflater.from(getContext()).inflate(R.layout.theme_layout, this);
		submitBtn = findViewById(R.id.submit);
		peronCountView = (PeronCountView) findViewById(R.id.personCountView);
		selectCoinBtn = findViewById(R.id.selectCoin);
		introView = findViewById(R.id.intro);
		gridView = (GridView) findViewById(R.id.gridview);

		tousuView = findViewById(R.id.tousu);
		headerImageView = (ImageView) findViewById(R.id.header_img);
		bubbleView = (ViewGroup) findViewById(R.id.bubble);
		coinView = (TextView) findViewById(R.id.coin);
		timerView = (TextView) findViewById(R.id.timer);

	}

	public void setTheme(Theme theme) {
		submitBtn.setEnabled(theme.enable);
		selectCoinBtn.setEnabled(theme.enable);
		bubbleView.addView(theme.bubble);
		introView.setVisibility(theme.header.intro ? View.VISIBLE : View.GONE);
		BitmapLoader.displayImage(getContext(), theme.header.photo,
				headerImageView);
		tousuView.setVisibility(theme.header.tousu ? View.VISIBLE : View.GONE);
		gridView.setAdapter(new QuestionAdapter(getContext(), theme.question));
		peronCountView.setCount(theme.header.count);
		coinView.setText(""+theme.coin);
		
		startTimer(90*1000);
		
		/*if (theme.endDate == null) {
			timerView.setText("今日已结束");
		} else {
			long left = new Date().getTime()-theme.endDate.getTime();
			if(left < 0){
				timerView.setText("今日已结束");
			}else{
				startTimer(left);
			}
			
		}*/
	}
	
	private static String format(long m){
		int second = (int) (m/60);
		m = m%60;
		String secondText = "";
		String mText = "";
		if(second < 10){
			secondText = "0"+second;
		}else{
			secondText = ""+second;
		}
		
		if(m<10){
			mText = "0"+m;
		}else{
			mText = ""+m;
		}
		return secondText+":"+mText;
		
		
	}

	private void startTimer(long total) {
		new CountDownTimer(total, 1000) {

		     public void onTick(long millisUntilFinished) {
		         timerView.setText(format(millisUntilFinished/1000));
		     }

		     public void onFinish() {
		    	 timerView.setText("00:00");
		     }
		  }.start();
	}

	public static View getBubbleCaiView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.bubble_cai_view,
				null);
		view.setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(DisplayUtils.Dp2Px(context, 71),DisplayUtils.Dp2Px(context, 66))));
		return view;

	}

	public static View getBubbleNoDaanView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.bubble_nodaan_view, null);
		view.setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(DisplayUtils.Dp2Px(context, 73),DisplayUtils.Dp2Px(context, 61))));
		return view;

	}

	public static View getBubbleDaanView(Context context, int color, String name) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.bubble_daan_view, null);
		BubbleAnswerView answerView = (BubbleAnswerView) view
				.findViewById(R.id.answerview);
		answerView.setColor(color);
		TextView contentView = (TextView) view.findViewById(R.id.content);
		contentView.setText(name);
		view.setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(DisplayUtils.Dp2Px(context, 73),DisplayUtils.Dp2Px(context, 61))));
		return view;

	}

	public static View getBubblePhotoView(Context context, String photo) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.bubble_photo_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.photo);
		BitmapLoader.displayImage(context, photo, imageView);
		view.setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(DisplayUtils.Dp2Px(context, 73),DisplayUtils.Dp2Px(context, 61))));
		return view;

	}

}
