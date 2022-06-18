package com.homework.mybatis.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.mybatis.common.header.CustomHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @Autowired
    private UserDao userDao;

    /**
     * 사용자 전체 조회
     * @param userDtoParam
     * @return
     */
    public Map<String, Object> selectUserList(UserDto userDtoParam) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));

        try {
            int isExist = userDao.selectUserCountByIdAndAccountTypeAndQuit(userDtoParam);
            if (isExist != 1) throw new Exception("존재하지 않는 사용자이거나 삭제된 사용자 입니다, 파라미터를 확인해 주십시오.");
            List<UserDto> userList = userDao.selectUserList();
            result.put("userList", userList);
            result.put("success", "true");
            return result;

        } catch (Exception err) {
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }
    }


    /**
     * 사용자 생성
     * @param jsonText
     * @return
     */
    public Map<String, String> insertUser(String jsonText) {
        Map<String, String> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));
        try {
            UserDto userDtoParam = objectMapper.readValue(jsonText, UserDto.class);

            if(!userDtoParam.getAccount_type().toUpperCase().equals(CustomHeader.Realtor)  &&
               !userDtoParam.getAccount_type().toUpperCase().equals(CustomHeader.Lessee) &&
               !userDtoParam.getAccount_type().toUpperCase().equals(CustomHeader.Lessor)) {
                throw new Exception("잘못된 계정 유형입니다, account_type을 확인하십시오");
            }

            // 사용자 생성
            int isCreate = userDao.insertUser(userDtoParam);
            if (isCreate != 1) throw new Exception("사용자 생성 에러가 발생하였습니다, 파라미터를 확인해 주십시오.");

            result.put("success", "true");
            return result;

        } catch (Exception err) {
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }

    }

    public Map<String, String> updateUserNickName(String jsonText, UserDto userDtoParam) {
        Map<String, String> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));
        try {

            userDtoParam.setNickname(objectMapper.readValue(jsonText, UserDto.class).getNickname());

            // 존재 체크 ( 미 삭제 )
            int isExist = userDao.selectUserCountByIdAndAccountTypeAndQuit(userDtoParam);
            if (isExist != 1) throw new Exception("존재하지 않는 사용자이거나 삭제된 사용자 입니다, 파라미터를 확인해 주십시오.");

            // 사용자 수정
            int isCreate = userDao.updateUserNickName(userDtoParam);
            if (isCreate != 1) throw new Exception("사용자 수정 에러가 발생하였습니다, 파라미터를 확인해 주십시오.");

            result.put("success", "true");
            return result;

        } catch (Exception err) {
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }
    }

    public Map<String, String> deleteUser(UserDto userDtoParam) {
        Map<String, String> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));
        try {

            // 존재 체크 ( 미 삭제 )
            int isExist = userDao.selectUserCountByIdAndAccountTypeAndQuit(userDtoParam);
            if (isExist != 1) throw new Exception("존재하지 않는 사용자이거나 삭제된 사용자 입니다, 파라미터를 확인해 주십시오.");

            // 사용자 삭제
            int isDelete = userDao.deleteUser(userDtoParam);
            if (isDelete != 1) throw new Exception("사용자 삭제 에러가 발생하였습니다, 파라미터를 확인해 주십시오.");

            result.put("success", "true");
            return result;

        } catch (Exception err) {
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }
    }

    public Map<String, String> selectUserRecovery(UserDto userDtoParam) {
        Map<String, String> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));
        try {

            // 존재 체크 ( 미 삭제 )
            int isExist = userDao.selectUserCountById(userDtoParam);
            if (isExist != 1) throw new Exception("존재하지 않는 사용자이거나, 파라미터를 확인해 주십시오.");

            // 사용자 수정
            int isUpdate = userDao.selectUserRecovery(userDtoParam);
            if (isUpdate != 1) throw new Exception("사용자 수정 에러가 발생하였습니다, 파라미터를 확인해 주십시오.");

            result.put("success", "true");
            return result;

        } catch (Exception err) {
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }
    }
}
