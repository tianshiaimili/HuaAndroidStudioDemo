package com.hua.interfaces;

/**
 */
public interface CommonItemView<T> {

    void bindView(T data);
    void bindView(T data, int position);
    void bindView(T data, int type, int position);
}
