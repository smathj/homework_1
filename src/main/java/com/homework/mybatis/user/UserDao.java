package com.homework.mybatis.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<UserDto> selectUserList();

    int selectUserCountById(UserDto userDtoParam);

    int updateUserNickName(UserDto userDtoParam);

    int selectUserRecovery(UserDto userDtoParam);

    int deleteUser(UserDto userDtoParam);
    int insertUser(UserDto userDtoParam);
    int selectUserCountByIdAndAccountTypeAndQuit(UserDto userDto);
}
