package com.mengle.lucky.fragments;


import java.util.ArrayList;
import java.util.Date;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.QuestionAdapter.Question;
import com.mengle.lucky.adapter.QuestionAdapter.QuestionItem;
import com.mengle.lucky.adapter.QuestionAdapter.Status;
import com.mengle.lucky.wiget.PeronCountView.Count;
import com.mengle.lucky.wiget.QuestionLayout;
import com.mengle.lucky.wiget.ThemeLayout;
import com.mengle.lucky.wiget.ThemeLayout.Theme;
import com.mengle.lucky.wiget.ThemeLayout.Theme.Header;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CaiFragment extends Fragment {

	private ThemeLayout themeLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.cai_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		themeLayout = (ThemeLayout) view.findViewById(R.id.theme);
		
		themeLayout.setTheme(new Theme(ThemeLayout.getBubbleDaanView(getActivity(), Color.RED, "A"), 
				new Header(new Count(R.drawable.btn_count, 5555), true, true, "http://pic5.nipic.com/20100126/2177138_152546644456_2.jpg"), 
				new Question(new Status(false,null), new ArrayList<QuestionItem>(){{
					new QuestionItem("A","结婚",0.5,Color.RED,QuestionLayout.TYPE_MOST);
					add(new QuestionItem("A","结婚",0.5,Color.RED,QuestionLayout.TYPE_MOST));
					add(new QuestionItem("A","结婚",0.5,Color.RED,QuestionLayout.TYPE_MOST));
				}}), 5, new Date(11), true));
	}

}
