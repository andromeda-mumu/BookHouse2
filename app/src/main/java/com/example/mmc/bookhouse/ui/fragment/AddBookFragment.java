package com.example.mmc.bookhouse.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.utils.Toast;
import com.example.mmc.bookhouse.utils.Tools;
import com.example.mmc.bookhouse.view.TextItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class AddBookFragment extends BaseFragment {
    @BindView(R.id.iv_upload)
    ImageView mIvUpload;
    @BindView(R.id.tiv_bookname)
    TextItemView mTivBookname;
    @BindView(R.id.tiv_bookauthor)
    TextItemView mTivBookauthor;
    @BindView(R.id.tiv_book_location)
    TextItemView mTivBookLocation;
    @BindView(R.id.tiv_book_desc)
    TextItemView mTivBookDesc;
    @BindView(R.id.tiv_book_type)
    TextItemView mTivBookType;
    @BindView(R.id.tiv_book_tag)
    TextItemView mTivBookTag;
    @BindView(R.id.tiv_book_date)
    TextItemView mTivBookDate;
    @BindView(R.id.tiv_book_readtimes)
    TextItemView mTivBookReadtimes;
    @BindView(R.id.btn_upload)
    Button mBtnUpload;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_add;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_upload)
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_upload:
                doUpload();
                break;
            default :
                break;

        }
    }

    private void doUpload() {
       String name = mTivBookname.getContent();
       String author = mTivBookauthor.getContent();
       String location = mTivBookLocation.getContent();
       String desc = mTivBookDesc.getContent();
       String type =mTivBookType.getContent();
       String tag = mTivBookTag.getContent();
       String date = mTivBookDate.getContent();
       String times = mTivBookReadtimes.getContent();

       if(!Tools.notEmpty(name)){
           Toast.show("书名不能为空");
           return;
       }
        Book book = new Book();
       book.name = name;
       book.author =author;
       book.location = location;
       book.desc =desc;
       book.type = type;
       book.tag=tag;
       book.date =date;
       book.times = times;
       book.save();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
