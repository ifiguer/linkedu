drop table Project353.Users;

create table Project353.Users(

USERID    VARCHAR(25),
PASSWORD    VARCHAR(64),
FIRSTNAME   VARCHAR(25),
LASTNAME    VARCHAR(25),
EMAIL       VARCHAR(25),
SECQUESTION VARCHAR(50),
SECANSWER   VARCHAR(50)


);

insert into Project353.Users values('testerman95','testpw','Joey','Test','test@test.com','What is your occupation','tester');