package com.mengle.lucky.fragments;

import java.util.ArrayList;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.CommentListAdapter;
import com.mengle.lucky.adapter.CommentModel;
import com.mengle.lucky.adapter.CommentModel.Reply;
import com.mengle.lucky.network.Login;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.Login.Params;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.OauthUtils;
import com.mengle.lucky.utils.OauthUtils.Callback;
import com.mengle.lucky.utils.Preferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class CommentFragment extends Fragment implements OnClickListener,Callback{

	private OauthUtils oauthUtils;
	
	private View nologinView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.comment_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		oauthUtils = new OauthUtils(getActivity());
		oauthUtils.setCallback(this);
		nologinView = view.findViewById(R.id.nologinpanel);
		view.findViewById(R.id.login_qq).setOnClickListener(this);
		view.findViewById(R.id.login_renren).setOnClickListener(this);
		view.findViewById(R.id.login_kaixin).setOnClickListener(this);
		view.findViewById(R.id.login_tencent).setOnClickListener(this);
		view.findViewById(R.id.login_weibo).setOnClickListener(this);
		ListView listView = (ListView) view.findViewById(R.id.listview);
		listView.setAdapter(new CommentListAdapter(getActivity(), new ArrayList<CommentModel>(){{
			add(new CommentModel("http://tp3.sinaimg.cn/1795737590/180/0/1", "威廉萌", "02-07 10:20", new ArrayList<CommentModel.Reply>(){{
				add(new Reply(null, null, "哇哈哈"));
				add(new Reply("123", "王玥", "哇哈哈"));
			}}));
		}}));
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Preferences.User user = new Preferences.User(getActivity());
		if(user.isLogin()){
			nologinView.setVisibility(View.GONE);
		}else{
			nologinView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_qq:
			oauthUtils.qqOauth();
			break;
		case R.id.login_kaixin:
			
			break;
		case R.id.login_weibo:
			oauthUtils.sinaOauth();
			break;
		case R.id.login_tencent:
			oauthUtils.tencentOauth();
			break;
		case R.id.login_renren:
			oauthUtils.renrenOauth();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onSuccess(Params params) {
		final Login login = new Login(getActivity(), params);
		RequestAsync.request(login, new Async() {
			
			public void onPostExecute(Request request) {
				if(request.getStatus() == Request.Status.SUCCESS){
					onResume();
				}
				
			}
		});
		
	}
	
}
