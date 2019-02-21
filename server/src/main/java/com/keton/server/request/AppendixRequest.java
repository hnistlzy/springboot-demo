package com.keton.server.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
@Data
@ToString
public class AppendixRequest implements Serializable {
    /**
     * recordId
     */
    @NotNull
    private Integer recordId;
    //TODO：附件记录id-以逗号隔开
    /***
     * appendixIds
     */
    @NotBlank
    private String appendixIds;
}
