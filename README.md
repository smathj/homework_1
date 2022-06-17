# 콜버스랩 자리톡 사전 과제

안녕하세요 김성준입니다.

사용한 환경에 대해 말씀드립니다.


Spring Boot 2.7
JDK 11
Mybatis

------------------------------------------------------------------------------------------------------

[Board API]

GET /board                  게시글 전체 조회
  - like_it 칼럼    : 자신이 누른 좋아요일경우 1, 아니면 0
  - like_count 칼럼 : 좋아요 수
  - likes 칼럼      : 좋아요 누른 id의 나열 ( 구분자 , )
  - full_name 칼럼  : "47번 공인 중개사 사용자" 처럼 나오는 칼럼
  - 헤더 여부 : 있으면 자신의 like_it 활성화, 
               없으면 like_it 비활성화
  - 파라미터 여부 : false
  
GET /board/{seq}            게시글 상세 조회
  - 헤더의 값이 없어도 조회가능
  - 헤더 여부 : 있으면 자신의 like_it 활성화, 
               없으면 like_it 비활성화
  - 파라미터 여부 : false 
  
POST /board                 게시글 생성
  - 헤더 여부 : true
  - 파라미터 여부 : true (example, { title: ..., content: ...} )
   
PUT /board/{seq}            게시글 수정
  - 헤더 여부 : true
  - 파라미터 여부 : true (example, { title: ..., content: ...} )
   
PATCH /board/{seq}          게시글 좋아요 ( 토글 방식으로 누르면 추가, 또 누르면 제거되는 형식 )
  - 헤더 여부 : true
  - 파라미터 여부 : false
  
DELETE /board/{seq}         게시글 삭제 ( delete_yn 칼럼을 1로 수정하는 형식, 게시글을 다시 살려내는 기능 X )
 - delete_yn 칼럼 : 삭제 여부, 삭제는 1, default 0
 - 헤더 여부 : true
 - 파라미터 여부 : false

------------------------------------------------------------------------------------------------------

[User API]

GET /user                   사용자 전체 조회
 - 헤더 여부 : true
 - 파라미터 여부 : false
 
POST /user                  사용자 생성
 - 헤더 여부 : true
 - 파라미터 여부 : O (example, { nickname: ..., account_type: ... } )
 
PATCH /user{id}             사용자 수정 ( 닉네임만 수정 가능합니다, account_type은 고정이라 생각하였습니다.)
 - 헤더 여부 : true
 - 파라미터 여부 : O (example, { nickname: ... } )

DELETE /user/{id}           사용자 삭제 ( quit 칼럼을 1로 수정하는 형식 )
 - 헤더 여부 : true

PATCH /user/{id}/recovery   사용자 복귀 ( quit 칼럼을 0로 수정하는 형식 )
 - 헤더 여부 : true



------------------------------------------------------------------------------------------------------

제가 NodeJS 진영에서의 ORM은 Prisma, TypeORM을 사용해본경험이있지만
JPA는 맛만봐본 상태입니다.
별도에 요구사항이 없어서 Mybatis로 급하게 만들어보았습니다.

네이밍도 약간 ORM에 흔히사용하는 findByxxx 형식으로 지었습니다.

좋아요 기능같은경우에는 한번 누르면 "좋아요" 활성화가 되며,
한번더 누를경우 실제 웹사이트 경험처럼 "좋아요"가 비활성화 되듯이 빠지게 만들었습니다.












