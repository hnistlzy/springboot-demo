package com.keton.server.controller;

import com.kenton.model.entity.OrderRecord;
import com.kenton.model.mapper.OrderRecordMapper;
import com.keton.server.enums.StatusCode;
import com.keton.server.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRecordController {
    @Autowired
    private OrderRecordMapper orderRecordMapper;
    private static final Logger log = LoggerFactory.getLogger(OrderRecordController.class);
    private static final String prefix="/order";
//    rest风格api,前端访问路径为：localhost:8081/order/getOrder/1
    @RequestMapping(value = prefix+"/getOrder/{id}",method = RequestMethod.GET)
    public OrderRecord getOrder(@PathVariable Integer id){
        return orderRecordMapper.selectByPrimaryKey(id);
    }

    @RequestMapping(value = prefix+"/getOrder2/{id}",method = RequestMethod.GET)
    public BaseResponse getOrder2(@PathVariable Integer id){
        BaseResponse baseResponse =new BaseResponse(StatusCode.Success);
        try{
            OrderRecord orderRecord = orderRecordMapper.selectByPrimaryKey(id);
            baseResponse.setData(orderRecord);
        }catch (Exception e){
            log.error("发生了未知错误!");
            baseResponse=new BaseResponse(StatusCode.Fail,e.getMessage());
            e.fillInStackTrace();
        }
        return baseResponse;
    }


}
