package com.xgx.yushu.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.User;
import com.xgx.yushu.utils.Constact;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.yokeyword.fragmentation.SupportActivity;

public class GuideActivity extends SupportActivity {
    private static final String TAG = GuideActivity.class.getSimpleName();
    @Bind(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;
    @Bind(R.id.banner_guide_foreground)
    BGABanner mForegroundBanner;
    @Bind(R.id.tv_guide_skip)
    TextView tvGuideSkip;
    @Bind(R.id.btn_guide_enter)
    Button btnGuideEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        User user = JSON.parseObject(SPUtils.getInstance().getString(Constact.loginUserSp), User.class);
        if (user != null && EmptyUtils.isNotEmpty(user.getUser_id())) {
            //直接登陆
            LoginInfomation.getInstance().setUser(user);
            startActivity(new Intent(GuideActivity.this, MainActivity.class));
            finish();
        } else {
            setListener();
            processLogic();
        }

    }


    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void processLogic() {
        // 设置数据源
        mBackgroundBanner.setData(R.drawable.uoko_guide_background_1, R.drawable.uoko_guide_background_2, R.drawable.uoko_guide_background_3);

        mForegroundBanner.setData(R.drawable.uoko_guide_foreground_1, R.drawable.uoko_guide_foreground_2, R.drawable.uoko_guide_foreground_3);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}