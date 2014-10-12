package com.mengle.lucky.fragments;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.ShitiListAdapter;
import com.mengle.lucky.adapter.ShitiListAdapter.ShitiList;
import com.mengle.lucky.network.GameLibraryGets.Result;
import com.mengle.lucky.network.GameLibraryGets;
import com.mengle.lucky.network.GameLibrarySubmitRequest;
import com.mengle.lucky.network.GameLibrarySubmitRequest.Param;
import com.mengle.lucky.network.GameLibrarySubmitRequest.Param.Log;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.BitmapLoader;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.Utils.Wechat;
import com.mengle.lucky.wiget.AlertDialog;
import com.mengle.lucky.wiget.LoadingPopup;
import com.mengle.lucky.wiget.ShareDialog;
import com.mengle.lucky.wiget.ShareDialog.Callback;
import com.mengle.lucky.wiget.ShitiOverView;
import com.mengle.lucky.wiget.ShitiOverView.Model;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.media.UMImage;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShitiFragment extends Fragment implements OnItemClickListener,OnClickListener,AnimationListener{

	
	public static ShitiFragment newInstance(){
		ShitiFragment fragment = new ShitiFragment();
		return fragment;
	}
	
	private TextView shitiNoView;
	
	private TextView coinView;
	
	private TextView titleView;
	
	private View parentView;
	
	private ShitiOverView shitiOverView;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.shiti_layout, null);
	}
	
	private ListView listView;
	
	private ShitiList list;
	
	private ShitiListAdapter adapter;
	
	private List<Result> results;
	
	private HashMap<Integer, ShitiList> hashMap = new HashMap<Integer, ShitiListAdapter.ShitiList>();
	
	private View btnPrev;
	
	private View btnNext;
	
	private View btnOK;
	
	private View headerView;
	
	private ImageView headerImg;
	
	private View add_anim;
	
	private View rootView;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		this.rootView = view;
		view.findViewById(R.id.btn_share).setOnClickListener(this);
		shitiOverView = (ShitiOverView) view.findViewById(R.id.shitiover);
		add_anim = view.findViewById(R.id.add_anim);
		btnOK = view.findViewById(R.id.btn_ok);
		btnOK.setOnClickListener(this);
		parentView = view.findViewById(R.id.parent);
		btnPrev = view.findViewById(R.id.btn_prev);
		btnPrev.setOnClickListener(this);
		btnNext = view.findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
		listView = (ListView) view.findViewById(R.id.listview);
		View hview = LayoutInflater.from(getActivity()).inflate(R.layout.shiti_header, null);;
		headerView = hview.findViewById(R.id.header_view);
		headerImg = (ImageView) headerView.findViewById(R.id.header_img);
		listView.addHeaderView(hview);
		listView.setOnItemClickListener(this);
		titleView = (TextView) view.findViewById(R.id.shiti_title);
		shitiNoView = (TextView) view.findViewById(R.id.shiti_no);
		Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SHOWG.TTF");
		shitiNoView.setTypeface(face);
		
		coinView = (TextView) view.findViewById(R.id.coin);
		coinView.setTypeface(face);
		
		request();
		
	}
	
	private void request(){
		Preferences.User user = new Preferences.User(getActivity());
		final GameLibraryGets gets = new GameLibraryGets(getActivity(), new GameLibraryGets.Params(user.getUid(), user.getToken(), 20));
		RequestAsync.request(gets, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				if(gets.getResults() != null){
					results = gets.getResults();
					build();
				}
				
			}
		});
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		submit();
	}
	
	private int i = 0;
	
	private void next(){
		if(list != null && list.getIndex() == null){
			Utils.tip(getActivity(), "您还没有作答");
			return;
		}
		i++;
		build();
	}
	
	private void prev(){
		i--;
		build();
	}
	
	private void submit(){
		if(list != null && list.getIndex() == null){
			Utils.tip(getActivity(), "您还没有作答");
			return;
		}
		final GameLibrarySubmitRequest submitRequest = new GameLibrarySubmitRequest(getActivity(), toSubmitParam());
		RequestAsync.request(submitRequest, new Async() {
			
			@Override
			public void onPostExecute(Request request) {
				shitiOverView.setModel(submitRequest.getResult().toModel());
				
				
			}
		});
		
	}
	
	private GameLibrarySubmitRequest.Param toSubmitParam(){
		List<Log> logs = new ArrayList<GameLibrarySubmitRequest.Param.Log>();
		for(ShitiList list : hashMap.values()){
			int u = list.getList().get(list.getIndex()).getId();
			int c = list.getList().get(list.getC()).getId();
			logs.add(new Log(list.getId(), u, c));
		}
		Preferences.User user = new Preferences.User(getActivity());
		return new GameLibrarySubmitRequest.Param(user.getUid(), user.getToken(), logs); 
		
	}
	
	private void build(){
		if(i==0){
			btnPrev.setVisibility(View.INVISIBLE);
		}else{
			btnPrev.setVisibility(View.VISIBLE);
		}
		if(i==results.size()-1){
			btnNext.setVisibility(View.GONE);
			btnOK.setVisibility(View.VISIBLE);
		}else{
			btnNext.setVisibility(View.VISIBLE);
			btnOK.setVisibility(View.GONE);
		}
		
		shitiNoView.setText(""+(i+1));
		Result result = results.get(i);
		if(TextUtils.isEmpty(result.getImage())){
			headerView.setVisibility(View.GONE);
		}else{
			headerView.setVisibility(View.VISIBLE);
			headerImg.setImageBitmap(null);
			BitmapLoader.displayImage(getActivity(), result.getImage(), headerImg);
		}
		titleView.setText(result.getTitle());
		list = hashMap.get(result.getId());
		if(list == null){
			list = result.toShiti();
			hashMap.put(result.getId(), list);
		}
		
		adapter = new ShitiListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		if(list.getIndex()==null){
			if(position-1 == list.getC()){
				startAnimation();
			}
			new Preferences.User(getActivity()).setLastShitiId(list.getId());
			list.setIndex(position-1);
			adapter.notifyDataSetChanged();
		}
		
		
	}
	
	private void startAnimation(){
		
		add_anim.setVisibility(View.VISIBLE);
		AnimationSet set = new AnimationSet(true);
		TranslateAnimation translate = new TranslateAnimation(0,(coinView.getLeft()-add_anim.getLeft())*2.1f, 0,(coinView.getTop()-add_anim.getTop())*2.1f);
		ScaleAnimation scale = new ScaleAnimation(1f, 0.2f, 1f, 0.2f,Animation.RELATIVE_TO_SELF, 0f,Animation.RELATIVE_TO_SELF, 1f);
		set.setDuration(700);
		scale.initialize(add_anim.getWidth(), add_anim.getWidth(), parentView.getWidth(), parentView.getHeight());
		set.addAnimation(translate);
		set.addAnimation(scale);
		set.setAnimationListener(this);

		add_anim.startAnimation(set);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_prev:
			prev();
			break;
		case R.id.btn_next:
			next();
			break;
		case R.id.btn_ok:
			submit();
			break;
		case R.id.btn_share:
			onShare();
		default:
			break;
		}
		
	}

	private void doShare(SHARE_MEDIA media){
		View view = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
		Bitmap bitmap = Utils.convertViewToBitmap(view);

		if(media == SHARE_MEDIA.WEIXIN || media ==SHARE_MEDIA.WEIXIN_CIRCLE){
			Wechat.share(getActivity(), bitmap, media);
			return;
		}
		final UMSocialService mController =Utils.getUMSocialService(getActivity());
		
		mController.setShareContent("谁能帮忙猜出这题？我家房子就是你的");
		mController.setShareImage(new UMImage(getActivity(), bitmap));
		
		mController.directShare(getActivity(),media,new SnsPostListener() {
			
			@Override
			public void onStart() {
				
				
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
				LoadingPopup.hide(getActivity());
				
			}
		});
	}
	
	private void onShare() {
		ShareDialog.open(getActivity(), "集运宝分享", new ShareDialog.Callback() {
			
			@Override
			public void onClick(final SHARE_MEDIA media,final String name) {
				final UMSocialService mController =Utils.getUMSocialService(getActivity());
				if(media == SHARE_MEDIA.WEIXIN || media ==SHARE_MEDIA.WEIXIN_CIRCLE){
					doShare(media);
					return;
				}
				
				LoadingPopup.show(getActivity());
				mController.checkTokenExpired(getActivity(), new SHARE_MEDIA[]{media}, new UMDataListener() {

					@Override
					public void onComplete(int arg0, Map<String, Object> arg1) {
						String key = arg1.keySet().toArray()[0].toString();
						if((Boolean)arg1.get(key)  == false){
							AlertDialog.open(getActivity(), "你的"+name+"账号还没有与本机绑定，请绑定后再分享", null);
							LoadingPopup.hide(getActivity());
						}else{
							doShare(media);
						}
						
						
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						
					}
					
				});
			}
		}, null);
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		add_anim.setVisibility(View.INVISIBLE);
		coinView.setText(""+(Integer.parseInt(coinView.getText().toString())+1));
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
