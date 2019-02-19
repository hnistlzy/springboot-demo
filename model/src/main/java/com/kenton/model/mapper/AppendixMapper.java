package com.kenton.model.mapper;


import com.kenton.model.entity.Appendix;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppendixMapper {
    int insertFileSaveRecord(Appendix record);
    int insert(Appendix record);
}