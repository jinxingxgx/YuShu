package com.xgx.yushu.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.permission.InvokeListener;
import com.melnykov.fab.FloatingActionButton;
import com.xgx.yushu.R;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.TabEntity;

import java.util.ArrayList;

import butterknife.Bind;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/25 0025 for YuShu
 */

public class TransferTabFragment extends BaseEventBusFragment implements TakePhoto.TakeResultListener, InvokeListener {
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    FloatingActionButton fab;
    @Bind(R.id.tl_1)
    CommonTabLayout tl1;
    @Bind(R.id.vp_2)
    ViewPager mViewPager;
    private String[] mTitles = {"最新动态", "闲置转让", "公共建议"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    public static TransferTabFragment newInstance() {
        Bundle args = new Bundle();
        TransferTabFragment fragment = new TransferTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (EventCenter.REFRESH_TRANSFER_FRAGMENT == eventCenter.getEventCode()) {
        }
    }

    @Override
    public boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_transfer_tab;
    }

    @Override
    public void initView() {
        titlebar.setTitleText("邻里发布");
        initToolbarNav(titlebar);
        tl_2();
    }

    @Override
    public void initPresenter() {
        //请求参数
    }

    private void tl_2() {
        mFragments.add(PostListFragment.newInstance(1));
        mFragments.add(TransferListFragment.newInstance(1));
        mFragments.add(PostListFragment.newInstance(2));
        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        tl1.setTabData(mTabEntities);
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tl1.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}

