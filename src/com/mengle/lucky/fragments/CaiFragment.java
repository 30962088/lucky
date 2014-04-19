package com.mengle.lucky.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.QuestionAdapter;
import com.mengle.lucky.adapter.QuestionAdapter.Question;
import com.mengle.lucky.adapter.QuestionAdapter.QuestionItem;
import com.mengle.lucky.adapter.QuestionAdapter.Status;
import com.mengle.lucky.wiget.QuestionLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class CaiFragment extends Fragment {

	private GridView gridView;

	private View selectCoinBtn;
	
	private View submitBtn;
	
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
		submitBtn = view.findViewById(R.id.submit);
		submitBtn.setEnabled(false);
		selectCoinBtn = view.findViewById(R.id.selectCoin);
		selectCoinBtn.setEnabled(false);
		gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setAdapter(new QuestionAdapter(getActivity(),
				new Question(new Status(false, Arrays.asList(new Integer[]{1})), new ArrayList<QuestionItem>() {
					{
						add(new QuestionItem("A", "文章", 0.42, Color.parseColor("#a0d468"),QuestionLayout.TYPE_MOST));
						add(new QuestionItem("B", "文章", 0.42, Color.parseColor("#4fc0e8"),QuestionLayout.TYPE_NORMAL));
						add(new QuestionItem("C", "文章", 0.42, Color.parseColor("#e47134"),QuestionLayout.TYPE_NORMAL));
					}
				})));
	}

}
