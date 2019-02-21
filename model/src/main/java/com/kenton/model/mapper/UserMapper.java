package com.kenton.model.mapper;


import com.kenton.model.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author KentonLee
 * @date 2019/2/21
 */
public interface UserMapper {
    /**
     * insertIntoUser
     * @param id id
     * @param name name
     */
    void insertIntoUser( @Param("id") Integer id, @Param("name") String name);

    /**
     * selectById
     * @param id id
     * @return User
     */
    User selectById(Integer id);
}
