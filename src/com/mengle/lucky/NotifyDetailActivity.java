package com.mengle.lucky;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.j256.ormlite.dao.Dao;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.model.Notice;
import com.mengle.lucky.wiget.IntroDialog;
import com.mengle.lucky.wiget.MyClickSpan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NotifyDetailActivity extends Activity {

	public static void open(Context context, int id) {
		Intent intent = new Intent(context, NotifyDetailActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}

	private int id;

	private TextView titleView;

	private TextView timeView;

	private TextView contentView;

	private Dao<Notice, Integer> dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notify_detail);
		try {
			dao = new DataBaseHelper(this).getNoticeDao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		id = getIntent().getIntExtra("id", 0);
		titleView = (TextView) findViewById(R.id.title);
		timeView = (TextView) findViewById(R.id.time);
		contentView = (TextView) findViewById(R.id.content);

		findViewById(R.id.back).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();

			}
		});

	}

	public enum Type {
		GAME, USER
	}

	private static class Symbol {
		
		private int id;
		private String name;
		private int start;
		private int end;
		private Type type;
		public Symbol(int id, String name, int start, int end, Type type) {
			super();
			this.id = id;
			this.name = name;
			this.start = start;
			this.end = end;
			this.type = type;
		}

		

	}

	private void setContent(String content) {

		
		List<Symbol> symbols = new ArrayList<Symbol>();
		{
			Pattern pattern = Pattern.compile("<(user|game):([0-9]{1,})>(.+?)<\\/(user|game)>");
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				int start = matcher.start();
				int end = matcher.end();
				int id = Integer.parseInt(matcher.group(2));
				String name = matcher.group(3);
				Type type = null;
				if(matcher.group(1).equals("user")){
					type = Type.USER;
				}else{
					type = Type.GAME;
				}
				symbols.add(new Symbol(id,name,start, end, type));
			}
		}
		
		/*{
			Pattern pattern = Pattern.compile("<game:([0-9]{1,})>(.+?)<\\/game>");
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				int start = matcher.start();
				int end = matcher.end();
				int id = Integer.parseInt(matcher.group(1));
				String name = matcher.group(2);
				symbols.add(new Symbol(id,name,start, end, Type.GAME));
			}
		}*/
		
		
		
		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		
		int reduce = 0;
		
		for (Symbol symbol : symbols) {
			int start = symbol.start+reduce;
			int end = symbol.end +reduce;
			sb.replace(start, end, symbol.name);
			reduce += (symbol.name.length()- (end-start));
			sb.setSpan(new UserClickSpan(symbol.id,symbol.type), start,start+symbol.name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		contentView.setText(sb);
		contentView.setClickable(true);
		contentView.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	private class UserClickSpan extends ClickableSpan{		
		
		private int id;
		
		private Type type;
		
		public UserClickSpan(int id,Type type) {
			this.id = id;
			this.type = type;
		}
		
		

		@Override
		public void onClick(View widget) {
			if(type == Type.USER){
				ZoneActivity.open(NotifyDetailActivity.this, id);
			}else{
				KanZhuangActivity.open(NotifyDetailActivity.this, id);
			}
			
		}

		
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			Notice notice = dao.queryForId(id);
			titleView.setText(notice.getTitle());
			timeView.setText(notice.getSend_time());
//			setContent("您发起的游戏<game:6>写代码累不累？</game>被<user:10002>qigemingzizhenfeishia</user>评论啦，点击查看详情");
//			setContent("<user:40>某某某</user>,<user:40>某某某</user>,<user:40>某某某</user>,<game:40>游戏</game>,<game:40>游戏</game>,我擦你妈妈吗啊啊啊啊啊");
			setContent(notice.getContent());
			notice.setChecked(false);
			dao.update(notice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
