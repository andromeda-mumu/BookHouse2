package com.example.mmc.bookhouse.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class BookFragment extends BaseFragment {
    RecyclerView mRlv;
    private List<Book> mDatas = new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    protected void initView() {
        mRlv = getView().findViewById(R.id.rlv);
    }

    @Override
    protected void initData() {
        List<Book> books = SQLite.select()
                .from(Book.class)
                .queryList();

        RecyclerView.LayoutManager manager = new GridLayoutManager(activity(),4);
        mRlv.setLayoutManager(manager);
        mRlv.setAdapter(new CommonAdapter<Book>(activity(),R.layout.item_book,books){

            @Override
            protected void convert(ViewHolder holder, Book book, int position) {
                holder.setText(R.id.tv_name,book.name);
                holder.setText(R.id.tv_author,book.author);
            }
        });
    }
}
