package com.kenton.model.mapper;


import com.kenton.model.entity.Appendix;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppendixMapper {
    int insertSelective(Appendix record);
    int insert(Appendix record);

    Appendix selectByPrimaryKey(Integer appendixId);

    void updateRecordId(Appendix appendix);
}