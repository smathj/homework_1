# 콜버스랩 자리톡 사전 과제

안녕하세요 김성준입니다.

사용한 환경에 대해 말씀드립니다.


    Spring Boot 2.7
    JDK 11
    Mybatis

------------------------------------------------------------------------------------------------------
# [Board API]

**GET /board                  게시글 전체 조회**
  - like_it 칼럼    : 자신이 누른 좋아요일경우 1, 아니면 0
  - like_count 칼럼 : 좋아요 수
  - likes 칼럼      : 좋아요 누른 id의 나열 ( 구분자 , )
  - full_name 칼럼  : "47번 공인 중개사 사용자" 처럼 나오는 칼럼
  - 헤더 여부 : 있으면 자신의 like_it 활성화, 
               없으면 like_it 비활성화
  - 파라미터 여부 : false
  
**GET /board/{seq}            게시글 상세 조회**
  - 헤더의 값이 없어도 조회가능
  - 헤더 여부 : 있으면 자신의 like_it 활성화, 
               없으면 like_it 비활성화
  - 파라미터 여부 : false 

**POST /board                 게시글 생성**
  - 헤더 여부 : true
  - 파라미터 여부 : true (example, { title: ..., content: ...} )

**PUT /board/{seq}            게시글 수정**
  - 헤더 여부 : true
  - 파라미터 여부 : true (example, { title: ..., content: ...} )

**PATCH /board/{seq}          게시글 좋아요 ( 토글 방식으로 누르면 추가, 또 누르면 제거되는 형식 )**
  - 헤더 여부 : true
  - 파라미터 여부 : false

**DELETE /board/{seq}         게시글 삭제 ( delete_yn 칼럼을 1로 수정하는 형식, 게시글을 다시 살려내는 기능 X )**
 - delete_yn 칼럼 : 삭제 여부, 삭제는 1, default 0
 - 헤더 여부 : true
 - 파라미터 여부 : false

------------------------------------------------------------------------------------------------------
# [User API]

**GET /user                   사용자 전체 조회**
 - 헤더 여부 : true
 - 파라미터 여부 : false

**POST /user                  사용자 생성**
 - 헤더 여부 : true
 - 파라미터 여부 : O (example, { nickname: ..., account_type: ... } )

**PATCH /user{id}             사용자 수정 ( 닉네임만 수정 가능합니다, account_type은 고정이라 생각하였습니다.)**
 - 헤더 여부 : true
 - 파라미터 여부 : O (example, { nickname: ... } )

**DELETE /user/{id}           사용자 삭제 ( quit 칼럼을 1로 수정하는 형식 )**
 - 헤더 여부 : true

**PATCH /user/{id}/recovery   사용자 복귀 ( quit 칼럼을 0로 수정하는 형식 )**
 - 헤더 여부 : true



------------------------------------------------------------------------------------------------------
# [ Response Format ]
기본적으로 apiTime, success 여부가를 리턴하며

리스트 조회시에는 data 라는 프로퍼티에 배열의 값이 있습니다

디테일 조회시에는 data 라는 프로퍼티에 오브젝트 값이 있습니다 

그외 수정,삭제는 data 라는 프로퍼티를 제공하지 않습니다

에러가 발생했을때는 message 프로퍼티에 에러 메세지가 나옵니다

    { 
      data?: [...] or {...},
      success: true or false,
      apiTime: 2022-06-18 01:11:22,
      message?: ...
    }



------------------------------------------------------------------------------------------------------

제가 NodeJS 진영에서의 ORM은 Prisma, TypeORM을 사용해본경험이있지만

JPA는 맛만봐본 상태입니다.

별도에 요구사항이 없어서 Mybatis로 급하게 만들어보았습니다. ;;

좋아요 기능같은경우에는 한번 누르면 "좋아요" 활성화가 되며,

한번더 누를경우 실제 웹사이트 경험처럼 "좋아요"가 비활성화 되듯이 빠지게 만들었습니다.



아쉬운점.

인터셉터를 이용해서 들어오는 header와 리턴하는 데이터를 가공했으면 어땟을까 합니다
최근 NestJs를 이용한 쏘카 프로젝트에서 그렇게 사용했었는데 만들고나니 아쉽네요.

Exception도 마찬가지입니다.

하지만 저에게 유익한 시간이였습니다.








