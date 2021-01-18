# `SQL` 태그가 붙어있는 모든 게시물(출력용) 리스트
SELECT A.*
, M.name AS extra__writer
, B.name AS extra__boardName
, B.code AS extra__boardCode
FROM article AS A
INNER JOIN `member` AS M
ON A.memberId = M.id
INNER JOIN `board` AS B
ON A.boardId = B.id
INNER JOIN `tag` AS T
ON T.relTypeCode = 'article'
AND A.id = T.relId
WHERE T.body = "SQL"
ORDER BY A.id DESC;

# 게시글에 붙어있는 태그들 확인
SELECT A.id, A.title, GROUP_CONCAT(T.body)
FROM article AS A
LEFT JOIN tag AS T
ON T.relTypeCode = 'article'
AND A.id = T.relId
GROUP BY A.id;