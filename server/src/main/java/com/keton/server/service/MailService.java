package com.keton.server.service;

import com.keton.server.request.SendMailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
public class MailService {
    private static  final Logger log= LoggerFactory.getLogger(MailService.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Value(value = "${send.mail.from}")
    private String from;

    @Value(value = "send.mail.attachment.url")
    private String attachmentUrl;

    @Value(value = "send.mail.attachment")
    private String attachmentName;
    @Autowired
    private Environment environment;
    @Autowired
    private TemplateEngine templateEngine;
    /**
     * 发送简单文本文件
     * @param dto 邮件实体
     */
    public void sendTextEmail(SendMailDto dto){

        SimpleMailMessage mailMessage =new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(dto.getTo());
        mailMessage.setSubject(dto.getSubject());
        mailMessage.setText(dto.getContent());
        try{
            javaMailSender.send(mailMessage);
            System.out.println("邮件发送成功");
        }catch (Exception e){
            e.fillInStackTrace();
            log.error("邮件发送发生了异常：{}"+e.getMessage());

        }
    }
    /**
     * 发送带文件的邮件
     * @param dto 邮件实体
     */
    public void sendFileEmail(SendMailDto dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = getMimeMsgHelper(dto, message,null);
            File file =new File(environment.getProperty("send.mail.attachment.url"));
            mimeMessageHelper.addAttachment(environment.getProperty("send.mail.attachment"),file);
            javaMailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
            log.error("send file mail filed"+e.getMessage());

        }

        log.info("邮件发送完毕");

    }



    /**
     * 发送带模板引擎的邮件
     * @param dto
     */
    public void sendTemplateEmail(SendMailDto dto, Map<String,Object> map){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            getMimeMsgHelper(dto, mimeMessage, map);
            javaMailSender.send(mimeMessage);
            log.info("邮件发送成功");

        }catch (Exception e){
            e.fillInStackTrace();
            log.error("邮件发送发生异常"+e.getMessage());
        }
    }
    private  MimeMessageHelper getMimeMsgHelper(SendMailDto dto, MimeMessage message,Map<String,Object> contextMap) throws MessagingException {

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(dto.getTo());
        mimeMessageHelper.setSubject(dto.getSubject());
        if(contextMap==null){
            mimeMessageHelper.setText(dto.getContent());
        }else{
            Context context =new Context();
            context.setVariables(contextMap);
            String mailContext = templateEngine.process("mailSender", context);
            mimeMessageHelper.setText(mailContext,true);
        }
        return  mimeMessageHelper;
    }

}
