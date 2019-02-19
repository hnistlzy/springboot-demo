package com.keton.server.service;

import com.kenton.model.entity.Appendix;
import com.kenton.model.mapper.AppendixMapper;
import com.keton.server.request.AppendixDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AppendixService {
    private  static  final Logger log= LoggerFactory.getLogger(AppendixService.class);
    private static  final String prefix="/appendix";
    @Autowired
    private Environment env;
    @Autowired
    private AppendixMapper mapper;
    public String fileUpload(MultipartFile file, AppendixDto dto)throws  Exception{
        String location=null;
        String uploadFilename = file.getOriginalFilename();
        String suffix = StringUtils.substring(uploadFilename, StringUtils.indexOf(uploadFilename, "."));
        //TODO:构建文件保存路径,形式：根目录/moduleType/日期
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        String fileSaveUrl=env.getProperty("spring.servlet.multipart.location") + File.separator + dto.getModuleType() + File.separator + dataFormat.format(new Date());
        File fileSaveDirectory= new File(fileSaveUrl);
        if(!fileSaveDirectory.exists()){
            //此处是创建多级目录，所以要采用file.mkdirs(),而非file.mkdir()
            boolean isCreat = fileSaveDirectory.mkdirs();
            log.info("文件保存路径创建状态:{}",isCreat);
            log.info("文件路径地址:{}",fileSaveUrl);
        }
        //TODO:保存用户所上传的文件，文件名称：时间戳+suffix
        SimpleDateFormat destFileNameDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String destFileUrl=destFileNameDate.format(new Date()) + suffix;
        File destFile = new File(fileSaveUrl + File.separator + destFileUrl);
        file.transferTo(destFile);

        location=File.separator+dto.getModuleType() + File.separator + dataFormat.format(new Date())+destFileUrl;
        log.info("文件最终的保存路径为:{}",location);
        return  location;
    }

    /**
     * 将moduleType,location,recordId,createTime,文件名保存到数据库中
     * @param multipartFile 用于获取文件名
     * @param appendixDto 用于装载moduleType,location,recordId等信息
     * @return 是否保存成功
     */
    public Boolean saveFileUploadRecord(MultipartFile multipartFile, AppendixDto appendixDto) throws Exception {
        boolean isSaveSucc=false;
        //TODO:获取当前Date类型时间
        SimpleDateFormat createTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String createTimeStr = createTimeFormat.format(new Date());
        Date createTime = createTimeFormat.parse(createTimeStr);

        //TODO:获取文件名
        String fileName = multipartFile.getOriginalFilename();
        Appendix appendix = new Appendix();
        BeanUtils.copyProperties(appendixDto,appendix);
        appendix.setName(fileName);
        appendix.setCreateTime(createTime);
        log.info("appendix:{}",appendix);
        int save = mapper.insert(appendix);
        if(save>=1){
            isSaveSucc=true;
        }
        log.info("文件记录保存状态:{}",isSaveSucc);
        return  isSaveSucc;
    }
}
