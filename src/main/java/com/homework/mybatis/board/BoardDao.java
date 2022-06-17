package com.homework.mybatis.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {

    List<BoardDto> list();

    BoardDto boardFindBySeq(BoardDto boardParamDto);

    int boardCountFindBySeq(BoardDto boardParamDto);
    int boardCountFindBySeqAndWriter(BoardDto boardParamDto);
    int boardCreate(BoardDto boardParamDto);

    int boardUpdate(BoardDto boardParamDto);
    int boardLike(BoardDto boardParamDto);

    int boardDelete(BoardDto boardParamDto);
}
