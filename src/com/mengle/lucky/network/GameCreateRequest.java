package com.mengle.lucky.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mengle.lucky.App;
import com.mengle.lucky.network.model.Game;
import com.mengle.lucky.utils.ScalingUtilities;
import com.svenkapudija.imageresizer.operations.ImageResize;
import com.svenkapudija.imageresizer.operations.ResizeMode;

public class GameCreateRequest extends Request{

	public static class Param{
		
		public static class Opt{
			protected String content;
			protected int is_answer = 0;
			public Opt(String content, int is_answer) {
				super();
				this.content = content;
				this.is_answer = is_answer;
			}
			public Opt(String content) {
				super();
				this.content = content;
			}
			
		}
		protected int uid;
		protected String token;
		protected String title;
		protected int cid;
		protected int cost_gold_coin;
		protected int gold_coin;
		protected int odds;
		protected int hour;
		protected int minute;
		protected String opts;
		protected String answer_remark;
		public Param(int uid, String token, String title, int cid,
				int cost_gold_coin, int gold_coin, int odds, int hour,
				int minute, List<Opt> opts, String answer_remark) {
			this.uid = uid;
			this.token = token;
			this.title = title;
			this.cid = cid;
			this.cost_gold_coin = cost_gold_coin;
			this.gold_coin = gold_coin;
			this.odds = odds;
			this.hour = hour;
			this.minute = minute;
			this.opts = new Gson().toJson(opts);
			this.answer_remark = answer_remark;
		}
		
		
		
	}
	
	
	
	private Param param;
	
	private File image;
	
	private Game game;
	
	
	
	public GameCreateRequest(Context context, Param param, String image) {
		super(context);
		this.param = param;
//		this.image = new File(image);
		if(image != null){
			File originImage = new File(image);
			File compressImage = cropImage(originImage, 450, 300);
			if(compressImage != null){
				this.image = compressImage;
			}else{
				this.image =originImage ;
			}
		}
		
	}

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			game = new Gson().fromJson(data, Game.class);
			
		}
		
	}
	public Game getGame() {
		return game;
	}

	@Override
	public void onError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResultError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerError(int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return HOST+"game/create/";
	}
	


	public static Bitmap getBitmapFromFile(File dst, int width, int height) {
	    if (null != dst && dst.exists()) {
	        BitmapFactory.Options opts = null;
	        if (width > 0 && height > 0) {
	            opts = new BitmapFactory.Options();
	            opts.inJustDecodeBounds = true;
	            BitmapFactory.decodeFile(dst.getPath(), opts);
	            // 计算图片缩放比例
	            final int minSideLength = Math.min(width, height);
	            opts.inSampleSize = computeSampleSize(opts, minSideLength,
	                    width * height);
	            opts.inJustDecodeBounds = false;
	            opts.inInputShareable = true;
	            opts.inPurgeable = true;
	        }
	        try {
	            return BitmapFactory.decodeFile(dst.getPath(), opts);
	        } catch (OutOfMemoryError e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	 


	 

	public static int computeSampleSize(BitmapFactory.Options options,
	        int minSideLength, int maxNumOfPixels) {
	    int initialSize = computeInitialSampleSize(options, minSideLength,
	            maxNumOfPixels);

	    int roundedSize;
	    if (initialSize <= 8) {
	        roundedSize = 1;
	        while (roundedSize < initialSize) {
	            roundedSize <<= 1;
	        }
	    } else {
	        roundedSize = (initialSize + 7) / 8 * 8;
	    }

	    return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
	        int minSideLength, int maxNumOfPixels) {
	    double w = options.outWidth;
	    double h = options.outHeight;

	    int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
	            .sqrt(w * h / maxNumOfPixels));
	    int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
	            .floor(w / minSideLength), Math.floor(h / minSideLength));

	    if (upperBound < lowerBound) {
	        // return the larger one when there is no overlapping zone.
	        return lowerBound;
	    }

	    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
	        return 1;
	    } else if (minSideLength == -1) {
	        return lowerBound;
	    } else {
	        return upperBound;
	    }
	}
	 

	
	private static Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
		Bitmap bitmap =	getBitmapFromFile(new File(photoPath), targetW, targetH);
		
	    return crop(bitmap,targetW,targetH);
	}
	
	public static Bitmap crop(Bitmap bitmap,int twidth,int theight) {
		int width = bitmap.getWidth(), height = bitmap.getHeight();
		int cropWidth = 0, cropHeight = 0;
		if(width == height){
			cropWidth = width;
			cropHeight = height;
		}else if (width > height) {
			cropHeight = height;
			cropWidth = height;
		} else {
			cropWidth = width;
			cropHeight = width;
		}

		int x = (width - cropWidth) / 2;

		int y = (height - cropHeight) / 2;

		Bitmap bitmap1 = Bitmap.createBitmap(bitmap, x, y, cropWidth, cropHeight);
		
		Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, twidth, theight, true);
		
		bitmap1.recycle();
		
		return bitmap2;

	}
	
	public static File cropImage(File image, int width,int height){
		FileOutputStream out = null;
		File outputDir = App.getInstance().getCacheDir();
		File outputFile = null;
		Bitmap bitmap = null;
		try {
			outputFile = File.createTempFile("prefix", "extension", outputDir);
	        bitmap =ScalingUtilities.scaleCenterCrop(BitmapFactory.decodeFile(image.toString()) ,width,height);
	        out = new FileOutputStream(outputFile);
	        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			try {
		        if (out != null) {
		            out.close();
		        }
		        if(bitmap != null && !bitmap.isRecycled()){
		        	bitmap.recycle();
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		return outputFile;
	}
	
	@Override
	protected List<BinaryBody> fillFiles() {
		if(image == null){
			return null;
		}
		List<BinaryBody> bodies = new ArrayList<Request.BinaryBody>();
		bodies.add(new BinaryBody("image.jpg",image, "image/jpeg", "image"));
		return bodies;
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(param);
	}

}
