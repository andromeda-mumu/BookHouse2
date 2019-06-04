package com.example.mmc.bookhouse;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mmc.bookhouse.adapter.HomePagerAdapter;
import com.example.mmc.bookhouse.db.DbBackups;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.model.BookType;
import com.example.mmc.bookhouse.model.Event;
import com.example.mmc.bookhouse.model.EventType;
import com.example.mmc.bookhouse.model.SharePref;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.ui.fragment.AddBookFragment;
import com.example.mmc.bookhouse.ui.fragment.BookFragment;
import com.example.mmc.bookhouse.ui.fragment.ImpressionFragment;
import com.example.mmc.bookhouse.ui.fragment.SearchFragment;
import com.example.mmc.bookhouse.utils.EventBusUtils;
import com.example.mmc.bookhouse.utils.ScreenUtils;
import com.example.mmc.bookhouse.utils.SharePreferentUtils;
import com.example.mmc.bookhouse.utils.Toast;
import com.example.mmc.bookhouse.view.ImageTextView;
import com.liyu.sqlitetoexcel.ExcelToSQLite;
import com.liyu.sqlitetoexcel.SQLiteToExcel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {
    private static final int SD_PERM =1001 ;
    @BindView(R.id.bottom)
    LinearLayout mBottom;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.itv_book)
    ImageTextView mItvBook;
    @BindView(R.id.itv_search)
    ImageTextView mItvSearch;
    @BindView(R.id.itv_add)
    ImageTextView mItvAdd;
    @BindView(R.id.itv_impression)
    ImageTextView mItvImpression;
    private HomePagerAdapter mAdapter;
    private SearchFragment mSearchFragment;
    private AddBookFragment mAddBookFragment;
    private String[] mTypes ={"技术","文学","传记","历史","管理","外文","绘画","其他"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBusUtils.register(this);
        ScreenUtils.init(this);

        initTypeTable();
        initView();
        initData();
        initListener();
        sqlToExcel();
        excelToSql();
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

    /**
     * 外部excel表格转换成sqlite数据
     */
    private void excelToSql() {
        new ExcelToSQLite
                .Builder(this)
                                .setDataBase(getDatabasePath("BookDatabase.db").getPath())
                .setAssetFileName("user.xls")
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
                        showDbMsg(result);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("=mmc=","-----error---"+e.getMessage());
                    }
                });
    }

    /**
     * 数据库转excel,并在外部存储中可以导出查看
     * 手机也能直接查看
     */
    private void sqlToExcel() {
        new SQLiteToExcel.Builder(this)
                .setDataBase(getDatabasePath("BookDatabase.db").getPath())
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

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},SD_PERM);
        }else{
            backUp(DbBackups.COMMAND_RESTORE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case SD_PERM:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                     backUp(DbBackups.COMMAND_RESTORE);
                }else{
                    Toast.show("请开启权限");
                    checkPermission();
                }
                break;
        }
    }

    /**
     * 备份数据库
     */
    private void backUp(String type) {
        new DbBackups(getApplicationContext()).execute(type);
    }

    /**
     * 数据库的图书类型表
     */
    private void initTypeTable() {
        boolean newApp = SharePreferentUtils.getBoolean(SharePref.NEW_APP,true);
        if(!newApp)return;

        for (int i=mTypes.length-1;i>=0;i--){
            BookType bookType = new BookType();
            bookType.type =mTypes[i];
            bookType.save();
        }
        SharePreferentUtils.putBoolean(SharePref.NEW_APP,false);
    }

    private void initListener() {
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpagerSelect(position);

                testBackup(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 测试备份和还原
     * @param position
     */
    private void testBackup(int position) {
        //先用外部存储的DB写入内存存储 ----->还原
        if(position==2){
            checkPermission();
        }
//        if(position==3) {
//            List<m_city> list = SQLite.select()
//                    .from(m_city.class)
//                    .queryList();
//            int cid = 0;
//            for (m_city city : list) {
//                Log.d("=mmc=", "--------" + city.cname);
//                cid = city.cid;
//            }
//            m_city ci = new m_city();
//            ci.cid = cid+1;
//            ci.cname = "慢慢学";
//
//            ci.save();
//            //添加了数据，重新备份到SD卡中-----》备份
//            backUp(DbBackups.COMMAND_RESTORE);
//        }
    }

    private void initView() {
        mItvBook.selectStatus(true);
    }

    private void initData() {

        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new BookFragment());
        mSearchFragment = new SearchFragment();
        fragmentList.add(mSearchFragment);
        mAddBookFragment = new AddBookFragment();
        fragmentList.add(mAddBookFragment);
        fragmentList.add(new ImpressionFragment());
        mAdapter = new HomePagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewpager.setAdapter(mAdapter);
        mViewpager.setOffscreenPageLimit(3);
    }

    @OnClick({R.id.itv_book, R.id.itv_search, R.id.itv_add, R.id.itv_impression})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.itv_book:
                setIconStatus(mItvBook);
                mViewpager.setCurrentItem(0);
                break;
            case R.id.itv_search:
                setIconStatus(mItvSearch);
                mViewpager.setCurrentItem(1);
                break;
            case R.id.itv_add:
                setIconStatus(mItvAdd);
                mViewpager.setCurrentItem(2);
                break;
            case R.id.itv_impression:
                setIconStatus(mItvImpression);
                mViewpager.setCurrentItem(3);
                break;
            default:
                break;

        }
    }

    private void viewpagerSelect(int pos){
        switch (pos){
            case 0:
                setIconStatus(mItvBook);
                break;
            case 1:
                setIconStatus(mItvSearch);
                break;
            case 2:
                setIconStatus(mItvAdd);
                break;
            case 3:
                setIconStatus(mItvImpression);
                break;
        }
    }
    private void setIconStatus(ImageTextView selectView){
        mItvSearch.selectStatus(false);
        mItvBook.selectStatus(false);
        mItvAdd.selectStatus(false);
        mItvImpression.selectStatus(false);
        selectView.selectStatus(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Event event){
        switch (event.eventType){
            case EventType.MORE_TYPE_BOOK:
                String type = (String) event.object;
                mSearchFragment.loadTypeData(type);
                mViewpager.setCurrentItem(1);
                break;
            case EventType.EDIT_BOOK:
                Book book = (Book) event.object;
                mAddBookFragment.editBook(book);
                mViewpager.setCurrentItem(2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }
}
