package com.example.mmc.bookhouse.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class ImageTextView extends LinearLayout {
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.tv)
    TextView mTv;
    private int mResourceId;
    private String mText;

    public ImageTextView(Context context) {
        this(context, null);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        mResourceId = ta.getResourceId(R.styleable.ImageTextView_image, 0);
        mText = ta.getString(R.styleable.ImageTextView_name);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_imgtx, this);
        ButterKnife.bind(this);

        if (mResourceId != 0) {
            mIv.setImageResource(mResourceId);
        }
        if (!TextUtils.isEmpty(mText)) {
            mTv.setText(mText);
        }
    }
    public void selectStatus(boolean selected){
        mTv.setSelected(selected?true:false);
    }
}
