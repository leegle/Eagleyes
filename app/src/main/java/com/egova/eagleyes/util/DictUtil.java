package com.egova.eagleyes.util;


import com.egova.baselibrary.model.RegionInfo;
import com.egova.eagleyes.helper.DictData;
import com.egova.eagleyes.model.respose.Tollgate;
import com.egova.eagleyes.model.respose.TollgateType;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典工具类
 */
public class DictUtil {

    /**
     * 给省市区数据加上不限
     *
     * @return
     */
    public static RegionInfo buildRegionAddNoLimit(RegionInfo regionInfo) {
        if (regionInfo.getParentId().equals("-1")) {
            ArrayList<RegionInfo> firstChildren = regionInfo.getChildren();
            int size = firstChildren.size();
            for (int i = 0; i < size; i++) {
                ArrayList<RegionInfo> secondChildren = firstChildren.get(i).getChildren();
                if (secondChildren != null) {
                    secondChildren.add(0, buildNoLimitBean(firstChildren.get(i).getId(), firstChildren.get(i).getName()));
                }
            }
            RegionInfo firstNoLimit = buildNoLimitBean(regionInfo.getId(), regionInfo.getName());
            ArrayList<RegionInfo> firstNoLimitChildren = new ArrayList<>();
            firstNoLimitChildren.add(buildNoLimitBean(regionInfo.getId(), "不限"));
            firstNoLimit.setChildren(firstNoLimitChildren);
            firstChildren.add(0, firstNoLimit);
        }
        return regionInfo;
    }

    //创建一个不限的省市区数据
    public static RegionInfo buildNoLimitBean(String id, String parentName) {
        RegionInfo regionInfo = new RegionInfo();
        regionInfo.setId(id);
        regionInfo.setParentId(id);
        regionInfo.setParentName(parentName);
        regionInfo.setName("不限");
        return regionInfo;
    }

    //创建一个 不限的卡口类型数据
    public static TollgateType buildNoLimitTollgateType() {
        TollgateType tollgateType = new TollgateType();
        tollgateType.setText("不限");
        tollgateType.setId("-1");
        return tollgateType;
    }

    //报警管理--判断某个卡口类型是否被选择
    public static boolean isTollgateSelectedByType(String tollgateTypeId) {
        int size = DictData.getInstance().getTollgateTypesList().size();
        for (int i = 0; i < size; i++) {
            if (tollgateTypeId.equals(DictData.getInstance().getTollgateTypesList().get(i).getValue())) {
                return DictData.getInstance().getTollgateTypesList().get(i).isHasPoliceAlertSelected();
            }
        }
        return false;
    }

    //报警管理---选择的区域，选择的卡口类型两个条件进行过滤卡口数据
    public static void buildTollgateData(List<Tollgate> tollgateList, RegionInfo regionInfo, boolean isFilterSelected) {
        if (DictData.getInstance().getTollgateList() == null) {
            return;
        }
        int size = DictData.getInstance().getTollgateList().size();
        for (int i = 0; i < size; i++) {
            Tollgate item = DictData.getInstance().getTollgateList().get(i);
            if (!isFilterSelected || (isFilterSelected && item.isHasPoliceAlertSelected())) {
                if (isTollgateSelectedByType(item.getTollgateType())) {//该卡口是在选择的卡口类型中
                    //在选择的区域中
                    if (regionInfo != null && !regionInfo.getId().endsWith("0000")) {
                        if (regionInfo.getId().endsWith("00")) {//选择的是某个市下面所有的区域
                            if (item.getRegionId().startsWith(regionInfo.getId().substring(0, 4))) {
                                tollgateList.add(item);
                            }
                        } else {
                            if (item.getRegionId().equals(regionInfo.getId())) {
                                tollgateList.add(item);
                            }
                        }
                    } else {
                        //全选
                        tollgateList.add(item);
                    }
                }
            }
        }
    }
}
