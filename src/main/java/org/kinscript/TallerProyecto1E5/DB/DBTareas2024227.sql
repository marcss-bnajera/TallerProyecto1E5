drop database if exists DBTareas2024227;
create database DBTareas2024227;
use DBTareas2024227;

create table Tareas (
	codigoTarea integer auto_increment,
    nombre varchar(200),
    descripcion varchar(200),
    fechaLimite date not null,
    estado enum("Pendiente", "Completada"),
    
    constraint pk_tarea primary key (codigoTarea)
);

insert into Tareas (nombre, descripcion, fechaLimite, estado)
values
("Limpiar el cuarto", "Levantar la basura del cuarto, recoger la ropa y ordenar los zapatos", "2025-08-19", "Pendiente"),
("Lavar el carro", "Darle una limpiada al carro por fuera y por dentro", "2025-08-20", "Pendiente"),
("Hacer los pagos", "Hacer los pagos de luz, agua, etc.", "2025-08-31", "Pendiente");

select * from Tareas;