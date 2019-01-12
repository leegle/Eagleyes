package com.egova.baselibrary.util;

import android.view.Gravity;
import android.widget.Toast;

import com.egova.baselibrary.application.EgovaApplication;

import es.dmoral.toasty.Toasty;

public class ToastUtil {

    public static void showNormal(String message) {
        Toast toast = Toasty.info(EgovaApplication.getContext(), message);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showWaring(String message){
        Toast toast = Toasty.warning(EgovaApplication.getContext(), message);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static void showSuccess(String message) {
        Toast toast = Toasty.success(EgovaApplication.getContext(), message);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showError(String message){
        Toast toast = Toasty.error(EgovaApplication.getContext(), message);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
