package com.mengle.lucky.fragments;

import java.util.Date;
import java.util.List;

import com.mengle.lucky.MainActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.adapter.QuestionAdapter.Question;
import com.mengle.lucky.adapter.QuestionAdapter.QuestionItem;
import com.mengle.lucky.adapter.QuestionAdapter.Status;
import com.mengle.lucky.network.CaiRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.Game;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.wiget.PeronCountView.Count;
import com.mengle.lucky.wiget.ResultDialog.Result;
import com.mengle.lucky.wiget.QuestionLayout;
import com.mengle.lucky.wiget.ResultDialog;
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
import android.widget.AdapterView.OnItemClickListener;

public class CaiFragment extends Fragment {

	private ThemeLayout themeLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.cai_layout, null);
	}

	private QuestionItem lastItem;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		themeLayout = (ThemeLayout) view.findViewById(R.id.theme);

		request();

	}

	private void doSuccess(final Game game) {
		
		MainActivity activity =  (MainActivity)getActivity();
		
		activity.switchRight(CommentFragment.newInstance(game.getId(),game.getPraise()));
		
		final List<QuestionItem> list = game.toQuestionList();

		themeLayout.setOnBtnClickListener(new OnBtnClickListener() {
			
			public void onOKClick() {
				if(lastItem == null){
					Utils.tip(getActivity(), "请选择答案。");
				}else{
					ResultDialog.Status status;
					if(lastItem.isAnswer()){
						status = ResultDialog.Status.SUCCESS;
					}else{
						status = ResultDialog.Status.FAIL;
					}
					
					new ResultDialog(getActivity(), new Result(status, game.getGold_coin()));
					
				}
				
				
			}
		});
		
		themeLayout.setTheme(new Theme(ThemeLayout
				.getBubbleCaiView(getActivity()), new Header(new Count(
				R.drawable.btn_count, game.getJoin_count()), true, false, game
				.getImage()), new Question(game.getTitle(), new Status(false,
				null), list), game.getGold_coin(), 0, true));

		themeLayout.getGridView().setOnItemClickListener(
				new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						QuestionItem item = list.get(position);
						if (lastItem != null) {
							lastItem.setType(QuestionLayout.TYPE_NORMAL);
						}

						item.setType(QuestionLayout.TYPE_MOST);

						themeLayout.getAdapter().notifyDataSetChanged();

						lastItem = item;

					}
				});
	}

	private void request() {
		Preferences.User user = new Preferences.User(getActivity());
		if (user.isLogin()) {
			final CaiRequest caiRequest = new CaiRequest(new CaiRequest.Params(
					user.getUid(), user.getToken()));
			RequestAsync.request(caiRequest, new Async() {

				public void onPostExecute(Request request) {
					if (caiRequest.getStatus() == Request.Status.SUCCESS) {
						doSuccess(caiRequest.getGame());
					}

				}
			});
		}

	}

}
