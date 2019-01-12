package com.egova.baselibrary.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.R;

import java.util.List;

public class MaterialDialogUtils {

    /***
     * 获取一个耗时等待对话框
     *
     * @param horizontal
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder showIndeterminateProgressDialog(Context context, String content, boolean horizontal) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(content)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .canceledOnTouchOutside(false)
                .widgetColorRes(R.color.colorPrimary)
                .backgroundColorRes(R.color.dialog_confirm_bg_color)
                .keyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {//如果是按下，则响应，否则，一次按下会响应两次
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                //activity.onBackPressed();

                            }
                        }
                        return false;//false允许按返回键取消对话框，true除了调用取消，其他情况下不会取消
                    }
                });
        return builder;
    }

    /**
     * 基本弹窗，包含标题，内容，确定按钮，取消按钮
     *
     * @param context
     * @param title        标题
     * @param content      内容
     * @param positiveText 确定
     * @param negativeText 取消
     * @return
     */
    public static MaterialDialog.Builder showBasicDialog(final Context context, String title, String content, String positiveText, String negativeText) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(0XFF333333)
                .content(content)
                .contentColor(0XFF333333)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .cancelable(false)
                .autoDismiss(true);
        return builder;
    }

    /***
     * 选择list弹窗，比如拍照，相册选择等Item的对话框  带标题，标题可为空
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder getSelectDialog(Context context, String title, String[] arrays) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .items(arrays)
                .itemsColor(0XFF456ea6)
                .cancelable(true)
                .canceledOnTouchOutside(true)
                .autoDismiss(true);

        if (!TextUtils.isEmpty(title)) {
            builder.title(title);
        }
        return builder;
    }

    /***
     * 选择list弹窗，比如拍照，相册选择等Item的对话框  带标题，标题可为空
     * @param
     * @return MaterialDialog.Builder
     */
    public static MaterialDialog.Builder getCollectionSelectDialog(Context context, String title, List<?> arrays) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .items(arrays)
                .itemsColor(0XFF456ea6)
                .cancelable(true)
                .canceledOnTouchOutside(true)
                .autoDismiss(true);

        if (!TextUtils.isEmpty(title)) {
            builder.title(title);
        }
        return builder;
    }

    /**
     * 自定义弹窗
     *
     * @param context
     * @param contentView
     * @param title
     * @param positiveText
     * @param negativeText
     * @return
     */
    public static MaterialDialog.Builder getCustomDialog(Context context, View contentView, String title, String positiveText, String negativeText) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(contentView, true)
                .title(title)
                .titleColor(0XFF333333)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .cancelable(false)
                .autoDismiss(true);
        return builder;
    }
    public static MaterialDialog.Builder getCustomDialog(Context context, View contentView, String title) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .customView(contentView, true)
                .title(title)
                .titleColor(0XFF333333)
                .cancelable(false)
                .autoDismiss(true);
        return builder;
    }
}
