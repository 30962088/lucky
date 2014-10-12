package com.mengle.lucky.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mengle.lucky.App;
import com.mengle.lucky.network.model.Game;
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
	
	
	
	public GameCreateRequest(Param param, String image) {
		super();
		this.param = param;
		if(image != null){
			File originImage = new File(image);
			File compressImage = cropImage(originImage, 640, 400);
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
	
	private static Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(photoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;
	    Bitmap bitmap = null;
	    if(targetW<photoW||targetH<photoH){
	    	bitmap = ImageResize.resize(new File(photoPath),targetW , targetH, ResizeMode.AUTOMATIC);
	    }else{

		    bmOptions.inJustDecodeBounds = false;
		    bmOptions.inSampleSize = 1;
		    bmOptions.inPurgeable = true;

		    bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
	    } 
	    return bitmap;
	}
	
	public static File cropImage(File image, int width,int height){
		FileOutputStream out = null;
		File outputDir = App.getInstance().getCacheDir();
		File outputFile = null;
		try {
			outputFile = File.createTempFile("prefix", "extension", outputDir);
	        Bitmap bitmap = resizeBitmap(image.toString(),width,height);
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
