package com.egova.baselibrary.widget.wheelregion;

import android.view.View;

import com.egova.baselibrary.R;
import com.egova.baselibrary.model.RegionInfo;
import com.egova.baselibrary.widget.wheeltime.OnWheelChangedListener;
import com.egova.baselibrary.widget.wheeltime.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 省市区联动控件
 */
public class WheelRegion {
    private View view;
    private WheelView wv_province;//省
    private WheelView wv_city; //市
    private WheelView wv_district;//区
    public int screenHeight;
    private String levelType;
    private List<RegionInfo> regionInfoList;
    public static final String level1 = "1";//二级数据 省-市
    public static final String level2 = "2";//二级数据 市-区
    public static final String level3 = "3";//三级数据 省-市-区

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelRegion(View view, String levelType) {
        this.view = view;
        this.levelType = levelType;
    }

    public void initRegionPicker(List<RegionInfo> regionInfo) {
        if (level1.equalsIgnoreCase(levelType)) {
            initLevel1(regionInfo);
        } else if (level2.equalsIgnoreCase(levelType)) {
            initLevel2(regionInfo);
        } else if (level3.equalsIgnoreCase(levelType)) {
            initLevel3(regionInfo);
        }
        this.regionInfoList = regionInfo;

    }

    //省-市
    private void initLevel1(final List<RegionInfo> regionInfo) {
        wv_province = (WheelView) view.findViewById(R.id.province);
        wv_city = (WheelView) view.findViewById(R.id.city);

        //省
        wv_province.setAdapter(new ListWheelAdapter<>(regionInfo, regionInfo.size()));
        wv_province.setLabel("");
        wv_province.setCyclic(false);
        wv_province.setCurrentItem(0);

        //市
        ArrayList<RegionInfo> cityItems = regionInfo.get(0).getChildren();
        wv_city.setAdapter(new ListWheelAdapter<>(cityItems, cityItems.size()));
        wv_city.setLabel("");
        wv_city.setCyclic(false);
        wv_city.setCurrentItem(0);

        //省监听
        OnWheelChangedListener wheelChangedListener_province = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                ArrayList<RegionInfo> cityItems = regionInfo.get(newValue).getChildren();
                wv_city.setAdapter(new ListWheelAdapter<>(cityItems, cityItems.size()));
                wv_city.setCurrentItem(0);
            }
        };
        wv_province.addChangingListener(wheelChangedListener_province);
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = (screenHeight / 100) * 3;
        wv_province.TEXT_SIZE = textSize;
        wv_city.TEXT_SIZE = textSize;
    }

    //市-区
    private void initLevel2(final List<RegionInfo> regionInfo) {
        wv_city = (WheelView) view.findViewById(R.id.city);
        wv_district = (WheelView) view.findViewById(R.id.district);

        wv_city = (WheelView) view.findViewById(R.id.city);
        wv_district = (WheelView) view.findViewById(R.id.district);

        //市
        wv_city.setAdapter(new ListWheelAdapter<>(regionInfo, regionInfo.size()));
        wv_city.setLabel("");
        wv_city.setCyclic(false);
        wv_city.setCurrentItem(0);

        //区
        ArrayList<RegionInfo> districtItems = regionInfo.get(0).getChildren();
        wv_district.setAdapter(new ListWheelAdapter<>(districtItems, districtItems.size()));
        wv_district.setLabel("");
        wv_district.setCyclic(false);
        wv_district.setCurrentItem(0);

        //省监听
        OnWheelChangedListener wheelChangedListener_province = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                ArrayList<RegionInfo> cityItems = regionInfo.get(newValue).getChildren();
                wv_district.setAdapter(new ListWheelAdapter<>(cityItems, cityItems.size()));
                wv_district.setCurrentItem(0);
            }
        };
        wv_city.addChangingListener(wheelChangedListener_province);
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = (screenHeight / 100) * 3;
        wv_city.TEXT_SIZE = textSize;
        wv_district.TEXT_SIZE = textSize;
    }

    //省-市-区
    private void initLevel3(final List<RegionInfo> regionInfo) {
        wv_province = (WheelView) view.findViewById(R.id.province);
        wv_city = (WheelView) view.findViewById(R.id.city);
        wv_district = (WheelView) view.findViewById(R.id.district);

        //省
        wv_province.setAdapter(new ListWheelAdapter<>(regionInfo, regionInfo.size()));
        wv_province.setLabel("");
        wv_province.setCyclic(false);
        wv_province.setCurrentItem(0);


        //市
        ArrayList<RegionInfo> cityItems = regionInfo.get(0).getChildren();
        wv_city.setAdapter(new ListWheelAdapter<>(cityItems, cityItems.size()));
        wv_city.setLabel("");
        wv_city.setCyclic(false);
        wv_city.setCurrentItem(0);

        //区
        ArrayList<RegionInfo> districtItems = cityItems.get(0).getChildren();
        wv_district.setAdapter(new ListWheelAdapter<>(districtItems, districtItems.size()));
        wv_district.setLabel("");
        wv_district.setCyclic(false);
        wv_district.setCurrentItem(0);

        //省监听
        OnWheelChangedListener wheelChangedListener_province = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                ArrayList<RegionInfo> cityItems = regionInfo.get(newValue).getChildren();
                wv_city.setAdapter(new ListWheelAdapter<>(cityItems, cityItems.size()));
                wv_city.setCurrentItem(0);
            }
        };
        //市监听
        OnWheelChangedListener wheelChangedListener_city = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                ArrayList<RegionInfo> provinceItems = regionInfo.get(wv_province.getCurrentItem()).getChildren();
                ArrayList<RegionInfo> cityItems = provinceItems.get(newValue).getChildren();
                wv_district.setAdapter(new ListWheelAdapter<>(cityItems, cityItems.size()));
                wv_district.setCurrentItem(0);
            }
        };
        wv_province.addChangingListener(wheelChangedListener_province);
        wv_city.addChangingListener(wheelChangedListener_city);
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = (screenHeight / 100) * 3;
        wv_province.TEXT_SIZE = textSize;
        wv_city.TEXT_SIZE = textSize;
        wv_district.TEXT_SIZE = textSize;
    }


    public RegionInfo getItem() {
        if (level1.equalsIgnoreCase(this.levelType)) {
            //省-市
            return this.regionInfoList.get(wv_province.getCurrentItem()).getChildren().get(wv_city.getCurrentItem());
        } else if (level2.equalsIgnoreCase(this.levelType)) {
            //市-区
            return this.regionInfoList.get(wv_city.getCurrentItem()).getChildren().get(wv_district.getCurrentItem());
        } else {
            //省-市-区
            return this.regionInfoList.get(wv_province.getCurrentItem()).getChildren().get(wv_city.getCurrentItem()).getChildren().get(wv_district.getCurrentItem());
        }
    }
}
