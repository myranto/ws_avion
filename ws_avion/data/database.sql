create database avion;
\c avion;

create table Avion(
  id serial PRIMARY key,
  nom VARCHAR(30) not null,
  Photo text not null
);

create TABLE kilometrage(
    idavion int not null REFERENCES Avion(id),
    date_Kil date not NULL CHECK (date_Kil <= CURRENT_DATE),
    debutKM DOUBLE PRECISION not null check (debutKM >= 0),
    finKM DOUBLE PRECISION not null check (finKM >= debutKM)
);

create table Assurance(
  id serial primary key,
  date_assurance date not null CHECK (date_assurance >= CURRENT_DATE),
  a_payer double precision not null check (a_payer >=0),
  idavion int not null REFERENCES Avion(id),
  DateDebut date not null CHECK(DateDebut<date_assurance)
);

create table Entretien (
   id serial PRIMARY KEY,
   idavion int not null REFERENCES Avion(id),
   date_entretien date not null
);

create table detail_entretien(
     idEntretien int not null references Entretien(id),
     libelle VARCHAR(30) not null,
     Depense int check (Depense > 0)
);

CREATE TABLE Admin(
  id SERIAL PRIMARY KEY,
  nom VARCHAR(30) not null,
  email VARCHAR(40) not null UNIQUE,
  pwd VARCHAR(40) not null
);
create table Token(
  idAdmin int not null REFERENCES Admin(id),
  token VARCHAR(100) not null,
  DateExpiration TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP
);

-- 2022-12-21
-- data

insert into admin(nom,email,pwd) VALUES ('user','user@gmail.com','mmm');

INSERT into Avion(nom,photo) values ('airbus','sary.jpg');
INSERT into Avion(nom,photo) values ('antonov','sary1.jpg');
INSERT into Avion(nom,photo) values ('atr','sary2.jpg');
INSERT into Avion(nom,photo) values ('boeing','sary3.jpg');
INSERT into Avion(nom,photo) values ('bristol','sary4.jpg');


insert into kilometrage(idavion,date_Kil,debutKM,finKm) VALUES('1','2022-10-11',32,100);
insert into kilometrage(idavion,date_Kil,debutKM,finKm) VALUES('2','2022-02-21',12,100);
insert into kilometrage(idavion,date_Kil,debutKM,finKm) VALUES('3','2022-05-21',25,100);
insert into kilometrage(idavion,date_Kil,debutKM,finKm) VALUES('4','2022-08-21',15,100);
insert into kilometrage(idavion,date_Kil,debutKM,finKm) VALUES('5','2022-11-21',45,100);

insert into Assurance(date_assurance,a_payer,idavion,DateDebut) VALUES('2023-02-17',30000,1,'2022-12-17');
insert into Assurance(date_assurance,a_payer,idavion,DateDebut) VALUES('2023-03-15',50000,2,'2022-01-15');
insert into Assurance(date_assurance,a_payer,idavion,DateDebut) VALUES('2023-04-22',40000,3,'2022-02-22');
insert into Assurance(date_assurance,a_payer,idavion,DateDebut) VALUES('2023-06-27',40000,4,'2022-04-27');
insert into Assurance(date_assurance,a_payer,idavion,DateDebut) VALUES('2023-08-28',20000,5,'2022-06-28');



insert into Entretien(idavion,date_entretien) VALUES(1,'2022-02-03');
insert into Entretien(idavion,date_entretien) VALUES(1,'2022-03-03');
insert into Entretien(idavion,date_entretien) VALUES(2,'2023-04-05');
insert into Entretien(idavion,date_entretien) VALUES(3,'2022-06-07');
insert into Entretien(idavion,date_entretien) VALUES(4,'2023-08-02');
insert into Entretien(idavion,date_entretien) VALUES(5,'2023-11-04');



insert into detail_entretien(idEntretien,libelle,depense) VALUES(1,'vidange',60000);
insert into detail_entretien(idEntretien,libelle,depense) VALUES(2,'moteur',200000);
insert into detail_entretien(idEntretien,libelle,depense) VALUES(3,'Reparation pneu',120000);
insert into detail_entretien(idEntretien,libelle,depense) VALUES(4,'frein',180000);
insert into detail_entretien(idEntretien,libelle,depense) VALUES(5,'vidange',60000);
insert into detail_entretien(idEntretien,libelle,depense) VALUES(5,'frein',70000);
