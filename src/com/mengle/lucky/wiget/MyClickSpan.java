package com.mengle.lucky.wiget;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class MyClickSpan extends ClickableSpan {
	private View.OnClickListener l;
	private int color;

	public MyClickSpan(int color,View.OnClickListener l) {
		super();
		this.color = color;
		this.l = l;
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		ds.setColor(color);// 设置颜色
		ds.setUnderlineText(true);
	}

	@Override
	public void onClick(View v) {
		l.onClick(v);
	}
}
