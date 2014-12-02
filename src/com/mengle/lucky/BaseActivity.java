package com.mengle.lucky;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.mengle.lucky.network.PingRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.Request.Status;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.PingRequest.Params;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.Preferences.User;
import com.umeng.analytics.MobclickAgent;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}
	
	public void ping(){
		User user = new User(this);
		if (user.isLogin()) {
			PingRequest request = new PingRequest(this, new Params(
					user.getUid(), user.getToken()));
			RequestAsync.request(request, new Async() {

				@Override
				public void onPostExecute(Request request) {
					if (request.getStatus() == Status.ERROR) {
						Intent intent = new Intent(BaseActivity.this,
								MainActivity.class);
						intent.setAction(MainActivity.ACTION_LOGOUT);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_SINGLE_TOP);
						BaseActivity.this.startActivity(intent);
					}

				}
			});
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public static interface OnPhotoSelectionListener {
		public void onPhotoSelection(Uri uri);
	}

	private OnPhotoSelectionListener onPhotoSelectionListener;

	public void getImage(int width, int height,
			OnPhotoSelectionListener onPhotoSelectionListener) {
		scaleWidth = width;
		scaleHeight = height;
		this.onPhotoSelectionListener = onPhotoSelectionListener;
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);
	}

	public static final int SELECT_PHOTO = 999;

	public static final int PIC_CROP = 998;
	
	private int scaleWidth;
	
	private int scaleHeight;
	
	private Uri cropImage;

	private void performCrop(Uri picUri) {
		try {
			File file = new File(Environment.getExternalStorageDirectory()+"/luck7");
			file.mkdirs();
			cropImage = Uri.fromFile(File.createTempFile("prefix", ".jpg", file));
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", scaleWidth);
			cropIntent.putExtra("aspectY", scaleHeight);
			// indicate output X and Y
			cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropImage);
			cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			cropIntent.putExtra("outputX", scaleWidth);
			cropIntent.putExtra("outputY", scaleHeight);
			// retrieve data on return
			cropIntent.putExtra("scale", true);
			cropIntent.putExtra("return-data", false);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			onSelectImage(picUri);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void onSelectImage(Uri selectedImage) {
		if (!selectedImage.toString().startsWith("file:")) {
			selectedImage = Uri.fromFile(new File(Utils.getRealPathFromURI(
					this, selectedImage)));
		}

		onPhotoSelectionListener.onPhotoSelection(selectedImage);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = data.getData();
				performCrop(selectedImage);
			}
			break;
		case PIC_CROP:
			if (data != null) {
				// get the returned data
				/*Bundle extras = data.getExtras();
				System.out.println(extras)*/;
				onSelectImage(cropImage);
				/*// get the cropped bitmap
				Bitmap selectedBitmap = extras.getParcelable("data");

				FileOutputStream out = null;
				try {
					File outputFile = File.createTempFile("prefix", ".png", App
							.getInstance().getCacheDir());
					out = new FileOutputStream(outputFile);
					selectedBitmap
							.compress(Bitmap.CompressFormat.PNG, 100, out);
					onSelectImage(Uri.fromFile(outputFile));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (out != null) {
							out.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/

			}
			break;
		}
	}

}
