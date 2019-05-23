package com.example.mmc.bookhouse.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        return view;
    }

    protected abstract int getLayoutId();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }
    public Activity activity(){
        return getActivity();
    }

    protected void initView() {}
    protected void initData(){}
    protected void initListener(){}

    public void startActivity(Class clazz){
        Intent intent = new Intent(activity(),clazz);
        activity().startActivity(intent);
    }

}
