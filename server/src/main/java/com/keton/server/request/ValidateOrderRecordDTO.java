package com.keton.server.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 为了区分是否有增加合法性校验，所以新增一个实体类
 */
@Data
@ToString
public class ValidateOrderRecordDTO {
    @NotBlank(message="订单号不能为空")
    private String orderNo;
    @NotBlank(message="订单类型不能为空")
    private String orderType;
}
