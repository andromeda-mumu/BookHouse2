package com.example.mmc.bookhouse.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.ui.base.BaseActivity;
import com.example.mmc.bookhouse.utils.Toast;

import butterknife.BindView;

/**
 * Created by wangjiao on 2019/5/23.
 * 功能描述：图书详情
 */

public class BookDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cdl_content)
    LinearLayout mCdlContent;

    @Override
    protected int getResId() {
        return R.layout.activity_detail;
    }

    public static void start(Context context) {
        Intent intnet = new Intent(context, BookDetailActivity.class);
        context.startActivity(intnet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mToolbar.setNavigationIcon(R.drawable.drawer);
//        mToolbar.setLogo(R.drawable.orange_icon);
        mToolbar.setTitle("BookHouse");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitle("毛毛虫");
        mToolbar.setSubtitleTextColor(Color.WHITE);

        mToolbar.inflateMenu(R.menu.base_toolbar_menu);
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
}
