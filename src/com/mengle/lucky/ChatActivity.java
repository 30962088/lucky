package com.mengle.lucky;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;
import com.mengle.lucky.adapter.ChatListAdapter;
import com.mengle.lucky.adapter.ChatListAdapter.Item;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserMeLetterSendRequest;
import com.mengle.lucky.network.model.MsgMe;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.ChatItem.Chat;
import com.mengle.lucky.wiget.ChatItem.Orientation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ChatActivity extends Activity implements OnEditorActionListener{
	
	public static void open(Context context, int uid){
		Intent intent = new Intent(context,ChatActivity.class);
		intent.putExtra("uid", uid);
		context.startActivity(intent);
	}
	
	private int uid;
	
	private List<Item> list = new ArrayList<Item>(); 
	
	private ChatListAdapter adapter;
	
	private ListView listView;
	
	private EditText msgText;
	
	private DataBaseHelper helper;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		helper = new DataBaseHelper(this);
		uid = getIntent().getIntExtra("uid", 0);
		setContentView(R.layout.chat_layout);
		msgText = (EditText) findViewById(R.id.msgText);
		msgText.setOnEditorActionListener(this);
		listView = (ListView) findViewById(R.id.listview);
		
		try {
			getList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getList() throws SQLException{
		String sql = "select id,content,send_time,1 from  msgme where toUid="+uid+" union all select id,content,send_time,0 from msg where sender like '{\"uid%"+uid+"%,%'";
		
		GenericRawResults<String[]> results =  helper.getMsgDao().queryRaw(sql);
		for(String[] row : results){
			String id = row[0];
			String content = row[1];
			String send_time = row[2];
			String type = row[3];
			if(TextUtils.equals(type, "1")){
				list.add(new Item(new Chat(Orientation.RIGHT, "", 
							content, 
							send_time, R.drawable.bg_chat_left), null));
			}else{
				list.add(new Item(new Chat(Orientation.LEFT, "", 
						content, 
						send_time, R.drawable.bg_chat_right_blue), null));

			}
		}
		adapter = new ChatListAdapter(this, list);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
            onsubmit();
            return true;
        }
		return false;
	}

	private void onsubmit() {
		final String msg = msgText.getText().toString();
		if(TextUtils.isEmpty(msg)){
			Utils.tip(this, "请输入内容");
			return;
		}
		final Date date = new Date();
		Preferences.User user = new Preferences.User(this);
		UserMeLetterSendRequest sendRequest = new UserMeLetterSendRequest(
				new UserMeLetterSendRequest.Param(user.getUid(), user.getToken(), uid, msg));
		RequestAsync.request(sendRequest, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				
				list.add(new Item(new Chat(Orientation.RIGHT, "", 
						msg, Utils.formatDate(date)
						, R.drawable.bg_chat_left), null));
				adapter.notifyDataSetChanged();
				MsgMe msgMe = new MsgMe(uid, msg, date);
				try {
					helper.getMsgMeDao().create(msgMe);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	
}
