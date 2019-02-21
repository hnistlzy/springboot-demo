package com.keton.server.controller;


import com.keton.server.entity.EnvironmentEntity;
import com.keton.server.enums.StatusCode;
import com.keton.server.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
@RestController
public class TestLombokController {
    private static final String prefix="/lombok";
    private static final Logger log= LoggerFactory.getLogger(TestLombokController.class);
    @Autowired
    private EnvironmentEntity envi;

    @RequestMapping(value = prefix+"/getProperties",method = RequestMethod.GET)
    public BaseResponse getProperties(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        log.info(envi.toString());
        baseResponse.setData(envi);
        return baseResponse;
    }

}
