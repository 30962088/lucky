package com.mengle.lucky;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.model.Notice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NotifyDetailActivity extends Activity{
	
	public static void open(Context context,int id){
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
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			Notice notice = dao.queryForId(id);
			titleView.setText(notice.getTitle());
			timeView.setText(notice.getSend_time());
			contentView.setText(notice.getContent());
			notice.setChecked(false);
			dao.update(notice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
