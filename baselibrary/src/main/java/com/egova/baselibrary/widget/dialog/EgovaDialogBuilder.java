package com.egova.baselibrary.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.egova.baselibrary.R;
import com.egova.baselibrary.widget.progressindicator.AVLoadingIndicatorView;
import com.egova.baselibrary.widget.progressindicator.ProgressStyle;

public class EgovaDialogBuilder {

    private EgovaDialogBuilder() {
    }

    /**
     * 加载中对话框
     *
     * @param context
     * @param text    加载提示文字  默认为：加载中...
     * @return
     */
    public static EgovaDialog showLoadingDialog(Context context, String text) {

        View loadingView = LayoutInflater.from(context).inflate(R.layout.loading_dialog_view, null);
        AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) loadingView.findViewById(R.id.loading_view_indicator_view);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressView.setIndicatorColor(Color.GRAY);
        if (!TextUtils.isEmpty(text)) {
            ((TextView) loadingView.findViewById(R.id.text)).setText(text);
        }
        EgovaDialog dialog = new EgovaDialog(context);
        dialog.setCancelAble(false);
        dialog.setAutoDismiss(true);
        dialog.setDismissDelay(2 * 60 * 1000);//加载中如果35S还未获取数据则认为超时，此时自动消失
        dialog.setContentView(loadingView);
        dialog.setmIsShowAnim(true);
        dialog.setShowDismissAnim(true);
        dialog.show();
        return dialog;
    }

    /**
     * 提示语弹窗，会自动几秒后消失
     *
     * @param context
     * @param promptText 弹窗默认会在1.8s后自动消失
     * @return
     */
    public static EgovaDialog showPromptDialog(Context context, String promptText) {
        return showPromptDialog(context, promptText, true, true, 2000);
    }

    /**
     * @param context
     * @param promptText       提示语
     * @param isAutoDismiss    在dismissDelayTime时间后，弹窗是否自动消失
     * @param outCancelAble    触摸弹窗外面，弹窗是否可以消失
     * @param dismissDelayTime 延时时间
     * @return
     */
    public static EgovaDialog showPromptDialog(Context context, String promptText, boolean isAutoDismiss, boolean outCancelAble, long dismissDelayTime) {
        View view = LayoutInflater.from(context).inflate(R.layout.prompt_dialog_layout, null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView prompt = (TextView) view.findViewById(R.id.promptText);
        prompt.setText(promptText);
        EgovaDialog dialog = new EgovaDialog(context);
        dialog.setCancelAble(outCancelAble);
        dialog.setAutoDismiss(isAutoDismiss);
        dialog.setDismissDelay(dismissDelayTime);
        dialog.setContentView(view);
        dialog.setmIsShowAnim(true);
        dialog.setShowDismissAnim(true);
        dialog.show();
        return dialog;
    }

    /**
     * 自定义内容弹窗显示
     *
     * @param context
     * @param contentView
     * @return
     */
    public static EgovaDialog showSelectDialog(Context context, View contentView) {
        EgovaDialog dialog = new EgovaDialog(context);
        dialog.setCancelAble(true);
        dialog.setAutoDismiss(false);
        dialog.setContentView(contentView);
        dialog.setmIsShowAnim(true);
        dialog.setShowDismissAnim(true);
        dialog.show();
        return dialog;
    }

    /**
     * 确认取消弹窗
     *
     * @param context
     * @param title  标题
     * @param content  内容
     * @return
     */
    public static EgovaDialog showConfirmDialog(Context context, String title, String content, final OnDialogClickListener clickListener) {
        return showConfirmDialog(context, title, content, "取消", "确定", clickListener);
    }

    /**
     * 确认取消弹窗
     *
     * @param context
     * @param title  标题
     * @param content  内容
     * @param cancelButtonText  左边按钮文字
     * @param okButtonText   右边按钮文字
     * @param clickListener 按钮点击事件
     * @return
     */
    public static EgovaDialog showConfirmDialog(Context context, String title, String content, String cancelButtonText, String okButtonText, final OnDialogClickListener clickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
        TextView titleText = view.findViewById(R.id.title);
        TextView contentText = view.findViewById(R.id.content);
        TextView cancelBtn = view.findViewById(R.id.cancelBtn);
        TextView okBtn = view.findViewById(R.id.okBtn);
        titleText.setText(title);
        contentText.setText(content);
        okBtn.setText(okButtonText);
        cancelBtn.setText(cancelButtonText);
        EgovaDialog dialog = new EgovaDialog(context);
        dialog.setCancelAble(false);
        dialog.setAutoDismiss(false);
        dialog.setContentView(view);
        dialog.setmIsShowAnim(true);
        dialog.setShowDismissAnim(true);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.OnClickCancel();
                }
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.OnClickConfirm();
                }
            }
        });
        dialog.show();
        return dialog;
    }

    /**
     * 确认单个按钮弹窗
     *
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static EgovaDialog showSingleConfirmDialog(Context context, String title, String content, final OnDialogClickListener clickListener) {
        return showSingleConfirmDialog(context, title, content, "确定", clickListener);
    }

    /**
     * 确认单个按钮弹窗
     *
     * @param context
     * @param title
     * @param content
     * @param okButtonText
     * @param clickListener
     * @return
     */
    public static EgovaDialog showSingleConfirmDialog(Context context, String title, String content, String okButtonText, final OnDialogClickListener clickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_single_confirm, null);
        TextView titleText = view.findViewById(R.id.title);
        TextView contentText = view.findViewById(R.id.content);
        TextView okBtn = view.findViewById(R.id.okBtn);
        titleText.setText(title);
        contentText.setText(content);
        okBtn.setText(okButtonText);
        EgovaDialog dialog = new EgovaDialog(context);
        dialog.setCancelAble(false);
        dialog.setAutoDismiss(false);
        dialog.setContentView(view);
        dialog.setmIsShowAnim(true);
        dialog.setShowDismissAnim(true);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.OnClickConfirm();
                }
            }
        });
        dialog.show();
        return dialog;
    }


}
