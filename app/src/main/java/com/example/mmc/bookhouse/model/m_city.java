package com.example.mmc.bookhouse.model;

import com.example.mmc.bookhouse.db.CityDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */
@Table(database = CityDatabase.class)
public class m_city extends BaseModel implements Serializable{
    private static final long serialVersionUID = 4902038122965537667L;
//    @PrimaryKey(autoincrement = true)
//    public int id;
    @PrimaryKey
    public int cid;
    @Column
    public String cname;

}
