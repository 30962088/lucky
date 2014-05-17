package com.mengle.lucky;

import java.util.Date;
import java.util.List;

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
import com.mengle.lucky.wiget.QuestionLayout;
import com.mengle.lucky.wiget.ResultDialog;
import com.mengle.lucky.wiget.ThemeLayout;
import com.mengle.lucky.wiget.PeronCountView.Count;
import com.mengle.lucky.wiget.ResultDialog.Result;
import com.mengle.lucky.wiget.ThemeLayout.OnBtnClickListener;
import com.mengle.lucky.wiget.ThemeLayout.Theme;
import com.mengle.lucky.wiget.ThemeLayout.Theme.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class KanZhuangActivity extends Activity{

	private ThemeLayout themeLayout;
	
	private QuestionItem lastItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kan_zhuang_layout);
		themeLayout = (ThemeLayout)findViewById(R.id.theme);
	}
	
	private void doSuccess(final Game game) {

		final List<QuestionItem> list = game.toQuestionList();

		themeLayout.setOnBtnClickListener(new OnBtnClickListener() {
			
			public void onOKClick() {
				if(lastItem == null){
					Utils.tip(KanZhuangActivity.this, "请选择答案。");
				}else{
					ResultDialog.Status status;
					if(game.getId() == lastItem.getId()){
						status = ResultDialog.Status.SUCCESS;
					}else{
						status = ResultDialog.Status.FAIL;
					}
					
					new ResultDialog(KanZhuangActivity.this, new Result(status, game.getGold_coin()));
					
				}
				
				
			}
		});
		
		themeLayout.setTheme(new Theme(ThemeLayout
				.getBubbleCaiView(this), new Header(new Count(
				R.drawable.btn_count, game.getJoin_count()), true, false, game
				.getImage()), new Question(game.getTitle(), new Status(false,
				null), list), game.getGold_coin(), new Date(11), true));

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
		Preferences.User user = new Preferences.User(this);
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
