package com.keton.server.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AppendixDto {
    private String moduleType;
    private Integer recordId;
    private String location;
    private Date createTime;
}
