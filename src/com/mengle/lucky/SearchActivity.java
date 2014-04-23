package com.mengle.lucky;

import java.util.ArrayList;
import java.util.List;

import org.apmem.tools.layouts.FlowLayout;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.widget.TextView;

public class SearchActivity extends BaseActivity{

	private FlowLayout flowLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		flowLayout = (FlowLayout) findViewById(R.id.flow);
		init();
	}

	private void init() {
		List<Recommand> list = new ArrayList<SearchActivity.Recommand>(){{
			add(new Recommand("1", "关键字"));
			add(new Recommand("1", "关键字"));
			add(new Recommand("1", "关键字"));
			add(new Recommand("1", "关键字"));
			add(new Recommand("1", "关键字"));
		}};
		for(Recommand recommand:list){
			TextView textView = (TextView) LayoutInflater.from(this).inflate(
					R.layout.search_item, null);
			SpannableString content = new SpannableString(recommand.name);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			textView.setText(content);
			flowLayout.addView(textView);
		}
		
		
	}
	
	private static class Recommand{
		private String id;
		private String name;
		public Recommand(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
	}
	
}
