package com.kenton.model.mapper;


import com.kenton.model.entity.OrderRecord;

import java.util.List;

public interface OrderRecordMapper {
     OrderRecord selectByPrimaryKey(Integer id);
     List<OrderRecord> getAllOrderRecord();
     void insertOrderRecord(OrderRecord orderRecord);
}