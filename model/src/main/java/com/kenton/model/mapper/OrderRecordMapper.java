package com.kenton.model.mapper;


import com.kenton.model.entity.OrderRecord;

import java.util.List;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
public interface OrderRecordMapper {
     /**
      * selectByPrimaryKey
      * @param id id
      * @return selectByPrimaryKey
      */
     OrderRecord selectByPrimaryKey(Integer id);

     /**
      * getAllOrderRecord
      * @return  List<OrderRecord>
      */
     List<OrderRecord> getAllOrderRecord();

     /**
      *  insertOrderRecord
      * @param orderRecord orderRecord
      */
     void insertOrderRecord(OrderRecord orderRecord);
}