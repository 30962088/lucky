package com.mengle.lucky;

import java.util.ArrayList;

import com.mengle.lucky.adapter.ChatListAdapter;
import com.mengle.lucky.adapter.ChatListAdapter.Item;
import com.mengle.lucky.wiget.ChatItem.Chat;
import com.mengle.lucky.wiget.ChatItem.Orientation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ChatActivity extends Activity{
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_layout);
		ChatListAdapter adapter = new ChatListAdapter(this, new ArrayList<ChatListAdapter.Item>(){{
			add(new Item(null, "2014.2.20"));
			add(new Item(
					new Chat(Orientation.LEFT, "", 
							"在吗?一起玩个局呀,我们的房间号是XXXXXX", 
							"21:30", R.drawable.bg_chat_left), null));
			add(new Item(null, "2014.2.27"));
			add(new Item(
					new Chat(Orientation.LEFT, "", 
							"在吗?一起玩个局呀,我们的房间号是XXXXXX", 
							"21:30", R.drawable.bg_chat_left), null));
			add(new Item(
					new Chat(Orientation.RIGHT, "", 
							"OK,马上到", 
							"21:32", R.drawable.bg_chat_right_blue), null));
		}});
		ListView listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
	}
	
}
