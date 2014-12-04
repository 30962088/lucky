package com.mengle.lucky;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.mengle.lucky.network.AppLoginRequest;
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
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity implements LocationListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				com.mengle.lucky.push.Utils.getMetaValue(this, "api_key"));

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
	
	public void loginRequest() {

		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);

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
	
	private boolean islogin = false;
	
	private LocationManager mlocManager;
	
	@Override
	public void onLocationChanged(Location location) {
		if (location.getLatitude() != 0 && location.getLongitude() != 0) {

			mlocManager.removeUpdates(this);
			try {
				synchronized (this) {
					if (!islogin) {
						islogin = true;
						User user = new User(this);
						int newUser = user.getNewuser();
						user.setNewuser(0);
						ApplicationInfo appInfo = this.getPackageManager()
								.getApplicationInfo(getPackageName(),
										PackageManager.GET_META_DATA);
						String channel = appInfo.metaData
								.getString("UMENG_CHANNEL");

						PackageInfo pinfo = getPackageManager().getPackageInfo(
								getPackageName(), 0);
						AppLoginRequest request = new AppLoginRequest(this,
								new AppLoginRequest.Params(user.getUid(),
										channel, "" + location.getLongitude(),
										"" + location.getLatitude(),
										pinfo.versionName, newUser));

						RequestAsync.request(request, null);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
