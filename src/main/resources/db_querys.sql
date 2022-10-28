SELECT
    users u
FROM
    verses
WHERE
    head in (:headList)
ORDER BY
    rand()
    limit :n

UPDATE
    users u
SET
    u.nickname = "상민박"
WHERE
    u.nickname = "박상민"


SELECT
    user0_.user_id,
    authority2_.authority_name,
    user0_.activated,
    user0_.nickname,
    user0_.password,
    user0_.username,
    authoritie1_.user_id,
    authoritie1_.authority_name
FROM
    users user0_
        LEFT OUTER JOIN
    user_authority authoritie1_
    ON user0_.user_id=authoritie1_.user_id
        LEFT OUTER JOIN
    authority authority2_
    ON authoritie1_.authority_name=authority2_.authority_name
WHERE
        user0_.username="sanggoe"