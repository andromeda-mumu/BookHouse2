package com.example.mmc.bookhouse.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.utils.SqliteAndXlsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：添加读后感
 */

public class ImpressionFragment extends BaseFragment {
    @BindView(R.id.export)
    Button mExport;
    @BindView(R.id.btn_import)
    Button mBtnImport;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_impression;
    }

    @OnClick({R.id.export,R.id.btn_import})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.export:
                doExport();
                break;
            case  R.id.btn_import:
                doImport();
                break;

            default :
                break;

        }
    }

    /**
     * xls->sql
     * 外部excel表格转换成sqlite数据
     *
     */
    public void doImport() {
        SqliteAndXlsUtils.excelToSqlite();
    }

    /**
     * sql -> xls
     * 数据库转excel,并在外部存储中可以导出查看
     * 手机也能直接查看
     */
    public void doExport() {
        SqliteAndXlsUtils.sqliteToExcel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
