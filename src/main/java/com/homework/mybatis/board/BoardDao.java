package com.homework.mybatis.board;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {

    List<BoardDto> selectBoardList();

    BoardDto selectBoardBySeq(BoardDto boardParamDto);

    int selectBoardCountBySeq(BoardDto boardParamDto);
    int selectBoardCountBySeqAndWriter(BoardDto boardParamDto);
    int insertBoard(BoardDto boardParamDto);

    int updateBoard(BoardDto boardParamDto);
    int updateBoardLike(BoardDto boardParamDto);

    int deleteBoard(BoardDto boardParamDto);
}
