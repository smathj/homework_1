package com.homework.mybatis.user;

import com.homework.mybatis.common.header.CustomHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 사용자 전체조회
     * @param header
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<?> selectUserList(@RequestHeader Map<String, String> header) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDtoParam = CustomHeader.getUserData(header);

        Map<String, Object> result = userService.selectUserList(userDtoParam);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 사용자 생성
     * @param jsonText
     */
    @PostMapping("/user")
    public ResponseEntity<?> insertUser(@RequestBody String jsonText) {
            Map<String, String> resultMap = userService.insertUser(jsonText);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    /**
     * 사용자 수정( 닉네임만 수정 가능 )
     * @param header
     * @param id
     * @return
     */
    @PatchMapping("/user/{id}")
    public ResponseEntity<?> updateUserNickName(@RequestHeader Map<String, String> header,
                                        @PathVariable("id") int id,
                                        @RequestBody String jsonText) {
        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDtoParam = CustomHeader.getUserData(header);

        Map<String, String> result = userService.updateUserNickName(jsonText, userDtoParam);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 사용자 삭제
     * @param header
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader Map<String, String> header,
                                        @PathVariable("id") int id) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDtoParam = CustomHeader.getUserData(header);

        Map<String, String> result = userService.deleteUser(userDtoParam);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * 사용자 복귀
     * @param header
     * @param id
     * @return
     */
    @PatchMapping("/user/{id}/recovery")
    public ResponseEntity<?> selectUserRecovery(@RequestHeader Map<String, String> header,
                                        @PathVariable("id") int id) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDtoParam = CustomHeader.getUserData(header);

        Map<String, String> result = userService.selectUserRecovery(userDtoParam);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}
