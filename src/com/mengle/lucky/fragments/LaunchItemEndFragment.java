package com.mengle.lucky.fragments;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mengle.lucky.R;
import com.mengle.lucky.fragments.LaunchItemFragment.Item;
import com.mengle.lucky.wiget.CustomAnimationDrawableNew;

public class LaunchItemEndFragment extends BlurFragment implements Callback {

	public static LaunchItemEndFragment newInstance(Item item) {
		LaunchItemEndFragment fragment = new LaunchItemEndFragment();
		fragment.item = item;
		return fragment;
	}

	private ImageView topView;

	private ImageView bottomView;

	private ImageView animView;

	private Item item;

	private View sevenView;

	private View starView;

	private View goodluckyView;
	
	private View finalView;
	
	private View sectionView;
	
	private Animation goodluckyAnimation;
	
	private CustomAnimationDrawableNew a1;
	
	private Animation sevenAnimation;
	
	private Animation starAnimation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.guide_end_item, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		topView = (ImageView) view.findViewById(R.id.top);

		bottomView = (ImageView) view.findViewById(R.id.bottom);

		animView = (ImageView) view.findViewById(R.id.animView);

		topView.setImageResource(item.getTop());

		bottomView.setImageResource(item.getBottom());

		sevenView = view.findViewById(R.id.seven);

		starView = view.findViewById(R.id.star);

		goodluckyView = view.findViewById(R.id.goodlucky);
		
		finalView = view.findViewById(R.id.launch_final);

		sectionView = view.findViewById(R.id.section);
		
		view.findViewById(R.id.btn_enter).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();
				
			}
		});
		
		goodluckyAnimation = AnimationUtils.loadAnimation(
				getActivity(), R.anim.goodlucky);
		goodluckyAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				startAnim4();
				
			}
		});
		
		a1 = new CustomAnimationDrawableNew(
				(AnimationDrawable) getResources().getDrawable(
						R.drawable.anim_guide)) {
			@Override
			protected void onAnimationFinish() {

				startAnim1();
			}

		};

		animView.setImageDrawable(a1);
		
		
		sevenAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.seven);
		sevenAnimation.setFillAfter(true);
		sevenAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startAnim2();

			}
		});
		
		starAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.star);
		starAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startAnim3();

			}
		});
		starAnimation.setFillAfter(true);
		
	}

	@Override
	public void setBlur(boolean blur) {
		if (blur) {
			topView.setVisibility(View.INVISIBLE);
			bottomView.setVisibility(View.VISIBLE);
		} else {
			topView.setVisibility(View.VISIBLE);
			bottomView.setVisibility(View.INVISIBLE);
		}
	}

	public void startAnim() {
		// startAnim1();
		topView.setVisibility(View.VISIBLE);
		animView.setVisibility(View.VISIBLE);

		
		a1.setOneShot(true);
		a1.start();
	}


	private void startAnim1() {

		
		sevenView.setVisibility(View.VISIBLE);
		sevenView.startAnimation(sevenAnimation);

	}

	private void startAnim2() {
		
		starView.setVisibility(View.VISIBLE);
		starView.startAnimation(starAnimation);

	}

	private void startAnim3() {
		
		// goodluckyAnimation.setFillAfter(true);
		goodluckyView.setVisibility(View.VISIBLE);
		
		goodluckyView.startAnimation(goodluckyAnimation);
	}
	
	private void startAnim4(){
		sectionView.setVisibility(View.GONE);
		finalView.setVisibility(View.VISIBLE);
		
		finalView.startAnimation(AnimationUtils.loadAnimation(
				getActivity(), R.anim.launch_final));
	}

	@Override
	public void invalidateDrawable(Drawable who) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scheduleDrawable(Drawable who, Runnable what, long when) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unscheduleDrawable(Drawable who, Runnable what) {
		// TODO Auto-generated method stub

	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
