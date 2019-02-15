package com.keton.server.request;

import java.io.Serializable;

public class OrderRecordInsertUpdateDTO implements Serializable {


    private String orderNo;

    private String orderType;

    @Override
    public String toString() {
        return "OrderRecordInsertUpdateDTO{" +
                "orderNo='" + orderNo + '\'' +
                ", orderType='" + orderType + '\'' +
                '}';
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
