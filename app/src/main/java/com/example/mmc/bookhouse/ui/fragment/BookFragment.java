package com.example.mmc.bookhouse.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.adapter.BookItemDelegate;
import com.example.mmc.bookhouse.adapter.TypeItemDelagate;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.model.Book_Table;
import com.example.mmc.bookhouse.model.Event;
import com.example.mmc.bookhouse.model.EventType;
import com.example.mmc.bookhouse.model.ItemDelagateBean;
import com.example.mmc.bookhouse.model.ItemDelagateType;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class BookFragment extends BaseFragment {
    RecyclerView mRlv;
    private List<ItemDelagateBean> mDatas = new ArrayList();
    private String[] types = {"技术类","历史类","人文类","科幻类","其他"};
    private MultiItemTypeAdapter mAdapter;

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
        EventBus.getDefault().register(this);


        mAdapter = new MultiItemTypeAdapter(activity(),mDatas);
        mAdapter.addItemViewDelegate(new TypeItemDelagate());
        mAdapter.addItemViewDelegate(new BookItemDelegate(activity()));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity(),LinearLayoutManager.VERTICAL,false);
        mRlv.setLayoutManager(manager);
        mRlv.setAdapter(mAdapter);

        loadDb();


    }

    private void loadDb() {
        for (String type :types){
            mDatas.add(new ItemDelagateBean(ItemDelagateType.item_type,type));
            mDatas.add(new ItemDelagateBean(ItemDelagateType.item_book,getTypeBook(type)));
        }

//        mDatas.add(new ItemDelagateBean(ItemDelagateType.item_type,types[1]));
//        mDatas.add(new ItemDelagateBean(ItemDelagateType.item_book,getTypeBook(types[1])));
//        mDatas.add(new ItemDelagateBean(ItemDelagateType.item_type,types[2]));
//        mDatas.add(new ItemDelagateBean(ItemDelagateType.item_book,getTypeBook(types[2])));
//        mDatas.add(new ItemDelagateBean(ItemDelagateType.item_type,types[3]));
//        mDatas.add(new ItemDelagateBean(ItemDelagateType.item_book,getTypeBook(types[3])));
//        mDatas.add(new ItemDelagateBean(ItemDelagateType.item_type,types[4]));
//        mDatas.add(new ItemDelagateBean(ItemDelagateType.item_book,getTypeBook(types[4])));
    }

    public List<Book> getTypeBook(String type){
        if(types[4].equals(type)){
            List<Book> books = SQLite.select()
                    .from(Book.class)
                    .where(Book_Table.type.isNot(types[0]),Book_Table.type.isNot(types[1]),Book_Table.type.isNot(types[2]),Book_Table.type.isNot(types[3]))
                    .queryList();
            return books;
        }else{
            List<Book> books = SQLite.select()
                    .from(Book.class)
                    .where(Book_Table.type.is(type))
                    .queryList();
            return books;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event){
        switch(event.eventType){
            case EventType.ADD_BOOK:
                 mDatas.clear();
                 loadDb();
                 mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
