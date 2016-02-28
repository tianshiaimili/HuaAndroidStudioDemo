package com.hua.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.hua.R;
import com.hua.image.ImageLoadUtil;

import java.util.List;

/**
 * 图片浏览Adapter
 *
 * @author alvin 2013-9-30下午4:06:16 TODO
 */
public class SquareGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int imagesize = 0;
    private List<String> imgurl;// 选中
    int number = 3;

    public SquareGridViewAdapter(Context mContext, int imagesize, List<String> imgurl) {
        this.mContext = mContext;
        this.imgurl = imgurl;
        this.imagesize = imagesize / number - 5;
    }

    public SquareGridViewAdapter(Context mContext, int imagesize, int number, List<String> imgurl) {
        this(mContext, imagesize, imgurl);
        this.imagesize = imagesize / number - 5;
        this.number = number;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imgurl.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        FrameLayout frameLayout;
        ViewHolde holde;
        if (null == convertView) {
            holde = new ViewHolde();
            frameLayout = new FrameLayout(mContext);
            ImageView imageView = new ImageView(mContext);
            imageView.setFocusable(false);
            imageView.setFocusableInTouchMode(false);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, imagesize));
            frameLayout.addView(imageView);
            holde.img = imageView;
            frameLayout.setTag(holde);
            convertView = frameLayout;
        } else {
            holde = (ViewHolde) convertView.getTag();
        }
        holde.img.setImageResource(R.drawable.cover);
        if (parent.getChildCount() == position) {
            if (TextUtils.isEmpty(imgurl.get(position))) {
                holde.img.setVisibility(View.GONE);
            } else {
//                ImageLoadUtil.loadImageWithUrl(mContext, holde.img, imgurl.get(position), R.drawable.cover, R.drawable.cover, true);
                ImageLoadUtil.loadImageWithCover(mContext, holde.img, imgurl.get(position), 1);
//                ImageLoadUtil.loadImageWithCover(mContext, holde.img, imgurl.get(position));
            }
        }

        return convertView;
    }

    class ViewHolde {
        ImageView img;
    }


}
