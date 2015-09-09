package com.hua.bean;

import java.io.Serializable;

/**
 * Created by sundh on 2015/7/27.
 */
public class InterestChoiceBean implements Serializable {

    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
