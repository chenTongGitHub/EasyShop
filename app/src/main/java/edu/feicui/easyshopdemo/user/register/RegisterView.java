package edu.feicui.easyshopdemo.user.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/11/23.
 */

public interface RegisterView extends MvpView{
    //显示加载圈
    void showProgress();
    //隐藏加载圈
    void hideProgress();
    //弹出消息
    void showMsg(String msg);
    //注册失败
    void registerFailed();
    //注册成功
    void registerSuccess();
    //提示注册信息不符合规则
    void showError(String msg);

}
