package com.homework.mybatis.user;

import lombok.Data;

@Data
public class UserDto {

    private int id;                     // PK
    private String nickname;            // 닉네임
    private String account_type;        // 계정 타입(LESSOR 임대인, REALTOR 공인 중개사, LESSEE 임차인
    private String account_id;          // 사용 미정
    private int quit;                   // 삭제 여부 1, 0
}
