# 콜버스랩 자리톡 사전 과제

안녕하세요 김성준입니다.

사용한 환경에 대해 말씀드립니다.


Spring Boot 2.7
JDK 11
Mybatis

[Board API]

GET /board                  게시글 전체 조회
  - like_it 칼럼    : 자신이 누른 좋아요일경우 1, 아니면 0
  - like_count 칼럼 : 좋아요 수
  - likes 칼럼      : 좋아요 누른 id의 나열 ( 구분자 , )
  - full_name 칼럼  : "47번 공인 중개사 사용자" 처럼 나오는 칼럼
  - 헤더의 값이 없어도 조회가능
  
GET /board/{seq}            게시글 상세 조회
  - 헤더의 값이 없어도 조회가능
   
POST /board                 게시글 생성

PUT /board/{seq}            게시글 수정

PATCH /board/{seq}          게시글 좋아요 ( 토글 방식으로 누르면 추가, 또 누르면 제거되는 형식 )

DELETE /board/{seq}         게시글 삭제 ( delete_yn 칼럼을 1로 수정하는 형식, 게시글을 다시 살려내는 기능 X )
 - delete_yn 칼럼 : 삭제 여부, 삭제는 1, default 0

------------------------------------------------------------------------------------------------------

[User API]

GET /user                   사용자 전체 조회

POST /user                  사용자 생성

PATCH /user{id}             사용자 수정 ( 닉네임만 수정 가능합니다, account_type은 고정이라 생각하였습니다.)

DELETE /user/{id}           사용자 삭제 ( quit 칼럼을 1로 수정하는 형식 )
- quit 칼럼 : 삭제 여부, 삭제는 1, default 0

PATCH /user/{id}/recovery   사용자 복귀 ( quit 칼럼을 0로 수정하는 형식 )




