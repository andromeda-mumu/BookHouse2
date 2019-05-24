package com.example.mmc.bookhouse.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.adapter.RecyclerViewDeleteAdapter;
import com.example.mmc.bookhouse.utils.ScreenUtils;

/**
 * Created by wangjiao on 2019/5/24.
 * 功能描述：
 */

public class CustomRecyclerView extends RecyclerView {
    //itemView的第一个childView
    private TextView tvTitle;
    //删除按钮
    private TextView tvDelete;
    //删除按钮的宽度
    private int tvDeleteWidth;
    //显示删除按钮的标识
    public boolean isDeleteShow = false;
    public int screenWidth;
    //手指按下的位置
    private int downX;
    private int downY;
    // 当前处理的item的LayoutParams
    private LinearLayout.LayoutParams mLayoutParams;
    private int mMargin;
    private int mTouchSlop;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取屏幕宽度
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        screenWidth = (int) (dm.widthPixels*0.8);
        mMargin = (ScreenUtils.ScreenWidth-screenWidth)/2;
        //滑动到最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        Log.d("=mmc=","----mTouchSlop----"+mTouchSlop);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                doActionDown(e);
                break;
            case MotionEvent.ACTION_MOVE:
                return doActionMove(e);
            case MotionEvent.ACTION_UP:
                doActionUp();
                break;
        }
        return super.onTouchEvent(e);
    }
    private void doActionDown(MotionEvent e) {
        if(isDeleteShow) {
            backToNormal();
        }
        //获取按下时的坐标x、y
        downX = (int)e.getX();
        downY = (int)e.getY();

        //1、获取当前点的item,Recyclerview没有ListView的pointToPosition(int downX,int downY) - getFirstVisiblePosition;
        //int firstPos = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();//首个可见itemView
        //int lastPos = ((LinearLayoutManager)getLayoutManager()).findLastVisibleItemPosition();//最后一个可见itemView
        //通过position得到当前点的itemView
        View itemView = findChildViewUnder(e.getX(),e.getY());
        RecyclerViewDeleteAdapter.MyViewHolder viewHolder = (RecyclerViewDeleteAdapter.MyViewHolder) getChildViewHolder(itemView);
        itemView = viewHolder.itemView;
        tvDelete = itemView.findViewById(R.id.tv_delete);
        tvTitle = itemView.findViewById(R.id.tv_title);
        //2、获取删除按钮的宽度
        tvDeleteWidth = tvDelete.getLayoutParams().width;
        mLayoutParams = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
        mLayoutParams.width = screenWidth;
        mLayoutParams.leftMargin = -mMargin;
        tvTitle.setLayoutParams(mLayoutParams);
    }
    private boolean doActionMove(MotionEvent e) {
        //计算偏移量
        int scrollX = (int)(e.getX() - downX);
        int scrollY = (int) (e.getY() - downY);
        //比较上下滑动与左右滑动的偏移量
        if(Math.abs(scrollX) - Math.abs(scrollY) > mTouchSlop){
            //左右滑动
            if(e.getX() < downX) {
                //向左滑动
                if (-(scrollX / 2)>= tvDeleteWidth ) {
                    scrollX = -tvDeleteWidth;
                }
                //重新设置第一个childView的左边距
                mLayoutParams.leftMargin = scrollX-mMargin;
                tvTitle.setLayoutParams(mLayoutParams);
            }
            return true;
        }else if(Math.abs(scrollY)-Math.abs(scrollX)>mTouchSlop){
            //上下滑动交由父view处理
            return super.onTouchEvent(e);
        }else{
            return true;
        }


    }
    private void doActionUp() {
        // 偏移量大于按钮尺寸的一半，则显示
        if(-mLayoutParams.leftMargin >= tvDeleteWidth / 2+mMargin) {
            mLayoutParams.leftMargin = -(tvDeleteWidth+mMargin)+ScreenUtils.dip2px(getContext(),10);
            //给显示按钮的标识赋值
            isDeleteShow = true;
        }else {
            // 否则恢复默认
            backToNormal();
        }
        tvTitle.setLayoutParams(mLayoutParams);
    }
    public void backToNormal(){
        //重新设置第一个childView的左边距
        mLayoutParams.leftMargin = -mMargin+ScreenUtils.dip2px(getContext(),21);
        tvTitle.setLayoutParams(mLayoutParams);
        isDeleteShow = false;
    }


}