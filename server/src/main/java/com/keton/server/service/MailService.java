package com.keton.server.service;

import com.keton.server.request.SendMailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
    private TemplateEngine templateEngine;

    /**
     * 通用的邮件发送服务
     * @param dto dto
     * @param contextMap contextMap
     * @param nameLocationMap nameLocationMap
     * @param templateName templateName
     * @throws Exception exception
     */
    public void sendMail(final SendMailDto dto,final Map<String,Object> contextMap,Map<String,String> nameLocationMap,final String templateName) throws  Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(dto.getSubject());
        if(dto.getContent()!=null){
            mimeMessageHelper.setText(dto.getContent());
        }
        if(dto.getTos()!=null){
            mimeMessageHelper.setTo(dto.getTos());
        }else{
            mimeMessageHelper.setTo(dto.getTo());
        }

        if(nameLocationMap!=null){
            for(Map.Entry<String,String> entry:nameLocationMap.entrySet()){
                String fileName = entry.getKey();
                String fileLocation = entry.getValue();
                mimeMessageHelper.addAttachment(fileName,new File(fileLocation));
            }
        }
        if(contextMap!=null){
            Context context = new Context();
            context.setVariables(contextMap);
            String mailSender = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(mailSender,true);
        }
        javaMailSender.send(mimeMessage);
        log.info("邮件发送成功！");
    }

}
