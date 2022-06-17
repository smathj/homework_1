package com.homework.mybatis.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<UserDto> userlist();

    int userCountById(UserDto userDtoParam);

    int userNickNameUpdate(UserDto userDtoParam);

    int userRecovery(UserDto userDtoParam);

    int userDelete(UserDto userDtoParam);
    int userCreate(UserDto userDtoParam);
    int userCountByIdAndAccountTypeAndQuit(UserDto userDto);
}
