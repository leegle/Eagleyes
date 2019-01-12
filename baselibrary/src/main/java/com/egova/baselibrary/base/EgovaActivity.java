package com.egova.baselibrary.base;

import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.R;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.baselibrary.util.ToastUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class EgovaActivity<V extends ViewDataBinding, VM extends AndroidViewModel> extends SwipeBackActivity {

    protected V binding;
    protected VM viewModel;
    private MaterialDialog dialog;

    private boolean isWithAnimation = true;//退出时是否携带动画

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewDataBinding(savedInstanceState);
    }


    private void initViewDataBinding(Bundle savedInstanceState) {
        //初始化viewModel
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Class modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            viewModel = (VM) createViewModel(this, modelClass);
        } else {
        }
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        //让ViewModel拥有View的生命周期感应
        addVMObserver();
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 添加viewModel观察
     */
    public abstract void addVMObserver();

    public void setWithAnimation(boolean withAnimation) {
        isWithAnimation = withAnimation;
    }

    public boolean isWithAnimation() {
        return isWithAnimation;
    }

    @Override
    public void finish() {
        super.finish();
        if (isWithAnimation())
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    /**
     * 开启耗时等待对话框
     */
    public void showLoadingDialog() {
        showLoadingDialog("加载中...");
    }

    /**
     * 开启耗时等待对话框
     */
    public void showLoadingDialog(String title) {
        if (dialog != null) {
            dialog.setTitle(title);
            dialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(this, title, true);
            dialog = builder.show();
        }
    }

    /**
     * 关闭耗时等待框
     */
    public void dismissLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showToast(String message) {
        ToastUtil.showNormal(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel = null;
        binding.unbind();
    }
}
