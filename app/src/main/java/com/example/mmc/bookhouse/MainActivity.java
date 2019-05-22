package com.example.mmc.bookhouse;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mmc.bookhouse.adapter.HomePagerAdapter;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.ui.fragment.AddBookFragment;
import com.example.mmc.bookhouse.ui.fragment.BookFragment;
import com.example.mmc.bookhouse.ui.fragment.ImpressionFragment;
import com.example.mmc.bookhouse.ui.fragment.SearchFragment;
import com.example.mmc.bookhouse.view.ImageTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {
    @BindView(R.id.bottom)
    LinearLayout mBottom;
    @BindView(R.id.view)
    View mView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
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

    private void initView() {
        mItvBook.selectStatus(true);
    }

    private void initData() {

        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new BookFragment());
        fragmentList.add(new SearchFragment());
        fragmentList.add(new AddBookFragment());
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
}
