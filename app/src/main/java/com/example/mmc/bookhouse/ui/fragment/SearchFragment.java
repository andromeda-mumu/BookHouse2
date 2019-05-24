package com.example.mmc.bookhouse.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.adapter.SearchAdapter;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.model.Book_Table;
import com.example.mmc.bookhouse.ui.activity.BookDetailActivity;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.utils.Tools;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.btn_all)
    Button mBtnAll;
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
        //        mSearchview.setIconified(false);//默认打开
        mSearchview.setIconifiedByDefault(false);//默认搜索图标在搜索框外
        mBtnAll.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        mDatas = SQLite.select()
                .from(Book.class)
                .queryList();

        mAdapter = new SearchAdapter(activity(), mDatas);
        mListview.setAdapter(mAdapter);
        mListview.setTextFilterEnabled(true);//输入框内容变化时，过滤内容
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = (Book) mAdapter.getItem(i);
                if (Tools.notNull(book)) {
                    BookDetailActivity.start(getActivity(),book);
                }
            }
        });
    }

    private void loadAllData(){
        updateUI(queryDb(null));
    }
    /**
     * 加载某一类的数据
     */
    public void loadTypeData(String type) {
        showBtnAll(true,"查找" + type + "书籍");
        updateUI(queryDb(type));
    }
    private void updateUI(List<Book> books){
        mDatas.clear();
        mDatas.addAll(books);
        mAdapter.notifyDataSetChanged();
    }
    private List<Book> queryDb(String type){
        if(Tools.isEmpty(type)){
            List<Book> allBooks = SQLite.select()
                    .from(Book.class)
                    .queryList();
            return allBooks;
        }else{
            List<Book> typeBooks = SQLite.select()
                    .from(Book.class)
                    .where(Book_Table.type.is(type))
                    .queryList();
            return typeBooks;
        }
    }
    private void showBtnAll(boolean visible,String hint){
        if(visible){
            mBtnAll.setVisibility(View.VISIBLE);
            mSearchview.setQueryHint(hint);
        }else{
            mBtnAll.setVisibility(View.GONE);
            mSearchview.setQueryHint(hint);
        }
    }

    @OnClick(R.id.btn_all)
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_all:
                loadAllData();
                showBtnAll(false,"请输入要查找的书籍名");
                break;
            default :
                break;

        }
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
