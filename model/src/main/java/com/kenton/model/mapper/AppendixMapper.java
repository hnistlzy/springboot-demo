package com.kenton.model.mapper;


import com.kenton.model.entity.Appendix;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
@Mapper
public interface AppendixMapper {
    int insertSelective(Appendix record);
    int insert(Appendix record);

    Appendix selectByPrimaryKey(Integer appendixId);

    void updateRecordId(Appendix appendix);

    List<Appendix> selectByRecordId(Integer recordId);

    List<Appendix> selectByRecordIdModuleType(@Param(value = "id") Integer id, @Param(value = "orderType") String orderType);

}