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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

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

    public void sendFileEmail(SendMailDto dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(dto.getTo());
            mimeMessageHelper.setSubject(dto.getSubject());
            mimeMessageHelper.setText(dto.getContent());
            File file =new File(environment.getProperty("send.mail.attachment.url"));

            mimeMessageHelper.addAttachment(environment.getProperty("send.mail.attachment"),file);
            javaMailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
            log.error("send file mail filed"+e.getMessage());

        }

        log.info("邮件发送完毕");

    }

    public void sendTemplateEmail(SendMailDto dto){

    }
}
