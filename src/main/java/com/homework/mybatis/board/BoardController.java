package com.homework.mybatis.board;

import com.homework.mybatis.common.header.CustomHeader;
import com.homework.mybatis.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;


    /**
     * 게시글 전체 조회
     * @param header
     * @return
     */
    @GetMapping("/board")
    public ResponseEntity<?> boardList(@RequestHeader Map<String, String> header) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDto = CustomHeader.getUserData(header);

        Map<String,Object> result = boardService.boardList(userDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    /**
     * 상세 조회
     * @param seq board Table Seq(PK)
     * @return BoardDto
     */
    @GetMapping("/board/{seq}")
    public ResponseEntity<?> boardFindBySeq(@RequestHeader Map<String, String> header,
                                            @PathVariable("seq") int seq) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDto = CustomHeader.getUserData(header);

        BoardDto boardDto = new BoardDto();
        boardDto.setSeq(seq);

        Map<String,Object> result = boardService.boardFindBySeq(boardDto, userDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    /**
     * 게시글 작성
     * @param header 계정 타입, 계정 아이디(PK)
     * @param jsonText title 제목 ,content 내용
     * @return ResponseEntity<?>
     */
    @PostMapping("/board")
    public ResponseEntity<?> boardCreate(@RequestHeader Map<String, String> header,
                                         @RequestBody String jsonText) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDto = CustomHeader.getUserData(header);

        // 게시글 작성자, 게시글 생성 서비스
        BoardDto boardParamDto = new BoardDto();
        boardParamDto.setWriter(Integer.valueOf(userDto.getId()));
        Map<String,String> result = boardService.boardCreate(jsonText, boardParamDto, userDto);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }



    /**
     * 게시글 수정
     * @param header 계정 타입, 계정 아이디(PK)
     * @param jsonText title 제목 ,content 내용
     * @return ResponseEntity<?>
     */
    @PutMapping("/board/{seq}")
    public ResponseEntity<?> updateBoard(@RequestHeader Map<String, String> header,
                                         @RequestBody String jsonText,
                                         @PathVariable("seq") int seq) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDto = CustomHeader.getUserData(header);

        // 게시글 작성자, 게시글 수정 서비스
        BoardDto boardParamDto = new BoardDto();
        boardParamDto.setSeq(seq);
        boardParamDto.setWriter(Integer.valueOf(userDto.getId()));
        Map<String,String> result = boardService.updateBoard(jsonText, boardParamDto, userDto);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    /**
     * 게시글 좋아요
     * @param header 계정 타입, 계정 아이디(PK)
     * @param seq 게시글 고유 번호
     * @return ResponseEntity<?>
     */
    @PatchMapping("/board/{seq}")
    public ResponseEntity<?> boardLike(@RequestHeader Map<String, String> header,
                                       @PathVariable("seq") int seq) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDto = CustomHeader.getUserData(header);

        // 게시글 작성자, 게시글 좋아요 서비스
        BoardDto boardParamDto = new BoardDto();
        boardParamDto.setSeq(seq);
        boardParamDto.setLikes(String.valueOf(userDto.getId()));
        boardParamDto.setWriter(Integer.valueOf(userDto.getId()));
        Map<String,String> result = boardService.boardLike(boardParamDto, userDto);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    /**
     * 게시글 삭제
     * @param header
     * @param seq
     * @return
     */
    @DeleteMapping("/board/{seq}")
    public ResponseEntity<?> updateBoard(@RequestHeader Map<String, String> header,
                                         @PathVariable("seq") int seq) {

        // 헤더에서 계정 타입, 계정 아이디 가져오기
        header = CustomHeader.parsingHeader(header);
        UserDto userDto = CustomHeader.getUserData(header);

        // 게시글 작성자, 게시글 수정 서비스
        BoardDto boardParamDto = new BoardDto();
        boardParamDto.setSeq(seq);
        boardParamDto.setWriter(Integer.valueOf(userDto.getId()));
        Map<String,String> result = boardService.boardDelete(boardParamDto, userDto);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
