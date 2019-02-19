package com.kenton.model.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
@ToString
@Data
public class Appendix {
    private Integer id;
    //文件名
    private String name;
    //文件大小
    private Long size;
    //文件所属模块
    private String moduleType;
    // 文件所属模块id
    private Integer recordId;
    //是否可删除
    private Integer isDelete;

    private String location;

    private Date createTime;

    private Date updateTime;

    private Integer sortBy;

    public Appendix() {
    }

    public Appendix(Integer id, String name, Long size, String moduleType, Integer recordId, Integer isDelete, String location, Date createTime, Date updateTime, Integer sortBy) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.moduleType = moduleType;
        this.recordId = recordId;
        this.isDelete = isDelete;
        this.location = location;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sortBy = sortBy;
    }

}