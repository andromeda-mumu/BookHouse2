package com.example.mmc.bookhouse.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.adapter.RecyclerViewDeleteAdapter;
import com.example.mmc.bookhouse.model.BookType;
import com.example.mmc.bookhouse.utils.ScreenUtils;
import com.example.mmc.bookhouse.view.CustomRecyclerView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjiao on 2019/5/24.
 * 功能描述：
 */

public class SelectTypeDialog extends Dialog {
    @BindView(R.id.rlv)
    CustomRecyclerView mRlv;
    @BindView(R.id.tv_add_type)
    TextView mTvAddType;
    private List<BookType> mDatas = new ArrayList();
    private Context mContext;
//    private CommonAdapter mAdapter;
    private OnSelectListener mListener;
    private RecyclerViewDeleteAdapter mAdapter;

    public SelectTypeDialog(@NonNull Context context,OnSelectListener listener) {
        super(context);
        this.mContext = context;
        this.mListener =listener;
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        setContentView(R.layout.dialog_type);
        ButterKnife.bind(this);

        getWindow().getAttributes().width = (int) (ScreenUtils.ScreenWidth*0.8);

        initView();
    }

    private void initView() {
        mDatas = SQLite.select()
                .from(BookType.class)
                .queryList();

        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mRlv.setLayoutManager(manager);
//        mAdapter = new CommonAdapter<BookType>(mContext, R.layout.item_search,mDatas){
//
//            @Override
//            protected void convert(ViewHolder holder, final BookType bookType, int position) {
//                holder.setText(R.id.tv_name,bookType.type);
//                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(mListener!=null){
//                            mListener.onSelect(bookType.type);
//                            dismiss();
//                        }
//                    }
//                });
//            }
//        };

        mAdapter = new RecyclerViewDeleteAdapter(mContext, mRlv, mDatas, mListener);
        mRlv.setAdapter(mAdapter);
    }

    @OnClick(R.id.tv_add_type)
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tv_add_type:
                AddTypeDialog dialog = new AddTypeDialog(mContext);
                dialog.show();
                dismiss();
                break;
            default :
                break;

        }
    }

    public interface OnSelectListener{
        void onSelect(String type);
//        void onDelete(String type);
    }
}
