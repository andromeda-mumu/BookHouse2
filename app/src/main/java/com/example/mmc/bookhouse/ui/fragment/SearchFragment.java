package com.example.mmc.bookhouse.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.adapter.SearchAdapter;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class SearchFragment extends BaseFragment {

    @BindView(R.id.searchview)
    SearchView mSearchview;
    Unbinder unbinder;
    @BindView(R.id.listview)
    ListView mListview;
    private List<Book> mDatas = new ArrayList();
    private SearchAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initView() {
        mSearchview.setIconified(false);//默认打开
        mSearchview.setIconifiedByDefault(false);//默认搜索图标在搜索框外
    }

    @Override
    protected void initData() {
        mDatas = SQLite.select()
                .from(Book.class)
                .queryList();

        mAdapter = new SearchAdapter(activity(),mDatas);
        mListview.setAdapter(mAdapter);
        mListview.setTextFilterEnabled(true);//输入框内容变化时，过滤内容
    }

    @Override
    protected void initListener() {
        mSearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
