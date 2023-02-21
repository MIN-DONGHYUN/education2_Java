
                    ----- **** 오라클 계정 생성하기 시작 **** ------

-- 오라클 계정 생성을 위해서는 SYS 또는 SYSTEM 으로 연결하여 작업을 해야 합니다(SYS 시작)

show user;
-- USER이(가) "SYS"입니다.


-- 오라클 계정 생성시 계정명 앞에 c## 붙이지 않고 생성하도록 하겠습니다.
alter session set "_ORACLE_SCRIPT" = true;
-- Session이(가) 변경되었습니다.


-- 오라클 계정명은 JDBC_USER 이고 암호는 gclass 인 사용자 계정을 생성합니다.
-- 위에것을 안해면 create user c##JDBC_USER 이렇게 해야한다.
create user JDBC_USER identified by gclass default tablespace users;
-- User JDBC_USER이(가) 생성되었습니다.


-- 위에서 생성되어진 JDBC_USER 이라는 오라클 일반 사용자 계정에게 오라클 서버에 접속이 되어지고, 
-- 테이블 생성 등등을 할 수 있도록 여러가지 권한을 부여해주겠습니다.
grant connect, resource, create view, unlimited tablespace to JDBC_USER;
-- Grant을(를) 성공했습니다.

                    ----- **** 오라클 계정 생성하기 끝 **** ------
                    

show user;
-- USER이(가) "JDBC_USER"입니다.

-- 테이블 생성하기 
create table tbl_memo
(no        number(4)
,name      Nvarchar2(20)    not NULL
,msg       Nvarchar2(100)    not NULL
,writeday  date default SYSDATE
,constraint PK_tbl_memo_no primary key(no)
);
-- Table TBL_MEMO이(가) 생성되었습니다.


-- 시퀀스 생성하기 
create sequence seq_memo
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-- Sequence SEQ_MEMO이(가) 생성되었습니다.


-------- 자바에서 인서트를 해올것이다. --------

-- 현재상황
select *
from tbl_memo
order by 1;
-- 1	민동현	안녕하세요~~	23/02/21
-- 3	이순신	좋은 하루되세요~	23/02/21
-- 2번이 없는 이유는 자바에서 commit을 하지 않고 rollback; 을 했기 때문에 2번을 건너뛰고 3번이 나오는 것이다.




---------- 자바에서 select를 해줄 것이다. --------

-- select 문 일때 실행한곳 
select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') AS writeday
from tbl_memo
order by no desc;

