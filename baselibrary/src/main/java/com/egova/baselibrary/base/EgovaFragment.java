package com.egova.baselibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.baselibrary.util.ToastUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public abstract class EgovaFragment<V extends ViewDataBinding, VM extends AndroidViewModel>extends Fragment {


    protected V binding;
    protected VM viewModel;
    private MaterialDialog dialog;

    public boolean isBackPressed() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewDataBinding(inflater,container,savedInstanceState);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        //让ViewModel拥有View的生命周期感应
        addVMObserver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel = null;
        binding.unbind();
    }

    private void initViewDataBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //初始化viewModel
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Class modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            viewModel = (VM) createViewModel(this, modelClass);
        } else {
        }
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);

    }
    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void initView();
    public abstract void initData();
    /**
     * 添加viewModel观察
     */
    public abstract void addVMObserver();

    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
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
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(getActivity(), title, true);
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

}
