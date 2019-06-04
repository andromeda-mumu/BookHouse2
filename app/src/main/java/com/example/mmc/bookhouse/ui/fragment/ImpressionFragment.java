package com.example.mmc.bookhouse.ui.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.liyu.sqlitetoexcel.ExcelToSQLite;
import com.liyu.sqlitetoexcel.SQLiteToExcel;

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
        new ExcelToSQLite
                .Builder(activity())
                .setDataBase(activity().getDatabasePath("BookDatabase.db").getPath())
                .setAssetFileName("book.xls")
                //                .setFilePath(outputFile)
                .setDecryptKey("1234567")
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .start(new ExcelToSQLite.ImportListener() {
                    @Override
                    public void onStart() {
                        Log.d("=mmc=","----start----");
                    }

                    @Override
                    public void onCompleted(String result) {
                        Log.d("=mmc=","Import completed--->" + result);
//                        showDbMsg(result);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("=mmc=","-----error---"+e.getMessage());
                    }
                });
    }

    /**
     * sql -> xls
     * 数据库转excel,并在外部存储中可以导出查看
     * 手机也能直接查看
     * @param view
     */
    public void doExport(View view){
        new SQLiteToExcel.Builder(activity())
                .setDataBase(activity().getDatabasePath("BookDatabase.db").getPath())
                .setTables("BookType")
                //                .setEncryptKey("1234567")
                //                .setProtectKey("9876543")
                .start(new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onCompleted(String filePath) {
                        Log.d("=mmc=", "Export completed--->" + filePath);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }
    private void showDbMsg(String dbName) {
        SQLiteDatabase database;
        try {
            database = SQLiteDatabase.openOrCreateDatabase(dbName, null);
            Cursor cursor = database.rawQuery("select name from sqlite_master where type='table' order by name", null);
            while (cursor.moveToNext()) {
                Log.d("=mmc=","New tables is : " + cursor.getString(0) + "  ");
                Cursor cursor2 = database.rawQuery("select * from " + cursor.getString(0), null);
                while (cursor2.moveToNext()) {
                    Log.d("=mmc=","\n");
                    for (int i = 0; i < cursor2.getColumnCount(); i++) {
                        Log.d("=mmc=",cursor2.getString(i) + "  ");
                    }
                }
                cursor2.close();
            }
            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
