package com.keton.server.controller;

import com.kenton.model.entity.OrderRecord;
import com.kenton.model.mapper.OrderRecordMapper;
import com.keton.server.enums.StatusCode;

import com.keton.server.request.OrderRecordInsertUpdateDTO;
import com.keton.server.response.BaseResponse;
import com.keton.server.service.OrderRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderRecordController {
    @Autowired
    private OrderRecordMapper orderRecordMapper;
    private static final Logger log = LoggerFactory.getLogger(OrderRecordController.class);
    private static final String prefix="/order";
    @Autowired
    private OrderRecordService service;
//    rest风格api,前端访问路径为：localhost:8081/order/getOrder/1
    @RequestMapping(value = prefix+"/getOrder/{id}",method = RequestMethod.GET)
    public OrderRecord getOrder(@PathVariable Integer id){
        return orderRecordMapper.selectByPrimaryKey(id);
    }

    /**
     * 返回一个标准json数据
     * @param id orderID
     * @return baseResponse
     */
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

    /**
     * 返回标准数据的list
     * @return baseResponse
     */
    @RequestMapping(value = prefix+"/getList",method = RequestMethod.GET)
    public BaseResponse getList(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            List<OrderRecord> allOrderRecord = orderRecordMapper.getAllOrderRecord();
            log.info(allOrderRecord.toString());
            baseResponse.setData(allOrderRecord);
        }catch (Exception e){
            log.error("查询全部订单发生了异常："+e.getMessage());
            baseResponse= new BaseResponse(StatusCode.Fail, e.getMessage());
        }
        return  baseResponse;
    }

    /**
     * 返回标准格式的map
     * @return baseResponse
     */
    @RequestMapping(value = prefix+"/getMap2",method = RequestMethod.GET)
    public BaseResponse getMap(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            List<OrderRecord> allOrderRecord = orderRecordMapper.getAllOrderRecord();
            log.info(allOrderRecord.toString());
            Map<String,Object> map=new HashMap<>();
            map.put("dataList",allOrderRecord);
            baseResponse.setData(map);
        }catch (Exception e){
            log.error("查询全部订单发生了异常："+e.getMessage());
            baseResponse= new BaseResponse(StatusCode.Fail, e.getMessage());
        }
        return  baseResponse;
    }

    /**
     * 接受json格式数据，并将数据插入到DB中
     * @param dto dto
     * @return response
     */
    @RequestMapping(value = prefix+"/insert",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse insertOrder(@RequestBody  OrderRecordInsertUpdateDTO dto){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        log.info("传进来的内容为："+dto.toString());
        try{
            service.insertOrder(dto);
        }catch (Exception e){
            log.error("插入发生了异常："+e.getMessage());
            response= new BaseResponse(StatusCode.Fail, e.getMessage());
        }
        return response;
    }

}
