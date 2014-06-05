package com.mengle.lucky.wiget;

import java.io.File;

import com.mengle.lucky.MainActivity;
import com.mengle.lucky.NotifyCenterActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.UserListActivity;
import com.mengle.lucky.ZoneActivity;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.UserMeAvatarUploadRequest;
import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserMeFollow;
import com.mengle.lucky.network.UserMeHeadUploadRequest;
import com.mengle.lucky.network.UserMeUnFollow;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.DisplayUtils;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.PhotoListDialog.OnResultClick;
import com.mengle.lucky.wiget.PhotoListDialog.Type;

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
import android.widget.TextView;

public class UserHeadView extends FrameLayout implements OnClickListener,
		OnResultClick {

	public enum Sex {
		MALE, FEMALE
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

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.user_head, this);
		iconHead = (ImageView) findViewById(R.id.icon_head);
		iconHead.setOnClickListener(this);
		btnFocusView = (CheckBox) findViewById(R.id.btn_focus);
		btnFocusView.setOnClickListener(this);
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
		btnMsgView = findViewById(R.id.icon_msg);
		btnMsgView.setOnClickListener(this);
		photoView.setOnClickListener(this);
		findViewById(R.id.btn_toFollower).setOnClickListener(this);
		findViewById(R.id.btn_toFollowing).setOnClickListener(this);
	}

	public void setData(UserHeadData data) {
		this.userHeadData = data;
		Preferences.User user = new Preferences.User(getContext());
		BitmapLoader.displayImage(getContext(), data.head, iconHead);
		if (user.getUid() == data.uid) {
			photoView.setEnabled(true);
			btnMsgView.setVisibility(View.VISIBLE);
			btnFocusView.setVisibility(View.GONE);
		} else {
			photoView.setEnabled(false);
			btnMsgView.setVisibility(View.GONE);
			btnFocusView.setChecked(data.isFallow());
			btnFocusView.setVisibility(View.VISIBLE);
		}
		newIconView.setVisibility(data.hasNewMsg ? View.VISIBLE : View.GONE);
		BitmapLoader.displayImage(getContext(), data.photo, photoView);
		nickView.setText(data.nick);
		if (data.sex == Sex.FEMALE) {
			femaleView.setVisibility(View.VISIBLE);
			maleView.setVisibility(View.GONE);
		} else {
			femaleView.setVisibility(View.GONE);
			maleView.setVisibility(View.VISIBLE);
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
			UserListActivity.open(getContext(), userHeadData.uid,
					UserListActivity.Type.FOLLERS);
			break;
		case R.id.btn_toFollowing:
			UserListActivity.open(getContext(), userHeadData.uid,
					UserListActivity.Type.FOLLOWS);
			break;
		case R.id.btn_focus:
			onFocus();
			break;
		case R.id.icon_msg:
			NotifyCenterActivity.open(getContext());
			break;
		case R.id.photo:
			if (getContext() instanceof MainActivity) {
				ZoneActivity.open(getContext(), userHeadData.uid);
			} else if (user.getUid() == userHeadData.uid) {
				new PhotoListDialog(getContext(), Type.AVATAR, this,
						userHeadData.photo);
			}

			break;
		case R.id.icon_head:
			if (!(getContext() instanceof MainActivity)
					&& user.getUid() == userHeadData.uid) {
				new PhotoListDialog(getContext(), Type.HEAD, this,
						userHeadData.head);
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
			request = new UserMeUnFollow(new UserMeUnFollow.Params(
					user.getUid(), user.getToken(), userHeadData.uid));
		} else {
			request = new UserMeFollow(new UserMeFollow.Params(user.getUid(),
					user.getToken(), userHeadData.uid));
		}
		RequestAsync.request(request, new Async() {

			@Override
			public void onPostExecute(Request request) {
				// TODO Auto-generated method stub

			}
		});

	}

	public String getRealPathFromURI(Uri contentUri) {

		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContext().getContentResolver().query(contentUri, proj, // Which columns to
														// return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	public void onResult(Type type, final String uri) {
		Preferences.User user = new Preferences.User(getContext());
		File file = new File(getRealPathFromURI(Uri.parse(uri)));
		if (type == Type.AVATAR) {
			
			
			if(file.exists()){
				UserMeAvatarUploadRequest uploadRequest = new UserMeAvatarUploadRequest(new UserMeAvatarUploadRequest.Param(user.getUid(), user.getToken(), file));
				RequestAsync.request(uploadRequest, new Async() {
					
					@Override
					public void onPostExecute(Request request) {
						if(request.getStatus() == Status.SUCCESS){
							userHeadData.photo = uri;
							BitmapLoader.displayImage(getContext(), uri, photoView);
						}
						
						
					}
				});
			}
			
			
		} else {
			if(file.exists()){
				UserMeHeadUploadRequest uploadRequest = new UserMeHeadUploadRequest(new UserMeHeadUploadRequest.Param(user.getUid(), user.getToken(), file));
				RequestAsync.request(uploadRequest, new Async() {
					
					@Override
					public void onPostExecute(Request request) {
						if(request.getStatus() == Status.SUCCESS){
							userHeadData.head = uri;
							BitmapLoader.displayImage(getContext(), uri, iconHead);
						}
						
						
					}
				});
			}
			
		}

	}

}
