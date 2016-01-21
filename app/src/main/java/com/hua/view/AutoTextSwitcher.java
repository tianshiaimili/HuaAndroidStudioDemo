package com.hua.view;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;

import com.hua.R;
import com.rockerhieu.emojicon.EmojiconTextView;

import java.util.ArrayList;


/**
 */
public class AutoTextSwitcher extends TextSwitcher {

    private Context context;
    private ArrayList<String> textString = new ArrayList<String>();
    private Handler handler = new Handler();
    private int switchPosition;
    private int switchTime = 1000;//间隔多长时间切换

    public AutoTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public AutoTextSwitcher(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                EmojiconTextView textView = new EmojiconTextView(context);
                textView.setTextSize(11);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setTextColor(context.getResources().getColor(R.color.home_item_subtitles));
                return textView;
            }
        });
        setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_up_in));
        setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_up_out));
    }

    public void setData(ArrayList<String> textString) {
        this.textString = textString;
        setCurrentText(textString.get(0));
    }
    public void startShow() {
        if(handler != null && runnable != null){
            stopShow();
            handler.postDelayed(runnable, switchTime);
        }
    }

    public void stopShow(){
        handler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, switchTime);
            switchPosition++;
            if (switchPosition >= textString.size()) {
                switchPosition = 0;
            }
            setText(textString.get(switchPosition));
        }
    } ;

}
