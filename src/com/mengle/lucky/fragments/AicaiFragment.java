package com.mengle.lucky.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mengle.lucky.R;
import com.mengle.lucky.ZoneActivity;
import com.mengle.lucky.adapter.AicaiListAdapter;
import com.mengle.lucky.adapter.AicaiListAdapter.Aicai;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.network.UserRank;
import com.mengle.lucky.network.UserRankMe;
import com.mengle.lucky.network.UserRank.Result;
import com.mengle.lucky.utils.Utils;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.utils.Utils.Wechat;
import com.mengle.lucky.wiget.AlertDialog;
import com.mengle.lucky.wiget.BaseListView;
import com.mengle.lucky.wiget.LoadingPopup;
import com.mengle.lucky.wiget.ShareDialog;
import com.mengle.lucky.wiget.BaseListView.OnLoadListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.media.UMImage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AicaiFragment extends Fragment implements OnLoadListener,OnItemClickListener,OnClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		user = new User(getActivity());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.aicai_layout, null);
	}
	
	private BaseListView listView;
	
	private List<Aicai> list = new ArrayList<AicaiListAdapter.Aicai>();
	
	private AicaiListAdapter adapter;
	
	private User user;
	
	private TextView rankView;
	
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.btn_share).setOnClickListener(this);
		rankView = (TextView) view.findViewById(R.id.rank);
		listView = (BaseListView) view.findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		listView.getRefreshableView().addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.aicai_header, null));
		listView.setOnLoadListener(this);
		adapter = new AicaiListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		listView.setRefreshing(true);
		listView.load(true);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!user.isLogin()){
			rankView.setText("未登录，无法查看我的排名");
			rankView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
		}else{
			final UserRankMe rankMe = new UserRankMe(getActivity(), new UserRankMe.Params(user.getUid(), user.getToken()));
			RequestAsync.request(rankMe, new Async() {
				
				@Override
				public void onPostExecute(Request request) {
					String rank = "排名太低，未入排行，加油！";
					int size = 24;
					if(rankMe.getRank() > 0){
						rank = ""+rankMe.getRank();
						
					}else{
						size = 12;
					}
					rankView.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
					rankView.setText(rank);
					
				}
			});
		}
		
	}

	private List<Result> results;
	
	private int offset;

	@Override
	public boolean onLoad(int offset, int limit) {
		results = null;
		this.offset = offset;
		UserRank rank = new UserRank(getActivity(), new UserRank.Params(user.getUid(), user.getToken(), offset,limit));
		rank.request();
		results = rank.getResults();
		
		
		return results.size()>=limit?true:false;
	}



	@Override
	public void onLoadSuccess() {
		if(offset == 0){
			list.clear();
		}
		list.addAll(Result.toAicaiList(results));
		adapter.notifyDataSetChanged();
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(!user.isLogin()){
			AlertDialog.open(getActivity(), "您目前不能查看其他用户\n请登录后重试", null);
			return;
		}
		position -= 2;
		if(position >=0){
			Aicai aicai = list.get(position);
			ZoneActivity.open(getActivity(), aicai.getUid());
		}
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_share:
			onShare();
			break;

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
	
}
