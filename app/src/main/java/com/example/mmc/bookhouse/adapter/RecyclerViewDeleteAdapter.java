package com.example.mmc.bookhouse.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.BookType;
import com.example.mmc.bookhouse.view.CustomRecyclerView;
import com.example.mmc.bookhouse.view.dialog.SelectTypeDialog;

import java.util.List;

/**
 * Created by wangjiao on 2019/5/24.
 * 功能描述：
 */

public class RecyclerViewDeleteAdapter extends RecyclerView.Adapter<RecyclerViewDeleteAdapter.MyViewHolder>{
    private Context mContext;
    private List<BookType> dataList;
    private CustomRecyclerView recyclerView;
    private SelectTypeDialog.OnSelectListener mSelectListener;
    public RecyclerViewDeleteAdapter(Context mContext, CustomRecyclerView recyclerView, List<BookType> dataList,SelectTypeDialog.OnSelectListener listener){
        this.mContext = mContext;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        this.mSelectListener = listener;
    }

    @Override
    public RecyclerViewDeleteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewDeleteAdapter.MyViewHolder holder = new RecyclerViewDeleteAdapter.MyViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_delete_recycler_view,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewDeleteAdapter.MyViewHolder holder, final int position) {
        final  int itemPosition = position;
        holder.tvTitle.setText(dataList.get(position).type);
        //给删除按钮设置点击事件的监听器
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSelectListener!=null){
                    mSelectListener.onSelect(dataList.get(position).type);
                }
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据库删除
                dataList.get(itemPosition).delete();
                //移除数据
                dataList.remove(itemPosition);
                //回归默认状态
                recyclerView.backToNormal();
                //更新数据
                notifyItemRemoved(itemPosition);
                notifyItemRangeRemoved(itemPosition,getItemCount() - itemPosition);


                //notifyDataSetChanged();//更新数据
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDelete;
        public MyViewHolder(View itemView){
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
        }
    }
}

