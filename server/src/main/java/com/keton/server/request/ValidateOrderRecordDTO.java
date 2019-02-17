package com.keton.server.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class ValidateOrderRecordDTO {
    @NotBlank(message="订单号不能为空")
    private String orderNo;
    @NotBlank(message="订单类型不能为空")
    private String orderType;
}
