package com.example.mmc.bookhouse.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.model.ItemDelagateBean;
import com.example.mmc.bookhouse.model.ItemDelagateType;
import com.example.mmc.bookhouse.ui.activity.BookDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wangjiao on 2019/5/23.
 * 功能描述：$end$
 */

public class BookItemDelegate implements ItemViewDelegate<ItemDelagateBean> {
    private Context mContext;
    private RecyclerView mRlv;

    public BookItemDelegate(Context context){
        Log.d("=mmc=","----book create----");
        mContext =context;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.layout_book;
    }

    @Override
    public boolean isForViewType(ItemDelagateBean item, int position) {
        return ItemDelagateType.item_book.equals(item.itemType);
    }

    @Override
    public void convert(ViewHolder holder, ItemDelagateBean itemDelagateBean, int position) {
        final List<Book> books = (List<Book>) itemDelagateBean.Object;
        mRlv = holder.getView(R.id.rlv);
        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        mRlv.setLayoutManager(manager);
        mRlv.setAdapter(new CommonAdapter<Book>(mContext,R.layout.item_book,books){
            @Override
            protected void convert(ViewHolder holder, final Book book, final int position) {
                holder.setText(R.id.tv_name,book.name);
                holder.setText(R.id.tv_author,book.author);
                holder.setText(R.id.tv_location,book.location);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        //删除
//                        SQLite.delete(Book.class)
//                                .where(Book_Table.name.eq(book.name))
//                                 .execute();
//                        books.remove(position);
//                        notifyItemRemoved(position);
                        BookDetailActivity.start(mContext,book);
                    }
                });
            }
        });
    }
}
