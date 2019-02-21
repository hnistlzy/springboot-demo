package com.keton.server.controller;

import com.kenton.model.mapper.OrderRecordMapper;
import com.keton.server.enums.StatusCode;
import com.keton.server.request.OrderRecordInsertUpdateDTO;
import com.keton.server.request.ValidateOrderRecordDTO;
import com.keton.server.response.BaseResponse;
import com.keton.server.service.OrderRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 之前的OrderRecordController中没有对接收到的数据进行合法性校验。
 * 这里增加合法性校验的例子
 * @author KentonLee
 * @date 2019/2/21
 */
@RestController
public class ValidateController {
    @Autowired
    private OrderRecordMapper orderRecordMapper;
    private static final Logger log = LoggerFactory.getLogger(OrderRecordController.class);
    private static final String prefix="/validate";
    @Autowired
    private OrderRecordService service;

    @RequestMapping(value = prefix+"/insert",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse insertOrder(@RequestBody @Validated ValidateOrderRecordDTO dto, BindingResult result){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            if(result.hasErrors()){
                response =new BaseResponse(StatusCode.Invalid_Pram);
            }
            log.info("传进来的内容为："+dto.toString());
            service.insertOrder(dto);
        } catch (Exception e){
            log.error("插入发生了异常："+e.getMessage());
            response= new BaseResponse(StatusCode.Fail, e.getMessage());
        }
        return response;
    }
}
