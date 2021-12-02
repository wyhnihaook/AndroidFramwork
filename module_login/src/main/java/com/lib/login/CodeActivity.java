package com.lib.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import com.lib.login.databinding.LoginActivityCodeViewBinding;
import me.goldze.mvvmhabit.utils.CountDownTimerUtils;
import com.lib.login.viewmodel.CodeViewModel;

import me.goldze.mvvmhabit.base.BaseAppActivity;

import me.goldze.mvvmhabit.utils.KeyBoardUtils;
import me.goldze.mvvmhabit.utils.StatusBarUtil;

/**
 * @author wengyiheng
 * @date 2021/8/25.
 * description：
 */
public class CodeActivity extends BaseAppActivity<LoginActivityCodeViewBinding, CodeViewModel> {
    private String phone;
    private CountDownTimerUtils mCountDownTimerUtils;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.login_activity_code_view;
    }

    @Override
    public int initVariableId() {
        return BR.codeViewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        if (getIntent() != null) {
            if(!TextUtils.isEmpty(getIntent().getStringExtra("phone"))){
                phone = getIntent().getStringExtra("phone").toString();
            }
        }
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setSystemBar(this, getResources().getColor(R.color.transport), false, true);


        viewModel.activityObservableField.set(this);
        mCountDownTimerUtils = new CountDownTimerUtils(binding.btnGetCode, 60000, 1000);
        mCountDownTimerUtils.start();

        viewModel.phoneInfo.set(phone);
        phone = phone.substring(0, 3) + "****" + phone.substring(7, 11);
        binding.tvPhoneCode.setText("验证码已发送到+86  " + phone);
        binding.btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimerUtils.start();
                viewModel.getCode.execute();
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.finish();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyBoardUtils.showKeyboard(binding.etCode);
            }
        },500);

        binding.etCode.addTextChangedListener(textWatcher);
        // 监听验证码删除按键
        binding.etCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && viewModel.codes.size() > 0) {
                    viewModel.codes.remove(viewModel.codes.size() - 1);
                    showCode();
                    return true;
                }
                return false;
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && s.length() > 0) {
                binding.etCode.setText("");
                if (viewModel.codes.size() < 4) {
                    viewModel.codes.add(s.toString());
                    showCode();
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }
    };

    /**
     * 显示输入的验证码
     */
    private void showCode() {
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        if (viewModel.codes.size() >= 1) {
            code1 = viewModel.codes.get(0);
        }
        if (viewModel.codes.size() >= 2) {
            code2 = viewModel.codes.get(1);
        }
        if (viewModel.codes.size() >= 3) {
            code3 = viewModel.codes.get(2);
        }
        if (viewModel.codes.size() >= 4) {
            code4 = viewModel.codes.get(3);
        }
        binding.text1.setText(code1);
        binding.text2.setText(code2);
        binding.text3.setText(code3);
        binding.text4.setText(code4);
        setColor();//设置高亮颜色
    }

    /**
     * 设置高亮颜色
     */
    private void setColor() {
        int color_default = Color.parseColor("#585858");
        int color_focus = Color.parseColor("#C5A873");
        binding.view1.setBackgroundColor(color_default);
        binding.view2.setBackgroundColor(color_default);
        binding.view3.setBackgroundColor(color_default);
        binding.view4.setBackgroundColor(color_default);
        if (viewModel.codes.size() == 0) {
            binding.view1.setBackgroundColor(color_focus);
        }
        if (viewModel.codes.size() == 1) {
            binding.view2.setBackgroundColor(color_focus);
        }
        if (viewModel.codes.size() == 2) {
            binding.view3.setBackgroundColor(color_focus);
        }
        if (viewModel.codes.size() >= 3) {
            binding.view4.setBackgroundColor(color_focus);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCountDownTimerUtils!=null){
            mCountDownTimerUtils.onFinish();
            mCountDownTimerUtils.cancel();
        }

        KeyBoardUtils.closeKeybord(binding.etCode);
    }
}

