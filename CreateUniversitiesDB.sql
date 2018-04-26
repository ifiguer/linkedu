drop table Project353.Universities;

create table Project353.Universities(

NAME    VARCHAR(25),
DESCRIPTION    VARCHAR(256),
FEATURED		BOOLEAN

);

insert into Project353.Universities values('ISU','Go Redbirds!', true);
insert into Project353.Universities values('UIUC','This is a sample description.', false);