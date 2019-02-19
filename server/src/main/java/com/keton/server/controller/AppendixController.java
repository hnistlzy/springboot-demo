package com.keton.server.controller;

import com.keton.server.enums.StatusCode;
import com.keton.server.request.AppendixDto;
import com.keton.server.response.BaseResponse;
import com.keton.server.service.AppendixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class AppendixController {
    private  static  final Logger log= LoggerFactory.getLogger(AppendixController.class);
    private static  final String prefix="/appendix";
    @Autowired
    private AppendixService service;

    @RequestMapping(value = prefix+"/upload",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse fileUpload(MultipartHttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            String moduleType = request.getParameter("moduleType");
            if (moduleType==null || moduleType.equals("")) {
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

            if(recordId==null || recordId.equals("")){
                baseResponse = new BaseResponse(StatusCode.Invalid_Pram);
                return baseResponse;
            }
            AppendixDto appendixDto = new AppendixDto();
            appendixDto.setModuleType(moduleType);
            appendixDto.setRecordId(Integer.parseInt(recordId));
            //TODO:保存文件
            String location= service.fileUpload(multipartFile, appendixDto);
            //TODO:保存上传文件记录
            appendixDto.setLocation(location);
            Boolean isSaved = service.saveFileUploadRecord(multipartFile, appendixDto);
            if(!isSaved){
                log.error("文件记录保存失败！");
            }
        }catch (Exception e){
            log.error("文件上传失败：{}",e);
            baseResponse=new BaseResponse(StatusCode.FileUploadFail);
            e.printStackTrace();
        }

        return baseResponse;
    }
}
