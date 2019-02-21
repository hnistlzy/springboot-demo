package com.keton.server.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
@Data
@ToString
public class SendMailDto {
    @NotBlank(message = "邮件接收方不能为空！")
    private String  to;
    @NotBlank(message = "邮件主题不能为空！")
    private String subject;
    private String content;
    /**
     * cc
     */
    private String cc;
    /**
     * bcc
     */
    private String bcc;

    private Integer recordId;

}
