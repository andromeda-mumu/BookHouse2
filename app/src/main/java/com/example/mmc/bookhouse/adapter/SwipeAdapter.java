package com.example.mmc.bookhouse.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.BookType;

import java.util.List;

/**
 * Created by wangjiao on 2019/6/6.
 * 功能描述：
 */

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.ViewHolder>  {

    private List<BookType> mDataList;
    private Context mContext;

    public SwipeAdapter(Context context,List<BookType> dataList) {
        this.mDataList = dataList;
        this.mContext = context;
    }

//    public void notifyDataSetChanged(List<String> dataList) {
//        this.mDataList = dataList;
//        super.notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(mDataList.get(position).type);
    }

    @Override
    public int getItemCount() {
        return  mDataList == null ? 0 : mDataList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }
    }
}

