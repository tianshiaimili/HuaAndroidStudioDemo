package com.hua.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.R;
import com.hua.utils.LogUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;


@EFragment(R.layout.myfragment_main)
public class MyFragment extends Fragment{

	
	@AfterInject
	void afterInjectMethod(){
		LogUtils.d("MyFragment--afterInjectMethod----");
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.d("MyFragment--onCreate----");
	}


	
	@AfterViews
	void AfterViewsMethod(){
		LogUtils.d("MyFragment--AfterViewsMethod----");
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtils.d("MyFragment--onCreateView----");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		LogUtils.d("MyFragment--onViewCreated----");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		LogUtils.d("MyFragment--onActivityCreated----");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		LogUtils.d("MyFragment--onStart----");
		super.onStart();
	}

	@Override
	public void onResume() {
		LogUtils.d("MyFragment--onResume----");
		super.onResume();
	}

	@Override
	public void onPause() {
		LogUtils.d("MyFragment--onPause----");
		super.onPause();
	}

	@Override
	public void onStop() {
		LogUtils.d("MyFragment--onStop----");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		LogUtils.d("MyFragment--onDestroyView----");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		LogUtils.d("MyFragment--onDestroy----");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		LogUtils.d("MyFragment--onDetach----");
		super.onDetach();
	}

	
	
}
