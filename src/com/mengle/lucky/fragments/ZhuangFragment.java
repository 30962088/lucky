package com.mengle.lucky.fragments;

import java.util.ArrayList;
import java.util.List;

import com.mengle.lucky.KanZhuangActivity;
import com.mengle.lucky.MainActivity;
import com.mengle.lucky.PublishActivity;
import com.mengle.lucky.R;
import com.mengle.lucky.adapter.CatListAdater;
import com.mengle.lucky.adapter.CatListAdater.CatList;
import com.mengle.lucky.adapter.StageAdapter;
import com.mengle.lucky.adapter.CatListAdater.CatList.Cat;
import com.mengle.lucky.adapter.ZhuangNewListAdapter;
import com.mengle.lucky.adapter.ZhuangNewListAdapter.Model;
import com.mengle.lucky.network.GameCategoryRequest;
import com.mengle.lucky.network.GameCategoryRequest.Category;
import com.mengle.lucky.network.GameGetsRequest;
import com.mengle.lucky.network.GameHotRequest;
import com.mengle.lucky.network.GameHotRequest.Params;
import com.mengle.lucky.network.Request;
import com.mengle.lucky.network.GameGetsRequest.Pamras;
import com.mengle.lucky.network.IGameGet.Result;
import com.mengle.lucky.network.RequestAsync;
import com.mengle.lucky.network.RequestAsync.Async;
import com.mengle.lucky.utils.Preferences;
import com.mengle.lucky.utils.Preferences.User;
import com.mengle.lucky.wiget.AlertDialog;
import com.mengle.lucky.wiget.BaseListView;
import com.mengle.lucky.wiget.BaseListView.OnLoadListener;
import com.mengle.lucky.wiget.GameListView;
import com.mengle.lucky.wiget.CommonHeaderView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ZhuangFragment extends Fragment implements OnClickListener,OnLoadListener,OnItemClickListener {

	public static ZhuangFragment newInstance(int cid) {
		ZhuangFragment fragment = new ZhuangFragment();
		fragment.setCid(cid);
		return fragment;
	}

	private int cid;

	private CommonHeaderView commonHeaderView;

	private StageFragment stageFragment;

	public void setCid(int cid) {
		this.cid = cid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.zhuang_layout, null);
	}

	private BaseListView listView;
	
	private ZhuangNewListAdapter adapter;
	
	private List<ZhuangNewListAdapter.Model> list = new ArrayList<ZhuangNewListAdapter.Model>();

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		adapter = new ZhuangNewListAdapter(getActivity(), list);
		view.findViewById(R.id.icon_bottom).setOnClickListener(this);
		listView = (BaseListView) getView().findViewById(R.id.listview);
		View headView = View.inflate(getActivity(), R.layout.zhuang_header,
				null);
		listView.getRefreshableView().addHeaderView(headView);
		listView.setOnLoadListener(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		stageFragment = new StageFragment();

		getFragmentManager().beginTransaction()
				.add(R.id.stage_fragment, stageFragment).commit();
		commonHeaderView = (CommonHeaderView) view
				.findViewById(R.id.headerView);

		loadHot();
		
		loadCat();
		listView.load(true);
	}

	private void loadHot() {
		Preferences.User user = new Preferences.User(getActivity());
		final GameHotRequest hotRequest = new GameHotRequest(new Params(
				user.getUid(), user.getToken()));
		RequestAsync.request(hotRequest, new Async() {

			@Override
			public void onPostExecute(Request request) {
				List<Result> results = hotRequest.getResults();
				stageFragment.setAdapter(new StageAdapter(
						getChildFragmentManager(), Result.toStageList(results)));

			}
		});
	}

	private void loadCat() {
		final GameCategoryRequest categoryRequest = new GameCategoryRequest();
		RequestAsync.request(categoryRequest, new Async() {

			@Override
			public void onPostExecute(Request request) {
				List<Cat> cats = Category.toList(categoryRequest.getResult());
				cats.add(new Cat(0, "全部"));
				int index = 0;
				for (int i = 0; i < cats.size(); i++) {
					if (cats.get(i).getId() == cid) {
						index = i;
						break;
					}
				}
				final CatList list = new CatList(cats, index);

				commonHeaderView.setAdapter(new CatListAdater(getActivity(),
						list));
				commonHeaderView.getGridView().setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						int id1 = list.getList().get(position).getId();
						((MainActivity)getActivity()).switchContent(ZhuangFragment.newInstance(id1));
						
					}
					
				});
			}
		});
	}
	
	private int offset;
	private List<Result> results;
	@Override
	public boolean onLoad(int offset, int limit) {
		Preferences.User user = new Preferences.User(getActivity());
		this.offset = offset;
		boolean hasMore = true;
		GameGetsRequest request = new GameGetsRequest(new Pamras(user.getUid(), user
				.getToken(), cid));
		request.request(offset, limit);
		results =  request.getResult();
		if(results.size()<limit){
			hasMore = false;
		}
		
		return hasMore;
	}

	@Override
	public void onLoadSuccess() {
		if(offset == 0){
			list.clear();
		}
		this.list.addAll(Result.toNewModel(results));
		adapter.notifyDataSetChanged();
		
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_bottom:
			Preferences.User user = new Preferences.User(getActivity());
			if(!user.isLogin()){
				AlertDialog.open(getActivity(), "您目前不能发布题目\n请登录后重试", null);
				return;
			}
			PublishActivity.open(getActivity());
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		ZhuangNewListAdapter.Model model = (Model) adapterView.getItemAtPosition(position);
		KanZhuangActivity.open(getActivity(), model.getId());
		
	}

}
