/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rockerhieu.emojicon;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rockerhieu.emojicon.emoji.Emojicon;

import cn.mama.emoji.R;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com)
 */
public class EmojiAdapter extends ArrayAdapter<Emojicon> {
    private boolean mUseSystemDefault = false;
    private boolean mUseEmoji = true;

    public EmojiAdapter(boolean useEmoji, Context context, List<Emojicon> data) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = false;
        mUseEmoji = useEmoji;
    }

    public EmojiAdapter(Context context, List<Emojicon> data) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = false;
    }

    public EmojiAdapter(Context context, List<Emojicon> data, boolean useSystemDefault) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = useSystemDefault;
    }

    public EmojiAdapter(Context context, Emojicon[] data) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = false;
    }

    public EmojiAdapter(Context context, Emojicon[] data, boolean useSystemDefault) {
        super(context, R.layout.emojicon_item, data);
        mUseSystemDefault = useSystemDefault;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.emojicon_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (EmojiconTextView) v.findViewById(R.id.emojicon_icon);
            holder.icon.setUseSystemDefault(mUseSystemDefault);
            v.setTag(holder);

            float density = v.getResources().getDisplayMetrics().density;
            int padding = Math.round((float)(mUseEmoji ? 4 : 4) * density);
            v.setPadding(0, padding, 0, padding);
        }

        Emojicon emoji = getItem(position);
        ViewHolder holder = (ViewHolder) v.getTag();
        if(emoji.getIcon()!=0){
            holder.icon.setText("");
            holder.icon.setBackgroundResource(emoji.getIcon());
        }else{
            holder.icon.setText(emoji.getEmoji());
        }

        return v;
    }

    class ViewHolder {
        EmojiconTextView icon;
    }
}