package com.mengle.lucky.fragments;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.CommentListAdapter;
import com.mengle.lucky.adapter.CommentModel;
import com.mengle.lucky.adapter.CommentModel.Reply;
import com.mengle.lucky.network.GameCommentRequest;
import com.mengle.lucky.network.GameCommentRequest.Result;
import com.mengle.lucky.network.GameCommentUpdate;
import com.mengle.lucky.network.Login;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.Login.Params;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.OauthUtils;
import com.mengle.lucky.utils.OauthUtils.Callback;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.wiget.BaseListView;
import com.mengle.lucky.wiget.BaseListView.OnLoadListener;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

public class CommentFragment extends Fragment implements OnClickListener,Callback,OnLoadListener,OnItemClickListener,OnEditorActionListener{

	public static CommentFragment newInstance(int gameId){
		CommentFragment fragment = new CommentFragment();
		fragment.gameId = gameId;
		return fragment;
	}
	
	private OauthUtils oauthUtils;
	
	private View nologinView;
	
	private int gameId;
	
	private BaseListView listView;
	
	private CommentListAdapter adapter;
	
	private View replylayout;
	
	private TextView replyTextView;
	
	private List<CommentModel> list = new ArrayList<CommentModel>();
	
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
		replylayout = view.findViewById(R.id.replylayout);
		replyTextView = (TextView) view.findViewById(R.id.reply_content);
		replyTextView.setOnEditorActionListener(this);
		view.findViewById(R.id.show_reply).setOnClickListener(this);
		view.findViewById(R.id.login_qq).setOnClickListener(this);
		view.findViewById(R.id.login_renren).setOnClickListener(this);
		view.findViewById(R.id.login_kaixin).setOnClickListener(this);
		view.findViewById(R.id.login_tencent).setOnClickListener(this);
		view.findViewById(R.id.login_weibo).setOnClickListener(this);
		listView = (BaseListView) view.findViewById(R.id.listview);
		listView.setOnLoadListener(this);
		adapter = new CommentListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.getRefreshableView().setOnItemClickListener(this);
		/*listView.setAdapter(new CommentListAdapter(getActivity(), new ArrayList<CommentModel>(){{
			add(new CommentModel("http://tp3.sinaimg.cn/1795737590/180/0/1", "威廉萌", "02-07 10:20", new ArrayList<CommentModel.Reply>(){{
				add(new Reply(null, null, "哇哈哈"));
				add(new Reply("123", "王玥", "哇哈哈"));
			}}));
		}}));*/
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Preferences.User user = new Preferences.User(getActivity());
		if(user.isLogin()){
			nologinView.setVisibility(View.GONE);
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					listView.load(true);
					
				}
			});
			
		}else{
			nologinView.setVisibility(View.VISIBLE);
		}
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show_reply:
			showReply(null);
			break;
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
	
	private void showReply(CommentModel model){
		this.replyModel = model;
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    if (inputMethodManager != null) {
	    	replyTextView.requestFocus();
	    	if(model != null){
	    		replyTextView.setHint("回复："+model.getName());
	    	}else{
	    		replyTextView.setHint("");
	    	}
	    	
	    	replylayout.setVisibility(View.VISIBLE);
	    	inputMethodManager.showSoftInput(replyTextView,InputMethodManager.SHOW_FORCED);
	    }
	}
	
	private void hideReply(){
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    if (inputMethodManager != null) {
	    	replyTextView.requestFocus();
	    	replylayout.setVisibility(View.GONE);
	    	inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

	@Override
	public boolean onLoad(int offset, int limit) {
		Preferences.User user = new Preferences.User(getActivity());
		GameCommentRequest commentRequest = new GameCommentRequest(new GameCommentRequest.Params(user.getUid(), user.getToken(), gameId, offset, limit));
		commentRequest.request();
		List<Result> results = commentRequest.getResults();
		
		if(offset == 0){
			list.clear();
		}
		list.addAll(Result.toCommentModelList(results));
		
		
		return results.size()>=limit?true:false;
	}

	@Override
	public void onLoadSuccess() {
		adapter.notifyDataSetChanged();
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		
	}
	
	private CommentModel replyModel;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		replyModel  = list.get(position-1);
		showReply(replyModel);
		
	}
	
	private void onsubmit(){
		Preferences.User user = new Preferences.User(getActivity());
		Integer uid = null;
		if(replyModel != null){
			uid =  replyModel.getUid();
		}
		final GameCommentUpdate commentUpdate = new GameCommentUpdate(new 
				GameCommentUpdate.Params(user.getUid(), user.getToken(), gameId, uid, replyTextView.getText().toString()));
		replyTextView.setText("");
		RequestAsync.request(commentUpdate, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				Result result = commentUpdate.getResult();
				if(result != null){
					list.add(result.toCommentModel());
					adapter.notifyDataSetChanged();
				}
				
			}
		});
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
            onsubmit();
            return true;
        }
		return false;
	}
	
}
