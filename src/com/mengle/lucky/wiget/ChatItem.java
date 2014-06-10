package com.mengle.lucky.wiget;

import java.io.Serializable;

import com.mengle.lucky.R;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.DisplayUtils;
import com.mengle.lucky.utils.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class ChatItem extends FrameLayout{

	public enum Orientation{
		LEFT,RIGHT
	}
	
	public static class Chat implements Serializable{
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
		_init();
	}

	public ChatItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		_init();
	}

	public ChatItem(Context context) {
		super(context);
		_init();
	}
	private ImageView imageView;
	private TextView textView;
	private TextView timeView;
	private void _init(){
		if(orientation() == Orientation.LEFT){
			LayoutInflater.from(getContext()).inflate(R.layout.chat_left_item, this);
		}else{
			LayoutInflater.from(getContext()).inflate(R.layout.chat_right_item, this);
		}
		WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
		imageView = (ImageView) findViewById(R.id.photo);
		textView = ((TextView)findViewById(R.id.content));
		textView.setMaxWidth(wm.getDefaultDisplay().getWidth()-DisplayUtils.Dp2Px(getContext(), 120));
		timeView = ((TextView) findViewById(R.id.time));
	}
	
	protected abstract Orientation orientation();
	
	
	public void setChatItem(Chat chat){
		
		BitmapLoader.displayImage(getContext(), chat.photo, imageView);
		textView.setText(chat.content);
		timeView.setText(chat.time);
		textView.setBackgroundResource(chat.bgRes);
	}


	
	
}
