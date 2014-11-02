package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.ZoneActivity;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.ChatLeftItem;
import com.mengle.lucky.wiget.ChatRightItem;
import com.mengle.lucky.wiget.ChatTitleItem;
import com.mengle.lucky.wiget.ChatItem.Chat;
import com.mengle.lucky.wiget.ChatItem.Orientation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChatListAdapter extends BaseAdapter{

	public static class Item{
		private Chat chat;
		private String title;
		public Item(Chat chat, String title) {
			super();
			this.chat = chat;
			this.title = title;
		}
		public Chat getChat() {
			return chat;
		}
		public String getTitle() {
			return title;
		}
		
		
	}
	
	private Context context;
	
	private List<Item> list;
	
	
	
	public ChatListAdapter(Context context, List<Item> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Item item = list.get(position);
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.chat_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setItem(item);
		
		return convertView;
	}
	
	private  class ViewHolder{
		private ChatLeftItem chatLeftItem;
		private ChatRightItem chatRightItem;
		private ChatTitleItem chatTitleItem;
		private ViewHolder(View view) {
			chatLeftItem = (ChatLeftItem) view.findViewById(R.id.left);
			chatRightItem = (ChatRightItem) view.findViewById(R.id.right);
			chatTitleItem = (ChatTitleItem) view.findViewById(R.id.title_layout);
		}
		private void setItem(final Item item){
			chatTitleItem.setVisibility(View.GONE);
			chatLeftItem.setVisibility(View.GONE);
			chatRightItem.setVisibility(View.GONE);
			if(item.chat != null){
				
				if(item.chat.getOrientation() == Orientation.LEFT){
					chatLeftItem.setVisibility(View.VISIBLE);
					chatLeftItem.getPhotoView().setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							ZoneActivity.open(context, item.chat.getUid());
							
						}
					});
					chatLeftItem.setChatItem(item.chat);
					chatRightItem.setVisibility(View.GONE);
					
				}else{
					chatRightItem.setVisibility(View.VISIBLE);
					chatRightItem.getPhotoView().setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							User user = new User(context);
							ZoneActivity.open(context, user.getUid());
							
						}
					});
					chatRightItem.setChatItem(item.chat);
					chatLeftItem.setVisibility(View.GONE);
				}
			}else if(item.title != null){
				chatRightItem.setVisibility(View.GONE);
				chatLeftItem.setVisibility(View.GONE);
				chatTitleItem.setVisibility(View.VISIBLE);
				chatTitleItem.setTitle(item.title);
			}
		}
		
	}

}
