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

import com.mengle.lucky.App;
import com.mengle.lucky.utils.Utils;



public abstract class Request implements Response,IRequest{

	
	
	public static String HOST = "http://114.215.104.75/";
	

	
	public static enum Status{
		SUCCESS,ERROR,SERVER_ERROR,DATA_ERROR
	}
	
	public static List<NameValuePair> build(Object obj){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		for(Field field : FieldUtils.getAllFields(obj.getClass())){
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
	
	public Request() {
		
	}
	
	public void request(){
		/*if(!Utils.isMobileNetworkAvailable(App.getInstance())){
			AlertDialog.show(App.getInstance(), "网络未连接");
			return;
		}*/
		try{
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(getURL());
			Map<String, File> fileMap = fillFiles();
			HttpEntity requsetEntity = null;
			if(fileMap != null){
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
			    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			    
			    for(NameValuePair param : fillParams()){
//			    	builder.addTextBody(param.getName(), param.getValue(),ContentType.create("text/plain", MIME.UTF8_CHARSET));
			    }
			    for(String filename:fileMap.keySet()){
//			    	builder.addPart(filename, new FileBody(fileMap.get(filename)));
			    	builder.addBinaryBody(filename, fileMap.get(filename), ContentType.create("image/jpeg"), "uploadimg");
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
			if(status_code == 200){
				
				try {
					JSONObject result = new JSONObject(IOUtils.toString(entity.getContent()));
					boolean s = result.getBoolean("s");
					String data = result.getString("v");
					if(s){
						success(data);
						status = Status.SUCCESS;
					}else{
						error(-1,data);
						status = Status.DATA_ERROR;
						status = Status.ERROR;
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
	
	
	
	protected Map<String, File> fillFiles(){
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
