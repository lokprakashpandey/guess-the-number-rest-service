-- Production database
create database if not exists GuessTheNumberDB;
use GuessTheNumberDB;
create table if not exists Games (
id int auto_increment primary key,
answer char(4) not null,
status varchar(20) not null
);
create table if not exists Rounds (
id int auto_increment primary key,
gameId int not null,
guess char(4) not null,
guessTime timestamp not null,
result char(7) not null,
constraint RoundsGamesFk foreign key (gameId) references Games(id)
);
-- some queries against production database
use GuessTheNumberDB;
select * from Games;
select * from Rounds;
drop database GuessTheNumberDB;

-- Test database
create database if not exists GuessTheNumberDBtest;
use GuessTheNumberDBtest;
create table if not exists Games (
id int auto_increment primary key,
answer char(4) not null,
status varchar(20) not null
);
create table if not exists Rounds (
id int auto_increment primary key,
gameId int not null,
guess char(4) not null,
guessTime timestamp not null,
result char(7) not null,
constraint RoundsGamesFk foreign key (gameId) references Games(id)
);
-- some queries against test database
use GuessTheNumberDBtest;
select * from games;
select * from rounds;
insert into games values (1, "1234", "In progress");
insert into rounds values (1,1,"5678",now(),"e:0:p:0");
insert into rounds values (2,1,"1234",now(),"e:4:p:0");
delete from games where id=1; -- gives error
delete from rounds where id=1;
delete from rounds where id=2;
delete from games where id=101; -- succeeds
drop database GuessTheNumberDBtest;

