package com.mengle.lucky;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mengle.lucky.adapter.QuestionAdapter.Question;
import com.mengle.lucky.adapter.QuestionAdapter.QuestionItem;
import com.mengle.lucky.adapter.QuestionAdapter.Status;
import com.mengle.lucky.wiget.QuestionLayout;
import com.mengle.lucky.wiget.ThemeLayout;
import com.mengle.lucky.wiget.PeronCountView.Count;
import com.mengle.lucky.wiget.ThemeLayout.Theme;
import com.mengle.lucky.wiget.ThemeLayout.Theme.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class KanZhuangPreviewActivity extends Activity implements OnClickListener{
	public static class PreviewModel implements Serializable {
		private String avatar;
		private String title;
		private String imagePath;
		private String A;
		private String B;
		private String C;
		private String coin;
		public PreviewModel(String avatar, String title, String imagePath,
				String a, String b, String c, String coin) {
			super();
			this.avatar = avatar;
			this.title = title;
			this.imagePath = imagePath;
			A = a;
			B = b;
			C = c;
			this.coin = coin;
		}
		
		
	}
	
	
	public static void open(Context context,PreviewModel model){
		Intent intent = new Intent(context, KanZhuangPreviewActivity.class);
		intent.putExtra("model", model);
		context.startActivity(intent);
	}
	
	private ThemeLayout themeLayout;
	
	
	private PreviewModel model;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		model = (PreviewModel) getIntent().getSerializableExtra("model");
		setContentView(R.layout.kan_zhuang_layout);
		themeLayout = (ThemeLayout)findViewById(R.id.theme);
		findViewById(R.id.leftnav).setOnClickListener(this);
		findViewById(R.id.right_comment).setVisibility(View.GONE);
		doSuccess();
	}
	
	private void doSuccess() {
		String[] colors = new String[]{"#a0d468","#4fc0e8","#e47134","#e47134","#e47134","#e47134","#e47134","#e47134","#e47134","#e47134"};
		List<QuestionItem> list = new ArrayList<QuestionItem>();
		if(!TextUtils.isEmpty(model.A)){
			list.add(new QuestionItem(0, "A", model.A, 0, false, Color.parseColor(colors[0]), QuestionLayout.TYPE_NORMAL));
		}
		if(!TextUtils.isEmpty(model.B)){
			list.add(new QuestionItem(0, "B", model.B, 0, false, Color.parseColor(colors[1]), QuestionLayout.TYPE_NORMAL));
		}
		if(!TextUtils.isEmpty(model.C)){
			list.add(new QuestionItem(0, "C", model.C, 0, false, Color.parseColor(colors[2]), QuestionLayout.TYPE_NORMAL));
		}
		
		
		
		themeLayout.setTheme(new Theme(null, ThemeLayout
				.getBubblePhotoView(this,model.avatar), new Header(new Count(
				R.drawable.btn_count_blue, 0), false, false, model
				.imagePath), new Question(model.title, new Status(false,
				null), list), Integer.parseInt(model.coin), 0, true,""));

		
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
	
}
