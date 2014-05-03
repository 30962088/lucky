package com.mengle.lucky.wiget;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class ChatItem extends FrameLayout{

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
		public Orientation getOrientation() {
			return orientation;
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
	
	protected abstract Orientation orientation();
	
	private Chat chat;
	
	public void setChatItem(Chat chat){
		this.chat = chat;
		init();
	}
	
	private void init(){
		if(orientation() == Orientation.LEFT){
			LayoutInflater.from(getContext()).inflate(R.layout.chat_left_item, this);
		}else{
			LayoutInflater.from(getContext()).inflate(R.layout.chat_right_item, this);
		}
		
		ImageView imageView = (ImageView) findViewById(R.id.photo);
		BitmapLoader.displayImage(getContext(), chat.photo, imageView);
		TextView textView = ((TextView)findViewById(R.id.content)); 
		textView.setText(chat.content);
		((TextView) findViewById(R.id.time)).setText(chat.time);
		textView.setBackgroundResource(chat.bgRes);
	}

	
	
}
