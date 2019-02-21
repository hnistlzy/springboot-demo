package com.keton.server.scheduled;

import com.kenton.model.entity.Appendix;
import com.kenton.model.entity.OrderRecord;
import com.kenton.model.mapper.AppendixMapper;
import com.kenton.model.mapper.OrderRecordMapper;
import com.keton.server.request.SendMailDto;
import com.keton.server.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
@Component
//@EnableScheduling
public class ScheduledMail {
    @Autowired
    private AppendixMapper appendixMapper;
    @Autowired
    private OrderRecordMapper orderRecordMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private Environment environment;
    private static final Integer id=9;
//  @Scheduled(cron = "${mail.send.schedule}")
    public void sendMailByDay(){
        //查询id=9的订单，并将订单信息(订单号，订单类型)，与订单相关的文件添加到邮件中去
        try{
            OrderRecord orderRecord = orderRecordMapper.selectByPrimaryKey(id);
            List<Appendix> appendices = appendixMapper.selectByRecordIdModuleType(id, orderRecord.getOrderType());

            Map<String,Object> contextMap=new HashMap<>();
            contextMap.put("orderNo",orderRecord.getOrderNo());
            contextMap.put("orderType",orderRecord.getOrderType());

            Map<String,String > nameLocationMap=new HashMap<>();

            List<String> nameList=new ArrayList<>();
            for(Appendix appendix :appendices){
                String name = appendix.getName();
                String location = environment.getProperty("spring.servlet.multipart.location")+ File.separator+appendix.getLocation();
                nameLocationMap.put(name,location);
                nameList.add(name);
            }
            contextMap.put("nameList",nameList);
            SendMailDto sendMailDto = new SendMailDto();

            sendMailDto.setTo("952737510@qq.com,3121992374@qq.com");
            sendMailDto.setSubject("测试定时邮件发送2");

            mailService.sendMail(sendMailDto,contextMap,nameLocationMap,"mailSender2");
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
