package com.mengle.lucky;

import java.util.ArrayList;

import com.mengle.lucky.KanZhuangPreviewActivity.PreviewModel;
import com.mengle.lucky.network.GameCategoryRequest;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.GameCategoryRequest.Category;
import com.mengle.lucky.network.IUserGet.UserResult;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserMe.Callback;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.wiget.RadioGroupLayout;
import com.mengle.lucky.wiget.RadioGroupLayout.RadioItem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class PublishActivity extends BaseActivity implements OnClickListener{

	
	
	private static final int SELECT_PHOTO = 100;
	
	private TextView nickView;
	
	private ImageView avatarView;
	
	private TextView totalCoinView;
	
	private ImageView imageView;
	
	private EditText titleView;
	
	private EditText question1View;
	
	private EditText question2View;
	
	private EditText question3View;
	
	private EditText jishuView;
	
	private EditText hourText;
	
	private EditText minuteText;
	
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
	            imageView.setTag(Utils.getRealPathFromURI(this, selectedImage));
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
		totalCoinView = (TextView) findViewById(R.id.totalCoin);
		nickView = (TextView) findViewById(R.id.nickname);
		avatarView = (ImageView) findViewById(R.id.user_photo);
		titleView = (EditText) findViewById(R.id.titleview);
		question1View = (EditText) findViewById(R.id.question1);
		question2View = (EditText) findViewById(R.id.question2);
		question3View = (EditText) findViewById(R.id.question3);
		hourText = (EditText) findViewById(R.id.hour);
		minuteText = (EditText) findViewById(R.id.minute);
		jishuView = (EditText)findViewById(R.id.jishu);
		findViewById(R.id.publish_btn).setOnClickListener(this);
		radioGroupLayout2 = (RadioGroupLayout) findViewById(R.id.radio2);
		radioGroupLayout2.setList(new ArrayList<RadioGroupLayout.RadioItem>(){{
			add(new RadioItem("2", "2倍"));
			add(new RadioItem("3", "3倍"));
			add(new RadioItem("4", "4倍"));
		}});
		
		imageView = (ImageView) findViewById(R.id.add_pic);
		imageView.setOnClickListener(this);
		radioGroupLayout1 =(RadioGroupLayout) findViewById(R.id.radio1);
		findViewById(R.id.preview).setOnClickListener(this);
		loadCat();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UserMe.get(this, new Callback() {
			
			@Override
			public void onsuccess(UserResult userResult) {
				totalCoinView.setText(""+userResult.getGold_coin());
				nickView.setText(userResult.getNickname());
				avatarView.setTag(userResult.getAvatar());
				BitmapLoader.displayImage(PublishActivity.this, userResult.getAvatar(), avatarView);
				
			}
		});
		
	}
	
	private boolean validate(String title,String imagePath,String A,String B,String category,String jishu,String beishu,String hour,String miniute){
		if(TextUtils.isEmpty(title)){
			Utils.tip(this, "您还没有命题");
			return false;
		}
		if(TextUtils.isEmpty(imagePath)){
			Utils.tip(this, "您还没有添加图片");
			return false;
		}
		if(TextUtils.isEmpty(category)){
			Utils.tip(this, "您还没有选择该问题的分类");
			return false;
		}
		if(TextUtils.isEmpty(A)){
			Utils.tip(this, "您还没有填写选项A内容");
			return false;
		}
		if(TextUtils.isEmpty(B)){
			Utils.tip(this, "您还没有填写选项B内容");
			return false;
		}
		if(TextUtils.isEmpty(beishu)){
			Utils.tip(this, "您还没有填写倍数");
			return false;
		}
		
		if(TextUtils.isEmpty(jishu)){
			Utils.tip(this, "您还没有填写基数");
			return false;
		}
		

		if(TextUtils.isEmpty(hour)){
			Utils.tip(this, "您还没有小时倒计时");
			return false;
		}
		if(TextUtils.isEmpty(miniute)){
			Utils.tip(this, "您还没有分钟倒计时");
			return false;
		}
		
		
		
		return true;
		
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
		case R.id.publish_btn:
			onPublish();
			break;
		case R.id.preview:
			onPreview();
		default:
			break;
		}
		
	}
	
	private void onPreview(){
		String title = titleView.getText().toString();
		String imagePath = null;
		if(imageView.getTag() != null){
			imagePath = imageView.getTag().toString();
		}
		String A = question1View.getText().toString();
		String B = question2View.getText().toString();
		String C = question3View.getText().toString();
		String cat = radioGroupLayout1.getValue();
		String jishu = jishuView.getText().toString();
		String beishu = radioGroupLayout2.getValue();
		String hour = hourText.getText().toString();
		String miniute = minuteText.getText().toString();
		if(validate(title, imagePath, A, B, cat, jishu, beishu,hour,miniute)){
			KanZhuangPreviewActivity.open(this, new PreviewModel(avatarView.getTag().toString(), title, imagePath, A, B, C, jishu));
		}
		
	}
	
	private void onPublish(){
		String title = titleView.getText().toString();
		String imagePath = null;
		if(imageView.getTag() != null){
			imagePath = imageView.getTag().toString();
		}
		String A = question1View.getText().toString();
		String B = question2View.getText().toString();
		String C = question3View.getText().toString();
		String cat = radioGroupLayout1.getValue();
		String jishu = jishuView.getText().toString();
		String beishu = radioGroupLayout2.getValue();
		String hour = hourText.getText().toString();
		String miniute = minuteText.getText().toString();
		if(validate(title, imagePath, A, B, cat, jishu, beishu,hour,miniute)){
			
		}
		
	}

	private void onAddPic() {
		openGallery();
		
	}


}
