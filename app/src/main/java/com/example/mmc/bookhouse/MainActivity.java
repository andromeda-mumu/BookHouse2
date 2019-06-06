package com.example.mmc.bookhouse;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mmc.bookhouse.adapter.HomePagerAdapter;
import com.example.mmc.bookhouse.db.DbBackups;
import com.example.mmc.bookhouse.model.Book;
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
import com.example.mmc.bookhouse.utils.SqliteAndXlsUtils;
import com.example.mmc.bookhouse.utils.Toast;
import com.example.mmc.bookhouse.view.ImageTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
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
    public static String OUTPATH = Environment.getExternalStorageDirectory().getPath()+"/bookhouse";
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


        checkPermission();

        initView();
        initData();
//        initListener();
    }

    private void createDir() {
        File file = new File(OUTPATH);
        if(!file.exists()){
            file.mkdirs();
        }
    }


    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},SD_PERM);
        }else{
//            backUp(DbBackups.COMMAND_RESTORE);
            initTypeTable();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case SD_PERM:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                     backUp(DbBackups.COMMAND_RESTORE);
                    initTypeTable();
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
     * 第一次从外部录入数据到sql
     */
    private void initTypeTable() {
        createDir();
        boolean importDataSuc = SharePreferentUtils.getBoolean(SharePref.IMPORT_DATA,false);
        if(importDataSuc)return;

        //第一次进入app导入xls文档
        SqliteAndXlsUtils.excelToSqlite(ImpressionFragment.ORIGIN_FILE_PATH);

//        SharePreferentUtils.putBoolean(SharePref.NEW_APP,false);
    }

    private void initListener() {
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpagerSelect(position);


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
