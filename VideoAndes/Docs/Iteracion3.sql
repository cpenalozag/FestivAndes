CREATE TABLE USUARIOS
(
    ID NUMBER NOT NULL,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    CORREO VARCHAR(255 BYTE) NOT NULL,
    CONTRASENA VARCHAR(255 BYTE) NOT NULL,
    ROL VARCHAR(255 BYTE) NOT NULL,
    CONSTRAINT USUARIOS_PK PRIMARY KEY (ID)
);

Insert into USUARIOS (ID,NOMBRE,CORREO,CONTRASENA,ROL) values ('1','Gory','goryhotmail.com','estrf45','1');
Insert into USUARIOS (ID,NOMBRE,CORREO,CONTRASENA,ROL) values ('2','Carlos','carlos@hotmail.com','stwert54','1');
Insert into USUARIOS (ID,NOMBRE,CORREO,CONTRASENA,ROL) values ('3','Camilo','camilo@hotmail.com','45awf5w','2');
Insert into USUARIOS (ID,NOMBRE,CORREO,CONTRASENA,ROL) values ('4','Sergio','sergiperge@hotmail.com','09fg8sd','2');
Insert into USUARIOS (ID,NOMBRE,CORREO,CONTRASENA,ROL) values ('5','Maya','maya@hotmail.com','t657ey','3');
Insert into USUARIOS (ID,NOMBRE,CORREO,CONTRASENA,ROL) values ('6','Salomon','salomon@hotmail.com','c43byr','4');
Insert into USUARIOS (ID,NOMBRE,CORREO,CONTRASENA,ROL) values ('7','Paula la Loca','pauloca@hotmail.com','wrcwcr','4');

CREATE TABLE TIPOUSUARIO
(
    ID NUMBER NOT NULL,
    NOMBRE VARCHAR2(255 BYTE),
    CONSTRAINT TIPOUSUARIO_PK PRIMARY KEY (ID)
);

Insert into TIPOUSUARIO (ID,NOMBRE) values ('1','Administrador');
Insert into TIPOUSUARIO (ID,NOMBRE) values ('2','Organizador');
Insert into TIPOUSUARIO (ID,NOMBRE) values ('3','Operador');
Insert into TIPOUSUARIO (ID,NOMBRE) values ('4','Cliente');

CREATE TABLE ESTADIACOMPANIA
(
    ID NUMBER NOT NULL,
    FECHALLEGADA VARCHAR2(255 BYTE),
    FECHASALIDA VARCHAR2(255 BYTE),
    CONSTRAINT ESTADIACOMPANIA_PK PRIMARY KEY (ID),
    CONSTRAINT CHCKFECHAS CHECK (FECHASALIDA>FECHALLEGADA)
);

Insert into ESTADIACOMPANIA (ID,FECHALLEGADA,FECHASALIDA) values ('1','07/10/17', '07/15/17');
Insert into ESTADIACOMPANIA (ID,FECHALLEGADA,FECHASALIDA) values ('2','07/15/17', '07/25/17');
Insert into ESTADIACOMPANIA (ID,FECHALLEGADA,FECHASALIDA) values ('3','09/23/17', '10/12/17');
Insert into ESTADIACOMPANIA (ID,FECHALLEGADA,FECHASALIDA) values ('4','08/25/17', '09/12/17');

CREATE TABLE COMPANIAS
(
    ID NUMBER NOT NULL,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    PAIS VARCHAR2(255 BYTE) NOT NULL,
    REPRESENTANTE VARCHAR2(255 BYTE) NOT NULL,
    PAGINAWEB VARCHAR2(255 BYTE),
    CIUDADORIGEN VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT COMPANIAS_PK PRIMARY KEY (ID),
    CONSTRAINT COMPCOMPESTADIA_FK FOREIGN KEY (ID) REFERENCES ESTADIACOMPANIA(ID)
);

Insert into COMPANIAS (ID, NOMBRE, PAIS, REPRESENTANTE, PAGINAWEB, CIUDADORIGEN) 
    values ('1', 'Teatro Bogota', 'Colombia', 'Camilo','teatrobog.com', 'Bogota');
Insert into COMPANIAS (ID, NOMBRE, PAIS, REPRESENTANTE, PAGINAWEB, CIUDADORIGEN) 
    values ('2', 'Teatro Cali', 'Colombia', 'JaJaime','teatrocali.com', 'Cali');
Insert into COMPANIAS (ID, NOMBRE, PAIS, REPRESENTANTE, PAGINAWEB, CIUDADORIGEN) 
    values ('3', 'Cirque du soleil', 'Francia', 'Remi','cds.com', 'Lyon');
Insert into COMPANIAS (ID, NOMBRE, PAIS, REPRESENTANTE, PAGINAWEB, CIUDADORIGEN) 
    values ('4', 'Ome Teatro Ome', 'Colombia', 'Maluma','teatrome.com', 'Medellin');

CREATE TABLE COMPANIA_ORGANIZADOR
(
    IDORGANIZADOR NUMBER NOT NULL,
    IDCOMPANIA NUMBER NOT NULL,
    CONSTRAINT COCOMPANIA_FK FOREIGN KEY (IDCOMPANIA) REFERENCES COMPANIAS(ID),
    CONSTRAINT COUSUARIO_FK FOREIGN KEY (IDORGANIZADOR) REFERENCES USUARIOS(ID),
    CONSTRAINT COMPANIA_ORGANIZADOR_PK PRIMARY KEY (IDCOMPANIA,IDORGANIZADOR)
);

Insert into COMPANIA_ORGANIZADOR (IDORGANIZADOR, IDCOMPANIA) values ('3','1');
Insert into COMPANIA_ORGANIZADOR (IDORGANIZADOR, IDCOMPANIA) values ('3','2');
Insert into COMPANIA_ORGANIZADOR (IDORGANIZADOR, IDCOMPANIA) values ('4','3');
Insert into COMPANIA_ORGANIZADOR (IDORGANIZADOR, IDCOMPANIA) values ('4','4');

CREATE TABLE TIPOSITIO
(
    ID NUMBER NOT NULL,
    NOMBRE VARCHAR2(255 BYTE),
    CONSTRAINT TIPOSITIO_PK PRIMARY KEY (ID)
);

Insert into TIPOSITIO (ID,NOMBRE) values ('1','Teatro');
Insert into TIPOSITIO (ID,NOMBRE) values ('2','Auditorio');
Insert into TIPOSITIO (ID,NOMBRE) values ('3','Coliseo');
Insert into TIPOSITIO (ID,NOMBRE) values ('4','Parque');
Insert into TIPOSITIO (ID,NOMBRE) values ('5','Plaza');


CREATE TABLE SITIO
(
    ID NUMBER NOT NULL,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    CAPACIDAD NUMBER NOT NULL,
    TIPO VARCHAR2(255 BYTE) NOT NULL,
    HORAINICIO NUMBER NOT NULL,
    HORAFIN NUMBER NOT NULL,
    IDORGANIZADOR NUMBER NOT NULL,
    CONSTRAINT SITIOUSUARIO_FK FOREIGN KEY (IDORGANIZADOR) REFERENCES USUARIOS(ID),
    CONSTRAINT SITIO_PK PRIMARY KEY (ID)
);

Insert into SITIO (ID,NOMBRE,CAPACIDAD,TIPO,HORAINICIO,HORAFIN,IDORGANIZADOR) 
    values ('1', 'Teatro de Bogota', '100', '1','700', '1900', '3');
Insert into SITIO (ID,NOMBRE,CAPACIDAD,TIPO,HORAINICIO,HORAFIN,IDORGANIZADOR) 
    values ('2', 'Coliseo Cubierto el Campin', '140', '3','800', '2200','4');

CREATE TABLE ESPECTACULO
(
    ID NUMBER NOT NULL,
    NOMBRE VARCHAR2 (255 BYTE) NOT NULL,
    DURATION NUMBER NOT NULL,
    IDIOMA VARCHAR2 (255 BYTE) NOT NULL,
    DESCRIPCION VARCHAR2(255 BYTE) NOT NULL,
    COSTOREALIZACION NUMBER NOT NULL,
    OTROIDIOMA CHAR(1) NOT NULL,
    IDORGANIZADOR NUMBER NOT NULL,
    CONSTRAINT ESPECTACULO_PK PRIMARY KEY (ID),
    CONSTRAINT ESPECTACULOUSUARIO_FK FOREIGN KEY (IDORGANIZADOR) REFERENCES USUARIOS(ID)
);

Insert into ESPECTACULO (ID,NOMBRE,DURATION,IDIOMA,DESCRIPCION,COSTOREALIZACION,OTROIDIOMA,IDORGANIZADOR) 
  values ('1','espectaculo1','100','espanol','espectaculo en espanol de drama','10000000', 't','2');
Insert into ESPECTACULO (ID,NOMBRE,DURATION,IDIOMA,DESCRIPCION,COSTOREALIZACION,OTROIDIOMA,IDORGANIZADOR) 
  values ('2','espectaculo2','90','frances','espectaculo en frances ooaaoo','45000000', 't','4');
Insert into ESPECTACULO (ID,NOMBRE,DURATION,IDIOMA,DESCRIPCION,COSTOREALIZACION,OTROIDIOMA,IDORGANIZADOR) 
  values ('3','espectaculo3','85','espanol','espectaculo en espanol muy bueno salu2','5000000', 'f','1');  
Insert into ESPECTACULO (ID,NOMBRE,DURATION,IDIOMA,DESCRIPCION,COSTOREALIZACION,OTROIDIOMA,IDORGANIZADOR) 
  values ('4','espectaculo4','45','ingl�s','espectaculo en ingles de drama','2000000', 'f','2');

    
CREATE TABLE FUNCION
(
    ID NUMBER NOT NULL,
    IDESPECTACULO NUMBER NOT NULL,
    IDSITIO NUMBER NOT NULL,
    DIA VARCHAR2(255 BYTE) NOT NULL,
    HORA NUMBER NOT NULL,
    REALIZADA CHAR(1) NOT NULL,
    CONSTRAINT FUNCION_PK PRIMARY KEY (ID, IDESPECTACULO),
    CONSTRAINT FUNCIONESPECTACULO_FK FOREIGN KEY (IDESPECTACULO) REFERENCES ESPECTACULO(ID),
    CONSTRAINT FUNCIONSITIO_FK FOREIGN KEY (IDSITIO) REFERENCES SITIO(ID)
);

Insert into FUNCION (ID,IDESPECTACULO,IDSITIO,DIA,HORA,REALIZADA) 
  values ('1','1','1','11/08/17','1300','f');
Insert into FUNCION (ID,IDESPECTACULO,IDSITIO,DIA,HORA,REALIZADA) 
  values ('2','1','2','06/08/17','1500','f');
Insert into FUNCION (ID,IDESPECTACULO,IDSITIO,DIA,HORA,REALIZADA) 
  values ('1','2','1','04/08/17','2000','t');
Insert into FUNCION (ID,IDESPECTACULO,IDSITIO,DIA,HORA,REALIZADA) 
  values ('2','2','2','08/10/17','2100','f');
Insert into FUNCION (ID,IDESPECTACULO,IDSITIO,DIA,HORA,REALIZADA) 
  values ('1','3','2','07/11/17','1400','f');

CREATE TABLE TIPOLOCALIDAD
(
    ID NUMBER NOT NULL,
    NOMBRE VARCHAR2(255 BYTE),
    CONSTRAINT TIPOLOCALIDAD_PK PRIMARY KEY (ID)
);

Insert into TIPOLOCALIDAD (ID,NOMBRE) values ('1','VIP');
Insert into TIPOLOCALIDAD (ID,NOMBRE) values ('2','Platea');
Insert into TIPOLOCALIDAD (ID,NOMBRE) values ('3','Platea lateral');
Insert into TIPOLOCALIDAD (ID,NOMBRE) values ('4','Intermedia');
Insert into TIPOLOCALIDAD (ID,NOMBRE) values ('5','Intermedia lateral');
Insert into TIPOLOCALIDAD (ID,NOMBRE) values ('6','General');

CREATE TABLE LOCALIDAD
(
    ID NUMBER NOT NULL,
    IDSITIO NUMBER NOT NULL,
    NUMERADA CHAR NOT NULL,
    PRECIO NUMBER NOT NULL,
    CAPACIDAD NUMBER NOT NULL,
    CONSTRAINT LOCALIDAD_PK PRIMARY KEY (ID,IDSITIO),
    CONSTRAINT LOCALIDADSITIO_FK FOREIGN KEY (IDSITIO) REFERENCES SITIO(ID),
    CONSTRAINT LACALIDADTL_FK FOREIGN KEY (ID) REFERENCES TIPOLOCALIDAD(ID)
);

Insert into LOCALIDAD (ID,IDSITIO,NUMERADA,PRECIO,CAPACIDAD) values ('1','1','t','200000','5');
Insert into LOCALIDAD (ID,IDSITIO,NUMERADA,PRECIO,CAPACIDAD) values ('2','1','t','150000',25);
Insert into LOCALIDAD (ID,IDSITIO,NUMERADA,PRECIO,CAPACIDAD) values ('3','1','t','100000','45');
Insert into LOCALIDAD (ID,IDSITIO,NUMERADA,PRECIO,CAPACIDAD) values ('4','1','t','75000','25');
Insert into LOCALIDAD (ID,IDSITIO,NUMERADA,PRECIO,CAPACIDAD) values ('1','2','f','30000','20');
Insert into LOCALIDAD (ID,IDSITIO,NUMERADA,PRECIO,CAPACIDAD) values ('2','2','f','25000','18');
Insert into LOCALIDAD (ID,IDSITIO,NUMERADA,PRECIO,CAPACIDAD) values ('3','2','f','20000','36');
Insert into LOCALIDAD (ID,IDSITIO,NUMERADA,PRECIO,CAPACIDAD) values ('4','2','f','17000','30');

CREATE TABLE CATEGORIA
(
    ID NUMBER NOT NULL,
    TIPO VARCHAR2 (255 BYTE) NOT NULL,
    CONSTRAINT CATEGORIA_PK PRIMARY KEY (ID)
);

Insert into CATEGORIA (ID,TIPO) 
  values ('1', 'Drama');
Insert into CATEGORIA (ID,TIPO) 
  values ('2', 'Teatro mudo');
Insert into CATEGORIA (ID,TIPO) 
  values ('3', 'Titeres');
Insert into CATEGORIA (ID,TIPO) 
  values ('4', 'Comparsas');
Insert into CATEGORIA (ID,TIPO) 
  values ('5', 'Opera');
Insert into CATEGORIA (ID,TIPO) 
  values ('6', 'Zarzuela');
Insert into CATEGORIA (ID,TIPO) 
  values ('7', 'Musical');
Insert into CATEGORIA (ID,TIPO) 
  values ('8', 'Circo');

CREATE TABLE CLIENTE
(
    ID NUMBER NOT NULL,
    EDAD NUMBER NOT NULL,
    CONSTRAINT CLIENTE_PK PRIMARY KEY (ID),
    CONSTRAINT CLIENTEUSUARIO_FK FOREIGN KEY (ID) REFERENCES USUARIOS(ID)
);
CREATE TABLE OPERADOR
(
IDUSUARIO NUMBER NOT NULL,
IDFUNCION NUMBER NOT NULL,
IDESPECTACULO NUMBER NOT NULL,
CONSTRAINT OPERADOR_PK  PRIMARY KEY (IDUSUARIO,IDFUNCION,IDESPECTACULO),
CONSTRAINT USUARIOID_FK FOREIGN KEY (IDUSUARIO) REFERENCES USUARIOS (ID),
CONSTRAINT FUNCIONID_FK FOREIGN KEY (IDFUNCION,IDESPECTACULO) REFERENCES FUNCION(ID,IDESPECTACULO)
);


INSERT INTO OPERADOR(IDUSUARIO, IDFUNCION,IDESPECTACULO) VALUES ('3','1','1');

Insert into CLIENTE (ID, EDAD) values ('6','19');
Insert into CLIENTE (ID, EDAD) values ('7','21');

CREATE TABLE PREFERENCIA
(
    IDCliente NUMBER NOT NULL,
    IDCategoria NUMBER NOT NULL,
    CONSTRAINT PREFERENCIA_PK PRIMARY KEY (IDCliente, IDCategoria),
    CONSTRAINT PREFCATEGORIA_FK FOREIGN KEY (IDCategoria) REFERENCES CATEGORIA (ID),
    CONSTRAINT PREFERENCIACLIENTE_FK FOREIGN KEY (IDCliente) REFERENCES CLIENTE (ID)
);

Insert into PREFERENCIA (IDCliente,IDCategoria) values ('6','1');
Insert into PREFERENCIA (IDCliente,IDCategoria) values ('6','2');
Insert into PREFERENCIA (IDCliente,IDCategoria) values ('7','4');
Insert into PREFERENCIA (IDCliente,IDCategoria) values ('7','6');

CREATE TABLE SILLA
(
    ID NUMBER NOT NULL,
    IDSITIO NUMBER NOT NULL,
    IDLOCALIDAD NUMBER NOT NULL,
    FILA NUMBER,
    NUMERO NUMBER,
    CONSTRAINT SILLA_PK PRIMARY KEY (ID,IDSITIO),
    CONSTRAINT SILLALOCALIDAD_FK FOREIGN KEY (IDLOCALIDAD,IDSITIO) REFERENCES LOCALIDAD (ID,IDSITIO)
);

Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('1','1','1','1','1');
Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('2','1','1','1','2');
Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('3','1','2','2','3');
Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('4','1','2','2','1');
Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('5','1','3','3','2');
Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('6','1','4','3','3');

Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('1','2','1','','');
Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('2','2','1','','');
Insert into SILLA (ID, IDSITIO, IDLOCALIDAD, FILA, NUMERO) values ('3','2','2','','');

insert into Silla values ('7','1', '4','1','12');

insert into silla values('8','1','1','1','5');
insert into silla values('9','1','1','1','6');
insert into silla values ('10',1,1,1,7);

insert into silla values (11,1,1,1,8);
insert into silla values (12,1,1,1,9);
insert into silla values (13,1,1,1,10);

insert into silla values(14,1,1,1,11);
insert into silla values(4,2,1,'','');

insert into silla values (15,1,1,1,15);
insert into silla values (16,1,1,1,16);
insert into silla values (17,1,1,2,17);
insert into silla values (18,1,1,2,18);
insert into silla values (19,1,1,2,19);


CREATE TABLE BOLETA
(
ID NUMBER NOT NULL,
IDFUNCION NUMBER NOT NULL,
IDESPECTACULO NUMBER NOT NULL,
ASISTENCIA char(1),
CANCELADA char(1),
ABONADA char(1),
CONSTRAINT BOLETA_PK PRIMARY KEY (ID),
CONSTRAINT FUNCIONB_FK FOREIGN KEY (IDFUNCION,IDESPECTACULO) REFERENCES FUNCION(ID,IDESPECTACULO)
);


Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO,ASISTENCIA,CANCELADA,ABONADA) values ('1','1','1','f','f','f');
Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO,ASISTENCIA,CANCELADA,ABONADA) values ('2','1','2','t','f','f');
Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO,ASISTENCIA,CANCELADA,ABONADA) values ('3','1','1','f','f','f');
Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO,ASISTENCIA,CANCELADA,ABONADA) values ('4','1','2','t','f','f');
Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO,ASISTENCIA,CANCELADA,ABONADA) values ('5','1','1','f','f','f');

Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO,ASISTENCIA,CANCELADA,ABONADA) values ('7','2','1','f','f','f');

CREATE TABLE BOLETA_DETALLE
(
    ID NUMBER NOT NULL,
    IDCLIENTE NUMBER,
    ESCLIENTE CHAR NOT NULL,
    CONSTRAINT BOLETADET_PK PRIMARY KEY (ID),
    CONSTRAINT BOLETACLIENTE_FK FOREIGN KEY (IDCLIENTE) REFERENCES USUARIOS(ID),
    CONSTRAINT BDETALLEB_FK FOREIGN KEY (ID) REFERENCES BOLETA(ID)
);

Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('1','','f');
Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('2','6','t');
Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('3','','f');
Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('4','','f');
Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('5','','f');

Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('7','7','t');

CREATE TABLE BOLETA_SILLA
(
    ID NUMBER NOT NULL,
    IDSITIO NUMBER  NOT NULL,
    IDSILLA NUMBER NOT NULL,
    CONSTRAINT BOLETA_SILLA_PK PRIMARY KEY (ID),
    CONSTRAINT SILLABS_FK FOREIGN KEY (IDSILLA,IDSITIO) REFERENCES SILLA(ID,IDSITIO),
    CONSTRAINT BBOLETASILLA_FK FOREIGN KEY (ID) REFERENCES BOLETA(ID)
);

Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('1','1','1');
Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('2','1','2');
Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('3','1','5');
Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('4','1','4');
Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('5','1','6');

Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('7','2','1');



CREATE TABLE PUBLICO
(
    ID NUMBER NOT NULL,
    TIPO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT PUBLICO_PK PRIMARY KEY (ID)
);
Insert into PUBLICO (ID,TIPO) 
  values ('1', 'Adultos');
Insert into PUBLICO (ID,TIPO) 
  values ('2', 'Infantil');
Insert into PUBLICO (ID,TIPO) 
  values ('3', 'Familiar');
Insert into PUBLICO (ID,TIPO) 
  values ('4', 'Jovenes');
Insert into PUBLICO (ID,TIPO) 
  values ('5', 'Todos');

CREATE TABLE PUBLICO_ESPECTACULO
(
    IDPUBLICO NUMBER NOT NULL,
    IDESPECTACULO NUMBER NOT NULL,
    CONSTRAINT PEPUBLICO_FK FOREIGN KEY (IDPUBLICO) REFERENCES PUBLICO (ID),
    CONSTRAINT PEESPECTACULO_FK FOREIGN KEY (IDESPECTACULO) REFERENCES ESPECTACULO (ID),
    CONSTRAINT PUBLICO_ESPECTACULO_PK PRIMARY KEY (IDPUBLICO,IDESPECTACULO)
);

Insert into PUBLICO_ESPECTACULO (IDPUBLICO,IDESPECTACULO) 
  values ('1', '1');
Insert into PUBLICO_ESPECTACULO (IDPUBLICO,IDESPECTACULO) 
  values ('2', '2');
Insert into PUBLICO_ESPECTACULO (IDPUBLICO,IDESPECTACULO) 
  values ('3', '1');  
Insert into PUBLICO_ESPECTACULO (IDPUBLICO,IDESPECTACULO) 
  values ('4', '3');

CREATE TABLE ESPECTACULO_CATEGORIA
(
    IDESPECTACULO NUMBER NOT NULL,
    IDCATEGORIA NUMBER NOT NULL,
    CONSTRAINT ESPECTACULO_CATEGORIA_PK PRIMARY KEY (IDESPECTACULO,IDCATEGORIA),
    CONSTRAINT ECESPECTACULO_FK FOREIGN KEY (IDESPECTACULO) REFERENCES ESPECTACULO (ID),
    CONSTRAINT ECCATEGORIA_FK FOREIGN KEY (IDCATEGORIA) REFERENCES CATEGORIA (ID)
);

Insert into ESPECTACULO_CATEGORIA (IDESPECTACULO,IDCATEGORIA) 
  values ('1', '1');
Insert into ESPECTACULO_CATEGORIA (IDESPECTACULO,IDCATEGORIA) 
  values ('1', '5');
Insert into ESPECTACULO_CATEGORIA (IDESPECTACULO,IDCATEGORIA) 
  values ('2', '4');
Insert into ESPECTACULO_CATEGORIA (IDESPECTACULO,IDCATEGORIA) 
  values ('3', '2');
Insert into ESPECTACULO_CATEGORIA (IDESPECTACULO,IDCATEGORIA) 
  values ('4', '1');

CREATE TABLE REQUERIMIENTOSTECNICOS
(
    ID NUMBER NOT NULL,
    TIPO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT REQUERIMIENTOSTECNICOS_PK PRIMARY KEY (ID)
);

Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('1', 'amplificacion de sonido');
Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('2', 'efectos de luz');  
Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('3', 'fuegos artificiales');
Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('4', 'efectos 4D');
Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('5', 'efectos 3D');
Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('6', 'interaccion con el publico');
Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('7', 'efectos de altura');
Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('8', 'instalacion de escenario propio');
Insert into REQUERIMIENTOSTECNICOS (ID,TIPO) 
  values ('9', 'instalacion propia'); 

CREATE TABLE NECESIDADESESPECIALES
(
    ID NUMBER NOT NULL,
    TIPO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT NECESIDADESESPECIALES_PK PRIMARY KEY (ID)
);

Insert into NECESIDADESESPECIALES (ID,TIPO) 
  values ('1', 'menores de edad');
Insert into NECESIDADESESPECIALES (ID,TIPO) 
  values ('2', 'restricciones de movilidad');
Insert into NECESIDADESESPECIALES (ID,TIPO) 
  values ('3', 'restricciones de movilidad');
Insert into NECESIDADESESPECIALES (ID,TIPO) 
  values ('4', 'personas mayores');
 

CREATE TABLE SITIO_NECESIDADES
(
    IDSITIO NUMBER NOT NULL,
    IDNECESIDADES NUMBER NOT NULL,
    CONSTRAINT SNSITIO_FK FOREIGN KEY (IDSITIO) REFERENCES SITIO(ID),
    CONSTRAINT SNNECESIDADESESPECIALES_FK FOREIGN KEY (IDNECESIDADES) REFERENCES NECESIDADESESPECIALES(ID),
    CONSTRAINT SITIO_NECESIDADES_PK PRIMARY KEY (IDSITIO,IDNECESIDADES)
);

Insert into SITIO_NECESIDADES (IDSITIO,IDNECESIDADES) 
  values ('1', '1');
Insert into SITIO_NECESIDADES (IDSITIO,IDNECESIDADES) 
  values ('1', '2');
Insert into SITIO_NECESIDADES (IDSITIO,IDNECESIDADES) 
  values ('1', '3');
Insert into SITIO_NECESIDADES (IDSITIO,IDNECESIDADES) 
  values ('1', '4');
Insert into SITIO_NECESIDADES (IDSITIO,IDNECESIDADES) 
  values ('2', '1');
Insert into SITIO_NECESIDADES (IDSITIO,IDNECESIDADES) 
  values ('2', '2');
Insert into SITIO_NECESIDADES (IDSITIO,IDNECESIDADES) 
  values ('2', '3');  
Insert into SITIO_NECESIDADES (IDSITIO,IDNECESIDADES) 
  values ('2', '4');

CREATE TABLE SITIO_REQUERIMIENTOS
(
    IDREQ NUMBER NOT NULL,
    IDSITIO NUMBER NOT NULL,
    CONSTRAINT SRSI2TIO_FK FOREIGN KEY (IDSITIO) REFERENCES SITIO(ID),
    CONSTRAINT SRREQUERIMIENTOSTECNICOS_FK FOREIGN KEY (IDREQ) REFERENCES REQUERIMIENTOSTECNICOS(ID),
    CONSTRAINT SITIO_REQUERIMIENTOS_PK PRIMARY KEY(IDREQ,IDSITIO)
);

Insert into SITIO_REQUERIMIENTOS (IDSITIO,IDREQ)
  values ('1', '1');
Insert into SITIO_REQUERIMIENTOS (IDSITIO,IDREQ)
  values ('1', '2');
Insert into SITIO_REQUERIMIENTOS (IDSITIO,IDREQ)
  values ('1', '3');
Insert into SITIO_REQUERIMIENTOS (IDSITIO,IDREQ)
  values ('1', '5');
Insert into SITIO_REQUERIMIENTOS (IDSITIO,IDREQ) 
  values ('2', '1');
Insert into SITIO_REQUERIMIENTOS (IDSITIO,IDREQ) 
  values ('2', '3');
Insert into SITIO_REQUERIMIENTOS (IDSITIO,IDREQ) 
  values ('2', '7');

CREATE TABLE ELEMENTOEXTRA
(
    ID NUMBER NOT NULL,
    TIPO VARCHAR2 (255 BYTE) NOT NULL,
    CONSTRAINT ELEMENTOEXTRA_PK PRIMARY KEY (ID)
);

Insert into ELEMENTOEXTRA (ID,TIPO) 
  values ('1', 'Agua');
Insert into ELEMENTOEXTRA (ID,TIPO) 
  values ('2', 'Espuma');
Insert into ELEMENTOEXTRA (ID,TIPO) 
  values ('3', 'Vidrio');
Insert into ELEMENTOEXTRA (ID,TIPO) 
  values ('4', 'Montaje escenario');
Insert into ELEMENTOEXTRA (ID,TIPO) 
  values ('5', 'Herramientas');
Insert into ELEMENTOEXTRA (ID,TIPO) 
  values ('6', 'Elementos visuales');


CREATE TABLE ELEMENTOEXTRA_ESPECTACULO
(
    IDESPECTACULO NUMBER NOT NULL,
    IDELEMENTOEXTRA NUMBER NOT NULL,
    CONSTRAINT EEESPECTACULO_FK FOREIGN KEY (IDESPECTACULO) REFERENCES ESPECTACULO (ID),
    CONSTRAINT EEELEMENTOEXTRA_FK FOREIGN KEY (IDELEMENTOEXTRA) REFERENCES ELEMENTOEXTRA (ID),
    CONSTRAINT ELEMENTO_ESPECTACULO_PK PRIMARY KEY (IDESPECTACULO,IDELEMENTOEXTRA)
);

Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('1', '1');
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('1', '2');
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('1', '6');
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('2', '1');
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('2', '3');  
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('3', '4');
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('3', '1');
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('4', '1');
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('4', '2');
Insert into ELEMENTOEXTRA_ESPECTACULO (IDESPECTACULO,IDELEMENTOEXTRA) 
  values ('4', '5');

CREATE TABLE COMPANIA_ESPECTACULO
 (
 IDCOMPANIA NUMBER NOT NULL,
 IDESPECTACULO NUMBER NOT NULL,
 constraint COMPANIA_ESPECTACULO_PK PRIMARY KEY (IDCOMPANIA,IDESPECTACULO),
 CONSTRAINT COMPANIAES_FK FOREIGN KEY (IDCOMPANIA)REFERENCES  COMPANIAS (ID),
  CONSTRAINT ESPECTACULOES_FK FOREIGN KEY (IDESPECTACULO)REFERENCES  ESPECTACULO (ID)
 );
 
 INSERT INTO COMPANIA_ESPECTACULO (IDCOMPANIA,IDESPECTACULO)
 VALUES ('1','1');
 
INSERT INTO COMPANIA_ESPECTACULO (IDCOMPANIA,IDESPECTACULO)
 VALUES ('1','2');
 
INSERT INTO COMPANIA_ESPECTACULO (IDCOMPANIA,IDESPECTACULO)
 VALUES ('3','3'); 

INSERT INTO COMPANIA_ESPECTACULO (IDCOMPANIA,IDESPECTACULO)
 VALUES ('4','4'); 