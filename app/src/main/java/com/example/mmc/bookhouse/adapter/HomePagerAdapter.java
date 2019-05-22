package com.example.mmc.bookhouse.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mmc.bookhouse.ui.base.BaseFragment;
import com.example.mmc.bookhouse.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mDatas = new ArrayList();
    public HomePagerAdapter(FragmentManager fm,List<BaseFragment> list) {
        super(fm);
        this.mDatas = list;
    }

    @Override
    public Fragment getItem(int position) {
        if(Tools.notNull(mDatas)){
            return mDatas.get(position);
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        if(Tools.notNull(mDatas)){
            return mDatas.size();
        }else{
            return 0;
        }
    }
}
