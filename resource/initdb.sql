create schema universityDB;

create table universityDB.Student
(
    studentName  varchar(250) not null,
    studyprogram varchar(250) not null,
    id           int auto_increment,
    constraint Student_pk
        primary key (id)
);

create table universityDB.Guest
(
    guestName varchar(250) not null,
    inviter   varchar(250) not null,
    id        int auto_increment,
    constraint Guest_pk
        primary key (id)
);

create table universityDB.Participants
(
    role         varchar(250) not null,
    name         varchar(250) not null,
    studyprogram varchar(250) not null,
    id           int auto_increment,
    constraint Participants_pk
        primary key (id)
);
alter table Student
    add guestInvited int null;


