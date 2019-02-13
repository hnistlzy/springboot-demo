package com.kenton.model.mapper;


import com.kenton.model.entity.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    void insertIntoUser( @Param("id") Integer id, @Param("name") String name);

    User selectById(Integer id);
}
