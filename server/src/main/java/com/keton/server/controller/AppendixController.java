package com.keton.server.controller;

import com.kenton.model.entity.Appendix;
import com.kenton.model.mapper.AppendixMapper;
import com.keton.server.enums.StatusCode;
import com.keton.server.request.AppendixDto;
import com.keton.server.request.AppendixRequest;
import com.keton.server.response.BaseResponse;
import com.keton.server.service.AppendixService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AppendixController {
    private static final Logger log = LoggerFactory.getLogger(AppendixController.class);
    private static final String prefix = "/appendix";
    @Autowired
    private AppendixService service;
    @Autowired
    private AppendixMapper mapper;
    @Autowired
    private Environment environment;
    /**
     * 文件上传功能
     * @param request MultipartHttpServletRequest
     * @return BaseResponse
     */
    @RequestMapping(value = prefix + "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse fileUpload(MultipartHttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            String moduleType = request.getParameter("moduleType");
            if (moduleType == null || moduleType.equals("")) {
                baseResponse = new BaseResponse(StatusCode.Invalid_Pram);
                return baseResponse;
            }
            //TODO:此处应该使用request.getFiles()以保证可以上传多个文件
            MultipartFile multipartFile = request.getFile("appendix");
            if (multipartFile == null) {
                baseResponse = new BaseResponse(StatusCode.Invalid_Pram);
                return baseResponse;
            }
            String recordId = request.getParameter("recordId");

            if (recordId == null || recordId.equals("")) {
                baseResponse = new BaseResponse(StatusCode.Invalid_Pram);
                return baseResponse;
            }
            AppendixDto appendixDto = new AppendixDto();
            appendixDto.setModuleType(moduleType);
            appendixDto.setRecordId(Integer.parseInt(recordId));
            //TODO:保存文件
            String location = service.fileUpload(multipartFile, appendixDto);
            //TODO:保存上传文件记录
            appendixDto.setLocation(location);
            Integer id = service.saveFileUploadRecord(multipartFile, appendixDto);
            baseResponse.setData(id);
        } catch (Exception e) {
            log.error("文件上传失败：{}", e);
            baseResponse = new BaseResponse(StatusCode.FileUploadFail);
            e.printStackTrace();
        }

        return baseResponse;
    }

    /**
     * 更新附件所属的模块
     *
     * @param appendixRequest appendixRequest
     * @return baseResponse
     */
    @RequestMapping(value = prefix + "/updateRecord", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse updateRecord(@RequestBody @Validated AppendixRequest appendixRequest, BindingResult bindingResult) {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            if (bindingResult.hasErrors()) {
                List<ObjectError> list = new ArrayList<>();
                for (ObjectError allError : bindingResult.getAllErrors()) {
                    list.add(allError);
                }
                baseResponse = new BaseResponse(list, StatusCode.Invalid_Pram);
                return baseResponse;
            }
            @NotBlank String appendixIdsStr = appendixRequest.getAppendixIds();
            String[] appendixIds = StringUtils.split(appendixIdsStr, ",");
            Appendix appendix;
            for (String appendixId : appendixIds) {
                try{
                    appendix = mapper.selectByPrimaryKey(Integer.parseInt(appendixId));
                    log.info("查询到的对象为:{}",appendix);
                    if (appendix != null) {
                        appendix.setRecordId(appendixRequest.getRecordId());
                        mapper.updateRecordId(appendix);
                    }
                }catch (Exception e){
                    log.error("更新附件所属模块发生异常，当前附件id:{}",appendixId);
                    e.printStackTrace();
                    return new BaseResponse(StatusCode.Fail);
                }
            }
        } catch (Exception e) {
            log.error("发生未知异常"+e);
            baseResponse = new BaseResponse(StatusCode.Fail);
            e.printStackTrace();
        }
        return baseResponse;
    }
    @RequestMapping(value =prefix+"/fileDownload/{id}",method = RequestMethod.GET)
    public   String fileDownload(@PathVariable Integer id, HttpServletResponse response){

        if(id==null ||id<0){
            return null;
        }
        try{
            Appendix appendix = mapper.selectByPrimaryKey(id);
            if(appendix!=null){
                String location = environment.getProperty("spring.servlet.multipart.location")+appendix.getLocation();
                String fileName = appendix.getName();
                log.info("获取到的文件路径为:"+fileName);
                service.fileDownload(location ,response,fileName);
            }

        }catch (Exception e){
            log.error("文件下载发生错误,{}"+e);
            e.printStackTrace();
        }

        return  null;
    }

}
