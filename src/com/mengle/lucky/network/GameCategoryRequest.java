package com.mengle.lucky.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mengle.lucky.adapter.CatListAdater;
import com.mengle.lucky.adapter.CatListAdater.CatList.Cat;

public class GameCategoryRequest extends Request{

	public static class Category{
		private int id;
		private String name;
		public Category(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		
		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
		public CatListAdater.CatList.Cat toCat(){
			return new CatListAdater.CatList.Cat(id, name);
		}
		
		public static List<Cat> toList(List<Category> list){
			List<Cat> cats = new ArrayList<CatListAdater.CatList.Cat>();
			for(Category category:list){
				cats.add(category.toCat());
			}
			return cats;
		}
		
	}
	
	private List<Category> result = new ArrayList<GameCategoryRequest.Category>();
	
	@Override
	public void onSuccess(String data) {
		Map<Integer,String> map = new Gson().fromJson(data, new TypeToken<Map<Integer, String>>(){}.getType());
		result = new ArrayList<GameCategoryRequest.Category>();
		for(Integer id : map.keySet()){
			result.add(new Category(id, map.get(id)));
		}
		
	}
	
	public List<Category> getResult() {
		return result;
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
		return HOST+"game/categories/";
	}

	@Override
	public List<NameValuePair> fillParams() {
		// TODO Auto-generated method stub
		return null;
	}

}
