
CREATE TABLE testtest(
id INT(10),regDate DATETIME,title CHAR(40),`body` VARCHAR(400)
); 

DESC testtest;
ALTER TABLE testtest MODIFY COLUMN id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT;
ALTER TABLE testtest MODIFY COLUMN title CHAR(40) NOT NULL;
ALTER TABLE testtest MODIFY COLUMN `body` VARCHAR(400) NOT NULL;
ALTER TABLE testtest MODIFY COLUMN regDate DATE NOT NULL;


INSERT INTO testtest
SET regDate = '2020-11-01',title='제목11',`body`='내용1';

SELECT * FROM testtest;

INSERT INTO testtest
SET regDate = '2020-11-02',title='제목2',`body`='내용2';

INSERT INTO testtest
SET regDate = '2020-11-03',title='제목7',`body`='내용3';

INSERT INTO testtest
SET regDate = '2020-11-04',title='제목4',`body`='내용4';

DELETE FROM testtest WHERE id=3;

SELECT * FROM testtest;

INSERT INTO testtest
SET regDate = '2020-11-05',title='제목10',`body`='내용5';

DELETE FROM testtest WHERE id=5;

INSERT INTO testtest
SET regDate = '2020-11-06',title='제목9',`body`='내용6';

INSERT INTO testtest
SET regDate = '2020-11-07',title='제목18',`body`='내용7';

SELECT * FROM testtest;

TRUNCATE testtest;

# article 시작

SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, a.*
FROM article a,(SELECT @ROWNUM := 0) TMP 
ORDER BY regDate DESC;

SELECT * 
 FROM ( SELECT 
                @ROWNUM := @ROWNUM + 1 AS ROWNUM,
                 a.* 
        FROM article a,
                (SELECT @ROWNUM := 0) TMP 
       WHERE boardId=2 ORDER BY boardId ASC)SUB 
ORDER BY SUB.ROWNUM DESC;

SELECT * FROM ( SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, a.* FROM article a,
       (SELECT @ROWNUM := 0) TMP WHERE boardId=2 ORDER BY boardId ASC)SUB 
ORDER BY SUB.ROWNUM DESC;


SET @rownum :=0;

SELECT @rownum :=@rownum+1, a.* FROM article a;

SELECT @rownum :=@rownum+1, a.* FROM article a WHERE boardId=2;

SELECT @rownum :=@rownum+1, a.* FROM article a, (SELECT @rownum:=0) TMP;

SELECT @rownum :=@rownum +1 AS rownum, a.* FROM
        (SELECT * FROM article WHERE boardId=2 ORDER BY id DESC) a,
        (SELECT @rownum := 0) i;
        
###############################################################################################
###############################################################################################
##########################################시작#################################################
###############################################################################################
###############################################################################################

DROP DATABASE a2;

CREATE DATABASE a2;

USE a2;

SHOW TABLES;

########## article 생성 #############
CREATE TABLE article(
id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
regDate DATE NOT NULL, updateDate DATE NOT NULL, 
title CHAR(200) NOT NULL, `body` TEXT NOT NULL,
memberId INT(10) UNSIGNED NOT NULL, boardId INT(10) UNSIGNED NOT NULL,
hit INT(10) UNSIGNED NOT NULL, `like` INT(10) UNSIGNED NOT NULL
);

DESC article;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='공지1', `body`='1번 사용자가 작성함.',
memberId=1, boardId=1;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='공지2', `body`='2번 사용자가 작성함',
memberId=2, boardId=1;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='공지3', `body`='3번 사용자가 작성함',
memberId=3, boardId=1;

SELECT * FROM article;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유1', `body`='3번 사용자가 작성함',
memberId=3, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유2', `body`='1번 사용자가 작성함',
memberId=1, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유3', `body`='4번 사용자가 작성함',
memberId=4, boardId=2;

##
INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유4', `body`='3번 사용자가 작성함',
memberId=3, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유5', `body`='2번 사용자가 작성함',
memberId=2, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유6', `body`='1번 사용자가 작성함',
memberId=1, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유7', `body`='4번 사용자가 작성함',
memberId=4, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유8', `body`='3번 사용자가 작성함',
memberId=3, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유9', `body`='2번 사용자가 작성함',
memberId=2, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유10', `body`='1번 사용자가 작성함',
memberId=1, boardId=2;

INSERT INTO article
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
title='자유11', `body`='4번 사용자가 작성함',
memberId=4, boardId=2;
##
SELECT * FROM article;

SHOW TABLES;

########## member 생성 ##########
CREATE TABLE `member`(
id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
regDate DATE NOT NULL,
updateDate DATE NOT NULL,
userId CHAR(30) NOT NULL, passwd VARCHAR(50) NOT NULL, `name` CHAR(30) NOT NULL
);

DESC `member`;

INSERT INTO `member`
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
userId = 'user1',
passwd = 'user1',
`name`='테스트1';

INSERT INTO `member`
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
userId = 'user2',
passwd = 'user2',
`name`='테스트2';

INSERT INTO `member`
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
userId = 'user3',
passwd = 'user3',
`name`='테스트3';

INSERT INTO `member`
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
userId = 'user4',
passwd = 'user4',
`name`='테스트4';


SELECT * FROM `member`;

########## board 생성 ##########
CREATE TABLE board(
id INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
regDate DATE NOT NULL,
updateDate DATE NOT NULL, 
`name` CHAR(20) NOT NULL,
`code` CHAR(20) NOT NULL
);

INSERT INTO board
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
`name`='공지사항',
`code`='notice';

INSERT INTO board
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
`name`='자유',
`code`='free';

SELECT * FROM board;

############# 선택된 게시판으로 리스팅시 번호 차례대로 내림차순 ################
SELECT * FROM ( SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, a.* FROM article a,
	  (SELECT @ROWNUM := 0) TMP WHERE boardId=2 ORDER BY boardId ASC)SUB
	  ORDER BY SUB.ROWNUM DESC;
################################################################################


	  
########## articleReply 생성 ##########
CREATE TABLE articleReply(
regDate DATE NOT NULL,
updateDate DATE NOT NULL,
replyId INT(10) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
articleId INT(10) UNSIGNED NOT NULL,
reply CHAR(200) NOT NULL,
memberReplyId INT(10) UNSIGNED NOT NULL,
boardReplyId INT(10) UNSIGNED NOT NULL,
`like` INT(10) UNSIGNED NOT NULL
);

DESC articleReply;

SELECT * FROM articleReply;


INSERT INTO articleReply 
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
articleId = 2,
boardReplyId = 1,
reply = '안녕하세요 저는 테스트1입니다.',
memberReplyId = 1;

INSERT INTO articleReply 
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
articleId = 2,
boardReplyId = 1,
reply = '안녕하세요 저는 테스트2입니다.',
memberReplyId = 2;

INSERT INTO articleReply 
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
articleId = 2,
boardReplyId = 1,
reply = '안녕하세요 저는 테스트3입니다.',
memberReplyId = 3;

INSERT INTO articleReply 
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
articleId = 1,
boardReplyId = 1,
reply = '안녕하세요 저는 테스트4입니다.',
memberReplyId = 4;

INSERT INTO articleReply 
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
articleId = 5,
boardReplyId = 1,
reply = '안녕하세요 저는 테스트1입니다.',
memberReplyId = 1;

INSERT INTO articleReply 
SET regDate = DATE(NOW()),
updateDate = DATE(NOW()),
articleId = 3,
boardReplyId = 1,
reply = '안녕하세요 저는 테스트2입니다.',
memberReplyId = 2;

SELECT * FROM articleReply;


################## select 다중 조인 #####################
SELECT articleReply.*, member.name FROM articleReply
INNER JOIN `member`
ON articleReply.memberReplyId = member.id
INNER JOIN article ON article.id = articleReply.articleId
WHERE articleId = 2;
#########################################################


SELECT * FROM articleReply WHERE articleId =2;


#################랜덤 게시물 생성####################

# 각종 함수
/*
SELECT DATE(NOW());
SELECT YEAR(NOW());
SELECT MONTH(NOW());
SELECT DAY(NOW());
SELECT SUBSTR("안녕하세요.", 1, 2);
SELECT SUBSTR("안녕하세요.", 2, 2);
SELECT CONCAT("안녕", "하세요.");
SELECT RAND() * 100;
*/

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT("제목_", RAND()),
`body` = CONCAT("내용_", RAND()),
memberId = FLOOR(RAND() * 4) + 1,
boardId = FLOOR(RAND() * 2) + 1;


# 3번글 내용을 마크다운 문법으로 수정
UPDATE article SET `body` = '# 공지사항\r\n안녕하세요.\r\n이 사이트는 저의 글 연재 공간입니다.\r\n\r\n---\r\n\r\n# 이 사이트의 특징\r\n- A\r\n- B\r\n- C'
WHERE id = '3';

SELECT * FROM article;

# 2번글 내용에 자바소스코드 넣기
UPDATE article SET `body` = '# 자바기본문법\r\n```java\r\nint a = 10;\r\nint b = 20;\r\nint c = a + b;\r\n```'
WHERE id = '2'; 

SELECT * FROM board
