package com.mengle.lucky.wiget;

import java.io.File;
import java.sql.SQLException;

import com.mengle.lucky.MainActivity;
import com.mengle.lucky.NotifyCenterActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.UserListActivity;
import com.mengle.lucky.ZoneActivity;
import com.mengle.lucky.adapter.PhotoListAdapter.Photo;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.MsgGetRequest;
import com.mengle.lucky.network.NoticeGetRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.UserMeAvatarUploadRequest;
import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.Msg;
import com.mengle.lucky.network.model.Notice;
import com.mengle.lucky.network.UserMeFollow;
import com.mengle.lucky.network.UserMeHeadUploadRequest;
import com.mengle.lucky.network.UserMeUnFollow;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.DisplayUtils;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.Preferences.Image;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.PhotoListDialog.OnResultClick;
import com.mengle.lucky.wiget.PhotoListDialog.Type;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserHeadView extends FrameLayout implements OnClickListener,
		OnResultClick {

	public enum Sex {
		MALE, FEMALE, UNKONWN
	}

	public static class UserHeadData {
		private int uid;
		private boolean hasNewMsg;
		private String head;
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
		private boolean fallow;

		public UserHeadData(int uid, boolean hasNewMsg, String photo,
				String head, String nick, Sex sex, int coin, int win, int eq,
				int fail, String level, int focusCount, int fansCount,
				int fansNewCount) {
			this.uid = uid;
			this.hasNewMsg = hasNewMsg;
			this.photo = photo;
			this.head = head;
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

		public void setFallow(boolean fallow) {
			this.fallow = fallow;
		}

		public boolean isFallow() {
			return fallow;
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

	private CheckBox btnFocusView;

	private View btnMsgView;

	private ImageView iconHead;

	private UserHeadData userHeadData;

	private TextView fansText;

	private TextView focusText;

	private View newGameTip;

	private void init() {
		setVisibility(View.GONE);
		LayoutInflater.from(getContext()).inflate(R.layout.user_head, this);
		newGameTip = findViewById(R.id.new_game_tip);
		iconHead = (ImageView) findViewById(R.id.icon_head);
		iconHead.setOnClickListener(this);
		btnFocusView = (CheckBox) findViewById(R.id.btn_focus);
		btnFocusView.setOnClickListener(this);
		newIconView = findViewById(R.id.icon_new);
		photoView = (ImageView) findViewById(R.id.photo);
		if (getContext() instanceof ZoneActivity) {
			int padding = DisplayUtils.Dp2Px(getContext(), 5);
			int size = DisplayUtils.Dp2Px(getContext(), 65);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					size, size);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			params.topMargin = DisplayUtils.Dp2Px(getContext(), 21);
			photoView.setLayoutParams(params);
			photoView.setPadding(padding, padding, padding, padding);

		}
		fansText = (TextView) findViewById(R.id.fans_text);
		focusText = (TextView) findViewById(R.id.focus_text);
		nickView = (TextView) findViewById(R.id.nickname);
		femaleView = findViewById(R.id.icon_female);
		maleView = findViewById(R.id.icon_male);
		coinView = (TextView) findViewById(R.id.coin);
		statusView = (TextView) findViewById(R.id.status);
		levelView = (TextView) findViewById(R.id.level);
		focusCountView = (TextView) findViewById(R.id.focus_count);
		fansCountView = (TextView) findViewById(R.id.fans_count);
		fansNewView = (TextView) findViewById(R.id.fans_new);
		btnMsgView = findViewById(R.id.icon_msg);
		btnMsgView.setOnClickListener(this);
		photoView.setOnClickListener(this);
		findViewById(R.id.btn_toFollower).setOnClickListener(this);
		findViewById(R.id.btn_toFollowing).setOnClickListener(this);
	}

	public void setData(UserHeadData data) {
		setVisibility(View.VISIBLE);
		this.userHeadData = data;
		Preferences.User user = new Preferences.User(getContext());
		final ImageLoader imageLoader = Utils.getImageLoader(getContext());
		imageLoader.displayImage(data.head, iconHead,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						imageLoader.displayImage(
								"http://res.joypaw.com/head/default/1.jpg",
								iconHead);
					}
				});
		String s = "";
		Integer uid = user.getUid();
		if (uid != null && uid.intValue() == data.uid) {
			photoView.setEnabled(true);
			btnMsgView.setVisibility(View.VISIBLE);
			btnFocusView.setVisibility(View.GONE);
			s = "我";

		} else {

			if (data.sex == Sex.FEMALE) {
				s = "她";
			} else {
				s = "他";
			}

			photoView.setEnabled(false);
			btnMsgView.setVisibility(View.GONE);
			btnFocusView.setChecked(data.isFallow());
			btnFocusView.setVisibility(View.VISIBLE);
		}

		fansText.setText("关注" + s + "的用户");
		focusText.setText(s + "关注的用户");
		newIconView.setVisibility(data.hasNewMsg ? View.VISIBLE : View.GONE);
		imageLoader.displayImage(data.photo, photoView,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						imageLoader.displayImage(
								"http://res.joypaw.com/avatar/default/1.jpg",
								photoView);
					}
				});
		nickView.setText(data.nick);
		if (data.sex == Sex.FEMALE) {
			femaleView.setVisibility(View.VISIBLE);
			maleView.setVisibility(View.GONE);
		} else if (data.sex == Sex.MALE) {
			femaleView.setVisibility(View.GONE);
			maleView.setVisibility(View.VISIBLE);
		} else {
			femaleView.setVisibility(View.GONE);
			maleView.setVisibility(View.GONE);
		}
		coinView.setText("" + data.coin);
		statusView.setText(getContext().getString(R.string.user_head_status,
				data.win, data.fail, data.eq));
		levelView.setText(data.level);
		focusCountView.setText("" + data.focusCount);
		fansCountView.setText("" + data.fansCount);
		fansNewView.setText("" + data.fansNewCount);
	}

	public void onClick(View v) {
		Preferences.User user = new Preferences.User(getContext());
		switch (v.getId()) {
		case R.id.btn_toFollower:
			UserListActivity.open(getContext(), userHeadData.uid, fansText
					.getText().toString(), UserListActivity.Type.FOLLERS);
			break;
		case R.id.btn_toFollowing:
			UserListActivity.open(getContext(), userHeadData.uid, focusText
					.getText().toString(), UserListActivity.Type.FOLLOWS);
			break;
		case R.id.btn_focus:
			if(!user.isLogin()){
				Utils.tip(getContext(), "请先登录");
				btnFocusView.setChecked(false);
				return;
			}
			onFocus();
			break;
		case R.id.icon_msg:
			NotifyCenterActivity.open(getContext());
			break;
		case R.id.photo:
			if (getContext() instanceof MainActivity) {
				ZoneActivity.open(getContext(), userHeadData.uid);
			} else if (user.getUid() == userHeadData.uid) {
				new PhotoListDialog(userHeadData.photo, getContext(),
						Type.AVATAR, this, userHeadData.photo);
			}

			break;
		case R.id.icon_head:
			if (!(getContext() instanceof MainActivity)
					&& user.getUid() != null && user.getUid().intValue() == userHeadData.uid) {
				new PhotoListDialog(userHeadData.head, getContext(), Type.HEAD,
						this, userHeadData.head);
			}

			break;
		default:
			break;
		}

	}

	private void onFocus() {
		Preferences.User user = new Preferences.User(getContext());
		Request request = null;
		if (!btnFocusView.isChecked()) {
			request = new UserMeUnFollow(getContext(),
					new UserMeUnFollow.Params(user.getUid(), user.getToken(),
							userHeadData.uid));
		} else {
			request = new UserMeFollow(getContext(), new UserMeFollow.Params(
					user.getUid(), user.getToken(), userHeadData.uid));
		}
		RequestAsync.request(request, new Async() {

			@Override
			public void onPostExecute(Request request) {
				if (getContext() instanceof ZoneActivity) {
					((ZoneActivity) getContext()).onResume();
				}

			}
		});

	}

	@Override
	public void onResult(Type type, final Photo photo) {
		final String uri = photo.getPhoto();
		Preferences.User user = new Preferences.User(getContext());
		UserMeAvatarUploadRequest.Param param1;
		UserMeHeadUploadRequest.Param param2;
		if (uri.startsWith("http")) {
			param1 = new UserMeAvatarUploadRequest.Param(user.getUid(),
					user.getToken(), uri);
			param2 = new UserMeHeadUploadRequest.Param(user.getUid(),
					user.getToken(), uri);
		} else {
			File file = new File(Uri.parse(uri).getPath());
			param1 = new UserMeAvatarUploadRequest.Param(user.getUid(),
					user.getToken(), file);
			param2 = new UserMeHeadUploadRequest.Param(user.getUid(),
					user.getToken(), file);
		}

		if (type == Type.AVATAR) {
			final UserMeAvatarUploadRequest uploadRequest = new UserMeAvatarUploadRequest(
					getContext(), param1);
			RequestAsync.request(uploadRequest, new Async() {

				@Override
				public void onPostExecute(Request request) {

					if (request.getStatus() == Status.SUCCESS) {

						userHeadData.photo = uploadRequest.getUser()
								.getAvatar();
						photo.setPhoto(userHeadData.photo);
						BitmapLoader.displayImage(getContext(),
								userHeadData.photo, photoView);
						
					}

				}
			});

		} else {

			final UserMeHeadUploadRequest uploadRequest = new UserMeHeadUploadRequest(
					getContext(), param2);
			RequestAsync.request(uploadRequest, new Async() {

				@Override
				public void onPostExecute(Request request) {
					if (request.getStatus() == Status.SUCCESS) {
						userHeadData.head = uploadRequest.getUser().getHead();
						photo.setPhoto(userHeadData.head);
						BitmapLoader.displayImage(getContext(),
								userHeadData.head, iconHead);

						
						
					}

				}
			});

		}

	}

	private int requestCount = 0;

	public void displayNewGame(boolean val) {
		newGameTip.setVisibility(val ? View.VISIBLE : View.GONE);
	}

	private void showNewMsg() {
		long count = Msg.getNewCount(getContext())
				+ Notice.getNewCount(getContext());
		if (count > 0) {
			newIconView.setVisibility(View.VISIBLE);
		} else {
			newIconView.setVisibility(View.GONE);
		}
	}

	public void checkNewMsg() {
		requestCount = 0;
		Preferences.User user = new Preferences.User(getContext());
		if (userHeadData != null && user.getUid() == userHeadData.uid) {
			NoticeGetRequest noticeGetRequest = new NoticeGetRequest(
					getContext(), new NoticeGetRequest.Params(user.getUid(),
							user.getToken()));
			MsgGetRequest msgGetRequest = new MsgGetRequest(getContext(),
					new MsgGetRequest.Params(user.getUid(), user.getToken()));

			RequestAsync.request(noticeGetRequest, new Async() {

				@Override
				public void onPostExecute(Request request) {
					requestCount++;
					if (requestCount == 2) {
						showNewMsg();
					}

				}
			});

			RequestAsync.request(msgGetRequest, new Async() {

				@Override
				public void onPostExecute(Request request) {
					requestCount++;
					if (requestCount == 2) {
						showNewMsg();
					}

				}
			});

		}
	}

}
