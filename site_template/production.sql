INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`name`='SQL',
`code`='it';

# 사용자 전부삭제
TRUNCATE `member`;

# 사용자 추가
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
userId = 'admin',
passwd = 'admin',
`name` = '이주영';

# 글 전부삭제
TRUNCATE article;

SELECT * FROM `member`;
SELECT * FROM article;
