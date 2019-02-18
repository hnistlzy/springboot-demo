package com.keton.server.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class MailController {
    private  static  final Logger log= LoggerFactory.getLogger(MailController.class);
    private static  final String prefix="/mail";
    @Autowired
    private MailService mailService;
    @Autowired
    private Environment environment;
    /**
     * 发送简单文本邮件
     * @param dto 收件人，标题，内容等信息
     * @param bindingResult  校验器
     * @return baseResponse
     */
    @RequestMapping(value = prefix+"/textMail",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendTextMail(@RequestBody @Validated SendMailDto dto, BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            if(bindingResult.hasErrors()){
                return errorResponse(bindingResult);
            }
            log.info("接受到数据:"+dto.toString());
            mailService.sendTextEmail(dto);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail,e.getMessage());
            e.fillInStackTrace();
        }
        return  baseResponse;
    }
    /**
     * 发送带附件的邮件
     * @param dto 收件人，标题，内容等信息
     * @param bindingResult  校验器
     * @return baseResponse
     */
    @RequestMapping(value = prefix+"/fileMail",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendFileMail(@RequestBody @Validated SendMailDto dto, BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            if(bindingResult.hasErrors()){
                return errorResponse(bindingResult);
            }
            log.info("前端数据:"+dto.toString());
            mailService.sendFileEmail(dto);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail,e.getMessage());
            e.fillInStackTrace();
        }
        return  baseResponse;
    }
    /**
     * 发送基于模板引擎的邮件
     * @param dto 收件人，标题，内容等信息
     * @param bindingResult  校验器
     * @return baseResponse
     */
    @RequestMapping(value = prefix+"/templateMail",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse sendTemplateMail(@RequestBody @Validated SendMailDto dto, BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            if(bindingResult.hasErrors()){
                return errorResponse(bindingResult);
            }
            log.info("接受到数据:"+dto.toString());
            //下面的数据除了用户名外，都应从配置文件中取得
            //用户名应从session，或者是其他数据源取得。
            HashMap<String, Object> map = new HashMap<>();
            map.put("user","dkf");
            map.put("web",environment.getProperty("send.mail.web"));
            map.put("company",environment.getProperty("send.mail.company"));
            map.put("product",environment.getProperty("send.mail.product"));
            mailService.sendTemplateEmail(dto,map);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail,e.getMessage());
            e.fillInStackTrace();
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
