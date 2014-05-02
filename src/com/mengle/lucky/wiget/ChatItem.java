package com.mengle.lucky.wiget;

import com.mengle.lucky.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class ChatItem extends FrameLayout{

	public enum Orientation{
		LEFT,RIGHT
	}
	
	public static class Chat{
		private Orientation orientation;
		private String photo;
		private String content;
		private String time;
		private int bgRes;
		public Chat(Orientation orientation, String photo, String content,
				String time, int bgRes) {
			super();
			this.orientation = orientation;
			this.photo = photo;
			this.content = content;
			this.time = time;
			this.bgRes = bgRes;
		}
		
		
		
	}
	
	public ChatItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public ChatItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public ChatItem(Context context) {
		super(context);
		
	}
	
	private Chat chat;
	
	public void setChatItem(Chat chat){
		this.chat = chat;
		init();
	}
	
	private void init(){
		if()
		LayoutInflater.from(getContext()).inflate(R.layout.zhuang_list_item2, this);
	}

	
	
}
