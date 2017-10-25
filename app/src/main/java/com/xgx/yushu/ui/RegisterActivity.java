package com.xgx.yushu.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xgx.yushu.R;
import com.xgx.yushu.base.BaseEventBusActivity;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.Constact;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.utils.UnicodeUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by xgx on 2017/9/9 0009 for YuShu
 */

public class RegisterActivity extends BaseEventBusActivity {

    @Bind(R.id.accoutEt)
    EditText accoutEt;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.realNameEt)
    EditText realNameEt;
    @Bind(R.id.phoneEt)
    EditText phoneEt;
    @Bind(R.id.registerBtn)
    Button registerBtn;
    @Bind(R.id.loginBtn)
    TextView loginBtn;
    @Bind(R.id.callKefuBtn)
    TextView callKefuBtn;


    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public boolean isBindEventBusHere() {
        return false;
    }


    @Override
    public void initView() {
        StatusBarUtil.setTransparent(RegisterActivity.this);

    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.layout_register);
    }

    @Override
    public void initPresenter() {

    }


    @OnClick({R.id.registerBtn, R.id.loginBtn, R.id.callKefuBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBtn:
                //验证房号
                if (!RegexUtils.isMatch(Constact.accountRex, accoutEt.getText())) {
                    ToastUtils.showShort("请输入正确的房号（如10#501）");
                    break;
                }
                if (EmptyUtils.isEmpty(realNameEt.getText())) {
                    ToastUtils.showShort("姓名不能为空");
                    break;
                }
                if (!RegexUtils.isMobileExact(phoneEt.getText())) {
                    ToastUtils.showShort("请输入正确的手机号");
                    break;
                }
                OkGo.<MyResponse>post(MyUrl.baseUrl)
                        .params("service", MyUrl.register)
                        .params("user_name", realNameEt.getText().toString())
                        .params("user_floor", RegexUtils.getSplits(accoutEt.getText().toString(), "#")[0])
                        .params("user_room", RegexUtils.getSplits(accoutEt.getText().toString(), "#")[1])
                        .params("user_tel", phoneEt.getText().toString())
                        .execute(new DialogCallback<MyResponse>(this) {
                            @Override
                            public void onSuccess(Response<MyResponse> response) {
                                //保存信息 进行登录
                                ToastUtils.showShort(response.body().data.getMsg());
                                finish();
                            }

                            @Override
                            public void onError(Response<MyResponse> response) {
                                super.onError(response);
                                ToastUtils.showShort(response.getException().getMessage());

                            }

                        });
                break;
            case R.id.loginBtn:
                finish();
                break;
            case R.id.callKefuBtn:
                break;
        }
    }
}
