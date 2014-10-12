package com.mengle.lucky.wiget;

import java.util.Date;
import java.util.TimerTask;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.QuestionAdapter;
import com.mengle.lucky.adapter.QuestionAdapter.Question;
import com.mengle.lucky.network.GameComplainRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.IUserGet.UserResult;
import com.mengle.lucky.network.UserMe.Callback;
import com.mengle.lucky.network.model.Tousu;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.DisplayUtils;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.wiget.PeronCountView.Count;
import com.mengle.lucky.wiget.ResultDialog.Result;
import com.mengle.lucky.wiget.ResultDialog.Status;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ThemeLayout extends FrameLayout implements OnClickListener {

	public static interface OnBtnClickListener {
		public void onOKClick();
	}

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

		private Integer id;

		private View bubble;

		private Header header;

		private Question question;

		private int coin;

		private long endDate;

		private boolean enable;

		private String endText;

		private int odd;

		public Theme(Integer id, View bubble, Header header, Question question,
				int coin, int odd, long endDate, boolean enable, String endText) {
			super();
			this.id = id;
			this.bubble = bubble;
			this.header = header;
			this.question = question;
			this.coin = coin;
			this.odd = odd;
			this.endDate = endDate;
			this.enable = enable;
			this.endText = endText;
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

	private TextView titleView;

	private OnBtnClickListener onBtnClickListener;

	private View lostView;

	private int totalCoin;

	public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
		this.onBtnClickListener = onBtnClickListener;
	}

	private void init() {

		setVisibility(View.GONE);
		LayoutInflater.from(getContext()).inflate(R.layout.theme_layout, this);
		lostView = findViewById(R.id.lost);
		submitBtn = findViewById(R.id.submit);
		titleView = (TextView) findViewById(R.id.title);

		submitBtn.setOnClickListener(this);
		peronCountView = (PeronCountView) findViewById(R.id.personCountView);
		selectCoinBtn = findViewById(R.id.selectCoin);
		introView = findViewById(R.id.intro);
		gridView = (GridView) findViewById(R.id.gridview);
		
		
		findViewById(R.id.intro).setOnClickListener(this);
		
		tousuView = findViewById(R.id.tousu);
		tousuView.setOnClickListener(this);
		headerImageView = (ImageView) findViewById(R.id.header_img);
		bubbleView = (ViewGroup) findViewById(R.id.bubble);
		coinView = (TextView) findViewById(R.id.coin);
		timerView = (TextView) findViewById(R.id.timer);

	}

	private QuestionAdapter adapter;

	private Theme theme;
	
	private int currentOdd = 1;

	public void setTheme(final Theme theme) {
		this.theme = theme;
		if(new Preferences.User(getContext()).isLogin()){
			UserMe.get(getContext(), new Callback() {

				@Override
				public void onsuccess(UserResult userResult) {
					totalCoin = userResult.getGold_coin();

				}
			});
		}
		
		setVisibility(View.VISIBLE);
		gridView.setNumColumns(theme.question.getList().size());
		adapter = new QuestionAdapter(getContext(), theme.question);
		titleView.setText(theme.question.getTitle());
		submitBtn.setEnabled(theme.enable);
		selectCoinBtn.setEnabled(theme.enable);
		bubbleView.addView(theme.bubble);
		introView.setVisibility(theme.header.intro ? View.VISIBLE : View.GONE);
		BitmapLoader.displayImage(getContext(), theme.header.photo,
				headerImageView);
		tousuView.setVisibility(theme.header.tousu ? View.VISIBLE : View.GONE);
		gridView.setAdapter(adapter);
		peronCountView.setCount(theme.header.count);
		coinView.setText("" + theme.coin);
		coinView.setTag(theme.coin);
		selectCoinBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int coin = (Integer) coinView.getTag();
				if (currentOdd == theme.odd){
					currentOdd = 0;
				}
				currentOdd ++;
				
				coin = theme.coin * currentOdd;
				
				if (coin > totalCoin) {
					Utils.tip(getContext(), "您拥有的金币已不足下注");
					return;
				}
				coinView.setText("" + coin);
				coinView.setTag(coin);

			}
		});

		if (theme.endDate <= 0) {
			timerView.setText(theme.endText);
		} else {
			startTimer(theme.endDate);

		}
	}

	public int getCoin() {
		return (Integer) coinView.getTag();
	}

	public GridView getGridView() {
		return gridView;
	}

	public QuestionAdapter getAdapter() {
		return adapter;
	}

	private static String format2(long m) {
		m = m / 60;
		int second = (int) (m / 60);
		m = m % 60;
		String secondText = "";
		String mText = "";
		if (second < 10) {
			secondText = "0" + second;
		} else {
			secondText = "" + second;
		}

		if (m < 10) {
			mText = "0" + m;
		} else {
			mText = "" + m;
		}
		return secondText + ":" + mText;

	}

	private static String format(long m) {
		int second = (int) (m / 60);
		m = m % 60;
		String secondText = "";
		String mText = "";
		if (second < 10) {
			secondText = "0" + second;
		} else {
			secondText = "" + second;
		}

		if (m < 10) {
			mText = "0" + m;
		} else {
			mText = "" + m;
		}
		return secondText + ":" + mText;

	}

	private void startTimer(long total) {
		new CountDownTimer(total, 1000) {

			public void onTick(long millisUntilFinished) {
				timerView.setText(format2(millisUntilFinished / 1000));
			}

			public void onFinish() {
				timerView.setText("00:00");
			}
		}.start();
	}

	public static View getBubbleCaiView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.bubble_cai_view, null);
		view.setLayoutParams(new ViewGroup.LayoutParams(
				new ViewGroup.LayoutParams(DisplayUtils.Dp2Px(context, 71),
						DisplayUtils.Dp2Px(context, 66))));
		return view;

	}

	public static View getBubbleNoDaanView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.bubble_nodaan_view, null);

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
		return view;

	}

	public static View getBubblePhotoView(Context context, String photo) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.bubble_photo_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.photo);
		BitmapLoader.displayImage(context, photo, imageView);
		
		return view;

	}
	
	public void countPlus(){
		peronCountView._setCount(peronCountView.getCount()+1);
	}

	public View getLostView() {
		return lostView;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit:

			if (onBtnClickListener != null) {
				onBtnClickListener.onOKClick();
			}

			break;
		case R.id.tousu:
			final Preferences.User user = new Preferences.User(getContext());
			if(Tousu.getTousu(user.getUid(), theme.id)){
				Utils.tip(getContext(), "您已投诉过该竞猜请勿重复投诉");
				return;
			}
			GameComplainRequest complainRequest = new GameComplainRequest(
					new GameComplainRequest.Param(user.getUid(),
							user.getToken(), theme.id));
			RequestAsync.request(complainRequest, new Async() {
				
				@Override
				public void onPostExecute(Request request) {
					new Tousu(user.getUid(), theme.id, true).save();
					
				}
			});
			break;
		case R.id.intro:
			GameIntroDialog.open(getContext());
			break;
		default:
			break;
		}

	}

}
