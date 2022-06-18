package com.homework.mybatis.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.mybatis.user.UserDao;
import com.homework.mybatis.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BoardService {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private UserDao userDao;


    /**
     * 게시글 전체 조회
     * @param userDto
     * @return
     */
    public Map<String,Object> selectBoardList(UserDto userDto) {

        Map<String,Object> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));

        try {
            String user_id = String.valueOf(userDto.getId());
            
            // 게시글 전체 조회
            List<BoardDto> boardList = boardDao.selectBoardList();

            boardList.forEach((board) -> {

                // 내부인일때, 자신이 눌렀다면,  like_it = 1
                if(user_id != null) {
                    // 자신이 좋아요 눌렀는지 좋아요 문자열에 자신의 user_id가 있는지 확인하기
                    if (board.getLikes() != null && board.getLikes().indexOf(user_id) != -1) {
                        board.setLike_it(1);
                    }
                }

                // 좋아요 갯수 구하기
                if (board.getLikes() != null && !board.getLikes().isEmpty()) {
                    int like_count = Arrays.asList(board.getLikes().split(",")).size();
                    board.setLike_count(like_count);
                }
            });

            result.put("success", "true");
            result.put("data", boardList);
            return result;

        } catch (Exception err ){
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }

    }

    public Map<String,Object> selectBoardBySeq(BoardDto boardDto, UserDto userDto) {

        Map<String,Object> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));

        try {
            String user_id = String.valueOf(userDto.getId());
            BoardDto boardDetail = boardDao.selectBoardBySeq(boardDto);

            // 내부인일때, 자신이 눌렀다면,  like_it = 1
            if(user_id != null) {
                // 자신이 좋아요 눌렀는지 좋아요 문자열에 자신의 user_id가 있는지 확인하기
                if (boardDetail.getLikes() != null && boardDetail.getLikes().indexOf(user_id) != -1) {
                    boardDetail.setLike_it(1);
                }
            }

            // 좋아요 갯수 구하기
            if (boardDetail.getLikes() != null && !boardDetail.getLikes().isEmpty()) {
                int like_count = Arrays.asList(boardDetail.getLikes().split(",")).size();
                boardDetail.setLike_count(like_count);
            }

            result.put("data", boardDetail);
            result.put("success", "true");
            return result;
        } catch (Exception err ){
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }

    }

    public Map<String,String> insertBoard(String jsonText, BoardDto boardParamDto, UserDto userDto) {

        Map<String,String> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));


        try {
            BoardDto jsonParamDto = objectMapper.readValue(jsonText, BoardDto.class);
            boardParamDto.setTitle(jsonParamDto.getTitle());
            boardParamDto.setContent(jsonParamDto.getContent());

            // 사용자 검증
            int userIsExist = userDao.selectUserCountByIdAndAccountTypeAndQuit(userDto);
            if(userIsExist != 1) throw new Exception("사용자 정보가 올바르지 않습니다, 헤더를 확인해 주십시오.");


            // 게시글 작성
            int boardResult = boardDao.insertBoard(boardParamDto);
            if(boardResult != 1) throw new Exception("게시글 작성중 에러가 발생하였습니다, 파라미터를 확인해 주십시오.");

            result.put("success", "true");
            return result;

        } catch (Exception err ){
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }

    }

    public Map<String, String> updateBoard(String jsonText, BoardDto boardParamDto, UserDto userDto) {

        Map<String,String> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));

        try {
            BoardDto jsonParamDto = objectMapper.readValue(jsonText, BoardDto.class);
            boardParamDto.setTitle(jsonParamDto.getTitle());
            boardParamDto.setContent(jsonParamDto.getContent());

            // 사용자 검증
            int userIsExist = userDao.selectUserCountByIdAndAccountTypeAndQuit(userDto);
            if(userIsExist != 1) throw new Exception("사용자 정보가 올바르지 않습니다, 헤더를 확인해 주십시오.");

            // 게시글 수정
            int boardResult = boardDao.updateBoard(boardParamDto);
            if(boardResult != 1) throw new Exception("게시글 수정중 에러가 발생하였습니다, 파라미터를 확인해 주십시오.");

            result.put("success", "true");
            return result;

        } catch (Exception err ){
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }
    }

    /**
     * 좋아요
     * @param boardParamDto
     * @param userDto
     * @return
     */
    public Map<String, String> updateBoardLike(BoardDto boardParamDto, UserDto userDto) {

        Map<String,String> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));

        String newLikeUserStr = null;

        try {
            // 사용자 검증
            int userIsExist = userDao.selectUserCountByIdAndAccountTypeAndQuit(userDto);
            if(userIsExist != 1) throw new Exception("사용자 정보가 올바르지 않습니다, 헤더를 확인해 주십시오.");

            // 게시글 확인
            int boardResult = boardDao.selectBoardCountBySeq(boardParamDto);
            if(boardResult != 1) throw new Exception("존재하지 않는 게시글입니다, 파라미터를 확인해 주십시오.");

            // 게시글 가져오기
            BoardDto oldBoardDto = boardDao.selectBoardBySeq(boardParamDto);

            // 새로운 좋아요 리스트
            ArrayList<String> likeUsersArr = new ArrayList<>();


            // 기존 좋아요 리스트
            if(oldBoardDto.getLikes() != null) {
                String[] oldLikeArr = oldBoardDto.getLikes().split(",");
                for(int i=0; i<oldLikeArr.length; i++) {
                    likeUsersArr.add(oldLikeArr[i]);
                }
            }

            // 좋아요 추가, 좋아요 배열에 새로운 사용자 id가 없다면, 추가하자
            if(likeUsersArr.indexOf(boardParamDto.getLikes()) == -1) {
                // 기존 좋아요 있을때
                if(oldBoardDto.getLikes() != null && !oldBoardDto.getLikes().isEmpty()) {
                    newLikeUserStr = oldBoardDto.getLikes() + "," + boardParamDto.getLikes();
                // 첫 좋아요 눌렀을때
                } else {
                    newLikeUserStr = boardParamDto.getLikes();
                }
            // 좋아요 해제
            } else {
                likeUsersArr.remove(likeUsersArr.indexOf(boardParamDto.getLikes()));
                newLikeUserStr = String.join(",",likeUsersArr);
            }
            
            // 변경된 좋아요 리스트
            boardParamDto.setLikes(newLikeUserStr);

            // 좋아요 업데이트
            int likeBoard = boardDao.updateBoardLike(boardParamDto);
            if(likeBoard != 1) throw new Exception("좋아요 업데이트에 오류가 발생했습니다, 파라미터를 확인해 주십시오.");

            result.put("success", "true");
            return result;

        } catch (Exception err ){
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }
    }

    public Map<String, String> deleteBoard(BoardDto boardParamDto, UserDto userDto) {

        Map<String,String> result = new HashMap<>();
        result.put("success", "false");
        result.put("apiTime", dateFormat.format(new Date()));

        try {
            // 사용자 존재 조회 ( 삭제 x )
            int userIsExist = userDao.selectUserCountByIdAndAccountTypeAndQuit(userDto);
            if(userIsExist != 1) throw new Exception("사용자 정보가 올바르지 않습니다, 헤더를 확인해 주십시오.");


            // 게시글 확인
            int boardResult = boardDao.selectBoardCountBySeqAndWriter(boardParamDto);
            if(boardResult != 1) throw new Exception("자신의 게시글이 아닙니다, 파라미터를 확인해 주십시오.");

            // 게시글 삭제
            String delete_date = dateFormat.format(new Date());

            boardParamDto.setDelete_date(delete_date);
            int deleteBoard = boardDao.deleteBoard(boardParamDto);
            if(deleteBoard != 1) throw new Exception("게시글 삭제 에러가 발생하였습니다, 파라미터를 확인해 주십시오.");

            result.put("success", "true");
            return result;
        } catch (Exception err) {
            err.printStackTrace();
            result.put("message", err.getMessage());
            return result;
        }
    }
}
