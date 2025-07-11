package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.ApartmentInfoMapper;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private ApartmentLabelService apartmentLabelService;

    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate = apartmentSubmitVo.getId() != null;
        super.saveOrUpdate(apartmentSubmitVo);
        if (isUpdate) {
            // 1.删除图片列表
            LambdaQueryWrapper<GraphInfo> graphInfoWrapper = new LambdaQueryWrapper<>();
            graphInfoWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            graphInfoWrapper.eq(GraphInfo::getItemId, apartmentSubmitVo.getId());
            graphInfoService.remove(graphInfoWrapper);
            // 2.删除配套列表
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityWrapper = new LambdaQueryWrapper<>();
            apartmentFacilityWrapper.eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentFacilityWrapper);
            // 3.删除标签列表
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelWrapper = new LambdaQueryWrapper<>();
            apartmentLabelWrapper.eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelWrapper);
            // 4.删除杂费列表
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueWrapper = new LambdaQueryWrapper<>();
            apartmentFeeValueWrapper.eq(ApartmentFeeValue::getApartmentId, apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeValueWrapper);
        }
        // 1.插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if (!graphVoList.isEmpty()) {
            List<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfoService.save(graphInfo);
                graphInfoList.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfoList);
        }
        // 2.插入配套列表
        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
        if (!facilityInfoIds.isEmpty()) {
            List<ApartmentFacility> apartmentFacilityList = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                ApartmentFacility apartmentFacility = ApartmentFacility.builder().apartmentId(apartmentSubmitVo.getId()).facilityId(facilityInfoId).build();
                apartmentFacilityList.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(apartmentFacilityList);

        }
        // 3.插入标签列表
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if (!labelIds.isEmpty()) {
            List<ApartmentLabel> apartmentLabelList = new ArrayList<>();
            for (Long labelId : labelIds) {
                ApartmentLabel apartmentLabel = ApartmentLabel.builder().apartmentId(apartmentSubmitVo.getId()).labelId(labelId).build();
                apartmentLabelList.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabelList);
        }
        // 4.插入杂费列表
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!feeValueIds.isEmpty()) {
            List<ApartmentFeeValue> apartmentFeeValueList = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue apartmentFeeValue = ApartmentFeeValue.builder().apartmentId(apartmentSubmitVo.getId()).feeValueId(feeValueId).build();
                apartmentFeeValueList.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValueList);
        }
    }

    @Override
    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo) {
        return apartmentInfoMapper.pageItem(page, queryVo);
    }
}




