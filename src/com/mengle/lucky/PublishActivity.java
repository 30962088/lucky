package com.mengle.lucky;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.mengle.lucky.KanZhuangPreviewActivity.PreviewModel;
import com.mengle.lucky.network.GameCategoryRequest;
import com.mengle.lucky.network.GameCreateRequest;
import com.mengle.lucky.network.GameCreateRequest.Param.Opt;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.GameCategoryRequest.Category;
import com.mengle.lucky.network.IUserGet.UserResult;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserMe.Callback;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.wiget.IntroDialog;
import com.mengle.lucky.wiget.LoadingPopup;
import com.mengle.lucky.wiget.MyClickSpan;
import com.mengle.lucky.wiget.RadioGroupLayout;
import com.mengle.lucky.wiget.RadioGroupLayout.RadioItem;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PublishActivity extends BaseActivity implements OnClickListener {

	private static final int SELECT_PHOTO = 100;

	private TextView nickView;

	private ImageView avatarView;

	private TextView totalCoinView;

	private ImageView imageView;

	private EditText titleView;

	private EditText question1View;

	private EditText question2View;

	private EditText question3View;

	private EditText jishuView;

	private EditText hourText;

	// private EditText minuteText;

	private RadioGroupLayout radioGroupLayout1;

	private RadioGroupLayout radioGroupLayout2;

	private RadioGroupLayout radioGroupLayout3;

	private EditText reasonText;

	private CheckBox btnRead;

	private TextView textRead;

	private ImageView headView;

	private void openGallery() {
		getImage(400, 300, new OnPhotoSelectionListener() {
			
			@Override
			public void onPhotoSelection(Uri uri) {
				imageView.setTag(uri.getPath());
				BitmapLoader.displayImage(PublishActivity.this, uri.toString(), imageView);
			}
		});
		
	}

	

	public static void open(Context context) {
		context.startActivity(new Intent(context, PublishActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		App.currentActivity = this;
		setContentView(R.layout.publish_layout);
		findViewById(R.id.leftnav).setOnClickListener(this);
		findViewById(R.id.shabi).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				findViewById(R.id.shabi).setVisibility(View.GONE);
				findViewById(R.id.caonima).setVisibility(View.VISIBLE);

			}
		});
		headView = (ImageView) findViewById(R.id.bg_top);
		btnRead = (CheckBox) findViewById(R.id.btn_read);
		textRead = (TextView) findViewById(R.id.text_read);

		ClickableSpan textClickable = new MyClickSpan(
				Color.parseColor("#5dc9e6"), new OnClickListener() {

					@Override
					public void onClick(View v) {
						IntroDialog
								.open(PublishActivity.this,
										"好运7之霸王条款",
										getResources().getString(
												R.string.zhuang_intro), null);
					}
				});
		SpannableString spanableInfo = new SpannableString("游戏规则。");
		spanableInfo.setSpan(textClickable, 0, 4,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textRead.setText(spanableInfo);
		textRead.setClickable(true);
		btnRead.setChecked(true);
		textRead.setMovementMethod(LinkMovementMethod.getInstance());
		reasonText = (EditText) findViewById(R.id.reasonView);
		totalCoinView = (TextView) findViewById(R.id.totalCoin);
		nickView = (TextView) findViewById(R.id.nickname);
		avatarView = (ImageView) findViewById(R.id.user_photo);
		titleView = (EditText) findViewById(R.id.titleview);
		question1View = (EditText) findViewById(R.id.question1);
		question2View = (EditText) findViewById(R.id.question2);
		question3View = (EditText) findViewById(R.id.question3);
		hourText = (EditText) findViewById(R.id.hour);
		// minuteText = (EditText) findViewById(R.id.minute);
		jishuView = (EditText) findViewById(R.id.jishu);
		findViewById(R.id.publish_btn).setOnClickListener(this);
		radioGroupLayout2 = (RadioGroupLayout) findViewById(R.id.radio2);
		radioGroupLayout2.setList(new ArrayList<RadioGroupLayout.RadioItem>() {
			{
				add(new RadioItem("2", "2倍"));
				add(new RadioItem("3", "3倍"));
				add(new RadioItem("4", "4倍"));
			}
		});
		radioGroupLayout3 = (RadioGroupLayout) findViewById(R.id.radio3);
		radioGroupLayout3.setList(new ArrayList<RadioGroupLayout.RadioItem>() {
			{
				add(new RadioItem("A", "A"));
				add(new RadioItem("B", "B"));
				add(new RadioItem("C", "C"));
			}
		});

		imageView = (ImageView) findViewById(R.id.add_pic);
		imageView.setOnClickListener(this);
		radioGroupLayout1 = (RadioGroupLayout) findViewById(R.id.radio1);
		findViewById(R.id.preview).setOnClickListener(this);
		loadCat();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UserMe.get(this, new Callback() {

			@Override
			public void onsuccess(UserResult userResult) {
				totalCoinView.setText("" + userResult.getGold_coin());
				nickView.setText(userResult.getNickname());
				avatarView.setTag(userResult.getAvatar());
				BitmapLoader.displayImage(PublishActivity.this,
						userResult.getHead(), headView);
				BitmapLoader.displayImage(PublishActivity.this,
						userResult.getAvatar(), avatarView);

			}
		});

	}

	private boolean validate(String title, String imagePath, String A,
			String B, String category, String jishu, String beishu,
			String hour, String miniute) {
		if (TextUtils.isEmpty(title)) {
			Utils.tip(this, "您还没有命题");
			return false;
		}
		/*
		 * if(TextUtils.isEmpty(imagePath)){ Utils.tip(this, "您还没有添加图片"); return
		 * false; }
		 */
		if (TextUtils.isEmpty(category)) {
			Utils.tip(this, "您还没有选择该问题的分类");
			return false;
		}
		if (TextUtils.isEmpty(A)) {
			Utils.tip(this, "您还没有填写选项A内容");
			return false;
		}
		if (TextUtils.isEmpty(B)) {
			Utils.tip(this, "您还没有填写选项B内容");
			return false;
		}
		if (TextUtils.isEmpty(beishu)) {
			Utils.tip(this, "您还没有填写倍数");
			return false;
		}

		if (TextUtils.isEmpty(jishu)) {
			Utils.tip(this, "您还没有填写基数");
			return false;
		}

		if (TextUtils.isEmpty(hour)) {
			Utils.tip(this, "您还没有小时倒计时");
			return false;
		}
		if (TextUtils.isEmpty(miniute)) {
			Utils.tip(this, "您还没有分钟倒计时");
			return false;
		}

		return true;

	}

	private void loadCat() {
		final GameCategoryRequest categoryRequest = new GameCategoryRequest(
				this);
		RequestAsync.request(categoryRequest, new Async() {

			@Override
			public void onPostExecute(Request request) {
				radioGroupLayout1.setList(Category
						.toRadioItemList(categoryRequest.getResult()));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_pic:
			onAddPic();
			break;
		case R.id.publish_btn:
			onPublish();
			break;
		case R.id.preview:
			onPreview();
			break;
		case R.id.leftnav:
			finish();
			break;
		default:
			break;
		}

	}

	private void onPreview() {
		String title = titleView.getText().toString();
		String imagePath = null;
		if (imageView.getTag() != null) {
			imagePath = imageView.getTag().toString();
		}
		String A = question1View.getText().toString();
		String B = question2View.getText().toString();
		String C = question3View.getText().toString();
		String cat = radioGroupLayout1.getValue();
		String jishu = jishuView.getText().toString();
		String beishu = radioGroupLayout2.getValue();
		String hour = hourText.getText().toString();
		// String miniute = minuteText.getText().toString();
		String miniute = "0";
		if (validate(title, imagePath, A, B, cat, jishu, beishu, hour, miniute)) {
			String fileuri = "";
			if (imagePath != null) {
				fileuri = Uri.fromFile(new File(imagePath)).toString();
			}
			KanZhuangPreviewActivity.open(this, new PreviewModel(avatarView
					.getTag().toString(), title, fileuri, A, B, C, jishu));
		}

	}

	private void onPublish() {
		String title = titleView.getText().toString();
		String imagePath = null;
		if (imageView.getTag() != null) {
			imagePath = imageView.getTag().toString();
		}
		String A = question1View.getText().toString();
		String B = question2View.getText().toString();
		String C = question3View.getText().toString();
		String cat = radioGroupLayout1.getValue();
		String jishu = jishuView.getText().toString();
		String beishu = radioGroupLayout2.getValue();
		String hour = hourText.getText().toString();
		// String miniute = minuteText.getText().toString();
		String miniute = "0";
		String reason = reasonText.getText().toString();
		String answer = radioGroupLayout3.getValue();
		if (validate(title, imagePath, A, B, cat, jishu, beishu, hour, miniute)) {
			if (!btnRead.isChecked()) {
				Utils.tip(this, "您还没有同意游戏规则");
				return;
			}
			List<Opt> opts = new ArrayList<GameCreateRequest.Param.Opt>();
			String[] contents = new String[] { A, B, C };
			for (int i = 0; i < contents.length; i++) {
				String content = contents[i];
				if (content == null || content.equals("")) {
					break;
				}
				int isanwser = 0;
				if (answer != null) {
					int index = ArrayUtils.indexOf(
							new String[] { "A", "B", "C" }, answer);
					if (index == i) {
						isanwser = 1;
					}
				}
				opts.add(new Opt(content, isanwser));
			}

			Preferences.User user = new Preferences.User(this);
			final GameCreateRequest createRequest = new GameCreateRequest(this,
					new GameCreateRequest.Param(user.getUid(), user.getToken(),
							title, Integer.parseInt(cat), 10,
							Integer.parseInt(jishu), Integer.parseInt(beishu),
							Integer.parseInt(hour), Integer.parseInt(miniute),
							opts, reason), imagePath);
			LoadingPopup.show(this);
			RequestAsync.request(createRequest, new Async() {

				@Override
				public void onPostExecute(Request request) {
					if (request.getStatus() == Status.SUCCESS) {
						Utils.tip(PublishActivity.this, "创建成功");
						LoadingPopup.hide(PublishActivity.this);
						if (createRequest.getGame() != null) {
							Intent intent = new Intent(PublishActivity.this,
									MainActivity.class);
							intent.setAction(MainActivity.ACTION_REFRESH_ZHUANG);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
									| Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(intent);
						}

					}

				}
			});
		}

	}

	private void onAddPic() {
		openGallery();

	}

}
