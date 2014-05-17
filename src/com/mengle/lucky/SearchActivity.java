package com.mengle.lucky;

import java.util.ArrayList;
import java.util.List;

import org.apmem.tools.layouts.FlowLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SearchActivity extends BaseActivity implements OnClickListener {

	private FlowLayout flowLayout;
	
	private TextView contentView;

	public static void open(Context context) {
		context.startActivity(new Intent(context, SearchActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		contentView = (TextView) findViewById(R.id.content);
		flowLayout = (FlowLayout) findViewById(R.id.flow);
		findViewById(R.id.search_btn).setOnClickListener(this);
		findViewById(R.id.leftnav).setOnClickListener(this);
		init();
	}

	private void init() {
		List<Recommand> list = new ArrayList<SearchActivity.Recommand>() {
			{
				add(new Recommand("1", "关键字"));
				add(new Recommand("1", "关键字"));
				add(new Recommand("1", "关键字"));
				add(new Recommand("1", "关键字"));
				add(new Recommand("1", "关键字"));
			}
		};
		for (Recommand recommand : list) {
			TextView textView = (TextView) LayoutInflater.from(this).inflate(
					R.layout.search_item, null);
			SpannableString content = new SpannableString(recommand.name);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			textView.setText(content);
			flowLayout.addView(textView);
		}

	}

	private static class Recommand {
		private String id;
		private String name;

		public Recommand(String id, String name) {
			this.id = id;
			this.name = name;
		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leftnav:
			finish();
			break;
		case R.id.search_btn:
			SearchListActivity.open(this, contentView.getText().toString());
			break;
		default:
			break;
		}

	}

}
