create database gymDB;
use gymdb;

drop database gymdb;

create table sexo(
id int auto_increment not null primary key,
sexo varchar(50)
);

insert into sexo (sexo) values ("Masculino");
insert into sexo (sexo) values ("Femenino");

select * from sexo;

create table clientes(
id int auto_increment not null primary key,
nombre varchar(100),
apellido varchar(100),
fksexo int,
edad int,
fechapago date,
foto longblob,

foreign key (fksexo) references sexo(id) on delete cascade on update cascade

);

select * from clientes;

select clientes.id, clientes.nombre, clientes.apellido, sexo.sexo, clientes.edad, clientes.fechapago, clientes.foto
from clientes inner join sexo on clientes.fksexo = sexo.id;

create table usuarios (
    id int auto_increment primary key,
    username varchar(50) unique not null,
    password varchar(100) not null
);

insert into usuarios (username, password) values ('admin', 'admin');

select * from usuarios where username = ? and password = ?


