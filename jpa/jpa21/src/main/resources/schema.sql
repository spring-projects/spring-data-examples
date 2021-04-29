DROP procedure IF EXISTS plus1inout
    /;
CREATE procedure plus1inout(IN arg int, OUT res int)
BEGIN ATOMIC
set res = arg + 1;
END
/;

DROP table subscription IF EXISTS
    /;
CREATE TABLE subscription
(
    id IDENTITY,
    product_name VARCHAR(255),
    user_id      INT
)
    /;


