package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.*;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.attr.AttrValueVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.atguigu.lease.web.admin.vo.room.RoomDetailVo;
import com.atguigu.lease.web.admin.vo.room.RoomItemVo;
import com.atguigu.lease.web.admin.vo.room.RoomQueryVo;
import com.atguigu.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo> implements RoomInfoService {

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private RoomAttrValueService roomAttrValueService;

    @Autowired
    private RoomFacilityService roomFacilityService;

    @Autowired
    private RoomLabelService roomLabelService;

    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;

    @Autowired
    private RoomLeaseTermService roomLeaseTermService;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;

    @Override
    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo) {
        boolean isUpdate = roomSubmitVo.getId() != null;
        super.saveOrUpdate(roomSubmitVo);
        if (isUpdate) {
            // 1.删除图片列表
            LambdaQueryWrapper<GraphInfo> graphInfoWrapper = new LambdaQueryWrapper<>();
            graphInfoWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
            graphInfoWrapper.eq(GraphInfo::getItemId, roomSubmitVo.getId());
            graphInfoService.remove(graphInfoWrapper);
            // 2.删除属性信息列表
            LambdaQueryWrapper<RoomAttrValue> roomAttrValueWrapper = new LambdaQueryWrapper<>();
            roomAttrValueWrapper.eq(RoomAttrValue::getRoomId, roomSubmitVo.getId());
            roomAttrValueService.remove(roomAttrValueWrapper);
            // 3.删除配套信息列表
            LambdaQueryWrapper<RoomFacility> roomFacilityWrapper = new LambdaQueryWrapper<>();
            roomFacilityWrapper.eq(RoomFacility::getRoomId, roomSubmitVo.getId());
            roomFacilityService.remove(roomFacilityWrapper);
            // 4.删除标签信息列表
            LambdaQueryWrapper<RoomLabel> roomLabelWrapper = new LambdaQueryWrapper<>();
            roomLabelWrapper.eq(RoomLabel::getRoomId, roomSubmitVo.getId());
            roomLabelService.remove(roomLabelWrapper);
            // 5.删除支付方式列表
            LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeWrapper = new LambdaQueryWrapper<>();
            roomPaymentTypeWrapper.eq(RoomPaymentType::getRoomId, roomSubmitVo.getId());
            roomPaymentTypeService.remove(roomPaymentTypeWrapper);
            // 6.删除可选租期列表
            LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermWrapper = new LambdaQueryWrapper<>();
            roomLeaseTermWrapper.eq(RoomLeaseTerm::getRoomId, roomSubmitVo.getId());
            roomLeaseTermService.remove(roomLeaseTermWrapper);
        }
        // 1.插入图片列表
        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)) {
            List<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                BeanUtils.copyProperties(graphVo, graphInfo);
                graphInfo.setItemId(roomSubmitVo.getId());
                graphInfo.setItemType(ItemType.ROOM);
                graphInfoList.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfoList);
        }
        // 2.插入属性信息列表
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
        if (!CollectionUtils.isEmpty(attrValueIds)) {
            List<RoomAttrValue> roomAttrValueList = new ArrayList<>();
            for (Long attrValueId : attrValueIds) {
                RoomAttrValue roomAttrValue = RoomAttrValue.builder().roomId(roomSubmitVo.getId()).attrValueId(attrValueId).build();
                roomAttrValueList.add(roomAttrValue);
            }
            roomAttrValueService.saveBatch(roomAttrValueList);
        }
        // 3.插入配套信息列表
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIds)) {
            List<RoomFacility> roomFacilityList = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                RoomFacility roomFacility = RoomFacility.builder().roomId(roomSubmitVo.getId()).facilityId(facilityInfoId).build();
                roomFacilityList.add(roomFacility);
            }
            roomFacilityService.saveBatch(roomFacilityList);
        }
        // 4.插入标签信息列表
        List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
        if (!CollectionUtils.isEmpty(labelInfoIds)) {
            List<RoomLabel> roomLabelList = new ArrayList<>();
            for (Long labelInfoId : labelInfoIds) {
                RoomLabel roomLabel = RoomLabel.builder().roomId(roomSubmitVo.getId()).labelId(labelInfoId).build();
                roomLabelList.add(roomLabel);
            }
            roomLabelService.saveBatch(roomLabelList);
        }
        // 5.插入支付方式列表
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
        if (!CollectionUtils.isEmpty(paymentTypeIds)) {
            List<RoomPaymentType> roomPaymentTypeList = new ArrayList<>();
            for (Long paymentTypeId : paymentTypeIds) {
                RoomPaymentType roomPaymentType = RoomPaymentType.builder().roomId(roomSubmitVo.getId()).paymentTypeId(paymentTypeId).build();
                roomPaymentTypeList.add(roomPaymentType);
            }
            roomPaymentTypeService.saveBatch(roomPaymentTypeList);
        }
        // 6.插入可选租期列表
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
        if (!CollectionUtils.isEmpty(leaseTermIds)) {
            List<RoomLeaseTerm> roomLeaseTermList = new ArrayList<>();
            for (Long leaseTermId : leaseTermIds) {
                RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder().roomId(roomSubmitVo.getId()).leaseTermId(leaseTermId).build();
                roomLeaseTermList.add(roomLeaseTerm);
            }
            roomLeaseTermService.saveBatch(roomLeaseTermList);
        }
    }

    @Override
    public IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageItem(page, queryVo);
    }

    @Override
    public RoomDetailVo getRoomDetailById(Long id) {
        // 1.查询RoomInfo
        RoomInfo roomInfo = roomInfoMapper.selectById(id);
        // 2.查询所属公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(roomInfo.getApartmentId());
        // 3.查询图片列表
        List<GraphVo> graphVoList = graphInfoMapper.selectByItemTypeAndId(ItemType.ROOM, id);
        // 4.查询属性信息列表
        List<AttrValueVo> attrValueList = attrValueMapper.selectByRoomId(id);
        // 5.查询配套信息列表
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectByRoomId(id);
        // 6.查询标签信息列表
        List<LabelInfo> labelInfoList = labelInfoMapper.selectByRoomId(id);
        // 7.查询支付方式列表
        List<PaymentType> paymentTypeList = paymentTypeMapper.selectByRoomId(id);
        // 8.查询可选租期列表
        List<LeaseTerm> leaseTermList = leaseTermMapper.selectByRoomId(id);
        RoomDetailVo roomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo, roomDetailVo);
        roomDetailVo.setApartmentInfo(apartmentInfo);
        roomDetailVo.setGraphVoList(graphVoList);
        roomDetailVo.setAttrValueVoList(attrValueList);
        roomDetailVo.setFacilityInfoList(facilityInfoList);
        roomDetailVo.setLabelInfoList(labelInfoList);
        roomDetailVo.setPaymentTypeList(paymentTypeList);
        roomDetailVo.setLeaseTermList(leaseTermList);
        return roomDetailVo;
    }

    @Override
    public void removeByRoomId(Long id) {
        // 1.删除RoomInfo
        super.removeById(id);
        // 2.删除图片列表
        LambdaQueryWrapper<GraphInfo> graphInfoWrapper = new LambdaQueryWrapper<>();
        graphInfoWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
        graphInfoWrapper.eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphInfoWrapper);
        // 3.删除属性信息列表
        LambdaQueryWrapper<RoomAttrValue> roomAttrValueWrapper = new LambdaQueryWrapper<>();
        roomAttrValueWrapper.eq(RoomAttrValue::getRoomId, id);
        roomAttrValueService.remove(roomAttrValueWrapper);
        // 4.删除配套信息列表
        LambdaQueryWrapper<RoomFacility> roomFacilityWrapper = new LambdaQueryWrapper<>();
        roomFacilityWrapper.eq(RoomFacility::getRoomId, id);
        roomFacilityService.remove(roomFacilityWrapper);
        // 5.删除标签信息列表
        LambdaQueryWrapper<RoomLabel> roomLabelWrapper = new LambdaQueryWrapper<>();
        roomLabelWrapper.eq(RoomLabel::getRoomId, id);
        roomLabelService.remove(roomLabelWrapper);
        // 6.删除支付方式列表
        LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeWrapper = new LambdaQueryWrapper<>();
        roomPaymentTypeWrapper.eq(RoomPaymentType::getRoomId, id);
        roomPaymentTypeService.remove(roomPaymentTypeWrapper);
        // 7.删除可选租期列表
        LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermWrapper = new LambdaQueryWrapper<>();
        roomLeaseTermWrapper.eq(RoomLeaseTerm::getRoomId, id);
        roomLeaseTermService.remove(roomLeaseTermWrapper);
    }
}




