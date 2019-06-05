package com.example.mmc.bookhouse.adapter;

import android.view.View;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.EventType;
import com.example.mmc.bookhouse.model.ItemDelagateBean;
import com.example.mmc.bookhouse.model.ItemDelagateType;
import com.example.mmc.bookhouse.utils.EventBusUtils;
import com.example.mmc.bookhouse.utils.Tools;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by wangjiao on 2019/5/23.
 * 功能描述：$end$
 */

public class TypeItemDelagate implements ItemViewDelegate<ItemDelagateBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_type;
    }

    @Override
    public boolean isForViewType(ItemDelagateBean item, int position) {
        return ItemDelagateType.item_type.equals(item.itemType);
    }

    @Override
    public void convert(ViewHolder holder, ItemDelagateBean itemDelagateBean, int position) {
        final String type = (String) itemDelagateBean.Object;
        if(Tools.isEmpty(type))return;
        holder.setText(R.id.tv_type,type);
        String str = type.replace("类","");
        holder.setText(R.id.tv_more,"更多"+str);
        holder.getView(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBusUtils.post(EventType.MORE_TYPE_BOOK,type);
            }
        });
    }

}
