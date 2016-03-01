package com.hua.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hua.R;
import com.hua.image.ImageLoadUtil;
import com.hua.utils.PhoneInfoUtil;
import com.hua.utils.StringUtil;
import com.hua.utils.ViewHolder;

import java.util.List;

/**
 * Created by sundh on 2016/2/25.
 */
public class CustomsHorizontalScrollView extends LinearLayout {

    private Context mContext;
    private ImageView headIco, rearIco, more_hr;
    private HorizontalScrollView horizontal_scrollview;
    private CustomGridView medal_gridview;
    private MasterIconAdapter adapter;

    private int head_ico_src;
    private int head_ico_size;
    private int more_ico_src;
    private int more_ico_size;
    private int ico_size;

    private int head_ico_src_deafult_drawable = R.drawable.hr_ring;
    private int more_ico_src_default_drawable = R.drawable.hr_all;

    private int ico_default_size = (int) getResources().getDimension(R.dimen.circle_height);
    private int more_ico_default_size = (int) getResources().getDimension(R.dimen.circle_height);
    private int head_ico_default_size = (int) getResources().getDimension(R.dimen.circle_height);
    private int ico_default_spacing_size = (int) getResources().getDimension(R.dimen.circle_spacing);


    public CustomsHorizontalScrollView(Context context) {
        super(context);
        init(context);
    }

    public CustomsHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init(context);
    }

    public CustomsHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init(context);

    }

    private void init(Context context) {
        this.mContext = context;

        inflate(mContext, R.layout.custome_horizontal_layout, this);
        headIco = (ImageView) findViewById(R.id.hongrenTtitle);
        headIco.setImageResource(head_ico_src);
        RelativeLayout.LayoutParams headico_params = new RelativeLayout.LayoutParams(head_ico_size,head_ico_size);
        headIco.setLayoutParams(headico_params);

        horizontal_scrollview = (HorizontalScrollView) findViewById(R.id.horizontal_scrollview);
        medal_gridview = (CustomGridView) findViewById(R.id.medal_gridview);

        rearIco = (ImageView) findViewById(R.id.more_hr_scroll);
        rearIco.setImageResource(more_ico_src);
        RelativeLayout.LayoutParams rearIco_params = new RelativeLayout.LayoutParams(more_ico_size,more_ico_size);
        rearIco_params.addRule(RelativeLayout.RIGHT_OF,R.id.horizontal_scrollview);
        rearIco_params.addRule(RelativeLayout.CENTER_VERTICAL);
        rearIco_params.leftMargin = ico_default_spacing_size;
        rearIco.setLayoutParams(rearIco_params);

        more_hr = (ImageView) findViewById(R.id.more_hr);
        more_hr.setImageResource(more_ico_src);
        RelativeLayout.LayoutParams more_hr_params = new RelativeLayout.LayoutParams(more_ico_size,more_ico_size);
        more_hr_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        more_hr_params.addRule(RelativeLayout.CENTER_VERTICAL);
        more_hr.setLayoutParams(more_hr_params);

    }

    private void initAttr(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.custome_horizontalView);

        head_ico_src = a.getResourceId(R.styleable.custome_horizontalView_head_ico_src, head_ico_src_deafult_drawable);
        more_ico_src = a.getResourceId(R.styleable.custome_horizontalView_more_ico_src, more_ico_src_default_drawable);

        ico_size = a.getDimensionPixelSize(
                R.styleable.custome_horizontalView_ico_size, ico_default_size);// 默认为10dp

        head_ico_size = a.getDimensionPixelSize(
                R.styleable.custome_horizontalView_head_ico_size, head_ico_default_size);// 默认为10dp

        more_ico_size = a.getDimensionPixelSize(
                R.styleable.custome_horizontalView_more_ico_size, more_ico_default_size);// 默认为10dp
        a.recycle();
    }

    public void setData(List<String> datas) {

        if (StringUtil.isListNoNull(datas)) {
            adapter = new MasterIconAdapter(mContext, datas, PhoneInfoUtil.getScreenWidth(mContext));
            setGridView(datas);
            medal_gridview.setAdapter(adapter);
        }

    }

    /**
     * 设置GirdView参数，绑定数据
     */
    private void setGridView(List<String> datas) {
        int size = datas.size();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        float screenWidth = dm.widthPixels;
        float width = mContext.getResources().getDimension(R.dimen.circle_height);
        float spacing = ico_default_spacing_size;//mContext.getResources().getDimension(R.dimen.circle_spacing);

        int allWidth = (int) (width * size + spacing * (size - 1));
        int readWidth = (int) (allWidth + width + spacing);
        if (readWidth > screenWidth) {
            rearIco.setVisibility(View.GONE);
            more_hr.setVisibility(View.VISIBLE);
        } else {
            rearIco.setVisibility(View.VISIBLE);
            more_hr.setVisibility(View.GONE);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                allWidth, LayoutParams.MATCH_PARENT);
        medal_gridview.setLayoutParams(params);
        medal_gridview.setColumnWidth((int) width);
        medal_gridview.setHorizontalSpacing((int) spacing);
        medal_gridview.setStretchMode(GridView.NO_STRETCH);
        medal_gridview.setNumColumns(size);
    }


    class MasterIconAdapter extends BaseAdapter {

        private Context context;
        private List<String> list;
        int screenWidth;

        public MasterIconAdapter(Context ctx, List<String> lis, int screenWidth) {
            this.context = ctx;
            this.list = lis;
            this.screenWidth = screenWidth;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.medal_adapter_item, arg2, false);
            }
            CircleImageView head = ViewHolder.get(convertView, R.id.iv_head);
            head.setLayoutParams(new RelativeLayout.LayoutParams(ico_size, ico_size));
            ImageLoadUtil.loadImageWithDefalut(context, head, list.get(position));
            return convertView;
        }
    }
}
