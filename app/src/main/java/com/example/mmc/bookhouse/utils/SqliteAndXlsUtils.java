package com.example.mmc.bookhouse.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mmc.bookhouse.MainActivity;
import com.example.mmc.bookhouse.app.BookApplication;
import com.example.mmc.bookhouse.model.SharePref;
import com.liyu.sqlitetoexcel.ExcelToSQLite;
import com.liyu.sqlitetoexcel.SQLiteToExcel;

/**
 * Created by wangjiao on 2019/6/4.
 * 功能描述：
 * xls <-> sql
 */

public class SqliteAndXlsUtils  {
    /**
     * xls->sql
     * 外部excel表格转换成sqlite数据
     */
    public static void excelToSqlite(final String fromFilePath){
        new ExcelToSQLite
                .Builder(BookApplication.mInstance)
                .setDataBase(BookApplication.mInstance.getDatabasePath("BookDatabase.db").getPath())
//                .setAssetFileName("book.xls")
//                .setFilePath(MainActivity.OUTPATH+"/book.xls")
                .setFilePath(fromFilePath)
                .setDecryptKey("1234567")
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .start(new ExcelToSQLite.ImportListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onCompleted(String result) {
                        //                        showDbMsg(result);
                        Toast.show("导入成功："+result);
                        SharePreferentUtils.putBoolean(SharePref.IMPORT_DATA,true);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.show("请从"+fromFilePath+"目录下导入文件");
                        Log.d("=mmc=","-----error---"+e.getMessage());
                        SharePreferentUtils.putBoolean(SharePref.IMPORT_DATA,false);
                    }
                });
    }

    /**
     * sql -> xls
     * 数据库转excel,并在外部存储中可以导出查看
     * 手机也能直接查看
     */
    public static void sqliteToExcel(){
        new SQLiteToExcel.Builder(BookApplication.mInstance)
                .setDataBase(BookApplication.mInstance.getDatabasePath("BookDatabase.db").getPath())
                .setOutputPath(MainActivity.OUTPATH)
                .setOutputFileName("book_backup.xls")
                .setTables("bookType","book")
                //                .setEncryptKey("1234567")
                //                .setProtectKey("9876543")
                .start(new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onCompleted(String filePath) {
                        Toast.show("保存在："+filePath);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }
    public static void showDbMsg(String dbName) {
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
