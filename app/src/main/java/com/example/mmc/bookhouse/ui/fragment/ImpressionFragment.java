package com.example.mmc.bookhouse.ui.fragment;

import android.view.View;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.utils.SqliteAndXlsUtils;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：添加读后感
 */

public class ImpressionFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_impression;
    }

    /**
     * xls->sql
     * 外部excel表格转换成sqlite数据
     * @param view
     */
    public void doImport(View view){
        SqliteAndXlsUtils.excelToSqlite();
    }

    /**
     * sql -> xls
     * 数据库转excel,并在外部存储中可以导出查看
     * 手机也能直接查看
     * @param view
     */
    public void doExport(View view){
       SqliteAndXlsUtils.sqliteToExcel();
    }

}
