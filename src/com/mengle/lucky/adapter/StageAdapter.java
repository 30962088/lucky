package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.fragments.StageItemFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StageAdapter extends FragmentPagerAdapter {

	public static class Stage {
		private String title;
		private String photo;
		private String endTime;
		private int person;

		

		public Stage(String title, String photo, String endTime, int person) {
			super();
			this.title = title;
			this.photo = photo;
			this.endTime = endTime;
			this.person = person;
		}
		
		public int getPerson() {
			return person;
		}
		public String getTitle() {
			return title;
		}

		public String getEndTime() {
			return endTime;
		}

		public String getPhoto() {
			return photo;
		}
	}

	private List<Stage> list;

	public StageAdapter(FragmentManager fragmentManager, List<Stage> list) {
		super(fragmentManager);
		this.list = list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Fragment getItem(int position) {
		Stage stage = list.get(position);

		return StageItemFragment.newInstance(stage.title,stage.photo, stage.endTime,stage.person);
	}
}
