package com.hua.activity.test;

import android.app.Activity;
import android.widget.TextView;

import com.hua.R;
import com.hua.bean.InterestBean;
import com.hua.view.CustomGridView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;


@EActivity(R.layout.choice_interest_layout)
public class InterestActivity extends Activity {
	
	@ViewById
	TextView cancel_tv,title_tv,sure_tv;
	
	@ViewById
	CustomGridView gridview;
	
	@StringArrayRes(R.array.interest)
	String [] titles;
	
	List<InterestBean> list = new ArrayList<InterestBean>();
	
	@AfterInject
	void initVariable(){
		
		for(String string : titles){
			InterestBean bean = new InterestBean();
			bean.setName(string);
			list.add(bean);
			
		}
		
	}
	
	@AfterViews 
	void initViews(){
		
		
		
		
	}
	
	
}
