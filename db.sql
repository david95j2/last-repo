# DB 생성
DROP DATABASE IF EXISTS textBoard;
CREATE DATABASE textBoard;
USE textBoard;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL
);

# 게시물 데이터 3개 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1',
memberId = 1,
boardId = 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2',
memberId = 1,
boardId = 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3',
memberId = 1,
boardId = 1;

# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(30) NOT NULL,
    loginPw VARCHAR(50) NOT NULL,
    `name` CHAR(30) NOT NULL
);

# 회원 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '테스터1';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '테스터2';

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(20) NOT NULL,
    `code` CHAR(20) NOT NULL
);

# 공지사항 게시판 추가
INSERT INTO board 
SET regDate = NOW(),
updateDate = NOW(),
`name` = '공지사항',
`code` = 'notice';

# 자유 게시판 추가
INSERT INTO board 
SET regDate = NOW(),
updateDate = NOW(),
`name` = '자유',
`code` = 'free';

UPDATE article
SET boardId = 2
LIMIT 2;

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

# 게시물 랜덤 생성
/*
insert into article
set regDate = NOW(),
updateDate = NOW(),
title = concat("제목_", rand()),
`body` = CONCAT("내용_", RAND()),
memberId = FLOOR(RAND() * 2) + 1,
boardId = FLOOR(RAND() * 2) + 1;
*/

# 3번글 내용을 마크다운 문법으로 수정
UPDATE article SET `body` = '# 공지사항\r\n안녕하세요.\r\n이 사이트는 저의 글 연재 공간입니다.\r\n\r\n---\r\n\r\n# 이 사이트의 특징\r\n- A\r\n- B\r\n- C'
WHERE id = '3';

SELECT * FROM article;

# 2번글 내용에 자바소스코드 넣기
UPDATE article SET `body` = '# 자바기본문법\r\n```java\r\nint a = 10;\r\nint b = 20;\r\nint c = a + b;\r\n```'
WHERE id = '2'; 

# 게시물 테이블에 추천수, 댓글수 칼럼 추가
ALTER TABLE article ADD COLUMN likesCount INT(10) UNSIGNED NOT NULL;
ALTER TABLE article ADD COLUMN commentsCount INT(10) UNSIGNED NOT NULL;

# 구글 애널리틱스 4 페이지 경로별 통계 정보
CREATE TABLE ga4DataPagePath (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    pagePath CHAR(100) NOT NULL UNIQUE,
    hit INT(10) UNSIGNED NOT NULL
);

# 1단계, 다 불러오기
SELECT pagePath
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'

# 2단계, pagePath 정제
SELECT 
IF(
    INSTR(GA4_PP.pagePath, '?') = 0,
    GA4_PP.pagePath,
    SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
) AS pagePathWoQueryStr
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'

# 3단계, pagePathWoQueryStr(정제된 pagePth)기준으로 sum
SELECT 
IF(
    INSTR(GA4_PP.pagePath, '?') = 0,
    GA4_PP.pagePath,
    SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
) AS pagePathWoQueryStr,
SUM(GA4_PP.hit) AS hit
FROM ga4DataPagePath AS GA4_PP
WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
GROUP BY pagePathWoQueryStr

# 4단계, subQuery를 이용
SELECT *
FROM (
    SELECT 
    IF(
        INSTR(GA4_PP.pagePath, '?') = 0,
        GA4_PP.pagePath,
        SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
    ) AS pagePathWoQueryStr,
    SUM(GA4_PP.hit) AS hit
    FROM ga4DataPagePath AS GA4_PP
    WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
    GROUP BY pagePathWoQueryStr
) AS GA4_PP;

# 5단계, subQuery를 이용해서 나온결과를 다시 재편집
SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, "/article_detail_", ""), ".html", "") AS UNSIGNED) AS articleId,
hit
FROM (
    SELECT 
    IF(
        INSTR(GA4_PP.pagePath, '?') = 0,
        GA4_PP.pagePath,
        SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
    ) AS pagePathWoQueryStr,
    SUM(GA4_PP.hit) AS hit
    FROM ga4DataPagePath AS GA4_PP
    WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
    GROUP BY pagePathWoQueryStr
) AS GA4_PP;

# 게시물 테이블에 조회수 칼럼 추가
ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL;

# 구글 애널리틱스에서 가져온 데이터를 기반으로 모든 게시물의 hit 정보를 갱신

# 1단계, 조인
SELECT AR.id, AR.hitCount, GA4_PP.hit
FROM article AS AR
INNER JOIN (
    SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId,
    hit
    FROM (
        SELECT 
        IF(
            INSTR(GA4_PP.pagePath, '?') = 0,
            GA4_PP.pagePath,
            SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
        ) AS pagePathWoQueryStr,
        SUM(GA4_PP.hit) AS hit
        FROM ga4DataPagePath AS GA4_PP
        WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
        GROUP BY pagePathWoQueryStr
    ) AS GA4_PP
) AS GA4_PP
ON AR.id = GA4_PP.articleId;

# 2단계, 실제 쿼리
UPDATE article AS AR
INNER JOIN (
    SELECT CAST(REPLACE(REPLACE(GA4_PP.pagePathWoQueryStr, '/article_detail_', ''), '.html', '') AS UNSIGNED) AS articleId,
    hit
    FROM (
        SELECT 
        IF(
            INSTR(GA4_PP.pagePath, '?') = 0,
            GA4_PP.pagePath,
            SUBSTR(GA4_PP.pagePath, 1, INSTR(GA4_PP.pagePath, '?') - 1)
        ) AS pagePathWoQueryStr,
        SUM(GA4_PP.hit) AS hit
        FROM ga4DataPagePath AS GA4_PP
        WHERE GA4_PP.pagePath LIKE '/article_detail_%.html%'
        GROUP BY pagePathWoQueryStr
    ) AS GA4_PP
) AS GA4_PP
ON AR.id = GA4_PP.articleId
SET AR.hitCount = GA4_PP.hit;

# 태그 테이블
CREATE TABLE tag (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    relTypeCode CHAR(20) NOT NULL,
    relId INT(10) UNSIGNED NOT NULL,
    `body` CHAR(20) NOT NULL
);

# 아래 쿼리와 관련된 인덱스 걸기
# select * from tag where where relTypeCode = 'article' AND `body` = 'SQL';
ALTER TABLE `textBoard`.`tag` ADD INDEX (`relTypeCode`, `body`); 

# 아래 쿼리와 관련된 인덱스 걸기
# 중복된 데이터 생성 금지
# select * from tag where where relTypeCode = 'article';
# select * from tag where where relTypeCode = 'article' AND relId = 5;
# select * from tag where where relTypeCode = 'article' AND relId = 5 AND `body` = 'SQL';
ALTER TABLE `textBoard`.`tag` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `body`); 

# 2번글에 `SQL`과, `INSERT`, `DB` 라는 태그 걸기
INSERT INTO tag
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 2,
`body` = 'SQL';

INSERT INTO tag
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 2,
`body` = 'INSERT';

INSERT INTO tag
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 2,
`body` = 'DB';

# 게시물 + 태그정보
SELECT A.id,
A.title,
IFNULL(GROUP_CONCAT(T.body), '') AS tags
FROM article AS A
LEFT JOIN tag AS T
ON A.id = T.relId
AND T.relTypeCode = 'article'
GROUP BY A.id;