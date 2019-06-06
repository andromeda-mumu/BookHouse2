package com.example.mmc.bookhouse.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mmc.bookhouse.MainActivity;
import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.model.BookType;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.utils.SqliteAndXlsUtils;
import com.example.mmc.bookhouse.utils.Tools;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

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
    public static final String ORIGIN_FILE_PATH = MainActivity.OUTPATH+"/book.xls";
    public static final String BACKUP_FILE_PATH = MainActivity.OUTPATH+"/book_backup.xls";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_impression;
    }

    @OnClick({R.id.export,R.id.btn_import,R.id.btn_import_two})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.export:
                doExport();
                break;
            case  R.id.btn_import:
                doImport();
                break;
            case R.id.btn_import_two:
                doImportTwo();
                break;
            default :
                break;

        }
    }


    /**
     * 情况数据库表
     */
    private void clearTable() {
        List<Book> books = SQLite.select()
                .from(Book.class)
                .queryList();
        if(Tools.notEmpty(books)){
            Delete.table(Book.class);
            Delete.table(BookType.class);
        }

    }

    /**
     * 如果已经导入过数据，那先清空再导入
     * book.xls->sql
     * 外部excel表格转换成sqlite数据
     *
     */
    public void doImport() {
//        boolean importDataSuc =  SharePreferentUtils.getBoolean(SharePref.IMPORT_DATA,false);
//        if(importDataSuc){
//        }
        clearTable();
        SqliteAndXlsUtils.excelToSqlite(ORIGIN_FILE_PATH);
    }

    /**
     * 如果已经导入过数据，那先清空再导入
     * book.xls->sql
     * 外部excel表格转换成sqlite数据
     *
     */
    private void doImportTwo() {
//        boolean importDataSuc =  SharePreferentUtils.getBoolean(SharePref.IMPORT_DATA,false);
//        if(importDataSuc){
//        }
        clearTable();
        SqliteAndXlsUtils.excelToSqlite(BACKUP_FILE_PATH);
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
