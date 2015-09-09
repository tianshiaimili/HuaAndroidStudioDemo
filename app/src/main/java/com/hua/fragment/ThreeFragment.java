package com.hua.fragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annotationdemo.R;
import com.example.annotationdemo.utils.LogUtils;


@EFragment(R.layout.threefragment_main)
public class ThreeFragment extends Fragment{

	  @FragmentArg("myStringArgument")
	  String myMessage;

	  @FragmentArg
	  String anotherStringArgument;
	
	
	@AfterInject
	void afterInjectMethod(){
		LogUtils.d("ThreeFragment--afterInjectMethod----");
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.d("ThreeFragment--onCreate----");
	}


	
	@AfterViews
	void AfterViewsMethod(){
		LogUtils.d("ThreeFragment--AfterViewsMethod----");
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtils.d("ThreeFragment--onCreateView----");
		LogUtils.i("ThreeFragment--myMessage----==="+myMessage);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		LogUtils.d("ThreeFragment--onViewCreated----");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		LogUtils.d("ThreeFragment--onActivityCreated----");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		LogUtils.d("ThreeFragment--onStart----");
		super.onStart();
	}

	@Override
	public void onResume() {
		LogUtils.d("ThreeFragment--onResume----");
		super.onResume();
	}

	@Override
	public void onPause() {
		LogUtils.d("ThreeFragment--onPause----");
		super.onPause();
	}

	@Override
	public void onStop() {
		LogUtils.d("ThreeFragment--onStop----");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		LogUtils.d("ThreeFragment--onDestroyView----");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		LogUtils.d("ThreeFragment--onDestroy----");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		LogUtils.d("ThreeFragment--onDetach----");
		super.onDetach();
	}

	
	
}
