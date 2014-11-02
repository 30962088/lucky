package com.mengle.lucky.fragments;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mengle.lucky.KanZhuangActivity;
import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.adapter.QuestionAdapter.Question;
import com.mengle.lucky.adapter.QuestionAdapter.QuestionItem;
import com.mengle.lucky.adapter.QuestionAdapter.Status;
import com.mengle.lucky.network.CaiRequest;
import com.mengle.lucky.network.GameBetRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserDayAwardRequest;
import com.mengle.lucky.network.model.Award;
import com.mengle.lucky.network.model.Game;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.wiget.AwardPopup;
import com.mengle.lucky.wiget.LoadingPopup;
import com.mengle.lucky.wiget.ResultDialog;
import com.mengle.lucky.wiget.PeronCountView.Count;

import com.mengle.lucky.wiget.QuestionLayout;

import com.mengle.lucky.wiget.ThemeLayout;
import com.mengle.lucky.wiget.ThemeLayout.OnBtnClickListener;
import com.mengle.lucky.wiget.ThemeLayout.Theme;
import com.mengle.lucky.wiget.ThemeLayout.Theme.Header;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CaiFragment extends Fragment implements OnBtnClickListener {

	private ThemeLayout themeLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.cai_layout, null);
	}

	private QuestionItem lastItem;

	private TextView dayView;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		themeLayout = (ThemeLayout) view.findViewById(R.id.theme);
		dayView = (TextView) view.findViewById(R.id.dayView);
		fillDay();
		request();

	}

	private void fillDay() {
		Date date = new Date();
		String weekday = Utils.getWeekday(date);
		int day = date.getDate();
		dayView.setText(weekday + " " + day);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Preferences.User user = new Preferences.User(getActivity());
		if (user.isLogin() && !Award.isAward()) {
			final UserDayAwardRequest awardRequest = new UserDayAwardRequest(
					getActivity(), new UserDayAwardRequest.Param(user.getUid(),
							user.getToken()));
			RequestAsync.request(awardRequest, new Async() {

				@Override
				public void onPostExecute(Request request) {
					int award = awardRequest.getResult().getAward_gold_coin();
					if (award > 0) {
						AwardPopup.open(getActivity(), award);
						Award.setAward(award);
					}

				}
			});
		}
	}

	private long endTime1;

	private Game game;

	private void doSuccess(final Game game) {

		this.game = game;

		MainActivity activity = (MainActivity) getActivity();

		activity.switchRight(CommentFragment.newInstance(game.getId(),
				game.getPraise()));

		final List<QuestionItem> list = game.toQuestionList();

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

		View bubbleView = ThemeLayout.getBubbleCaiView(getActivity());

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

		themeLayout.setTheme(new Theme(game.getId(), bubbleView, new Header(
				new Count(R.drawable.btn_count, game.getJoin_count()), true,
				false, game.getImage()), new Question(game.getTitle(), status,
				list), game.getGold_coin(), game.getOdds(), endTime,
				endTime > 0 ? true : false, "今日已结束"));

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

	private void request() {
		Preferences.User user = new Preferences.User(getActivity());
		final CaiRequest caiRequest = new CaiRequest(getActivity(),
				new CaiRequest.Params(user.getUid(), user.getToken()));
		RequestAsync.request(caiRequest, new Async() {

			public void onPostExecute(Request request) {
				if (caiRequest.getStatus() == Request.Status.SUCCESS) {
					doSuccess(caiRequest.getGame());
				}

			}
		});

	}

	@Override
	public void onOKClick() {
		Preferences.User user = new Preferences.User(getActivity());
		if (!user.isLogin()) {
			Utils.tip(getActivity(), "由于您没有登录所以无法完成下注");
			return;
		}
		if (lastItem == null) {
			Utils.tip(getActivity(), "请选择答案。");
			return;
		}

		LoadingPopup.show(getActivity());

		GameBetRequest betRequest = new GameBetRequest(getActivity(),
				new GameBetRequest.Param(user.getUid(), user.getToken(),
						game.getId(), lastItem.getId(), game.getGold_coin()));
		RequestAsync.request(betRequest, new Async() {

			@Override
			public void onPostExecute(Request request) {
				if (request.getStatus() == com.mengle.lucky.network.Request.Status.SUCCESS) {
					themeLayout.countPlus();
					new ResultDialog(getActivity(), new ResultDialog.Result(
							ResultDialog.Status.CHECK, game.getGold_coin()));
				}

			}
		});

	}

}
