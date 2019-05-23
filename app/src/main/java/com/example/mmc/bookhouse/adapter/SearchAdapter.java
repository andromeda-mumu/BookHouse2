package com.example.mmc.bookhouse.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjiao on 2019/5/23.
 * 功能描述：
 */

public class SearchAdapter extends BaseAdapter implements Filterable{
    private List<Book> mBooks,mOriginData;
    private Context mContext;
    private MyFileter mMyFileter;
    public SearchAdapter(Context context,List<Book> books){
        this.mBooks =books;
        this.mOriginData = books;
        mContext = context;
    }

    @Override
    public int getCount() {
        if(Tools.isEmpty(mBooks))
            return 0;
        else
            return mBooks.size();
    }

    @Override
    public Object getItem(int i) {
        return mBooks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SearchHolder holder = null;
        if(view==null){
            view =View.inflate(mContext,R.layout.item_search,null);
            holder = new SearchHolder();
            holder.tvName = view.findViewById(R.id.tv_name);
            holder.tvType = view.findViewById(R.id.tv_type);
            view.setTag(holder);
        }else{
            holder = (SearchHolder) view.getTag();
        }

        holder.tvName.setText(mBooks.get(i).name);
        holder.tvType.setText(mBooks.get(i).type);
        return view;
    }

    public List<Book> getData(){
        return mBooks;
    }

    @Override
    public Filter getFilter() {
        if(null==mMyFileter){
            mMyFileter = new MyFileter();
        }
        return mMyFileter;
    }

    class SearchHolder {
        TextView tvName;
        TextView tvType;
    }

    class MyFileter extends Filter{
        // 该方法在子线程中执行
        // 自定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<Book> newValues = new ArrayList<>();
            String filterString = charSequence.toString().trim().toLowerCase();
            //如果搜索框内容为null，就恢复原始数据
            if(Tools.isEmpty(filterString)){
                newValues = mOriginData;
            }else{
                //过滤出新数据
                for (Book book:mOriginData){
                    if(-1 !=book.name.toLowerCase().indexOf(filterString)){
                        newValues.add(book);
                    }
                }
            }
            results.values = newValues;
            results.count = newValues.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mBooks = (List<Book>) filterResults.values;
                if(filterResults.count>0){
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
        }
    }


}
