package com.xgx.yushu.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xgx.yushu.R;
import com.xgx.yushu.base.BaseEventBusActivity;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.User;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.Constact;
import com.xgx.yushu.utils.MyUrl;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by xgx on 2017/9/9 0009 for YuShu
 */

public class LoginActivity extends BaseEventBusActivity {
    @Bind(R.id.accoutEt)
    EditText accoutEt;
    @Bind(R.id.passwordEt)
    EditText passwordEt;
    @Bind(R.id.loginBtn)
    Button loginBtn;
    @Bind(R.id.findPasswordBtn)
    TextView findPasswordBtn;
    @Bind(R.id.registerBtn)
    TextView registerBtn;
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
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        StatusBarUtil.setTransparent(LoginActivity.this);
        passwordEt.setText(SPUtils.getInstance().getString(Constact.loginHistoryPassWord, ""));
        accoutEt.setText(SPUtils.getInstance().getString(Constact.loginHistoryUserName, ""));
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
    }


    @OnClick({R.id.loginBtn, R.id.findPasswordBtn, R.id.registerBtn, R.id.callKefuBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                if (EmptyUtils.isEmpty(accoutEt.getText())) {
                    ToastUtils.showShort("用户名不能为空）");
                    break;
                }

                if (EmptyUtils.isEmpty(passwordEt.getText())) {
                    ToastUtils.showShort("密码不能为空");
                    break;
                }
                OkGo.<MyResponse>post(MyUrl.baseUrl)
                        .params("service", MyUrl.login)
                        .params("account", accoutEt.getText().toString())
                        .params("pwd", passwordEt.getText().toString())
                        .execute(new DialogCallback<MyResponse>(this) {
                            @Override
                            public void onSuccess(Response<MyResponse> response) {
                                try {
                                    //保存信息 进行登录
                                    ToastUtils.showShort("登陆成功");

                                    //记住账号 和密码
                                    ResultData userData = response.body().data;
                                    User user = ((JSONObject) userData.info).toJavaObject(User.class);
                                    LoginInfomation.getInstance().setUser(user);
                                    SPUtils.getInstance().put(Constact.loginUserSp, JSON.toJSONString(user));
                                    SPUtils.getInstance().put(Constact.loginHistoryUserName, accoutEt.getText().toString());
                                    SPUtils.getInstance().put(Constact.loginHistoryPassWord, passwordEt.getText().toString());
                                    //保存历史记录
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } catch (Exception e) {
                                    ToastUtils.showShort("登录失败" + e.getMessage());
                                }

                            }

                            @Override
                            public void onError(Response<MyResponse> response) {
                                super.onError(response);
                                ToastUtils.showShort(response.getException().getMessage());
                            }
                        });


                break;
            case R.id.findPasswordBtn:
                break;
            case R.id.registerBtn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.callKefuBtn:
                break;
            default:
                break;
        }
    }
}
