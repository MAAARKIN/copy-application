drop table user if exists;
create table user (
  id integer primary key GENERATED BY DEFAULT AS IDENTITY(START WITH 100),
  name varchar(50),
  age integer
);