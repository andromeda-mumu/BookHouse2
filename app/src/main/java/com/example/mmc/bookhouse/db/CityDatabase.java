package com.example.mmc.bookhouse.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by wangjiao on 2019/5/22.
 * 新建数据库
 *
 * 将外部数据库city.db导入到data目录下才能进行CRUD操作
 */

@Database(version  = CityDatabase.VERSION)
public class CityDatabase {
    public static final int VERSION =1;
}
