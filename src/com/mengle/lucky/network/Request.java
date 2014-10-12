package com.mengle.lucky.network;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.mengle.lucky.App;
import com.mengle.lucky.MainActivity;
import com.mengle.lucky.PublishActivity;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.wiget.LoadingPopup;
import com.mengle.lucky.wiget.PushDialog;



public abstract class Request implements Response,IRequest{

	
	
	public static String HOST = "http://api.joypaw.com/";
	

	
	public static enum Status{
		SUCCESS,ERROR,SERVER_ERROR,DATA_ERROR
	}
	
	public static List<NameValuePair> build(Object obj){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		for(Field field : obj.getClass().getDeclaredFields()){
			try {
				
				Object value = field.get(obj);
				
				if(value != null){
					params.add(new BasicNameValuePair(field.getName(), value.toString()));
				}
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return params;
		
	}
	
	
	
	
	private Status status;
	
	private String data;
	
	private String errorMsg;
	
	private boolean disable = false;
	
	protected Context context;
	
	public Request(Context context) {
		this.context = context;
	}
	
	public static class BinaryBody{
		private String filename;
		private File file;
		private String contentType;
		private String uploadName;
		public BinaryBody(String filename, File file, String contentType,
				String uploadName) {
			super();
			this.filename = filename;
			this.file = file;
			this.contentType = contentType;
			this.uploadName = uploadName;
		}
		
	}
	
	public void request(){
		if(disable){
			return;
		}
		/*if(!Utils.isMobileNetworkAvailable(App.getInstance())){
			AlertDialog.show(App.getInstance(), "网络未连接");
			return;
		}*/
		try{
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(getURL());
			List<BinaryBody> binaryBodies = fillFiles();
			HttpEntity requsetEntity = null;
			if(binaryBodies != null){
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
			    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			    if(fillParams() != null){
			    	for(NameValuePair param : fillParams()){
				    	builder.addTextBody(param.getName(), param.getValue(),ContentType.create("text/plain", MIME.UTF8_CHARSET));
				    }
			    }
			    
			    for(BinaryBody body:binaryBodies){
//			    	builder.addPart(filename, new FileBody(fileMap.get(filename)));
			    	builder.addBinaryBody(body.uploadName, body.file, ContentType.create(body.contentType), body.filename);
			    }
			    requsetEntity = builder.build();
			    httpost.setEntity(requsetEntity);
			}else{
				if(fillParams() != null){
					requsetEntity = new UrlEncodedFormEntity(fillParams(), HTTP.UTF_8);
					httpost.setEntity(requsetEntity);
				}
				
			}
			
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			
			
			
			int status_code = response.getStatusLine().getStatusCode();
			LoadingPopup.hide(App.getInstance());
			if(status_code == 200){
				
				try {
					JSONObject result = new JSONObject(IOUtils.toString(entity.getContent()));
					boolean s = result.getBoolean("s");
					String data = result.getString("v");
					if(s){
						success(data);
						status = Status.SUCCESS;
					}else{
						if(TextUtils.equals("用户验证失败！", data)){
							disable = true;
//							Utils.tip(App.getInstance(), "该账号已在其他设备上登录");
							
							new Handler(context.getMainLooper()).post(new Runnable() {
								
								@Override
								public void run() {

									Intent intent = new Intent(context, MainActivity.class);
									intent.setAction(MainActivity.ACTION_LOGOUT);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
									context.startActivity(intent);
									disable = false;
									
								}
							});
						}else{
							error(-1,data);
							status = Status.DATA_ERROR;
							status = Status.ERROR;
						}
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}else{
				serverError(status_code);
				status = Status.SERVER_ERROR;
				status = Status.ERROR;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	protected List<BinaryBody> fillFiles(){
		return null;
	}
	
	public abstract String getURL();
	
	public abstract List<NameValuePair> fillParams();
	
	public Status getStatus() {
		return status;
	}
	
	public String getData() {
		return data;
	}
	
	private void success(String str){
		data = str;
		onSuccess(str);
	}
	
	private void error(int code,String msg){
		/*if(code == 10020){
			User.saveUser(null);
		}
		if(ArrayUtils.indexOf(new int[]{CODE_NO_DATA}, code) == -1 ){
			AlertDialog.show(App.getInstance(), msg);
		}*/
		errorMsg = msg;
		onResultError(code, msg);
		onError(code,msg);
		Utils.tip(App.getInstance().getApplicationContext(), msg);
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	private void serverError(int code){
		String msg = "";
		if(code == 500){
			msg = "服务器内部错误";
			
		}
		errorMsg = msg;
		onServerError(code,msg);
		onError(code,msg);
	}
	
	
}
