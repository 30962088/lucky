package com.mengle.lucky;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mengle.lucky.adapter.QuestionAdapter.Question;
import com.mengle.lucky.adapter.QuestionAdapter.QuestionItem;
import com.mengle.lucky.adapter.QuestionAdapter.Status;
import com.mengle.lucky.fragments.CommentFragment;
import com.mengle.lucky.fragments.SidingMenuFragment;
import com.mengle.lucky.network.CaiRequest;
import com.mengle.lucky.network.GameBetRequest;
import com.mengle.lucky.network.GrameGetRequest;
import com.mengle.lucky.network.GrameGetRequest.Params;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.Game;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.wiget.QuestionLayout;
import com.mengle.lucky.wiget.ResultDialog;
import com.mengle.lucky.wiget.ThemeLayout;
import com.mengle.lucky.wiget.PeronCountView.Count;
import com.mengle.lucky.wiget.ResultDialog.Result;
import com.mengle.lucky.wiget.ThemeLayout.OnBtnClickListener;
import com.mengle.lucky.wiget.ThemeLayout.Theme;
import com.mengle.lucky.wiget.ThemeLayout.Theme.Header;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class KanZhuangActivity extends SlidingFragmentActivity implements
		OnClickListener, OnBtnClickListener {

	public static void open(Context context, int game_id) {
		Intent intent = new Intent(context, KanZhuangActivity.class);
		intent.putExtra("game_id", game_id);
		context.startActivity(intent);
	}

	private ThemeLayout themeLayout;

	private QuestionItem lastItem;

	private int game_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		game_id = getIntent().getIntExtra("game_id", 0);
		// game_id = 4;
		setContentView(R.layout.kan_zhuang_layout);
		themeLayout = (ThemeLayout) findViewById(R.id.theme);
		findViewById(R.id.leftnav).setOnClickListener(this);
		request();
		initSlidingMenu();
	}

	private long endTime1;

	private GrameGetRequest.Result game;

	private void doSuccess(final GrameGetRequest.Result game) {
		this.game = game;
		fragment.setPraiseCount(game.getPraise());
		final List<QuestionItem> list = game.toQuestionList();

		Preferences.User user = new Preferences.User(this);

		long endTime = 0;
		try {
			endTime = Utils.parseDate(game.getStop_time()).getTime()
					- Utils.parseDate(game.getNow()).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.endTime1 = endTime;
		String[] colors = new String[] { "#a0d468", "#4fc0e8", "#e47134",
				"#e47134", "#e47134", "#e47134", "#e47134", "#e47134",
				"#e47134", "#e47134" };
		String[] options = new String[] { "A", "B", "C", "D", "E", "F", "G" };
		View bubbleView = null;
		if (user.getUid() == game.getCreator().get(0).getUid()) {
			if (game.getIs_publish_answer() == 1) {
				bubbleView = ThemeLayout.getBubbleDaanView(this,
						Color.parseColor(colors[game.getAnswer()]),
						options[game.getAnswer()]);
			} else {
				bubbleView = ThemeLayout.getBubbleNoDaanView(this);
			}
		} else {
			bubbleView = ThemeLayout.getBubblePhotoView(this, game.getCreator()
					.get(0).getAvatar());
		}

		Status status = null;

		switch (game.getState()) {
		case 1:
			if (endTime <= 0) {
				status = new Status(true, new ArrayList<Integer>() {
					{
						add(game.getAnswer());
					}
				});
			} else {
				status = new Status(false, null);
			}
			break;
		case 0:
			status = new Status(true, null);
			break;
		case 2:
			status = new Status(true, null);
			themeLayout.getLostView().setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		themeLayout.setOnBtnClickListener(this);
		/*
		 * themeLayout.setOnBtnClickListener(new OnBtnClickListener() {
		 * 
		 * public void onOKClick() { if (lastItem == null) {
		 * Utils.tip(KanZhuangActivity.this, "请选择答案。"); } else {
		 * ResultDialog.Status status; if (lastItem.isAnswer()) { status =
		 * ResultDialog.Status.SUCCESS; } else { status =
		 * ResultDialog.Status.FAIL; }
		 * 
		 * new ResultDialog(KanZhuangActivity.this, new Result(status,
		 * game.getGold_coin()));
		 * 
		 * } } });
		 */

		themeLayout.setTheme(new Theme(bubbleView, new Header(new Count(
				R.drawable.btn_count_blue, game.getJoin_count()), false, true,
				game.getImage()), new Question(game.getTitle(), status, list),
				game.getGold_coin(), endTime, endTime > 0 ? true : false));

		themeLayout.getGridView().setOnItemClickListener(
				new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (endTime1 > 0) {
							QuestionItem item = list.get(position);
							if (lastItem != null) {
								lastItem.setType(QuestionLayout.TYPE_NORMAL);
							}

							item.setType(QuestionLayout.TYPE_MOST);

							themeLayout.getAdapter().notifyDataSetChanged();

							lastItem = item;
						}

					}
				});
	}

	private CommentFragment fragment;

	private void initSlidingMenu() {
		fragment = CommentFragment.newInstance(game_id, 0);
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.RIGHT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadowright);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, fragment).commit();

		findViewById(R.id.right_comment).setOnClickListener(
				new OnClickListener() {

					public void onClick(View v) {
						toggle();

					}
				});

		/*
		 * sm.setSecondaryMenu(R.layout.menu_frame_two);
		 * sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		 */

	}

	private void request() {
		Preferences.User user = new Preferences.User(this);
		if (user.isLogin()) {
			final GrameGetRequest getRequest = new GrameGetRequest(new Params(
					user.getUid(), user.getToken(), game_id));

			RequestAsync.request(getRequest, new Async() {

				public void onPostExecute(Request request) {
					if (getRequest.getStatus() == Request.Status.SUCCESS) {
						doSuccess(getRequest.getResult());
					}

				}
			});
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leftnav:
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	public void onOKClick() {
		if (lastItem == null) {
			Utils.tip(KanZhuangActivity.this, "请选择答案。");
			return;
		}
		Preferences.User user = new Preferences.User(this);

		GameBetRequest betRequest = new GameBetRequest(
				new GameBetRequest.Param(user.getUid(), user.getToken(),
						game_id, lastItem.getId(), game.getGold_coin()));
		RequestAsync.request(betRequest, null);
	}

}
