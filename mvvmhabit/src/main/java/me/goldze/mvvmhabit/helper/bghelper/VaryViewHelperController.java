package me.goldze.mvvmhabit.helper.bghelper;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.goldze.mvvmhabit.R;


/**
 * @author WYH_Healer
 * @email 3425934925@qq.com
 * Created by xz on 2016/9/19.
 * Role:
 */
public class VaryViewHelperController {

    private IVaryViewHelper helper;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void showNetworkError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.module_public_message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(helper.getContext().getResources().getString(R.string.module_public_common_no_network_msg));

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_net_error);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showError(String errorMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.module_public_message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(errorMsg)) {
            textView.setText(errorMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.module_public_common_error_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_service_404);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.module_public_message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.module_public_common_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_exception);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.module_public_loading);
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }

    public void restore() {
        helper.restoreView();
    }

    /**
     * 距离顶部高度的自定义的无数据样式
     */
    public void showTopImageEmpty(String emptyMsg, String go2,int textColor, int imgId, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.module_public_top_empty_message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if(textColor!=-1){
            textView.setTextColor(textColor);
        }
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.module_public_common_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        if(imgId!=0){
            imageView.setImageResource(imgId);
        }

        TextView text_go = (TextView) layout.findViewById(R.id.tv_go);
        if (!TextUtils.isEmpty(go2) && go2 != null) {
            text_go.setText(go2 + "");
            text_go.setVisibility(View.VISIBLE);
        } else {
            text_go.setVisibility(View.GONE);
        }


        if (null != onClickListener) {
            text_go.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    /**
     * 自定义的无数据样式
     */
    public void showImageEmpty(String emptyMsg, String go2,int textColor, int imgId, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.module_public_empty_message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if(textColor!=-1){
            textView.setTextColor(textColor);
        }
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.module_public_common_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        if(imgId!=0){
            imageView.setImageResource(imgId);
        }

        TextView text_go = (TextView) layout.findViewById(R.id.tv_go);
        if (!TextUtils.isEmpty(go2) && go2 != null) {
            text_go.setText(go2 + "");
            text_go.setVisibility(View.VISIBLE);
        } else {
            text_go.setVisibility(View.GONE);
        }


        if (null != onClickListener) {
            text_go.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    /**
     * 内嵌可滚动的自定义的无数据样式
     */
    public void showScrollImageEmpty(String emptyMsg, String go2,int textColor, int imgId, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.module_public_scroll_empty_message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if(textColor!=-1){
            textView.setTextColor(textColor);
        }
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.module_public_common_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        if(imgId!=0){
            imageView.setImageResource(imgId);
        }

        TextView text_go = (TextView) layout.findViewById(R.id.tv_go);
        if (!TextUtils.isEmpty(go2) && go2 != null) {
            text_go.setText(go2 + "");
            text_go.setVisibility(View.VISIBLE);
        } else {
            text_go.setVisibility(View.GONE);
        }


        if (null != onClickListener) {
            text_go.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }


    /**
     *正在加载中新增
     */
    public void showLoading() {
        View layout = helper.inflate(R.layout.module_public_layout_loading);

        ImageView imagiv = (ImageView) layout.findViewById(R.id.iv_loading);
//        ((AnimationDrawable) imagiv.getDrawable()).start();

        helper.showLayout(layout);
    }



}
