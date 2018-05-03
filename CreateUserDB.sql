drop table Project353.Users;

create table Project353.Users(

USERID    			VARCHAR(64),
PASSWORD    		VARCHAR(64),
FIRSTNAME   		VARCHAR(64),
LASTNAME    		VARCHAR(64),
EMAIL       		VARCHAR(64),
PROFILEURL			VARCHAR(64),
FOLLOWING			VARCHAR(1000),
GRADDETAILS			VARCHAR(50),
HIGHSCHOOLDETAILS	VARCHAR(200),
ADMINISTRATOR		BOOLEAN,
ACT   				INTEGER,
SAT					INTEGER	


);

insert into Project353.users values ('admin','secure','LinkedU','Admin','bmjame1@ilstu.edu','img/egg.jpg','','May 2019','Harvard High',true,'999','9999');
