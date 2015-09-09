package com.hua.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.annotationdemo.R;
import com.example.annotationdemo.bean.Person;

/**
 * Created by test on 2015/7/2.
 */
@EViewGroup(R.layout.person_item)
public class PersonItemView extends LinearLayout {

    @ViewById
    TextView firstNameView;

    @ViewById
    TextView lastNameView;

    public PersonItemView(Context context) {
        super(context);
    }

    public void bind(Person person) {
        firstNameView.setText(person.getFirstName());
        lastNameView.setText(person.getLastName());
    }
}