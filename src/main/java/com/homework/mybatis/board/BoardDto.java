package com.homework.mybatis.board;

import lombok.Data;

import java.util.Date;


@Data
public class BoardDto {

    // DataBase
    private int seq;                // pk
    private String title;           // 제목
    private String content;         // 내용
    private String likes;           // 좋아요 누른 사용자의 id
    private int writer;             // 작성자
    private String create_date;     // 작성일
    private String update_date;     // 수정일
    private String delete_date;     // 삭제일
    private String full_name;       // 풀네임

    // virtual
    private int like_it;            // 자신이 누른 좋아요 1, 안누름 0
    private int like_count;         // 좋아요 수


}
