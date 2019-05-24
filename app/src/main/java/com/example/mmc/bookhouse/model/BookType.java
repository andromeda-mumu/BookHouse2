package com.example.mmc.bookhouse.model;

import com.example.mmc.bookhouse.db.BookDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by wangjiao on 2019/5/24.
 * 功能描述：图书类型
 */
@Table(database = BookDatabase.class)
public class BookType extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public int id;
    @Column
    public String type;
}
