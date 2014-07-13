package com.mengle.lucky.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.DiscCacheUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMWXHandler;

import android.net.NetworkInfo;
import android.view.animation.Transformation;

/**
 * 
 * @author zhangzimeng
 * 
 */
public class Utils {

	public static String getString(String str) {
		return str == null ? "" : str;
	}

	public static Bitmap convertViewToBitmap(View view) {
		view.destroyDrawingCache();
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		
		return bitmap;
	}
	
	public static UMSocialService getUMSocialService(Context context){
		UMSocialService mController =UMServiceFactory.getUMSocialService("com.umeng.login", RequestType.SOCIAL);
		/*String appID = "wx1897ea1cb217a00e";
		// 微信图文分享必须设置一个url 
		String contentUrl = "http://www.umeng.com/social";
		// 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
		UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(context,new UMWXHandler(context, appID));
		//设置分享标题
//		wxHandler.setWXTitle("友盟社会化组件很不错");
		
		// 支持微信朋友圈
		UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(context,appID, contentUrl) ;*/
//		circleHandler.setCircleTitle("友盟社会化组件还不错...");
		return mController;
	}
	
	public static class Wechat{
		
		private Context context;
		
		private static final int THUMB_SIZE = 150;
		
		private IWXAPI api;
		
		public Wechat(Context context) {
			
			this.context = context;
			api = WXAPIFactory.createWXAPI(context,"wx1897ea1cb217a00e");
			api.registerApp("wx1897ea1cb217a00e");
		}
		
		public void sharePhoto(Bitmap bmp,int scene){
			if(!api.isWXAppInstalled()){
				Utils.tip(context, "微信未安装");
				return;
			}
			
			
			
			
			
			WXImageObject imgObj = new WXImageObject(bmp);
			WXMediaMessage msg = new WXMediaMessage();
			msg.mediaObject = imgObj;
			
			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
			bmp.recycle();
			msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);  // 设置缩略图

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("img");
			req.message = msg;
			req.scene = scene;
			api.sendReq(req);
		}
		
		private String buildTransaction(final String type) {
			return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
		}
		
		public static void share(Context context,Bitmap bitmap,SHARE_MEDIA media){
			int scene = 0;
			switch (media) {
			case WEIXIN:
				scene = SendMessageToWX.Req.WXSceneSession;
				break;
			case WEIXIN_CIRCLE:
				scene = SendMessageToWX.Req.WXSceneTimeline;
				break;
			default:
				break;
			}
			new Wechat(context).sharePhoto(bitmap, scene);
		}
		
	}
	
	

	public static boolean isMyAppRunning(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
		ComponentName componentInfo = taskInfo.get(0).topActivity;
		Log.d("",
				"CURRENT Activity ::"
						+ taskInfo.get(0).topActivity.getClassName()
						+ "   Package Name :  "
						+ componentInfo.getPackageName());
		if (TextUtils.equals(componentInfo.getPackageName(),
				context.getPackageName())) {
			return true;
		}
		return false;
	}

	public static String getWeekday(Date date) {
		String[] models = { "Sunday", "Monday", "Tuesday", "Wednesday",
				"Thursday", "Friday", "Saturday" };

		return models[date.getDay()];
	}

	public static String formatDay12Hour(String str) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd a h:mm",
				Locale.US);
		String res = "";
		try {
			Date date = parseDate(str);
			res = format.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;

	}

	public static String format12Hour(String str) {
		SimpleDateFormat format = new SimpleDateFormat("a h:mm", Locale.US);
		String res = "";
		try {
			Date date = parseDate(str);
			res = format.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;

	}

	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static Date parseDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(date);
	}

	public static String formatDate(long date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(date));
	}

	public static String getRealPathFromURI(Context context, Uri contentUri) {

		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj, // Which
																				// columns
																				// to
				// return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	private static ImageLoader imageLoader = null;

	public static int dpToPx(Context context, int dp) {
		return (int) (dp * context.getResources().getDisplayMetrics().density);
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

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

	public static void setGridViewHeightBasedOnChildren(GridView gridView) {
		BaseAdapter listAdapter = (BaseAdapter) gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int count = listAdapter.getCount();
		View listItem = listAdapter.getView(0, null, gridView);
		listItem.measure(0, 0); // 计算子项View 的宽高
		totalHeight = listItem.getMeasuredHeight(); // 统计所有子项的总高度
		int yu = count % 4;
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		if (yu > 0) {
			params.height = (count - yu) / 4 * (totalHeight + 10) + totalHeight;
		} else {
			params.height = count / 4 * totalHeight + (count / 4 - 1) * 10;
		}
		gridView.setLayoutParams(params);
	}

	/**
	 * Use md5 encoded code value
	 * 
	 * @param sInput
	 *            clearly @ return md5 encrypted password
	 */
	public static String getMD5(String sInput) {

		String algorithm = "";
		if (sInput == null) {
			return "null";
		}
		try {
			algorithm = System.getProperty("MD5.algorithm", "MD5");
		} catch (SecurityException se) {
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte buffer[] = sInput.getBytes();

		for (int count = 0; count < sInput.length(); count++) {
			md.update(buffer, 0, count);
		}
		byte bDigest[] = md.digest();
		BigInteger bi = new BigInteger(bDigest);
		return (bi.toString(16));
	}

	public static boolean isValidEmailAddress(String email) {
		boolean stricterFilter = true;
		String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
		String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
		String emailRegex = stricterFilter ? stricterFilterString : laxString;
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static void tip(final Context context, final String str) {
		new Handler(context.getMainLooper()).post(new Runnable() {

			public void run() {
				Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

			}
		});

	}

	private static ConnectivityManager connMgr;

	public static boolean isMobileNetworkAvailable(Context con) {
		if (null == connMgr) {
			connMgr = (ConnectivityManager) con
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		NetworkInfo wifiInfo = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobileInfo = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (wifiInfo != null && wifiInfo.isAvailable()) {
			return true;
		} else if (mobileInfo != null && mobileInfo.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	public static void expand(final View v) {
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {

			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? LayoutParams.WRAP_CONTENT
						: (int) (targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration(200);
		v.startAnimation(a);
	}

	public static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight
							- (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration(200);
		v.startAnimation(a);
	}

	public static boolean isValidURL(String url) {

		URL u = null;

		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}

		try {
			u.toURI();
		} catch (URISyntaxException e) {
			return false;
		}

		return true;
	}

	private static final String TAG = "zzm";

	public static Bitmap getBitmap(String path) {

		final int IMAGE_MAX_SIZE = 600000; // 1.2MP

		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, o);
		// BitmapFactory.decodeStream(in, null, o);

		int scale = 1;
		while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
			scale++;
		}
		Log.d(TAG, "scale = " + scale + ", orig-width: " + o.outWidth
				+ ", orig-height: " + o.outHeight);

		Bitmap b = null;

		if (scale > 1) {
			scale--;
			// scale to max possible inSampleSize that still yields an image
			// larger than target
			o = new BitmapFactory.Options();
			o.inSampleSize = scale;
			b = BitmapFactory.decodeFile(path, o);

			// resize to desired dimensions
			int height = b.getHeight();
			int width = b.getWidth();
			Log.d(TAG, "1th scale operation dimenions - width: " + width
					+ ", height: " + height);

			double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
			double x = (y / height) * width;

			Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
					(int) y, true);
			b.recycle();
			b = scaledBitmap;

			System.gc();
		} else {
			b = BitmapFactory.decodeFile(path);
		}

		return b;

	}

}
