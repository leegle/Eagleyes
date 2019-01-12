package com.egova.baselibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.egova.baselibrary.R;

/**
 * @Auth zhangyuhao
 * @Date 2017/3/13
 * 自定义一个弹出对话框，在这里定义统一弹出样式
 */

public class EgovaDialog extends Dialog {


    private AnimationSet mAnimIn, mAnimOut;
    private View mDialogView;

    private boolean mIsShowAnim;//是否显示弹出动画
    private boolean isCancelAble = true;//点击屏幕，dialog是否消失
    private boolean isAutoDismiss = false;
    private long dismissDelay = 1000;//多少秒后自动消失
    private boolean showDismissAnim;//是否显示关闭动画

    protected CloseCallBack closeCallBack;

    public EgovaDialog(@NonNull Context context) {
        super(context, R.style.app_dialog);
        init();
    }

    public interface CloseCallBack {
        void close();
    }

    private void init() {
        mAnimIn = AnimationLoader.getInAnimation(getContext());
        mAnimOut = AnimationLoader.getOutAnimation(getContext());
        initAnimListener();
    }

    private void callDismiss() {
        if (closeCallBack != null) {
            closeCallBack.close();
        }
        mHandler.removeCallbacksAndMessages(null);
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (isShowing()) {
                    callDismiss();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        startWithAnimation(mIsShowAnim);
        setCancelable(isCancelAble);
        if (isAutoDismiss) {
            mHandler.sendEmptyMessageDelayed(1, dismissDelay);
        }
    }

    public CloseCallBack getCloseCallBack() {
        return closeCallBack;
    }

    public void setCloseCallBack(CloseCallBack closeCallBack) {
        this.closeCallBack = closeCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);

    }


    private void startWithAnimation(boolean showInAnimation) {
        if (showInAnimation) {
            mDialogView.startAnimation(mAnimIn);
        }
    }

    private void dismissWithAnimation(boolean showOutAnimation) {
        if (showOutAnimation) {
            mDialogView.startAnimation(mAnimOut);
        } else {
            super.dismiss();
        }
    }

    private void initAnimListener() {
        mAnimOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        callDismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public boolean isCancelAble() {
        return isCancelAble;
    }

    public void setCancelAble(boolean cancelAble) {
        isCancelAble = cancelAble;
    }

    @Override
    public void dismiss() {
        mHandler.removeCallbacksAndMessages(null);
        dismissWithAnimation(showDismissAnim);
    }

    public boolean ismIsShowAnim() {
        return mIsShowAnim;
    }

    public void setmIsShowAnim(boolean mIsShowAnim) {
        this.mIsShowAnim = mIsShowAnim;
    }

    public boolean isShowDismissAnim() {
        return showDismissAnim;
    }

    public void setShowDismissAnim(boolean showDismissAnim) {
        this.showDismissAnim = showDismissAnim;
    }

    public boolean isAutoDismiss() {
        return isAutoDismiss;
    }

    public void setAutoDismiss(boolean autoDismiss) {
        isAutoDismiss = autoDismiss;
    }

    public long getDismissDelay() {
        return dismissDelay;
    }

    public void setDismissDelay(long dismissDelay) {
        this.dismissDelay = dismissDelay;
    }
}
