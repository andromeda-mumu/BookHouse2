package com.example.mmc.bookhouse.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.adapter.HomePagerAdapter;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.ui.fragment.AddBookFragment;
import com.example.mmc.bookhouse.ui.fragment.BookFragment;
import com.example.mmc.bookhouse.ui.fragment.SearchFragment;
import com.example.mmc.bookhouse.utils.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 系统自动生成
 */

public class FloatingActivityAuto extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_auto);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //        fab.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                        .setAction("Action", null).show();
        //            }
        //        });


        initToolbar();
        initView();
    }

    private void initToolbar() {
        mToolbarLayout.setTitle("toolbarlayout"); //这才是真正名字的地方

        mToolbar.setNavigationIcon(R.drawable.drawer);
        //        mToolbar.setLogo(R.drawable.orange_icon);
        mToolbar.setTitle("BookHouse");
        mToolbar.setLogo(android.R.drawable.ic_menu_add);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitle("毛毛虫");
        mToolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                switch(menuItemId){
                    case R.id.action_search:
                        com.example.mmc.bookhouse.utils.Toast.show("搜索");
                        break;
                    case R.id.action_notification:
                        Toast.show("通知");
                        break;
                    case R.id.action_item1:
                        Toast.show("item1");
                        break;
                    case R.id.action_item2:
                        Toast.show("item2");
                        break;
                }
                return true;
            }
        });
    }


    //这会自动加到toolbar上
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.base_toolbar_menu,menu);
//        return true;
//    }


    private void initView() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new BookFragment());
        fragmentList.add(new SearchFragment());
        fragmentList.add(new AddBookFragment());
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewpager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),fragmentList));

    }
}
