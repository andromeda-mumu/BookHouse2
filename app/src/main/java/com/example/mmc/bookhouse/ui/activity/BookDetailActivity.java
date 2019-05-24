package com.example.mmc.bookhouse.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.model.EventType;
import com.example.mmc.bookhouse.ui.base.BaseActivity;
import com.example.mmc.bookhouse.utils.EventBusUtils;
import com.example.mmc.bookhouse.utils.TextviewUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class BookDetailActivity extends BaseActivity {
    public static final String EXTRA_BOOK = "book";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_tag)
    TextView mTvTag;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_times)
    TextView mTvTimes;
    @BindView(R.id.tv_times_log)
    TextView mTvTimesLog;
    @BindView(R.id.tv_note)
    TextView mTvNote;
    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void start(Context context, Book book) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        context.startActivity(intent);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initData() {
        mBook = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);
        mToolbarLayout.setTitle(mBook.name);
        mTvType.setText(mBook.type);
        TextviewUtils.setRightDrawable(this,mTvType,R.drawable.type_icon_2);
        mTvAuthor.setText(mBook.author);
        TextviewUtils.setRightDrawable(this,mTvAuthor,R.drawable.author_icon_2);
        mTvLocation.setText(mBook.location);
        TextviewUtils.setRightDrawable(this,mTvLocation,R.drawable.location_icon);

        mTvTag.setText("标签："+mBook.tag);
        TextviewUtils.setLeftDrawable(this,mTvTag,R.drawable.tag_cion);

        mTvDate.setText("购书日期："+mBook.date);
        TextviewUtils.setLeftDrawable(this,mTvDate,R.drawable.date_icon);

    }

    @OnClick(R.id.fab)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                EventBusUtils.post(EventType.EDIT_BOOK, mBook);
                finish();
                break;
            default:
                break;

        }
    }
}
