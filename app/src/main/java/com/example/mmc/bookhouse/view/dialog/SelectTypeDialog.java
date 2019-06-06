package com.example.mmc.bookhouse.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.adapter.SwipeAdapter;
import com.example.mmc.bookhouse.model.BookType;
import com.example.mmc.bookhouse.model.BookType_Table;
import com.example.mmc.bookhouse.model.EventType;
import com.example.mmc.bookhouse.utils.EventBusUtils;
import com.example.mmc.bookhouse.utils.ScreenUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjiao on 2019/5/24.
 * 功能描述：
 */

public class SelectTypeDialog extends Dialog implements OnItemClickListener {
    @BindView(R.id.rlv)
    SwipeRecyclerView mRecyclerView;
    @BindView(R.id.tv_add_type)
    TextView mTvAddType;
    private List<BookType> mDatas = new ArrayList();
    private Activity mContext;
    private OnSelectListener mListener;
    private SwipeAdapter mAdapter;

    public SelectTypeDialog(@NonNull Activity context,OnSelectListener listener) {
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
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
        mRecyclerView.setOnItemClickListener(this);

        mAdapter = new SwipeAdapter(mContext,mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.tv_add_type)
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tv_add_type:
                AddTypeDialog dialog = new AddTypeDialog(mContext,mListener);
                dialog.show();
                dismiss();
                break;
            default :
                break;

        }
    }

    @Override
    public void onItemClick(View view, int adapterPosition) {
        if(mListener!=null){
            mListener.onSelect(mDatas.get(adapterPosition).type);
        }
    }

    public interface OnSelectListener{
        void onSelect(String type);
//        void onDelete(String type);
    }
    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext).setBackgroundColor(mContext.getResources().getColor(R.color.red))
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                SQLite.delete()
                        .from(BookType.class)
                        .where(BookType_Table.type.is(mDatas.get(position).type))
                        .execute();
                EventBusUtils.post(EventType.UPDATE_BOOK);
                mDatas.remove(position);
                mAdapter.notifyDataSetChanged();

            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };
}
