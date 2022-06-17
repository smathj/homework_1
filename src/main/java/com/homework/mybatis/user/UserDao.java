package com.homework.mybatis.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<UserDto> list();

    UserDto findById(UserDto userDtoParam);
    int findCountById(UserDto userDtoParam);

    int userNickNameUpdate(UserDto userDtoParam);

    int userRecovery(UserDto userDtoParam);

    int userDelete(UserDto userDtoParam);
    int userCreate(UserDto userDtoParam);
    int findCountByIdAndAccountType(UserDto userDto);
}
