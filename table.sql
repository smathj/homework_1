create database homework;

use homework;

-- 데이터 베이스 homework 체크
select database();

create table user
(
    id  int primary key auto_increment comment 'PK',
    nickname     varchar(50)          not null comment '닉네임',
    account_type varchar(10)          not null comment '계정 타입(LESSOR 임대인, REALTOR 공인 중개사, LESSEE 임차인',
    account_id   varchar(10)          null,
    quit         tinyint(1) default 0 not null comment '삭제 여부, 삭제되었다면 1'
);

-- 샘플 데이터
insert into user(nickname,account_type) values
('서준', 'LESSOR'),
('하준', 'REALTOR'),
('시우', 'REALTOR'),
('도윤', 'LESSOR'),
('예준', 'LESSEE'),
('주원', 'LESSEE'),
('민준', 'LESSOR')
;


create table board
(
    seq         int primary key auto_increment comment 'PK',
    title       varchar(50)                            not null comment '제목',
    content     text                                   null comment '내용',
    writer      int                                    not null comment '작성자 pk',
    likes       text                                   null comment '좋아요 누른 user의 id',
    create_date datetime   default current_timestamp() not null comment '작성일',
    update_date datetime                               null on update current_timestamp() comment '수정일',
    delete_date datetime                               null comment '삭제일',
    delete_yn   tinyint(1) default 0                   not null comment '삭제 여부, 삭제되었다면 1'
);

-- 샘플 데이터
insert into board(title, content, writer) values
('첫번째 게시글','첫번째 게시글의 내용', 1)
('두번째 게시글','두번째 게시글의 내용', 2)
;

