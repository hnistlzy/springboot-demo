package com.kenton.model.mapper;


import com.kenton.model.entity.OrderRecord;

public interface OrderRecordMapper {
     OrderRecord selectByPrimaryKey(Integer id);
}