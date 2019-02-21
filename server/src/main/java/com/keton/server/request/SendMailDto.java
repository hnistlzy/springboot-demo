package com.keton.server.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
@Data
@ToString
public class SendMailDto {
    @NotBlank(message = "邮件接受方不能为空！")
    private String  to;
    @NotBlank(message = "邮件主题不能为空！")
    private String subject;
    private String [] tos;
    private String content;

    private Integer recordId;

}
