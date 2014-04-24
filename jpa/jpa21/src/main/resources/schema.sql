DROP procedure IF EXISTS plus1inout
/;
CREATE procedure plus1inout (IN arg int, OUT res int)  
BEGIN ATOMIC  
	set res = arg + 1; 
END
/;
