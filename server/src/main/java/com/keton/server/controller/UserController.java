package com.keton.server.controller;

import com.kenton.model.entity.User;
import com.kenton.model.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KentonLee
 * @date 2019/2/21
 */
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping(value = "/signin",method = RequestMethod.GET)
    public void signIn(Integer id, String name){
        userMapper.insertIntoUser(id,name);
    }
    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public User signup(Integer id){
        return userMapper.selectById(id);
    }
}
