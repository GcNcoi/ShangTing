package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.AttrValue;
import com.atguigu.lease.web.admin.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_value(房间基本属性值表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.atguigu.lease.model.AttrValue
*/
@Mapper
public interface AttrValueMapper extends BaseMapper<AttrValue> {

    List<AttrValueVo> selectByRoomId(Long id);
}




