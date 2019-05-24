package com.example.mmc.bookhouse.model;

import com.example.mmc.bookhouse.db.BookDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */
@Table(database = BookDatabase.class)
public class Book extends BaseModel implements Serializable{
    private static final long serialVersionUID = 4902038122965537667L;
    @PrimaryKey(autoincrement = true)
    public int id;
    @Column
    public String name;
    @Column
    public String author;
    @Column
    public String location;
    @Column
    public String desc;
    @Column
    public String type;
    @Column
    public String tag;
    @Column
    public String date;
    @Column
    public String times;

}
