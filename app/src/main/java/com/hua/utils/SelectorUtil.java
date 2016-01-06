package com.hua.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by sundh on 2016/1/5.
 */
public class SelectorUtil {

    /**
     * 根据图片的id设置选中后背景颜色改变
     * @param context
     * @param idNormal
     * @param idPressed
     * @param idFocused
     * @return
     */
    public static StateListDrawable createDrawableSelector(Context context, int idNormal, int idPressed, int idFocused,
                                                int idUnable) {
        StateListDrawable bg = new StateListDrawable();

        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }

    /**
     *根据颜色值（e.g. 0xffffffff）中的值对TextView设置不同状态时其文字颜色
     * @param normal the normal color
     * @param pressed
     * @param focused
     * @param unable
     * @return
     */
    public static ColorStateList createColorSelectorByNum(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[] { pressed,focused,normal ,focused ,unable, normal };
        int[][] states = new int[6][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }


    /**
     *根据color。xml中的值对TextView设置不同状态时其文字颜色
     * @param normal the normal color
     * @param pressed
     * @param focused
     * @param unable
     * @return
     * e.g.  textView.setTextColor(SelectorUtil.createColorStateListByID(this,R.color.gray12,R.color.white,R.color.white,R.color.gray12));
     */
    public static ColorStateList createColorStateListByID(Context context, int normal, int pressed, int focused, int unable) {
        int[] colors = new int[] { getColor(context,pressed), getColor(context,focused), getColor(context,normal), getColor(context,focused), getColor(context,unable), getColor(context,normal) };
        int[][] states = new int[6][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }


    /**
     *根据布局文件中的id设置背景选中后颜色
     * @param strokeWidth 边框宽度
     * @param roundRadius 圆角半径
     * @param strokeColor 边框颜色
     * @param fillNormalColor 内部填充正常颜色
     * @param fillPressColor 内部填充选中后颜色
     *        int strokeWidth = 5; // 3dp 边框宽度
    //        int roundRadius = 15; // 8dp 圆角半径
    //        int strokeColor = Color.parseColor("#2E3135");//边框颜色
    //        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色
                             padding的设置目前只能从View中设置
     * @return
     */
    public static StateListDrawable createShapeSelectorByID(Context context,int strokeWidth,int roundRadius,int strokeColor, int fillNormalColor,int fillPressColor){

        GradientDrawable normal = new GradientDrawable();//创建drawable
        normal.setColor(getColor(context,fillNormalColor));
        normal.setCornerRadius(getDimen(context,roundRadius));
        normal.setStroke(getDimen(context,strokeWidth), getColor(context,strokeColor));

        GradientDrawable pressed = new GradientDrawable();//创建drawable
        pressed.setColor(getColor(context,fillPressColor));
        pressed.setCornerRadius(getDimen(context,roundRadius));
        pressed.setStroke(getDimen(context,strokeWidth), getColor(context,strokeColor));

        StateListDrawable bg = new StateListDrawable();
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, pressed);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, pressed);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, normal);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }

    /**
     *根据参数设置背景选中后颜色
     * @param strokeWidth 边框宽度
     * @param roundRadius 圆角半径
     * @param strokeColor 边框颜色
     * @param fillNormalColor 内部填充正常颜色
     * @param fillPressColor 内部填充选中后颜色
     *        int strokeWidth = 5; // 3dp 边框宽度
    //        int roundRadius = 15; // 8dp 圆角半径
    //        int strokeColor = Color.parseColor("#2E3135");//边框颜色
    //        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色
     * @return
     */
    public static StateListDrawable createShapeSelectorByNum(int strokeWidth,int roundRadius,int strokeColor, int fillNormalColor,int fillPressColor){

        ShapeDrawable shapeDrawable = new ShapeDrawable();
        GradientDrawable normal = new GradientDrawable();//创建drawable
        normal.setColor(fillNormalColor);
        normal.setCornerRadius(roundRadius);
        normal.setStroke(strokeWidth,strokeColor);

        GradientDrawable pressed = new GradientDrawable();//创建drawable
        pressed.setColor(fillPressColor);
        pressed.setCornerRadius(roundRadius);
        pressed.setStroke(strokeWidth,strokeColor);

        StateListDrawable bg = new StateListDrawable();
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, pressed);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, pressed);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, normal);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }


    private static int getColor(Context context,int color){
        return context.getResources().getColor(color);
    }

    private static int getDimen(Context context,int size){
        return (int) context.getResources().getDimension(size);
    }

}
