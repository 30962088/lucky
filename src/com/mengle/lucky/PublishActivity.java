package com.mengle.lucky;

import java.util.ArrayList;

import com.mengle.lucky.network.GameCategoryRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.GameCategoryRequest.Category;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.wiget.RadioGroupLayout;
import com.mengle.lucky.wiget.RadioGroupLayout.RadioItem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class PublishActivity extends BaseActivity implements OnClickListener{

	private static final int SELECT_PHOTO = 100;
	
	private ImageView imageView;
	
	private RadioGroupLayout radioGroupLayout1;
	
	private RadioGroupLayout radioGroupLayout2;
	
	private void openGallery(){
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);    
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = data.getData();
	            imageView.setVisibility(View.VISIBLE);
	            BitmapLoader.displayImage(this, selectedImage.toString(), imageView);
	        }
	    }
	}
	
	public static void open(Context context){
		context.startActivity(new Intent(context, PublishActivity.class));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_layout);
		radioGroupLayout2 = (RadioGroupLayout) findViewById(R.id.radio2);
		radioGroupLayout2.setList(new ArrayList<RadioGroupLayout.RadioItem>(){{
			add(new RadioItem(1, "2倍"));
			add(new RadioItem(2, "3倍"));
			add(new RadioItem(3, "4倍"));
		}});
		findViewById(R.id.add_pic).setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.image);
		radioGroupLayout1 =(RadioGroupLayout) findViewById(R.id.radio1);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadCat();
		
	}
	
	private void loadCat(){
		final GameCategoryRequest categoryRequest = new GameCategoryRequest();
		RequestAsync.request(categoryRequest, new Async(){

			@Override
			public void onPostExecute(Request request) {
				radioGroupLayout1.setList(Category.toRadioItemList(categoryRequest.getResult()));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_pic:
			onAddPic();
			break;

		default:
			break;
		}
		
	}

	private void onAddPic() {
		openGallery();
		
	}


}
