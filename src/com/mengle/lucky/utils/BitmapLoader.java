package com.mengle.lucky.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.mengle.lucky.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BitmapLoader {
	

	private static ImageLoader imageLoader = null;
	
	public synchronized static ImageLoader getImageLoader(Context context) {
		if (imageLoader == null) {
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					context).denyCacheImageMultipleSizesInMemory()
					.memoryCacheExtraOptions(768, 1280)
					.memoryCache(new UsingFreqLimitedMemoryCache(5 * 1024 * 1024))
					.memoryCacheSize(5 * 1024 * 1024)
					.discCacheSize(50 * 1024 * 1024)
					.discCacheFileNameGenerator(new Md5FileNameGenerator())
					.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 1)
					.tasksProcessingOrder(QueueProcessingType.LIFO).build();
			imageLoader = ImageLoader.getInstance();
			// 初始化ImageLoader的与配置。
			imageLoader.init(config);;
		}

		return imageLoader;
	}
	

	public enum DisplayOptions {
		IMG(new Builder().showImageForEmptyUri(R.drawable.empty)
				.showImageOnLoading(R.drawable.empty).cacheInMemory(true)
				.cacheOnDisc(true).build());

		DisplayImageOptions options;

		DisplayOptions(DisplayImageOptions options) {
			this.options = options;
		}

		public DisplayImageOptions getOptions() {
			return options;
		}
	}
	
	public static String getPhoto(String photo){
		return !TextUtils.isEmpty(photo)?photo:"drawable://" + R.drawable.test_img_user;
	}
	
	public static String getHead(String head){
		return !TextUtils.isEmpty(head)?head:"drawable://" + R.drawable.bg_user;
	}
	
	public static void displayImage(Context context, String url,ImageView imageView){
		ImageLoader imageLoader = getImageLoader(context);
		imageLoader.displayImage(url, imageView,DisplayOptions.IMG.getOptions());
	}
	
	
}
