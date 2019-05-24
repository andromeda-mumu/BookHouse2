package com.example.mmc.bookhouse.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.Book;
import com.example.mmc.bookhouse.model.Event;
import com.example.mmc.bookhouse.model.EventType;
import com.example.mmc.bookhouse.ui.activity.BookDetailActivity;
import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.utils.EventBusUtils;
import com.example.mmc.bookhouse.utils.Toast;
import com.example.mmc.bookhouse.utils.Tools;
import com.example.mmc.bookhouse.view.TextItemView;
import com.example.mmc.bookhouse.view.dialog.SelectTypeDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class AddBookFragment extends BaseFragment implements SelectTypeDialog.OnSelectListener {
    public static final int ADD_BOOK = 1;
    public static final int EDIT_BOOK = 2;
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
//    @BindView(R.id.tiv_book_type)
//    TextItemView mTivBookType;
    @BindView(R.id.tiv_book_tag)
    TextItemView mTivBookTag;
    @BindView(R.id.tiv_book_date)
    TextItemView mTivBookDate;
    @BindView(R.id.tiv_book_readtimes)
    TextItemView mTivBookReadtimes;
    @BindView(R.id.btn_upload)
    Button mBtnUpload;
    Unbinder unbinder;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.iv_type)
    ImageView mIvType;
    private int mType = ADD_BOOK;
    private Book mOldBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_add;
    }


    @OnClick({R.id.btn_upload,R.id.iv_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                if (mType == ADD_BOOK) {
                    doUpload();
                } else {
                    doEdit();
                }
                break;
            case R.id.iv_type:
                SelectTypeDialog dialog = new SelectTypeDialog(activity(),this);
                dialog.show();
                break;
            default:
                break;

        }
    }

    private Book preCheck() {
        String name = mTivBookname.getContent();
        String author = mTivBookauthor.getContent();
        String location = mTivBookLocation.getContent();
        String desc = mTivBookDesc.getContent();
//        String type = mTivBookType.getContent();
        String tag = mTivBookTag.getContent();
        String date = mTivBookDate.getContent();
        String times = mTivBookReadtimes.getContent();
        String type = mTvType.getText().toString();
        Log.d("=mmc=","----type----"+type);
        if (!Tools.notEmpty(name)) {
            Toast.show("书名不能为空");
            return null;
        }
        Book book;
        if (mType == ADD_BOOK) {
            book = new Book();
        } else {
            book = mOldBook;
        }
        book.name = name;
        book.author = author;
        book.location = location;
        book.desc = desc;
        book.type = type;
        book.tag = tag;
        book.date = date;
        book.times = times;
        return book;
    }


    private void doUpload() {
        Book book = preCheck();
        if (book == null)
            return;
        book.save();
        EventBus.getDefault().post(new Event(EventType.UPDATE_BOOK));
        resetData();
        Toast.show("添加成功");
    }

    private void resetData() {
        mBtnUpload.setText("确认添加");
        mTivBookname.reset();
        mTivBookauthor.reset();
        mTivBookLocation.reset();
        mTivBookDesc.reset();
//        mTivBookType.reset();
        mTivBookTag.reset();
        mTivBookDate.reset();
        mTivBookReadtimes.reset();
        mTvType.setText("");
    }

    public void editBook(Book book) {
        mBtnUpload.setText("确认修改");
        mOldBook = book;
        mType = EDIT_BOOK;
        mTivBookname.setContent(book.name);
        mTivBookauthor.setContent(book.author);
        mTivBookLocation.setContent(book.location);
        mTivBookDesc.setContent(book.desc);
//        mTivBookType.setContent(book.type);
        mTivBookTag.setContent(book.tag);
        mTivBookDate.setContent(book.date);
        mTivBookReadtimes.setContent(book.times);
        mTvType.setText(book.type);
    }

    private void doEdit() {
        Book book = preCheck();
        if (book == null)
            return;
        book.update();
        Toast.show("已修改成功");
        resetData();
        BookDetailActivity.start(activity(), book);
        EventBusUtils.post(EventType.UPDATE_BOOK);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSelect(String type) {
        mTvType.setText(type+"类");
    }
}
