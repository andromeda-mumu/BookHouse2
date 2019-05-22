package com.example.mmc.bookhouse.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.model.Book_Table;
import com.example.mmc.bookhouse.model.ItemDelagateBean;
import com.example.mmc.bookhouse.model.ItemDelagateType;
import com.example.mmc.bookhouse.utils.Tools;
import com.raizlabs.android.dbflow.sql.language.SQLite;
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
    public BookItemDelegate(Context context){
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
        if(Tools.isEmpty(books))return;
        RecyclerView rlv = holder.getView(R.id.rlv);
        GridLayoutManager manager = new GridLayoutManager(mContext,4);
        rlv.setLayoutManager(manager);
        rlv.setAdapter(new CommonAdapter<Book>(mContext,R.layout.item_book,books){
            @Override
            protected void convert(ViewHolder holder, final Book book, final int position) {
                holder.setText(R.id.tv_name,book.name);
                holder.setText(R.id.tv_author,book.author);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //删除
                        SQLite.delete(Book.class)
                                .where(Book_Table.name.eq(book.name))
                                 .execute();
                        books.remove(position);
                        notifyItemRemoved(position);
                    }
                });
            }
        });



    }
}
