package com.mengle.lucky;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.EagerForeignCollection;
import com.mengle.lucky.database.DataBaseHelper;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.SNSBindRequest;
import com.mengle.lucky.network.SNSUnBindRequest;
import com.mengle.lucky.network.UserEditRequest;
import com.mengle.lucky.network.UserEditRequest.Params;
import com.mengle.lucky.network.UserMe;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.model.City;
import com.mengle.lucky.network.model.Province;
import com.mengle.lucky.network.model.User;
import com.mengle.lucky.network.model.User.SNS;
import com.mengle.lucky.utils.OauthUtils;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.OauthUtils.Callback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class UserSettingActivity extends Activity implements OnClickListener,
		Callback {

	public static void open(Context context) {
		context.startActivity(new Intent(context, UserSettingActivity.class));
	}

	private EditText qqView;

	private EditText phoneView;

	private CheckBox maleCheckbox;

	private CheckBox femaleCheckbox;

	private EditText nicknameView;

	private View snsWeiboView;

	private TextView snsWeiboText;

	private View snsQQView;

	private TextView snsQQText;

	private View snsTencentView;

	private TextView snsTencentText;

	private View snsRenrenView;

	private TextView snsRenrenText;

	private View snsKaixinView;

	private TextView snsKaixinText;

	private OauthUtils oauthUtils;
	
	private DataBaseHelper dataBaseHelper;
	
	private TextView provinceView;
	
	private TextView cityView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_setting_layout);
		
		try {
			Province.build(this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		dataBaseHelper = new DataBaseHelper(this);
		
		
		provinceView = (TextView) findViewById(R.id.province);
		provinceView.setOnClickListener(this);
		cityView = (TextView) findViewById(R.id.city);
		cityView.setOnClickListener(this);
		oauthUtils = new OauthUtils(this);
		oauthUtils.setCallback(this);

		findViewById(R.id.icon_back).setOnClickListener(this);
		nicknameView = (EditText) findViewById(R.id.nickname);
		maleCheckbox = (CheckBox) findViewById(R.id.male);
		femaleCheckbox = (CheckBox) findViewById(R.id.female);
		qqView = (EditText) findViewById(R.id.qq);
		phoneView = (EditText) findViewById(R.id.phone);

		snsWeiboView = findViewById(R.id.sns_weibo);
		snsWeiboView.setOnClickListener(this);
		snsWeiboText = (TextView) findViewById(R.id.sns_weibo_text);

		snsQQView = findViewById(R.id.sns_qq);
		snsQQView.setOnClickListener(this);
		snsQQText = (TextView) findViewById(R.id.sns_qq_text);

		snsTencentView = findViewById(R.id.sns_tencent);
		snsTencentView.setOnClickListener(this);
		snsTencentText = (TextView) findViewById(R.id.sns_tencent_text);

		snsRenrenView = findViewById(R.id.sns_renren);
		snsRenrenView.setOnClickListener(this);
		snsRenrenText = (TextView) findViewById(R.id.sns_renren_text);

		snsKaixinView = findViewById(R.id.sns_kaixin);
		snsKaixinView.setOnClickListener(this);
		snsKaixinText = (TextView) findViewById(R.id.sns_kaixin_text);

		maleCheckbox.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				switchGender(1);
			}
		});

		femaleCheckbox.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				switchGender(0);

			}
		});

		findViewById(R.id.update).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				request();

			}
		});
	}

	private int gender;

	private void switchGender(int gender) {
		this.gender = gender;
		if (gender == 0) {
			femaleCheckbox.setChecked(true);
			maleCheckbox.setChecked(false);
		} else if (gender == 1) {
			femaleCheckbox.setChecked(false);
			maleCheckbox.setChecked(true);
		}
	}
	
	private void fillCity(int id) throws SQLException{
		City city = dataBaseHelper.getCityDao().queryForId(id);
		String str = "请选择市";
		if(city != null){
			str = city.getName();
			cityView.setTag(city);
		}
		cityView.setText(str);
	}
	
	private void fillProvince(int id) throws SQLException{
		Province province = dataBaseHelper.getProvinceDao().queryForId(id);
		String str = "请选择省";
		if(province != null){
			str = province.getName();
			provinceView.setTag(province);
			
		}
		provinceView.setText(str);
	}

	private void fill() {
		Preferences.User user = new Preferences.User(this);
		final UserMe userMe = new UserMe(new UserMe.Params(user.getUid(),
				user.getToken()));
		RequestAsync.request(userMe, new Async() {

			public void onPostExecute(Request request) {
				if (userMe.getStatus() == Request.Status.SUCCESS) {
					User user = userMe.getUserResult();
					qqView.setText(Utils.getString(user.getQq()));
					phoneView.setText(Utils.getString(user.getMobile()));
					nicknameView.setText(Utils.getString(user.getNickname()));
					switchGender(user.getGender());
					try {
						fillCity(user.getCity());
						fillProvince(user.getProvince());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fillSns(user);
				}

			}
		});
	}

	private void fillSns(User user) {
		List<SNS> list = user.getSns();
		for (SNS sns : list) {
			String name = sns.getVia();
			if (TextUtils.equals(name, "weibo")) {
				snsWeiboView.setSelected(true);
				snsWeiboText.setText("取消绑定");
			} else if (TextUtils.equals(name, "tqq")) {
				snsTencentView.setSelected(true);
				snsTencentText.setText("取消绑定");
			} else if (TextUtils.equals(name, "qq")) {
				snsQQView.setSelected(true);
				snsQQText.setText("取消绑定");
			} else if (TextUtils.equals(name, "renren")) {
				snsRenrenView.setSelected(true);
				snsRenrenText.setText("取消绑定");
			} else if (TextUtils.equals(name, "kaixin")) {
				snsKaixinView.setSelected(true);
				snsKaixinText.setText("取消绑定");
			}

		}

	}

	private void request() {
		Preferences.User user = new Preferences.User(this);
		int province = 0;
		if(provinceView.getTag() != null){
			province = ((Province)provinceView.getTag()).getId();
		}
		
		int city = 0;
		if(cityView.getTag() != null){
			city = ((City)cityView.getTag()).getId();
		}
		
		UserEditRequest editRequest = new UserEditRequest(new Params(
				user.getUid(), user.getToken(), nicknameView.getText()
						.toString(), gender, province, city, qqView.getText()
						.toString(), phoneView.getText().toString()));

		RequestAsync.request(editRequest, new Async() {

			public void onPostExecute(Request request) {
				if (request.getStatus() == Request.Status.SUCCESS) {
					Utils.tip(UserSettingActivity.this, "更新成功");
					finish();
				}

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fill();

	}

	private void unbindRequest(String via) {
		Preferences.User user = new Preferences.User(this);
		final SNSUnBindRequest snsUnBindRequest = new SNSUnBindRequest(
				new SNSUnBindRequest.Params(user.getUid(), user.getToken(), via));

		RequestAsync.request(snsUnBindRequest, new Async() {

			public void onPostExecute(Request request) {
				if (snsUnBindRequest.getUser() != null) {
					fillSns(snsUnBindRequest.getUser());
				}

			}
		});

	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.province:
			try {
				onProvinceChange();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.city:
			try {
				onCityChange();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.sns_qq:
			if (v.isSelected()) {
				unbindRequest("qq");
			} else {
				oauthUtils.qqOauth();
			}
			break;
		case R.id.sns_weibo:
			if (v.isSelected()) {
				unbindRequest("weibo");
			} else {
				oauthUtils.sinaOauth();
			}
			break;
		case R.id.sns_tencent:
			if (v.isSelected()) {
				unbindRequest("tqq");
			} else {
				oauthUtils.tencentOauth();
			}
			break;
		case R.id.sns_renren:
			if (v.isSelected()) {
				unbindRequest("renren");
			} else {
				oauthUtils.renrenOauth();
			}
			break;
		case R.id.icon_back:
			finish();
			break;
		default:
			break;
		}

	}
	
	private void onCityChange() throws SQLException {
		Province province = (Province) provinceView.getTag();
		if(province == null){
			Utils.tip(this, "请先选择省份");
			return;
		}
		final List<City> cities = dataBaseHelper.getCityDao().queryForEq("province_id", province.getId());
		final String[] d = new String[cities.size()];
		for(int i = 0;i<cities.size();i++){
			d[i] = cities.get(i).getName();
		}
		openDialog("请选择城市", d, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				cityView.setText(d[which]);
				cityView.setTag(cities.get(which));
				
			}
		});
		
	}

	private void onProvinceChange() throws SQLException {
		
		final List<Province> list = dataBaseHelper.getProvinceDao().queryForAll();
		final String[] d = new String[list.size()];
		
		for(int i = 0;i<list.size();i++){
			d[i] = list.get(i).getName();
		}
		
		openDialog("请选择省份", d, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				provinceView.setText(d[which]);
				provinceView.setTag(list.get(which));
				cityView.setText("请选择市");
				cityView.setTag(null);
				
			}
		});
		
	}

	private void openDialog(String title,String[] array,DialogInterface.OnClickListener clickListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(title)
	           .setItems(array,clickListener);
	    builder.create().show();
	}

	@Override
	public void onSuccess(com.mengle.lucky.network.Login.Params params) {
		Preferences.User user = new Preferences.User(this);
		SNSBindRequest.Params sParams = new SNSBindRequest.Params(
				user.getUid(), user.getToken(), params);
		SNSBindRequest bindRequest = new SNSBindRequest(sParams);
		RequestAsync.request(bindRequest, new Async() {

			@Override
			public void onPostExecute(Request request) {
				onResume();
			}
		});

	}

}
