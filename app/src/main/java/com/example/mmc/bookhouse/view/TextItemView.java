package com.example.mmc.bookhouse.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class TextItemView extends LinearLayout {

    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.et)
    EditText mEt;
    private String mText;
    private String mHint;

    public TextItemView(Context context) {
        this(context, null);
    }

    public TextItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextItemView);
        mText = ta.getString(R.styleable.TextItemView_text);
        mHint = ta.getString(R.styleable.TextItemView_hint);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_text_item, this);
        ButterKnife.bind(this);
        if (Tools.notEmpty(mText)) {
            mTv.setText(mText);
        }
        if(Tools.notEmpty(mHint)){
            mEt.setHint(mHint);
        }
    }

    public String getContent(){
      return  mEt.getText().toString().trim();
    }
    public void reset(){
        mEt.setText("");
    }
}
