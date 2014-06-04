package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.adapter.ShitiListAdapter.Shiti;
import com.mengle.lucky.adapter.ShitiListAdapter.ShitiList;
import com.mengle.lucky.network.UserRank.Result;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.User;

import android.content.Context;
import android.text.TextUtils;

public class GameLibraryGets extends Request{

	public static class Result{
		public static class Opt{
			private int id;
			private String opt;
			private int c;
			public int getId() {
				return id;
			}
			public String getOpt() {
				return opt;
			}
			public int getC() {
				return c;
			}
			
		}
		
		private int id;
		private String title;
		private String image;
		private int coin;
		private List<Opt> opts;
		public int getId() {
			return id;
		}
		public String getTitle() {
			return title;
		}
		public String getImage() {
			return image;
		}
		public int getCoin() {
			return coin;
		}
		public List<Opt> getOpts() {
			return opts;
		}
		
		public ShitiList toShiti(){
			
			List<Shiti> list = new ArrayList<Shiti>();
			int i = 0,k=0;
			for(Opt opt:opts){
				if(opt.c==1){
					k=i;
				}
				list.add(new Shiti(opt.id,opt.opt,opt.c==1?true:false));
				i++;
			}
			ShitiList list1 = new ShitiList(list,k);
			return list1;
		}
		
		
		
		
		
	}
	
	public static class Params{
		protected int uid;
		protected String token;
		protected int last;
		protected int limit;
		public Params( int uid, String token,  int limit) {
			super();
			this.uid = uid;
			this.token = token;
			this.limit = limit;
		}
		
	}
	
	private Params params;
	
	private List<Result> results;
	
	private Preferences.User user;
	
	public GameLibraryGets(Context context, Params params) {
		super();
		this.params = params;
		user = new Preferences.User(context);
//		this.params.last = user.getLastShitiId();
		this.params.last = 0;
	}

	@Override
	public void onSuccess(String data) {
		if(!TextUtils.isEmpty(data)){
			results = new Gson().fromJson(data, new TypeToken<List<Result>>(){}.getType());
			Result result = results.get(results.size()-1);
			user.setLastShitiId(result.id);
		}
		
	}
	
	public List<Result> getResults() {
		return results;
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
		return HOST+"game/library/gets/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return build(params);
	}

}
