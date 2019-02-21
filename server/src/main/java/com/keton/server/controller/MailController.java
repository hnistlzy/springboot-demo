package com.keton.server.controller;

import com.kenton.model.entity.Appendix;
import com.kenton.model.mapper.AppendixMapper;
import com.keton.server.enums.StatusCode;
import com.keton.server.request.SendMailDto;
import com.keton.server.response.BaseResponse;
import com.keton.server.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
@RestController
public class MailController {
    private  static  final Logger log= LoggerFactory.getLogger(MailController.class);
    private static  final String prefix="/mail";
    @Autowired
    private MailService mailService;
    @Autowired
    private Environment environment;
    @Autowired
    private AppendixMapper appendixMapper;

    @GetMapping(value = prefix+"/sendMail",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendMail(@RequestBody @Validated SendMailDto dto,BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        if(bindingResult.hasErrors()){
            return errorResponse(bindingResult);
        }
        try{
            log.info("传进来的参数为：{}",dto);
            //TODO:接受前端传过来的收件箱，主题，内容等信息
            Integer recordId = dto.getRecordId();
            if(recordId==null || recordId<=0){
                return errorResponse(bindingResult);
            }
            List<Appendix> appendices = appendixMapper.selectByRecordId(recordId);
            if(appendices==null||appendices.isEmpty()){
                return  new BaseResponse(StatusCode.Fail);
            }
            Map<String,String> nameLocationMap=new HashMap<>();
            for(Appendix appendix:appendices){
                String location = environment.getProperty("spring.servlet.multipart.location")+ File.separator+appendix.getLocation();
                String name = appendix.getName();
                nameLocationMap.put(name,location);
            }
            //TODO:设置模板引擎需要的数据
            Map<String,Object> contextMap=new HashMap<>();
            contextMap.put("product",environment.getProperty("send.mail.product"));
            contextMap.put("web",environment.getProperty("send.mail.web"));
            contextMap.put("company",environment.getProperty("send.mail.company"));

            mailService.sendMail(dto,contextMap,nameLocationMap,"mailSender");
        }catch (Exception e){
            log.error("邮件发送失败,{}",e);
            e.printStackTrace();
            return  new BaseResponse(StatusCode.Fail);
        }
        return baseResponse;
    }

    @GetMapping(value = prefix+"/sendTextMail",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendTextMail(@RequestBody @Validated SendMailDto dto,BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        if(bindingResult.hasErrors()){
            return errorResponse(bindingResult);
        }
        try{
            mailService.sendMail(dto,null,null,null);
        }catch (Exception e){
            log.error("发送邮件发生错误，错误原因：{}",e.getMessage());
            e.printStackTrace();
            return  new BaseResponse(StatusCode.Fail);
        }
        return  baseResponse;
    }


    private BaseResponse errorResponse(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> errorMsg = new ArrayList<>();
        for (ObjectError obj : allErrors) {
            String defaultMessage = obj.getDefaultMessage();
            errorMsg.add(defaultMessage);
        }
        return new BaseResponse(StatusCode.Invalid_Pram,errorMsg);
    }


}
