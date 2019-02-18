package com.keton.server.request;

import javax.validation.constraints.NotBlank;

public class SendMailDto {
    @NotBlank(message = "邮件接受方不能为空！")
    private String to;
    @NotBlank(message = "邮件主题不能为空！")
    private String subject;
    @NotBlank(message = "邮件内容不能为空！")
    private String content;

    @Override
    public String toString() {
        return "SendMailDto{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
