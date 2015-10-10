package com.hua.activity.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.bean.InterestChoiceBean;
import com.hua.utils.LogUtils;
import com.hua.view.MMFlowLayout;
import com.hua.view.RefleshListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sundh on 2015/7/27.
 */
@EActivity(R.layout.choice_interest_test)
public class ChoiceInterest3  extends Activity  {

    @ViewById
    TextView title_tv;
    @ViewById
    ImageView cancel_tv,sure_tv;

//    @ViewById(R.id.flowlayout)
    MMFlowLayout flowlayout;
    LinearLayout  linearLayout;

    @ViewById(R.id.listview)
    RefleshListView listview;

    
    View errorView;
//    @ViewById
//    ViewStub vs_error;
//    @ViewById
//    LinearLayout dialogbody;

//    private ErroeMessageUtil erroeMessageUtil;

    int [] colors = {
            R.drawable.blue_box_selector,
            R.drawable.red_box_selector,
            R.drawable.pink_box_selector,
            R.drawable.green_box_selector,
            R.drawable.grey_box_selector};

//
//    @ViewById(R.id.gridview)
//    CustomGridView gridview;

    @StringArrayRes(R.array.interest_option)
    String [] testTitles;

    List<InterestChoiceBean> listBean = new ArrayList<InterestChoiceBean>();
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
    	linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.folowlayout, null);
//    	flowlayout = (MMFlowLayout) LayoutInflater.from(this).inflate(R.layout.follower, null);
    	flowlayout = (MMFlowLayout) linearLayout.findViewById(R.id.flowlayout);
//        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.MATCH_PARENT);
//        flowlayout.setLayoutParams(params);
        initChildViews();
        LogUtils.e("count--" + flowlayout.getChildCount());
        setListView();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getDataList(false);
    }

    public void setListView(){

//        listview.addChild(LayoutInflater.from(this).inflate(R.layout.change_position, null));
        listview.addChild(linearLayout);
        listview.setOnRefreshListener(new RefleshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                getDataList(false);
            }
        });
        listview.setAdapter(new TestAdapter());
//        listview.
//        listview.setPreLoadMore(true);
        listview.refreshCompleted();
//        listview.loadMoreCompleted();
        listview.setLoadMoreable(true);
//
//        erroeMessageUtil = new ErroeMessageUtil(this);
//        erroeMessageUtil.setOnClickListener(new ErroeMessageUtil.onClickListener() {
//
//            @Override
//            public void Result() {
////                getDataList(false);
//            }
//        });

    }

    private void initChildViews() {

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
                    Toast.makeText(ChoiceInterest3.this, content, 300).show();
                }
            });
            flowlayout.addView(view,lp);
        }
    }


    private void getDataList(boolean flag) {
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("uid", uid);
//        String url = RequestHelper.buildCommonToken(UrlPath.MASTER_LIST, params);
//        MmRequest request = new MmRequest(url, new ResponseListener<String>(
//                this){
//            @Override
//            protected void onPtSucc(String url, String result) {
//                datacallback(result);
//                super.onPtSucc(url, result);
//            }
//
//            @Override
//            public void onNetworkComplete() {
//                dialogbody.setVisibility(View.GONE);
//                listview.refreshCompleted();
//                listview.loadMoreCompleted();
//                super.onNetworkComplete();
//            }
//
//            @Override
//            protected void onFail(int errorType, String errorDesc) {
//                showErrorMessage(ErroeMessageUtil.ERROR_NO_NETWORK);
//                // super.onFail(errorType, errorDesc);
//            }
//
//            @Override
//            protected void onPtError(String url, Result.ErrorMsg errorMsg) {
//                if(errorMsg.getErrno() == -30){
//                }
//                adapter.notifyDataSetChanged();
//                showErrorMessage(ErroeMessageUtil.ERROR_NO_MASTER_DATA);
//            }
//        });
//        addQueue(request);

    }

    private void datacallback(String json) {
//
//        DataParser<MasterListBean> pd = new DataParser<MasterListBean>(
//                MasterListBean.class);
//        List<MasterListBean> lis = pd.getDatas(json);
//        if (lis.size() != 0) {
//            if (PAGENOW == 1) {
//                list.clear();
//            }
//            list.addAll(lis);
//            adapter.notifyDataSetChanged();
//            PAGENOW++;
//            listview.setLoadMoreable(true);
//            showErrorMessage(ErroeMessageUtil.ERROR_NO_MASTER_DATA);
//        } else {
//            if (StringUtil.isListNoNull(list)) {
//                ToastUtil.showToast(getActivity(), "没有更多数据");
//            }
//        }


    }


    private void showErrorMessage(int errorType) {
//        if (!StringUtil.isListNoNull(null)) {
//            if (vs_error != null && errorView == null) {
//                errorView = vs_error.inflate();
//            }
//            if (errorView != null) {
//                erroeMessageUtil.showErrorMessage(listview, dialogbody,
//                        errorView, errorType);
//            }
//        } else {
//            if (errorView != null) {
//                errorView.setVisibility(View.GONE);
//            }
//        }
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

    class TestAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 10;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textView = new TextView(getApplicationContext());
			textView.setText("Test"+position);
			
			return textView;
		}
    	
    }
    
    
}

