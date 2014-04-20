package com.mengle.lucky.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BitmapLoader {
	

	private static ImageLoader imageLoader = null;
	
	private synchronized static ImageLoader getImageLoader(Context context) {
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
	
	
	public static void displayImage(Context context, String url,ImageView imageView){
		ImageLoader imageLoader = getImageLoader(context);
		imageLoader.displayImage(url, imageView);
	}
	
	
}
