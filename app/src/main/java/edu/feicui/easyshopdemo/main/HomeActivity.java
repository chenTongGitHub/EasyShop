package edu.feicui.easyshopdemo.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.feicui.easyshopdemo.R;
import edu.feicui.easyshopdemo.commons.ActivityUtils;
import edu.feicui.easyshopdemo.main.account.AcountFragment;
import edu.feicui.easyshopdemo.main.market.MarketFragment;
import edu.feicui.easyshopdemo.model.CachePreferences;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_home)
    Toolbar toolbarHome;
    @BindView(R.id.viewpager_home)
    ViewPager viewpagerHome;
    @BindView(R.id.market_home)
    TextView marketHome;
    @BindView(R.id.message_home)
    TextView messageHome;
    @BindView(R.id.friends_home)
    TextView friendsHome;
    @BindView(R.id.account_home)
    TextView accountHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.activity_home)
    RelativeLayout activityHome;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindViews({R.id.market_home,R.id.message_home,R.id.friends_home,R.id.account_home})
    TextView[] textViews;
//    public TextView[] textViews =new TextView[] {marketHome, messageHome, friendsHome, accountHome};

    private boolean isEXIT = false;//点击两次返回键退出程序（默认false）
    private ActivityUtils activityUtils;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);//绑定ButterKnife

        activityUtils = new ActivityUtils(this);
        //设置toolbar
        setSupportActionBar(toolbarHome);
        //设置一下ActionBae标题为空，否则默认显示应用名
        getSupportActionBar().setTitle("");

        init();
    }


    //进入页面，初始化数据。默认首次进入显示市场页面
    public void init() {
        //默认首次进入显示购物车图标点亮
        textViews[0].setSelected(true);
        // TODO: 2016/11/16 选择不同的适配器
        /**
         * 根据用户是否登录，选择不同的适配器
         */
        if(CachePreferences.getUser().getName()==null){
            viewpagerHome.setAdapter(unLoginAdapter);
            //viewpager展示第一个子界面
            viewpagerHome.setCurrentItem(0);
        }else{
            viewpagerHome.setAdapter(loginAdapter);
            viewpagerHome.setCurrentItem(0);
        }

        //viewpager滑动监听事件
        viewpagerHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //首先将所有图标置为灰色
                for (TextView textView : textViews) {
                    textView.setSelected(false);
                }
                //滑到哪个界面：toolbar对应标题改变，对应图标点亮
                //toolbar对应标题改变
                mainTitle.setText(textViews[position].getText());
                //对应图标点亮
                textViews[position].setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    /**
     * 点击图标，对应展示viewpager哪个界面
     */
    @OnClick({R.id.market_home, R.id.message_home, R.id.friends_home, R.id.account_home})
    public void onClick(TextView textView) {
        //遍历所有图标，先将都置成灰的
        for (int i = 0; i <textViews.length ; i++) {
            textViews[i].setSelected(false);
            textViews[i].setTag(i);//注意此处tag的用法
        }
         //选择效果
        textView.setSelected(true);
        //参数false代表瞬间切换，而不是平滑过渡(此处用到textViews[i].setTag(i);)
        viewpagerHome.setCurrentItem((Integer) textView.getTag(),false);
        //toolbar对应的标题改变
        mainTitle.setText(textViews[(int) textView.getTag()].getText());
    }


    /**
     * 点击2次返回键，退出程序
     */
    @Override
    public void onBackPressed() {
        if (!isEXIT) {
            isEXIT = true;
            activityUtils.showToast("再按一次返回键退出应用");
            //2秒内再次按返回键，退出应用
            viewpagerHome.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isEXIT = false;
                }
            }, 2000);//延时2秒，2秒内没有再按返回键，开关置为默认状态
        } else {
            finish();//已经按过返回键，开关置为true，2秒内再按返回键，退出应用
        }
    }

    //用户没有登陆时viewPager的适配器
    private FragmentStatePagerAdapter unLoginAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MarketFragment();

                case 1:
                    return new UnLoginFragment();
                case 2:
                    return new UnLoginFragment();
                case 3:
                    return new AcountFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    //用户登陆后viewPager的适配器
    private FragmentStatePagerAdapter loginAdapter = new
            FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position){
                        //市场
                        case 0:
                            return new MarketFragment();
                        //消息
                        case 1:
                            // TODO: 2016/11/23 0023 环信消息fragment
                            return new UnLoginFragment();
                        //通讯录
                        case 2:
                            // TODO: 2016/11/23 0023 环信的通讯录fragment
                            return new UnLoginFragment();
                        //我的
                        case 3:
                            return new AcountFragment();
                    }
                    return null;
                }
                @Override
                public int getCount() {
                    return 4;
                }
            };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
