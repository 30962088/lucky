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
import com.mengle.lucky.network.model.Notice;
import com.mengle.lucky.utils.Dirctionary;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class App extends FrontiaApplication{

	
	private static App instance;
	
	public static App getInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
//		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		Dirctionary.init(this);
		
		
		
//		Thread.setDefaultUncaughtExceptionHandler(handler);

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
	            report.append( "    ");
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
	        
//	        CrashActivity.open(getApplicationContext(), report.toString());
	        
	        System.exit(0);
		}
	};
	
	public static Activity currentActivity;
	
	
}
