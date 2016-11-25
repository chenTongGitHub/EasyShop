package edu.feicui.easyshopdemo.user.register;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import edu.feicui.easyshopdemo.model.CachePreferences;
import edu.feicui.easyshopdemo.model.User;
import edu.feicui.easyshopdemo.model.UserResult;
import edu.feicui.easyshopdemo.network.EasyShopClient;
import edu.feicui.easyshopdemo.network.UICallBack;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/23.
 */

public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView>{
    private Call call;


    //视图销毁，取消网络请求
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call!=null) {
            call.cancel();
        }
    }
    public void register(String userName,String password){
        //显示加载圈
        getView().showProgress();
        call = EasyShopClient.getInstance().register(userName,password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                //隐藏动画
                getView().hideProgress();
                //显示异常信息
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                //拿到返回的结果
                UserResult userResult = new Gson().fromJson(body,UserResult.class);
                //根据结果码处理不同情况
                if (userResult.getCode() == 1){
                    //成功提示
                    getView().showMsg("注册成功");
                    //拿到用户的实体类
                    User user = userResult.getData();
                    //将用户信息保存到本地配置里
                    CachePreferences.setUser(user);
                    //调用注册成功的方法
                    getView().registerSuccess();
                    // TODO: 2016/11/21 0021 还需要登录环信，待实现
                }else if (userResult.getCode() == 2){
                    //隐藏进度条
                    getView().hideProgress();
                    //提示错误信息
                    getView().showMsg(userResult.getMessage());
                    //调用注册失败的方法
                    getView().registerFailed();
                }else{
                    getView().showMsg("未知错误！");
                }
            }
        });
    }
}
