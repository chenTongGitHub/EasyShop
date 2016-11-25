package edu.feicui.easyshopdemo.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



import java.util.Timer;
import java.util.TimerTask;

import edu.feicui.easyshopdemo.R;
import edu.feicui.easyshopdemo.commons.ActivityUtils;

public class SplashActivity extends AppCompatActivity {

    private ActivityUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        utils = new ActivityUtils(this);

        // TODO:  登录账号冲突
        // TODO:  判断用户是否登录


        Timer timer = new Timer();
        //2秒后跳转到主页面并且finish
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                utils.startActivity(HomeActivity.class);
                finish();
            }
        },2000);
    }
}
