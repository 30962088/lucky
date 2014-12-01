package com.mengle.lucky;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.baidu.frontia.FrontiaApplication;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.AppLoginRequest;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.model.Notice;
import com.mengle.lucky.utils.Dirctionary;
import com.mengle.lucky.utils.Preferences.User;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class App extends FrontiaApplication implements LocationListener {

	private static App instance;

	public static App getInstance() {
		return instance;
	}

	private LocationManager mlocManager;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		// TelephonyManager tm = (TelephonyManager)
		// getSystemService(Context.TELEPHONY_SERVICE);
		Dirctionary.init(this);
		request();

		// Thread.setDefaultUncaughtExceptionHandler(handler);

	}

	public static final String DOUBLE_LINE_SEP = "\n\n";

	public static final String SINGLE_LINE_SEP = "\n";

	UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {

		public void uncaughtException(Thread thread, Throwable e) {
			System.err.println(e);
			StackTraceElement[] arr = e.getStackTrace();
			final StringBuffer report = new StringBuffer(e.toString());
			final String lineSeperator = "-------------------------------\n\n";
			report.append(DOUBLE_LINE_SEP);
			report.append("--------- Stack trace ---------\n\n");
			for (int i = 0; i < arr.length; i++) {
				report.append("    ");
				report.append(arr[i].toString());
				report.append(SINGLE_LINE_SEP);
			}
			report.append(lineSeperator);
			// If the exception was thrown in a background thread inside
			// AsyncTask, then the actual exception can be found with getCause
			report.append("--------- Cause ---------\n\n");
			Throwable cause = e.getCause();
			if (cause != null) {
				report.append(cause.toString());
				report.append(DOUBLE_LINE_SEP);
				arr = cause.getStackTrace();
				for (int i = 0; i < arr.length; i++) {
					report.append("    ");
					report.append(arr[i].toString());
					report.append(SINGLE_LINE_SEP);
				}
			}
			// Getting the Device brand,model and sdk verion details.
			report.append(lineSeperator);
			report.append("--------- Device ---------\n\n");
			report.append("Brand: ");
			report.append(Build.BRAND);
			report.append(SINGLE_LINE_SEP);
			report.append("Device: ");
			report.append(Build.DEVICE);
			report.append(SINGLE_LINE_SEP);
			report.append("Model: ");
			report.append(Build.MODEL);
			report.append(SINGLE_LINE_SEP);
			report.append("Id: ");
			report.append(Build.ID);
			report.append(SINGLE_LINE_SEP);
			report.append("Product: ");
			report.append(Build.PRODUCT);
			report.append(SINGLE_LINE_SEP);
			report.append(lineSeperator);
			report.append("--------- Firmware ---------\n\n");
			report.append("SDK: ");
			report.append(Build.VERSION.SDK);
			report.append(SINGLE_LINE_SEP);
			report.append("Release: ");
			report.append(Build.VERSION.RELEASE);
			report.append(SINGLE_LINE_SEP);
			report.append("Incremental: ");
			report.append(Build.VERSION.INCREMENTAL);
			report.append(SINGLE_LINE_SEP);
			report.append(lineSeperator);

			// CrashActivity.open(getApplicationContext(), report.toString());

			System.exit(0);
		}
	};

	private int isNew;

	private void request() {
		User user = new User(this);
		isNew = user.isFirstLogin() ? 1 : 0;

		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);

	}

	public static Activity currentActivity;

	private boolean islogin = false;

	@Override
	public void onLocationChanged(Location location) {
		mlocManager.removeUpdates(this);
		try {
			synchronized (this) {
				if (!islogin) {
					islogin = true;
					ApplicationInfo appInfo = this.getPackageManager()
							.getApplicationInfo(getPackageName(),
									PackageManager.GET_META_DATA);
					String channel = appInfo.metaData.getString("UMENG_CHANNEL");
					User user = new User(this);
					PackageInfo pinfo = getPackageManager().getPackageInfo(
							getPackageName(), 0);
					AppLoginRequest request = new AppLoginRequest(this,
							new AppLoginRequest.Params(user.getUid(), channel, ""
									+ location.getLongitude(), ""
									+ location.getLatitude(), pinfo.versionName,
									isNew));
					RequestAsync.request(request, null);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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
