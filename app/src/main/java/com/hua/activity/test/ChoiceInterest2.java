package com.hua.activity.test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.hua.R;
import com.hua.bean.InterestBean;
import com.hua.utils.LogUtils;
import com.hua.view.CustomGridView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sundh on 2015/7/27.
 */
@EActivity(R.layout.choice_interest_layout)
public class ChoiceInterest2  extends Activity  {

    @ViewById
    TextView cancel_tv,title_tv,sure_tv;

    @ViewById(R.id.gridview)
    CustomGridView gridview;

    @StringArrayRes(R.array.interest)
    String [] testTitles;

    List<InterestBean> listBean = new ArrayList<InterestBean>();
    InterestAdapter adapter ;

    @AfterInject
    void initVariable(){

        for(String option : testTitles){
        	InterestBean bean = new InterestBean();
            bean.setName(option);
            listBean.add(bean);
        }

    }

    @AfterViews
    void initViews(){

        adapter = new InterestAdapter(this,listBean);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                OnItemClick(listBean.get(i));
            	InterestBean bean = (InterestBean) adapterView.getItemAtPosition(position);
            	String id = adapterView.getItemIdAtPosition(position)+"";
            	LogUtils.d("bean.name = " + bean.getName());
            	LogUtils.d("bean.id = "+id);
            	
            	
            }
        });
    }

    private void OnItemClick(final InterestBean bean){



    }

}

class InterestAdapter extends BaseAdapter{

    Context context;
    List<InterestBean> beans;

    public InterestAdapter(Context ct ,List<InterestBean> temp){
        context = ct;
        beans = temp;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView == null){
//            convertView = new TextView(context);//LayoutInflater.from(context).inflate(R.layout.interest_grid_item,null);
        	convertView = LayoutInflater.from(context).inflate(R.layout.interest_choice_item,null);
            viewHolder = new ViewHolder();
//            viewHolder.textView = (TextView) convertView;
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.testC);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.textView.setText(beans.get(i).getName());
        viewHolder.checkBox.setText(beans.get(i).getName());
        viewHolder.checkBox.setId(i);
//        convertView.setId(i);

        viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
//					ViewHolder  viewHolder = (ViewHolder) buttonView.getTag();
//					LogUtils.d("#viewHolder= "+viewHolder);
					LogUtils.e("buttonView--"+buttonView+"  || id = "+buttonView.getId());
//					LogUtils.d("tag = "+viewHolder.checkBox.getId());
					
					
				}else {
					
					LogUtils.i("buttonView--"+buttonView+"  || id = "+buttonView.getId());
					
				}
				
			}
		});
        
        return convertView;
    }

    class ViewHolder{
//        TextView textView;
    	CheckBox checkBox;

    }

}