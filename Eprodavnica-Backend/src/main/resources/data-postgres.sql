/*Musterija*/
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (0,'nesto@gmail.com','Jovan','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'Savić')
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (5,'nesto1@gmail.com','Marko','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'Tesić')


/*Prodavac*/
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (1,'nesto2@gmail.com','Nikola','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'Ščepić')
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (4,'nesto21@gmail.com','Vladimir','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'Nikloć')

/*Admin*/
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (2,'nesto3@gmail.com','bbbb','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'grgrgr')

/*Superadmin*/
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (3,'nesto4@gmail.com','nnnn','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'dwww')

/*Uloge*/
INSERT INTO uloga(id,name) VALUES (1,'ROLE_SUPERADMIN')
INSERT INTO uloga(id,name) VALUES (2,'ROLE_ADMIN')
INSERT INTO uloga(id,name) VALUES (3,'ROLE_PRODAVAC')
INSERT INTO uloga(id,name) VALUES (4,'ROLE_MUSTERIJA')

/*Privilegije*/
insert into privilegije (id,name) values (0,'LOGOUT')
insert into privilegije (id,name) values (1,'OPERACIJE_SA_SUPERADMINOM')
insert into privilegije (id,name) values (2,'OPERACIJE_SA_ADMINOM')
insert into privilegije (id,name) values (3,'OPERACIJE_SA_PRODAVCEM')
insert into privilegije (id,name) values (4,'OPERACIJE_SA_MUSTERIJOM')

insert into uloga_privilegije (role_id, privilege_id) values (1,0)
insert into uloga_privilegije (role_id, privilege_id) values (2,0)
insert into uloga_privilegije (role_id, privilege_id) values (3,0)
insert into uloga_privilegije (role_id, privilege_id) values (4,0)

insert into uloga_privilegije (role_id, privilege_id) values (1,1)
insert into uloga_privilegije (role_id, privilege_id) values (2,2)
insert into uloga_privilegije (role_id, privilege_id) values (3,3)
insert into uloga_privilegije (role_id, privilege_id) values (4,4)

/*Uloga konekcija*/
INSERT INTO korisnik_uloge(korisnik_list_user_id,uloge_id) VALUES (0,4)
INSERT INTO korisnik_uloge(korisnik_list_user_id,uloge_id) VALUES (1,3)
INSERT INTO korisnik_uloge(korisnik_list_user_id,uloge_id) VALUES (2,2)
INSERT INTO korisnik_uloge(korisnik_list_user_id,uloge_id) VALUES (3,1)

/*TIP*/
INSERT INTO tip (id,naziv) VALUES (0,'Voce')
INSERT INTO tip (id,naziv) VALUES (1,'Letnje')
INSERT INTO tip (id,naziv) VALUES (2,'Jesenje')
INSERT INTO tip (id,naziv) VALUES (3,'Računar')
INSERT INTO tip (id,naziv) VALUES (4,'Računarska komponenta')
INSERT INTO tip (id,naziv) VALUES (5,'Gaming komponenta')
INSERT INTO tip (id,naziv) VALUES (6,'Zvučnici')
INSERT INTO tip (id,naziv) VALUES (7,'Miš')
INSERT INTO tip (id,naziv) VALUES (8,'Monitor')

/*Produkt*/
INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('jagoda.jpg',0,200,'2023-09-21','Zdrava jagoda','Jagoda',5,5,'123',1,0,true,0,true)
INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('borovnica.jpg',1,100,'2023-09-21','Zdrava borovnica','Borovnica',-1,-1,'456',1,0,true,0,true)
INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('jabuka.jpg',2,200,'2023-09-21','Zdrava jabuka','Jabuka',-1,-1,'789',1,0,true,0,true)
INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('kolac.jpg',3,300,'2023-09-12','Kolač je vrsta peciva koji se pravi za određene praznike i svečane trenutke.','Kolač',-1,-1,'321',1,0,true,0,true)
INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('tepih.jpg',4,1300,'2022-09-12','Tepisi su tekstilije, koje pokrivaju podove ili zidove. U prvobitnoj formi to su bili komadi ka kojim se prilagođava celokupna dekoracija, uzorak i konstrukcija. Od 50-ih godina se proizvodi tepih u proizvoljnoj dužini odnosno traka u raznim širinama čak do 6 m koja se seče po potrebi korisnika.','Tepih',-1,-1,'654',1,0,true,0,true)

INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('racunar.jpg',5,70000,'2023-07-21','Složeni uređaj koji služi za izvršavanje matematičkih operacija ili kontrolnih operacija koje se mogu izraziti u numeričkom ili logičkom obliku. Računari su sastavljeni od komponenata koje obavljaju jednostavnije, jasno određene funkcije. Kompleksna interakcija tih komponenata omogućava računar da obrađuje informacije.','Računar',-1,-1,'987',4,0,true,0,true)
INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('monitor.jpg',6,20000,'2023-05-13','Monitor je električni izlazni uređaj koji služi za prikazivanje slike poslate sa drugog uređaja, obično grafičke karte u sklopu računara. Na njemu pratimo rezultate obrade i trenutna dešavanja.','Monitor',-1,-1,'258',4,0,true,0,true)
INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('zvucnici.jpg',7,5000,'2023-02-15','Prvi je zvučnik je bila telefonska slušalica koju je 1876. patentirao Aleksander Grejam Bel. Nedugo zatim usledila je poboljšana verzija Ernsta Simensa u Nemačkoj i Engleskoj 1878. Veruje se da je sličnu napravu stvorio 1881. i Nikola Tesla. Modernu konstrukciju zvučnika s pomičnom zavojnicom ostvario je Oliver Lodž u Engleskoj 1898.','Zvučnici',-1,-1,'147',4,0,true,0,true)
INSERT INTO produkt(naziv_slike,id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato,odobren_od_prodavca) VALUES ('mouse.jpg',8,3000,'2021-09-21','Miš je spoljašnji uređaj za unos podataka računara, jedan od sastavnih delova današnjeg stonog računara i funkcija mu je da se pomoću njega pomera kursor na ekranu monitora.','Miš',-1,-1,'369',4,0,true,0,true)

/*Veza tipa i produkta*/
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (0,0)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (1,1)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (2,2)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (2,0)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (2,1)

/*Recenzija*/
INSERT INTO recenzija(id,datum_pravljenja,komentar,ocena,musterija_user_id,produkt_id) VALUES (5000,'2022-12-1','wdwdwdw wdwdw wdw',5,0,0)

/*Racun*/
INSERT INTO racun(id,broj_racuna,datum_kreiranja,konacna_cena,musterija_user_id ,korpa) values (0,'qwe','2021-12-1',5000.0,0,false)
INSERT INTO artikal(id,broj,produkt_id,racun_id,akcija,cena,naziv_produkta,ukupna_cena) VALUES (5000,2,0,0,10,50,'Test',1000)

/*Wishlist*/
INSERT INTO korisnik_wishlist(wishlist_user_id,wishlist_id) VALUES (0,0)

/*Istorija kupljenih*/
INSERT INTO korisnik_istorija_kupljenih_produkata(istorija_kupaca_user_id,istorija_kupljenih_produkata_id) VALUES (0,0)
