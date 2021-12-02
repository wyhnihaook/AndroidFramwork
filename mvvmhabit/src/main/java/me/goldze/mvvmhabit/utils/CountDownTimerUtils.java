package me.goldze.mvvmhabit.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import me.goldze.mvvmhabit.R;


/**
 * @author wengyiheng
 * @date 2021/8/25.
 * description：
 */
public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;

    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setTextColor(Color.parseColor("#80E0FB10"));
        mTextView.setBackgroundResource(R.drawable.shape_code_btn_alpha_50);
        mTextView.setText(millisUntilFinished / 1000 + "秒后重试");  //设置倒计时时间
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setTextColor(Color.parseColor("#E0FB10"));
        mTextView.setBackgroundResource(R.drawable.shape_code_btn);
        mTextView.setClickable(true);//重新获得点击
    }
}

