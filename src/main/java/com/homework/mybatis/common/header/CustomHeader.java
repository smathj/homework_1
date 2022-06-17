package com.homework.mybatis.common.header;

import com.homework.mybatis.user.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomHeader {
    public final static String Realtor = "REALTOR";
    public final static String Lessor = "LESSOR";
    public final static String Lessee = "LESSEE";

    /**
     * 헤더로 부터 계정타입, 아이디 추출
     * @param header
     * @return
     */
    public static Map<String,String> parsingHeader(Map<String,String> header) {

        // DB에 추가 테이블만들었다면 거기서 조회해오기 => 안만듬
        List<String> accountList = new ArrayList<>();
        accountList.add(CustomHeader.Realtor);
        accountList.add(CustomHeader.Lessor);
        accountList.add(CustomHeader.Lessee);

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        String userMetaData = header.get("authorization");

        if(userMetaData != null) {

            String user_account = userMetaData.split("\\s")[0];
            String user_id = userMetaData.split("\\s")[1];

            // 유효한 계정 타입인지 체크, 외부인 out, 내부인 in
            if(accountList.indexOf(user_account) == -1) {
                header.put("user_flag", "out");
                header.put("user_account", "empty");
                header.put("user_id", "0");
            } else {
                header.put("user_flag", "in");
                header.put("user_account", user_account);
                header.put("user_id", user_id);
            }
        }
        return header;
    }

    /**
     * 사용자 객체 생성
     * @param header
     * @return
     */
    public static UserDto getUserData(Map<String,String> header) {
        String user_account = header.get("user_account") != null ? header.get("user_account") : "" ;
        String user_id = header.get("user_id") != null ? header.get("user_id") : "0" ;

        // 사용자 조회 Dto
        UserDto userDto = new UserDto();
        userDto.setAccount_type(user_account);
        userDto.setId(Integer.valueOf(user_id));

        return userDto;
    }
}
