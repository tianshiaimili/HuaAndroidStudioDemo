package com.hua.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.annotationdemo.bean.Person;
import com.example.annotationdemo.view.PersonItemView;
import com.example.annotationdemo.view.PersonItemView_;

/**
 * Created by test on 2015/7/2.
 */
@EBean
public class PersonListAdapter extends BaseAdapter {

    List<Person> persons;

    
//    @Bean(InMemoryPersonFinder.class)
//    @Bean(InMemoryPersonFinder.class)
//    PersonFinder personFinder;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        persons = new ArrayList<Person>();
//        persons =personFinder.findAll();
//        persons =setData()
    }

    public void setData(List<Person> data){
        this.persons = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PersonItemView personItemView;
        if (convertView == null) {
            personItemView = PersonItemView_.build(context);
        } else {
            personItemView = (PersonItemView) convertView;
        }

        personItemView.bind(getItem(position));

        return personItemView;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Person getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
