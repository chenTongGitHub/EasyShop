package edu.feicui.easyshopdemo.user.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import edu.feicui.easyshopdemo.R;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import edu.feicui.easyshopdemo.commons.ActivityUtils;
import edu.feicui.easyshopdemo.commons.RegexUtils;
import edu.feicui.easyshopdemo.components.AlertDialogFragment;
import edu.feicui.easyshopdemo.components.ProgressDialogFragment;
import edu.feicui.easyshopdemo.main.HomeActivity;

public class RegisterActivity extends MvpActivity<RegisterView,RegisterPresenter> implements RegisterView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText et_userName;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_pwdAgain)
    EditText et_pwdAgain;
    @BindView(R.id.btn_register)
    Button btn_register;

    private String username;
    private String password;
    private String pwd_again;
    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        init();
    }

    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    private void init() {
        et_userName.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);
        et_pwdAgain.addTextChangedListener(textWatcher);
        setSupportActionBar(toolbar);
        //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //给左上角加一个返回图标,需要重写菜单点击事件，否则点击无效
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            username = et_userName.getText().toString();
            password = et_pwd.getText().toString();
            pwd_again = et_pwdAgain.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(pwd_again));
            btn_register.setEnabled(canLogin);
        }
    };

    @OnClick(R.id.btn_register)
    public void onClick() {
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            String msg = getString(R.string.username_rules);
            showError(msg);
            return;
        } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            String msg = getString(R.string.password_rules);
            showError(msg);
            return;
        } else if (!TextUtils.equals(password, pwd_again)) {
            String msg = getString(R.string.username_equal_pwd);
            showError(msg);
            return;
        }
        presenter.register(username,password);
    }



    @Override
    public void registerFailed() {
        et_userName.setText("");
    }

    @Override
    public void registerSuccess() {
        //成功跳转到主页
        activityUtils.startActivity(HomeActivity.class);
        finish();
    }
    //显示错误提示
    @Override
    public void showError(String msg) {
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(msg);
        fragment.show(getSupportFragmentManager(), getString(R.string.username_pwd_rule));
    }

    @Override
    public void showProgress() {
        //关闭软键盘
        activityUtils.hideSoftKeyboard();
        if (dialogFragment == null) {
            dialogFragment = new ProgressDialogFragment();
        }
        if (dialogFragment.isVisible()){
            return;
        }
        // TODO: 2016/11/25 有问题
        //dialogFragment.show(getSupportFragmentManager(),"progress_dialog_fragment");
    }

    @Override
    public void hideProgress() {
        dialogFragment.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }


}