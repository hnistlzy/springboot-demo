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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件上传、下载服务
 */
@Service
public class AppendixService {
    private  static  final Logger log= LoggerFactory.getLogger(AppendixService.class);
    private static  final String prefix="/appendix";
    @Autowired
    private Environment env;
    @Autowired
    private AppendixMapper mapper;
    public String fileUpload(MultipartFile file, AppendixDto dto)throws  Exception{
        String location;
        String uploadFilename = file.getOriginalFilename();
        String suffix = StringUtils.substring(uploadFilename, StringUtils.lastIndexOf(uploadFilename, "."));
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

        location=File.separator+dto.getModuleType() + File.separator + dataFormat.format(new Date())+File.separator+destFileUrl;
        log.info("文件最终的保存路径为:{}",location);
        return  location;
    }

    /**
     * 将moduleType,location,recordId,createTime,文件名保存到数据库中
     * @param multipartFile 用于获取文件名
     * @param appendixDto 用于装载moduleType,location,recordId等信息
     * @return 是否保存成功
     */
    public Integer saveFileUploadRecord(MultipartFile multipartFile, AppendixDto appendixDto) throws Exception {

        Appendix appendix = new Appendix();
        BeanUtils.copyProperties(appendixDto,appendix);

        //TODO:获取当前Date类型时间
        SimpleDateFormat updateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String  updateTimeStr =  updateTimeFormat.format(new Date());
        Date  updateTime =  updateTimeFormat.parse(updateTimeStr );
        //TODO:设置文件名、文件大小、文件上传时间
        appendix.setName(multipartFile.getOriginalFilename());
        appendix.setSize(multipartFile.getSize());
        appendix.setUpdateTime(updateTime);
         mapper.insertSelective(appendix);
        log.info("appendix:{}",appendix);

        return  appendix.getId();
    }

    /**
     *  文件下载:将location处的文件，传入response
     * @param location  文件位置
     * @param response response
     * @param fileName 文件的实际名称
     */
    public void fileDownload(String location, HttpServletResponse response, String fileName){
        InputStream inputStream=null;
        OutputStream outputStream;
        BufferedInputStream bufferedInputStream=null;
        BufferedOutputStream bufferedOutputStream;
        //TODO:完成文件下载功能
        try{
            //转化输入、输出流
            inputStream=  new FileInputStream(location);
            bufferedInputStream=new BufferedInputStream(inputStream);
            outputStream=response.getOutputStream();
            bufferedOutputStream=new BufferedOutputStream(outputStream);
            //application/octet-stream;表示为二进制数据，不知道明确类型

            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName .getBytes("utf-8"),"iso-8859-1"));

            byte [] buffer=new byte[1024];
            int len = bufferedInputStream.read(buffer);
            while(len!=-1){
                bufferedOutputStream.write(buffer,0,len);
                len=bufferedInputStream.read(buffer);
            }
            bufferedOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(bufferedInputStream!=null){
                try{
                    bufferedInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try{
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }


        }
    }
}
