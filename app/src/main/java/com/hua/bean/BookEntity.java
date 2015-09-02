package com.hua.bean;

/**
 * Created by sundh on 2015/7/22.
 */

import java.io.Serializable;

/** 实体类 */
public class BookEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bookId;
    private String bookName;

    public BookEntity(String bookName, String bookId) {
        super();
        this.bookName = bookName;
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}