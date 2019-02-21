package com.keton.server.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
/**
 * @author KentonLee
 * @date 2019/2/21
 */
public class AppendixDto {
    private String moduleType;
    private Integer recordId;
    private String location;
    private Date createTime;
}
