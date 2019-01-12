package com.egova.eagleyes.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.egova.baselibrary.base.EgovaFragment;
import com.egova.baselibrary.model.BaseResponse;
import com.egova.baselibrary.model.Lcee;
import com.egova.baselibrary.model.Paging;
import com.egova.baselibrary.model.QueryModel;
import com.egova.baselibrary.util.ActivityLaunchUtil;
import com.egova.baselibrary.util.DateUtil;
import com.egova.baselibrary.util.MaterialDialogUtils;
import com.egova.baselibrary.util.ToastUtil;
import com.egova.baselibrary.widget.wheeltime.ScreenInfo;
import com.egova.baselibrary.widget.wheeltime.WheelMain;
import com.egova.eagleyes.R;
import com.egova.eagleyes.constants.Constants;
import com.egova.eagleyes.databinding.FragmentDispositionSearchBinding;
import com.egova.eagleyes.model.request.DispositionCondition;
import com.egova.eagleyes.model.respose.DispositionInfo;
import com.egova.eagleyes.model.respose.PersonInfo;
import com.egova.eagleyes.vm.PoliceControlViewModel;
import com.egova.eagleyes.vm.UserViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * 车辆布控-布控查询
 */
public class DispositionSearchFragment extends EgovaFragment<FragmentDispositionSearchBinding, PoliceControlViewModel> {
    private QueryModel<DispositionCondition> queryModel;
    private DispositionCondition condition;
    private WheelMain wheelMain;//时间控件
    private int selectType = 0;
    private PersonInfo selectPerson;

    private UserViewModel userViewModel;

    private ArrayList<PersonInfo> personList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_disposition_search;
    }

    @Override
    public void initView() {
        binding.startDate.setText(DateUtil.convertMisToDay(condition.getStartCreateTime()));
        binding.endDate.setText(DateUtil.convertMisToDay(condition.getEndCreateTime()));
        binding.operatePerson.setContent("不限");
        setSelected(R.id.noLimit);
        binding.setOnClick(onClickListener);

    }

    @Override
    public void initData() {
        queryModel = new QueryModel<>();
        condition = new DispositionCondition();
        condition.setStartCreateTime(DateUtil.dateFormatMis(DateUtil.getTodayStart(DateUtil.getDateAddDays(-15))));
        condition.setEndCreateTime(DateUtil.dateFormatMis(DateUtil.getTodayEnd(new Date())));
        condition.setDisabled(null);
        queryModel.setCondition(condition);

        personList = new ArrayList<>();
    }

    @Override
    public void addVMObserver() {
        //布控列表查询
        viewModel.getDispositionSearchData().observe(this, new Observer<Lcee<BaseResponse<List<DispositionInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<DispositionInfo>>> baseResponseLcee) {
                updateDispositionListData(baseResponseLcee);
            }
        });
        //布控人员查询
        userViewModel = createViewModel(this, UserViewModel.class);
        userViewModel.getPersonListData().observe(this, new Observer<Lcee<BaseResponse<List<PersonInfo>>>>() {
            @Override
            public void onChanged(Lcee<BaseResponse<List<PersonInfo>>> baseResponseLcee) {
                dismissLoadingDialog();
                switch (baseResponseLcee.status) {
                    case Error:
                        ToastUtil.showError(baseResponseLcee.error.getMessage());
                        break;
                    case Content:
                        personList.clear();
                        personList.addAll(baseResponseLcee.data.getResult());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.BUNDLE_USER, personList);
                        ActivityLaunchUtil.launchContainerActivity(getActivity(), PersonSearchFragment.class.getCanonicalName(), bundle);
                        break;
                }

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventBus(PersonInfo personInfo) {
        if (personInfo != null) {
            binding.operatePerson.setContent(personInfo.getName());
            selectPerson = personInfo;
        }
    }


    private void setSelected(int viewId) {
        switch (viewId) {
            case R.id.noLimit:
                binding.noLimit.setSelected(true);
                binding.deal.setSelected(false);
                binding.noDeal.setSelected(false);
                condition.setDisabled(null);
                break;
            case R.id.deal:
                binding.noLimit.setSelected(false);
                binding.deal.setSelected(true);
                binding.noDeal.setSelected(false);
                condition.setDisabled(false);
                break;
            case R.id.noDeal:
                binding.noLimit.setSelected(false);
                binding.deal.setSelected(false);
                binding.noDeal.setSelected(true);
                condition.setDisabled(true);
                break;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.noLimit:
                case R.id.deal:
                case R.id.noDeal:
                    setSelected(v.getId());
                    break;
                case R.id.startDate://开始日期
                    selectType = 0;
                    showTimeDialog(getString(R.string.start_time), DateUtil.dateFormatMis(condition.getStartCreateTime()));
                    break;
                case R.id.endDate://结束日期
                    selectType = 1;
                    showTimeDialog(getString(R.string.end_time), DateUtil.dateFormatMis(condition.getEndCreateTime()));
                    break;
                case R.id.operatePerson://布控人员
                    if (personList != null && personList.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.BUNDLE_USER, personList);
                        ActivityLaunchUtil.launchContainerActivity(getActivity(), PersonSearchFragment.class.getCanonicalName(), bundle);
                    } else {
                        showLoadingDialog("数据加载中");
                        userViewModel.loadAllPerson();
                    }
                    break;
                case R.id.startSearch:
                    loadData();
                    break;

            }

        }
    };

    /**
     * 弹窗选择时间段
     *
     * @param title
     * @param date
     */
    private void showTimeDialog(String title, Date date) {
        final LinearLayout contentView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.time_wheel_layout, null);
        wheelMain = new WheelMain(contentView, 2070, false);
        ScreenInfo screenInfo = new ScreenInfo(getActivity());
        wheelMain.screenheight = screenInfo.getHeight();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        wheelMain.initDateTimePicker(year, month, day, hour, min);
        MaterialDialogUtils.getCustomDialog(getActivity(), contentView, title, getString(R.string.ok), getString(R.string.cancel))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (selectType == 0) {
                            if (DateUtil.compareDate(condition.getEndCreateTime(), wheelMain.getTime() + " 00:00:00", "yyyy-MM-dd HH:mm:ss") < 0) {
                                ToastUtil.showNormal("起始时段不能大于结束时段");
                            } else {
                                condition.setStartCreateTime(wheelMain.getTime() + " 00:00:00");
                                updateTimeTextViewValue();
                                dialog.dismiss();
                            }
                        } else {
                            if (DateUtil.compareDate(wheelMain.getTime() + " 23:59:59", condition.getStartCreateTime(), "yyyy-MM-dd HH:mm:ss") < 0) {
                                ToastUtil.showNormal("结束时段不能小于起始时段");
                            } else {
                                condition.setEndCreateTime(wheelMain.getTime() + " 23:59:59");
                                updateTimeTextViewValue();
                                dialog.dismiss();
                            }
                        }
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        }).autoDismiss(false).show();
    }

    private void updateTimeTextViewValue() {
        binding.startDate.setText(DateUtil.dateFormat(DateUtil.dateFormatMis(condition.getStartCreateTime())));
        binding.endDate.setText(DateUtil.dateFormat(DateUtil.dateFormatMis(condition.getEndCreateTime())));
    }

    private void loadData() {
        showLoadingDialog();
        if (!TextUtils.isEmpty(binding.plateEdit.getText())) {
            condition.setPlate(binding.plateEdit.getText().toString());
        }else{
            condition.setPlate(null);
        }
        if (selectPerson != null) {
            condition.setCreator(selectPerson.getName());
        }else{
            condition.setCreator(null);
        }
        Paging paging = new Paging();
        paging.setPageIndex(1);
        queryModel.setPaging(paging);
        viewModel.loadDispositionList(queryModel);

    }

    private void updateDispositionListData(Lcee<BaseResponse<List<DispositionInfo>>> response) {
        dismissLoadingDialog();
        switch (response.status) {
            case Error:
                ToastUtil.showError(response.error.getMessage());
                break;
            case Content:
                if (response.data.getResult().size() > 0) {
                    ArrayList<DispositionInfo> result = new ArrayList<>();
                    result.addAll(response.data.getResult());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_QUERY_MODEL, queryModel);
                    bundle.putSerializable(Constants.BUNDLE_DISPOSITION, result);
                    ActivityLaunchUtil.launchContainerActivity(getActivity(), DispositionListFragment.class.getCanonicalName(), bundle);

                } else {
                    ToastUtil.showNormal("未搜索到结果，更换条件试试");
                }
                break;
        }
    }
}
