package com.mengle.lucky.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.mengle.lucky.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BitmapLoader {
	

	private static ImageLoader imageLoader = null;
	
	public synchronized static ImageLoader getImageLoader(Context context) {
		if (imageLoader == null) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true).build();
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					context).defaultDisplayImageOptions(options)
					.memoryCacheSizePercentage(33).build();
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(config);
		}

		return imageLoader;
	}
	
	public static String getPhoto(String photo){
		return !TextUtils.isEmpty(photo)?photo:"drawable://" + R.drawable.test_img_user;
	}
	
	public static String getHead(String head){
		return !TextUtils.isEmpty(head)?head:"drawable://" + R.drawable.bg_user;
	}
	
	public static void displayImage(Context context, String url,ImageView imageView){
		ImageLoader imageLoader = getImageLoader(context);
		imageLoader.displayImage(url, imageView);
	}
	
	
}
