package mapper;

import pojo.Appendix;

public interface AppendixMapper {
    int insert(Appendix record);

    int insertSelective(Appendix record);
}