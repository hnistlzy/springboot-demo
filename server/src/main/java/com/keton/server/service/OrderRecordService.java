package com.keton.server.service;

import com.kenton.model.entity.OrderRecord;
import com.kenton.model.mapper.OrderRecordMapper;
import com.keton.server.request.OrderRecordInsertUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderRecordService {
    private static final Logger log= LoggerFactory.getLogger(OrderRecordService.class);
    @Autowired
    private OrderRecordMapper orderRecordMapper;

    public void insertOrder(OrderRecordInsertUpdateDTO dto){
        OrderRecord orderRecord = new OrderRecord();
        BeanUtils.copyProperties(dto, orderRecord);
        orderRecordMapper.insertOrderRecord(orderRecord);
    }
}