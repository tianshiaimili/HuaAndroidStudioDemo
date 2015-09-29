package com.hua.activity.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.view.MMFlowLayout;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.Random;

/**
 * Created by sundh on 2015/7/27.
 */
@EActivity(R.layout.choice_interest)   
public class ChoiceInterest  extends Activity  {

    @ViewById
    TextView title_tv;
    @ViewById
    ImageView cancel_tv,sure_tv;

    @ViewById(R.id.flowlayout)
    MMFlowLayout flowlayout;
//    MMFlowLayout  flowlayout;

    int [] colors = {R.drawable.green_big_box_selector,
            R.drawable.blue_box_selector,
            R.drawable.red_box_selector,
            R.drawable.pink_box_selector,
            R.drawable.green_big_box_selector,
            R.drawable.grey_box_selector};

//
//    @ViewById(R.id.gridview)
//    CustomGridView gridview;

    @StringArrayRes(R.array.interest_option)
    String [] testTitles;

//    List<InterestChoiceBean> listBean = new ArrayList<>();
    InterestAdapter adapter ;
    private final static int CANCEL_CODE = 0;
    private final static int SURE_CODE = 200;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
    }

    @AfterInject
    void initVariable(){

//        TypedArray typedArray = getResources().obtainTypedArray(R.array.myfragment_item_icos);
//        for(int i =0;i < typedArray.getIndexCount();i++){
//            MineBean mineBean = new MineBean(i,titles[i],typedArray.getResourceId(i,R.drawable.woicon2));
//            if(i == 0){
//                mineBean.setMyValue(getBBType());
//            }
//            listData.add(mineBean);
//        }

    }

    @AfterViews
    void initViews(){

//        adapter = new InterestAdapter(this,listBean);
//        gridview.setAdapter(adapter);
        initChildViews();

    }

    private void initChildViews() {

    	//这个是针对ViewGroup的Layout
    	
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 20;
        lp.rightMargin = 20;
        lp.topMargin = 20;
        lp.bottomMargin = 20;
        for(int i = 0; i < testTitles.length; i ++){
            CheckBox view = new CheckBox(this);
            view.setButtonDrawable(android.R.color.transparent);
            view.setText(testTitles[i]);
            view.setTextColor(Color.parseColor("#666666"));
            view.setTextSize(16);
            view.setGravity(Gravity.CENTER);
//            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            Random random = new Random();
            int index = random.nextInt(colors.length);
            view.setBackgroundResource(colors[index]);
            final String content = view.getText().toString();
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Toast.makeText(ChoiceInterest.this, content, 300).show();
                }
            });
            flowlayout.addView(view,lp);
        }
    	
    	/**这个是针对RelativeLayout的*/
    	
//    	 LayoutParams lp = new LayoutParams(
//                 LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//         lp.leftMargin = 20;
//         lp.rightMargin = 20;
//         lp.topMargin = 20;
//         lp.bottomMargin = 20;
//         for(int i = 0; i < testTitles.length; i ++){
//             CheckBox view = new CheckBox(this);
//             view.setButtonDrawable(android.R.color.transparent);
//             view.setText(testTitles[i]);
//             view.setTextColor(Color.parseColor("#666666"));
//             view.setTextSize(16);
//             view.setGravity(Gravity.CENTER);
////             view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
//             Random random = new Random();
//             int index = random.nextInt(colors.length);
//             view.setBackgroundResource(colors[index]);
//             final String content = view.getText().toString();
//             view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                 @Override
//                 public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                     Toast.makeText(ChoiceInterest.this, content, 300).show();
//                 }
//             });
//             flowlayout.addView(view,lp);
//         }
    	
    }

    @Click
    void cancel_tv(){
        this.setResult(CANCEL_CODE);
        finish();
    }

    @Click
    void sure_tv(){
//        ArrayList<String> choiceData = adapter.getReturnData();
//        if(choiceData.size() == 0){
//            ToastUtil.showToast(this,"请选择兴趣先哦");
//        }else {
//            Intent intent = new Intent();
////            intent.putIntegerArrayListExtra("choiceData",choiceData);
////            intent.putCharSequenceArrayListExtra("choiceData",new ArrayList<CharSequence>());
//            intent.putStringArrayListExtra("choiceData",choiceData);
//            this.setResult(SURE_CODE, intent);
//            finish();
//        }

    }

}

