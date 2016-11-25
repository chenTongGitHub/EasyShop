package edu.feicui.easyshopdemo.main.account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.feicui.easyshopdemo.R;
import edu.feicui.easyshopdemo.commons.ActivityUtils;
import edu.feicui.easyshopdemo.components.AvatarLoadOptions;
import edu.feicui.easyshopdemo.main.account.PersonInfo.PersonActivity;
import edu.feicui.easyshopdemo.model.CachePreferences;
import edu.feicui.easyshopdemo.network.EasyShopApi;
import edu.feicui.easyshopdemo.user.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcountFragment extends Fragment {


    @BindView(R.id.iv_user_head)
    CircularImageView mIvUserHead;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_person_info)
    TextView mTvPersonInfo;
    @BindView(R.id.tv_person_goods)
    TextView mTvPersonGoods;
    @BindView(R.id.tv_goods_upload)
    TextView mTvGoodsUpload;
    private ActivityUtils mActivityUtils;
    private View mView;

    public AcountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUtils = new ActivityUtils(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView==null){
            mView = inflater.inflate(R.layout.fragment_acount, container, false);
            ButterKnife.bind(this, mView);
        }

        return mView;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (CachePreferences.getUser().getName() == null) return;
        if (CachePreferences.getUser().getNick_name() == null){
            mTvLogin.setText("请输入昵称");
        }else{
            mTvLogin.setText(CachePreferences.getUser().getNick_name());
        }
        ImageLoader.getInstance()
                .displayImage(EasyShopApi.IMAGE_URL + CachePreferences.getUser().getHead_Image()
                        ,mIvUserHead, AvatarLoadOptions.build());
    }

    @OnClick({R.id.iv_user_head, R.id.tv_person_info, R.id.tv_login, R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View view) {
        if (CachePreferences.getUser().getName() == null){
            mActivityUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()){
            case R.id.iv_user_head:
            case R.id.tv_login:
            case R.id.tv_person_info:
                mActivityUtils.startActivity(PersonActivity.class);
                break;
            case R.id.tv_person_goods:
                mActivityUtils.showToast("我的商品 待实现");
                break;
            case R.id.tv_goods_upload:
                mActivityUtils.showToast("商品上传 待实现");
                break;
        }
    }


}
